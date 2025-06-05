# Apache-Kafka-4.0
Here's a step-by-step documentation guide to **install and run Apache Kafka 4.0 on Windows**:

To install **Apache Kafka 4.0**, follow these steps based on your operating system and environment. Kafka 4.0 uses **KRaft mode by default** (no ZooKeeper). Here’s a simple step-by-step installation guide:

---

## ✅ Prerequisites

* **Java 17+** (Kafka 4.0 requires Java 17 for the broker)
* **JRE/JDK 11+** for Kafka clients/streams
* **Linux/macOS/Windows with WSL (Linux preferred for simplicity)**
* **curl or wget**, and `tar` or `unzip`

---

## 📥 Step 0: Download Kafka 4.0

From the official Apache site:

```bash
wget https://downloads.apache.org/kafka/4.0.0/kafka_2.13-4.0.0.tgz
```

Or via `curl`:

```bash
curl -O https://downloads.apache.org/kafka/4.0.0/kafka_2.13-4.0.0.tgz
```

---

## 📦 Step 1: Extract Kafka

```bash
tar -xzf kafka_2.13-4.0.0.tgz
cd kafka_2.13-4.0.0
```

````markdown

### a. Edit `server.properties`

Make sure `config/server.properties` has the following minimal configuration for **KRaft mode**:

```properties
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@localhost:9093
listeners=PLAINTEXT://localhost:9092,CONTROLLER://localhost:9093
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
inter.broker.listener.name=PLAINTEXT
log.dirs=/tmp/kraft-combined-logs
````

* `log.dirs` can be any writable directory.
* Make sure ports **9092** (for clients) and **9093** (for controller communication) are free.

---

### 2. Set up Logging Configuration

Set the environment variable for Kafka’s logging configuration:

```bash
export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///D:/kafka_2.13-4.0.0/config/tools-log4j2.yaml"
```

> 📝 Update the path to your actual Kafka directory if different.

---

### 3. Generate Cluster UUID

Kafka in KRaft mode requires a unique cluster ID:

```bash
./bin/kafka-storage.sh random-uuid
```

Example output:

```
k7uCOaJpRHi9hBGujYaCEg
```

---

### 4. Format the Storage Directory

Format Kafka’s log directory using the generated UUID:

```bash
./bin/kafka-storage.sh format -t k7uCOaJpRHi9hBGujYaCEg -c config/server.properties
```

Expected output:

```
Formatting metadata directory /tmp/kraft-combined-logs with metadata.version 4.0-IV3.
```

---

### 5. Start the Kafka Server

```bash
./bin/kafka-server-start.sh config/server.properties
```

Look for logs indicating successful startup and controller activation:

```
Performing controller activation...
Appending 3 bootstrap record(s)...
```

---

### 6. Create and List Topics (in another terminal)

#### Set logging config again:

```bash
export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///D:/kafka_2.13-4.0.0/config/tools-log4j2.yaml"
```

#### List existing topics:

```bash
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

#### Create a new topic:

```bash
./bin/kafka-topics.sh --create \
  --topic test-topic \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1
```

#### Verify the topic:

```bash
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

Output:

```
test-topic
```

---

## Notes

* Kafka 4.x uses **KRaft mode by default**, removing the need for ZooKeeper.
* `log.dirs` is the directory used for metadata and log storage. Make sure it’s writable.
* Firewall must allow ports `9092` and `9093`.

## References

* [Apache Kafka Official Documentation](https://kafka.apache.org/documentation/)
* [Kafka KRaft Mode Guide](https://kafka.apache.org/documentation/#kraft)

---

Happy streaming! 🚀

```

---

```
