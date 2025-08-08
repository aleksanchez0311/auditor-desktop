package cu.lacumbre.auditor.view.operations;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.crud.EntitiesCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.view.custom.CustomComboBoxModel;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_RMTransfer;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;

public class TransferOperGestion extends JDialog {

    private final EntitiesCRUD entitiesCRUD;
    private final OperationsCRUD operationsCRUD;
    private final Timing timing;

    public TransferOperGestion(JFrame parent, boolean lockParent, EntitiesCRUD entitiesCRUD, OperationsCRUD operationsCRUD) {
        super(parent, lockParent);
        this.entitiesCRUD = entitiesCRUD;
        this.operationsCRUD = operationsCRUD;
        timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new JPanel();
        north = new JPanel();
        jLabel1 = new JLabel();
        cbxEntities = new JComboBox<>();
        center = new JScrollPane();
        jTable1 = new CustomJTable();
        south = new JPanel();
        btnTransfer = new JButton();
        btnCancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tranferencia de Materias Primas entre Entidades");
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setEnabled(false);
        main.setLayout(new BorderLayout(6, 6));

        north.setMinimumSize(new Dimension(100, 24));
        north.setPreferredSize(new Dimension(100, 24));
        north.setLayout(new BoxLayout(north, BoxLayout.LINE_AXIS));

        jLabel1.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Entidad Final: ");
        north.add(jLabel1);

        cbxEntities.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        cbxEntities.setModel(new CustomComboBoxModel<Entity>(entitiesCRUD.getActiveList(false)));
        cbxEntities.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        cbxEntities.setSelectedIndex(0);
        cbxEntities.setVisible(!entitiesCRUD.getList().isEmpty());
        north.add(cbxEntities);

        main.add(north, BorderLayout.NORTH);

        try{
            TreeMap<RawMaterial, Double> inventory = operationsCRUD.getInventory(timing.getInstant());
            jTable1.setModel(new CustomTM_RMTransfer(inventory));
        }catch(SQLException ex){
            Logger.getInstance().updateErrorLog(ex);
        }
        center.setViewportView(jTable1);

        main.add(center, BorderLayout.CENTER);

        south.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        btnTransfer.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnTransfer.setText("Transferir");
        btnTransfer.addActionListener(this::btnTransferActionPerformed);
        south.add(btnTransfer);

        btnCancel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(this::btnCancelActionPerformed);
        south.add(btnCancel);

        main.add(south, BorderLayout.PAGE_END);

        getContentPane().add(main, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnTransferActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnTransferActionPerformed
//        int[] selectedRows = jTable1.getSelectedRows();
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        for (int row : selectedRows) {
//            arrayList.add(row);
//        }
//        if (!arrayList.isEmpty()) {
//            CustomTM_RMTransfer model = (CustomTM_RMTransfer) jTable1.getModel();
//            Object[][] dataArray = model.getDataArray();
//            Entity currentEntity = EntitySelector.currentEntity;
//            Entity destinyEntity = (Entity) cbxEntities.getSelectedItem();
//            Timing destityTiming = new Timing(destinyEntity.getCurrentDay());
//            String transferencias = "";
//            try {
//                for (int i = 0; i < dataArray.length; i++) {
//                    if (arrayList.contains(i)) {
//                        Object[] datas = dataArray[i];
//                        RawMaterial rawMaterial = (RawMaterial) datas[0];
//                        transferencias += rawMaterial.getDescription() + "          ";
//                        double toTransferAmmount = (double) datas[2];
//                        transferencias += (double) datas[1] + "          ";
//                        transferencias += toTransferAmmount + ".\n";
//                        boolean isRFSRawMaterial = rawMaterial.getSubcategory().equals(i);
//                        //TODO: COMPLETAR METODO
//                    }
//                }
//                jTable1.setModel(new CustomTM_RMTransfer(operationsCRUD.getInventory(timing.getInstant())));
//                JOptionPane.showMessageDialog(this, "Transferencia completada desde " + currentEntity.getDescription() + " hasta " + destinyEntity.getDescription() + ".\nProducto         Entidad Origen           Entidad Destino\n" + transferencias, "Información", JOptionPane.INFORMATION_MESSAGE);
//            } catch (SQLException ex) {
//                Logger.getInstance().updateErrorLog( ex);
//            }
//        }
    }//GEN-LAST:event_btnTransferActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnCancel;
    private JButton btnTransfer;
    private JComboBox<Entity> cbxEntities;
    private JScrollPane center;
    private JLabel jLabel1;
    private CustomJTable jTable1;
    private JPanel main;
    private JPanel north;
    private JPanel south;
    // End of variables declaration//GEN-END:variables

}
