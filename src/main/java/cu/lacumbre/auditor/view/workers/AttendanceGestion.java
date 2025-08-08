package cu.lacumbre.auditor.view.workers;

import cu.lacumbre.auditor.exceptions.EmptyWorkersException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.PaymentRecordsCRUD;
import cu.lacumbre.auditor.crud.WorkersCRUD;
import cu.lacumbre.auditor.model.Attendance;
import cu.lacumbre.utils.CustomPeriod;
import cu.lacumbre.auditor.model.PaymentRecord;
import cu.lacumbre.auditor.model.Worker;
import cu.lacumbre.auditor.view.custom.DisabledCheckboxRenderer;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_AttendanceAdjust;
import cu.lacumbre.auditor.view.custom.CustomTM_AttendanceFill;
import cu.lacumbre.auditor.view.utils.tree.CustomTreeNode;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;

public class AttendanceGestion extends JDialog {

    private final Attendance document;
    private final WorkersCRUD workersCRUD;
    private final boolean fill;
    private final PaymentRecordsCRUD paymentRecordsCRUD;

    public AttendanceGestion(DocumentsGestion parent, boolean modal, Connection connection, WorkersCRUD workersCRUD, PaymentRecordsCRUD paymentRecordsCRUD, Attendance document, CustomTreeNode node, boolean fill) throws EmptyWorkersException, SQLException {
        super(parent, modal);
        this.workersCRUD = workersCRUD;
        this.document = document;
        this.fill = fill;
        this.paymentRecordsCRUD = paymentRecordsCRUD;
        initComponents();
        if (fill) {
            createFillInstance();
        } else {
            createAdjustInstance();
        }
    }

    private void createFillInstance() throws EmptyWorkersException, SQLException {
        CustomPeriod period = document.getPeriod();
        ArrayList<LocalDate> dates = period.getDates();
        TreeMap<Worker, PaymentRecord> dataMap = loadOrCreate(dates);
        ArrayList<String> days = period.getDays();
        days.add(0, "Nombre y Apellidos");
        tableAttendance.setModel(new CustomTM_AttendanceFill(dataMap, dates, days, document));
        tableAttendance.getModel().addTableModelListener((TableModelEvent e) -> {
            lblTotalPaymentValue.setText(calcTotalPayment());
        });
    }

    private void createAdjustInstance() throws EmptyWorkersException, SQLException {
        TreeMap<Worker, Double[]> dataMap = new TreeMap<>(Comparator.comparing(Worker::getCode));
        for (PaymentRecord paymentRecord : paymentRecordsCRUD.getList()) {
            dataMap.put(paymentRecord.getWorker(), new Double[]{paymentRecord.getWorkedDays(), paymentRecord.getWorkedDaysAdjusted()});
        }
        tableAttendance.setModel(new CustomTM_AttendanceAdjust(dataMap, paymentRecordsCRUD));
        tableAttendance.getModel().addTableModelListener((TableModelEvent e) -> {
            lblTotalPaymentValue.setText(calcTotalPayment());
        });

    }

    private String calcTotalPayment() {
        ArrayList<Double> payments = new ArrayList<>();
        for (PaymentRecord paymentRecord : paymentRecordsCRUD.getList()) {
            if (paymentRecord.getDocument().equals(document)) {
                payments.add(paymentRecord.getToPay());
            }
        }
        double sum = payments.stream().mapToDouble((Double record) -> record).sum();
        return String.valueOf(sum);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new JPanel();
        center = new JScrollPane();
        tableAttendance = new CustomJTable();
        south = new JPanel();
        jPanel1 = new JPanel();
        jButton1 = new JButton();
        filler1 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        lblTotalPaymentKey = new JLabel();
        lblTotalPaymentValue = new JLabel();
        filler2 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        btnSave = new JButton();
        btnCancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Control de asistencia del periodo");
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
        //tableAttendance.setDefaultRenderer(Boolean.class, new DisabledCheckboxRenderer());
        center.setViewportView(tableAttendance);

        main.add(center, BorderLayout.CENTER);

        south.setLayout(new BoxLayout(south, BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        jButton1.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Exportar CSV");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        south.add(jPanel1);
        south.add(filler1);

        lblTotalPaymentKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalPaymentKey.setText("Total a pagar:  ");
        south.add(lblTotalPaymentKey);

        lblTotalPaymentKey.setVisible(!fill);
        lblTotalPaymentValue.setVisible(!fill);
        lblTotalPaymentValue.setText(calcTotalPayment());
        lblTotalPaymentValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        south.add(lblTotalPaymentValue);
        south.add(filler2);

        btnSave.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSave.setText("Guardar");
        btnSave.setVisible(fill);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        south.add(btnSave);

        btnCancel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setText("Cancelar");
        south.add(btnCancel);

        main.add(south, BorderLayout.SOUTH);

        getContentPane().add(main, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (fill) {
            saveFill();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jButton1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (fill) {
            exportFillModel();
        } else {
            exportAdjustModel();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void saveFill() {
        CustomTM_AttendanceFill model = (CustomTM_AttendanceFill) tableAttendance.getModel();
        try {
            TreeMap<Integer, PaymentRecord> currentPaymentRecords = paymentRecordsCRUD.getMap();
            if (currentPaymentRecords.isEmpty()) {
                for (Map.Entry<Worker, PaymentRecord> entry : model.getParsedDataMap().entrySet()) {
                    Worker worker = entry.getKey();
                    PaymentRecord paymentRecord = entry.getValue();
                    currentPaymentRecords.put(worker.getId(), paymentRecord);
                }
                paymentRecordsCRUD.save(new ArrayList<>(currentPaymentRecords.values()));
            } else {
                for (Map.Entry<Worker, PaymentRecord> entry : model.getParsedDataMap().entrySet()) {
                    Worker worker = entry.getKey();
                    PaymentRecord paymentRecord = entry.getValue();
                    currentPaymentRecords.put(worker.getId(), paymentRecord);
                }
                paymentRecordsCRUD.update(new ArrayList<>(currentPaymentRecords.values()));
            }
            dispose();
            JOptionPane.showMessageDialog(this, "Registro de asistencia guardado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnCancel;
    private JButton btnSave;
    private JScrollPane center;
    private Box.Filler filler1;
    private Box.Filler filler2;
    private JButton jButton1;
    private JPanel jPanel1;
    private JLabel lblTotalPaymentKey;
    private JLabel lblTotalPaymentValue;
    private JPanel main;
    private JPanel south;
    private CustomJTable tableAttendance;
    // End of variables declaration//GEN-END:variables

    private TreeMap<Worker, PaymentRecord> loadOrCreate(ArrayList<LocalDate> dates) throws SQLException {
        TreeMap<Worker, PaymentRecord> loadedMap = load();
        if (loadedMap.isEmpty()) {
            loadedMap = create(dates);
        }
        return loadedMap;
    }

    private TreeMap<Worker, PaymentRecord> load() throws SQLException {
        TreeMap<Worker, PaymentRecord> dataMap = new TreeMap<>(Comparator.comparing(Worker::getCode));
        for (PaymentRecord paymentRecord : paymentRecordsCRUD.getList()) {
            dataMap.put(paymentRecord.getWorker(), paymentRecord);
        }
        return dataMap;
    }

    private TreeMap<Worker, PaymentRecord> create(ArrayList<LocalDate> days) {
        TreeMap<Worker, PaymentRecord> dataMap = new TreeMap<>(Comparator.comparing(Worker::getCode));
        for (Worker worker : workersCRUD.getActiveList(document.getPeriod())) {
            if (!worker.getEnrollDate().isAfter(days.get(days.size() - 1))) {
                TreeMap<Integer, Boolean> attendance = new TreeMap<>(Comparator.comparingLong(Integer::intValue));
                for (LocalDate day : days) {
                    attendance.put(day.getDayOfMonth(), false);
                }
                PaymentRecord paymentRecord = new PaymentRecord(worker, attendance, document);
                dataMap.put(worker, paymentRecord);
            }
        }
        return dataMap;
    }

    private void exportFillModel() {
        CustomTM_AttendanceFill model = (CustomTM_AttendanceFill) tableAttendance.getModel();
        TreeMap<Worker, PaymentRecord> parsedDataMap = model.getParsedDataMap();
        File lastDirectoryOfAttendance = Setup.getLastDirectoryOf(Setup.ATTENDANCE_DIRECTORY);
        JFileChooser chooser = new JFileChooser(lastDirectoryOfAttendance);
        String filename = "Reporte de Pago " + document.getPeriod();
        chooser.setSelectedFile(new File(chooser.getCurrentDirectory().getPath() + System.getProperty("file.separator") + filename + ".csv"));
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        int dialogOption = chooser.showSaveDialog(this);
        if (dialogOption == JFileChooser.APPROVE_OPTION) {
            FileWriter fw = null;
            try {
                File selectedFile = chooser.getSelectedFile();
                Setup.setLastDirectory(Setup.ATTENDANCE_DIRECTORY, selectedFile.getParentFile());
                if (selectedFile.getName().split("\\.").length == 0) {
                    selectedFile = new File(selectedFile.getPath().concat(".csv"));
                } else {
                    selectedFile = new File(selectedFile.getPath().split("\\.")[0].concat(".csv"));
                }
                fw = new FileWriter(selectedFile);
                int monthLenght = 0;
                if (!parsedDataMap.isEmpty()) {
                    monthLenght = new ArrayList<>(parsedDataMap.values()).get(0).getRecords().size();
                }
                fw.append("Trabajador").append(';');
                for (int i = 1; i <= monthLenght; i++) {
                    fw.append(i + "").append(';');
                }
                fw.append('\n');
                for (Map.Entry<Worker, PaymentRecord> workers : parsedDataMap.entrySet()) {
                    Worker worker = workers.getKey();
                    TreeMap<Integer, Boolean> map = workers.getValue().getRecords();
                    String description = worker.getFullName();
                    fw.append(description).append(';');
                    for (Map.Entry<Integer, Boolean> entry1 : map.entrySet()) {
                        int attend = entry1.getValue() ? 1 : 0;
                        fw.append(attend + "").append(';');
                    }
                    fw.append('\n');
                }
                fw.append("\n;\n");
                fw.close();
                JOptionPane.showMessageDialog(this, "Reporte de asistencia exportados correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getInstance().updateErrorLog(ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }
        }
    }

    private void exportAdjustModel() {
        CustomTM_AttendanceAdjust model = (CustomTM_AttendanceAdjust) tableAttendance.getModel();
        TreeMap<Worker, Double[]> datas = model.getDataMap();
        File lastDirectoryOfAttendance = Setup.getLastDirectoryOf(Setup.ATTENDANCE_DIRECTORY);
        JFileChooser chooser = new JFileChooser(lastDirectoryOfAttendance);
        String filename = "Reporte de Pago " + document.getPeriod();
        chooser.setSelectedFile(new File(chooser.getCurrentDirectory().getPath() + System.getProperty("file.separator") + filename + ".csv"));
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        int dialogOption = chooser.showSaveDialog(this);
        if (dialogOption == JFileChooser.APPROVE_OPTION) {
            FileWriter fw = null;
            try {
                File selectedFile = chooser.getSelectedFile();
                Setup.setLastDirectory(Setup.ATTENDANCE_DIRECTORY, selectedFile.getParentFile());
                if (selectedFile.getName().split("\\.").length == 0) {
                    selectedFile = new File(selectedFile.getPath().concat(".csv"));
                } else {
                    selectedFile = new File(selectedFile.getPath().split("\\.")[0].concat(".csv"));
                }
                fw = new FileWriter(selectedFile);
                fw.append("Trabajador").append(';');
                fw.append("Dias Trabajados").append(';');
                fw.append("Dias A Pagar").append(';');
                fw.append('\n');
                for (Map.Entry<Worker, Double[]> workers : datas.entrySet()) {
                    Worker worker = workers.getKey();
                    Double[] value = workers.getValue();
                    String description = worker.getFullName();
                    fw.append(description).append(';');
                    fw.append(value[0] + "").append(';');
                    fw.append(value[1] + "").append(';');
                    fw.append('\n');
                }
                fw.append("\n;\n");
                fw.close();
                JOptionPane.showMessageDialog(this, "Reporte de asistencia exportados correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getInstance().updateErrorLog(ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }
        }
    }
}
