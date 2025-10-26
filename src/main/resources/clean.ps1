$filePath = "R:\java\workspace4\RatCam3\src\main\resources\common.xsd"
$content = (Get-Content $filePath) | ForEach-Object { $_.Trim() }
Set-Content -Path $filePath -Value $content