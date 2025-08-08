package cu.lacumbre.auditor.model;

public class Role extends IDSuperClass {

    private String description;
    private final double daysToWork, hoursToWork, hoursPerDay, paymentProportion;
    private double payment;

    public Role(String description, double payment, double daysToWork, double hoursToWork) {
        super();
        this.description = description;
        this.payment = payment;
        this.daysToWork = daysToWork;
        this.hoursToWork = hoursToWork;
        this.hoursPerDay = daysToWork * hoursToWork;
        this.paymentProportion = payment / hoursPerDay;
    }

    public Role(int id, String description, double payment, double daysToWork, double hoursToWork) {
        super(id);
        this.description = description;
        this.payment = payment;
        this.daysToWork = daysToWork;
        this.hoursToWork = hoursToWork;
        this.hoursPerDay = daysToWork * hoursToWork;
        this.paymentProportion = payment / hoursPerDay;
    }

    public String getDescription() {
        return description;
    }

    public double getPayment() {
        return payment;
    }

    public double getDaysToWork() {
        return daysToWork;
    }

    public double getHoursToWork() {
        return hoursToWork;
    }

    public double getHoursPerDay() {
        return hoursPerDay;
    }

    public double getPaymentProportion() {
        return paymentProportion;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return description;
    }

}
