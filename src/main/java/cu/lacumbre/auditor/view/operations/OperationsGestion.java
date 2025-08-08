package cu.lacumbre.auditor.view.operations;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import com.toedter.calendar.JDateChooser;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.view.custom.CustomListModel;
import cu.lacumbre.auditor.view.utils.Checker;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Comparator;
import javax.swing.Box;
import javax.swing.JTextField;

public abstract class OperationsGestion extends JDialog {

    protected final OperationsCRUD operationsCRUD;
    protected ArrayList<Operation> operations;
    protected final ItemsCRUD itemsCRUD;
    protected final Checker checker;
    private String currentFilter;
    private boolean isInserting = false;

    public OperationsGestion(JFrame parent, boolean lockParent, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, boolean isIncomeForm, ArrayList<Operation> operations, String category) {
        super(parent, lockParent);
        this.operationsCRUD = operationsCRUD;
        this.itemsCRUD = itemsCRUD;
        this.operations = operations;
        this.checker = new Checker(this, itemsCRUD, category, false);
        String title = "Gestionar " + (isIncomeForm ? "entrada " : "salida ") + "de " + category;
        setTitle(title);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new JPanel();
        controlsPane = new JPanel();
        northPane = new JPanel();
        idPane = new JPanel();
        lblIDKey = new JLabel();
        jLabel10 = new JLabel();
        lblIDValue = new JLabel();
        datePane = new JPanel();
        lblDateKey = new JLabel();
        jLabel20 = new JLabel();
        dcDateValue = new JDateChooser();
        ammountAndCostPane = new JPanel();
        lblAmmountKey = new JLabel();
        jLabel22 = new JLabel();
        spAmmountValue = new JSpinner();
        jLabel8 = new JLabel();
        lblCostKey = new JLabel();
        jLabel6 = new JLabel();
        spCostValue = new JSpinner();
        detailsPane = new JPanel();
        lblSingleCostMemoryValue = new JLabel();
        lblSingleCostKey = new JLabel();
        jLabel2 = new JLabel();
        lblSingleCostValue = new JLabel();
        filler1 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        cbBilledValue = new JCheckBox();
        centerPane = new JPanel();
        list = new JPanel();
        filter = new JPanel();
        tfFilter = new JTextField();
        jButton1 = new JButton();
        scroll = new JScrollPane();
        listObjects = new JList<>();
        total = new JPanel();
        jPanel1 = new JPanel();
        lblTotalKay = new JLabel();
        lblTotalValue = new JLabel();
        southPane = new JPanel();
        crudButtons = new JPanel();
        btnSave = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();
        otherButtons = new JPanel();
        btnAdd = new JButton();
        btnCancel = new JButton();
        mainPanel = new JPanel();
        itemsPanel = new JScrollPane();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new CardLayout(10, 10));

        panel.setLayout(new BorderLayout(10, 10));

        controlsPane.setEnabled(false);
        controlsPane.setMinimumSize(new Dimension(368, 368));
        controlsPane.setPreferredSize(new Dimension(368, 368));
        controlsPane.setLayout(new BorderLayout(10, 10));

        northPane.setLayout(new GridLayout(4, 1, 6, 6));

        lblIDKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblIDKey.setText("ID:");
        lblIDKey.setEnabled(false);
        idPane.add(lblIDKey);

        jLabel10.setText(" ");
        jLabel10.setEnabled(false);
        idPane.add(jLabel10);

        lblIDValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblIDValue.setText(" ");
        lblIDValue.setEnabled(false);
        idPane.add(lblIDValue);

        northPane.add(idPane);

        datePane.setLayout(new BoxLayout(datePane, BoxLayout.LINE_AXIS));

        lblDateKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblDateKey.setText("Fecha:");
        lblDateKey.setEnabled(false);
        datePane.add(lblDateKey);

        jLabel20.setText("   ");
        jLabel20.setEnabled(false);
        datePane.add(jLabel20);

        dcDateValue.setEnabled(false);
        dcDateValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        dcDateValue.setNextFocusableComponent(spAmmountValue);
        datePane.add(dcDateValue);

        northPane.add(datePane);

        ammountAndCostPane.setEnabled(false);
        ammountAndCostPane.setLayout(new BoxLayout(ammountAndCostPane, BoxLayout.LINE_AXIS));

        lblAmmountKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblAmmountKey.setText("Cantidad:");
        lblAmmountKey.setEnabled(false);
        ammountAndCostPane.add(lblAmmountKey);

        jLabel22.setText("   ");
        jLabel22.setEnabled(false);
        ammountAndCostPane.add(jLabel22);

        spAmmountValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spAmmountValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spAmmountValue.setEnabled(false);
        spCostValue.addChangeListener(this::spinersValueStateChanged);
        spAmmountValue.addChangeListener(this::spinersValueStateChanged);
        ammountAndCostPane.add(spAmmountValue);

        jLabel8.setText("   ");
        ammountAndCostPane.add(jLabel8);

        lblCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblCostKey.setText("Costo Total:");
        lblCostKey.setEnabled(false);
        ammountAndCostPane.add(lblCostKey);

        jLabel6.setText("   ");
        ammountAndCostPane.add(jLabel6);

        spCostValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spCostValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spCostValue.setEnabled(false);
        spCostValue.addChangeListener(this::spinersValueStateChanged);
        ammountAndCostPane.add(spCostValue);

        northPane.add(ammountAndCostPane);

        detailsPane.setEnabled(false);
        detailsPane.setLayout(new BoxLayout(detailsPane, BoxLayout.LINE_AXIS));

        lblSingleCostMemoryValue.setVisible(false);
        detailsPane.add(lblSingleCostMemoryValue);

        lblSingleCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblSingleCostKey.setText("Costo Unitario:");
        lblSingleCostKey.setEnabled(false);
        detailsPane.add(lblSingleCostKey);

        jLabel2.setText(" ");
        detailsPane.add(jLabel2);

        lblSingleCostValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblSingleCostValue.setEnabled(false);
        detailsPane.add(lblSingleCostValue);
        detailsPane.add(filler1);

        cbBilledValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        cbBilledValue.setText("Facturado?");
        cbBilledValue.setEnabled(false);
        detailsPane.add(cbBilledValue);

        northPane.add(detailsPane);

        controlsPane.add(northPane, BorderLayout.NORTH);

        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.PAGE_AXIS));

        list.setMinimumSize(new Dimension(382, 130));
        list.setLayout(new BoxLayout(list, BoxLayout.LINE_AXIS));

        filter.setLayout(new BoxLayout(filter, BoxLayout.LINE_AXIS));

        tfFilter.setHorizontalAlignment(JTextField.CENTER);
        tfFilter.setMaximumSize(new Dimension(100, 24));
        tfFilter.setMinimumSize(new Dimension(100, 24));
        tfFilter.setPreferredSize(new Dimension(100, 24));
        filter.add(tfFilter);

        jButton1.setFont(new Font("Segoe UI", 0, 3)); // NOI18N
        jButton1.setText("y");
        jButton1.setMaximumSize(new Dimension(24, 24));
        jButton1.setMinimumSize(new Dimension(24, 24));
        jButton1.setOpaque(false);
        jButton1.setPreferredSize(new Dimension(24, 24));
        filter.add(jButton1);

        filter.setVisible(false);

        list.add(filter);

        listObjects.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                listObjectsKeyTyped(evt);
            }
        });
        listObjects.addListSelectionListener(this::listObjectsValueChanged);
        scroll.setViewportView(listObjects);

        list.add(scroll);

        centerPane.add(list);

        total.setMaximumSize(new Dimension(2147483647, 30));
        total.setLayout(new CardLayout());

        jPanel1.setMaximumSize(new Dimension(32767, 30));

        lblTotalKay.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalKay.setText("Total:");
        jPanel1.add(lblTotalKay);

        lblTotalValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalValue.setText("0");
        lblTotalValue.setMaximumSize(new Dimension(500, 20));
        lblTotalValue.setMinimumSize(new Dimension(38, 20));
        lblTotalValue.setPreferredSize(new Dimension(38, 20));
        jPanel1.add(lblTotalValue);

        total.add(jPanel1, "card3");

        centerPane.add(total);

        controlsPane.add(centerPane, BorderLayout.CENTER);

        southPane.setLayout(new GridLayout(2, 1, 6, 6));

        crudButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));

        btnSave.setVisible(false);
        btnSave.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSave.setText("Guardar");
        btnSave.addActionListener(this::btnSaveActionPerformed);
        crudButtons.add(btnSave);

        btnUpdate.setVisible(false);
        btnUpdate.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setText("Modificar");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        crudButtons.add(btnUpdate);

        btnDelete.setVisible(false);
        btnDelete.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setText("Eliminar");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        crudButtons.add(btnDelete);

        southPane.add(crudButtons);

        otherButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));

        btnAdd.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setText("Nuevo");
        btnAdd.setNextFocusableComponent(dcDateValue);
        btnAdd.addActionListener(this::btnAddActionPerformed);
        otherButtons.add(btnAdd);

        btnCancel.setVisible(false);
        btnCancel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.setNextFocusableComponent(listObjects);
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });
        btnCancel.addActionListener(this::btnCancelActionPerformed);
        otherButtons.add(btnCancel);

        southPane.add(otherButtons);

        controlsPane.add(southPane, BorderLayout.SOUTH);

        panel.add(controlsPane, BorderLayout.WEST);

        mainPanel.setBorder(BorderFactory.createTitledBorder(""));
        mainPanel.setLayout(new CardLayout());
        mainPanel.add(itemsPanel, "card2");

        panel.add(mainPanel, BorderLayout.CENTER);

        getContentPane().add(panel, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        prepareViewToInsertion();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try {
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    protected ArrayList<Operation> delete() {
        try {
            ArrayList<Operation> selectedOperatons = (ArrayList<Operation>) listObjects.getSelectedValuesList();
            int response = -100;
            if (!selectedOperatons.isEmpty()) {
                if (selectedOperatons.size() == 1) {
                    response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar la operación elegida", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
                } else {
                    response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar las " + selectedOperatons.size() + " operaciones elegidas?", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
                }
                if (response == 0) {
                    operationsCRUD.delete(selectedOperatons);
                    refreshFrameView();
                    JOptionPane.showMessageDialog(this, "Operación(ones) eliminada(s) correctamente");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe elegir al menos una operación para eliminar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            return selectedOperatons;
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
        return null;
    }

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (!evt.getValueIsAdjusting()) {
            if (!isInserting) {
                Operation operation = (Operation) listObjects.getSelectedValue();
                if (operation != null) {
                    prepareViewToEdition();
                    lblIDValue.setText(operation.getId() + "");
                    dcDateValue.setDate(new Timing(operation.getInstant()).getDate());
                    spAmmountValue.setValue(operation.getAmmount());
                    spCostValue.setValue(operation.getValue());
                    cbBilledValue.setSelected(operation.isBilled());
                    checker.check(operation.getItem().getId());
                }
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void spinersValueStateChanged(ChangeEvent evt) {//GEN-FIRST:event_spinersValueStateChanged
        double cost = (double) spCostValue.getValue();
        double ammount = (double) spAmmountValue.getValue();
        if (ammount > 0.0d) {
            lblSingleCostKey.setEnabled(true);
            lblSingleCostValue.setEnabled(true);
            double singleCost = cost / ammount;
            DecimalFormat df = new DecimalFormat("$#.##");
            String singleCostFormated = df.format(singleCost);
            lblSingleCostValue.setText(singleCostFormated);
        }
    }//GEN-LAST:event_spinersValueStateChanged

    private void listObjectsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listObjectsKeyTyped
        currentFilter = tfFilter.getText();
        char charTyped = evt.getKeyChar();
        if (Character.isAlphabetic(charTyped) || Character.isWhitespace(charTyped) || Character.isDigit(charTyped)) {
            currentFilter = currentFilter + charTyped;
            CustomListModel<Operation> model = (CustomListModel<Operation>) listObjects.getModel();
            ArrayList<Operation> tempList = new ArrayList<>();
            ArrayList<Operation> list = model.getList();
            for (Operation operation : list) {
                if (operation.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(operation);
                }
            }
            setListObjectsModel(tempList);
            tfFilter.setText(currentFilter);
        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<Operation> tempList = new ArrayList<>();
                for (Operation operation : operations) {
                    if (operation.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                        tempList.add(operation);
                    }
                }
                setListObjectsModel(tempList);
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                operations.sort(Comparator.comparingInt(Operation::getId));
                setListObjectsModel(operations);
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_listObjectsKeyTyped

    private void btnCancelMouseClicked(MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        System.out.println(evt.getButton());
        if (evt.getButton() == 3) {
            try {
                int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Está seguro que desea actualizar todas las operaciones de la entidad?");
                if (showConfirmDialog == JOptionPane.OK_OPTION) {
                    operationsCRUD.updateAll();
                } else {
                    evt.consume();
                }
            } catch (SQLException ex) {
                Logger.getInstance().updateErrorLog(ex);
            }
        }
    }//GEN-LAST:event_btnCancelMouseClicked

    private void prepareViewToEdition() {
        lblDateKey.setEnabled(true);
        dcDateValue.setEnabled(true);
        lblAmmountKey.setEnabled(true);
        spAmmountValue.setEnabled(true);
        lblCostKey.setEnabled(true);
        spCostValue.setEnabled(true);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
        cbBilledValue.setEnabled(true);
        btnAdd.setVisible(false);
        checker.enableChecks();
    }

    protected void prepareViewToInsertion() {
        isInserting = true;
        lblDateKey.setEnabled(true);
        dcDateValue.setEnabled(true);
        dcDateValue.setDate(operationsCRUD.getLastOperationDate().getDate());
        lblAmmountKey.setEnabled(true);
        spAmmountValue.setEnabled(true);
        lblCostKey.setEnabled(true);
        spCostValue.setEnabled(true);
        cbBilledValue.setEnabled(true);
        cbBilledValue.setSelected(false);
        btnAdd.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        checker.enableChecks();
    }

    protected void refreshFrameView() throws SQLException {
        lblDateKey.setEnabled(false);
        dcDateValue.setEnabled(false);
        dcDateValue.setDate(operationsCRUD.getCurrentOperationDate().getDate());
        lblAmmountKey.setEnabled(false);
        spAmmountValue.setEnabled(false);
        spAmmountValue.setValue(0.0d);
        lblCostKey.setEnabled(false);
        spCostValue.setEnabled(false);
        spCostValue.setValue(0.0d);
        lblSingleCostKey.setEnabled(false);
        lblSingleCostValue.setEnabled(false);
        lblSingleCostValue.setText("");
        lblSingleCostMemoryValue.setText("");
        lblSingleCostMemoryValue.setText("");
        cbBilledValue.setEnabled(false);
        cbBilledValue.setSelected(false);
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        currentFilter = "";
        isInserting = false;
    }

    protected void fillCenterPanel() {
        mainPanel.removeAll();
        itemsPanel = checker.getPanel(mainPanel);
        mainPanel.add(itemsPanel);
        mainPanel.validate();
        pack();
        setListObjectsModel();
    }

    protected void setListObjectsModel() {
        lblTotalValue.setText(String.valueOf(operations.size()));
    }

    protected abstract void setListObjectsModel(ArrayList<Operation> tempList);

    protected abstract void save();

    protected abstract void update();


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected JPanel ammountAndCostPane;
    protected JButton btnAdd;
    protected JButton btnCancel;
    protected JButton btnDelete;
    protected JButton btnSave;
    protected JButton btnUpdate;
    protected JCheckBox cbBilledValue;
    protected JPanel centerPane;
    protected JPanel controlsPane;
    protected JPanel crudButtons;
    protected JPanel datePane;
    protected JDateChooser dcDateValue;
    protected JPanel detailsPane;
    protected Box.Filler filler1;
    protected JPanel filter;
    protected JPanel idPane;
    protected JScrollPane itemsPanel;
    protected JButton jButton1;
    protected JLabel jLabel10;
    protected JLabel jLabel2;
    protected JLabel jLabel20;
    protected JLabel jLabel22;
    protected JLabel jLabel6;
    protected JLabel jLabel8;
    protected JPanel jPanel1;
    protected JLabel lblAmmountKey;
    protected JLabel lblCostKey;
    protected JLabel lblDateKey;
    protected JLabel lblIDKey;
    protected JLabel lblIDValue;
    protected JLabel lblSingleCostKey;
    protected JLabel lblSingleCostMemoryValue;
    protected JLabel lblSingleCostValue;
    protected JLabel lblTotalKay;
    protected JLabel lblTotalValue;
    protected JPanel list;
    protected JList<Operation> listObjects;
    protected JPanel mainPanel;
    protected JPanel northPane;
    protected JPanel otherButtons;
    protected JPanel panel;
    protected JScrollPane scroll;
    protected JPanel southPane;
    protected JSpinner spAmmountValue;
    protected JSpinner spCostValue;
    protected JTextField tfFilter;
    protected JPanel total;
    // End of variables declaration//GEN-END:variables

}
