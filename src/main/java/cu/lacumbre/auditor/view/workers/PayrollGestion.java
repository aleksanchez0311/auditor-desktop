package cu.lacumbre.auditor.view.workers;

import cu.lacumbre.auditor.exceptions.EmptyWorkersException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cu.lacumbre.auditor.crud.DocumentsCRUD;
import cu.lacumbre.auditor.crud.PaymentRecordsCRUD;
import cu.lacumbre.auditor.crud.WorkersCRUD;
import cu.lacumbre.auditor.model.Attendance;
import cu.lacumbre.auditor.model.CustomDocument;
import cu.lacumbre.auditor.model.PaymentRecord;
import cu.lacumbre.auditor.model.Payroll;
import cu.lacumbre.auditor.view.custom.DisabledCheckboxRenderer;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.utils.tree.CustomTreeNode;
import java.sql.Connection;

public class PayrollGestion extends JDialog {

    private final DocumentsCRUD documentsCRUD;
    private final Attendance attendance;
    private Payroll payroll;
    private final WorkersCRUD workersCRUD;
    private final Connection connection;

    public PayrollGestion(DocumentsGestion parent, boolean modal, Connection connection, WorkersCRUD workersCRUD, DocumentsCRUD documentsCRUD, CustomTreeNode node) throws EmptyWorkersException, SQLException {
        super(parent, modal);
        this.workersCRUD = workersCRUD;
        this.documentsCRUD = documentsCRUD;
        this.connection = connection;
        this.attendance = (Attendance) documentsCRUD.get(CustomDocument.ATTENDANCE_REPORT);
        this.payroll = (Payroll) documentsCRUD.get(CustomDocument.PAYROLL);
        if (this.payroll == null) {
            generateDefaultPayroll();
        }
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new JPanel();
        center = new JScrollPane();
        tableAttendance = new CustomJTable();
        south = new JPanel();
        jPanel1 = new JPanel();
        jCheckBox1 = new JCheckBox();
        btnSave = new JButton();
        btnCancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Conntrol de asistencia del periodo");
        setMinimumSize(new Dimension(900, 500));
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setMinimumSize(new Dimension(880, 480));
        main.setPreferredSize(new Dimension(880, 480));
        main.setLayout(new BorderLayout(5, 5));

        center.setMinimumSize(new Dimension(870, 470));
        center.setPreferredSize(new Dimension(870, 470));

        tableAttendance.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableAttendance.setDefaultRenderer(Boolean.class, new DisabledCheckboxRenderer());
        center.setViewportView(tableAttendance);

        main.add(center, BorderLayout.CENTER);

        south.setLayout(new BoxLayout(south, BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

        jCheckBox1.setText("Aplicar Impuestos Salariales");
        jPanel1.add(jCheckBox1);

        south.add(jPanel1);

        btnSave.setText("Guardar");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        south.add(btnSave);

        btnCancel.setText("Cancelar");
        south.add(btnCancel);

        main.add(south, BorderLayout.SOUTH);

        getContentPane().add(main, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

    }//GEN-LAST:event_btnSaveActionPerformed

    private void generateDefaultPayroll() throws SQLException {
        PaymentRecordsCRUD paymentRecordsCRUD = new PaymentRecordsCRUD(connection, workersCRUD, attendance);
        documentsCRUD.save(new Payroll(CustomDocument.PAYROLL, false));
        for (PaymentRecord paymentRecord : paymentRecordsCRUD.getList()) {
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnCancel;
    private JButton btnSave;
    private JScrollPane center;
    private JCheckBox jCheckBox1;
    private JPanel jPanel1;
    private JPanel main;
    private JPanel south;
    private CustomJTable tableAttendance;
    // End of variables declaration//GEN-END:variables

}
