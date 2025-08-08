package cu.lacumbre.auditor.model;

public class Attendance extends CustomDocument {

    private Attendance(String description, String startDateString, boolean completed) {
        super(description, startDateString, completed);
    }

    public Attendance(int id, String description, String startDateString, boolean completed) {
        super(id, description, startDateString, completed);
    }

    public Attendance(String description, boolean completed) {
        super(description, completed);
    }

    private Attendance(int id, String description, boolean completed) {
        super(id, description, completed);
    }

}
