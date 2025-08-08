package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.utils.Logger;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public abstract class CustomAbstractTableModelTotalizableEditable extends CustomAbstractTableModelTotalizable implements TableModelListener {

    private final ArrayList<Integer> editableColumnIndexes;
    private CustomTableModelListener cellListener;

    public CustomAbstractTableModelTotalizableEditable(int rowCount, int totalColumnIndexKey, int[] totalColumnIndexValues, ArrayList<Integer> editableColumnIndexes) {
        super(rowCount, totalColumnIndexKey, totalColumnIndexValues);
        this.editableColumnIndexes = editableColumnIndexes;
    }

    public CustomAbstractTableModelTotalizableEditable(int rowCount, ArrayList<String> columnNames, int totalColumnIndexKey, int[] totalColumnIndexValues, ArrayList<Integer> editableColumnIndexes) {
        super(rowCount, columnNames, totalColumnIndexKey, totalColumnIndexValues);
        this.editableColumnIndexes = editableColumnIndexes;
    }

    public CustomAbstractTableModelTotalizableEditable(int rowCount, int columnCount, ArrayList<String> columnNames, int totalColumnIndexKey, int[] totalColumnIndexValues, ArrayList<Integer> editableColumnIndexes) {
        super(rowCount, columnCount, columnNames, totalColumnIndexKey, totalColumnIndexValues);
        this.editableColumnIndexes = editableColumnIndexes;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editableColumnIndexes.contains(columnIndex);
    }

    @Override
    public void tableChanged(TableModelEvent evt) {
        int row = evt.getFirstRow();
        int column = evt.getColumn();
        Object newValue = getValueAt(row, column);
        setValueAt(newValue, row, column);
        try {
            cellListener.onTableCellChange(newValue);
        } catch (Exception ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

}
