package cu.lacumbre.auditor.model;

import java.util.ArrayList;

public class ProductListo extends Product {

    public ProductListo(String description, MeasureUnit measureUnit, TPVCategory tpvCategory, CostSheet costSheet, double price, int code, boolean archivated) {
        super(description, measureUnit, tpvCategory, costSheet, price, code, archivated);
    }

    public ProductListo(int id, String description, MeasureUnit measureUnit, TPVCategory tpvCategory, CostSheet costSheet, double price, int code, boolean archivated) {
        super(id, description, measureUnit, tpvCategory, costSheet, price, code, archivated);
    }

    public ProductListo(int id, String description, MeasureUnit measureUnit, TPVCategory tpvCategory, Recipe recipe, CostSheet costSheet, double price, int code, boolean archivated) {
        super(id, description, measureUnit, tpvCategory, recipe, costSheet, price, code, archivated);
    }

    public RawMaterialLista getLinkedRawMaterial(ArrayList<? extends RawMaterial> list) {
        for (RawMaterial rawMaterial : list) {
            if (rawMaterial.getDescription().equals(description)) {
                return (RawMaterialLista) rawMaterial;
            }
        }
        return null;
    }
}
