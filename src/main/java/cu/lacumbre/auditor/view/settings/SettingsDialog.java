package cu.lacumbre.auditor.view.settings;

import cu.lacumbre.auditor.DashBoard;
import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.EntitiesCRUD;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.view.custom.CustomComboBoxModel;
import cu.lacumbre.auditor.view.utils.CustomComparator;
import cu.lacumbre.auditor.view.utils.MatchingCategoriesException;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Pair;
import cu.lacumbre.utils.Settings;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsDialog extends JDialog {

    private final Connection connection;
    private CustomComparator customComparator;
    private final DashBoard dashBoard;
    private final Properties options;

    public SettingsDialog(Frame parent, boolean modal, Connection connection, ItemsCRUD itemsCRUD, CustomComparator customComparator) {
        super(parent, modal);
        this.options = Setup.options;
        this.dashBoard = (DashBoard) parent;
        this.connection = connection;
        this.customComparator = customComparator;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main = new JPanel();
        west = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTree1 = new JTree();
        center = new JPanel();
        panelEnterpriceName = new JPanel();
        lblEnterpriseNameKey = new JLabel();
        filler1 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        txfEnterpriceNameValue = new JTextField();
        panelSorting = new JPanel();
        lblProductSortKey = new JLabel();
        filler2 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        cbxProductSortValue = new JComboBox<>();
        panelEntity = new JPanel();
        lblEntityNameKey = new JLabel();
        filler3 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        txfEntityNameValue = new JTextField();
        filler5 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        ckxShowArchivated = new JCheckBox();
        filler4 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        chxIsWorkable = new JCheckBox();
        panelMagic = new JPanel();
        chxMagicEnabled = new JCheckBox();
        jSpinner1 = new JSpinner();
        south = new JPanel();
        btnAceptar = new JButton();
        btnClose = new JButton();
        btnApply = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuración");
        getContentPane().setLayout(new CardLayout(10, 10));

        Main.setLayout(new BorderLayout(5, 5));

        west.setMinimumSize(new Dimension(200, 23));
        west.setPreferredSize(new Dimension(200, 484));
        west.setLayout(new CardLayout());

        jScrollPane1.setViewportView(jTree1);

        west.add(jScrollPane1, "card2");

        Main.add(west, BorderLayout.WEST);

        center.setEnabled(false);
        center.setLayout(new GridLayout(10, 0, 0, 5));

        panelEnterpriceName.setLayout(new BoxLayout(panelEnterpriceName, BoxLayout.LINE_AXIS));

        lblEnterpriseNameKey.setText("Nombre de la Empresa:");
        panelEnterpriceName.add(lblEnterpriseNameKey);
        panelEnterpriceName.add(filler1);

        txfEnterpriceNameValue.setText((String) options.get("showArchivated"));
        panelEnterpriceName.add(txfEnterpriceNameValue);

        center.add(panelEnterpriceName);

        panelSorting.setLayout(new BoxLayout(panelSorting, BoxLayout.LINE_AXIS));

        lblProductSortKey.setText("Orden de Productos:");
        panelSorting.add(lblProductSortKey);
        panelSorting.add(filler2);

        try{
            cbxProductSortValue.setModel(new CustomComboBoxModel<Pair>(customComparator.getPairs()));
            cbxProductSortValue.setSelectedItem(customComparator.getSelectedColumn());
        }catch(SQLException ex){
            Logger.getInstance().updateErrorLog(ex);
        }
        panelSorting.add(cbxProductSortValue);

        center.add(panelSorting);

        panelEntity.setLayout(new BoxLayout(panelEntity, BoxLayout.LINE_AXIS));

        lblEntityNameKey.setText("Nombre de la Entidad:");
        panelEntity.add(lblEntityNameKey);
        panelEntity.add(filler3);

        txfEntityNameValue.setText(EntitySelector.currentEntity.getDescription());
        panelEntity.add(txfEntityNameValue);
        panelEntity.add(filler5);

        ckxShowArchivated.setText("Mostrar Archivadas");
        ckxShowArchivated.setSelected((Boolean.parseBoolean((String) options.get("showArchivated"))));
        ckxShowArchivated.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ckxShowArchivatedActionPerformed(evt);
            }
        });
        panelEntity.add(ckxShowArchivated);
        panelEntity.add(filler4);

        chxIsWorkable.setSelected(EntitySelector.currentEntity.isWorkable());
        chxIsWorkable.setText("Entidad Elaboradora");
        chxIsWorkable.setEnabled(false);
        panelEntity.add(chxIsWorkable);

        center.add(panelEntity);

        panelMagic.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        chxMagicEnabled.setSelected(EntitySelector.currentEntity.isMagic());
        chxMagicEnabled.setText("Habilitar Magia");
        chxMagicEnabled.setEnabled(false);
        chxMagicEnabled.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                chxMagicEnabledMouseClicked(evt);
            }
        });
        panelMagic.add(chxMagicEnabled);

        jSpinner1.setModel(new SpinnerNumberModel(0.0d, 0.0d, 100.0d, 1.0d));
        jSpinner1.setEnabled(false);
        jSpinner1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });
        panelMagic.add(jSpinner1);

        center.add(panelMagic);

        Main.add(center, BorderLayout.CENTER);

        south.setMinimumSize(new Dimension(300, 38));
        south.setPreferredSize(new Dimension(871, 38));
        south.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 5));

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        south.add(btnAceptar);

        btnClose.setText("Cerrar");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        south.add(btnClose);

        btnApply.setText("Aplicar");
        btnApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });
        south.add(btnApply);

        Main.add(south, BorderLayout.SOUTH);

        jSpinner1.setVisible(EntitySelector.currentEntity.isMagic());
        jSpinner1.setValue(EntitySelector.currentEntity.getMAmmount());

        getContentPane().add(Main, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnAceptarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        btnApply.doClick();
        btnClose.doClick();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnApplyActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        try {
            customComparator = new CustomComparator((Pair) cbxProductSortValue.getSelectedItem());
            options.put("enterpriseName", txfEnterpriceNameValue.getText());
            options.put("showArchivated", String.valueOf(ckxShowArchivated.isSelected()));
            options.put("comparator", customComparator.getSelectedColumn().toString());
            Settings.updateOptions(options);
            EntitySelector.currentEntity.setDescription(txfEntityNameValue.getText());
            dashBoard.setItemsCRUD(new ItemsCRUD<>(connection, EntitySelector.currentEntity, customComparator));
            dashBoard.setCustomComparator(customComparator);
            new EntitiesCRUD(connection).update(EntitySelector.currentEntity);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnApplyActionPerformed

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        try {
            EntitySelector.currentEntity.setmAmmount((double) jSpinner1.getValue());
            new EntitiesCRUD(connection).update(EntitySelector.currentEntity);
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_jSpinner1StateChanged

    private void chxMagicEnabledMouseClicked(MouseEvent evt) {//GEN-FIRST:event_chxMagicEnabledMouseClicked
        int clickCount = evt.getClickCount();
        switch (clickCount) {
            case 5 ->
                jSpinner1.setEnabled(true);
            case 6 ->
                jSpinner1.setEnabled(false);
            default -> {
            }
        }
    }//GEN-LAST:event_chxMagicEnabledMouseClicked

    private void ckxShowArchivatedActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ckxShowArchivatedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckxShowArchivatedActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel Main;
    private JButton btnAceptar;
    private JButton btnApply;
    private JButton btnClose;
    private JComboBox<Pair> cbxProductSortValue;
    private JPanel center;
    private JCheckBox chxIsWorkable;
    private JCheckBox chxMagicEnabled;
    private JCheckBox ckxShowArchivated;
    private Box.Filler filler1;
    private Box.Filler filler2;
    private Box.Filler filler3;
    private Box.Filler filler4;
    private Box.Filler filler5;
    private JScrollPane jScrollPane1;
    private JSpinner jSpinner1;
    private JTree jTree1;
    private JLabel lblEnterpriseNameKey;
    private JLabel lblEntityNameKey;
    private JLabel lblProductSortKey;
    private JPanel panelEnterpriceName;
    private JPanel panelEntity;
    private JPanel panelMagic;
    private JPanel panelSorting;
    private JPanel south;
    private JTextField txfEnterpriceNameValue;
    private JTextField txfEntityNameValue;
    private JPanel west;
    // End of variables declaration//GEN-END:variables
}
