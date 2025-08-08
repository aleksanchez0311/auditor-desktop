package cu.lacumbre.auditor.view.merchandise;

import cu.lacumbre.auditor.Setup;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;

import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.model.Expense;
import cu.lacumbre.auditor.view.custom.CustomListModel;
import cu.lacumbre.utils.Logger;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Comparator;

public class ExpensesGestion extends JDialog {

    private final ItemsCRUD itemsCRUD;
    private String currentFilter;

    public ExpensesGestion(JFrame parent, ItemsCRUD itemsCRUD, boolean lockParent) {
        super(parent, lockParent);
        this.itemsCRUD = itemsCRUD;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlsPane = new JPanel();
        northPane = new JPanel();
        idPanel = new JPanel();
        lblIDKey = new JLabel();
        jLabel10 = new JLabel();
        lblIDValue = new JLabel();
        desciptionPane = new JPanel();
        jLabel8 = new JLabel();
        lblDescKey = new JLabel();
        jLabel6 = new JLabel();
        tfDescValue = new JTextField();
        jLabel2 = new JLabel();
        costPane = new JPanel();
        jLabel1 = new JLabel();
        lblCostKey = new JLabel();
        jLabel5 = new JLabel();
        spCostValue = new JSpinner();
        jLabel11 = new JLabel();
        centerPane = new JPanel();
        jLabel12 = new JLabel();
        filter = new JPanel();
        tfFilter = new JTextField();
        jButton1 = new JButton();
        jScrollPane1 = new JScrollPane();
        listObjects = new JList<>();
        jLabel4 = new JLabel();
        southPane = new JPanel();
        crudButtons = new JPanel();
        btnSave = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();
        otherButtons = new JPanel();
        btnAdd = new JButton();
        btnCancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administrar Gastos");
        getContentPane().setLayout(new BorderLayout(6, 6));

        controlsPane.setEnabled(false);
        controlsPane.setMinimumSize(new Dimension(600, 600));
        controlsPane.setPreferredSize(new Dimension(600, 600));
        controlsPane.setLayout(new BorderLayout(6, 6));

        northPane.setLayout(new GridLayout(3, 1, 6, 6));

        idPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 6));

        lblIDKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblIDKey.setText("ID:");
        lblIDKey.setEnabled(false);
        idPanel.add(lblIDKey);

        jLabel10.setText(" ");
        jLabel10.setEnabled(false);
        idPanel.add(jLabel10);

        lblIDValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblIDValue.setText(" ");
        lblIDValue.setEnabled(false);
        idPanel.add(lblIDValue);

        northPane.add(idPanel);

        desciptionPane.setLayout(new BoxLayout(desciptionPane, BoxLayout.LINE_AXIS));

        jLabel8.setText("   ");
        jLabel8.setEnabled(false);
        desciptionPane.add(jLabel8);

        lblDescKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblDescKey.setText("Descripción:");
        lblDescKey.setEnabled(false);
        desciptionPane.add(lblDescKey);

        jLabel6.setText("   ");
        jLabel6.setEnabled(false);
        desciptionPane.add(jLabel6);

        tfDescValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        tfDescValue.setHorizontalAlignment(JTextField.RIGHT);
        tfDescValue.setEnabled(false);
        desciptionPane.add(tfDescValue);

        jLabel2.setText("   ");
        jLabel2.setEnabled(false);
        desciptionPane.add(jLabel2);

        northPane.add(desciptionPane);

        costPane.setLayout(new BoxLayout(costPane, BoxLayout.LINE_AXIS));

        jLabel1.setText("   ");
        jLabel1.setEnabled(false);
        costPane.add(jLabel1);

        lblCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblCostKey.setText("Costo:");
        lblCostKey.setEnabled(false);
        costPane.add(lblCostKey);

        jLabel5.setText("   ");
        jLabel5.setEnabled(false);
        costPane.add(jLabel5);

        spCostValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spCostValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spCostValue.setEnabled(false);
        spCostValue.setValue(0.0d);
        costPane.add(spCostValue);

        jLabel11.setText("   ");
        jLabel11.setEnabled(false);
        costPane.add(jLabel11);

        northPane.add(costPane);

        controlsPane.add(northPane, BorderLayout.NORTH);

        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.LINE_AXIS));

        jLabel12.setText("   ");
        centerPane.add(jLabel12);

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

        centerPane.add(filter);

        listObjects.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        listObjects.setModel(new CustomListModel(getList()));
        listObjects.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                listObjectsKeyTyped(evt);
            }
        });
        listObjects.addListSelectionListener(this::listObjectsValueChanged);
        jScrollPane1.setViewportView(listObjects);

        centerPane.add(jScrollPane1);

        jLabel4.setText("   ");
        centerPane.add(jLabel4);

        controlsPane.add(centerPane, BorderLayout.CENTER);

        southPane.setLayout(new GridLayout(2, 1, 6, 6));

        crudButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));

        btnSave.setVisible(false);
        btnSave.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSave.setText("Guardar");
        btnSave.setNextFocusableComponent(listObjects);
        btnSave.addActionListener(this::btnSaveActionPerformed);
        crudButtons.add(btnSave);

        btnUpdate.setVisible(false);
        btnUpdate.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setText("Modificar");
        btnUpdate.setNextFocusableComponent(tfDescValue);
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        crudButtons.add(btnUpdate);

        btnDelete.setVisible(false);
        btnDelete.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setText("Eliminar");
        btnDelete.setNextFocusableComponent(listObjects);
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        crudButtons.add(btnDelete);

        southPane.add(crudButtons);

        otherButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));

        btnAdd.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setText("Nuevo");
        btnAdd.setNextFocusableComponent(tfDescValue);
        btnAdd.addActionListener(this::btnAddActionPerformed);
        otherButtons.add(btnAdd);

        btnCancel.setVisible(false);
        btnCancel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.setNextFocusableComponent(listObjects);
        btnCancel.addActionListener(this::btnCancelActionPerformed);
        otherButtons.add(btnCancel);

        southPane.add(otherButtons);

        controlsPane.add(southPane, BorderLayout.SOUTH);

        getContentPane().add(controlsPane, BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        prepareViewToInsertion();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        refreshFrameView();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            String description = tfDescValue.getText();
            double cost = (double) spCostValue.getValue();
            int necesaryID = itemsCRUD.getNextId(Setup.SUBCATEGORIA_GASTO);
            Expense expense = new Expense(necesaryID, description, itemsCRUD.getMeasureUnitsCRUD().get(1), cost, necesaryID, false);
            itemsCRUD.save(expense);
            int option = JOptionPane.showConfirmDialog(this, "Materia Prima registrada correctamente.\n ¿Desea agregar más?", "Confirmación", JOptionPane.YES_NO_OPTION);
            refreshFrameView();
            if (option == 0) {
                prepareViewToInsertion();
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            String description = tfDescValue.getText();
            double cost = (double) spCostValue.getValue();
            Expense expense = (Expense) listObjects.getSelectedValue();
            expense.setDescription(description);
            expense.setCost(cost);
            itemsCRUD.update(expense);
            JOptionPane.showMessageDialog(this, "Materia Prima modificada correctamente.");
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            ArrayList<Expense> selectedItems = (ArrayList<Expense>) listObjects.getSelectedValuesList();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar la(s) Materia(s) Prima(s) elegida(s) y todas sus operaciones?", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                itemsCRUD.delete(selectedItems);
            }
            JOptionPane.showMessageDialog(this, "Materia(s) Prima(s) eliminada(s) correctamente");
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (!evt.getValueIsAdjusting()) {
            Expense expense = (Expense) listObjects.getSelectedValue();
            if (expense != null) {
                prepareViewToEdition();
                lblIDValue.setText(expense.getId() + "");
                tfDescValue.setText(expense.getDescription());
                spCostValue.setValue(expense.getCost());
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void listObjectsKeyTyped(KeyEvent evt) {//GEN-FIRST:event_listObjectsKeyTyped
        currentFilter = tfFilter.getText();
        char charTyped = evt.getKeyChar();
        if (Character.isAlphabetic(charTyped) || Character.isWhitespace(charTyped) || Character.isDigit(charTyped)) {
            currentFilter = currentFilter + charTyped;
            CustomListModel<Expense> model = (CustomListModel<Expense>) listObjects.getModel();
            ArrayList<Expense> tempList = new ArrayList<>();
            ArrayList<Expense> expenses = model.getList();
            for (Expense expense : expenses) {
                if (expense.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(expense);
                }
            }
            listObjects.setModel(new CustomListModel<>(tempList));
            tfFilter.setText(currentFilter);
        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<Expense> tempList = new ArrayList<>();
                for (Expense expense : (ArrayList<Expense>) getList()) {
                    if (expense.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                        tempList.add(expense);
                    }
                }
                listObjects.setModel(new CustomListModel(tempList));
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                ArrayList<Expense> expenses = getList();
                expenses.sort(Comparator.comparingInt(Expense::getId));
                listObjects.setModel(new CustomListModel(expenses));
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_listObjectsKeyTyped

    private void prepareViewToEdition() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        spCostValue.setEnabled(true);
        lblCostKey.setEnabled(true);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
        btnAdd.setVisible(false);
        crudButtons.setVisible(true);
    }

    private void prepareViewToInsertion() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        spCostValue.setEnabled(true);
        lblCostKey.setEnabled(true);
        btnAdd.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        crudButtons.setVisible(true);

    }

    private void refreshFrameView() {
        listObjects.setModel(new CustomListModel(getList()));
        tfDescValue.setText("");
        tfDescValue.setEnabled(false);
        lblDescKey.setEnabled(false);
        lblIDValue.setText("");
        spCostValue.setEnabled(false);
        lblCostKey.setEnabled(false);
        spCostValue.setValue(0.0d);
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        crudButtons.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAdd;
    private JButton btnCancel;
    private JButton btnDelete;
    private JButton btnSave;
    private JButton btnUpdate;
    private JPanel centerPane;
    private JPanel controlsPane;
    private JPanel costPane;
    private JPanel crudButtons;
    private JPanel desciptionPane;
    private JPanel filter;
    private JPanel idPanel;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel8;
    private JScrollPane jScrollPane1;
    private JLabel lblCostKey;
    private JLabel lblDescKey;
    private JLabel lblIDKey;
    private JLabel lblIDValue;
    private JList<Expense> listObjects;
    private JPanel northPane;
    private JPanel otherButtons;
    private JPanel southPane;
    private JSpinner spCostValue;
    private JTextField tfDescValue;
    private JTextField tfFilter;
    // End of variables declaration//GEN-END:variables

    private ArrayList<Expense> getList() {
        return itemsCRUD.getList(Setup.CATEGORIA_GASTO, true);
    }

}
