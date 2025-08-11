package cu.lacumbre.auditor.utils.optimization;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Component;

public class OptimizedTableCellRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            
        // Solo renderizar si la celda es visible
        if (!table.getVisibleRect().intersects(table.getCellRect(row, column, false))) {
            return null;
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
