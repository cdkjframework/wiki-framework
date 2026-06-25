[CmdletBinding()]
param(
  [Parameter(Position = 0)]
  [ValidateSet("install", "doc", "pack", "help")]
  [string]$Command = "help"
)

$ErrorActionPreference = "Stop"

$RootDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $RootDir

function Show-Help {
  Write-Host "--------------------------------------------------------------------------"
  Write-Host ""
  Write-Host "usage: .\wiki.ps1 [install | doc | pack]"
  Write-Host ""
  Write-Host "  install    Install wiki to your local Maven repository."
  Write-Host "  doc        Generate aggregated Java API docs under target/site/apidocs."
  Write-Host "  pack       Build jar packages by Maven."
  Write-Host ""
  Write-Host "--------------------------------------------------------------------------"
}

function Invoke-Maven {
  param(
    [Parameter(Mandatory = $true)]
    [string[]]$Arguments
  )

  & mvn @Arguments
  if ($LASTEXITCODE -ne 0) {
    throw "Maven command failed with exit code $LASTEXITCODE."
  }
}

switch ($Command) {
  "install" {
    Invoke-Maven @("-T", "1C", "clean", "source:jar", "javadoc:javadoc", "install", "-Dmaven.test.skip=true", "-Dmaven.javadoc.skip=false")
  }
  "doc" {
    Invoke-Maven @("javadoc:aggregate")

    $CustomIndex = Join-Path $RootDir "docs\apidocs\index.html"
    $TargetIndex = Join-Path $RootDir "target\site\apidocs\index.html"

    if ((Test-Path -LiteralPath $CustomIndex) -and (Test-Path -LiteralPath (Split-Path -Parent $TargetIndex))) {
      Copy-Item -LiteralPath $CustomIndex -Destination $TargetIndex -Force
    }
  }
  "pack" {
    Invoke-Maven @("-T", "1C", "clean", "source:jar", "javadoc:javadoc", "package", "-Dmaven.test.skip=true", "-Dmaven.javadoc.skip=false")
  }
  default {
    Show-Help
  }
}
