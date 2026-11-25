// Registers an Azure Files share with the shared Container Apps environment (which lives in another resource group).
param environmentName string
param storageName string
param accountName string
@secure()
param accountKey string
param shareName string

resource env 'Microsoft.App/managedEnvironments@2026-07-01' existing = {
  name: environmentName
}

resource storage 'Microsoft.App/managedEnvironments/storages@2026-07-01' = {
  parent: env
  name: storageName
  properties: {
    azureFile: {
      accountName: accountName
      accountKey: accountKey
      shareName: shareName
      accessMode: 'ReadWrite'
    }
  }
}

output name string = storage.name
