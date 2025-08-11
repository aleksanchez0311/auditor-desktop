package cu.lacumbre.auditor.utils.optimization.ui;

import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.JComponent;

import java.awt.event.ComponentAdapter;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;

public class ComponentCache {
    private static final Map<JComponent, Image> bufferCache = new WeakHashMap<>();
    private static final int MAX_CACHE_SIZE = 50;

    public static void enableBuffering(JComponent component) {
        component.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                clearBuffer(component);
            }
        });
    }

    public static Image getBuffer(JComponent component) {
        Image buffer = bufferCache.get(component);
        if (buffer == null || buffer.getWidth(null) != component.getWidth() ||
            buffer.getHeight(null) != component.getHeight()) {
            buffer = createBuffer(component);
            bufferCache.put(component, buffer);
        }
        return buffer;
    }

    private static Image createBuffer(JComponent component) {
        // Limpiar caché si es necesario
        if (bufferCache.size() > MAX_CACHE_SIZE) {
            bufferCache.clear();
        }
        
        return component.createImage(component.getWidth(), component.getHeight());
    }

    public static void clearBuffer(JComponent component) {
        bufferCache.remove(component);
    }

    public static void paintComponentBuffered(JComponent component, Graphics g) {
        Image buffer = getBuffer(component);
        Graphics bufferGraphics = buffer.getGraphics();
        try {
            // Configurar el contexto gráfico
            if (bufferGraphics instanceof Graphics2D) {
                Graphics2D g2d = (Graphics2D) bufferGraphics;
                g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                g2d.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                );
            }
            
            // Limpiar el buffer
            bufferGraphics.setColor(component.getBackground());
            bufferGraphics.fillRect(0, 0, component.getWidth(), component.getHeight());
            
            // Pintar en el buffer
            component.paint(bufferGraphics);
            
            // Dibujar el buffer en el componente
            g.drawImage(buffer, 0, 0, null);
        } finally {
            if (bufferGraphics != null) {
                bufferGraphics.dispose();
            }
        }
    }
}
