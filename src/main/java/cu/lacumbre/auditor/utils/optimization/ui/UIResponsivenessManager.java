package cu.lacumbre.auditor.utils.optimization.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.Color;

public class UIResponsivenessManager {
    private static final int UI_UPDATE_DELAY = 100; // ms
    private static final Timer uiUpdateTimer = new Timer(UI_UPDATE_DELAY, null);
    private static final AtomicBoolean isProcessing = new AtomicBoolean(false);

    static {
        uiUpdateTimer.setRepeats(false);
    }

    public static void scheduleUIUpdate(Runnable update) {
        if (!isProcessing.get()) {
            isProcessing.set(true);
            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        SwingUtilities.invokeLater(update);
                    } finally {
                        isProcessing.set(false);
                    }
                }
            };
            
            uiUpdateTimer.addActionListener(listener);
            uiUpdateTimer.restart();
        }
    }

    public static void showLoadingDialog(JComponent parent, String message) {
        JDialog loadingDialog = new JDialog();
        loadingDialog.setUndecorated(true);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        panel.add(messageLabel, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        
        loadingDialog.add(panel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(parent);
        loadingDialog.setModal(true);
        
        // Mostrar después de un pequeño retraso para evitar flashes
        Timer showTimer = new Timer(500, e -> loadingDialog.setVisible(true));
        showTimer.setRepeats(false);
        showTimer.start();
    }

    public static void hideLoadingDialog(JDialog loadingDialog) {
        if (loadingDialog != null && loadingDialog.isVisible()) {
            SwingUtilities.invokeLater(() -> loadingDialog.dispose());
        }
    }

    public static void addDelayedActionListener(JComponent component, ActionListener listener, int delay) {
        Timer timer = new Timer(delay, null);
        timer.setRepeats(false);
        
        ActionListener delayedListener = e -> {
            if (!timer.isRunning()) {
                timer.addActionListener(evt -> listener.actionPerformed(e));
                timer.start();
            }
        };
        
        if (component instanceof AbstractButton) {
            ((AbstractButton) component).addActionListener(delayedListener);
        } else if (component instanceof JTextField) {
            ((JTextField) component).addActionListener(delayedListener);
        }
    }
}
