package cu.lacumbre.auditor.utils.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ModernDialog extends JDialog {
    protected final ModernContentPanel contentPanel;
    protected final JPanel buttonPanel;
    private float fadeOpacity = 1.0f;
    
    public ModernDialog(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        
        // Configurar ventana
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(parent);
        
        // Panel de contenido principal
        contentPanel = new ModernContentPanel();
        contentPanel.setTitle(title);
        
        // Panel de botones
        buttonPanel = createButtonPanel();
        
        // Layout
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(contentPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        // Efecto de fade al cerrar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fadeOut();
            }
        });
    }
    
    protected JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }
    
    protected void addDefaultButtons() {
        JButton cancelButton = ModernComponents.createActionButton("Cancelar", ModernThemeManager.getAccentColor());
        JButton saveButton = ModernComponents.createActionButton("Guardar", ModernThemeManager.getPrimaryColor());
        
        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> handleSave());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(saveButton);
    }
    
    protected void handleSave() {
        // Implementar en subclases
    }
    
    protected void fadeOut() {
        Timer timer = new Timer(20, null);
        fadeOpacity = 1.0f;
        
        timer.addActionListener(e -> {
            fadeOpacity -= 0.1f;
            if (fadeOpacity <= 0.0f) {
                timer.stop();
                dispose();
            } else {
                setOpacity(fadeOpacity);
            }
        });
        
        timer.start();
    }
    
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            setOpacity(0.0f);
            super.setVisible(true);
            fadeIn();
        } else {
            fadeOut();
        }
    }
    
    private void fadeIn() {
        Timer timer = new Timer(20, null);
        fadeOpacity = 0.0f;
        
        timer.addActionListener(e -> {
            fadeOpacity += 0.1f;
            if (fadeOpacity >= 1.0f) {
                timer.stop();
            } else {
                setOpacity(fadeOpacity);
            }
        });
        
        timer.start();
    }
}
