package com.gym.equipment_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String EQUIPOS_TOPIC = "equipos-topic";

    @Bean
    public NewTopic equiposTopic() {
        return TopicBuilder.name(EQUIPOS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}