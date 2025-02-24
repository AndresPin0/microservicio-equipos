package com.gym.equipment_service;

import com.gym.equipment_service.domain.Equipment;
import com.gym.equipment_service.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public void run(String... args) throws Exception {
        equipmentRepository.save(new Equipment(null, "Treadmill", 5, "Good"));
        equipmentRepository.save(new Equipment(null, "Dumbbells", 10, "Good"));
        equipmentRepository.save(new Equipment(null, "Bench Press", 3, "Good"));
    }
}
