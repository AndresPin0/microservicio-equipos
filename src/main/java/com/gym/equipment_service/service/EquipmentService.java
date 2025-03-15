package com.gym.equipment_service.service;

import com.gym.equipment_service.config.RabbitMQConfig;
import com.gym.equipment_service.domain.Equipment;
import com.gym.equipment_service.repository.EquipmentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public Equipment addEquipment(Equipment equipment) {

        String msg = "New equipment added: " + equipment.getName() + " with quantity: " + equipment.getQuantity();
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICACIONES_QUEUE, msg);
        return equipmentRepository.save(equipment);
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
        equipmentRepository.deleteById(id);
    }
}
