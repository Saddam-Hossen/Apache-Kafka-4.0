# 📝 Kafka KRaft Mode: One-Click Startup on Windows Using Git Bash

This guide helps you run Kafka in **KRaft mode** on Windows by just **clicking a file**, without needing to reopen Git Bash every time manually.

---

## 📁 Directory Structure

Assume you have Kafka installed at:

```
D:\kafka_2.13-4.0.0\
```

And Git Bash is installed at the default path:

```
C:\Program Files\Git\bin\bash.exe
```

---

## ⚙️ 1. Update Kafka Configuration for KRaft

Edit the file:

```
D:\kafka_2.13-4.0.0\config\server.properties
```

Make sure it contains:

```properties
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@localhost:9093
listeners=PLAINTEXT://localhost:9092,CONTROLLER://localhost:9093
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
inter.broker.listener.name=PLAINTEXT
log.dirs=/tmp/kraft-combined-logs
```

> Ensure `/tmp/kraft-combined-logs` is a valid, writable directory.
> Ports **9092** and **9093** must be free.

---

## 🧪 2. Format Kafka Storage (One-Time Only)

Open Git Bash and run:

```bash
cd /d/kafka_2.13-4.0.0
export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///D:/kafka_2.13-4.0.0/config/tools-log4j2.yaml"
./bin/kafka-storage.sh random-uuid
# Copy the UUID, e.g., abc123def456
./bin/kafka-storage.sh format -t abc123def456 -c config/server.properties
```

---

## ⚙️ 3. Create `start-kafka.bat`

Save this file as:

```
D:\kafka_2.13-4.0.0\start-kafka.bat
```

### ✅ Content:

```bat
@echo off
REM === Change to Kafka directory ===
cd /d D:\kafka_2.13-4.0.0

REM === Start Kafka using Git Bash ===
start "" "C:\Program Files\Git\bin\bash.exe" -c "export KAFKA_LOG4J_OPTS='-Dlog4j.configurationFile=file:///D:/kafka_2.13-4.0.0/config/tools-log4j2.yaml'; ./bin/kafka-server-start.sh config/server.properties"
```

---

## 🪄 4. Create `start-kafka.vbs` (for silent launch)

Save this file as:

```
D:\kafka_2.13-4.0.0\start-kafka.vbs
```

### ✅ Content:

```vbscript
Set WshShell = CreateObject("WScript.Shell")
WshShell.Run chr(34) & "D:\kafka_2.13-4.0.0\start-kafka.bat" & Chr(34), 0
Set WshShell = Nothing
```

This runs Kafka **without opening an extra Command Prompt window**.

---

## 🚀 How to Run Kafka (KRaft Mode)

Double-click:

```
D:\kafka_2.13-4.0.0\start-kafka.vbs
```

Kafka will start using Git Bash with the KRaft controller.

---

## 🧪 Test It’s Working

Once Kafka starts:

Run this in Git Bash:

```bash
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

Or create a topic:

```bash
./bin/kafka-topics.sh --create \
  --topic test-topic \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1
```

---

## ✅ Result

You now have a one-click startup setup for Kafka in KRaft mode using:

* ✅ `start-kafka.bat` — launches via Git Bash
* ✅ `start-kafka.vbs` — hides the command prompt window for a cleaner experience

---
