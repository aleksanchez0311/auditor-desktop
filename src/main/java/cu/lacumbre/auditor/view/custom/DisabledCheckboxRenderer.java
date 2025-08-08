package cu.lacumbre.auditor.view.custom;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DisabledCheckboxRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JCheckBox checkbox = new JCheckBox();
        checkbox.setSelected((Boolean) value);
        checkbox.setHorizontalAlignment(JLabel.CENTER);
        checkbox.setEnabled(table.isCellEditable(row, column));
        return checkbox;
    }
}
