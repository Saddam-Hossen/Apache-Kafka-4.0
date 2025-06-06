package com.device.DeviceManagement.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic loginInsert() {
        return new NewTopic("login.insert", 5, (short) 3);
    }
}
