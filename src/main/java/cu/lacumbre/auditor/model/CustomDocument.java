package cu.lacumbre.auditor.model;

import cu.lacumbre.utils.MonthlyPeriod;
import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.exceptions.DocumentAlreadyCompletedException;

public class CustomDocument extends IDSuperClass {

    public static final String PAYROLL = "NOMINA";
    public static final String ATTENDANCE_REPORT = "REPORTE DE PAGO";

    private final String description;
    private MonthlyPeriod period;
    private boolean completed;

    public CustomDocument(String description, boolean completed) {
        this(description, new MonthlyPeriod(EntitySelector.currentEntity.getFirstDay()).toString(), completed);
    }

    public CustomDocument(int id, String description, boolean completed) {
        this(id, description, new MonthlyPeriod(EntitySelector.currentEntity.getFirstDay()).toString(), completed);
    }

    public CustomDocument(String description, String startDateString, boolean completed) {
        super();
        this.description = description;
        this.completed = completed;
        this.period = new MonthlyPeriod(startDateString);
    }

    public CustomDocument(int id, String description, String startDateString, boolean completed) {
        super(id);
        this.description = description;
        this.completed = completed;
        this.period = new MonthlyPeriod(startDateString);
    }

    public String getDescription() {
        return description;
    }

    public MonthlyPeriod getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = new MonthlyPeriod(period);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() throws DocumentAlreadyCompletedException {
        if (!completed) {
            completed = true;
        } else {
            throw new DocumentAlreadyCompletedException("Este documento ya fue completado.");
        }
    }

    @Override
    public String toString() {
        return description + ": [Periodo " + period + "]";
    }

}
