package cu.lacumbre.auditor.model;

public class MeasureUnit extends IDSuperClass {

    private String description;
    private String abrev;

    public MeasureUnit(String descripcion, String abrev) {
        super();
        this.description = descripcion;
        this.abrev = abrev;
    }

    public MeasureUnit(int id, String descripcion, String abrev) {
        super(id);
        this.description = descripcion;
        this.abrev = abrev;
    }

    public static MeasureUnit generate() {
        return new MeasureUnit("Default", "Default");
    }

    public String getDescription() {
        return description;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    @Override
    public String toString() {
        return abrev;
    }

}
