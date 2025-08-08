package cu.lacumbre.auditor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Entity extends IDSuperClass {

    private String description, abrev;
    private final boolean workable, magic;
    private LocalDate firstDay, currentDay;
    private boolean filled, closed, active;
    private double mAmmount;

    public Entity(String descripcion, boolean isWorkable, LocalDate firstDay, LocalDate currentDay, boolean isFilled, boolean isClosed, boolean isMagic, double mAmmount, boolean isActive) {
        super();
        this.description = descripcion;
        this.workable = isWorkable;
        this.firstDay = firstDay;
        this.currentDay = currentDay;
        this.filled = isFilled;
        this.closed = isClosed;
        this.magic = isMagic;
        this.mAmmount = mAmmount;
        this.active = isActive;
    }

    public Entity(int id, String descripcion, boolean isWorkable, LocalDate firstDay, LocalDate currentDay, boolean isFilled, boolean isClosed, boolean isMagic, double mAmmount, boolean isActive) {
        super(id);
        this.description = descripcion;
        this.abrev = setAbrev(description);
        this.workable = isWorkable;
        this.firstDay = firstDay;
        this.currentDay = currentDay;
        this.filled = isFilled;
        this.closed = isClosed;
        this.magic = isMagic;
        this.mAmmount = mAmmount;
        this.active = isActive;
    }

    public static Entity generate() {
        return new Entity("Nivel Empresarial", false, LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-01"), false, false, false, 100, true);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.abrev = setAbrev(description);
    }

    public String getAbrev() {
        return abrev;
    }
    
    private static String setAbrev(String description){
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> descartar = new ArrayList<>(Arrays.asList(new String[]{"El", "La", "Los", "Las", "En", "De", "Bajo", "Por", "Sin"}));
        String[] split = description.split(" ");
        for (String string : split) {
//            if(!descartar.contains(string)){
                list.add(string);
//            }
        }
        String result = "";
        for (String string : list) {
            String substring = string.substring(0, 1);
            result += substring.toUpperCase();
        }
        return result;
    }

    public boolean isWorkable() {
        return workable;
    }

    public LocalDate getFirstDay() {
        return firstDay;
    }

    public LocalDate getCurrentDay() {
        return currentDay;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setCurrentDay(LocalDate currentDay) {
        this.currentDay = currentDay;
    }

    public void setFirstDay(LocalDate firstDay) {
        this.firstDay = firstDay;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isMagic() {
        return magic;
    }

    public double getMAmmount() {
        return mAmmount;
    }

    public void setmAmmount(double mAmmount) {
        this.mAmmount = mAmmount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return description;
    }

}
