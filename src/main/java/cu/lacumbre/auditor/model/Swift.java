package cu.lacumbre.auditor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Swift extends IDSuperClass{

    private String description;
    private Worker boss;
    private ArrayList<Worker> members;
    private final HashMap<String, ArrayList<Operation>> operations;
    private final LocalDate localDate;

    public Swift(int id, String description, Worker boss, ArrayList<Worker> members, HashMap<String, ArrayList<Operation>> operations, LocalDate localDate) {
        super(id);
        this.description = description;
        this.boss = boss;
        this.members = members;
        this.operations = operations;
        this.localDate = localDate;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Worker getBoss() {
        return boss;
    }

    public ArrayList<Worker> getMembers() {
        return members;
    }

    public HashMap<String, ArrayList<Operation>> getOperations() {
        return operations;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBoss(Worker boss) {
        this.boss = boss;
    }

    public void setMembers(ArrayList<Worker> members) {
        this.members = members;
    }

}
