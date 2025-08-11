package cu.lacumbre.auditor.utils.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ModernContentPanel extends JPanel {
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JLabel titleLabel;
    
    public ModernContentPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        initComponents();
    }
    
    private void initComponents() {
        // Panel de encabezado
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Panel de acciones del encabezado (lado derecho)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        headerPanel.add(actionPanel, BorderLayout.EAST);
        
        // Panel de contenido principal
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Agregar componentes
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    public void setContent(JComponent component) {
        contentPanel.removeAll();
        contentPanel.add(component, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public JPanel getHeaderActionPanel() {
        return (JPanel) headerPanel.getComponent(1);
    }
    
    public void addHeaderAction(JComponent component) {
        getHeaderActionPanel().add(component);
    }
    
    public void addQuickAction(String text, Runnable action) {
        JButton button = ModernComponents.createActionButton(text, ModernThemeManager.getPrimaryColor());
        button.addActionListener(e -> action.run());
        addHeaderAction(button);
    }
}
