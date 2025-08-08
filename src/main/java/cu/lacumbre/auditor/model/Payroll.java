package cu.lacumbre.auditor.model;

public class Payroll extends CustomDocument {

    private Payroll(String category, String period, boolean completed) {
        super(category, period, completed);
    }

    public Payroll(int id, String category, String period, boolean completed) {
        super(id, category, period, completed);
    }

    public Payroll(String category, boolean completed) {
        super(category, completed);
    }

    private Payroll(int id, String category, boolean completed) {
        super(id, category, completed);
    }

}
