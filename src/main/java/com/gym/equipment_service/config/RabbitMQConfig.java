package com.gym.equipment_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String NOTIFICACIONES_QUEUE = "notificaciones-queue";
    public static final String HORARIOS_EXCHANGE = "horarios-exchange";
    public static final String HORARIOS_QUEUE_ADMIN = "horarios-queue-admin";
    public static final String HORARIOS_QUEUE_TRAINER = "horarios-queue-trainer";

    @Bean
    public Queue notificacionesQueue() {
        return new Queue(NOTIFICACIONES_QUEUE, true); // Durable
    }

    @Bean
    public FanoutExchange horariosExchange() {
        return new FanoutExchange(HORARIOS_EXCHANGE);
    }

    @Bean
    public Queue horariosQueueAdmin() {
        return new Queue(HORARIOS_QUEUE_ADMIN, true);
    }

    @Bean
    public Queue horariosQueueTrainer() {
        return new Queue(HORARIOS_QUEUE_TRAINER, true);
    }

    @Bean
    public Binding bindingAdmin(FanoutExchange horariosExchange, Queue horariosQueueAdmin) {
        return BindingBuilder.bind(horariosQueueAdmin).to(horariosExchange);
    }

    @Bean
    public Binding bindingTrainer(FanoutExchange horariosExchange, Queue horariosQueueTrainer) {
        return BindingBuilder.bind(horariosQueueTrainer).to(horariosExchange);
    }

}
