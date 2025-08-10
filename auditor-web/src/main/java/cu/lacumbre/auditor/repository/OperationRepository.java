package cu.lacumbre.auditor.repository;

import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la gestión de operaciones comerciales
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    /**
     * Busca operaciones por entidad y tipo
     */
    List<Operation> findByEntityAndOperationTypeAndActiveTrue(Entity entity, Operation.OperationType operationType);

    /**
     * Busca operaciones por entidad con paginación
     */
    Page<Operation> findByEntityAndActiveTrue(Entity entity, Pageable pageable);

    /**
     * Busca operaciones por entidad y rango de fechas
     */
    @Query("SELECT o FROM Operation o WHERE o.entity = :entity AND o.operationDate BETWEEN :startDate AND :endDate AND o.active = true ORDER BY o.operationDate DESC")
    List<Operation> findByEntityAndDateRange(@Param("entity") Entity entity, 
                                           @Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);

    /**
     * Busca operaciones por entidad, tipo y rango de fechas
     */
    @Query("SELECT o FROM Operation o WHERE o.entity = :entity AND o.operationType = :operationType AND o.operationDate BETWEEN :startDate AND :endDate AND o.active = true ORDER BY o.operationDate DESC")
    List<Operation> findByEntityAndTypeAndDateRange(@Param("entity") Entity entity, 
                                                   @Param("operationType") Operation.OperationType operationType,
                                                   @Param("startDate") LocalDateTime startDate, 
                                                   @Param("endDate") LocalDateTime endDate);

    /**
     * Busca operaciones por estado
     */
    List<Operation> findByEntityAndStatusAndActiveTrue(Entity entity, Operation.OperationStatus status);

    /**
     * Calcula el total de ventas por entidad en un rango de fechas
     */
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Operation o WHERE o.entity = :entity AND o.operationType = 'SALE' AND o.status = 'COMPLETED' AND o.operationDate BETWEEN :startDate AND :endDate AND o.active = true")
    BigDecimal calculateSalesTotal(@Param("entity") Entity entity, 
                                  @Param("startDate") LocalDateTime startDate, 
                                  @Param("endDate") LocalDateTime endDate);

    /**
     * Calcula el total de compras por entidad en un rango de fechas
     */
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Operation o WHERE o.entity = :entity AND o.operationType = 'PURCHASE' AND o.status = 'COMPLETED' AND o.operationDate BETWEEN :startDate AND :endDate AND o.active = true")
    BigDecimal calculatePurchasesTotal(@Param("entity") Entity entity, 
                                      @Param("startDate") LocalDateTime startDate, 
                                      @Param("endDate") LocalDateTime endDate);

    /**
     * Calcula el total de ingresos por entidad en un rango de fechas
     */
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Operation o WHERE o.entity = :entity AND o.operationType = 'INCOME' AND o.status = 'COMPLETED' AND o.operationDate BETWEEN :startDate AND :endDate AND o.active = true")
    BigDecimal calculateIncomesTotal(@Param("entity") Entity entity, 
                                    @Param("startDate") LocalDateTime startDate, 
                                    @Param("endDate") LocalDateTime endDate);

    /**
     * Calcula el total de gastos por entidad en un rango de fechas
     */
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Operation o WHERE o.entity = :entity AND o.operationType = 'EXPENSE' AND o.status = 'COMPLETED' AND o.operationDate BETWEEN :startDate AND :endDate AND o.active = true")
    BigDecimal calculateExpensesTotal(@Param("entity") Entity entity, 
                                     @Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * Busca las últimas operaciones por entidad
     */
    @Query("SELECT o FROM Operation o WHERE o.entity = :entity AND o.active = true ORDER BY o.operationDate DESC")
    List<Operation> findRecentOperations(@Param("entity") Entity entity, Pageable pageable);

    /**
     * Cuenta operaciones por tipo y entidad
     */
    long countByEntityAndOperationTypeAndActiveTrue(Entity entity, Operation.OperationType operationType);

    /**
     * Busca operaciones por número de documento
     */
    List<Operation> findByEntityAndDocumentNumberContainingAndActiveTrue(Entity entity, String documentNumber);
}