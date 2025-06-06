package com.device.DeviceManagement.consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "login.insert", groupId = "login")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
