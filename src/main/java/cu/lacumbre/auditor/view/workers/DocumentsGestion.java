package cu.lacumbre.auditor.view.workers;

import cu.lacumbre.auditor.exceptions.EmptyWorkersException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.crud.DocumentsCRUD;
import cu.lacumbre.auditor.crud.PaymentRecordsCRUD;
import cu.lacumbre.auditor.crud.WorkersCRUD;
import cu.lacumbre.auditor.model.Attendance;
import cu.lacumbre.auditor.model.CustomDocument;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.utils.MonthlyPeriod;
import cu.lacumbre.auditor.model.PaymentRecord;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_Documents;
import cu.lacumbre.auditor.view.utils.tree.CustomTreeNode;
import cu.lacumbre.auditor.view.utils.tree.MyTreeModel;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.TableModel;

public class DocumentsGestion extends JDialog {

    private static final int TYPE = MyTreeModel.MONTH_LEAVES;
    private final WorkersCRUD workersCRUD;
    private final Entity entity;
    private DocumentsCRUD documentsCRUD;
    private final Connection connection;
    private HashMap<CustomDocument, PaymentRecordsCRUD> documentPaymentRecordsCRUDs;
    private HashMap<CustomDocument, ArrayList<PaymentRecord>> documentPaymentRecords;

    public DocumentsGestion(Frame parent, boolean modal, Connection connection, WorkersCRUD workersCRUD) throws SQLException {
        super(parent, modal);
        this.connection = connection;
        this.workersCRUD = workersCRUD;
        this.entity = EntitySelector.currentEntity;
        this.documentsCRUD = new DocumentsCRUD(connection, new MonthlyPeriod(entity.getCurrentDay()));
        this.documentPaymentRecordsCRUDs = new HashMap<>();
        this.documentPaymentRecords = new HashMap<>();
        for (Map.Entry<Integer, CustomDocument> entry : documentsCRUD.getMap().entrySet()) {
            CustomDocument customDocument = entry.getValue();
            try {
                documentPaymentRecordsCRUDs.put(customDocument, new PaymentRecordsCRUD(connection, workersCRUD, customDocument));
                documentPaymentRecords.put(customDocument, new PaymentRecordsCRUD(connection, workersCRUD, customDocument).getList());
            } catch (SQLException ex) {
                Logger.getInstance().updateErrorLog(ex);
            }
        }
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenuTable = new JPopupMenu();
        btnFill = new JMenuItem();
        btnAdjust = new JMenuItem();
        btnPayroll = new JMenuItem();
        btnSelectAll = new JMenuItem();
        btnDelete = new JMenuItem();
        btnDispose = new JMenuItem();
        main = new JPanel();
        west = new JScrollPane();
        periodsTree = new JTree();
        center = new JScrollPane();
        south = new JPanel();
        btnNew = new JButton();
        btnClose = new JButton();

        btnFill.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        btnFill.setText("Rellenar");
        btnFill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnFillActionPerformed(evt);
            }
        });
        popMenuTable.add(btnFill);

        btnAdjust.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_DOWN_MASK));
        btnAdjust.setText("Ajustar");
        btnAdjust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAdjustActionPerformed(evt);
            }
        });
        popMenuTable.add(btnAdjust);

        btnPayroll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        btnPayroll.setText("Generar Nomina");
        btnPayroll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnPayrollActionPerformed(evt);
            }
        });
        popMenuTable.add(btnPayroll);

        btnSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        btnSelectAll.setText("Seleccionar Todo");
        btnSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });
        popMenuTable.add(btnSelectAll);

        btnDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        btnDelete.setText("Eliminar");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        popMenuTable.add(btnDelete);

        btnDispose.setVisible(false);
        btnDispose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        btnDispose.setText("Dispose");
        btnDispose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDisposeActionPerformed(evt);
            }
        });
        popMenuTable.add(btnDispose);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administración de documentos de pago");
        addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(WindowEvent evt) {
            }
        });
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setLayout(new BorderLayout(5, 5));

        periodsTree.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        periodsTree.setModel(new MyTreeModel(TYPE));
        periodsTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent evt) {
                periodsTreeValueChanged(evt);
            }
        });
        west.setViewportView(periodsTree);

        main.add(west, BorderLayout.WEST);

        center.setComponentPopupMenu(popMenuTable);

        tableDocuments.setModel(new CustomTM_Documents(connection, documentsCRUD, documentPaymentRecords));
        autoselectCurrentPeriodLeaf();
        tableDocuments.setComponentPopupMenu(popMenuTable);
        center.setViewportView(tableDocuments);

        main.add(center, BorderLayout.CENTER);

        south.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnNew.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnNew.setText("Nuevo Reporte de Pago");
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

    private void btnSelectAllActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSelectAllActionPerformed
        tableDocuments.selectAll();
    }//GEN-LAST:event_btnSelectAllActionPerformed

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int[] selectedRows = tableDocuments.getSelectedRows();
        try {
            for (int selectedRow : selectedRows) {
                documentsCRUD.delete(documentsCRUD.getList().get(selectedRow));
            }
            tableDocuments.setModel(new CustomTM_Documents(connection, documentsCRUD, documentPaymentRecords));
            JOptionPane.showMessageDialog(this, "Documento(s) eliminado(s) correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void periodsTreeValueChanged(TreeSelectionEvent evt) {//GEN-FIRST:event_periodsTreeValueChanged
        reloadTable();
    }//GEN-LAST:event_periodsTreeValueChanged

    private void btnNewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        Attendance newDocument = new Attendance(CustomDocument.ATTENDANCE_REPORT, false);
        try {
            documentsCRUD.save(newDocument);
            reloadTable();
            JOptionPane.showMessageDialog(this, "Reporte de pago creado satisfactoriamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnFillActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnFillActionPerformed
        int selectedRow = tableDocuments.getSelectedRow();
        if (selectedRow > -1) {
            Object[] selection = periodsTree.getSelectionPath().getPath();
            if (selection.length > 0) {
                for (Object object : selection) {
                    CustomTreeNode node = (CustomTreeNode) object;
                    if (node.isLeaf()) {
                        try {
                            PaymentRecordsCRUD paymentRecordsCRUD = documentPaymentRecordsCRUDs.get((Attendance) documentsCRUD.getList().get(selectedRow));
                            AttendanceGestion dialog = new AttendanceGestion(this, false, connection, workersCRUD, paymentRecordsCRUD, (Attendance) documentsCRUD.getList().get(selectedRow), (CustomTreeNode) periodsTree.getSelectionPath().getLastPathComponent(), true);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        } catch (SQLException | EmptyWorkersException ex) {
                            Logger.getInstance().updateErrorLog(ex);
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un documento para rellenar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnFillActionPerformed

    private void btnDisposeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDisposeActionPerformed
        dispose();
    }//GEN-LAST:event_btnDisposeActionPerformed

    private void formWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        reloadTable();
        checkRefillerButtonsEnablility();
        checkBtnNewEnablility();
    }//GEN-LAST:event_formWindowGainedFocus

    private void btnAdjustActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAdjustActionPerformed
        int selectedRow = tableDocuments.getSelectedRow();
        if (selectedRow > -1) {
            Object[] selection = periodsTree.getSelectionPath().getPath();
            if (selection.length > 0) {
                for (Object object : selection) {
                    CustomTreeNode node = (CustomTreeNode) object;
                    if (node.isLeaf()) {
                        try {
                            PaymentRecordsCRUD paymentRecordsCRUD = documentPaymentRecordsCRUDs.get((Attendance) documentsCRUD.getList().get(selectedRow));
                            AttendanceGestion dialog = new AttendanceGestion(this, false, connection, workersCRUD, paymentRecordsCRUD, (Attendance) documentsCRUD.getList().get(selectedRow), (CustomTreeNode) periodsTree.getSelectionPath().getLastPathComponent(), false);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        } catch (SQLException | EmptyWorkersException ex) {
                            Logger.getInstance().updateErrorLog(ex);
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un documento para ajustar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAdjustActionPerformed

    private void btnPayrollActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnPayrollActionPerformed
        int selectedRow = tableDocuments.getSelectedRow();
        if (selectedRow > -1) {
            Object[] selection = periodsTree.getSelectionPath().getPath();
            if (selection.length > 0) {
                for (Object object : selection) {
                    CustomTreeNode node = (CustomTreeNode) object;
                    if (node.isLeaf()) {
                        try {
                            PayrollGestion dialog = new PayrollGestion(this, false, connection, workersCRUD, documentsCRUD, (CustomTreeNode) periodsTree.getSelectionPath().getLastPathComponent());
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                        } catch (SQLException | EmptyWorkersException ex) {
                            Logger.getInstance().updateErrorLog(ex);
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un documento para generar su nomina.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnPayrollActionPerformed

    private void checkRefillerButtonsEnablility() {
        int[] selectedRows = tableDocuments.getSelectedRows();
        if (selectedRows.length > 0) {
            if (selectedRows.length == 1) {
                if (!documentsCRUD.getList().isEmpty()) {
                    Attendance selectedDocument = (Attendance) documentsCRUD.getList().get(selectedRows[0]);
                    btnFill.setEnabled(!selectedDocument.isCompleted());
                    btnAdjust.setEnabled(!selectedDocument.isCompleted());
                    btnPayroll.setEnabled(selectedDocument.isCompleted());
                }
            }
        }
    }

    private void checkBtnNewEnablility() {
        int[] selectedRows = tableDocuments.getSelectedRows();
        if (selectedRows.length > 0) {
            if (selectedRows.length == 1) {
                if (!documentsCRUD.getList().isEmpty()) {
                    Attendance selectedDocument = (Attendance) documentsCRUD.getList().get(selectedRows[0]);
                    btnNew.setEnabled(!selectedDocument.getDescription().equals(CustomDocument.ATTENDANCE_REPORT));
                }
            }
        } else {
            btnNew.setEnabled(true);
        }
    }

    private void autoselectFirstTableRow() {
        if (tableDocuments.getRowCount() > 0) {
            tableDocuments.setRowSelectionInterval(0, 0);
        }
        checkRefillerButtonsEnablility();
        checkBtnNewEnablility();
    }

    private void autoselectCurrentPeriodLeaf() {
        MyTreeModel model = (MyTreeModel) periodsTree.getModel();
        ArrayList<CustomTreeNode> path = new ArrayList<>();
        CustomTreeNode node = model.getCurrentLeaf();
        while (node != null) {
            path.add(node);
            node = (CustomTreeNode) node.getParent();
        }
        CustomTreeNode[] reversedPath = new CustomTreeNode[path.size()];
        int pos = path.size() - 1;
        for (int i = 0; i < path.size(); i++) {
            reversedPath[pos] = path.get(i);
            pos--;
        }
        TreePath tp = new TreePath(reversedPath);
        periodsTree.setSelectionPath(tp);
        autoselectFirstTableRow();
    }

    protected void reloadTable() {
        Object[] selection = periodsTree.getSelectionPath().getPath();
        if (selection.length > 0) {
            for (Object object : selection) {
                CustomTreeNode node = (CustomTreeNode) object;
                if (node.isLeaf()) {
                    try {
                        documentsCRUD = new DocumentsCRUD(connection, node.getPeriod());
                        documentPaymentRecordsCRUDs = new HashMap<>();
                        documentPaymentRecords = new HashMap<>();
                        for (Map.Entry<Integer, CustomDocument> entry : documentsCRUD.getMap().entrySet()) {
                            CustomDocument customDocument = entry.getValue();
                            try {
                                documentPaymentRecordsCRUDs.put(customDocument, new PaymentRecordsCRUD(connection, workersCRUD, customDocument));
                                documentPaymentRecords.put(customDocument, new PaymentRecordsCRUD(connection, workersCRUD, customDocument).getList());
                            } catch (SQLException ex) {
                                Logger.getInstance().updateErrorLog(ex);
                            }
                        }
                        tableDocuments.setModel(new CustomTM_Documents(connection, documentsCRUD, documentPaymentRecords));
                        autoselectFirstTableRow();
                    } catch (SQLException ex) {
                        Logger.getInstance().updateErrorLog(ex);
                    }
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JMenuItem btnAdjust;
    private JButton btnClose;
    private JMenuItem btnDelete;
    private JMenuItem btnDispose;
    private JMenuItem btnFill;
    private JButton btnNew;
    private JMenuItem btnPayroll;
    private JMenuItem btnSelectAll;
    private JScrollPane center;
    private JPanel main;
    private JTree periodsTree;
    private JPopupMenu popMenuTable;
    private JPanel south;
    private final CustomJTable tableDocuments = new CustomJTable();
    private JScrollPane west;
    // End of variables declaration//GEN-END:variables
}
