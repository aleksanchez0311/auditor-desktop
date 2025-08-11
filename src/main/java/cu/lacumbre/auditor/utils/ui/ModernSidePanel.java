package cu.lacumbre.auditor.utils.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ModernSidePanel extends JPanel {
    private final Color HOVER_COLOR = new Color(240, 240, 240);
    private final int BUTTON_HEIGHT = 40;
    private final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    private JPanel menuPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    public ModernSidePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));
        setBackground(Color.WHITE);
        
        initComponents();
    }
    
    private void initComponents() {
        // Panel superior para el logo/título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ModernThemeManager.getPrimaryColor());
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("Auditor");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Panel de menú con diseño vertical
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.WHITE);
        
        // Panel de contenido con CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        
        // ScrollPane para el menú
        JScrollPane menuScroll = new JScrollPane(menuPanel);
        menuScroll.setBorder(null);
        menuScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Agregar componentes
        add(headerPanel, BorderLayout.NORTH);
        add(menuScroll, BorderLayout.CENTER);
    }
    
    public JButton addMenuItem(String text, String iconPath) {
        JButton menuButton = new JButton(text);
        menuButton.setFont(MENU_FONT);
        menuButton.setForeground(new Color(60, 60, 60));
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false);
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);
        menuButton.setPreferredSize(new Dimension(200, BUTTON_HEIGHT));
        menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, BUTTON_HEIGHT));
        
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            menuButton.setIcon(new ImageIcon(img));
        }
        
        // Efectos hover
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuButton.setContentAreaFilled(true);
                menuButton.setBackground(HOVER_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                menuButton.setContentAreaFilled(false);
            }
        });
        
        menuPanel.add(menuButton);
        menuPanel.add(Box.createVerticalStrut(1)); // Espaciado entre botones
        
        return menuButton;
    }
    
    public void addContent(String name, JComponent content) {
        contentPanel.add(content, name);
    }
    
    public void showContent(String name) {
        cardLayout.show(contentPanel, name);
    }
    
    public JPanel getContentPanel() {
        return contentPanel;
    }
}
