package cu.lacumbre.auditor.view.workers;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import cu.lacumbre.auditor.crud.WorkersCRUD;
import cu.lacumbre.auditor.model.Role;
import cu.lacumbre.auditor.model.Worker;
import cu.lacumbre.auditor.view.custom.CustomComboBoxModel;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_Staff;
import cu.lacumbre.utils.Logger;

public class StaffGestion extends JDialog {

    private final WorkersCRUD workersCRUD;

    public StaffGestion(Frame parent, WorkersCRUD wCrud, boolean modal) {
        super(parent, modal);
        this.workersCRUD = wCrud;
        initComponents();
    }

    public void updateModel() throws SQLException {
        tableStaff.setModel(new CustomTM_Staff(workersCRUD));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenuTable = new JPopupMenu();
        btnDetails = new JMenuItem();
        btnSelectAll = new JMenuItem();
        btnEliminar = new JMenuItem();
        cbxRoles = new JComboBox<>();
        main = new JPanel();
        center = new JScrollPane();
        tableStaff = new CustomJTable();
        south = new JPanel();
        btnNew = new JButton();
        btnClose = new JButton();

        btnDetails.setText("Detalles");
        btnDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDetailsActionPerformed(evt);
            }
        });
        popMenuTable.add(btnDetails);

        btnSelectAll.setText("Seleccionar");
        btnSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });
        popMenuTable.add(btnSelectAll);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        popMenuTable.add(btnEliminar);

        cbxRoles.setModel(new CustomComboBoxModel<>(workersCRUD.getRolesCRUD().getList()));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administración del Personal");
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setLayout(new BorderLayout(5, 5));

        center.setComponentPopupMenu(popMenuTable);

        tableStaff.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        tableStaff.setModel(new CustomTM_Staff(workersCRUD));
        DefaultCellEditor editor = new DefaultCellEditor(cbxRoles);
        tableStaff.getColumnModel().getColumn(2).setCellEditor(editor);
        tableStaff.setComponentPopupMenu(popMenuTable);
        center.setViewportView(tableStaff);

        main.add(center, BorderLayout.CENTER);

        south.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnNew.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnNew.setText("Nuevo");
        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        south.add(btnNew);

        btnClose.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnClose.setText("Cerrar");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        south.add(btnClose);

        main.add(south, BorderLayout.SOUTH);

        getContentPane().add(main, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnNewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        NewWorker dialog = new NewWorker(this, workersCRUD, false);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSelectAllActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSelectAllActionPerformed
        tableStaff.selectAll();
    }//GEN-LAST:event_btnSelectAllActionPerformed

    private void btnEliminarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int[] selectedRows = tableStaff.getSelectedRows();
        CustomTM_Staff mtmws = (CustomTM_Staff) tableStaff.getModel();
        ArrayList<Worker> workers = mtmws.getWorkers();
        try {
            for (int selectedRow : selectedRows) {
                workersCRUD.delete(workers.get(selectedRow));
            }
            tableStaff.setModel(new CustomTM_Staff(workersCRUD));
            JOptionPane.showMessageDialog(this, "Trabajador(es) eliminado(s) correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog( ex);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnDetailsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDetailsActionPerformed
        int[] selectedRows = tableStaff.getSelectedRows();
        CustomTM_Staff mtmws = (CustomTM_Staff) tableStaff.getModel();
        ArrayList<Worker> workers = mtmws.getWorkers();
        for (int selectedRow : selectedRows) {
            Worker selectedWorker = workers.get(selectedRow);
            JOptionPane.showMessageDialog(this, "CI: " + selectedWorker.getDni() + "\nFecha de Ingreso: " + selectedWorker.getEnrollDate().toString() + "\nNombre: " + selectedWorker.getFullName() + "\nDirección: " + selectedWorker.getAddress(), "Detalles de " + selectedWorker.toString(), JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDetailsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnClose;
    private JMenuItem btnDetails;
    private JMenuItem btnEliminar;
    private JButton btnNew;
    private JMenuItem btnSelectAll;
    private JComboBox<Role> cbxRoles;
    private JScrollPane center;
    private JPanel main;
    private JPopupMenu popMenuTable;
    private JPanel south;
    private CustomJTable tableStaff;
    // End of variables declaration//GEN-END:variables
}
