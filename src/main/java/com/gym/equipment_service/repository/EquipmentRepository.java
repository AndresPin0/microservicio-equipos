package com.gym.equipment_service.repository;

import com.gym.equipment_service.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
