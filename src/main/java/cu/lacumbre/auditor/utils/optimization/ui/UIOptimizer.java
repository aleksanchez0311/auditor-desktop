package cu.lacumbre.auditor.utils.optimization.ui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.lowagie.text.Image;

import cu.lacumbre.auditor.utils.optimization.OptimizedTableCellRenderer;
import cu.lacumbre.auditor.utils.optimization.TableOptimizer;
import net.sf.jasperreports.engine.component.Component;

public class UIOptimizer {
    private static final ExecutorService backgroundExecutor = Executors.newFixedThreadPool(2);
    private static final int BUFFER_SIZE = 100;
    private static final int PAINT_DELAY = 16; // ~60 FPS

    public static void optimizeComponent(JComponent component) {
        // Activar double buffering
        component.setDoubleBuffered(true);
        
        // Optimizar el pintado
        component.setOpaque(true);
        
        // Establecer un tamaño preferido razonable
        if (component.getPreferredSize().width == 0 || component.getPreferredSize().height == 0) {
            component.setPreferredSize(new Dimension(200, 100));
        }
    }

    public static void optimizeFrame(JFrame frame) {
        // Optimizar el frame principal
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Optimizar todos los componentes del frame
        optimizeContainer(frame.getContentPane());
    }

    public static void optimizeContainer(Container container) {
        // Optimizar todos los componentes hijos
        for (Component component : container.getComponents()) {
            if (component instanceof JComponent) {
                optimizeComponent((JComponent) component);
            }
            if (component instanceof Container) {
                optimizeContainer((Container) component);
            }
        }
    }

    public static void optimizeTable(JTable table) {
        // Usar el optimizador de tablas existente
        TableOptimizer.optimizeTable(table, BUFFER_SIZE);
        
        // Optimizar el renderizado de celdas
        table.setDefaultRenderer(Object.class, new OptimizedTableCellRenderer());
        
        // Optimizar el scroll
        if (table.getParent() instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) table.getParent();
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
    }

    public static void executeInBackground(Runnable task, Runnable onComplete) {
        backgroundExecutor.submit(() -> {
            try {
                task.run();
                SwingUtilities.invokeLater(onComplete);
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> handleError(e));
            }
        });
    }

    public static void optimizeImage(Image image) {
        if (image instanceof BufferedImage) {
            BufferedImage buffered = (BufferedImage) image;
            // Convertir a un formato más eficiente para el renderizado
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            BufferedImage optimized = gc.createCompatibleImage(
                buffered.getWidth(), 
                buffered.getHeight(),
                buffered.getTransparency()
            );
            Graphics2D g2d = optimized.createGraphics();
            g2d.drawImage(buffered, 0, 0, null);
            g2d.dispose();
        }
    }

    private static void handleError(Exception e) {
        JOptionPane.showMessageDialog(
            null,
            "Error en la operación en segundo plano: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void shutdown() {
        backgroundExecutor.shutdown();
    }
}
