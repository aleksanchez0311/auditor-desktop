package cu.lacumbre.auditor.view.merchandise;

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
import cu.lacumbre.auditor.model.MeasureUnit;
import cu.lacumbre.auditor.model.Workable;
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
import java.time.Instant;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;

public class WorkablesGestion extends RecipableGestion{

    private final ItemsCRUD itemsCRUD;
    private final MeasureUnitsCRUD muCrud;
    boolean listSelectable = true;
    private String currentFilter;
    private long lastFiterKeyEventTime = Instant.now().toEpochMilli();

    public WorkablesGestion(JFrame parent, boolean lockParent, ItemsCRUD itemsCRUD) {
        super(parent, lockParent);
        this.itemsCRUD = itemsCRUD;
        muCrud = itemsCRUD.getMeasureUnitsCRUD();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlsPane = new JPanel();
        northPane = new JPanel();
        idPanel = new JPanel();
        lblIDKey = new JLabel();
        jLabel10 = new JLabel();
        lblIDValue = new JLabel();
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
        cbMeasureValues = new JComboBox<>();
        jLabel15 = new JLabel();
        costPane = new JPanel();
        jLabel1 = new JLabel();
        lblCostKey = new JLabel();
        jLabel5 = new JLabel();
        spCostValue = new JSpinner();
        jLabel11 = new JLabel();
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
        setTitle("Gestionar Recetas");
        getContentPane().setLayout(new CardLayout());

        controlsPane.setEnabled(false);
        controlsPane.setMaximumSize(new Dimension(2147483647, 2147483647));
        controlsPane.setMinimumSize(new Dimension(450, 450));
        controlsPane.setPreferredSize(new Dimension(450, 450));
        controlsPane.setLayout(new BorderLayout(6, 6));

        northPane.setLayout(new GridLayout(4, 1, 6, 6));

        idPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0, 0, 0)));
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

        cbMeasureValues.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        cbMeasureValues.setModel(new CustomComboBoxModel(muCrud.getList()));
        ComboBoxEditor cbe = cbMeasureValues.getEditor();
        JTextField editor = (JTextField) cbe.getEditorComponent();
        editor.setHorizontalAlignment(JTextField.RIGHT);
        cbe.setItem(editor);
        cbMeasureValues.setEditor(cbe);
        cbMeasureValues.setEnabled(false);
        measurePane.add(cbMeasureValues);

        jLabel15.setText("   ");
        measurePane.add(jLabel15);

        northPane.add(measurePane);

        costPane.setLayout(new BoxLayout(costPane, BoxLayout.LINE_AXIS));

        jLabel1.setText("   ");
        jLabel1.setEnabled(false);
        costPane.add(jLabel1);

        lblCostKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblCostKey.setText("Costo de Producción/Compra:");
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
        listObjects.setModel(new CustomListModel(getList())
        );
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

        btnUpdate.setVisible(false);
        btnRecipeView.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnRecipeView.setText("Ver Receta");
        btnRecipeView.setNextFocusableComponent(tfDescValue);
        btnRecipeView.addActionListener(this::btnRecipeViewActionPerformed);
        btnRecipeView.setVisible(false);
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
            MeasureUnit measureUnit = (MeasureUnit) cbMeasureValues.getModel().getSelectedItem();
            int necesaryID = itemsCRUD.getNextId(Setup.SUBCATEGORIA_RECETA);
            Workable workable = new Workable(necesaryID, description, measureUnit, necesaryID, false);
            itemsCRUD.save(workable);
            int option = JOptionPane.showConfirmDialog(this, "Elaborable registrado correctamente.\n ¿Desea agregar más?", "Confirmación", JOptionPane.YES_NO_OPTION);
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
            Workable workable = (Workable) listObjects.getSelectedValue();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea modificar el elaborable elegido?", "Confirmar Modificación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                workable.setDescription(tfDescValue.getText());
                workable.setMeasureUnit((MeasureUnit) cbMeasureValues.getModel().getSelectedItem());
                itemsCRUD.update(workable);
                refreshFrameView();
                JOptionPane.showMessageDialog(this, "Elaborable modificado correctamente");
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            ArrayList<Workable> selectedItems = (ArrayList<Workable>) listObjects.getSelectedValuesList();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea eliminar el(los) elaborable(s) elegido(s)?", "Confirmar Eliminación", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                itemsCRUD.delete(selectedItems);
            }
            JOptionPane.showMessageDialog(this, "Elaborable(s) eliminado(s) correctamente");
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (listSelectable) {
            if (!evt.getValueIsAdjusting()) {
                Workable workable = (Workable) listObjects.getSelectedValue();
                if (workable != null) {
                    System.err.println(workable.print(1, "|_"));
                    prepareViewToEdition();
                    lblIDValue.setText(workable.getId() + "");
                    tfDescValue.setText(workable.getDescription());
                    double itemionCost = workable.getRecipeCost();
                    spCostValue.setValue(itemionCost);
                    ArrayList<MeasureUnit> itemsInCombo = ((CustomComboBoxModel<MeasureUnit>) cbMeasureValues.getModel()).getArrayList();
                    for (MeasureUnit measureUnit : itemsInCombo) {
                        if (measureUnit.equals(workable.getMeasureUnit())) {
                            cbMeasureValues.setSelectedItem(measureUnit);
                            break;
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void btnRecipeViewActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnRecipeViewActionPerformed
        try {
            Workable workable = (Workable) listObjects.getSelectedValue();
            RecipeViewer jDialog = new RecipeViewer(this, true, itemsCRUD, listObjects, workable);
            jDialog.setVisible(true);
        } catch (Exception ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnRecipeViewActionPerformed

    private void listObjectsMouseClicked(MouseEvent evt) {//GEN-FIRST:event_listObjectsMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Workable workable = (Workable) listObjects.getSelectedValue();
                RecipeViewer viewer = new RecipeViewer(this, true, itemsCRUD, listObjects, workable);
                viewer.setVisible(true);
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
            CustomListModel<Workable> model = (CustomListModel<Workable>) listObjects.getModel();
            ArrayList<Workable> tempList = new ArrayList<>();
            ArrayList<Workable> list = model.getList();
            for (Workable workable : list) {
                if (workable.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(workable);
                }
            }
            listObjects.setModel(new CustomListModel<>(tempList));
            tfFilter.setText(currentFilter);
        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<Workable> tempList = new ArrayList<>();
                for (Workable workable : (ArrayList<Workable>) getList()) {
                    if (workable.toString().toLowerCase().contains(currentFilter.toLowerCase())) {
                        tempList.add(workable);
                    }
                }
                listObjects.setModel(new CustomListModel(tempList));
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                ArrayList<Workable> workables = getList();
                workables.sort(Comparator.comparingInt(Workable::getCode));
                listObjects.setModel(new CustomListModel(workables));
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_listObjectsKeyTyped

    private void btnArchivateActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnArchivateActionPerformed
        try {
            ArrayList<Workable> selectedItems = (ArrayList<Workable>) listObjects.getSelectedValuesList();
            int response = JOptionPane.showConfirmDialog(this, "Está seguro que desea archivar el(los) elaborable(s) elegido(s)?", "Confirmar Archivado", JOptionPane.OK_CANCEL_OPTION);
            if (response == 0) {
                itemsCRUD.archivate(selectedItems);
            }
            JOptionPane.showMessageDialog(this, "Elaborable(s) archivado(s) correctamente");
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnArchivateActionPerformed

    private void prepareViewToEdition() {
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        cbMeasureValues.setEnabled(true);
        lblMeasureKey.setEnabled(true);
        btnUpdate.setVisible(true);
        btnRecipeView.setVisible(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(true);
        btnAdd.setVisible(false);
        crudButtons.setVisible(true);

    }

    private void prepareViewToInsertion() {
        cbMeasureValues.setSelectedIndex(0);
        tfDescValue.setEnabled(true);
        lblDescKey.setEnabled(true);
        lblMeasureKey.setEnabled(true);
        cbMeasureValues.setEnabled(true);
        lblCostKey.setEnabled(true);
        btnAdd.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        crudButtons.setVisible(true);
        listSelectable = false;
    }

    private void refreshFrameView() {
        try {
            itemsCRUD.reloadDB();
            listObjects.setModel(new CustomListModel(getList()));
            tfDescValue.setText("");
            tfDescValue.setEnabled(false);
            lblDescKey.setEnabled(false);
            spCostValue.setValue(0.0d);
            lblCostKey.setEnabled(false);
            lblIDValue.setText("");
            cbMeasureValues.setEnabled(false);
            cbMeasureValues.setSelectedItem(null);
            lblMeasureKey.setEnabled(false);
            btnAdd.setVisible(true);
            btnUpdate.setVisible(false);
            btnRecipeView.setVisible(false);
            btnDelete.setVisible(false);
            btnSave.setVisible(false);
            btnCancel.setVisible(false);
            crudButtons.setVisible(false);
            listSelectable = true;
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAdd;
    private JButton btnArchivate;
    private JButton btnCancel;
    private JButton btnDelete;
    private JButton btnRecipeView;
    private JButton btnSave;
    private JButton btnUpdate;
    private JComboBox<MeasureUnit> cbMeasureValues;
    private JPanel centerPane;
    private JPanel controlsPane;
    private JPanel costPane;
    private JPanel crudButtons;
    private JPanel desciptionPane;
    private JPanel filter;
    private JPanel idPanel;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel15;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JLabel lblCostKey;
    private JLabel lblDescKey;
    private JLabel lblIDKey;
    private JLabel lblIDValue;
    private JLabel lblMeasureKey;
    private JList<Workable> listObjects;
    private JPanel measurePane;
    private JPanel northPane;
    private JPanel otherButtons;
    private JPanel southPane;
    private JSpinner spCostValue;
    private JTextField tfDescValue;
    private JTextField tfFilter;
    // End of variables declaration//GEN-END:variables

    private ArrayList<Workable> getList() {
        return itemsCRUD.getList(Setup.CATEGORIA_RECETA, true);
    }

}
