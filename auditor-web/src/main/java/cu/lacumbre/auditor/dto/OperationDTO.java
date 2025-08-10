package cu.lacumbre.auditor.dto;

import cu.lacumbre.auditor.model.Operation;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para la entidad Operation
 */
public class OperationDTO {

    private Long id;

    @NotNull(message = "El tipo de operación es obligatorio")
    private Operation.OperationType operationType;

    @NotNull(message = "La fecha de operación es obligatoria")
    private LocalDateTime operationDate;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", message = "El total debe ser mayor o igual a 0")
    private BigDecimal total;

    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observations;

    @Size(max = 50, message = "El número de documento no puede exceder 50 caracteres")
    private String documentNumber;

    @NotNull(message = "La entidad es obligatoria")
    private Long entityId;
    private String entityName;

    @NotNull(message = "El estado es obligatorio")
    private Operation.OperationStatus status;

    private List<OperationDetailDTO> details;

    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public OperationDTO() {}

    public OperationDTO(Operation.OperationType operationType, LocalDateTime operationDate, BigDecimal total, Long entityId) {
        this.operationType = operationType;
        this.operationDate = operationDate;
        this.total = total;
        this.entityId = entityId;
        this.status = Operation.OperationStatus.PENDING;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operation.OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(Operation.OperationType operationType) {
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

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Operation.OperationStatus getStatus() {
        return status;
    }

    public void setStatus(Operation.OperationStatus status) {
        this.status = status;
    }

    public List<OperationDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<OperationDetailDTO> details) {
        this.details = details;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
                "id=" + id +
                ", operationType=" + operationType +
                ", operationDate=" + operationDate +
                ", total=" + total +
                ", status=" + status +
                '}';
    }
}