package cu.lacumbre.auditor.view.reports;

import cu.lacumbre.auditor.crud.ItemsCRUD;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_SalesReport;
import cu.lacumbre.auditor.view.utils.PeriodsNavigatorExportable;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.time.LocalDate;

public final class R_PeriodSales extends Reports {

    private double totalIncome;
    private double totalProfit;

    public R_PeriodSales(JFrame parent, boolean lockParent, Connection connection, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) {
        super(parent, lockParent, connection, operationsCRUD, itemsCRUD, R_Handler.PERIOD_SALE);
        initComponents();
        controls.setInvoker(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new JPanel();
        centerTab2 = new JPanel();
        controls = new PeriodsNavigatorExportable();
        center = new JScrollPane();
        tableProduct = new CustomJTable();
        south = new JPanel();
        jLabel19 = new JLabel();
        jLabel20 = new JLabel();
        lblTotalPriceValue = new JLabel();
        jLabel21 = new JLabel();
        jLabel22 = new JLabel();
        jLabel23 = new JLabel();
        lblTotalUtilityValue = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ventas del periodo");
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setLayout(new BorderLayout(6, 6));

        centerTab2.setEnabled(false);
        centerTab2.setMinimumSize(new Dimension(450, 162));
        centerTab2.setPreferredSize(new Dimension(800, 478));
        centerTab2.setLayout(new BorderLayout(6, 6));
        centerTab2.add(controls, BorderLayout.NORTH);

        center.setBorder(BorderFactory.createTitledBorder(null, "Productos Vendidos:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14))); // NOI18N
        center.setViewportView(tableProduct);

        centerTab2.add(center, BorderLayout.CENTER);

        south.setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 6));

        jLabel19.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Ingreso Total:");
        jLabel19.setEnabled(false);
        south.add(jLabel19);

        jLabel20.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText(" $");
        jLabel20.setEnabled(false);
        south.add(jLabel20);

        lblTotalPriceValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalPriceValue.setText("                     ");
        lblTotalPriceValue.setEnabled(false);
        south.add(lblTotalPriceValue);

        jLabel21.setText("   ");
        south.add(jLabel21);

        jLabel22.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Utilidad Total:");
        jLabel22.setEnabled(false);
        south.add(jLabel22);

        jLabel23.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText(" $");
        jLabel23.setEnabled(false);
        south.add(jLabel23);

        lblTotalUtilityValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalUtilityValue.setText("                     ");
        lblTotalUtilityValue.setEnabled(false);
        south.add(lblTotalUtilityValue);

        centerTab2.add(south, BorderLayout.SOUTH);

        main.add(centerTab2, BorderLayout.CENTER);

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
            TreeMap<Product, Double> sales = reportsHandler.getPeriodSale(timing.atStartOfMonth(), timing.atStartOfNextMonth());
            CustomTM_SalesReport customTM_SalesReport = new CustomTM_SalesReport(sales, operationsCRUD.getPeriodExpenses(timing.atStartOfMonth(), timing.atStartOfNextMonth()));
            tableProduct.setModel(customTM_SalesReport);
            double[] totals = customTM_SalesReport.getTotals();
            totalIncome = totals[4];
            totalProfit = totals[6];
            lblTotalPriceValue.setText(df.format(totalIncome));
            lblTotalUtilityValue.setText(df.format(totalProfit));
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    @Override
    protected void addCommonParameters() {
        super.addCommonParameters();
        parameters.put("TITLE", "Ventas del periodo");
        parameters.put("TI", totalIncome);
        parameters.put("TP", totalProfit);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JScrollPane center;
    private JPanel centerTab2;
    private PeriodsNavigatorExportable controls;
    private JLabel jLabel19;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel lblTotalPriceValue;
    private JLabel lblTotalUtilityValue;
    private JPanel main;
    private JPanel south;
    private CustomJTable tableProduct;
    // End of variables declaration//GEN-END:variables

}
