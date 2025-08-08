package cu.lacumbre.auditor.view.inventory;

import cu.lacumbre.auditor.EntitySelector;

import javax.swing.JDialog;
import javax.swing.JFrame;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_RMCosts;
import cu.lacumbre.auditor.view.utils.DaysNavigableView;
import cu.lacumbre.auditor.view.utils.DaysNavigator;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TreeMap;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Inventory extends JDialog implements DaysNavigableView {

    private final OperationsCRUD operationsCRUD;
    private Timing timing;

    public Inventory(JFrame parent, OperationsCRUD operationsCRUD, boolean lockParent) {
        super(parent, lockParent);
        this.operationsCRUD = operationsCRUD;
        timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
        initComponents();
    }

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
        try {
            timing = new Timing(LocalDate.of(year, month, day));
            setTitle("Inventario del " + timing.getLocalDate().toString());
            LocalDateTime.ofInstant(timing.atEndOfDay().minusSeconds(86400), ZoneId.systemDefault());
            TreeMap<RawMaterial, Double> rawMaterialExistences = operationsCRUD.getInventory(timing.atEndOfDay().minusSeconds(86400));
            jTable.setModel(new CustomTM_RMCosts(rawMaterialExistences));
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainP = new JPanel();
        controls = new DaysNavigator();
        mainPanel = new JPanel();
        centerPane = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTable = new CustomJTable();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(" Existencia de Materias Primas");
        getContentPane().setLayout(new CardLayout(10, 10));

        mainP.setLayout(new BorderLayout(0, 10));

        controls.setInvoker(this);
        mainP.add(controls, BorderLayout.NORTH);

        mainPanel.setEnabled(false);
        mainPanel.setLayout(new CardLayout());

        centerPane.setMinimumSize(new Dimension(476, 402));
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.LINE_AXIS));

        jTable.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable);

        centerPane.add(jScrollPane1);

        mainPanel.add(centerPane, "card2");

        mainP.add(mainPanel, BorderLayout.CENTER);

        getContentPane().add(mainP, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel centerPane;
    private DaysNavigator controls;
    private JScrollPane jScrollPane1;
    private CustomJTable jTable;
    private JPanel mainP;
    private JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

}
