package cu.lacumbre.auditor.view.merchandise;

import cu.lacumbre.auditor.crud.CostSheetsCRUD;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import cu.lacumbre.auditor.model.CostSheet;
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
import javax.swing.event.ListSelectionEvent;

public class CostSheetsGestion extends JDialog {

    private CostSheetsCRUD csCrud;
    private String currentFilter;
    private long lastFiterKeyEventTime = Instant.now().toEpochMilli();

    public CostSheetsGestion(JFrame parent, boolean lockParent, Connection connection) {
        super(parent, lockParent);
        try {
            csCrud = new CostSheetsCRUD(connection);
            initComponents();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog( ex);
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
        controls = new JPanel();
        upLeft = new JPanel();
        jLabel8 = new JLabel();
        lblDescKey = new JLabel();
        jLabel6 = new JLabel();
        tfDescValue = new JTextField();
        jLabel2 = new JLabel();
        downLeft = new JPanel();
        jLabel9 = new JLabel();
        lblInputCostKey = new JLabel();
        jLabel7 = new JLabel();
        sprInputCostValue = new JSpinner();
        jLabel3 = new JLabel();
        downLeft1 = new JPanel();
        jLabel11 = new JLabel();
        lblFinancialCostsKey = new JLabel();
        jLabel12 = new JLabel();
        sprFinancialCostsValue = new JSpinner();
        jLabel4 = new JLabel();
        downLeft2 = new JPanel();
        jLabel13 = new JLabel();
        lblEnergyCostsKey = new JLabel();
        jLabel14 = new JLabel();
        sprEnergyCostsValue = new JSpinner();
        jLabel5 = new JLabel();
        downLeft3 = new JPanel();
        jLabel15 = new JLabel();
        lblRentalCostsKey = new JLabel();
        jLabel16 = new JLabel();
        sprRentalCostsValue = new JSpinner();
        jLabel17 = new JLabel();
        downLeft4 = new JPanel();
        jLabel18 = new JLabel();
        lblLaborCostKey = new JLabel();
        jLabel19 = new JLabel();
        sprLaborCostValue = new JSpinner();
        jLabel20 = new JLabel();
        downLeft5 = new JPanel();
        jLabel21 = new JLabel();
        lblProfitMarginKey = new JLabel();
        jLabel22 = new JLabel();
        sprProfitMarginValue = new JSpinner();
        jLabel23 = new JLabel();
        buttons = new JPanel();
        jLabel24 = new JLabel();
        btnSave = new JButton();
        jLabel25 = new JLabel();
        btnUpdate = new JButton();
        jLabel26 = new JLabel();
        btnDelete = new JButton();
        jLabel27 = new JLabel();
        buttons1 = new JPanel();
        jLabel28 = new JLabel();
        btnCancel = new JButton();
        jLabel29 = new JLabel();
        btnAdd = new JButton();
        jLabel30 = new JLabel();
        east = new JPanel();
        filter = new JPanel();
        tfFilter = new JTextField();
        jButton1 = new JButton();
        jScrollPane1 = new JScrollPane();
        listObjects = new JList<>();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestionar Fichas de Costo");
        setIconImage(null);
        setMinimumSize(new Dimension(530, 500));
        getContentPane().setLayout(new BorderLayout(6, 6));

        north.setPreferredSize(new Dimension(500, 50));
        north.setLayout(new GridLayout(2, 0, 0, 6));
        north.add(lblBanner);

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

        north.add(idPanel);

        getContentPane().add(north, BorderLayout.NORTH);

        west.setPreferredSize(new Dimension(300, 250));
        west.setLayout(new CardLayout());

        controls.setLayout(new GridLayout(9, 0, 0, 6));

        upLeft.setEnabled(false);
        upLeft.setLayout(new BoxLayout(upLeft, BoxLayout.LINE_AXIS));

        jLabel8.setText("   ");
        jLabel8.setEnabled(false);
        upLeft.add(jLabel8);

        lblDescKey.setText("Descripción:");
        upLeft.add(lblDescKey);

        jLabel6.setText("   ");
        jLabel6.setEnabled(false);
        upLeft.add(jLabel6);

        tfDescValue.setEnabled(false);
        upLeft.add(tfDescValue);

        jLabel2.setText("   ");
        jLabel2.setEnabled(false);
        upLeft.add(jLabel2);

        controls.add(upLeft);

        downLeft.setEnabled(false);
        downLeft.setLayout(new BoxLayout(downLeft, BoxLayout.LINE_AXIS));

        jLabel9.setText("   ");
        jLabel9.setEnabled(false);
        downLeft.add(jLabel9);

        lblInputCostKey.setText("Gastos por Insumos:");
        downLeft.add(lblInputCostKey);

        jLabel7.setText("   ");
        downLeft.add(jLabel7);

        sprInputCostValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        sprInputCostValue.setEnabled(false);
        downLeft.add(sprInputCostValue);

        jLabel3.setText("   ");
        jLabel3.setEnabled(false);
        downLeft.add(jLabel3);

        controls.add(downLeft);

        downLeft1.setEnabled(false);
        downLeft1.setLayout(new BoxLayout(downLeft1, BoxLayout.LINE_AXIS));

        jLabel11.setText("   ");
        jLabel11.setEnabled(false);
        downLeft1.add(jLabel11);

        lblFinancialCostsKey.setText("Gastos Financieros:");
        downLeft1.add(lblFinancialCostsKey);

        jLabel12.setText("   ");
        downLeft1.add(jLabel12);

        sprFinancialCostsValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        sprFinancialCostsValue.setEnabled(false);
        downLeft1.add(sprFinancialCostsValue);

        jLabel4.setText("   ");
        jLabel4.setEnabled(false);
        downLeft1.add(jLabel4);

        controls.add(downLeft1);

        downLeft2.setEnabled(false);
        downLeft2.setLayout(new BoxLayout(downLeft2, BoxLayout.LINE_AXIS));

        jLabel13.setText("   ");
        jLabel13.setEnabled(false);
        downLeft2.add(jLabel13);

        lblEnergyCostsKey.setText("Gastos Energéticos:");
        downLeft2.add(lblEnergyCostsKey);

        jLabel14.setText("   ");
        downLeft2.add(jLabel14);

        sprEnergyCostsValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        sprEnergyCostsValue.setEnabled(false);
        downLeft2.add(sprEnergyCostsValue);

        jLabel5.setText("   ");
        jLabel5.setEnabled(false);
        downLeft2.add(jLabel5);

        controls.add(downLeft2);

        downLeft3.setEnabled(false);
        downLeft3.setLayout(new BoxLayout(downLeft3, BoxLayout.LINE_AXIS));

        jLabel15.setText("   ");
        jLabel15.setEnabled(false);
        downLeft3.add(jLabel15);

        lblRentalCostsKey.setText("Gastos por Alquiler:");
        downLeft3.add(lblRentalCostsKey);

        jLabel16.setText("   ");
        downLeft3.add(jLabel16);

        sprRentalCostsValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        sprRentalCostsValue.setEnabled(false);
        downLeft3.add(sprRentalCostsValue);

        jLabel17.setText("   ");
        jLabel17.setEnabled(false);
        downLeft3.add(jLabel17);

        controls.add(downLeft3);

        downLeft4.setEnabled(false);
        downLeft4.setLayout(new BoxLayout(downLeft4, BoxLayout.LINE_AXIS));

        jLabel18.setText("   ");
        jLabel18.setEnabled(false);
        downLeft4.add(jLabel18);

        lblLaborCostKey.setText("Gastos de Mano de Obra:");
        downLeft4.add(lblLaborCostKey);

        jLabel19.setText("   ");
        downLeft4.add(jLabel19);

        sprLaborCostValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        sprLaborCostValue.setEnabled(false);
        downLeft4.add(sprLaborCostValue);

        jLabel20.setText("   ");
        jLabel20.setEnabled(false);
        downLeft4.add(jLabel20);

        controls.add(downLeft4);

        downLeft5.setEnabled(false);
        downLeft5.setLayout(new BoxLayout(downLeft5, BoxLayout.LINE_AXIS));

        jLabel21.setText("   ");
        jLabel21.setEnabled(false);
        downLeft5.add(jLabel21);

        lblProfitMarginKey.setText("Margen de Utilidad:");
        downLeft5.add(lblProfitMarginKey);

        jLabel22.setText("   ");
        downLeft5.add(jLabel22);

        sprProfitMarginValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        sprProfitMarginValue.setEnabled(false);
        downLeft5.add(sprProfitMarginValue);

        jLabel23.setText("   ");
        jLabel23.setEnabled(false);
        downLeft5.add(jLabel23);

        controls.add(downLeft5);

        buttons.setEnabled(false);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        jLabel24.setText("   ");
        buttons.add(jLabel24);

        btnSave.setVisible(false);
        btnSave.setText("Guardar");
        btnSave.addActionListener(this::btnSaveActionPerformed);
        buttons.add(btnSave);

        jLabel25.setText("   ");
        buttons.add(jLabel25);

        btnUpdate.setVisible(false);
        btnUpdate.setText("Modificar");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        buttons.add(btnUpdate);

        jLabel26.setText("   ");
        buttons.add(jLabel26);

        btnDelete.setVisible(false);
        btnDelete.setText("Eliminar");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        buttons.add(btnDelete);

        jLabel27.setText("   ");
        buttons.add(jLabel27);

        controls.add(buttons);

        buttons1.setEnabled(false);
        buttons1.setLayout(new BoxLayout(buttons1, BoxLayout.LINE_AXIS));

        jLabel28.setText("   ");
        buttons1.add(jLabel28);

        btnCancel.setVisible(false);
        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(this::btnCancelActionPerformed);
        buttons1.add(btnCancel);

        jLabel29.setText("   ");
        buttons1.add(jLabel29);

        btnAdd.setText("Nuevo");
        btnAdd.addActionListener(this::btnAddActionPerformed);
        buttons1.add(btnAdd);

        jLabel30.setText("   ");
        buttons1.add(jLabel30);

        controls.add(buttons1);

        west.add(controls, "card2");

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

        listObjects.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        listObjects.setModel(new CustomListModel(csCrud.getList()));
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
            double inputCosts = (double) sprInputCostValue.getValue();
            double energyCosts = (double) sprEnergyCostsValue.getValue();
            double financialCosts = (double) sprFinancialCostsValue.getValue();
            double rentalCosts = (double) sprRentalCostsValue.getValue();
            double laborCosts = (double) sprLaborCostValue.getValue();
            double profitMargin = (double) sprProfitMarginValue.getValue();
            csCrud.save(new CostSheet(description, inputCosts, financialCosts, energyCosts, rentalCosts, laborCosts, profitMargin));
            refreshFrameView();
            int option = JOptionPane.showConfirmDialog(this, "Ficha de costo registrada correctamente.\n ¿Desea agregar más?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                prepareViewToInsertion();
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog( ex);
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
            CostSheet costSheet = (CostSheet) listObjects.getSelectedValue();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea modificar la ficha de costo elegida?", "Confirmar Modificación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                costSheet.setDescription(tfDescValue.getText());
                costSheet.setInputCosts((double) sprInputCostValue.getValue());
                costSheet.setEnergyCosts((double) sprEnergyCostsValue.getValue());
                costSheet.setFinancialCosts((double) sprFinancialCostsValue.getValue());
                costSheet.setRentalCosts((double) sprRentalCostsValue.getValue());
                costSheet.setLaborCosts((double) sprLaborCostValue.getValue());
                costSheet.setProfitMargin((double) sprProfitMarginValue.getValue());
                csCrud.update(costSheet);
                refreshFrameView();
                JOptionPane.showMessageDialog(this, "Ficha de costo modificada correctamente");
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog( ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            CostSheet costSheet = (CostSheet) listObjects.getSelectedValue();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar la ficha de costo elegida?", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                csCrud.delete(costSheet);
                JOptionPane.showMessageDialog(this, "Ficha de costo eliminada correctamente");
                refreshFrameView();
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog( ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void listObjectsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listObjectsKeyTyped
        currentFilter = tfFilter.getText();
        char charTyped = evt.getKeyChar();
        if (Character.isAlphabetic(charTyped) || Character.isWhitespace(charTyped) || Character.isDigit(charTyped)) {
            currentFilter = currentFilter + charTyped;
            CustomListModel<CostSheet> model = (CustomListModel<CostSheet>) listObjects.getModel();
            ArrayList<CostSheet> tempList = new ArrayList<>();
            ArrayList<CostSheet> list = model.getList();
            for (CostSheet costSheet : list) {
                if (costSheet.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(costSheet);
                }
            }
            listObjects.setModel(new CustomListModel<>(tempList));
            tfFilter.setText(currentFilter);
        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<CostSheet> tempList = new ArrayList<>();
                for (CostSheet costSheet : csCrud.getList()) {
                    if (costSheet.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                        tempList.add(costSheet);
                    }
                }
                listObjects.setModel(new CustomListModel<>(tempList));
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                ArrayList<CostSheet> costSheets = csCrud.getList();
                costSheets.sort(Comparator.comparingInt(CostSheet::getId));
                listObjects.setModel(new CustomListModel(costSheets));
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_listObjectsKeyTyped

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (!evt.getValueIsAdjusting()) {
            CostSheet costSheet = (CostSheet) listObjects.getSelectedValue();
            if (costSheet != null) {
                prepareViewToEdition();
                lblIDValue.setText(costSheet.getId() + "");
                tfDescValue.setText(costSheet.getDescription());
                sprInputCostValue.setValue(costSheet.getInputCosts());
                sprEnergyCostsValue.setValue(costSheet.getEnergyCosts());
                sprFinancialCostsValue.setValue(costSheet.getFinancialCosts());
                sprRentalCostsValue.setValue(costSheet.getRentalCosts());
                sprLaborCostValue.setValue(costSheet.getLaborCosts());
                sprProfitMarginValue.setValue(costSheet.getProfitMargin());
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void prepareViewToEdition() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        sprEnergyCostsValue.setEnabled(true);
        sprFinancialCostsValue.setEnabled(true);
        sprInputCostValue.setEnabled(true);
        sprLaborCostValue.setEnabled(true);
        sprProfitMarginValue.setEnabled(true);
        sprRentalCostsValue.setEnabled(true);
        lblInputCostKey.setEnabled(true);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
        btnAdd.setVisible(false);
    }

    private void prepareViewToInsertion() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        sprEnergyCostsValue.setEnabled(true);
        sprFinancialCostsValue.setEnabled(true);
        sprInputCostValue.setEnabled(true);
        sprLaborCostValue.setEnabled(true);
        sprProfitMarginValue.setEnabled(true);
        sprRentalCostsValue.setEnabled(true);
        lblInputCostKey.setEnabled(true);
        btnAdd.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
    }

    private void refreshFrameView() {
        listObjects.setModel(new CustomListModel(csCrud.getList()));
        tfDescValue.setEnabled(false);
        lblDescKey.setEnabled(false);
        sprEnergyCostsValue.setEnabled(false);
        sprFinancialCostsValue.setEnabled(false);
        sprInputCostValue.setEnabled(false);
        sprLaborCostValue.setEnabled(false);
        sprProfitMarginValue.setEnabled(false);
        sprRentalCostsValue.setEnabled(false);
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
    private JPanel buttons1;
    private JPanel controls;
    private JPanel downLeft;
    private JPanel downLeft1;
    private JPanel downLeft2;
    private JPanel downLeft3;
    private JPanel downLeft4;
    private JPanel downLeft5;
    private JPanel east;
    private JPanel filter;
    private JPanel idPanel;
    private JButton jButton1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel24;
    private JLabel jLabel25;
    private JLabel jLabel26;
    private JLabel jLabel27;
    private JLabel jLabel28;
    private JLabel jLabel29;
    private JLabel jLabel3;
    private JLabel jLabel30;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JLabel lblBanner;
    private JLabel lblDescKey;
    private JLabel lblEnergyCostsKey;
    private JLabel lblFinancialCostsKey;
    private JLabel lblIDKey;
    private JLabel lblIDValue;
    private JLabel lblInputCostKey;
    private JLabel lblLaborCostKey;
    private JLabel lblProfitMarginKey;
    private JLabel lblRentalCostsKey;
    private JList<CostSheet> listObjects;
    private JPanel north;
    private JSpinner sprEnergyCostsValue;
    private JSpinner sprFinancialCostsValue;
    private JSpinner sprInputCostValue;
    private JSpinner sprLaborCostValue;
    private JSpinner sprProfitMarginValue;
    private JSpinner sprRentalCostsValue;
    private JTextField tfDescValue;
    private JTextField tfFilter;
    private JPanel upLeft;
    private JPanel west;
    // End of variables declaration//GEN-END:variables
}
