package cu.lacumbre.auditor.model;

import org.postgresql.util.PGobject;

public class PayrollRecord extends PaymentRecord {

    private final double[] bonifications, penalizations, taxes, retentions;
    private final double devengated, payed;

    public PayrollRecord(int id, Worker worker, PGobject pGobject, CustomDocument document, double[] bonifications, double[] penalizations, double[] taxes, double[] retentions) {
        super(id, worker, pGobject, document);
        this.bonifications = bonifications;
        this.penalizations = penalizations;
        this.taxes = taxes;
        this.retentions = retentions;
        double bonification = calcBonifications();
        double penalization = calcPenalizations();
        this.devengated = getToPay() + bonification - penalization;
        double tax = calcTaxes();
        double retention = calcRetentions();
        this.payed = devengated - tax - retention;
    }

    public PayrollRecord(Worker worker, CustomDocument document, double[] bonifications, double[] penalizations, double[] taxes, double[] retentions) {
        super(worker, document);
        this.bonifications = bonifications;
        this.penalizations = penalizations;
        this.taxes = taxes;
        this.retentions = retentions;
        double bonification = calcBonifications();
        double penalization = calcPenalizations();
        this.devengated = getToPay() + bonification - penalization;
        double tax = calcTaxes();
        double retention = calcRetentions();
        this.payed = devengated - tax - retention;
    }

    private double calcBonifications() {
        return 0.0d;
    }

    private double calcPenalizations() {
        return 0.0d;
    }

    private double calcTaxes() {
        return 0.0d;
    }

    private double calcRetentions() {
        return 0.0d;
    }

}
