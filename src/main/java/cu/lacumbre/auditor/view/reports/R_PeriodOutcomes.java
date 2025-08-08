package cu.lacumbre.auditor.view.reports;

import cu.lacumbre.auditor.crud.ItemsCRUD;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_OutcomesReport;
import cu.lacumbre.auditor.view.utils.PeriodsNavigatorExportable;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;

public class R_PeriodOutcomes extends Reports {

    private double totalCost;

    public R_PeriodOutcomes(JFrame parent, boolean lockParent, Connection connection, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) {
        super(parent, lockParent, connection, operationsCRUD, itemsCRUD, R_Handler.PERIOD_OUTCOMES);
        initComponents();
        controls.setInvoker(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new JPanel();
        controls = new PeriodsNavigatorExportable();
        center = new JScrollPane();
        tableOutcomes = new CustomJTable();
        south = new JPanel();
        jLabel16 = new JLabel();
        jLabel17 = new JLabel();
        lblTotalCostValueTab1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Salida del periodo");
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setLayout(new BorderLayout(6, 6));
        main.add(controls, BorderLayout.NORTH);

        center.setBorder(BorderFactory.createTitledBorder(null, "Materias primas:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14))); // NOI18N
        center.setViewportView(tableOutcomes);

        main.add(center, BorderLayout.CENTER);

        south.setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 6));

        jLabel16.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Costo Total:");
        jLabel16.setEnabled(false);
        south.add(jLabel16);

        jLabel17.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText(" $");
        jLabel17.setEnabled(false);
        south.add(jLabel17);

        lblTotalCostValueTab1.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalCostValueTab1.setText("   ");
        lblTotalCostValueTab1.setEnabled(false);
        south.add(lblTotalCostValueTab1);

        main.add(south, BorderLayout.SOUTH);

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
            TreeMap<RawMaterial, Double[]> outcomes = reportsHandler.getPeriodRawMaterialsOutcomes(timing.atStartOfMonth(), timing.atStartOfNextMonth());
            CustomTM_OutcomesReport tableModel = new CustomTM_OutcomesReport(outcomes);
            tableOutcomes.setModel(tableModel);
            double[] totals = tableModel.getTotals();
            totalCost = totals[4];
            lblTotalCostValueTab1.setText(df.format(totalCost));
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    @Override
    protected void addCommonParameters() {
        super.addCommonParameters();
        parameters.put("TITLE", "Salidas del periodo");
        parameters.put("TC", totalCost);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JScrollPane center;
    private PeriodsNavigatorExportable controls;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel lblTotalCostValueTab1;
    private JPanel main;
    private JPanel south;
    private CustomJTable tableOutcomes;
    // End of variables declaration//GEN-END:variables

}
