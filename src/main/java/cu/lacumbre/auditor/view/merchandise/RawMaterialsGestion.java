package cu.lacumbre.auditor.view.merchandise;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

import cu.lacumbre.auditor.crud.MeasureUnitsCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.TPVCategoriesCRUD;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.model.CostSheet;
import cu.lacumbre.auditor.model.MeasureUnit;
import cu.lacumbre.auditor.model.Recipe;
import cu.lacumbre.auditor.model.Ingredient;
import cu.lacumbre.auditor.model.ProductListo;
import cu.lacumbre.auditor.model.RawMaterialCocina;
import cu.lacumbre.auditor.model.RawMaterialLista;
import cu.lacumbre.auditor.model.TPVCategory;
import cu.lacumbre.auditor.view.custom.CustomComboBoxModel;
import cu.lacumbre.auditor.view.custom.CustomListModel;
import cu.lacumbre.utils.Logger;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Comparator;
import javax.swing.Box;
import javax.swing.ComboBoxEditor;

public class RawMaterialsGestion extends JDialog {

    private final OperationsCRUD operationsCrud;
    private final MeasureUnitsCRUD measureUnitsCrud;
    private final TPVCategoriesCRUD tpvCategoriesCRUD;
    private final ItemsCRUD itemsCRUD;
    private String currentFilter;

    public RawMaterialsGestion(JFrame parent, boolean lockParent, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) {
        super(parent, lockParent);
        this.operationsCrud = operationsCRUD;
        this.itemsCRUD = itemsCRUD;
        this.measureUnitsCrud = this.itemsCRUD.getMeasureUnitsCRUD();
        this.tpvCategoriesCRUD = this.itemsCRUD.getTPVCategoriesCRUD();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlsPane = new JPanel();
        northPane = new JPanel();
        idPanel = new JPanel();
        filler1 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        lblIDKey = new JLabel();
        lblIDValue = new JLabel();
        filler3 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        lblCodeKey = new JLabel();
        spCodeValue = new JSpinner();
        filler2 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        desciptionPane = new JPanel();
        jLabel8 = new JLabel();
        lblDescKey = new JLabel();
        jLabel6 = new JLabel();
        tfDescValue = new JTextField();
        jLabel2 = new JLabel();
        measurePane = new JPanel();
        jLabel9 = new JLabel();
        lblMeasureKey = new JLabel();
        jLabel7 = new JLabel();
        cbxMeasureValues = new JComboBox<>();
        jLabel15 = new JLabel();
        costPane = new JPanel();
        jLabel1 = new JLabel();
        lblCostKey = new JLabel();
        jLabel5 = new JLabel();
        spCostValue = new JSpinner();
        jLabel11 = new JLabel();
        otherCostsPane = new JPanel();
        jLabel3 = new JLabel();
        lblWeightedCostKey = new JLabel();
        jLabel13 = new JLabel();
        spWeightedCostValue = new JSpinner();
        jLabel14 = new JLabel();
        lblLastCostKey = new JLabel();
        jLabel16 = new JLabel();
        spLastCostValue = new JSpinner();
        jLabel17 = new JLabel();
        lblHighestCostKey = new JLabel();
        jLabel18 = new JLabel();
        spHighestCostValue = new JSpinner();
        jLabel19 = new JLabel();
        isForSalePane = new JPanel();
        chxIsReadyForSale = new JCheckBox();
        btnCheckLink = new JButton();
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
        setTitle("Administrar Materias Primas");
        getContentPane().setLayout(new BorderLayout(6, 6));

        controlsPane.setEnabled(false);
        controlsPane.setMinimumSize(new Dimension(600, 600));
        controlsPane.setPreferredSize(new Dimension(600, 600));
        controlsPane.setLayout(new BorderLayout(6, 6));

        northPane.setLayout(new GridLayout(6, 1, 6, 6));

        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.LINE_AXIS));
        idPanel.add(filler1);

        lblIDKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblIDKey.setText("ID:");
        lblIDKey.setEnabled(false);
        idPanel.add(lblIDKey);

        lblIDValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblIDValue.setText(" ");
        lblIDValue.setEnabled(false);
        idPanel.add(lblIDValue);
        idPanel.add(filler3);

        lblCodeKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblCodeKey.setText("Código:");
        lblCodeKey.setEnabled(false);
        idPanel.add(lblCodeKey);

        spCodeValue.setModel(new SpinnerNumberModel(0, 0, null, 1));
        spCodeValue.setEnabled(false);
        idPanel.add(spCodeValue);
        idPanel.add(filler2);

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

        measurePane.setEnabled(false);
        measurePane.setLayout(new BoxLayout(measurePane, BoxLayout.LINE_AXIS));

        jLabel9.setText("   ");
        jLabel9.setEnabled(false);
        measurePane.add(jLabel9);

        lblMeasureKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblMeasureKey.setText("Unidad de Medida:");
        lblMeasureKey.setEnabled(false);
        measurePane.add(lblMeasureKey);

        jLabel7.setText("   ");
        measurePane.add(jLabel7);

        cbxMeasureValues.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        cbxMeasureValues.setModel(new CustomComboBoxModel(measureUnitsCrud.getList()));
        ComboBoxEditor cbe = cbxMeasureValues.getEditor();
        JTextField editor = (JTextField) cbe.getEditorComponent();
        editor.setHorizontalAlignment(JTextField.RIGHT);
        cbe.setItem(editor);
        cbxMeasureValues.setEditor(cbe);
        cbxMeasureValues.setEnabled(false);
        measurePane.add(cbxMeasureValues);

        jLabel15.setText("   ");
        measurePane.add(jLabel15);

        if(EntitySelector.currentEntity.isWorkable()){

            northPane.add(measurePane);
        }

        costPane.setLayout(new BoxLayout(costPane, BoxLayout.LINE_AXIS));

        jLabel1.setText("   ");
        jLabel1.setEnabled(false);
        costPane.add(jLabel1);

        lblCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblCostKey.setText("Costo Estandar:");
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

        otherCostsPane.setLayout(new BoxLayout(otherCostsPane, BoxLayout.LINE_AXIS));

        jLabel3.setText("   ");
        jLabel3.setEnabled(false);
        otherCostsPane.add(jLabel3);

        lblWeightedCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblWeightedCostKey.setText("Costo Ponderado:");
        lblWeightedCostKey.setEnabled(false);
        otherCostsPane.add(lblWeightedCostKey);

        jLabel13.setText("   ");
        jLabel13.setEnabled(false);
        otherCostsPane.add(jLabel13);

        spWeightedCostValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spWeightedCostValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spWeightedCostValue.setEnabled(false);
        spWeightedCostValue.setValue(0.0d);
        otherCostsPane.add(spWeightedCostValue);

        jLabel14.setText("   ");
        jLabel14.setEnabled(false);
        otherCostsPane.add(jLabel14);

        lblLastCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblLastCostKey.setText("Último Costo:");
        lblLastCostKey.setEnabled(false);
        otherCostsPane.add(lblLastCostKey);

        jLabel16.setText("   ");
        jLabel16.setEnabled(false);
        otherCostsPane.add(jLabel16);

        spLastCostValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spLastCostValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spLastCostValue.setEnabled(false);
        spLastCostValue.setValue(0.0d);
        otherCostsPane.add(spLastCostValue);

        jLabel17.setText("   ");
        jLabel17.setEnabled(false);
        otherCostsPane.add(jLabel17);

        lblHighestCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblHighestCostKey.setText("Mayor Costo:");
        lblHighestCostKey.setEnabled(false);
        otherCostsPane.add(lblHighestCostKey);

        jLabel18.setText("   ");
        jLabel18.setEnabled(false);
        otherCostsPane.add(jLabel18);

        spHighestCostValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spHighestCostValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spHighestCostValue.setEnabled(false);
        spHighestCostValue.setValue(0.0d);
        otherCostsPane.add(spHighestCostValue);

        jLabel19.setText("   ");
        jLabel19.setEnabled(false);
        otherCostsPane.add(jLabel19);

        northPane.add(otherCostsPane);

        chxIsReadyForSale.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        chxIsReadyForSale.setVisible(false);
        chxIsReadyForSale.setText("Es Listo para la Venta");
        chxIsReadyForSale.setEnabled(false);
        isForSalePane.add(chxIsReadyForSale);

        btnCheckLink.setVisible(false);
        btnCheckLink.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnCheckLink.setText("Reparar Enlace");
        btnCheckLink.addActionListener(this::btnCheckLinkActionPerformed);
        isForSalePane.add(btnCheckLink);

        northPane.add(isForSalePane);

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
            MeasureUnit measureUnit = (MeasureUnit) cbxMeasureValues.getModel().getSelectedItem();
            boolean isReadyForSale = chxIsReadyForSale.isSelected();
            double basicCost = (double) spCostValue.getValue();
            double lastCost = (double) spCostValue.getValue();
            double weightedCost = (double) spCostValue.getValue();
            double highestCost = (double) spCostValue.getValue();
            if (!isReadyForSale) {
                int necesaryID = itemsCRUD.getNextId(Setup.SUBCATEGORIA_MATERIA_PRIMA_COCINA);
                RawMaterialCocina newRawMaterialCocina = new RawMaterialCocina(necesaryID, description, measureUnit, basicCost, lastCost, weightedCost, highestCost, necesaryID, false);
                itemsCRUD.save(newRawMaterialCocina);
            } else {
                int necesaryID = itemsCRUD.getNextId(Setup.SUBCATEGORIA_MATERIA_PRIMA_LISTA);
                RawMaterialLista newRawMaterialLista = new RawMaterialLista(necesaryID, description, measureUnit, basicCost, lastCost, weightedCost, highestCost, necesaryID, false);
                itemsCRUD.save(newRawMaterialLista);
                createRFSProduct(newRawMaterialLista);
            }
            int option = JOptionPane.showConfirmDialog(this, "Materia prima registrada correctamente.\n ¿Desea agregar más?", "Confirmación", JOptionPane.YES_NO_OPTION);
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
            MeasureUnit measureUnit = (MeasureUnit) cbxMeasureValues.getModel().getSelectedItem();
            double basicCost = (double) spCostValue.getValue();
            int code = (int) spCodeValue.getValue();
            RawMaterial rawMaterial = (RawMaterial) listObjects.getSelectedValue();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea modificar la materia prima elegida?\nTenga en cuenta que si la es un listo para la venta, este se modificará también.", "Confirmar Modificación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                rawMaterial.setDescription(description);
                rawMaterial.setMeasureUnit(measureUnit);
                rawMaterial.setCost(basicCost);
                rawMaterial.setCode(code);
                if (rawMaterial instanceof RawMaterialLista rawMaterialLista) {
                    ProductListo productListo = itemsCRUD.getItemRawMaterialsCRUD().getProductListoOf(rawMaterial);
                    productListo.copyItemsFields(rawMaterialLista);
                    itemsCRUD.update(productListo);
                }
                itemsCRUD.update(rawMaterial);
                operationsCrud.updateRelatedRawMaterialCosts(rawMaterial);
                JOptionPane.showMessageDialog(this, "Materia prima modificada correctamente.");
                refreshFrameView();
            }
        } catch (SQLException | NullPointerException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            ArrayList<RawMaterial> selectedItems = (ArrayList<RawMaterial>) listObjects.getSelectedValuesList();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar la(s) materia(s) prima(s) elegida(s) y todas sus operaciones?", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                itemsCRUD.delete(selectedItems);
            }
            JOptionPane.showMessageDialog(this, "Materia(s) prima(s) eliminada(s) correctamente");
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (!evt.getValueIsAdjusting()) {
            RawMaterial rawMaterial = (RawMaterial) listObjects.getSelectedValue();
            if (rawMaterial != null) {
                prepareViewToEdition();
                lblIDValue.setText(rawMaterial.getId() + "");
                tfDescValue.setText(rawMaterial.getDescription());
                spCodeValue.setValue(rawMaterial.getCode());
                ArrayList<MeasureUnit> itemsInCombo = ((CustomComboBoxModel<MeasureUnit>) cbxMeasureValues.getModel()).getArrayList();
                for (MeasureUnit measureUnit : itemsInCombo) {
                    if (measureUnit.equals(rawMaterial.getMeasureUnit())) {
                        cbxMeasureValues.setSelectedItem(measureUnit);
                        break;
                    }
                }
                spCostValue.setValue(rawMaterial.getBasicCost());
                spWeightedCostValue.setValue(rawMaterial.getWeightedCost());
                spHighestCostValue.setValue(rawMaterial.getHighestCost());
                spLastCostValue.setValue(rawMaterial.getLastCost());
                btnCheckLink.setVisible(rawMaterial instanceof RawMaterialLista && EntitySelector.currentEntity.isWorkable() && !existsRFSProduct(rawMaterial));
                chxIsReadyForSale.setVisible(rawMaterial instanceof RawMaterialLista && EntitySelector.currentEntity.isWorkable());
                chxIsReadyForSale.setSelected(rawMaterial instanceof RawMaterialLista);
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void createRFSProduct(RawMaterial rawMaterial) throws SQLException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        int necesaryID = itemsCRUD.getNextId(Setup.SUBCATEGORIA_PRODUCTO_LISTO);
        CostSheet costSheet = itemsCRUD.getCostSheetsCRUD().get(0);
        ProductListo newProduct = new ProductListo(necesaryID, rawMaterial.getDescription(), rawMaterial.getMeasureUnit(), enterProductListoTPVCategory(), costSheet, enterProductListoPrice(), necesaryID, false);
        itemsCRUD.save(newProduct);
        ingredients.add(new Ingredient(itemsCRUD.getNecesaryID(), newProduct, rawMaterial, 1.0d));
        newProduct.setRecipe(new Recipe(newProduct.getDescription(), ingredients));
        itemsCRUD.modifyRecipe(newProduct, ingredients);
    }

    private boolean existsRFSProduct(RawMaterial rawMaterial) {
        for (ProductListo productListo : getProductListoList()) {
            if (productListo.getDescription().equals(rawMaterial.getDescription())) {
                return true;
            }
        }
        return false;
    }

    private void listObjectsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listObjectsKeyTyped
        currentFilter = tfFilter.getText();
        char charTyped = evt.getKeyChar();
        if (Character.isAlphabetic(charTyped) || Character.isWhitespace(charTyped) || Character.isDigit(charTyped)) {
            currentFilter = currentFilter + charTyped;
            CustomListModel<RawMaterial> model = (CustomListModel<RawMaterial>) listObjects.getModel();
            ArrayList<RawMaterial> tempList = new ArrayList<>();
            ArrayList<RawMaterial> list = model.getList();
            for (RawMaterial rawMaterial : list) {
                if (rawMaterial.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(rawMaterial);
                }
            }
            listObjects.setModel(new CustomListModel<>(tempList));
            tfFilter.setText(currentFilter);
        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<RawMaterial> rawMaterials = new ArrayList<>();
                for (RawMaterial rawMaterial : (ArrayList<RawMaterial>) getList()) {
                    if (rawMaterial.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                        rawMaterials.add(rawMaterial);
                    }
                }
                listObjects.setModel(new CustomListModel(rawMaterials));
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                ArrayList<RawMaterial> rawMaterials = getList();
                rawMaterials.sort(Comparator.comparingInt(RawMaterial::getCode));
                listObjects.setModel(new CustomListModel(rawMaterials));
            }
        } else {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_listObjectsKeyTyped

    private void btnCheckLinkActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCheckLinkActionPerformed
        try {
            RawMaterial rawMaterial = (RawMaterial) listObjects.getSelectedValue();
            if (rawMaterial != null) {
                createRFSProduct(rawMaterial);
                refreshFrameView();
                JOptionPane.showMessageDialog(this, "Producto listo para la venta asociado creado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnCheckLinkActionPerformed

    private void prepareViewToEdition() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        lblMeasureKey.setEnabled(true);
        lblCodeKey.setEnabled(true);
        cbxMeasureValues.setEnabled(true);
        spCostValue.setEnabled(true);
        spCodeValue.setEnabled(true);
        lblCostKey.setEnabled(true);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
        btnAdd.setVisible(false);
        crudButtons.setVisible(true);
    }

    private void prepareViewToInsertion() {
        cbxMeasureValues.setSelectedIndex(0);
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        lblMeasureKey.setEnabled(true);
        cbxMeasureValues.setEnabled(true);
        chxIsReadyForSale.setEnabled(true);
        chxIsReadyForSale.setVisible(EntitySelector.currentEntity.isWorkable());
        chxIsReadyForSale.setSelected(!EntitySelector.currentEntity.isWorkable());
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
        spCodeValue.setValue(0);
        spCodeValue.setEnabled(false);
        lblCodeKey.setEnabled(false);
        chxIsReadyForSale.setVisible(false);
        chxIsReadyForSale.setEnabled(false);
        btnCheckLink.setVisible(false);
        lblIDValue.setText("");
        spCostValue.setEnabled(false);
        lblCostKey.setEnabled(false);
        spCostValue.setValue(0.0d);
        spLastCostValue.setValue(0.0d);
        spWeightedCostValue.setValue(0.0d);
        spHighestCostValue.setValue(0.0d);
        cbxMeasureValues.setEnabled(false);
        cbxMeasureValues.setSelectedItem(null);
        lblMeasureKey.setEnabled(false);
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
    private JButton btnCheckLink;
    private JButton btnDelete;
    private JButton btnSave;
    private JButton btnUpdate;
    private JComboBox<MeasureUnit> cbxMeasureValues;
    private JPanel centerPane;
    private JCheckBox chxIsReadyForSale;
    private JPanel controlsPane;
    private JPanel costPane;
    private JPanel crudButtons;
    private JPanel desciptionPane;
    private Box.Filler filler1;
    private Box.Filler filler2;
    private Box.Filler filler3;
    private JPanel filter;
    private JPanel idPanel;
    private JPanel isForSalePane;
    private JButton jButton1;
    private JLabel jLabel1;
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
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JLabel lblCodeKey;
    private JLabel lblCostKey;
    private JLabel lblDescKey;
    private JLabel lblHighestCostKey;
    private JLabel lblIDKey;
    private JLabel lblIDValue;
    private JLabel lblLastCostKey;
    private JLabel lblMeasureKey;
    private JLabel lblWeightedCostKey;
    private JList<RawMaterial> listObjects;
    private JPanel measurePane;
    private JPanel northPane;
    private JPanel otherButtons;
    private JPanel otherCostsPane;
    private JPanel southPane;
    private JSpinner spCodeValue;
    private JSpinner spCostValue;
    private JSpinner spHighestCostValue;
    private JSpinner spLastCostValue;
    private JSpinner spWeightedCostValue;
    private JTextField tfDescValue;
    private JTextField tfFilter;
    // End of variables declaration//GEN-END:variables

    private double enterProductListoPrice() {
        String value = JOptionPane.showInputDialog(this, "Introduzca el precio de venta del producto.\n(Utilice solo números, coma o punto y no deje espacios.)",
                "Precio de venta del producto", JOptionPane.PLAIN_MESSAGE);
        Double price;
        try {
            price = Double.valueOf(value);
            return price;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en el precio introducido.", "Error", JOptionPane.ERROR_MESSAGE);
            return enterProductListoPrice();
        }
    }

    private TPVCategory enterProductListoTPVCategory() {
        ArrayList<TPVCategory> list = new ArrayList<>(tpvCategoriesCRUD.getMap().values());
        TPVCategory[] array = (TPVCategory[]) list.toArray(TPVCategory[]::new);
        return (TPVCategory) JOptionPane.showInputDialog(this, "Seleccione la categoria de punto de venta del producto.", "Precio de venta del producto", JOptionPane.PLAIN_MESSAGE, null, array, array[0]);
    }

    private ArrayList<RawMaterial> getList() {
        return itemsCRUD.getList(Setup.CATEGORIA_MATERIA_PRIMA, true);
    }

    private ArrayList<ProductListo> getProductListoList() {
        return itemsCRUD.getList(Setup.SUBCATEGORIA_PRODUCTO_LISTO, true);
    }

}
