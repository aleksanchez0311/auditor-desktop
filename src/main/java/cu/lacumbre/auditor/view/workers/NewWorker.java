/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package cu.lacumbre.auditor.view.workers;

import com.toedter.calendar.JDateChooser;
import cu.lacumbre.auditor.crud.WorkersCRUD;
import cu.lacumbre.auditor.model.Role;
import cu.lacumbre.auditor.model.Worker;
import cu.lacumbre.auditor.view.custom.CustomComboBoxModel;
import cu.lacumbre.utils.Logger;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class NewWorker extends JDialog {

    private final WorkersCRUD workersCRUD;
    private final StaffGestion invoker;

    public NewWorker(StaffGestion parent, WorkersCRUD workersCRUD, boolean modal) {
        super(parent, modal);
        this.workersCRUD = workersCRUD;
        this.invoker = parent;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new JPanel();
        center = new JPanel();
        code = new JPanel();
        lblCodeKey = new JLabel();
        filler10 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        tfCodeValue = new JTextField();
        date = new JPanel();
        lblDateKey = new JLabel();
        filler8 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        dcDate = new JDateChooser();
        name = new JPanel();
        lblNameKey = new JLabel();
        filler1 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        tfNameValue = new JTextField();
        lastName = new JPanel();
        lblLastNameKey = new JLabel();
        filler2 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        tfLastNameValue = new JTextField();
        role = new JPanel();
        lblRoleKey = new JLabel();
        filler3 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        cbxRoleValue = new JComboBox<>();
        dni = new JPanel();
        lblDNIKey = new JLabel();
        filler4 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        tfDNIValue = new JTextField();
        address = new JPanel();
        lblAddressKey = new JLabel();
        filler5 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        tfAddressValue = new JTextField();
        phone = new JPanel();
        lblPrefix = new JLabel();
        cbxPrefixes = new JComboBox<>();
        filler6 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        tfPhoneValue = new JTextField();
        email = new JPanel();
        lblEmailKey = new JLabel();
        filler7 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 32767));
        tfEmailValue = new JTextField();
        south = new JPanel();
        activated = new JPanel();
        chxActivatedValue = new JCheckBox();
        filler9 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 32767));
        btnSave = new JButton();
        btnClose = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setLayout(new BorderLayout(5, 5));

        center.setLayout(new GridLayout(5, 2, 10, 5));

        code.setLayout(new BoxLayout(code, BoxLayout.LINE_AXIS));

        lblCodeKey.setText("Código de Trabajador:");
        code.add(lblCodeKey);
        code.add(filler10);

        tfCodeValue.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                tfCodeValueKeyTyped(evt);
            }
        });
        code.add(tfCodeValue);

        center.add(code);

        date.setLayout(new BoxLayout(date, BoxLayout.LINE_AXIS));

        lblDateKey.setText("Fecha de Ingreso:");
        date.add(lblDateKey);
        date.add(filler8);
        date.add(dcDate);

        center.add(date);

        name.setLayout(new BoxLayout(name, BoxLayout.LINE_AXIS));

        lblNameKey.setText("Nombre:");
        name.add(lblNameKey);
        name.add(filler1);
        name.add(tfNameValue);

        center.add(name);

        lastName.setLayout(new BoxLayout(lastName, BoxLayout.LINE_AXIS));

        lblLastNameKey.setText("Apellidos:");
        lastName.add(lblLastNameKey);
        lastName.add(filler2);
        lastName.add(tfLastNameValue);

        center.add(lastName);

        role.setLayout(new BoxLayout(role, BoxLayout.LINE_AXIS));

        lblRoleKey.setText("Ocupación:");
        role.add(lblRoleKey);
        role.add(filler3);

        cbxRoleValue.setModel(new CustomComboBoxModel<>(workersCRUD.getRolesCRUD().getList()));
        cbxRoleValue.setSelectedIndex(0);
        role.add(cbxRoleValue);

        center.add(role);

        dni.setLayout(new BoxLayout(dni, BoxLayout.LINE_AXIS));

        lblDNIKey.setText("DNI:");
        dni.add(lblDNIKey);
        dni.add(filler4);

        tfDNIValue.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                tfDNIValueKeyTyped(evt);
            }
        });
        dni.add(tfDNIValue);

        center.add(dni);

        address.setLayout(new BoxLayout(address, BoxLayout.LINE_AXIS));

        lblAddressKey.setText("Dirección");
        address.add(lblAddressKey);
        address.add(filler5);
        address.add(tfAddressValue);

        center.add(address);

        phone.setLayout(new BoxLayout(phone, BoxLayout.LINE_AXIS));

        lblPrefix.setText("+");
        phone.add(lblPrefix);

        cbxPrefixes.setModel(new DefaultComboBoxModel<>(new String[] { "53-Cuba", "1-USA" }));
        phone.add(cbxPrefixes);
        phone.add(filler6);

        tfPhoneValue.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                tfPhoneValueKeyTyped(evt);
            }
        });
        phone.add(tfPhoneValue);

        center.add(phone);

        email.setLayout(new BoxLayout(email, BoxLayout.LINE_AXIS));

        lblEmailKey.setText("Correo Electrónico:");
        email.add(lblEmailKey);
        email.add(filler7);

        tfEmailValue.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                tfEmailValueKeyTyped(evt);
            }
        });
        email.add(tfEmailValue);

        center.add(email);

        main.add(center, BorderLayout.CENTER);

        south.setLayout(new BoxLayout(south, BoxLayout.LINE_AXIS));

        activated.setLayout(new FlowLayout(FlowLayout.LEFT));

        chxActivatedValue.setSelected(true);
        chxActivatedValue.setText("Trabajador Activo");
        activated.add(chxActivatedValue);
        activated.add(filler9);

        south.add(activated);

        btnSave.setText("Guardar");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        south.add(btnSave);

        btnClose.setText("Cerrar");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        south.add(btnClose);

        main.add(south, BorderLayout.SOUTH);

        getContentPane().add(main, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSaveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (validateForm()) {
            try {
                String name = tfNameValue.getText();
                String lastName = tfLastNameValue.getText();
                Role role = (Role) cbxRoleValue.getSelectedItem();
                String dni = tfDNIValue.getText();
                String address = tfAddressValue.getText();
                String fullPrefix = (String) cbxPrefixes.getSelectedItem();
                String prefix = fullPrefix.split("-")[0];
                String phone = tfPhoneValue.getText();
                String email = tfEmailValue.getText();
                String code = tfCodeValue.getText();
                boolean activated = chxActivatedValue.isSelected();
                LocalDate date = LocalDate.ofInstant(dcDate.getDate().toInstant(), ZoneId.systemDefault());
                workersCRUD.save(new Worker(name, lastName, role, dni, address, prefix, phone, email, date, activated, code));
                JOptionPane.showMessageDialog(this, "Trabajador registrado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                invoker.updateModel();
                dispose();
            } catch (SQLException ex) {
                Logger.getInstance().updateErrorLog( ex);
            }
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void tfDNIValueKeyTyped(KeyEvent evt) {//GEN-FIRST:event_tfDNIValueKeyTyped
        char keyChar = evt.getKeyChar();
        if (!Character.isDigit(keyChar)) {
            evt.consume();
        }
    }//GEN-LAST:event_tfDNIValueKeyTyped

    private void tfPhoneValueKeyTyped(KeyEvent evt) {//GEN-FIRST:event_tfPhoneValueKeyTyped
        char keyChar = evt.getKeyChar();
        if (!Character.isDigit(keyChar)) {
            evt.consume();
        }
    }//GEN-LAST:event_tfPhoneValueKeyTyped

    private void tfEmailValueKeyTyped(KeyEvent evt) {//GEN-FIRST:event_tfEmailValueKeyTyped
       char keyChar = evt.getKeyChar();
        if (!(Character.isAlphabetic(keyChar) || Character.isDigit(keyChar) || evt.getKeyChar() == '.' || evt.getKeyChar() == '@' || evt.getKeyChar() == '-' | evt.getKeyChar() == '_')) {
            evt.consume();
        }
    }//GEN-LAST:event_tfEmailValueKeyTyped

    private void tfCodeValueKeyTyped(KeyEvent evt) {//GEN-FIRST:event_tfCodeValueKeyTyped
        char keyChar = evt.getKeyChar();
        if (!Character.isDigit(keyChar)) {
            evt.consume();
        }
    }//GEN-LAST:event_tfCodeValueKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel activated;
    private JPanel address;
    private JButton btnClose;
    private JButton btnSave;
    private JComboBox<String> cbxPrefixes;
    private JComboBox<Role> cbxRoleValue;
    private JPanel center;
    private JCheckBox chxActivatedValue;
    private JPanel code;
    private JPanel date;
    private JDateChooser dcDate;
    private JPanel dni;
    private JPanel email;
    private Box.Filler filler1;
    private Box.Filler filler10;
    private Box.Filler filler2;
    private Box.Filler filler3;
    private Box.Filler filler4;
    private Box.Filler filler5;
    private Box.Filler filler6;
    private Box.Filler filler7;
    private Box.Filler filler8;
    private Box.Filler filler9;
    private JPanel lastName;
    private JLabel lblAddressKey;
    private JLabel lblCodeKey;
    private JLabel lblDNIKey;
    private JLabel lblDateKey;
    private JLabel lblEmailKey;
    private JLabel lblLastNameKey;
    private JLabel lblNameKey;
    private JLabel lblPrefix;
    private JLabel lblRoleKey;
    private JPanel main;
    private JPanel name;
    private JPanel phone;
    private JPanel role;
    private JPanel south;
    private JTextField tfAddressValue;
    private JTextField tfCodeValue;
    private JTextField tfDNIValue;
    private JTextField tfEmailValue;
    private JTextField tfLastNameValue;
    private JTextField tfNameValue;
    private JTextField tfPhoneValue;
    // End of variables declaration//GEN-END:variables

    private boolean validateForm() {
        boolean result = true;
        if (cbxRoleValue.getSelectedItem() == null) {
            cbxRoleValue.setBorder(new LineBorder(Color.red));
            result = false;
        }
        if (cbxPrefixes.getSelectedItem() == null) {
            cbxPrefixes.setBorder(new LineBorder(Color.red));
            result = false;
        }
        if (tfNameValue.getText().isBlank()) {
            tfNameValue.setBorder(new LineBorder(Color.red));
            result = false;
        }
        if (tfLastNameValue.getText().isBlank()) {
            tfLastNameValue.setBorder(new LineBorder(Color.red));
            result = false;
        }
        if (tfAddressValue.getText().isBlank()) {
            tfAddressValue.setBorder(new LineBorder(Color.red));
            result = false;
        }
        if (tfDNIValue.getText().isBlank()) {
            tfDNIValue.setBorder(new LineBorder(Color.red));
            result = false;
        }
        if (tfEmailValue.getText().isBlank()) {
            tfEmailValue.setBorder(new LineBorder(Color.red));
            result = false;
        }
        if (tfPhoneValue.getText().isBlank()) {
            tfPhoneValue.setBorder(new LineBorder(Color.red));
            result = false;
        }
        return result;
    }
}
