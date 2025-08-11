package cu.lacumbre.auditor.utils.ui;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DashboardView extends JPanel {
    private final ModernContentPanel contentPanel;
    private final JPanel statsPanel;
    private JPanel chartsPanel;
    private final DateTimeFormatter dateFormatter;
    
    public DashboardView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Panel de contenido principal
        contentPanel = new ModernContentPanel();
        contentPanel.setTitle("Panel de Control");
        
        // Panel de estadísticas
        statsPanel = createStatsPanel();
        contentPanel.setContent(createMainContent());
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Actualizar layout cuando se redimensiona
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidateLayout();
            }
        });
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4, 15, 0));
        panel.setOpaque(false);
        
        // Estadística 1: Ventas del día
        addStatCard(panel, "Ventas del Día", "0.00", "↑ 5%", ModernThemeManager.getPrimaryColor());
        
        // Estadística 2: Gastos
        addStatCard(panel, "Gastos", "0.00", "↓ 3%", new Color(255, 99, 71));
        
        // Estadística 3: Inventario
        addStatCard(panel, "Items en Inventario", "150", "+12", new Color(46, 139, 87));
        
        // Estadística 4: Personal Activo
        addStatCard(panel, "Personal Activo", "8", "100%", new Color(70, 130, 180));
        
        return panel;
    }
    
    private void addStatCard(JPanel container, String title, String value, String change, Color color) {
        JPanel card = ModernThemeManager.createRoundedPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        card.setBackground(Color.WHITE);
        
        // Título
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 100));
        
        // Valor
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        
        // Cambio
        JLabel changeLabel = new JLabel(change);
        changeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        changeLabel.setForeground(new Color(100, 100, 100));
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(changeLabel);
        
        container.add(card);
    }
    
    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 20));
        mainPanel.setOpaque(false);
        
        // Agregar panel de estadísticas
        mainPanel.add(statsPanel, BorderLayout.NORTH);
        
        // Panel de gráficos
        chartsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        chartsPanel.setOpaque(false);
        
        // Gráfico 1: Ventas por hora
        JPanel salesChart = createChartPanel("Ventas por Hora", new Color(51, 153, 255));
        chartsPanel.add(salesChart);
        
        // Gráfico 2: Productos más vendidos
        JPanel productsChart = createChartPanel("Productos Más Vendidos", new Color(46, 139, 87));
        chartsPanel.add(productsChart);
        
        mainPanel.add(chartsPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createChartPanel(String title, Color color) {
        JPanel panel = ModernThemeManager.createRoundedPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Aquí se agregaría el gráfico real
        JPanel chartPlaceholder = new JPanel();
        chartPlaceholder.setPreferredSize(new Dimension(0, 200));
        chartPlaceholder.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 30));
        panel.add(chartPlaceholder, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void revalidateLayout() {
        if (getWidth() < 800) {
            chartsPanel.setLayout(new GridLayout(2, 1, 0, 15));
        } else {
            chartsPanel.setLayout(new GridLayout(1, 2, 15, 0));
        }
        revalidate();
    }
    
    public void updateStats(double dailySales, double expenses, int inventory, int activeStaff) {
        // Actualizar los valores de las estadísticas
    }
}
