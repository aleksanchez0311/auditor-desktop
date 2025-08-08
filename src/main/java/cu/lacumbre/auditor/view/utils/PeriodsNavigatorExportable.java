package cu.lacumbre.auditor.view.utils;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public final class PeriodsNavigatorExportable extends PeriodsNavigator {

    private JButton btnPrint;

    public PeriodsNavigatorExportable() {
        super();
        btnPrint = new JButton();
        btnPrint.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnPrint.setText("Exportar");
        btnPrint.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });
        add(btnPrint);
    }

    private void btnPrintMouseClicked(MouseEvent evt) {
        invoker.export();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
