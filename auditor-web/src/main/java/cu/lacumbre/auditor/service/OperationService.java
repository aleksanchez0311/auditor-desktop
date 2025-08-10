package cu.lacumbre.auditor.service;

import cu.lacumbre.auditor.dto.OperationDTO;
import cu.lacumbre.auditor.dto.OperationDetailDTO;
import cu.lacumbre.auditor.mapper.OperationMapper;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.OperationDetail;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de operaciones comerciales
 */
@Service
@Transactional
public class OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private final EntityService entityService;
    private final ProductService productService;

    @Autowired
    public OperationService(OperationRepository operationRepository,
                           OperationMapper operationMapper,
                           EntityService entityService,
                           ProductService productService) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
        this.entityService = entityService;
        this.productService = productService;
    }

    /**
     * Obtiene operaciones por entidad con paginación
     */
    @Transactional(readOnly = true)
    public Page<OperationDTO> findByEntityWithPagination(Long entityId, Pageable pageable) {
        Entity entity = entityService.getEntityById(entityId);
        Page<Operation> operations = operationRepository.findByEntityAndActiveTrue(entity, pageable);
        return operations.map(operationMapper::toDTO);
    }

    /**
     * Busca una operación por ID
     */
    @Transactional(readOnly = true)
    public Optional<OperationDTO> findById(Long id) {
        return operationRepository.findById(id)
                .filter(Operation::getActive)
                .map(operationMapper::toDTO);
    }

    /**
     * Obtiene operaciones por entidad y tipo
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findByEntityAndType(Long entityId, Operation.OperationType operationType) {
        Entity entity = entityService.getEntityById(entityId);
        List<Operation> operations = operationRepository.findByEntityAndOperationTypeAndActiveTrue(entity, operationType);
        return operationMapper.toDTOList(operations);
    }

    /**
     * Obtiene operaciones por rango de fechas
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findByDateRange(Long entityId, LocalDateTime startDate, LocalDateTime endDate) {
        Entity entity = entityService.getEntityById(entityId);
        List<Operation> operations = operationRepository.findByEntityAndDateRange(entity, startDate, endDate);
        return operationMapper.toDTOList(operations);
    }

    /**
     * Obtiene operaciones por entidad, tipo y rango de fechas
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findByEntityTypeAndDateRange(Long entityId, Operation.OperationType operationType, 
                                                          LocalDateTime startDate, LocalDateTime endDate) {
        Entity entity = entityService.getEntityById(entityId);
        List<Operation> operations = operationRepository.findByEntityAndTypeAndDateRange(entity, operationType, startDate, endDate);
        return operationMapper.toDTOList(operations);
    }

    /**
     * Obtiene las operaciones más recientes
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findRecentOperations(Long entityId, int limit) {
        Entity entity = entityService.getEntityById(entityId);
        Pageable pageable = PageRequest.of(0, limit);
        List<Operation> operations = operationRepository.findRecentOperations(entity, pageable);
        return operationMapper.toDTOList(operations);
    }

    /**
     * Crea una nueva operación
     */
    public OperationDTO create(OperationDTO operationDTO) {
        Entity entity = entityService.getEntityById(operationDTO.getEntityId());

        Operation operation = operationMapper.toEntity(operationDTO);
        operation.setEntity(entity);
        operation.setActive(true);

        // Establecer fecha actual si no se proporciona
        if (operation.getOperationDate() == null) {
            operation.setOperationDate(LocalDateTime.now());
        }

        // Establecer estado por defecto
        if (operation.getStatus() == null) {
            operation.setStatus(Operation.OperationStatus.PENDING);
        }

        // Procesar detalles si existen
        if (operationDTO.getDetails() != null && !operationDTO.getDetails().isEmpty()) {
            BigDecimal calculatedTotal = BigDecimal.ZERO;
            
            for (OperationDetailDTO detailDTO : operationDTO.getDetails()) {
                OperationDetail detail = operationMapper.toDetailEntity(detailDTO);
                Product product = productService.getProductById(detailDTO.getProductId());
                detail.setProduct(product);
                detail.setOperation(operation);
                
                // Calcular subtotal si no se proporciona
                if (detail.getSubtotal() == null) {
                    detail.setSubtotal(detail.getQuantity().multiply(detail.getUnitPrice()));
                }
                
                operation.addDetail(detail);
                calculatedTotal = calculatedTotal.add(detail.getSubtotal());
            }
            
            // Actualizar total si no se proporciona o es diferente
            if (operation.getTotal() == null || operation.getTotal().compareTo(calculatedTotal) != 0) {
                operation.setTotal(calculatedTotal);
            }
        }

        Operation savedOperation = operationRepository.save(operation);
        return operationMapper.toDTO(savedOperation);
    }

    /**
     * Actualiza una operación existente
     */
    public OperationDTO update(Long id, OperationDTO operationDTO) {
        Operation existingOperation = operationRepository.findById(id)
                .filter(Operation::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Operación no encontrada con ID: " + id));

        // No permitir actualizar operaciones completadas
        if (existingOperation.getStatus() == Operation.OperationStatus.COMPLETED) {
            throw new IllegalStateException("No se puede modificar una operación completada");
        }

        Entity entity = entityService.getEntityById(operationDTO.getEntityId());
        operationMapper.updateEntityFromDTO(operationDTO, existingOperation);
        existingOperation.setEntity(entity);

        Operation updatedOperation = operationRepository.save(existingOperation);
        return operationMapper.toDTO(updatedOperation);
    }

    /**
     * Completa una operación (cambia estado y actualiza inventario)
     */
    public OperationDTO completeOperation(Long id) {
        Operation operation = operationRepository.findById(id)
                .filter(Operation::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Operación no encontrada con ID: " + id));

        if (operation.getStatus() == Operation.OperationStatus.COMPLETED) {
            throw new IllegalStateException("La operación ya está completada");
        }

        if (operation.getStatus() == Operation.OperationStatus.CANCELLED) {
            throw new IllegalStateException("No se puede completar una operación cancelada");
        }

        // Actualizar inventario según el tipo de operación
        updateInventoryForOperation(operation);

        operation.setStatus(Operation.OperationStatus.COMPLETED);
        Operation completedOperation = operationRepository.save(operation);
        return operationMapper.toDTO(completedOperation);
    }

    /**
     * Cancela una operación
     */
    public OperationDTO cancelOperation(Long id) {
        Operation operation = operationRepository.findById(id)
                .filter(Operation::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Operación no encontrada con ID: " + id));

        if (operation.getStatus() == Operation.OperationStatus.COMPLETED) {
            throw new IllegalStateException("No se puede cancelar una operación completada");
        }

        operation.setStatus(Operation.OperationStatus.CANCELLED);
        Operation cancelledOperation = operationRepository.save(operation);
        return operationMapper.toDTO(cancelledOperation);
    }

    /**
     * Elimina una operación (soft delete)
     */
    public void delete(Long id) {
        Operation operation = operationRepository.findById(id)
                .filter(Operation::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Operación no encontrada con ID: " + id));

        if (operation.getStatus() == Operation.OperationStatus.COMPLETED) {
            throw new IllegalStateException("No se puede eliminar una operación completada");
        }

        operation.setActive(false);
        operationRepository.save(operation);
    }

    /**
     * Calcula totales por tipo de operación en un rango de fechas
     */
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalByType(Long entityId, Operation.OperationType operationType, 
                                          LocalDateTime startDate, LocalDateTime endDate) {
        Entity entity = entityService.getEntityById(entityId);
        
        return switch (operationType) {
            case SALE -> operationRepository.calculateSalesTotal(entity, startDate, endDate);
            case PURCHASE -> operationRepository.calculatePurchasesTotal(entity, startDate, endDate);
            case INCOME -> operationRepository.calculateIncomesTotal(entity, startDate, endDate);
            case EXPENSE -> operationRepository.calculateExpensesTotal(entity, startDate, endDate);
            default -> BigDecimal.ZERO;
        };
    }

    /**
     * Actualiza el inventario según el tipo de operación
     */
    private void updateInventoryForOperation(Operation operation) {
        for (OperationDetail detail : operation.getDetails()) {
            Product product = detail.getProduct();
            BigDecimal quantity = detail.getQuantity();

            switch (operation.getOperationType()) {
                case SALE -> {
                    // Reducir stock en ventas
                    productService.adjustStock(product.getId(), quantity.negate());
                }
                case PURCHASE -> {
                    // Aumentar stock en compras
                    productService.adjustStock(product.getId(), quantity);
                }
                // INCOME, EXPENSE, TRANSFER no afectan directamente el inventario
            }
        }
    }

    /**
     * Cuenta operaciones por tipo
     */
    @Transactional(readOnly = true)
    public long countByEntityAndType(Long entityId, Operation.OperationType operationType) {
        Entity entity = entityService.getEntityById(entityId);
        return operationRepository.countByEntityAndOperationTypeAndActiveTrue(entity, operationType);
    }
}