package cu.lacumbre.auditor.model;

import java.util.ArrayList;

public class RawMaterial extends Expense {

    private double lastCost, weightedCost, highestCost;

    public RawMaterial(int id, String description, MeasureUnit measureUnit, double basicCost, double lastCost, double weightedCost, double highestCost, int code, boolean archivated) {
        super(id, description, measureUnit, basicCost, code, archivated);
        this.lastCost = lastCost;
        this.weightedCost = weightedCost;
        this.highestCost = highestCost;
    }

    public double promediarCosto(ArrayList<Operation> list) {
        double value = 0;
        int counter = 0;
        for (Operation operation : list) {
            value += operation.getValue() / operation.getAmmount();
            counter++;
        }
        value += cost;
        counter++;
        return value / counter;
    }

    public double getBasicCost() {
        return cost;
    }

    public double getLastCost() {
        return lastCost;
    }

    public void setLastCost(double lastCost) {
        this.lastCost = lastCost;
    }
    
    public double getHighestCost() {
        return highestCost;
    }

    public void setHighestCost(double highestCost) {
        this.highestCost = highestCost;
    }

    public double getWeightedCost() {
        return weightedCost;
    }

    public void setWeightedCost(double weightedCost) {
        this.weightedCost = weightedCost;
    }

    public RawMaterial createCopy(int ID) {
        return new RawMaterial(ID, description, measureUnit, cost, lastCost, weightedCost, highestCost, ID, false);
    }

    public double getDefaultCost() {
        switch (COST_TO_USE) {
            case BASIC_COST -> {
                return cost;
            }
            case WEIGHTED_COST -> {
                return weightedCost;
            }
            case LAST_COST -> {
                return lastCost;
            }
            case HIGHEST_COST -> {
                return highestCost;
            }
            default -> {
                return 0.0d;
            }
        }
    }

    public String printDefaultCost() {
        switch (COST_TO_USE) {
            case BASIC_COST -> {
                return "Costo Básico";
            }
            case WEIGHTED_COST -> {
                return "Costo Ponderado";
            }
            case LAST_COST -> {
                return "Último Costo";
            }
            case HIGHEST_COST -> {
                return "Mayor Costo";
            }
            default -> {
                return null;
            }
        }
    }
}
