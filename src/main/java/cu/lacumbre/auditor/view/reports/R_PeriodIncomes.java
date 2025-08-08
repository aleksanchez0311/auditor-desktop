package cu.lacumbre.auditor.view.reports;

import javax.swing.JFrame;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.view.custom.CustomTM_IncomesReport;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.time.LocalDate;

public class R_PeriodIncomes extends Reports {

    private double totalCost;
    private double billedTotalCost;
    private double leftForBills;

    public R_PeriodIncomes(JFrame parent, boolean lockParent, Connection connection, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) {
        super(parent, lockParent, connection, operationsCRUD, itemsCRUD, R_Handler.PERIOD_INCOMES);
        initComponents();
        controls.setInvoker(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new javax.swing.JPanel();
        controls = new cu.lacumbre.auditor.view.utils.PeriodsNavigatorExportable();
        center = new javax.swing.JPanel();
        scrollRawMaterials = new javax.swing.JScrollPane();
        tableWRawMaterials = new cu.lacumbre.auditor.view.custom.CustomJTable();
        scrollRfsMaterials = new javax.swing.JScrollPane();
        tableRFSRawMaterials = new cu.lacumbre.auditor.view.custom.CustomJTable();
        south = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblTotalExpendValue = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblIvoicedExpendValue = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblLeftForBillValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Entradas del periodo");
        getContentPane().setLayout(new java.awt.CardLayout(10, 10));

        main.setEnabled(false);
        main.setMinimumSize(new java.awt.Dimension(450, 162));
        main.setPreferredSize(new java.awt.Dimension(800, 478));
        main.setLayout(new java.awt.BorderLayout(6, 6));
        main.add(controls, java.awt.BorderLayout.NORTH);

        center.setPreferredSize(new java.awt.Dimension(800, 402));
        center.setLayout(new javax.swing.BoxLayout(center, javax.swing.BoxLayout.PAGE_AXIS));

        scrollRawMaterials.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Materias primas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        scrollRawMaterials.setViewportView(tableWRawMaterials);

        if(EntitySelector.currentEntity.isWorkable()){

            center.add(scrollRawMaterials);
        }

        scrollRfsMaterials.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listos para la Venta:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        scrollRfsMaterials.setViewportView(tableRFSRawMaterials);

        center.add(scrollRfsMaterials);

        main.add(center, java.awt.BorderLayout.CENTER);

        south.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 6));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Gasto Total:");
        jLabel16.setEnabled(false);
        south.add(jLabel16);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText(" $");
        jLabel17.setEnabled(false);
        south.add(jLabel17);

        lblTotalExpendValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalExpendValue.setText("   ");
        lblTotalExpendValue.setEnabled(false);
        south.add(lblTotalExpendValue);

        jLabel18.setText("   ");
        south.add(jLabel18);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Facturado:");
        jLabel19.setEnabled(false);
        south.add(jLabel19);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText(" $");
        jLabel21.setEnabled(false);
        south.add(jLabel21);

        lblIvoicedExpendValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIvoicedExpendValue.setText("   ");
        lblIvoicedExpendValue.setEnabled(false);
        south.add(lblIvoicedExpendValue);

        jLabel15.setText("   ");
        south.add(jLabel15);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Pendiente:");
        jLabel4.setEnabled(false);
        south.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText(" $");
        jLabel5.setEnabled(false);
        south.add(jLabel5);

        lblLeftForBillValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLeftForBillValue.setText("   ");
        lblLeftForBillValue.setEnabled(false);
        south.add(lblLeftForBillValue);

        main.add(south, java.awt.BorderLayout.SOUTH);

        getContentPane().add(main, "card2");

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
            ArrayList<Operation> wrmIncomes = reportsHandler.getPeriodIncomes(timing.atStartOfMonth(), timing.atStartOfNextMonth(), true);
            ArrayList<Operation> rfsrmIncomes = reportsHandler.getPeriodIncomes(timing.atStartOfMonth(), timing.atStartOfNextMonth(), false);
            CustomTM_IncomesReport customTM_WRMIncomesReport = new CustomTM_IncomesReport(operationsCRUD, wrmIncomes);
            CustomTM_IncomesReport customTM_RFSRMIncomesReport = new CustomTM_IncomesReport(operationsCRUD, rfsrmIncomes);
            tableWRawMaterials.setModel(customTM_WRMIncomesReport);
            tableRFSRawMaterials.setModel(customTM_RFSRMIncomesReport);
            double[] wrmTotals = customTM_WRMIncomesReport.getTotals();
            double[] rfsrmTotals = customTM_RFSRMIncomesReport.getTotals();
            totalCost = wrmTotals[5] + rfsrmTotals[5];
            billedTotalCost = wrmTotals[7] + rfsrmTotals[7];
            double billsNeeded = totalCost - (totalCost * 0.2);
            leftForBills = billsNeeded - billedTotalCost;
            lblTotalExpendValue.setText(df.format(totalCost));
            lblIvoicedExpendValue.setText(df.format(billedTotalCost));
            lblLeftForBillValue.setText(leftForBills >= 0 ? df.format(leftForBills) : "Gastos Justificados");
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    @Override
    protected void addCommonParameters() {
        super.addCommonParameters();
        parameters.put("TITLE", "Entradas del periodo");
        parameters.put("TC", totalCost);
        parameters.put("BTC", billedTotalCost);
        parameters.put("LFB", leftForBills >= 0 ? leftForBills : 0.0d);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel center;
    private cu.lacumbre.auditor.view.utils.PeriodsNavigatorExportable controls;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblIvoicedExpendValue;
    private javax.swing.JLabel lblLeftForBillValue;
    private javax.swing.JLabel lblTotalExpendValue;
    private javax.swing.JPanel main;
    private javax.swing.JScrollPane scrollRawMaterials;
    private javax.swing.JScrollPane scrollRfsMaterials;
    private javax.swing.JPanel south;
    private cu.lacumbre.auditor.view.custom.CustomJTable tableRFSRawMaterials;
    private cu.lacumbre.auditor.view.custom.CustomJTable tableWRawMaterials;
    // End of variables declaration//GEN-END:variables

}
