package com.gym.equipment_service.service;

import com.gym.equipment_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificacionListener {

    @RabbitListener(queues = RabbitMQConfig.NOTIFICACIONES_QUEUE)
    public void getNotification(String mensaje) {
        System.out.println("Notification received: " + mensaje);
    }
}