package cu.lacumbre.auditor.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.postgresql.util.PGobject;

public class PaymentRecord extends IDSuperClass {

    private final Worker worker;
    private final TreeMap<Integer, Boolean> records;
    private final double workedDays;
    private final CustomDocument document;
    private double workedDaysAdjusted, workedHours, toPay;
    private boolean adjusted = false;

    public PaymentRecord(int id, Worker worker, PGobject pGobject, CustomDocument document) {
        super(id);
        this.worker = worker;
        this.document = document;
        this.records = setMap(pGobject);
        this.workedDays = countDays(records);
        this.workedDaysAdjusted = workedDays;
        this.workedHours = workedDaysAdjusted * worker.getRole().getHoursToWork();
        this.toPay = workedHours * worker.getRole().getPaymentProportion();
    }

    public PaymentRecord(int id, Worker worker, double workedDays, double workedDaysAdjusted, PGobject pGobject, CustomDocument document) {
        super(id);
        this.worker = worker;
        this.document = document;
        this.records = setMap(pGobject);
        this.workedDays = workedDays;
        this.workedDaysAdjusted = workedDaysAdjusted;
        this.workedHours = workedDaysAdjusted * worker.getRole().getHoursToWork();
        this.toPay = workedHours * worker.getRole().getPaymentProportion();
    }

    public PaymentRecord(Worker worker, CustomDocument document) {
        super(-1);
        this.worker = worker;
        this.document = document;
        this.records = createEmptyMap();
        this.workedDays = countDays(records);
        this.workedDaysAdjusted = workedDays;
        this.workedHours = workedDaysAdjusted * worker.getRole().getHoursToWork();
        this.toPay = workedHours * worker.getRole().getPaymentProportion();
    }

    public PaymentRecord(int id, Worker worker, TreeMap<Integer, Boolean> records, CustomDocument document) {
        super(id);
        this.worker = worker;
        this.document = document;
        this.records = records;
        this.workedDays = countDays(records);
        this.workedDaysAdjusted = workedDays;
        this.workedHours = workedDaysAdjusted * worker.getRole().getHoursToWork();
        this.toPay = workedHours * worker.getRole().getPaymentProportion();
    }
    public PaymentRecord(Worker worker, TreeMap<Integer, Boolean> records, CustomDocument document) {
        super(-1);
        this.worker = worker;
        this.document = document;
        this.records = records;
        this.workedDays = countDays(records);
        this.workedDaysAdjusted = workedDays;
        this.workedHours = workedDaysAdjusted * worker.getRole().getHoursToWork();
        this.toPay = workedHours * worker.getRole().getPaymentProportion();
    }
    
    public PaymentRecord(PaymentRecord paymentRecord) {
        super(paymentRecord.getId());
        this.worker = paymentRecord.getWorker();
        this.document = paymentRecord.getDocument();
        this.records = paymentRecord.getRecords();
        this.workedDays = countDays(records);
        this.workedDaysAdjusted = workedDays;
        this.workedHours = workedDaysAdjusted * worker.getRole().getHoursToWork();
        this.toPay = workedHours * worker.getRole().getPaymentProportion();
    }

    public boolean isAdjusted() {
        return workedDays != workedDaysAdjusted;
    }

    public Worker getWorker() {
        return worker;
    }

    public TreeMap<Integer, Boolean> getRecords() {
        return records;
    }

    public PGobject getJSONB() throws SQLException {
        String json = new Gson().toJson(records);
        PGobject jsonObject = new PGobject();
        jsonObject.setType("jsonb");
        jsonObject.setValue(json);
        return jsonObject;
    }

    private TreeMap<Integer, Boolean> setMap(PGobject pGobject) {
        String json = pGobject.getValue();
        Type type = new TypeToken<TreeMap<Integer, Boolean>>() {
        }.getType();
        TreeMap<Integer, Boolean> records = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        records.putAll(new Gson().fromJson(json, type));
        return records;
    }

//    private TreeMap<Integer, Boolean> setMap(TreeMap<LocalDate, Boolean> records) {
//        TreeMap<Integer, Boolean> treeMap = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
//        for (Map.Entry<LocalDate, Boolean> entry : records.entrySet()) {
//            LocalDate key = entry.getKey();
//            Boolean value = entry.getValue();
//            treeMap.put(key.getDayOfMonth(), value);
//        }
//        return treeMap;
//    }
    public double getWorkedDays() {
        return workedDays;
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public double getToPay() {
        return toPay;
    }

    public double getWorkedDaysAdjusted() {
        return workedDaysAdjusted;
    }

    public CustomDocument getDocument() {
        return document;
    }

    public void setRecords(TreeMap<Integer, Boolean> records) {
        
        this.workedDaysAdjusted = workedDaysAdjusted;
        this.workedHours = workedDaysAdjusted * worker.getRole().getHoursToWork();
        this.toPay = workedHours * worker.getRole().getPaymentProportion();
        adjusted = true;
    }
    public void setWorkedDaysAdjusted(double workedDaysAdjusted) {
        this.workedDaysAdjusted = workedDaysAdjusted;
        this.workedHours = workedDaysAdjusted * worker.getRole().getHoursToWork();
        this.toPay = workedHours * worker.getRole().getPaymentProportion();
        adjusted = true;
    }

    public boolean wasAdjusted() {
        return adjusted;
    }

    private double countDays(TreeMap<Integer, Boolean> map) {
        double cant = 0;
        for (Map.Entry<Integer, Boolean> entry : map.entrySet()) {
            Boolean value = entry.getValue();
            if (value) {
                cant++;
            }
        }
        return cant;
    }

    private TreeMap<Integer, Boolean> createEmptyMap() {
        String[] split = document.getPeriod().toString().split("/");
        Year year = Year.parse("20" + split[1]);
        Month month = Month.of(Integer.parseInt(split[0]));
        LocalDate monthStartDate = LocalDate.of(year.getValue(), month.getValue(), 1);
        LocalDate monthFinalDate = LocalDate.of(year.getValue(), month.getValue(), month.length(year.isLeap()));
        ArrayList<LocalDate> days = new ArrayList<>();
        for (LocalDate currentDate = monthStartDate; !currentDate.isAfter(monthFinalDate); currentDate = currentDate.plusDays(1)) {
            days.add(currentDate);
        }
        TreeMap<Integer, Boolean> attendance = new TreeMap<>(Comparator.comparingLong(Integer::intValue));
        if (!worker.getEnrollDate().isAfter(days.get(days.size() - 1))) {
            for (LocalDate day : days) {
                attendance.put(day.getDayOfMonth(), false);
            }
        }
        return attendance;
    }

    @Override
    public String toString() {
        return "REGISTRO DE ASISTENCIA: " + worker.getFullName() + " -> [WorkedDays: " + workedDays + ", Worked Days Adjusted: " + workedDaysAdjusted + "]";
    }

}
