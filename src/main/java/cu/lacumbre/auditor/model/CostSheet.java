package cu.lacumbre.auditor.model;

public class CostSheet extends IDSuperClass {

    private String description;
    private double higestProductionCost, highestCalculatedPrice, highestSalesTax, highestLaborTax;
    private double productionCost, calculatedPrice, salesTax, laborTax;
    private double inputCosts, financialCosts, energyCosts, rentalCosts, laborCosts, profitMargin;
    private double ingredientsCost, highestIngredientsCost = 0.0d;

    public CostSheet(String description, double inputCosts, double financialCosts, double energyCosts, double rentalCosts, double laborCosts, double profitMargin) {
        super();
        this.description = description;
        this.inputCosts = inputCosts;
        this.financialCosts = financialCosts;
        this.energyCosts = energyCosts;
        this.rentalCosts = rentalCosts;
        this.laborCosts = laborCosts;
        this.profitMargin = profitMargin;
    }

    public CostSheet(int id, String description, double inputCosts, double financialCosts, double energyCosts, double rentalCosts, double laborCosts, double profitMargin) {
        super(id);
        this.description = description;
        this.inputCosts = inputCosts;
        this.financialCosts = financialCosts;
        this.energyCosts = energyCosts;
        this.rentalCosts = rentalCosts;
        this.laborCosts = laborCosts;
        this.profitMargin = profitMargin;
    }

    public void setIngredientsCost(double ingredientsCost) {
        this.ingredientsCost = ingredientsCost;
        if (this.ingredientsCost > 0) {
            calculateValues();
        }
    }

    public void setHighestIngredientsCost(double highestIngredientsCost) {
        this.highestIngredientsCost = highestIngredientsCost;
        if (this.highestIngredientsCost > 0) {
            calculateHighestValues();
        }
    }

    public static CostSheet generate() {
        return new CostSheet("Default", 0, 0, 0, 0, 0, 0);
    }

    public double getInputCosts() {
        return inputCosts;
    }

    public double getFinancialCosts() {
        return financialCosts;
    }

    public double getEnergyCosts() {
        return energyCosts;
    }

    public double getRentalCosts() {
        return rentalCosts;
    }

    public double getLaborCosts() {
        return laborCosts;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public double getHigestProductionCost() {
        return higestProductionCost;
    }

    public double getCalculatedPrice() {
        return calculatedPrice;
    }

    public double getHighestCalculatedPrice() {
        return highestCalculatedPrice;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public double getHighestSalesTax() {
        return highestSalesTax;
    }

    public double getLaborTax() {
        return laborTax;
    }

    public double getHighestLaborTax() {
        return highestLaborTax;
    }

    public String getDescription() {
        return description;
    }

    public void setInputCosts(double inputCosts) {
        this.inputCosts = inputCosts;
    }

    public void setFinancialCosts(double financialCosts) {
        this.financialCosts = financialCosts;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnergyCosts(double energyCosts) {
        this.energyCosts = energyCosts;
    }

    public void setLaborCosts(double laborCosts) {
        this.laborCosts = laborCosts;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public void setRentalCosts(double rentalCosts) {
        this.rentalCosts = rentalCosts;
    }

    @Override
    public String toString() {
        return description;
    }

    private void calculateValues() {
        double basic_additions = inputCosts + financialCosts + energyCosts + rentalCosts + laborCosts;
        productionCost = ingredientsCost + basic_additions;
        double salePrice = productionCost * (1 + profitMargin);
        salesTax = salePrice * 0.1;
        laborTax = laborCosts * 0.05;
        calculatedPrice = salePrice + salesTax + laborTax;
    }

    private void calculateHighestValues() {
        double basic_additions = inputCosts + financialCosts + energyCosts + rentalCosts + laborCosts;
        higestProductionCost = highestIngredientsCost + basic_additions;
        double salePrice = higestProductionCost * (1 + profitMargin);
        highestSalesTax = salePrice * 0.1;
        highestLaborTax = laborCosts * 0.05;
        highestCalculatedPrice = salePrice + highestSalesTax + highestLaborTax;
    }

    public CostSheet copy(String itemDescription) {
        return new CostSheet(id, itemDescription + " [" + description + "]", inputCosts, financialCosts, energyCosts, rentalCosts, laborCosts, profitMargin);
    }

}
