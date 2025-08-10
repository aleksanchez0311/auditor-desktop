package cu.lacumbre.auditor.controller;

import cu.lacumbre.auditor.dto.OperationDTO;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para la gestión de operaciones comerciales
 */
@RestController
@RequestMapping("/operations")
@Tag(name = "Operaciones", description = "API para la gestión de operaciones comerciales")
public class OperationController {

    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @Operation(summary = "Obtener operaciones por entidad con paginación")
    @ApiResponse(responseCode = "200", description = "Página de operaciones obtenida exitosamente")
    @GetMapping("/entity/{entityId}")
    public ResponseEntity<Page<OperationDTO>> getOperationsByEntity(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            Pageable pageable) {
        Page<OperationDTO> operations = operationService.findByEntityWithPagination(entityId, pageable);
        return ResponseEntity.ok(operations);
    }

    @Operation(summary = "Obtener operación por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación encontrada"),
        @ApiResponse(responseCode = "404", description = "Operación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperationById(
            @Parameter(description = "ID de la operación") @PathVariable Long id) {
        return operationService.findById(id)
                .map(operation -> ResponseEntity.ok(operation))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener operaciones por entidad y tipo")
    @ApiResponse(responseCode = "200", description = "Lista de operaciones obtenida exitosamente")
    @GetMapping("/entity/{entityId}/type/{operationType}")
    public ResponseEntity<List<OperationDTO>> getOperationsByEntityAndType(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            @Parameter(description = "Tipo de operación") @PathVariable cu.lacumbre.auditor.model.Operation.OperationType operationType) {
        List<OperationDTO> operations = operationService.findByEntityAndType(entityId, operationType);
        return ResponseEntity.ok(operations);
    }

    @Operation(summary = "Obtener operaciones por rango de fechas")
    @ApiResponse(responseCode = "200", description = "Lista de operaciones obtenida exitosamente")
    @GetMapping("/entity/{entityId}/date-range")
    public ResponseEntity<List<OperationDTO>> getOperationsByDateRange(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            @Parameter(description = "Fecha de inicio") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Fecha de fin") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<OperationDTO> operations = operationService.findByDateRange(entityId, startDate, endDate);
        return ResponseEntity.ok(operations);
    }

    @Operation(summary = "Obtener operaciones por entidad, tipo y rango de fechas")
    @ApiResponse(responseCode = "200", description = "Lista de operaciones obtenida exitosamente")
    @GetMapping("/entity/{entityId}/type/{operationType}/date-range")
    public ResponseEntity<List<OperationDTO>> getOperationsByEntityTypeAndDateRange(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            @Parameter(description = "Tipo de operación") @PathVariable cu.lacumbre.auditor.model.Operation.OperationType operationType,
            @Parameter(description = "Fecha de inicio") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Fecha de fin") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<OperationDTO> operations = operationService.findByEntityTypeAndDateRange(entityId, operationType, startDate, endDate);
        return ResponseEntity.ok(operations);
    }

    @Operation(summary = "Obtener operaciones recientes")
    @ApiResponse(responseCode = "200", description = "Lista de operaciones recientes obtenida exitosamente")
    @GetMapping("/entity/{entityId}/recent")
    public ResponseEntity<List<OperationDTO>> getRecentOperations(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            @Parameter(description = "Límite de resultados") @RequestParam(defaultValue = "10") int limit) {
        List<OperationDTO> operations = operationService.findRecentOperations(entityId, limit);
        return ResponseEntity.ok(operations);
    }

    @Operation(summary = "Crear nueva operación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Operación creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<OperationDTO> createOperation(@Valid @RequestBody OperationDTO operationDTO) {
        try {
            OperationDTO createdOperation = operationService.create(operationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOperation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar operación existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o operación completada"),
        @ApiResponse(responseCode = "404", description = "Operación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OperationDTO> updateOperation(
            @Parameter(description = "ID de la operación") @PathVariable Long id,
            @Valid @RequestBody OperationDTO operationDTO) {
        try {
            OperationDTO updatedOperation = operationService.update(id, operationDTO);
            return ResponseEntity.ok(updatedOperation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Completar operación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación completada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Operación ya completada o cancelada"),
        @ApiResponse(responseCode = "404", description = "Operación no encontrada")
    })
    @PutMapping("/{id}/complete")
    public ResponseEntity<OperationDTO> completeOperation(
            @Parameter(description = "ID de la operación") @PathVariable Long id) {
        try {
            OperationDTO completedOperation = operationService.completeOperation(id);
            return ResponseEntity.ok(completedOperation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Cancelar operación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación cancelada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Operación ya completada"),
        @ApiResponse(responseCode = "404", description = "Operación no encontrada")
    })
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OperationDTO> cancelOperation(
            @Parameter(description = "ID de la operación") @PathVariable Long id) {
        try {
            OperationDTO cancelledOperation = operationService.cancelOperation(id);
            return ResponseEntity.ok(cancelledOperation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar operación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Operación eliminada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Operación completada no se puede eliminar"),
        @ApiResponse(responseCode = "404", description = "Operación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(
            @Parameter(description = "ID de la operación") @PathVariable Long id) {
        try {
            operationService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Calcular total por tipo de operación en rango de fechas")
    @ApiResponse(responseCode = "200", description = "Total calculado exitosamente")
    @GetMapping("/entity/{entityId}/total/{operationType}")
    public ResponseEntity<BigDecimal> calculateTotalByType(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            @Parameter(description = "Tipo de operación") @PathVariable cu.lacumbre.auditor.model.Operation.OperationType operationType,
            @Parameter(description = "Fecha de inicio") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Fecha de fin") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal total = operationService.calculateTotalByType(entityId, operationType, startDate, endDate);
        return ResponseEntity.ok(total);
    }

    @Operation(summary = "Contar operaciones por tipo")
    @ApiResponse(responseCode = "200", description = "Conteo realizado exitosamente")
    @GetMapping("/entity/{entityId}/count/{operationType}")
    public ResponseEntity<Long> countOperationsByType(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            @Parameter(description = "Tipo de operación") @PathVariable cu.lacumbre.auditor.model.Operation.OperationType operationType) {
        long count = operationService.countByEntityAndType(entityId, operationType);
        return ResponseEntity.ok(count);
    }
}