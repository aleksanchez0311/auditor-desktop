package cu.lacumbre.auditor.model;

public class RawMaterialCocina extends RawMaterial {


    public RawMaterialCocina(int id, String description, MeasureUnit measureUnit, double basicCost, double lastCost, double weightedCost, double highestCost, int code, boolean archivated) {
        super(id, description, measureUnit, basicCost, lastCost, weightedCost, highestCost, code, archivated);
    }

}
