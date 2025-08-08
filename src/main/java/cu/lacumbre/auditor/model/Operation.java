package cu.lacumbre.auditor.model;

import java.time.Instant;

public class Operation extends IDSuperClass {

    private final Item item;
    private final boolean income, opening;
    private Instant instant;
    private boolean billed;
    private double value, ammount, sigleCost;

    public Operation(Item item, Instant instant, double ammount, double value, boolean income, boolean billed, boolean opening) {
        super();
        this.item = item;
        this.income = income;
        this.billed = billed;
        this.ammount = ammount;
        this.value = value;
        this.instant = instant;
        this.opening = opening;
        this.sigleCost = value / ammount;
    }

    public Operation(int id, Item item, Instant instant, double ammount, double value, boolean income, boolean billed, boolean opening) {
        super(id);
        this.item = item;
        this.income = income;
        this.billed = billed;
        this.ammount = ammount;
        this.value = value;
        this.instant = instant;
        this.opening = opening;
        this.sigleCost = value / ammount;
    }

    public int getId() {
        return id;
    }

    public boolean isIncome() {
        return income;
    }

    public Item getItem() {
        return item;
    }

    public double getAmmount() {
        return ammount;
    }

    public double getValue() {
        return value;
    }

    public Instant getInstant() {
        return instant;
    }

    public boolean isOpening() {
        return opening;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
        this.sigleCost = value / ammount;
    }

    public void setValue(double value) {
        this.value = value;
        this.sigleCost = value / ammount;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public boolean isBilled() {
        return billed;
    }

    public void setBilled(boolean billed) {
        this.billed = billed;
    }

    public double getSigleCost() {
        return sigleCost;
    }

    public Operation createCopy(int ID) {
        return new Operation(ID, item, instant, ammount, value, income, billed, opening);
    }

    @Override
    public String toString() {
        String result = income ? "Entrada" : "Salida";
        result += " de " + ammount + " " + item.getMeasureUnit().getAbrev() + " de " + item.getDescription() + " - (" + id + ")";
        return result;
    }

}
