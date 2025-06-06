package com.device.DeviceManagement.Config;
// File: src/main/java/com/example/kafkaorder/util/PartitionUpdater.java
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewPartitions;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

@Component
public class PartitionUpdater {

    public void increasePartitionCount(String topic, int newPartitionCount) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (AdminClient admin = AdminClient.create(props)) {
            admin.createPartitions(Collections.singletonMap(
                    topic, NewPartitions.increaseTo(newPartitionCount)
            )).all().get();
            System.out.println("🔄 Partition count updated to " + newPartitionCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}