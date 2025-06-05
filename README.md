# Apache-Kafka-4.0
Here's a step-by-step documentation guide to **install and run Apache Kafka 4.0 on Windows**:

---

# Apache Kafka 4.0 Installation and Setup Guide on Windows

### Prerequisites:

* Java JDK 11 or later installed and `JAVA_HOME` environment variable configured.
* Git Bash or another Unix-like terminal emulator on Windows (e.g., MINGW64, Git Bash, or WSL).
* Basic knowledge of command line usage.

---

## 1. Download Kafka

1. Go to the official Apache Kafka download page:
   [https://kafka.apache.org/downloads](https://kafka.apache.org/downloads)

2. Download the **binary** for Scala 2.13 and Kafka 4.0, for example:
   `kafka_2.13-4.0.0.tgz`

3. Extract the downloaded archive to a convenient location, e.g.,
   `C:\Users\YOUR_USERNAME\Downloads\kafka_2.13-4.0.0`

---

## 2. Set Environment Variables (Optional but recommended)

Add Kafka's `bin/windows` (for running Windows scripts) or `bin` (for bash scripts) to your PATH or use full path commands in Git Bash.

---

## 3. Generate a Cluster ID (UUID)

Kafka 4.0 introduces the KRaft mode, which can run Kafka without ZooKeeper.

Run this in Git Bash or terminal:

```bash
cd /c/Users/YOUR_USERNAME/Downloads/kafka_2.13-4.0.0
export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///c:/Users/YOUR_USERNAME/Downloads/kafka_2.13-4.0.0/config/tools-log4j2.yaml"

./bin/kafka-storage.sh random-uuid
```

This outputs a UUID like `_5zEC1txQumyeV_tyFIlHg`. Save this value.

---

## 4. Configure Kafka Storage

Format the Kafka storage directory using the UUID:

```bash
./bin/kafka-storage.sh format -t YOUR_UUID_HERE -c config/server.properties
```

Example:

```bash
./bin/kafka-storage.sh format -t _5zEC1txQumyeV_tyFIlHg -c config/server.properties
```

This initializes Kafka's log storage for KRaft mode.

---

## 5. Edit `server.properties`

Make sure `config/server.properties` has the following minimal relevant configs for KRaft mode:

```properties
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@localhost:9093
listeners=PLAINTEXT://localhost:9092,CONTROLLER://localhost:9093
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
inter.broker.listener.name=PLAINTEXT
log.dirs=/tmp/kraft-combined-logs
```

* `log.dirs` can be any writable directory.
* Ports 9092 (client connections) and 9093 (controller) must be free.

---

## 6. Start Kafka Server

Run:

```bash
./bin/kafka-server-start.sh config/server.properties
```

You should see logs indicating Kafka started with KRaft mode and no ZooKeeper.

---

## 7. Create a Kafka Topic

In a new terminal, run:

```bash
./bin/kafka-topics.sh --create --topic my-first-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

If successful, it will print:

```
Created topic my-first-topic.
```

---

## 8. Verify Topics

List topics:

```bash
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

---

## 9. Produce and Consume Messages

* **Producer:**

```bash
./bin/kafka-console-producer.sh --topic my-first-topic --bootstrap-server localhost:9092
```

Type messages and hit enter to send.

* **Consumer:**

```bash
./bin/kafka-console-consumer.sh --topic my-first-topic --from-beginning --bootstrap-server localhost:9092
```

You will see the messages consumed live.

---

## Troubleshooting Tips

* **Log4j Configuration Errors:**
  Use a valid URI for `log4j.configurationFile` when setting `KAFKA_LOG4J_OPTS`, e.g.:
  `file:///c:/Users/YOUR_USERNAME/Downloads/kafka_2.13-4.0.0/config/tools-log4j2.yaml` (use forward slashes and `file:///` prefix).

* **Port Conflicts:**
  Make sure ports 9092 and 9093 are free before starting Kafka.

* **Kafka Storage Format Errors:**
  If you see errors about mismatched cluster IDs, delete your log directory (e.g., `/tmp/kraft-combined-logs`) and re-run `kafka-storage.sh format` with your UUID.

* **Running on Windows:**
  Use Git Bash or WSL for bash scripts. Alternatively, use `.bat` files in `bin/windows` if you prefer Windows cmd.

---

If you want, I can help you prepare a batch script or PowerShell script to automate this setup. Would you like that?
