# Creates or updates the portfolio API on Azure Container Apps and binds its custom domain.
# Usage: az login; .\scripts\deploy-azure.ps1 [-Tag <commit sha>]
# Needs the image for the commit, published by the "Backend" GitHub Actions workflow on push to main.
# After the first run, CI deploys new images automatically; re-run this only to change infrastructure.
param(
    [string]$ResourceGroup = 'PORTFOLIO',
    [string]$Location = 'centralindia',
    [string]$Domain = 'api.anshrai.in',
    [string]$Tag = '',
    [string]$OldWebApp = 'ansh-portfolio',
    [string]$DeployerIdentity = 'oidc-msi-8571'
)
$ErrorActionPreference = 'Stop'
$root = (Resolve-Path "$PSScriptRoot\..").Path
$appName = 'portfolio-api'

function Assert-Az([string]$What) { if ($LASTEXITCODE -ne 0) { throw "$What failed (exit code $LASTEXITCODE)." } }
function Invoke-Quiet([scriptblock]$Command) {
    $saved = $ErrorActionPreference; $ErrorActionPreference = 'Continue'
    try { & $Command 2>$null } finally { $ErrorActionPreference = $saved }
}
function From-Json($text) { ($text | Out-String) | ConvertFrom-Json | ForEach-Object { $_ } }

Invoke-Quiet { az account show --output none } | Out-Null
if ($LASTEXITCODE -ne 0) { throw "Not signed in to Azure. Run 'az login' first." }

Write-Host '1/5 Checking the image'
if (-not $Tag) { $Tag = (git -C $root rev-parse HEAD).Trim() }
$repo = 'ansh5025/portfolio-api'
$token = (Invoke-RestMethod "https://ghcr.io/token?scope=repository:$($repo):pull").token
try {
    Invoke-WebRequest "https://ghcr.io/v2/$repo/manifests/$Tag" -Method Head -UseBasicParsing -Headers @{
        Authorization = "Bearer $token"
        Accept = 'application/vnd.oci.image.index.v1+json, application/vnd.docker.distribution.manifest.v2+json, application/vnd.oci.image.manifest.v1+json'
    } | Out-Null
} catch { throw "ghcr.io/$($repo):$Tag isn't published. Push the commit and wait for the 'Backend' workflow." }
$image = "ghcr.io/$($repo):$Tag"
Write-Host "  $image"

Write-Host '2/5 Collecting settings'
# Reuse the running app's secrets; on the first run, copy them from the old App Service. Never printed.
$values = @{}
$existing = Invoke-Quiet { az containerapp secret list --resource-group $ResourceGroup --name $appName --show-values --output json }
if ($LASTEXITCODE -eq 0 -and $existing) {
    foreach ($s in From-Json $existing) { $values[$s.name] = $s.value }
    $envs = From-Json (az containerapp show --resource-group $ResourceGroup --name $appName --query "properties.template.containers[0].env" --output json)
    foreach ($e in $envs) { if ($e.value) { $values[$e.name] = $e.value } }
    $resend = $values['resend-api-key']; $visitorKey = $values['visitor-log-access-key']
} else {
    foreach ($s in From-Json (az webapp config appsettings list --resource-group $ResourceGroup --name $OldWebApp --output json)) { $values[$s.name] = $s.value }
    Assert-Az "Reading settings from $OldWebApp"
    $resend = $values['RESEND_API_KEY']; $visitorKey = $values['VISITOR_LOG_ACCESS_KEY']
}
$principal = (az identity show --resource-group $ResourceGroup --name $DeployerIdentity --query principalId --output tsv).Trim()
Assert-Az "Looking up $DeployerIdentity"

$binding = From-Json (Invoke-Quiet { az containerapp hostname list --resource-group $ResourceGroup --name $appName --query "[?certificateId!=null] | [0].{name:name, cert:certificateId}" --output json })

Write-Host '3/5 Deploying (first run takes about 5 minutes)'
$parameters = @{
    image = $image; resendApiKey = "$resend"; visitorLogAccessKey = "$visitorKey"
    contactMailFrom = "$($values['CONTACT_MAIL_FROM'])"; contactMailTo = "$($values['CONTACT_MAIL_TO'])"
    deployerPrincipalId = $principal
}
if ($binding -and $binding.name) { $parameters.customDomain = $binding.name; $parameters.customDomainCertificateId = $binding.cert }
$paramsFile = Join-Path ([IO.Path]::GetTempPath()) "portfolio-params-$([guid]::NewGuid().ToString('N')).json"
$wrapped = @{}; foreach ($k in $parameters.Keys) { $wrapped[$k] = @{ value = $parameters[$k] } }
@{ '$schema' = 'https://schema.management.azure.com/schemas/2019-04-01/deploymentParameters.json#'; contentVersion = '1.0.0.0'; parameters = $wrapped } |
    ConvertTo-Json -Depth 5 | Set-Content -Path $paramsFile -Encoding utf8
try {
    $out = From-Json (az deployment group create --resource-group $ResourceGroup --name portfolio-api --template-file "$root\infra\main.bicep" `
        --parameters "@$paramsFile" "location=$Location" --query properties.outputs --output json)
    Assert-Az 'Deploying'
} finally { Remove-Item $paramsFile -ErrorAction SilentlyContinue }

Write-Host '4/5 Waiting for the new revision'
$deadline = (Get-Date).AddMinutes(10)
while ($true) {
    $state = From-Json (az containerapp show --resource-group $ResourceGroup --name $appName --query "{latest:properties.latestRevisionName, ready:properties.latestReadyRevisionName}" --output json)
    if ($state.latest -and $state.latest -eq $state.ready) { break }
    if ((Get-Date) -gt $deadline) { throw "Revision $($state.latest) didn't become ready. Logs: az containerapp logs show -g $ResourceGroup -n $appName" }
    Start-Sleep -Seconds 10
}
Write-Host "  Running at https://$($out.fqdn.value)"

Write-Host "5/5 Custom domain $Domain"
if ($binding -and $binding.name -eq $Domain) {
    Write-Host '  Already bound.'
} else {
    $label = $Domain.Substring(0, $Domain.IndexOf('.'))
    Write-Host "`n  Add these records where the DNS for $($Domain.Substring($label.Length + 1)) is managed:" -ForegroundColor Cyan
    Write-Host ("    CNAME  {0,-18} {1}" -f $label, $out.fqdn.value)
    Write-Host ("    TXT    {0,-18} {1}" -f "asuid.$label", $out.verificationId.value)
    Write-Host '  Waiting for them to appear in public DNS (checks every 30 s, up to 45 min)...'
    $deadline = (Get-Date).AddMinutes(45)
    do {
        $cname = foreach ($dns in '8.8.8.8', '1.1.1.1') { Resolve-DnsName $Domain -Type CNAME -Server $dns -DnsOnly -ErrorAction SilentlyContinue | Where-Object Type -eq 'CNAME' }
        $txt = foreach ($dns in '8.8.8.8', '1.1.1.1') { Resolve-DnsName "asuid.$Domain" -Type TXT -Server $dns -DnsOnly -ErrorAction SilentlyContinue | Where-Object Type -eq 'TXT' }
        $ready = [bool]($cname | Where-Object { $_.NameHost.TrimEnd('.') -eq $out.fqdn.value }) -and [bool]($txt | Where-Object { ($_.Strings -join '') -eq $out.verificationId.value })
        if (-not $ready) { Start-Sleep -Seconds 30 }
    } until ($ready -or (Get-Date) -gt $deadline)
    if (-not $ready) { throw 'DNS records not visible yet. Re-run this script once they are.' }
    Invoke-Quiet { az containerapp hostname add --resource-group $ResourceGroup --name $appName --hostname $Domain --output none } | Out-Null
    az containerapp hostname bind --resource-group $ResourceGroup --name $appName --hostname $Domain `
        --environment $out.environmentId.value --validation-method CNAME --output none
    Assert-Az "Binding $Domain"
}
Write-Host "`nPortfolio API: https://$Domain/api" -ForegroundColor Green
