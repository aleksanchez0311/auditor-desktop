package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.PaymentRecord;
import cu.lacumbre.auditor.model.Worker;
import cu.lacumbre.auditor.exceptions.EmptyWorkersException;
import cu.lacumbre.auditor.model.CustomDocument;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomTM_AttendanceFill extends CustomAbstractTableModel {

    private final ArrayList<LocalDate> dates;
    private final CustomDocument document;

    public CustomTM_AttendanceFill(TreeMap<Worker, PaymentRecord> records, ArrayList<LocalDate> dates, ArrayList<String> days, CustomDocument document) throws EmptyWorkersException {
        super(records.size(), days);
        this.dates = dates;
        this.document = document;
        if (records.isEmpty()) {
            throw new EmptyWorkersException("No existen trabajadores registrados antes de la fecha actual.");
        }
        for (Map.Entry<Worker, PaymentRecord> entry : records.entrySet()) {
            Worker worker = (Worker) entry.getKey();
            PaymentRecord paymentRecord = entry.getValue();
            ArrayList<Object> sublist = new ArrayList<>();
            sublist.add(worker);
            sublist.add(paymentRecord);
            this.dataArray.add(sublist);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            Worker worker = (Worker) dataArray.get(rowIndex).get(0);
            return worker;
        } else {
            PaymentRecord paymentRecord = (PaymentRecord) dataArray.get(rowIndex).get(1);
            TreeMap<Integer, Boolean> records = paymentRecord.getRecords();
            return records.get(columnIndex);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PaymentRecord currentPaymentRecord = (PaymentRecord) dataArray.get(rowIndex).get(1);
        TreeMap<Integer, Boolean> records = currentPaymentRecord.getRecords();
        records.put(columnIndex, (Boolean) aValue);
        PaymentRecord newPaymentRecord = new PaymentRecord(currentPaymentRecord.getId(), currentPaymentRecord.getWorker(), records, currentPaymentRecord.getDocument());
        dataArray.get(rowIndex).set(1, newPaymentRecord);
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Worker.class : Boolean.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        } else {
            Worker worker = (Worker) dataArray.get(rowIndex).get(0);
            LocalDate enrollDate = worker.getEnrollDate();
            LocalDate columnDate = dates.get(columnIndex - 1);
            return !enrollDate.isAfter(columnDate);
        }
    }

    public TreeMap<Worker, PaymentRecord> getParsedDataMap() {
        TreeMap<Worker, PaymentRecord> parsedDataMap = new TreeMap<>(Comparator.comparingInt(Worker::getId));
        for (ArrayList<Object> values : dataArray) {
            parsedDataMap.put((Worker) values.get(0), (PaymentRecord) values.get(1));
        }
        return parsedDataMap;
    }

}
