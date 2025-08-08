package cu.lacumbre.auditor.view.custom;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class DisabledRowRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JTextField cell = new JTextField();
        cell.setText((String) value);
        cell.setEnabled((boolean) table.getValueAt(row, 2));
        return cell;
    }
}
