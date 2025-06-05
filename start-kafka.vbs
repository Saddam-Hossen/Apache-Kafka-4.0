Set WshShell = CreateObject("WScript.Shell")
WshShell.Run chr(34) & "D:\kafka_2.13-4.0.0\start-kafka.bat" & Chr(34), 0
Set WshShell = Nothing
