package cu.lacumbre.auditor.view.merchandise;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.TPVCategoriesCRUD;
import cu.lacumbre.auditor.model.CostSheet;
import cu.lacumbre.auditor.model.MeasureUnit;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.ProductCocina;
import cu.lacumbre.auditor.model.TPVCategory;
import cu.lacumbre.auditor.view.custom.CustomComboBoxModel;
import cu.lacumbre.auditor.view.custom.CustomListModel;
import cu.lacumbre.utils.Logger;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxEditor;

public class ProductsGestion extends RecipableGestion {

    private final ItemsCRUD itemsCRUD;
    private final MeasureUnitsCRUD measureUnitsCRUD;
    private final TPVCategoriesCRUD tpvCategoriesCRUD;
    boolean listSelectable = true;
    private String currentFilter;

    public ProductsGestion(JFrame parent, boolean lockParent, ItemsCRUD itemsCRUD) throws SQLException {
        super(parent, lockParent);
        this.itemsCRUD = itemsCRUD;
        measureUnitsCRUD = itemsCRUD.getMeasureUnitsCRUD();
        tpvCategoriesCRUD = itemsCRUD.getTPVCategoriesCRUD();
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
        sprCodeValue = new JSpinner();
        filler2 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        desciptionPane = new JPanel();
        jLabel8 = new JLabel();
        lblDescKey = new JLabel();
        jLabel6 = new JLabel();
        tfDescValue = new JTextField();
        jLabel23 = new JLabel();
        lblTPVCategoryKey = new JLabel();
        jLabel10 = new JLabel();
        cbTPVCategoryValues = new JComboBox<>();
        jLabel2 = new JLabel();
        aPanePane = new JPanel();
        jLabel9 = new JLabel();
        lblPriceKey = new JLabel();
        jLabel7 = new JLabel();
        sprPriceValue = new JSpinner();
        jLabel18 = new JLabel();
        lblCostSheetKey = new JLabel();
        jLabel14 = new JLabel();
        cbCostSheetValues = new JComboBox<>();
        jLabel15 = new JLabel();
        costSheetPane = new JPanel();
        jLabel3 = new JLabel();
        lblCalculatedPriceKey = new JLabel();
        jLabel19 = new JLabel();
        sprCalculatedPriceValue = new JSpinner();
        jLabel22 = new JLabel();
        lblRecipeCostKey = new JLabel();
        jLabel5 = new JLabel();
        sprRecipeCostValue = new JSpinner();
        jLabel1 = new JLabel();
        costPane = new JPanel();
        jLabel16 = new JLabel();
        lblProductionCostKey = new JLabel();
        jLabel17 = new JLabel();
        sprProductionCostValue = new JSpinner();
        jLabel11 = new JLabel();
        lblUtilityKey = new JLabel();
        jLabel13 = new JLabel();
        sprUtilityValue = new JSpinner();
        jLabel21 = new JLabel();
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
        btnRecipeView = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();
        btnArchivate = new JButton();
        otherButtons = new JPanel();
        btnAdd = new JButton();
        btnCancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestionar Productos para la Venta");
        getContentPane().setLayout(new CardLayout());

        controlsPane.setEnabled(false);
        controlsPane.setMaximumSize(new Dimension(2147483647, 2147483647));
        controlsPane.setMinimumSize(new Dimension(710, 710));
        controlsPane.setPreferredSize(new Dimension(710, 710));
        controlsPane.setLayout(new BorderLayout(6, 6));

        northPane.setLayout(new GridLayout(5, 1, 6, 6));

        idPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0, 0, 0)));
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

        sprCodeValue.setModel(new SpinnerNumberModel(0, 0, null, 1));
        sprCodeValue.setEnabled(false);
        idPanel.add(sprCodeValue);
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

        jLabel23.setText("   ");
        jLabel23.setEnabled(false);
        desciptionPane.add(jLabel23);

        lblTPVCategoryKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTPVCategoryKey.setText("Categoría de TPV");
        lblTPVCategoryKey.setEnabled(false);
        desciptionPane.add(lblTPVCategoryKey);

        jLabel10.setText("   ");
        desciptionPane.add(jLabel10);

        cbTPVCategoryValues.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        cbTPVCategoryValues.setModel(new CustomComboBoxModel(tpvCategoriesCRUD.getList())
        );
        cbTPVCategoryValues.setEnabled(false);
        desciptionPane.add(cbTPVCategoryValues);

        jLabel2.setText("   ");
        jLabel2.setEnabled(false);
        desciptionPane.add(jLabel2);

        northPane.add(desciptionPane);

        aPanePane.setEnabled(false);
        aPanePane.setLayout(new BoxLayout(aPanePane, BoxLayout.LINE_AXIS));

        jLabel9.setText("   ");
        jLabel9.setEnabled(false);
        aPanePane.add(jLabel9);

        lblPriceKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblPriceKey.setText("Precio de Venta (Real): ");
        lblPriceKey.setEnabled(false);
        aPanePane.add(lblPriceKey);

        jLabel7.setText("   ");
        aPanePane.add(jLabel7);

        sprPriceValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        sprPriceValue.setModel(new SpinnerNumberModel(0.0d, null, null, 1.0d));
        sprPriceValue.setEnabled(false);
        aPanePane.add(sprPriceValue);

        jLabel18.setText("   ");
        jLabel18.setEnabled(false);
        aPanePane.add(jLabel18);

        lblCostSheetKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblCostSheetKey.setText("Ficha de Costo:");
        lblCostSheetKey.setEnabled(false);
        aPanePane.add(lblCostSheetKey);

        jLabel14.setText("   ");
        jLabel14.setEnabled(false);
        aPanePane.add(jLabel14);

        cbCostSheetValues.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        cbCostSheetValues.setModel(new CustomComboBoxModel(itemsCRUD.getCostSheetsCRUD().getList()));
        ComboBoxEditor cbe = cbCostSheetValues.getEditor();
        JTextField editor = (JTextField) cbe.getEditorComponent();
        editor.setHorizontalAlignment(JTextField.RIGHT);
        cbe.setItem(editor);
        cbCostSheetValues.setEditor(cbe);
        cbCostSheetValues.setEnabled(false);
        aPanePane.add(cbCostSheetValues);

        jLabel15.setText("   ");
        aPanePane.add(jLabel15);

        northPane.add(aPanePane);

        costSheetPane.setLayout(new BoxLayout(costSheetPane, BoxLayout.LINE_AXIS));

        jLabel3.setText("   ");
        jLabel3.setEnabled(false);
        costSheetPane.add(jLabel3);

        lblCalculatedPriceKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblCalculatedPriceKey.setText("Precio de venta (Calculado):");
        lblCalculatedPriceKey.setEnabled(false);
        costSheetPane.add(lblCalculatedPriceKey);

        jLabel19.setText("   ");
        jLabel19.setEnabled(false);
        costSheetPane.add(jLabel19);

        sprCalculatedPriceValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        sprCalculatedPriceValue.setModel(new SpinnerNumberModel(0.0d, null, null, 1.0d));
        sprCalculatedPriceValue.setEnabled(false);
        sprCalculatedPriceValue.setValue(0.0d);
        costSheetPane.add(sprCalculatedPriceValue);

        jLabel22.setText("   ");
        jLabel22.setEnabled(false);
        costSheetPane.add(jLabel22);

        lblRecipeCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        if(EntitySelector.currentEntity.isWorkable()){
            lblRecipeCostKey.setText("Costo de Receta:");
        }else{
            lblRecipeCostKey.setText("Costo de Materia Prima:");
        }
        lblRecipeCostKey.setEnabled(false);
        costSheetPane.add(lblRecipeCostKey);

        jLabel5.setText("   ");
        jLabel5.setEnabled(false);
        costSheetPane.add(jLabel5);

        sprRecipeCostValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        sprRecipeCostValue.setModel(new SpinnerNumberModel(0.0d, null, null, 1.0d));
        sprRecipeCostValue.setEnabled(false);
        sprRecipeCostValue.setValue(0.0d);
        costSheetPane.add(sprRecipeCostValue);

        jLabel1.setText("   ");
        jLabel1.setEnabled(false);
        costSheetPane.add(jLabel1);

        northPane.add(costSheetPane);

        costPane.setLayout(new BoxLayout(costPane, BoxLayout.LINE_AXIS));

        jLabel16.setText("   ");
        jLabel16.setEnabled(false);
        costPane.add(jLabel16);

        lblProductionCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblProductionCostKey.setText("Costo de Producción:");
        lblProductionCostKey.setEnabled(false);
        costPane.add(lblProductionCostKey);

        jLabel17.setText("   ");
        jLabel17.setEnabled(false);
        costPane.add(jLabel17);

        sprProductionCostValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        sprProductionCostValue.setModel(new SpinnerNumberModel(0.0d, null, null, 1.0d));
        sprProductionCostValue.setEnabled(false);
        sprProductionCostValue.setValue(0.0d);
        costPane.add(sprProductionCostValue);

        jLabel11.setText("   ");
        jLabel11.setEnabled(false);
        costPane.add(jLabel11);

        lblUtilityKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblUtilityKey.setText("Utilidad:");
        lblUtilityKey.setEnabled(false);
        costPane.add(lblUtilityKey);

        jLabel13.setText("   ");
        jLabel13.setEnabled(false);
        costPane.add(jLabel13);

        sprUtilityValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        sprUtilityValue.setModel(new SpinnerNumberModel(0.0d, null, null, 1.0d));
        sprUtilityValue.setEnabled(false);
        sprUtilityValue.setValue(0.0d);
        costPane.add(sprUtilityValue);

        jLabel21.setText("   ");
        jLabel21.setEnabled(false);
        costPane.add(jLabel21);

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
        ArrayList<Product> list = getList();
        list.sort(Comparator.comparingInt(p -> p.getTpvCategory().getId()));
        listObjects.setModel(new CustomListModel(list));
        listObjects.setToolTipText(listObjects.getSelectedValue() != null ? ((Product) listObjects.getSelectedValue()).getRecipe().toString(): "Seleccione un elemento");
        listObjects.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                listObjectsMouseClicked(evt);
            }
        });
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

        btnRecipeView.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnRecipeView.setText("Ver Receta");
        btnRecipeView.setNextFocusableComponent(tfDescValue);
        btnRecipeView.addActionListener(this::btnRecipeViewActionPerformed);
        crudButtons.add(btnRecipeView);

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

        btnDelete.setVisible(false);
        btnArchivate.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnArchivate.setText("Archivar");
        btnArchivate.setNextFocusableComponent(listObjects);
        btnArchivate.addActionListener(this::btnArchivateActionPerformed);
        crudButtons.add(btnArchivate);

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

        getContentPane().add(controlsPane, "card2");

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
            MeasureUnit measureUnit = measureUnitsCRUD.getIfContains("nidad");
            double price = (double) sprPriceValue.getValue();
            TPVCategory tpvCategory = (TPVCategory) cbTPVCategoryValues.getSelectedItem();
            CostSheet costSheet = (CostSheet) cbCostSheetValues.getSelectedItem();
            int necesaryID = itemsCRUD.getNextId(Setup.SUBCATEGORIA_PRODUCTO_COCINA);
            Product product = new Product(necesaryID, description, measureUnit, tpvCategory, costSheet, price, necesaryID, false);
            itemsCRUD.save(product);
            int option = JOptionPane.showConfirmDialog(this, "Producto registrado correctamente.\n ¿Desea agregar más?", "Confirmación", JOptionPane.YES_NO_OPTION);
            refreshFrameView();
            if (option == 0) {
                prepareViewToInsertion();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            List list = listObjects.getSelectedValuesList();
            if (list.size() == 1) {
                Product product = (Product) list.get(0);
                int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea modificar el producto elegido?", "Confirmar Modificación", JOptionPane.OK_CANCEL_OPTION);
                if (response == 0) {
                    product.setDescription(tfDescValue.getText());
                    product.setPrice((double) sprPriceValue.getValue());
                    product.setCode((int) sprCodeValue.getValue());
                    product.setCostSheet((CostSheet) cbCostSheetValues.getSelectedItem());
                    product.setTpvCategory((TPVCategory) cbTPVCategoryValues.getSelectedItem());
                    itemsCRUD.update(product);
                    refreshFrameView();
                    JOptionPane.showMessageDialog(this, "Producto modificado correctamente");
                }
            } else if (list.size() > 1) {
                updateBatch(list);
            }

        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed
    
    private void updateBatch(List list) {
        ArrayList<Product> products = new ArrayList<>(list);
        Object column = JOptionPane.showInputDialog(this, "Seleccione la categoría de punto de venta del producto.", "Precio de venta del producto", JOptionPane.PLAIN_MESSAGE, null,ItemsCRUD.editableProductFields, ItemsCRUD.editableProductFields[0]);
        Object value = JOptionPane.showInputDialog(this, "Seleccione la categoría de punto de venta del producto.", "Precio de venta del producto", JOptionPane.PLAIN_MESSAGE);
        
    }
    
    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            ArrayList<Product> selectedItems = (ArrayList<Product>) listObjects.getSelectedValuesList();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar el(los) producto(s) elegido(s)?", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                itemsCRUD.delete(selectedItems);
            }
            JOptionPane.showMessageDialog(this, "Producto(s) eliminado(s) correctamente");
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (listSelectable) {
            if (!evt.getValueIsAdjusting()) {
                Product product = (Product) listObjects.getSelectedValue();
                if (product != null) {
                    System.err.println(product.print(1, "|_"));
                    prepareViewToEdition();
                    lblIDValue.setText(product.getId() + "");
                    tfDescValue.setText(product.getDescription());
                    sprPriceValue.setValue(product.getPrice());
                    sprCodeValue.setValue(product.getCode());
                    sprRecipeCostValue.setValue(product.getRecipeCost());
                    sprProductionCostValue.setValue(product.getProductionCost());
                    sprCalculatedPriceValue.setValue(product.getCalculatedPrice());
                    sprUtilityValue.setValue(product.getLowerProfit());
                    btnRecipeView.setVisible(product instanceof ProductCocina);
                    ArrayList<CostSheet> costSheetsInCombo = ((CustomComboBoxModel<CostSheet>) cbCostSheetValues.getModel()).getArrayList();
                    for (CostSheet item : costSheetsInCombo) {
                        if (item.equals(product.getCostSheet())) {
                            cbCostSheetValues.setSelectedItem(item);
                            break;
                        }
                    }
                    ArrayList<TPVCategory> tpvCategoriesInCombo = ((CustomComboBoxModel<TPVCategory>) cbTPVCategoryValues.getModel()).getArrayList();
                    for (TPVCategory item : tpvCategoriesInCombo) {
                        if (item.equals(product.getTpvCategory())) {
                            cbTPVCategoryValues.setSelectedItem(item);
                            break;
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void btnRecipeViewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnRecipeViewActionPerformed
        try {
            Product product = (Product) listObjects.getSelectedValue();
            if (product instanceof ProductCocina productCocina) {
                RecipeViewer jDialog = new RecipeViewer(this, true, itemsCRUD, listObjects, productCocina);
                jDialog.setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnRecipeViewActionPerformed

    private void listObjectsMouseClicked(MouseEvent evt) {//GEN-FIRST:event_listObjectsMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Product product = (Product) listObjects.getSelectedValue();
                RecipeViewer jDialog = new RecipeViewer(this, true, itemsCRUD, listObjects, product);
                jDialog.setVisible(true);
            } catch (Exception ex) {
                Logger.getInstance().updateErrorLog(ex);
            }
        }
    }//GEN-LAST:event_listObjectsMouseClicked

    private void listObjectsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listObjectsKeyTyped
        currentFilter = tfFilter.getText();
        char charTyped = evt.getKeyChar();
        if (Character.isAlphabetic(charTyped) || Character.isWhitespace(charTyped) || Character.isDigit(charTyped)) {
            currentFilter = currentFilter + charTyped;
            CustomListModel<Product> model = (CustomListModel<Product>) listObjects.getModel();
            ArrayList<Product> tempList = new ArrayList<>();
            ArrayList<Product> list = model.getList();
            for (Product product : list) {
                if (product.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(product);
                }
            }
            listObjects.setModel(new CustomListModel(tempList));
            tfFilter.setText(currentFilter);
        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<Product> tempList = new ArrayList<>();
                for (Product product : (ArrayList<Product>) getList()) {
                    if (product.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                        tempList.add(product);
                    }
                }
                listObjects.setModel(new CustomListModel(tempList));
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                ArrayList<Product> products = getList();
                products.sort(Comparator.comparingInt(Product::getCode));
                listObjects.setModel(new CustomListModel(products));
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_listObjectsKeyTyped

    private void btnArchivateActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnArchivateActionPerformed
        try {
            ArrayList<Product> selectedItems = (ArrayList<Product>) listObjects.getSelectedValuesList();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea archivar el(los) producto(s) elegido(s)?", "Confirmar Archivado", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                itemsCRUD.archivate(selectedItems);
            }
            JOptionPane.showMessageDialog(this, "Producto(s) archivado(s) correctamente");
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnArchivateActionPerformed

    private void prepareViewToEdition() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        lblTPVCategoryKey.setEnabled(true);
        cbTPVCategoryValues.setEnabled(true);
        cbCostSheetValues.setEnabled(true);
        lblCostSheetKey.setEnabled(true);
        sprPriceValue.setEnabled(true);
        lblPriceKey.setEnabled(true);
        sprCodeValue.setEnabled(true);
        lblCodeKey.setEnabled(true);
        btnUpdate.setVisible(true);
        btnRecipeView.setVisible(EntitySelector.currentEntity.isWorkable());
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
        btnAdd.setVisible(false);
        crudButtons.setVisible(true);
    }

    private void prepareViewToInsertion() {
        cbCostSheetValues.setSelectedIndex(0);
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        lblTPVCategoryKey.setEnabled(true);
        lblPriceKey.setEnabled(true);
        sprPriceValue.setEnabled(true);
        cbCostSheetValues.setEnabled(true);
        cbTPVCategoryValues.setEnabled(true);
        lblCostSheetKey.setEnabled(true);
        btnAdd.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        crudButtons.setVisible(true);
        listSelectable = false;
    }

    private void refreshFrameView() {
        listObjects.setModel(new CustomListModel(itemsCRUD.getItemProductsCRUD().getList()));
        tfDescValue.setText("");
        tfDescValue.setEnabled(false);
        sprRecipeCostValue.setValue(0.0d);
        sprRecipeCostValue.setEnabled(false);
        sprProductionCostValue.setValue(0.0d);
        sprProductionCostValue.setEnabled(false);
        sprPriceValue.setValue(0.0d);
        sprPriceValue.setEnabled(false);
        sprCodeValue.setValue(0);
        sprCodeValue.setEnabled(false);
        sprCalculatedPriceValue.setValue(0.0d);
        sprCalculatedPriceValue.setEnabled(false);
        sprUtilityValue.setValue(0.0d);
        sprUtilityValue.setEnabled(false);
        lblIDValue.setText("");
        cbCostSheetValues.setEnabled(false);
        cbCostSheetValues.setSelectedItem(null);
        cbTPVCategoryValues.setEnabled(false);
        cbTPVCategoryValues.setSelectedItem(null);
        lblPriceKey.setEnabled(false);
        lblCodeKey.setEnabled(false);
        lblDescKey.setEnabled(false);
        lblTPVCategoryKey.setEnabled(false);
        lblCostSheetKey.setEnabled(false);
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnRecipeView.setVisible(false);
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnRecipeView.setVisible(false);
        btnCancel.setVisible(false);
        crudButtons.setVisible(false);
        listSelectable = true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel aPanePane;
    private JButton btnAdd;
    private JButton btnArchivate;
    private JButton btnCancel;
    private JButton btnDelete;
    private JButton btnRecipeView;
    private JButton btnSave;
    private JButton btnUpdate;
    private JComboBox<CostSheet> cbCostSheetValues;
    private JComboBox<TPVCategory> cbTPVCategoryValues;
    private JPanel centerPane;
    private JPanel controlsPane;
    private JPanel costPane;
    private JPanel costSheetPane;
    private JPanel crudButtons;
    private JPanel desciptionPane;
    private Box.Filler filler1;
    private Box.Filler filler2;
    private Box.Filler filler3;
    private JPanel filter;
    private JPanel idPanel;
    private JButton jButton1;
    private JLabel jLabel1;
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
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JLabel lblCalculatedPriceKey;
    private JLabel lblCodeKey;
    private JLabel lblCostSheetKey;
    private JLabel lblDescKey;
    private JLabel lblIDKey;
    private JLabel lblIDValue;
    private JLabel lblPriceKey;
    private JLabel lblProductionCostKey;
    private JLabel lblRecipeCostKey;
    private JLabel lblTPVCategoryKey;
    private JLabel lblUtilityKey;
    private JList<Product> listObjects;
    private JPanel northPane;
    private JPanel otherButtons;
    private JPanel southPane;
    private JSpinner sprCalculatedPriceValue;
    private JSpinner sprCodeValue;
    private JSpinner sprPriceValue;
    private JSpinner sprProductionCostValue;
    private JSpinner sprRecipeCostValue;
    private JSpinner sprUtilityValue;
    private JTextField tfDescValue;
    private JTextField tfFilter;
    // End of variables declaration//GEN-END:variables

    private ArrayList<Product> getList() {
        return itemsCRUD.getList(Setup.CATEGORIA_PRODUCTO, true);
    }
}
