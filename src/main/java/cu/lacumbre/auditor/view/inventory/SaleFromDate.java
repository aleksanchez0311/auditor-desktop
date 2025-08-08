package cu.lacumbre.auditor.view.inventory;

import cu.lacumbre.auditor.DaysController;
import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import javax.swing.JDialog;
import javax.swing.JFrame;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.utils.DaysNavigableView;
import cu.lacumbre.auditor.view.utils.DaysNavigator;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class SaleFromDate extends JDialog implements DaysNavigableView {

    private final OperationsCRUD operationsCRUD;
    private final ItemsCRUD itemsCRUD;
    private final DaysController daysController;
    private ArrayList<Operation> operations;
    private Timing timing;

    public SaleFromDate(JFrame parent, boolean modal, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, DaysController daysController) {
        super(parent, modal);
        this.operationsCRUD = operationsCRUD;
        this.itemsCRUD = itemsCRUD;
        this.daysController = daysController;
        timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new JPanel();
        controls = new DaysNavigator();
        centerPane = new JPanel();
        jScrollPane1 = new JScrollPane();
        tableSaleFromDate = new CustomJTable();
        southPane = new JPanel();
        btnInventoryAdjust = new JButton();
        btnRawMaterialsUsesView = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Efectuar venta del día");
        getContentPane().setLayout(new CardLayout(10, 10));

        mainPanel.setLayout(new BorderLayout(10, 10));

        controls.setInvoker(this);
        mainPanel.add(controls, BorderLayout.NORTH);

        centerPane.setPreferredSize(new Dimension(600, 360));
        centerPane.setLayout(new CardLayout());

        tableSaleFromDate.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableSaleFromDate);

        centerPane.add(jScrollPane1, "card2");

        mainPanel.add(centerPane, BorderLayout.CENTER);

        southPane.setPreferredSize(new Dimension(600, 40));
        southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));

        btnInventoryAdjust.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnInventoryAdjust.setText("Comprobar y Ajustar Inventario");
        btnInventoryAdjust.setVisible(false);
        btnInventoryAdjust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnInventoryAdjustActionPerformed(evt);
            }
        });
        southPane.add(btnInventoryAdjust);

        btnRawMaterialsUsesView.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnRawMaterialsUsesView.setText("Ver Consumo de Materias Primas");
        btnRawMaterialsUsesView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRawMaterialsUsesViewActionPerformed(evt);
            }
        });
        southPane.add(btnRawMaterialsUsesView);

        mainPanel.add(southPane, BorderLayout.SOUTH);

        getContentPane().add(mainPanel, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public Timing getTiming() {
        return timing;
    }

    @Override
    public void setTiming(Timing timing) {
        this.timing = timing;
    }

    @Override
    public void updateView(int day, int month, int year) {
        timing = new Timing(LocalDate.of(year, month, day));
        setTitle("Resumen de ventas del " + timing.getLocalDate().toString());
        TreeMap<Product, Double[]> ammountOfProducts = new TreeMap<>(Comparator.comparingInt(Product::getCode));
        operations = operationsCRUD.getDateSaleOperationsList(timing.getLocalDate());
        operations.sort(Comparator.comparingInt(Operation::getId));
        for (Operation operation : operations) {
            Double[] values = new Double[]{operation.getAmmount(), operation.getSigleCost(), operation.getValue()};
            ammountOfProducts.put((Product) operation.getItem(), values);
        }
        tableSaleFromDate.setModel(new cu.lacumbre.auditor.view.custom.CustomTM_PDateSaleSummary(ammountOfProducts));
    }

    private void btnRawMaterialsUsesViewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnRawMaterialsUsesViewActionPerformed
        try {
            RawMaterialsFromDate dialog = new RawMaterialsFromDate((JFrame) this.getParent(), false, operationsCRUD, itemsCRUD, daysController, operations, timing);
            dialog.setLocationRelativeTo((JFrame) this.getParent());
            dialog.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnRawMaterialsUsesViewActionPerformed

    private void btnInventoryAdjustActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnInventoryAdjustActionPerformed
        LoadCSV main = new LoadCSV((JFrame) this.getParent(), false, operationsCRUD, itemsCRUD, daysController);
        main.setLocationRelativeTo(this.getParent());
        main.setResizable(true);
        main.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnInventoryAdjustActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnInventoryAdjust;
    private JButton btnRawMaterialsUsesView;
    private JPanel centerPane;
    private DaysNavigator controls;
    private JScrollPane jScrollPane1;
    private JPanel mainPanel;
    private JPanel southPane;
    private CustomJTable tableSaleFromDate;
    // End of variables declaration//GEN-END:variables

}
