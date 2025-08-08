package cu.lacumbre.auditor.view.inventory;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JDialog;
import javax.swing.JFrame;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.model.RawMaterialLista;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_PAdjustNoSoldProducts;
import cu.lacumbre.auditor.view.utils.PeriodsNavigableView;
import cu.lacumbre.auditor.view.utils.PeriodsNavigator;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class AdjustNotSoldProducts extends JDialog implements PeriodsNavigableView {

    private final OperationsCRUD operationsCRUD;
    private final ItemsCRUD itemsCRUD;
    private ArrayList<Operation> operations;
    private Timing timing;

    public AdjustNotSoldProducts(JFrame parent, boolean modal, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) {
        super(parent, modal);
        this.operationsCRUD = operationsCRUD;
        this.itemsCRUD = itemsCRUD;
        timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
        initComponents();
        controls.setInvoker(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new JPanel();
        controls = new PeriodsNavigator();
        centerPane = new JPanel();
        jScrollPane1 = new JScrollPane();
        tableUnsoldRaws = new CustomJTable();
        southPane = new JPanel();
        btnAdjust = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ajustar Productos no vendidos en el periodo");
        getContentPane().setLayout(new CardLayout(10, 10));

        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.add(controls, BorderLayout.NORTH);

        centerPane.setPreferredSize(new Dimension(600, 360));
        centerPane.setLayout(new CardLayout());

        tableUnsoldRaws.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableUnsoldRaws.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tableUnsoldRaws);

        centerPane.add(jScrollPane1, "card2");

        mainPanel.add(centerPane, BorderLayout.CENTER);

        southPane.setPreferredSize(new Dimension(600, 40));
        southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));

        btnAdjust.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnAdjust.setText("Ajustar");
        btnAdjust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAdjustActionPerformed(evt);
            }
        });
        southPane.add(btnAdjust);

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
    public void updateView(int month, int year) {
        try {
            timing = new Timing(LocalDate.of(year, month, 1));
            setTitle("Ajustar productos no vendidos en " + timing.getLocalDate().toString());
            TreeMap<RawMaterial, Double> periodUnsoldSaleableRawMaterials = operationsCRUD.getPeriodUnsoldSaleableRawMaterials(timing);
            tableUnsoldRaws.setModel(new CustomTM_PAdjustNoSoldProducts(periodUnsoldSaleableRawMaterials, itemsCRUD));
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }


    private void btnAdjustActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAdjustActionPerformed
//        try {
        int selectedRow = tableUnsoldRaws.getSelectedRow();
        if (selectedRow != -1) {
            try {
                RawMaterialLista rawMaterialLista = (RawMaterialLista) new ArrayList<>(((CustomTM_PAdjustNoSoldProducts) tableUnsoldRaws.getModel()).getDatas().keySet().stream().toList()).get(selectedRow);
                int ammount = new ArrayList<>(((CustomTM_PAdjustNoSoldProducts) tableUnsoldRaws.getModel()).getDatas().values()).get(selectedRow).intValue();
                Product productListo = rawMaterialLista.getLinkedProduct(itemsCRUD.getItemProductsCRUD().getProductsListoList());
                int weightedSale = operationsCRUD.getWeightedSaleOf(productListo);
                int[] sales = operationsCRUD.adjustUnsoldProducts(weightedSale, ammount, timing.getLocalDate());
                TreeMap<LocalDate, Integer> dates = new TreeMap<>();
                for (int i = 0; i < sales.length; i++) {
                    dates.put(LocalDate.of(timing.getYear(), timing.getMonth(), i + 1), sales[i]);
                }
                ArrayList<Operation> opers = new ArrayList<>();
                for (Map.Entry<LocalDate, Integer> entry : dates.entrySet()) {
                    LocalDate date = entry.getKey();
                    Integer sale = entry.getValue();
                    if (sale > 0) {
                        opers.add(new Operation(productListo, new Timing(date).atEndOfDay(), sale, productListo.getPrice() * sale, false, false, false));
                        opers.add(new Operation(rawMaterialLista, new Timing(date).atMorningOfDay(), sale, rawMaterialLista.getDefaultCost() * sale, false, false, false));
                    }
                }
                operationsCRUD.save(opers);
                JOptionPane.showMessageDialog(this, "Operaciones registradas correctamente");
                updateView(timing.getMonth(), timing.getYear());
            } catch (SQLException ex) {
                Logger.getInstance().updateErrorLog(ex);
            }
        }
//        } catch (SQLException ex) {
//            Logger.getInstance().updateErrorLog(ex);
//        }
    }//GEN-LAST:event_btnAdjustActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAdjust;
    private JPanel centerPane;
    private PeriodsNavigator controls;
    private JScrollPane jScrollPane1;
    private JPanel mainPanel;
    private JPanel southPane;
    private CustomJTable tableUnsoldRaws;
    // End of variables declaration//GEN-END:variables

    @Override
    public void export() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
