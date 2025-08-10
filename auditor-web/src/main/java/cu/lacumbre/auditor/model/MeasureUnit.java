package cu.lacumbre.auditor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa una unidad de medida en el sistema
 */
@jakarta.persistence.Entity
@Table(name = "measure_units")
public class MeasureUnit extends BaseEntity {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotBlank(message = "La abreviación es obligatoria")
    @Size(max = 10, message = "La abreviación no puede exceder 10 caracteres")
    @Column(name = "abbreviation", nullable = false, length = 10)
    private String abbreviation;

    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    @Column(name = "description", length = 200)
    private String description;

    // Constructors
    public MeasureUnit() {}

    public MeasureUnit(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MeasureUnit{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                '}';
    }
}