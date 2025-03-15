package com.gym.equipment_service.controller;

import com.gym.equipment_service.domain.Equipment;
import com.gym.equipment_service.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/equipos")
@Tag(name = "Gestión de Equipos", description = "Operaciones para administrar equipos del gimnasio")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @Operation(summary = "Crear un nuevo equipo",
            description = "Registra un equipo nuevo. Solo para ROLE_ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo creado exitosamente"),
            @ApiResponse(responseCode = "403", description = "Permisos insuficientes"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Equipment> addEquipment(@RequestBody Equipment equipment) {
        return ResponseEntity.ok(equipmentService.addEquipment(equipment));
    }

    @Operation(summary = "Listar todos los equipos",
            description = "Devuelve una lista de equipos. Accesible para ADMIN, TRAINER y MEMBER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida con éxito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado por falta de permisos")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER', 'ROLE_MEMBER')")
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }


    @Operation(summary = "Obtener un equipo por ID",
            description = "Devuelve un equipo por su ID. Accesible para ADMIN, TRAINER y MEMBER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo obtenido con éxito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado por falta de permisos"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER', 'ROLE_MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        return equipmentService.getEquipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un equipo",
            description = "Actualiza un equipo existente. Solo para ROLE_ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado con éxito"),
            @ApiResponse(responseCode = "403", description = "Permisos insuficientes"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable Long id, @RequestBody Equipment equipment) {
        return ResponseEntity.ok(equipmentService.updateEquipment(id, equipment));
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de un equipo", description = "Solo para ROLE_ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "403", description = "Permisos insuficientes")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String actualizarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        equipmentService.updateEquipmentStatus(id, nuevoEstado);
        return "Estado actualizado para equipo " + id;
    }

    @Operation(summary = "Eliminar un equipo",
            description = "Elimina un equipo existente. Solo para ROLE_ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipo eliminado con éxito"),
            @ApiResponse(responseCode = "403", description = "Permisos insuficientes"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
