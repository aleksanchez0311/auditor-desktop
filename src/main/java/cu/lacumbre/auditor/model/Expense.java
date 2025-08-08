package cu.lacumbre.auditor.model;

public class Expense extends Item implements Buyable{

    protected double cost;

    public Expense(int id, String description, MeasureUnit measureUnit, double basicCost, int code, boolean archivated) {
        super(id, description, measureUnit, code, archivated);
        this.cost = basicCost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

//    @Override
//    public boolean isExpense() {
//        return true;
//    }

}
