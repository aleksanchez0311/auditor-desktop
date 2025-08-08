package cu.lacumbre.auditor.model;

public class ProductCocina extends Product {

    public ProductCocina(String description, MeasureUnit measureUnit, TPVCategory tpvCategory, CostSheet costSheet, double price, int code, boolean archivated) {
        super(description, measureUnit, tpvCategory, costSheet, price, code, archivated);
    }

    public ProductCocina(int id, String description, MeasureUnit measureUnit, TPVCategory tpvCategory, CostSheet costSheet, double price, int code, boolean archivated) {
        super(id, description, measureUnit, tpvCategory, costSheet, price, code, archivated);
    }

    public ProductCocina(int id, String description, MeasureUnit measureUnit, TPVCategory tpvCategory, Recipe recipe, CostSheet costSheet, double price, int code, boolean archivated){
        super(id, description, measureUnit, tpvCategory, recipe, costSheet, price, code, archivated);
    }

}
