package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.utils.Logger;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public abstract class CustomAbstractTableModelEditable extends CustomAbstractTableModel implements TableModelListener {

    private final ArrayList<Integer> editableRowIndexes;
    private CustomTableModelListener cellListener;

    public CustomAbstractTableModelEditable(int rowCount, int columnCount, ArrayList<Integer> editableRowIndexes) {
        super(rowCount);
        this.editableRowIndexes = editableRowIndexes;
    }

    public CustomAbstractTableModelEditable(int rowCount, int columnCount, ArrayList<String> columnNames, ArrayList<Integer> editableRowIndexes) {
        super(rowCount, columnNames);
        this.editableRowIndexes = editableRowIndexes;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editableRowIndexes.contains(columnIndex);
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

    public void setCellListener(CustomTableModelListener cellListener) {
        this.cellListener = cellListener;
    }

}
