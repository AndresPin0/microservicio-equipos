package com.gym.equipment_service.service;

import com.gym.equipment_service.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    @KafkaListener(topics = KafkaConfig.EQUIPOS_TOPIC, groupId = "gimnasio-equipos-group")
    public void escucharEventosEquipos(String mensaje) {
        System.out.println("Kafka event received: " + mensaje);
    }
}
