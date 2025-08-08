package cu.lacumbre.auditor.view.workers;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cu.lacumbre.auditor.crud.RolesCRUD;
import cu.lacumbre.auditor.model.Role;
import cu.lacumbre.auditor.view.custom.CustomListModel;
import cu.lacumbre.utils.Logger;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;

public class RolesGestion extends JDialog {

    private RolesCRUD rolesCRUD;
    private String currentFilter;
    private long lastFiterKeyEventTime = Instant.now().toEpochMilli();

    public RolesGestion(JFrame parent, boolean lockParent, Connection connection) {
        super(parent, lockParent);
        try {
            rolesCRUD = new RolesCRUD(connection);
            initComponents();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filter = new JPanel();
        tfFilter = new JTextField();
        jButton1 = new JButton();
        main = new JPanel();
        west = new JPanel();
        id = new JPanel();
        lblIDKey = new JLabel();
        jLabel10 = new JLabel();
        lblIDValue = new JLabel();
        filler1 = new Box.Filler(new Dimension(280, 0), new Dimension(280, 0), new Dimension(280, 32767));
        desc = new JPanel();
        lblDescKey = new JLabel();
        jLabel6 = new JLabel();
        tfDescValue = new JTextField();
        pay = new JPanel();
        lblPaymentKey = new JLabel();
        jLabel7 = new JLabel();
        spnPaymentValue = new JSpinner();
        time = new JPanel();
        lblDaysToWorkKey = new JLabel();
        jLabel5 = new JLabel();
        spnDaysToWorkValue = new JSpinner();
        jLabel4 = new JLabel();
        lblHoursToWorkKey = new JLabel();
        jLabel12 = new JLabel();
        spnHoursToWorkValue = new JSpinner();
        east = new JScrollPane();
        listObjects = new JList<>();
        south = new JPanel();
        btnSave = new JButton();
        btnCancel = new JButton();
        btnAdd = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();

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

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion de Roles");
        setIconImage(null);
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setLayout(new BorderLayout());

        west.setLayout(new GridLayout(10, 0, 0, 5));

        id.setLayout(new BoxLayout(id, BoxLayout.LINE_AXIS));

        lblIDKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblIDKey.setText("ID:");
        lblIDKey.setEnabled(false);
        id.add(lblIDKey);

        jLabel10.setText(" ");
        jLabel10.setEnabled(false);
        id.add(jLabel10);

        lblIDValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblIDValue.setText(" ");
        lblIDValue.setEnabled(false);
        id.add(lblIDValue);
        id.add(filler1);

        west.add(id);

        desc.setLayout(new BoxLayout(desc, BoxLayout.LINE_AXIS));

        lblDescKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblDescKey.setText("Descripción:");
        lblDescKey.setEnabled(false);
        desc.add(lblDescKey);

        jLabel6.setText("   ");
        jLabel6.setEnabled(false);
        desc.add(jLabel6);

        tfDescValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        tfDescValue.setEnabled(false);
        desc.add(tfDescValue);

        west.add(desc);

        pay.setLayout(new BoxLayout(pay, BoxLayout.LINE_AXIS));

        lblPaymentKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblPaymentKey.setText("Salario Básico:");
        lblPaymentKey.setEnabled(false);
        pay.add(lblPaymentKey);

        jLabel7.setText("   ");
        pay.add(jLabel7);

        spnPaymentValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spnPaymentValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spnPaymentValue.setEnabled(false);
        pay.add(spnPaymentValue);

        west.add(pay);

        time.setLayout(new BoxLayout(time, BoxLayout.LINE_AXIS));

        lblDaysToWorkKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblDaysToWorkKey.setText("Días de Trabajo:");
        lblDaysToWorkKey.setEnabled(false);
        time.add(lblDaysToWorkKey);

        jLabel5.setText("   ");
        jLabel5.setEnabled(false);
        time.add(jLabel5);

        spnDaysToWorkValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spnDaysToWorkValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spnDaysToWorkValue.setEnabled(false);
        time.add(spnDaysToWorkValue);

        jLabel4.setText("   ");
        jLabel4.setEnabled(false);
        time.add(jLabel4);

        lblHoursToWorkKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblHoursToWorkKey.setText("Horas por Día:");
        lblHoursToWorkKey.setEnabled(false);
        time.add(lblHoursToWorkKey);

        jLabel12.setText("   ");
        time.add(jLabel12);

        spnHoursToWorkValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spnHoursToWorkValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spnHoursToWorkValue.setEnabled(false);
        time.add(spnHoursToWorkValue);

        west.add(time);

        main.add(west, BorderLayout.WEST);

        east.setBorder(BorderFactory.createTitledBorder(null, "Roles:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14))); // NOI18N

        listObjects.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        listObjects.setModel(new CustomListModel(rolesCRUD.getList()));
        listObjects.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                listObjectsKeyTyped(evt);
            }
        });
        listObjects.addListSelectionListener(this::listObjectsValueChanged);
        east.setViewportView(listObjects);

        main.add(east, BorderLayout.EAST);

        south.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnSave.setVisible(false);
        btnSave.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSave.setText("Guardar");
        btnSave.addActionListener(this::btnSaveActionPerformed);
        south.add(btnSave);

        btnCancel.setVisible(false);
        btnCancel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(this::btnCancelActionPerformed);
        south.add(btnCancel);

        btnAdd.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setText("Nuevo");
        btnAdd.addActionListener(this::btnAddActionPerformed);
        south.add(btnAdd);

        btnUpdate.setVisible(false);
        btnUpdate.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setText("Modificar");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        south.add(btnUpdate);

        btnDelete.setVisible(false);
        btnDelete.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setText("Eliminar");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        south.add(btnDelete);

        main.add(south, BorderLayout.SOUTH);

        getContentPane().add(main, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            String description = tfDescValue.getText();
            double paymemt = (double) spnPaymentValue.getValue();
            double daysToWork = (double) spnDaysToWorkValue.getValue();
            double hoursToWork = (double) spnHoursToWorkValue.getValue();
            rolesCRUD.save(new Role(description, paymemt, daysToWork, hoursToWork));
            refreshFrameView();
            int option = JOptionPane.showConfirmDialog(this, "Cargo registrado correctamente.\n ¿Desea agregar más?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                prepareViewToInsertion();
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        refreshFrameView();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        prepareViewToInsertion();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            Role role = (Role) listObjects.getSelectedValue();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea modificar el cargo elegido?", "Confirmar Modificación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                role.setDescription(tfDescValue.getText());
                role.setPayment((double) spnPaymentValue.getValue());
                rolesCRUD.update(role);
                refreshFrameView();
                JOptionPane.showMessageDialog(this, "Cargo modificado correctamente");
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            Role role = (Role) listObjects.getSelectedValue();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar el cargo elegido?", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                rolesCRUD.delete(role);
                JOptionPane.showMessageDialog(this, "Cargo eliminado correctamente");
                refreshFrameView();
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void listObjectsKeyTyped(KeyEvent evt) {//GEN-FIRST:event_listObjectsKeyTyped
        currentFilter = tfFilter.getText();
        char charTyped = evt.getKeyChar();
        if (Character.isAlphabetic(charTyped) || Character.isWhitespace(charTyped) || Character.isDigit(charTyped)) {
            currentFilter = currentFilter + charTyped;
            CustomListModel<Role> model = (CustomListModel<Role>) listObjects.getModel();
            ArrayList<Role> tempList = new ArrayList<>();
            ArrayList<Role> list = model.getList();
            for (Role role : list) {
                if (role.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(role);
                }
            }
            listObjects.setModel(new CustomListModel(tempList));
            tfFilter.setText(currentFilter);
        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<Role> tempList = new ArrayList<>();
                for (Role role : rolesCRUD.getList()) {
                    if (role.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                        tempList.add(role);
                    }
                }
                listObjects.setModel(new CustomListModel(tempList));
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                ArrayList<Role> role = rolesCRUD.getList();
                role.sort(Comparator.comparingInt(Role::getId));
                listObjects.setModel(new CustomListModel(role));
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_listObjectsKeyTyped

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (!evt.getValueIsAdjusting()) {
            Role role = (Role) listObjects.getSelectedValue();
            if (role != null) {
                prepareViewToEdition();
                lblIDValue.setText(role.getId() + "");
                tfDescValue.setText(role.getDescription());
                spnPaymentValue.setValue(role.getPayment());
                spnDaysToWorkValue.setValue(role.getDaysToWork());
                spnHoursToWorkValue.setValue(role.getHoursToWork());
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void prepareViewToEdition() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        spnPaymentValue.setEnabled(true);
        lblPaymentKey.setEnabled(true);
        spnDaysToWorkValue.setEnabled(true);
        lblDaysToWorkKey.setEnabled(true);
        spnHoursToWorkValue.setEnabled(true);
        lblHoursToWorkKey.setEnabled(true);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
        btnAdd.setVisible(false);
    }

    private void prepareViewToInsertion() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        spnPaymentValue.setEnabled(true);
        lblPaymentKey.setEnabled(true);
        spnDaysToWorkValue.setEnabled(true);
        lblDaysToWorkKey.setEnabled(true);
        spnHoursToWorkValue.setEnabled(true);
        lblDaysToWorkKey.setEnabled(true);
        btnAdd.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
    }

    private void refreshFrameView() {
        listObjects.setModel(new CustomListModel(rolesCRUD.getList()));
        tfDescValue.setText("");
        tfDescValue.setEnabled(false);
        lblDescKey.setEnabled(false);
        spnPaymentValue.setValue(0.0d);
        spnPaymentValue.setEnabled(false);
        lblPaymentKey.setEnabled(false);
        spnDaysToWorkValue.setValue(0.0d);
        spnDaysToWorkValue.setEnabled(false);
        lblDaysToWorkKey.setEnabled(false);
        spnHoursToWorkValue.setValue(0.0d);
        spnHoursToWorkValue.setEnabled(false);
        lblHoursToWorkKey.setEnabled(false);
        lblIDValue.setText("");
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAdd;
    private JButton btnCancel;
    private JButton btnDelete;
    private JButton btnSave;
    private JButton btnUpdate;
    private JPanel desc;
    private JScrollPane east;
    private Box.Filler filler1;
    private JPanel filter;
    private JPanel id;
    private JButton jButton1;
    private JLabel jLabel10;
    private JLabel jLabel12;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel lblDaysToWorkKey;
    private JLabel lblDescKey;
    private JLabel lblHoursToWorkKey;
    private JLabel lblIDKey;
    private JLabel lblIDValue;
    private JLabel lblPaymentKey;
    private JList<Role> listObjects;
    private JPanel main;
    private JPanel pay;
    private JPanel south;
    private JSpinner spnDaysToWorkValue;
    private JSpinner spnHoursToWorkValue;
    private JSpinner spnPaymentValue;
    private JTextField tfDescValue;
    private JTextField tfFilter;
    private JPanel time;
    private JPanel west;
    // End of variables declaration//GEN-END:variables
}
