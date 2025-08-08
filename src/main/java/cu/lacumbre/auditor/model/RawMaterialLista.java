package cu.lacumbre.auditor.model;

import java.util.ArrayList;

public class RawMaterialLista extends RawMaterial {

    public RawMaterialLista(int id, String description, MeasureUnit measureUnit, double basicCost, double lastCost, double weightedCost, double highestCost, int code, boolean archivated) {
        super(id, description, measureUnit, basicCost, lastCost, weightedCost, highestCost, code, archivated);
    }

    public ProductListo getLinkedProduct(ArrayList<? extends Product> list) {
        for (Product product : list) {
            if (product.getDescription().equals(description)) {
                return (ProductListo) product;
            }
        }
        return null;
    }
}
