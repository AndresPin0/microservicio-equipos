package com.gym.equipment_service.service;

import com.gym.equipment_service.config.RabbitMQConfig;
import com.gym.equipment_service.domain.Equipment;
import com.gym.equipment_service.repository.EquipmentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private EquipmentRepository equipmentRepository;

    private static final String EQUIPMENT_TOPIC = "equipment-events";


    public Equipment addEquipment(Equipment equipment) {

        Equipment savedEquipment = equipmentRepository.save(equipment);

        String rabbitMsg = "New equipment added: " + savedEquipment.getName() + " with quantity: " + savedEquipment.getQuantity();
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICACIONES_QUEUE, rabbitMsg);

        String kafkaMsg = "Equipment added - ID: " + savedEquipment.getId() + ", Name: " + savedEquipment.getName() + ", Quantity: " + savedEquipment.getQuantity();
        kafkaTemplate.send(EQUIPMENT_TOPIC, kafkaMsg);

        return savedEquipment;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }

    public Equipment updateEquipment(Long id, Equipment updatedEquipment) {
        Optional<Equipment> existing = equipmentRepository.findById(id);
        if (existing.isPresent()) {
            Equipment equipment = existing.get();
            equipment.setName(updatedEquipment.getName());
            equipment.setQuantity(updatedEquipment.getQuantity());
            equipment.setCondition(updatedEquipment.getCondition());

            String kafkaMsg = "Equipment updated - ID: " + updatedEquipment.getId() + ", Name: " + updatedEquipment.getName() + ", Condition: " + updatedEquipment.getCondition();
            kafkaTemplate.send(EQUIPMENT_TOPIC, kafkaMsg);

            return equipmentRepository.save(equipment);

        }
        throw new RuntimeException("Equipment not found with id: " + id);
    }

    public void updateEquipmentStatus(Long id, String newStatus){
        System.out.println("Equipment with id: " + id + " has been updated to: " + newStatus);

        String msg = "Equipment ID: " + id + " has been updated to: " + newStatus;
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICACIONES_QUEUE, msg);
    }

    public void deleteEquipment(Long id) {
        if(equipmentRepository.existsById(id)){
            equipmentRepository.deleteById(id);

            String kafkaMsg = "Equipment deleted - ID " + id;
            kafkaTemplate.send(EQUIPMENT_TOPIC, kafkaMsg);
        }else {
            throw new RuntimeException("Equipment not found with id: " + id);
        }
    }
}
