package cu.lacumbre.auditor.utils.ui;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;


import cu.lacumbre.auditor.utils.ui.ModernComponents.ModernCellRenderer;
import cu.lacumbre.auditor.utils.ui.ModernComponents.ModernHeaderRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernComponents {
    
    public static class ModernTable extends JTable {
        public ModernTable() {
            setShowGrid(false);
            setIntercellSpacing(new Dimension(0, 0));
            setRowHeight(30);
            setFillsViewportHeight(true);
            setSelectionBackground(ModernThemeManager.getPrimaryColor());
            setSelectionForeground(Color.WHITE);
            getTableHeader().setDefaultRenderer(new ModernHeaderRenderer());
            setDefaultRenderer(Object.class, new ModernCellRenderer());
        }
    }
    
    public static class ModernHeaderRenderer extends DefaultTableCellRenderer {
        public ModernHeaderRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
            setBackground(ModernThemeManager.getSecondaryColor());
            setForeground(Color.DARK_GRAY);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }
    }
    
    public static class ModernCellRenderer extends DefaultTableCellRenderer {
        public ModernCellRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }
        
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (row % 2 == 0 && !isSelected) {
                setBackground(Color.WHITE);
            } else if (!isSelected) {
                setBackground(new Color(250, 250, 250));
            }
            
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            return this;
        }
    }
    
    public static JTextField createModernTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }
    
    public static JComboBox<?> createModernComboBox(ComboBoxModel<?> model) {
        JComboBox<?> comboBox = new JComboBox<>(model);
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setBackground(Color.WHITE);
        return comboBox;
    }
    
    public static JPanel createModernFormPanel() {
        JPanel panel = ModernThemeManager.createRoundedPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    public static JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 35));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    public static JScrollPane createModernScrollPane(java.awt.Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Personalizar las barras de desplazamiento
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(8, 0));
        verticalBar.setUnitIncrement(16);
        
        return scrollPane;
    }
    
    public static void addFormRow(JPanel panel, String labelText, JComponent component, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 10);
        panel.add(label, gbc);
        
        // Component
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(component, gbc);
    }
}
