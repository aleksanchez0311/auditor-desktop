package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.crud.PaymentRecordsCRUD;
import cu.lacumbre.auditor.model.PaymentRecord;
import cu.lacumbre.auditor.model.Worker;
import cu.lacumbre.auditor.exceptions.EmptyWorkersException;
import cu.lacumbre.utils.Logger;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.table.AbstractTableModel;

public class CustomTM_AttendanceAdjust extends AbstractTableModel {

    private final TreeMap<Worker, Double[]> dataMap;
    private final PaymentRecordsCRUD paymentRecordsCRUD;

    public CustomTM_AttendanceAdjust(TreeMap<Worker, Double[]> dataMap, PaymentRecordsCRUD paymentRecordsCRUD) throws EmptyWorkersException {
        this.dataMap = dataMap;
        this.paymentRecordsCRUD = paymentRecordsCRUD;
        if (dataMap.isEmpty()) {
            throw new EmptyWorkersException("No existen trabajadores registrados antes de la fecha actual.");
        }
    }

    @Override
    public int getRowCount() {
        return dataMap.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return "Trabajador";
            }
            case 1 -> {
                return "Días Trabajados";
            }
            case 2 -> {
                return "Días Ajustados";
            }
            default ->
                throw new AssertionError();
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Worker worker = new ArrayList<>(dataMap.keySet()).get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return worker;
            }
            case 1 -> {
                return dataMap.get(worker)[0];
            }
            case 2 -> {
                return dataMap.get(worker)[1];
            }
            default ->
                throw new AssertionError();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            Worker worker = new ArrayList<>(dataMap.keySet()).get(rowIndex);
            if (columnIndex == 2) {
                Double[] doubles = new Double[]{dataMap.get(worker)[0], (double) aValue};
                dataMap.put(worker, doubles);
                ArrayList<PaymentRecord> adjustedPaymentRecords = new ArrayList<>();
                for (PaymentRecord paymentRecord : paymentRecordsCRUD.getList()) {
                    paymentRecord.setWorkedDaysAdjusted(dataMap.get(paymentRecord.getWorker())[1]);
                    if (paymentRecord.wasAdjusted()) {
                        adjustedPaymentRecords.add(paymentRecord);
                    }
                }
                paymentRecordsCRUD.update(adjustedPaymentRecords);
                fireTableDataChanged();
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Worker.class : Double.class;
    }

    public TreeMap<Worker, Double[]> getDataMap() {
        return dataMap;
    }
}
