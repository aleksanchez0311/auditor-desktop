package cu.lacumbre.auditor.view.reports;

import cu.lacumbre.auditor.crud.ItemsCRUD;
import java.sql.SQLException;
import java.util.TreeMap;

import javax.swing.JFrame;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.view.custom.CustomTM_MovementsReport;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.time.LocalDate;

public class R_PeriodMovements extends Reports {

    public R_PeriodMovements(JFrame parent, boolean lockParent, Connection connection, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) {
        super(parent, lockParent, connection, operationsCRUD, itemsCRUD, R_Handler.PERIOD_MOVEMENTS);
        initComponents();
        controls.setInvoker(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new javax.swing.JPanel();
        controls = new cu.lacumbre.auditor.view.utils.PeriodsNavigatorExportable();
        center = new javax.swing.JScrollPane();
        tableRawMaterials = new cu.lacumbre.auditor.view.custom.CustomJTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Movimientos del periodo");
        getContentPane().setLayout(new java.awt.CardLayout(10, 10));

        main.setLayout(new java.awt.BorderLayout(6, 6));
        main.add(controls, java.awt.BorderLayout.NORTH);

        center.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Materias primas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        center.setViewportView(tableRawMaterials);

        main.add(center, java.awt.BorderLayout.CENTER);

        getContentPane().add(main, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public Timing getTiming() {
        return timing;
    }

    @Override
    public void setTiming(Timing newTiming) {
        this.timing = newTiming;
    }

    @Override
    public void updateView(int month, int year) {
        try {
            timing = new Timing(LocalDate.of(year, month, 1));
            TreeMap<RawMaterial, TreeMap<String, Double>> movements = reportsHandler.getPeriodRawMaterialsExistences(timing.atStartOfMonth(), timing.atStartOfNextMonth());
            CustomTM_MovementsReport tableModel = new CustomTM_MovementsReport(movements);
            tableRawMaterials.setModel(tableModel);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog( ex);
        }
    }

    @Override
    protected void addCommonParameters() {
        super.addCommonParameters();
        parameters.put("TITLE", "Movimientos del periodo");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane center;
    private cu.lacumbre.auditor.view.utils.PeriodsNavigatorExportable controls;
    private javax.swing.JPanel main;
    private cu.lacumbre.auditor.view.custom.CustomJTable tableRawMaterials;
    // End of variables declaration//GEN-END:variables

}
