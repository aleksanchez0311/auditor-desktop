package cu.lacumbre.auditor;

import com.toedter.calendar.JDateChooser;
import cu.lacumbre.auditor.crud.EntitiesCRUD;
import cu.lacumbre.auditor.crud.PostgreSQL;
import cu.lacumbre.auditor.model.Entity;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import cu.lacumbre.auditor.view.custom.CustomComboBoxModel;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class EntitySelector extends JFrame {

    public static Entity currentEntity;
    private final EntitiesCRUD entitiesCRUD;
    private final PostgreSQL postgreSQL;

    public EntitySelector(PostgreSQL postgreSQL, EntitiesCRUD entitiesCRUD) throws SQLException {
        this.postgreSQL = postgreSQL;
        this.entitiesCRUD = entitiesCRUD;
        setIconImage(new ImageIcon(getClass().getResource("/assets/icon.png")).getImage());
        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    postgreSQL.disconnect(new Exception().getStackTrace());
                } catch (SQLException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new ButtonGroup();
        jPanel1 = new JPanel();
        jPanel4 = new JPanel();
        chxEnterpriseLevel = new JCheckBox();
        filler5 = new Box.Filler(new Dimension(0, 10), new Dimension(0, 10), new Dimension(32767, 10));
        jPanel3 = new JPanel();
        jLabel1 = new JLabel();
        filler3 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        filler1 = new Box.Filler(new Dimension(0, 10), new Dimension(0, 10), new Dimension(32767, 10));
        cbxEntities = new JComboBox<>();
        filler2 = new Box.Filler(new Dimension(0, 10), new Dimension(0, 10), new Dimension(32767, 10));
        jPanel2 = new JPanel();
        activated = new JPanel();
        filler9 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 32767));
        rbShowActive = new JRadioButton();
        rbShowAll = new JRadioButton();
        btnCreate = new JButton();
        btnSelect = new JButton();
        btnSalir = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(Setup.enterpriseName);
        getContentPane().setLayout(new CardLayout(10, 10));

        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS));

        jPanel4.setLayout(new BoxLayout(jPanel4, BoxLayout.LINE_AXIS));

        chxEnterpriseLevel.setFont(new Font("Segoe UI", 3, 14)); // NOI18N
        chxEnterpriseLevel.setText("Nivel Empresarial");
        chxEnterpriseLevel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                chxEnterpriseLevelItemStateChanged(evt);
            }
        });
        jPanel4.add(chxEnterpriseLevel);

        jPanel1.add(jPanel4);
        jPanel1.add(filler5);

        jLabel1.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione la entidad de "+Setup.enterpriseName+" en la que desea trabajar:");
        jPanel3.add(jLabel1);
        jPanel3.add(filler3);

        jPanel1.add(jPanel3);
        jPanel1.add(filler1);

        cbxEntities.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        cbxEntities.setModel(new CustomComboBoxModel<Entity>(entitiesCRUD.getActiveList(false)));
        cbxEntities.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        cbxEntities.setSelectedIndex(0);
        cbxEntities.setVisible(!entitiesCRUD.isEmpty());
        btnSelect.setVisible(!entitiesCRUD.isEmpty());
        jPanel1.add(cbxEntities);
        jPanel1.add(filler2);

        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.LINE_AXIS));

        activated.setLayout(new FlowLayout(FlowLayout.LEFT));
        activated.add(filler9);

        buttonGroup1.add(rbShowActive);
        rbShowActive.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        rbShowActive.setSelected(true);
        rbShowActive.setText("Mostrar Activas");
        rbShowActive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                rbShowActiveActionPerformed(evt);
            }
        });
        activated.add(rbShowActive);

        buttonGroup1.add(rbShowAll);
        rbShowAll.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        rbShowAll.setText("Mostrar Todas");
        rbShowAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                rbShowAllActionPerformed(evt);
            }
        });
        activated.add(rbShowAll);

        jPanel2.add(activated);

        btnCreate.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnCreate.setText("Crear");
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });
        jPanel2.add(btnCreate);

        btnSelect.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSelect.setText("Seleccionar");
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        jPanel2.add(btnSelect);

        btnSalir.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir);

        jPanel1.add(jPanel2);

        getContentPane().add(jPanel1, "card4");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnSelectActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        currentEntity = (Entity) cbxEntities.getSelectedItem();
        Setup.updateLimits(currentEntity.getId());
        try {
            DashBoard main = new DashBoard(postgreSQL, entitiesCRUD);
//            try {
//                OperationsCRUD operationsCRUD = new OperationsCRUD(postgreSQL.getConnection(), EntitySelector.currentEntity);
//                operationsCRUD.updateAll();
//            } catch (SQLException ex) {
//                Logger.getInstance().updateErrorLog( ex);
//            }
            main.setLocationRelativeTo(null);
            main.setVisible(true);
        } catch (IOException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
        dispose();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void btnCreateActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        try {
            String newEntityName = JOptionPane.showInputDialog(this, "Nombre de la nueva entidad", "Crear Entidad", JOptionPane.QUESTION_MESSAGE);
            if (newEntityName != null) {
                int response = JOptionPane.CLOSED_OPTION;
                Boolean isWorkableEntity = null;
                while (response == JOptionPane.CLOSED_OPTION) {
                    response = JOptionPane.showConfirmDialog(this, "¿" + newEntityName + " es una entidad productora?", "Creae Entidad", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.OK_OPTION) {
                        isWorkableEntity = true;
                        break;
                    }
                    if (response == JOptionPane.NO_OPTION) {
                        isWorkableEntity = false;
                        break;
                    }
                }
                Timing timing = new Timing();
                JDateChooser dateChooser = new JDateChooser();
                int result = JOptionPane.showConfirmDialog(null, dateChooser, "Seleccione la fecha de apertura", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    timing = new Timing(dateChooser.getDate());
                }
                if (isWorkableEntity) {
                    entitiesCRUD.save(new Entity(newEntityName, response == JOptionPane.OK_OPTION, timing.getLocalDate(), timing.getLocalDate(), false, false, true, 35, true));
                } else {
                    entitiesCRUD.save(new Entity(newEntityName, response == JOptionPane.OK_OPTION, timing.getLocalDate(), timing.getLocalDate(), false, false, false, 100, true));
                }
                cbxEntities.setModel(new CustomComboBoxModel<>(entitiesCRUD.getActiveList(chxEnterpriseLevel.isSelected())));
                cbxEntities.setSelectedIndex(0);
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnCreateActionPerformed

    private void rbShowAllActionPerformed(ActionEvent evt) {//GEN-FIRST:event_rbShowAllActionPerformed
        JRadioButton rb = (JRadioButton) evt.getSource();
        if (rb.isSelected()) {
            cbxEntities.setModel(new CustomComboBoxModel<>(entitiesCRUD.getInactiveList(chxEnterpriseLevel.isSelected())));
            cbxEntities.setSelectedIndex(0);
        }
    }//GEN-LAST:event_rbShowAllActionPerformed

    private void rbShowActiveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_rbShowActiveActionPerformed
        JRadioButton rb = (JRadioButton) evt.getSource();
        if (rb.isSelected()) {
            cbxEntities.setModel(new CustomComboBoxModel<>(entitiesCRUD.getActiveList(chxEnterpriseLevel.isSelected())));
            cbxEntities.setSelectedIndex(0);
        }
    }//GEN-LAST:event_rbShowActiveActionPerformed

    private void chxEnterpriseLevelItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_chxEnterpriseLevelItemStateChanged
        CustomComboBoxModel model = (CustomComboBoxModel) cbxEntities.getModel();
        ArrayList currentList = model.getArrayList();
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            currentList.add(entitiesCRUD.getDefaultEntity());
        } else {
            currentList.remove(entitiesCRUD.getDefaultEntity());
        }
    }//GEN-LAST:event_chxEnterpriseLevelItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel activated;
    private JButton btnCreate;
    private JButton btnSalir;
    private JButton btnSelect;
    private ButtonGroup buttonGroup1;
    private JComboBox<Entity> cbxEntities;
    private JCheckBox chxEnterpriseLevel;
    private Box.Filler filler1;
    private Box.Filler filler2;
    private Box.Filler filler3;
    private Box.Filler filler5;
    private Box.Filler filler9;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JRadioButton rbShowActive;
    private JRadioButton rbShowAll;
    // End of variables declaration//GEN-END:variables
}
