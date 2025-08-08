package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CustomTM_IncomesReport extends CustomAbstractTableModelTotalizableEditable {

    private final OperationsCRUD operationsCRUD;
    private static final int KEY_COLUMN = 2;
    private static final int VALUE_COLUMN_1 = 4;
    private static final int VALUE_COLUMN_2 = 5;
    private static final int VALUE_COLUMN_3 = 7;
    private static final int EDITABLE_COLUMN_1 = 6;
    private static final int[] VALUE_COLUMNS = new int[]{VALUE_COLUMN_1, VALUE_COLUMN_2, VALUE_COLUMN_3};
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList(new String[]{"No.", "Fecha", "Producto [U/M]", "Cantidad Comprada", "Precio de Compra", "Costo de Compra($)", "Con Factura?", ""}));
    private static final ArrayList<Integer> EDITABLE_COLUMNS = new ArrayList<>(Arrays.asList(new Integer[]{EDITABLE_COLUMN_1}));

    public CustomTM_IncomesReport(OperationsCRUD operationsCRUD, ArrayList<Operation> incomes) {
        super(incomes.size(), COLUMN_NAMES.size()-1, COLUMN_NAMES, KEY_COLUMN, VALUE_COLUMNS, EDITABLE_COLUMNS);
        this.operationsCRUD = operationsCRUD;
        int no = 1;
        for (Operation operation : incomes) {
            ArrayList<Object> sublist = new ArrayList<>();
            sublist.add(no);
            sublist.add(new Timing(operation.getInstant()).getDate());
            sublist.add(operation.getItem() + " [" + operation.getItem().getMeasureUnit().getAbrev() + "]");
            sublist.add(operation.getAmmount());
            sublist.add(operation.getSigleCost());
            sublist.add(operation.getValue());
            sublist.add(operation.isBilled());
            sublist.add(operation.isBilled() ? operation.getValue() : 0.0d);
            sublist.add(operation);
            this.dataArray.add(sublist);
            no++;
        }
        setTotals();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < rowCount) {
            return switch (columnIndex) {
                case 0, 1, 2, 6 ->
                    dataArray.get(rowIndex).get(columnIndex);
                case 3, 4, 5 ->
                    decimalFormat.format(dataArray.get(rowIndex).get(columnIndex));
                default ->
                    null;
            };
        } else {
            return switch (columnIndex) {
                case KEY_COLUMN ->
                    dataArray.get(rowCount).get(totalColumnIndexKey);
                case VALUE_COLUMN_1, VALUE_COLUMN_2 ->
                    decimalFormat.format(dataArray.get(rowIndex).get(columnIndex));
                default ->
                    null;
            };
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case EDITABLE_COLUMN_1 -> {
                try {
                    boolean newBilled = (boolean) aValue;
                    dataArray.get(rowIndex).set(EDITABLE_COLUMN_1, newBilled);
                    Operation currentOperation = (Operation) dataArray.get(rowIndex).get(8);
                    currentOperation.setBilled(newBilled);
                    dataArray.get(rowIndex).set(8, currentOperation);
                    operationsCRUD.update(currentOperation);
                    break;
                } catch (SQLException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                    break;
                }
            }
            default -> {
                break;
            }
        }
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                Integer.class;
            case 1 ->
                Date.class;
            case 6 ->
                Boolean.class;
            default ->
                String.class;
        };
    }

}
