package cu.lacumbre.auditor.view.merchandise;

import com.lowagie.text.pdf.RGBColor;
import cu.lacumbre.auditor.crud.TPVCategoriesCRUD;
import cu.lacumbre.auditor.model.TPVCategory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import cu.lacumbre.auditor.view.custom.CustomListModel;
import cu.lacumbre.utils.Logger;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JColorChooser;
import javax.swing.event.ListSelectionEvent;

public class TPVCategoriesGestion extends JDialog {

    private TPVCategoriesCRUD tpvCategoriesCRUD;
    private String currentFilter;

    public TPVCategoriesGestion(JFrame parent, boolean lockParent, Connection connection) {
        super(parent, lockParent);
        try {
            tpvCategoriesCRUD = new TPVCategoriesCRUD(connection);
            initComponents();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        north = new JPanel();
        lblBanner = new JLabel();
        idPanel = new JPanel();
        lblIDKey = new JLabel();
        jLabel10 = new JLabel();
        lblIDValue = new JLabel();
        west = new JPanel();
        jPanel1 = new JPanel();
        upLeft = new JPanel();
        jLabel8 = new JLabel();
        lblDescKey = new JLabel();
        jLabel6 = new JLabel();
        tfDescValue = new JTextField();
        jLabel2 = new JLabel();
        downLeft = new JPanel();
        jLabel9 = new JLabel();
        lblAbrevKey = new JLabel();
        jLabel7 = new JLabel();
        tfAbrevValue = new JTextField();
        jLabel3 = new JLabel();
        jColorChooser1 = new JColorChooser();
        jPanel2 = new JPanel();
        buttons = new JPanel();
        btnSave = new JButton();
        btnCancel = new JButton();
        btnAdd = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();
        east = new JPanel();
        filter = new JPanel();
        tfFilter = new JTextField();
        jButton1 = new JButton();
        jScrollPane1 = new JScrollPane();
        listObjects = new JList<>();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestionar Unidades de Medida");
        setIconImage(null);
        getContentPane().setLayout(new BorderLayout(6, 6));

        north.setPreferredSize(new Dimension(500, 50));
        north.setLayout(new GridLayout(2, 0, 0, 6));
        north.add(lblBanner);

        idPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 6));

        lblIDKey.setText("ID:");
        lblIDKey.setEnabled(false);
        idPanel.add(lblIDKey);

        jLabel10.setText(" ");
        jLabel10.setEnabled(false);
        idPanel.add(jLabel10);

        lblIDValue.setText(" ");
        lblIDValue.setEnabled(false);
        idPanel.add(lblIDValue);

        north.add(idPanel);

        getContentPane().add(north, BorderLayout.NORTH);

        west.setPreferredSize(new Dimension(300, 250));
        west.setLayout(new GridLayout(3, 1, 0, 6));

        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS));

        upLeft.setEnabled(false);
        upLeft.setLayout(new BoxLayout(upLeft, BoxLayout.LINE_AXIS));

        jLabel8.setText("   ");
        jLabel8.setEnabled(false);
        upLeft.add(jLabel8);

        lblDescKey.setText("Descripción:");
        lblDescKey.setEnabled(false);
        upLeft.add(lblDescKey);

        jLabel6.setText("   ");
        jLabel6.setEnabled(false);
        upLeft.add(jLabel6);

        tfDescValue.setEnabled(false);
        upLeft.add(tfDescValue);

        jLabel2.setText("   ");
        jLabel2.setEnabled(false);
        upLeft.add(jLabel2);

        jPanel1.add(upLeft);

        downLeft.setEnabled(false);
        downLeft.setLayout(new BoxLayout(downLeft, BoxLayout.LINE_AXIS));

        jLabel9.setText("   ");
        jLabel9.setEnabled(false);
        downLeft.add(jLabel9);

        lblAbrevKey.setText("Abreviatura:");
        lblAbrevKey.setEnabled(false);
        downLeft.add(lblAbrevKey);

        jLabel7.setText("   ");
        downLeft.add(jLabel7);

        tfAbrevValue.setEnabled(false);
        downLeft.add(tfAbrevValue);

        jLabel3.setText("   ");
        jLabel3.setEnabled(false);
        downLeft.add(jLabel3);

        jPanel1.add(downLeft);
        jPanel1.add(jColorChooser1);

        west.add(jPanel1);
        west.add(jPanel2);

        buttons.setEnabled(false);

        btnSave.setVisible(false);
        btnSave.setText("Guardar");
        btnSave.addActionListener(this::btnSaveActionPerformed);
        buttons.add(btnSave);

        btnCancel.setVisible(false);
        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(this::btnCancelActionPerformed);
        buttons.add(btnCancel);

        btnAdd.setText("Nuevo");
        btnAdd.addActionListener(this::btnAddActionPerformed);
        buttons.add(btnAdd);

        btnUpdate.setVisible(false);
        btnUpdate.setText("Modificar");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        buttons.add(btnUpdate);

        btnDelete.setVisible(false);
        btnDelete.setText("Eliminar");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        buttons.add(btnDelete);

        west.add(buttons);

        getContentPane().add(west, BorderLayout.WEST);

        east.setPreferredSize(new Dimension(200, 250));
        east.setLayout(new BoxLayout(east, BoxLayout.LINE_AXIS));

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

        east.add(filter);

        listObjects.setModel(new CustomListModel(tpvCategoriesCRUD.getList()));
        listObjects.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                listObjectsKeyTyped(evt);
            }
        });
        listObjects.addListSelectionListener(this::listObjectsValueChanged);
        jScrollPane1.setViewportView(listObjects);

        east.add(jScrollPane1);

        getContentPane().add(east, BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            String description = tfDescValue.getText();
            Color color = jColorChooser1.getColor();
            RGBColor rgbColor = new RGBColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            tpvCategoriesCRUD.save(new TPVCategory(description, rgbColor));
            refreshFrameView();
            int option = JOptionPane.showConfirmDialog(this, "Categoría de Pto. de Venta registrada correctamente.\n ¿Desea agregar más?", "Confirmación", JOptionPane.YES_NO_OPTION);
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
            TPVCategory tpvCategory = (TPVCategory) listObjects.getSelectedValue();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea modificar la categoría de pto. de venta elegida?", "Confirmar Modificación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                tpvCategory.setDescription(tfDescValue.getText());
                Color color = jColorChooser1.getColor();
                tpvCategory.setColor(new RGBColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
                tpvCategoriesCRUD.update(tpvCategory);
                refreshFrameView();
                JOptionPane.showMessageDialog(this, "Categoría de Pto. de Venta modificada correctamente");
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            TPVCategory tpvCategory = (TPVCategory) listObjects.getSelectedValue();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar la categoría de pto. de venta elegida?", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                tpvCategoriesCRUD.delete(tpvCategory);
                JOptionPane.showMessageDialog(this, "Categoría de Pto. de Venta eliminada correctamente");
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
            CustomListModel<TPVCategory> model = (CustomListModel<TPVCategory>) listObjects.getModel();
            ArrayList<TPVCategory> tempList = new ArrayList<>();
            ArrayList<TPVCategory> list = model.getList();
            for (TPVCategory tpvCategory : list) {
                if (tpvCategory.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(tpvCategory);
                }
            }
            listObjects.setModel(new CustomListModel<>(tempList));
            tfFilter.setText(currentFilter);
        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<TPVCategory> tempList = new ArrayList<>();
                for (TPVCategory tpvCategory : tpvCategoriesCRUD.getList()) {
                    if (tpvCategory.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                        tempList.add(tpvCategory);
                    }
                }
                listObjects.setModel(new CustomListModel<>(tempList));
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                ArrayList<TPVCategory> measureUnits = tpvCategoriesCRUD.getList();
                measureUnits.sort(Comparator.comparingInt(TPVCategory::getId));
                listObjects.setModel(new CustomListModel(measureUnits));
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_listObjectsKeyTyped

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (!evt.getValueIsAdjusting()) {
            TPVCategory tpvCategory = (TPVCategory) listObjects.getSelectedValue();
            if (tpvCategory != null) {
                prepareViewToEdition();
                lblIDValue.setText(tpvCategory.getId() + "");
                tfDescValue.setText(tpvCategory.getDescription());
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void prepareViewToEdition() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
        btnAdd.setVisible(false);
    }

    private void prepareViewToInsertion() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        btnAdd.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
    }

    private void refreshFrameView() {
        listObjects.setModel(new CustomListModel<>(tpvCategoriesCRUD.getList()));
        tfDescValue.setText("");
        tfDescValue.setEnabled(false);
        lblDescKey.setEnabled(false);
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
    private JPanel buttons;
    private JPanel downLeft;
    private JPanel east;
    private JPanel filter;
    private JPanel idPanel;
    private JButton jButton1;
    private JColorChooser jColorChooser1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JLabel lblAbrevKey;
    private JLabel lblBanner;
    private JLabel lblDescKey;
    private JLabel lblIDKey;
    private JLabel lblIDValue;
    private JList<TPVCategory> listObjects;
    private JPanel north;
    private JTextField tfAbrevValue;
    private JTextField tfDescValue;
    private JTextField tfFilter;
    private JPanel upLeft;
    private JPanel west;
    // End of variables declaration//GEN-END:variables
}
