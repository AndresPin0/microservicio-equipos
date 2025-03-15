package com.gym.equipment_service.service;

import com.gym.equipment_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class ScheduleListener {
    @RabbitListener(queues = RabbitMQConfig.HORARIOS_QUEUE_ADMIN)
    public void recibirCambioAdmin(String mensaje) {
        System.out.println("Admin notified: " + mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.HORARIOS_QUEUE_TRAINER)
    public void recibirCambioTrainer(String mensaje) {
        System.out.println("Trainer notified: " + mensaje);
    }
}
