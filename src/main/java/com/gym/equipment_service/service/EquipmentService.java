package com.gym.equipment_service.service;

import com.gym.equipment_service.domain.Equipment;
import com.gym.equipment_service.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public Equipment addEquipment(Equipment equipment) {
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

    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }
}
