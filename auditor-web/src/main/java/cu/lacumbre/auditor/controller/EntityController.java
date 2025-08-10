package cu.lacumbre.auditor.controller;

import cu.lacumbre.auditor.dto.EntityDTO;
import cu.lacumbre.auditor.service.EntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de entidades comerciales
 */
@RestController
@RequestMapping("/entities")
@Tag(name = "Entidades", description = "API para la gestión de entidades comerciales")
public class EntityController {

    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @Operation(summary = "Obtener todas las entidades activas")
    @ApiResponse(responseCode = "200", description = "Lista de entidades obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<EntityDTO>> getAllEntities() {
        List<EntityDTO> entities = entityService.findAllActive();
        return ResponseEntity.ok(entities);
    }

    @Operation(summary = "Obtener entidad por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidad encontrada"),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityDTO> getEntityById(
            @Parameter(description = "ID de la entidad") @PathVariable Long id) {
        return entityService.findById(id)
                .map(entity -> ResponseEntity.ok(entity))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener entidad por código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidad encontrada"),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<EntityDTO> getEntityByCode(
            @Parameter(description = "Código de la entidad") @PathVariable String code) {
        return entityService.findByCode(code)
                .map(entity -> ResponseEntity.ok(entity))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar entidades por nombre")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    @GetMapping("/search")
    public ResponseEntity<List<EntityDTO>> searchEntitiesByName(
            @Parameter(description = "Nombre a buscar") @RequestParam String name) {
        List<EntityDTO> entities = entityService.findByNameContaining(name);
        return ResponseEntity.ok(entities);
    }

    @Operation(summary = "Crear nueva entidad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Entidad creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe una entidad con el mismo código")
    })
    @PostMapping
    public ResponseEntity<EntityDTO> createEntity(@Valid @RequestBody EntityDTO entityDTO) {
        try {
            EntityDTO createdEntity = entityService.create(entityDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(summary = "Actualizar entidad existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidad actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada"),
        @ApiResponse(responseCode = "409", description = "Ya existe otra entidad con el mismo código")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityDTO> updateEntity(
            @Parameter(description = "ID de la entidad") @PathVariable Long id,
            @Valid @RequestBody EntityDTO entityDTO) {
        try {
            EntityDTO updatedEntity = entityService.update(id, entityDTO);
            return ResponseEntity.ok(updatedEntity);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no encontrada")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
    }

    @Operation(summary = "Eliminar entidad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Entidad eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(
            @Parameter(description = "ID de la entidad") @PathVariable Long id) {
        try {
            entityService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Verificar si existe una entidad con el código dado")
    @ApiResponse(responseCode = "200", description = "Verificación realizada exitosamente")
    @GetMapping("/exists/code/{code}")
    public ResponseEntity<Boolean> existsByCode(
            @Parameter(description = "Código a verificar") @PathVariable String code) {
        boolean exists = entityService.existsByCode(code);
        return ResponseEntity.ok(exists);
    }
}