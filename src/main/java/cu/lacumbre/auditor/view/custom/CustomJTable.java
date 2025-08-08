package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.utils.Logger;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class CustomJTable extends JTable {

    @Override
    public Font getFont() {
        return new Font("Segoe UI", 1, 14);
    }

    @Override
    public void setModel(TableModel dataModel) {
        super.setModel(dataModel);
        ColumnsAdjuster.excecute(this);
    }

    @Override
    public String getToolTipText(java.awt.event.MouseEvent e) {
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        try {
            return getValueAt(rowIndex, colIndex).toString();
        } catch (RuntimeException ex) {
            return null;
        }
    }

    class ColumnsAdjuster {

        public static void excecute(JTable table) {
            int c = 0;
            int r = 0;
            try {
                TableColumnModel columnModel = table.getColumnModel();
                for (int column = 0; column < table.getColumnCount(); column++) {
                    TableColumn columnHeader = columnModel.getColumn(column);
                    int preferredWidth = columnHeader.getMinWidth();
                    for (int row = 0; row < table.getRowCount(); row++) {
                        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                        Component component = table.prepareRenderer(cellRenderer, row, column);
                        c = column;
                        r = row;
                        int width = component.getPreferredSize().width + table.getIntercellSpacing().width;
                        preferredWidth = Math.max(preferredWidth, width);
                    }
                    columnHeader.setPreferredWidth(preferredWidth);
                }
            } catch (IllegalArgumentException ex) {
                Logger.getInstance().updateErrorLog(ex);
                Logger.getInstance().updateInfoLog("Error con y la fila " + r + " la columna " + c + ".");
            }
        }

    }
}
