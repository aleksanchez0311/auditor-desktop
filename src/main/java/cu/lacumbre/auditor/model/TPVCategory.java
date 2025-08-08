package cu.lacumbre.auditor.model;

import com.lowagie.text.pdf.RGBColor;
import java.awt.Color;

public class TPVCategory extends IDSuperClass {

    private String description;
    private RGBColor color;

    public TPVCategory(String descripcion, RGBColor color) {
        super();
        this.description = descripcion;
        this.color = color;
    }

    public TPVCategory(int id, String descripcion, RGBColor color) {
        super(id);
        this.description = descripcion;
        this.color = color;
    }

    public static TPVCategory generate() {
        return new TPVCategory("Autegenerated", new RGBColor(0, 0, 0, 10));
    }

    public String getDescription() {
        return description;
    }

    public RGBColor getColor() {
        return color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(RGBColor color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return description;
    }

}
