If you **close Git Bash** and want to **run Kafka again later**, you **do not** need to repeat all setup steps. Here’s what you need to do to restart Kafka:

---

## ✅ Steps to Start Kafka Again (After Closing Git Bash)

> You only need to **re-run the format step once** when setting up the cluster the first time. After that, just follow these steps:

---

### 1. Open Git Bash and Set Logging Config Again

```bash
cd /d/kafka_2.13-4.0.0
export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///D:/kafka_2.13-4.0.0/config/tools-log4j2.yaml"
```

---

### 2. Start Kafka Server

```bash
./bin/kafka-server-start.sh config/server.properties
```

Let it run in this terminal. Kafka is now running.

---

### 3. (Optional) Use a Second Git Bash Terminal for Commands

Open another Git Bash terminal if you want to interact with Kafka (create topics, send messages, etc.):

```bash
cd /d/kafka_2.13-4.0.0
export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///D:/kafka_2.13-4.0.0/config/tools-log4j2.yaml"
```

Then use Kafka commands like:

```bash
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

---

## 🛑 Do NOT Re-run:

* `kafka-storage.sh random-uuid`
* `kafka-storage.sh format ...`

These are **one-time setup steps**. Running them again will erase your data.

---

Would you like a simple `.sh` script to automate these restart steps for you?
