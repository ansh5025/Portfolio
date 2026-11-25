// Portfolio API on Azure Container Apps: one small always-on replica (instant responses) with visitor logs on Azure Files.
// Deploy with scripts/deploy-azure.ps1; CI then rolls out new images with `az containerapp update`.
// Azure for Students allows one Container Apps environment per subscription, so this app joins the existing one.

@description('Must match the shared environment\'s region.')
param location string = 'japaneast'
param prefix string = 'portfolio'

@description('Resource group and name of the shared Container Apps environment.')
param environmentResourceGroup string = 'pulsedesk-rg'
param environmentName string = 'pulsedesk-env'

@description('Public image, e.g. ghcr.io/ansh5025/portfolio-api:<commit sha>.')
param image string

@secure()
param resendApiKey string = ''

@secure()
param visitorLogAccessKey string = ''

param contactMailFrom string = ''
param contactMailTo string = ''

@description('Principal of the GitHub Actions identity that rolls out new images.')
param deployerPrincipalId string = ''

param customDomain string = ''
param customDomainCertificateId string = ''

var suffix = uniqueString(resourceGroup().id)
var appName = '${prefix}-api'

resource env 'Microsoft.App/managedEnvironments@2026-07-01' existing = {
  name: environmentName
  scope: resourceGroup(environmentResourceGroup)
}

resource storage 'Microsoft.Storage/storageAccounts@2023-05-01' = {
  name: '${prefix}st${suffix}'
  location: location
  sku: { name: 'Standard_LRS' }
  kind: 'StorageV2'
  properties: {
    minimumTlsVersion: 'TLS1_2'
    supportsHttpsTrafficOnly: true
    allowBlobPublicAccess: false
  }
}

resource share 'Microsoft.Storage/storageAccounts/fileServices/shares@2023-05-01' = {
  name: '${storage.name}/default/visitorlogs'
  properties: { shareQuota: 1 }
}

module envStorage 'env-storage.bicep' = {
  name: 'portfolio-env-storage'
  scope: resourceGroup(environmentResourceGroup)
  dependsOn: [share]
  params: {
    environmentName: environmentName
    storageName: 'portfolio-visitorlogs'
    accountName: storage.name
    accountKey: storage.listKeys().keys[0].value
    shareName: 'visitorlogs'
  }
}

resource app 'Microsoft.App/containerApps@2026-07-01' = {
  name: appName
  location: location
  properties: {
    environmentId: env.id
    workloadProfileName: 'Consumption'
    configuration: {
      activeRevisionsMode: 'Single'
      ingress: {
        external: true
        targetPort: 8080
        transport: 'http'
        allowInsecure: false
        customDomains: empty(customDomainCertificateId) ? [] : [
          { name: customDomain, certificateId: customDomainCertificateId, bindingType: 'SniEnabled' }
        ]
      }
      secrets: [
        { name: 'resend-api-key', value: resendApiKey }
        { name: 'visitor-log-access-key', value: visitorLogAccessKey }
      ]
    }
    template: {
      containers: [
        {
          name: 'api'
          image: image
          resources: { cpu: json('0.25'), memory: '0.5Gi' }
          env: [
            { name: 'RESEND_API_KEY', secretRef: 'resend-api-key' }
            { name: 'VISITOR_LOG_ACCESS_KEY', secretRef: 'visitor-log-access-key' }
            { name: 'CONTACT_MAIL_FROM', value: contactMailFrom }
            { name: 'CONTACT_MAIL_TO', value: contactMailTo }
            { name: 'VISITOR_LOG_JSON_FILE', value: '/data/landing-visits.json' }
            // Use the client IP from the ingress proxy's X-Forwarded-For header.
            { name: 'SERVER_FORWARD_HEADERS_STRATEGY', value: 'native' }
          ]
          volumeMounts: [
            { volumeName: 'visitorlogs', mountPath: '/data' }
          ]
          probes: [
            {
              type: 'Startup'
              httpGet: { path: '/actuator/health', port: 8080 }
              initialDelaySeconds: 20
              periodSeconds: 10
              failureThreshold: 18
            }
            {
              type: 'Liveness'
              httpGet: { path: '/actuator/health', port: 8080 }
              periodSeconds: 30
              failureThreshold: 3
            }
          ]
        }
      ]
      volumes: [
        {
          name: 'visitorlogs'
          storageType: 'AzureFile'
          storageName: envStorage.outputs.name
          // The image runs as uid/gid 1000; SMB has no Unix ownership, so present the share as that user.
          mountOptions: 'uid=1000,gid=1000,dir_mode=0750,file_mode=0640,nobrl'
        }
      ]
      // One always-on replica: no cold starts, and one writer for the visitor log file.
      scale: { minReplicas: 1, maxReplicas: 1 }
    }
  }
}

var contributorRoleId = 'b24988ac-6180-42a0-ab88-20f7382dd24c'

// Lets GitHub Actions (OIDC) roll out new images to this app only.
resource deployerRole 'Microsoft.Authorization/roleAssignments@2022-04-01' = if (!empty(deployerPrincipalId)) {
  name: guid(app.id, deployerPrincipalId, contributorRoleId)
  scope: app
  properties: {
    principalId: deployerPrincipalId
    principalType: 'ServicePrincipal'
    roleDefinitionId: subscriptionResourceId('Microsoft.Authorization/roleDefinitions', contributorRoleId)
  }
}

output appName string = app.name
output fqdn string = app.properties.configuration.ingress.fqdn
output verificationId string = app.properties.customDomainVerificationId
output environmentId string = env.id
