[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

Write-Host "===================================" -ForegroundColor Green
Write-Host " Maven Central 发布脚本（Windows版）"
Write-Host "==================================="

mvn clean
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

mvn -P wiki test package
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

mvn -P wiki -DskipTests=true verify
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

mvn -P wiki -DskipTests=true deploy
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

Write-Host "DONE"
Write-Host "https://central.sonatype.com"