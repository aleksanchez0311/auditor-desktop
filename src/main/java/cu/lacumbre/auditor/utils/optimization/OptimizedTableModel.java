package cu.lacumbre.auditor.utils.optimization;

import javax.swing.table.AbstractTableModel;

public class OptimizedTableModel extends AbstractTableModel {
    private Object[][] data;
    private final String[] columnNames;
    
    public OptimizedTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        this.data = new Object[0][0];
    }
    
    public void updateData(Object[][] newData) {
        this.data = newData;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return data.length;
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
