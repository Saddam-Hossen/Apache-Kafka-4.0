@echo off
REM === Change to Kafka directory ===
cd /d D:\kafka_2.13-4.0.0

REM === Start Kafka using Git Bash ===
start "" "C:\Program Files\Git\bin\bash.exe" -c "export KAFKA_LOG4J_OPTS='-Dlog4j.configurationFile=file:///D:/kafka_2.13-4.0.0/config/tools-log4j2.yaml'; ./bin/kafka-server-start.sh config/server.properties"
