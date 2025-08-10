package cu.lacumbre.auditor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Entidad que representa un producto en el sistema
 */
@jakarta.persistence.Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    @Column(name = "code", length = 50, unique = true)
    private String code;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor que 0")
    @Column(name = "sale_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @DecimalMin(value = "0.0", message = "El precio de costo debe ser mayor o igual a 0")
    @Column(name = "cost_price", precision = 10, scale = 2)
    private BigDecimal costPrice;

    @DecimalMin(value = "0.0", message = "El stock debe ser mayor o igual a 0")
    @Column(name = "stock", precision = 10, scale = 3)
    private BigDecimal stock = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "El stock mínimo debe ser mayor o igual a 0")
    @Column(name = "min_stock", precision = 10, scale = 3)
    private BigDecimal minStock = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_unit_id")
    private MeasureUnit measureUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private TPVCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    private Entity entity;

    @Column(name = "is_buildable")
    private Boolean isBuildable = false;

    @Column(name = "is_sellable")
    private Boolean isSellable = true;

    // Constructors
    public Product() {}

    public Product(String name, String code, BigDecimal salePrice, Entity entity) {
        this.name = name;
        this.code = code;
        this.salePrice = salePrice;
        this.entity = entity;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getMinStock() {
        return minStock;
    }

    public void setMinStock(BigDecimal minStock) {
        this.minStock = minStock;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public TPVCategory getCategory() {
        return category;
    }

    public void setCategory(TPVCategory category) {
        this.category = category;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Boolean getIsBuildable() {
        return isBuildable;
    }

    public void setIsBuildable(Boolean isBuildable) {
        this.isBuildable = isBuildable;
    }

    public Boolean getIsSellable() {
        return isSellable;
    }

    public void setIsSellable(Boolean isSellable) {
        this.isSellable = isSellable;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", salePrice=" + salePrice +
                '}';
    }
}