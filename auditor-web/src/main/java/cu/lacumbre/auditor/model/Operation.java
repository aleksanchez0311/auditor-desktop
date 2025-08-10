package cu.lacumbre.auditor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una operación comercial (venta, compra, etc.)
 */
@jakarta.persistence.Entity
@Table(name = "operations")
public class Operation extends BaseEntity {

    @NotNull(message = "El tipo de operación es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType;

    @NotNull(message = "La fecha de operación es obligatoria")
    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", message = "El total debe ser mayor o igual a 0")
    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    @Column(name = "observations", length = 500)
    private String observations;

    @Size(max = 50, message = "El número de documento no puede exceder 50 caracteres")
    @Column(name = "document_number", length = 50)
    private String documentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    private Entity entity;

    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OperationDetail> details = new ArrayList<>();

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OperationStatus status = OperationStatus.PENDING;

    // Constructors
    public Operation() {}

    public Operation(OperationType operationType, LocalDateTime operationDate, BigDecimal total, Entity entity) {
        this.operationType = operationType;
        this.operationDate = operationDate;
        this.total = total;
        this.entity = entity;
    }

    // Getters and Setters
    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public List<OperationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OperationDetail> details) {
        this.details = details;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    // Helper methods
    public void addDetail(OperationDetail detail) {
        details.add(detail);
        detail.setOperation(this);
    }

    public void removeDetail(OperationDetail detail) {
        details.remove(detail);
        detail.setOperation(null);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + getId() +
                ", operationType=" + operationType +
                ", operationDate=" + operationDate +
                ", total=" + total +
                ", status=" + status +
                '}';
    }

    /**
     * Enum para los tipos de operación
     */
    public enum OperationType {
        SALE("Venta"),
        PURCHASE("Compra"),
        INCOME("Ingreso"),
        EXPENSE("Gasto"),
        TRANSFER("Transferencia");

        private final String displayName;

        OperationType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Enum para los estados de operación
     */
    public enum OperationStatus {
        PENDING("Pendiente"),
        COMPLETED("Completada"),
        CANCELLED("Cancelada");

        private final String displayName;

        OperationStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}