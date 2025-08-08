package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.crud.DocumentsCRUD;
import cu.lacumbre.auditor.crud.PaymentRecordsCRUD;
import cu.lacumbre.auditor.crud.WorkersCRUD;
import cu.lacumbre.auditor.model.CustomDocument;
import cu.lacumbre.auditor.model.PaymentRecord;
import cu.lacumbre.auditor.exceptions.DocumentAlreadyCompletedException;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class CustomTM_Documents extends CustomAbstractTableModelEditable {

    private final DocumentsCRUD documentsCRUD;
    private static final ArrayList<Integer> EDITABLE_ROWS = new ArrayList<>(Arrays.asList(new Integer[]{2}));
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList(new String[]{"Descripción", "Total", "Completado"}));

    public CustomTM_Documents(Connection connection, DocumentsCRUD documentsCRUD, HashMap<CustomDocument, ArrayList<PaymentRecord>> periodRecords) {
        super(periodRecords.size(), 3, COLUMN_NAMES, EDITABLE_ROWS);
        this.documentsCRUD = documentsCRUD;
        for (Map.Entry<CustomDocument, ArrayList<PaymentRecord>> entry : periodRecords.entrySet()) {
            CustomDocument customDocument = entry.getKey();
            ArrayList<PaymentRecord> records = entry.getValue();
            ArrayList<Object> sublist = new ArrayList<>();
            sublist.add(customDocument);
            sublist.add(decimalFormat.format(records.stream().mapToDouble((PaymentRecord record) -> record.getToPay()).sum()));
            sublist.add(customDocument.isCompleted());
            this.dataArray.add(sublist);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return dataArray.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            try {
                dataArray.get(rowIndex).add(columnIndex, aValue);
                CustomDocument customDocument = (CustomDocument) dataArray.get(rowIndex).get(0);
                customDocument.setCompleted();
                documentsCRUD.update(customDocument);
                fireTableDataChanged();
            } catch (DocumentAlreadyCompletedException | SQLException ex) {
                Logger.getInstance().updateErrorLog(ex);
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 2 ->
                Boolean.class;
            default ->
                String.class;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex) && !(boolean) dataArray.get(rowIndex).get(2);
    }

}
