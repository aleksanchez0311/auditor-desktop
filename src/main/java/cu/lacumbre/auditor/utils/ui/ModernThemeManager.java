package cu.lacumbre.auditor.utils.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Color;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class ModernThemeManager {
    private static final Color PRIMARY_COLOR = new Color(51, 153, 255);
    private static final Color SECONDARY_COLOR = new Color(245, 245, 245);
    private static final Color ACCENT_COLOR = new Color(255, 102, 102);
    private static final int BORDER_RADIUS = 8;
    
    public static void setupModernTheme() {
        try {
            // Configurar FlatLaf como Look and Feel
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            // Configurar propiedades globales
            UIManager.put("Button.arc", BORDER_RADIUS);
            UIManager.put("Component.arc", BORDER_RADIUS);
            UIManager.put("ProgressBar.arc", BORDER_RADIUS);
            UIManager.put("TextComponent.arc", BORDER_RADIUS);
            
            // Colores modernos
            UIManager.put("Button.background", PRIMARY_COLOR);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Panel.background", SECONDARY_COLOR);
            UIManager.put("TextField.background", Color.WHITE);
            UIManager.put("Table.selectionBackground", PRIMARY_COLOR);
            UIManager.put("Table.selectionForeground", Color.WHITE);
            
            // Fuentes modernas
            Font defaultFont = new Font("Segoe UI", Font.PLAIN, 12);
            UIManager.put("defaultFont", defaultFont);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JPanel createRoundedPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), BORDER_RADIUS * 2, BORDER_RADIUS * 2));
                g2.dispose();
            }
        };
    }
    
    public static JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return button;
    }
    
    public static Color getPrimaryColor() {
        return PRIMARY_COLOR;
    }
    
    public static Color getSecondaryColor() {
        return SECONDARY_COLOR;
    }
    
    public static Color getAccentColor() {
        return ACCENT_COLOR;
    }
}
