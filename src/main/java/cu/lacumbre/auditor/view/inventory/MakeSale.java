package cu.lacumbre.auditor.view.inventory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import com.toedter.calendar.JDateChooser;
import cu.lacumbre.auditor.DaysController;
import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.ItemsCRUD;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.crud.Mapper;
import cu.lacumbre.auditor.model.Ingredient;
import cu.lacumbre.auditor.view.utils.CheckerAmmounts;
import cu.lacumbre.auditor.view.utils.LoadCuadre;
import cu.lacumbre.excelreaper.NullCellException;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import org.apache.poi.ss.formula.CollaboratingWorkbooksEnvironment.WorkbookNotFoundException;

public final class MakeSale extends JDialog {

    private final OperationsCRUD operationsCRUD;
    private final ItemsCRUD itemsCRUD;
    private final DaysController daysController;
    private final Timing timing;
    private File selectedFile;
    private Mapper mapper;
    private final CheckerAmmounts checker;
    private LoadCuadre loadCuadre;
    private final Connection connection;

    public MakeSale(JFrame parent, boolean lockParent, Connection connection, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, DaysController daysController) throws SQLException {
        super(parent, lockParent);
        this.connection = connection;
        this.operationsCRUD = operationsCRUD;
        this.itemsCRUD = itemsCRUD;
        this.daysController = daysController;
        this.checker = new CheckerAmmounts(this, itemsCRUD, Setup.CATEGORIA_PRODUCTO, false);
        this.mapper = new Mapper(connection, itemsCRUD);
        timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new JPanel();
        northPane = new JPanel();
        lblTotalSaleValueKey = new JLabel();
        filler1 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        lblTotalSaleValueValue = new JLabel();
        filler6 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        lblTotalSaleAmmountKey = new JLabel();
        filler7 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        lblTotalSaleAmmountValue = new JLabel();
        filler4 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        lblDescKey = new JLabel();
        filler2 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        dcDateValue = new JDateChooser();
        filler5 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        lblPriceKey = new JLabel();
        filler3 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        spPriceValue = new JSpinner();
        itemsPanel = new JScrollPane();
        southPane = new JPanel();
        jPanel1 = new JPanel();
        btnBrowse = new JButton();
        btnSaveOperations = new JButton();
        filler8 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        jPanel2 = new JPanel();
        btnSaleSumary = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrar Venta de la Jornada");
        setMaximumSize(new Dimension(1200, 156));
        setMinimumSize(new Dimension(600, 156));
        getContentPane().setLayout(new CardLayout(10, 10));

        mainPanel.setMinimumSize(new Dimension(590, 136));
        mainPanel.setPreferredSize(new Dimension(1180, 136));
        mainPanel.setLayout(new BorderLayout(10, 10));

        northPane.setMaximumSize(new Dimension(32839, 29));
        northPane.setMinimumSize(new Dimension(580, 29));
        northPane.setPreferredSize(new Dimension(1160, 29));
        northPane.setLayout(new BoxLayout(northPane, BoxLayout.LINE_AXIS));

        lblTotalSaleValueKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalSaleValueKey.setText("Venta Total:");
        northPane.add(lblTotalSaleValueKey);
        northPane.add(filler1);

        lblTotalSaleValueValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalSaleValueValue.setText("0");
        northPane.add(lblTotalSaleValueValue);
        northPane.add(filler6);

        lblTotalSaleAmmountKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalSaleAmmountKey.setText("Cantidad de Productos Vendidos:");
        northPane.add(lblTotalSaleAmmountKey);
        northPane.add(filler7);

        lblTotalSaleAmmountValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalSaleAmmountValue.setText("0");
        northPane.add(lblTotalSaleAmmountValue);
        northPane.add(filler4);

        lblDescKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblDescKey.setText("Fecha:");
        lblDescKey.setEnabled(false);
        lblDescKey.setMaximumSize(new Dimension(42, 29));
        lblDescKey.setMinimumSize(new Dimension(42, 29));
        lblDescKey.setPreferredSize(new Dimension(42, 29));
        northPane.add(lblDescKey);
        northPane.add(filler2);

        dcDateValue.setToolTipText("");
        dcDateValue.setDate(timing.getDate());
        dcDateValue.setEnabled(false);
        dcDateValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        dcDateValue.setMaximumSize(new Dimension(150, 29));
        dcDateValue.setMinimumSize(new Dimension(150, 29));
        dcDateValue.setPreferredSize(new Dimension(150, 29));
        northPane.add(dcDateValue);
        northPane.add(filler5);

        lblPriceKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblPriceKey.setText("Importe:");
        lblPriceKey.setEnabled(false);
        lblPriceKey.setMaximumSize(new Dimension(58, 29));
        lblPriceKey.setMinimumSize(new Dimension(58, 29));
        lblPriceKey.setPreferredSize(new Dimension(58, 29));
        northPane.add(lblPriceKey);
        northPane.add(filler3);

        spPriceValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spPriceValue.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spPriceValue.setEnabled(false);
        spPriceValue.setMaximumSize(new Dimension(70, 29));
        spPriceValue.setMinimumSize(new Dimension(70, 29));
        spPriceValue.setPreferredSize(new Dimension(70, 29));
        spPriceValue.setValue(0.0d);
        northPane.add(spPriceValue);

        mainPanel.add(northPane, BorderLayout.NORTH);

        itemsPanel.setMinimumSize(new Dimension(580, 58));

        itemsPanel.setPreferredSize(new Dimension(1160, 58));
        itemsPanel = checker.getPanel(mainPanel);

        mainPanel.add(itemsPanel, BorderLayout.CENTER);

        southPane.setMaximumSize(new Dimension(32839, 29));
        southPane.setMinimumSize(new Dimension(580, 29));
        southPane.setPreferredSize(new Dimension(1160, 29));
        southPane.setLayout(new BoxLayout(southPane, BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        btnBrowse.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnBrowse.setText("Importar Cuadre");
        btnBrowse.addActionListener(this::btnBrowseActionPerformed);
        jPanel1.add(btnBrowse);

        btnSaveOperations.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSaveOperations.setText("Guardar Operaciones");
        btnSaveOperations.addActionListener(this::btnSaveOperationsActionPerformed);
        jPanel1.add(btnSaveOperations);

        southPane.add(jPanel1);
        southPane.add(filler8);

        jPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        btnSaleSumary.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSaleSumary.setText("Ver Resumen");
        btnSaleSumary.addActionListener(this::btnSaleSumaryActionPerformed);
        jPanel2.add(btnSaleSumary);

        southPane.add(jPanel2);

        mainPanel.add(southPane, BorderLayout.SOUTH);

        getContentPane().add(mainPanel, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public void setLoadCuadre(LoadCuadre loadCuadre) {
        this.loadCuadre = loadCuadre;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public CheckerAmmounts getChecker() {
        return checker;
    }

    public JScrollPane getItemsPanel() {
        return itemsPanel;
    }

    public JButton getBtnLoadCuadre() {
        return btnBrowse;
    }

    public JButton getBtnSaveOperations() {
        return btnSaveOperations;
    }

    public JLabel getLblTotalSaleValue() {
        return lblTotalSaleValueValue;
    }

    public JLabel getLblTotalSaleAmmount() {
        return lblTotalSaleAmmountValue;
    }

    public LoadCuadre getLoadCuadre() {
        return loadCuadre;
    }

    private void btnSaveOperationsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaveOperationsActionPerformed
        try {
            int option = JOptionPane.showConfirmDialog(this, "Está seguro que desea guardar las operaciones de venta establecidas?", "Confirmar Venta", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                ArrayList<Operation> newOperations = new ArrayList<>();
                for (Ingredient item : checker.getSelectedItems()) {
                    Product selectedItem = (Product) item.getItem();
                    double selectedAmmount = item.getAmmount();
                    double magic = (100 - EntitySelector.currentEntity.getMAmmount()) / 100;
                    double ammount = EntitySelector.currentEntity.isMagic() ? Math.round(selectedAmmount - (selectedAmmount * magic)) : selectedAmmount;
                    if (ammount > 0) {
                        newOperations.add(new Operation(selectedItem, timing.atEndOfDay(), ammount, selectedItem.getPrice() * ammount, false, false, false));
                    }
                }
                operationsCRUD.save(newOperations);
                daysController.fillDay();
                JOptionPane.showMessageDialog(this, "Venta del " + timing.getLocalDate() + " registrada correctamente.");
                dispose();
                btnSaleSumary.doClick();
            }
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnSaveOperationsActionPerformed

    private void btnBrowseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        try {
            mapper.getMap();
            loadCuadre = new LoadCuadre(connection, this, operationsCRUD, itemsCRUD);
            selectedFile = LoadCuadre.browseCuadreFile();
            loadCuadre.loadCuadre();
        } catch (IOException | SQLException | NullCellException | WorkbookNotFoundException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnSaleSumaryActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSaleSumaryActionPerformed
        SaleFromDate saleFromDate = new SaleFromDate((JFrame) this.getParent(), false, operationsCRUD, itemsCRUD, daysController);
        saleFromDate.setLocationRelativeTo(this.getParent());
        saleFromDate.setResizable(true);
        saleFromDate.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnSaleSumaryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnBrowse;
    private JButton btnSaleSumary;
    private JButton btnSaveOperations;
    private JDateChooser dcDateValue;
    private Box.Filler filler1;
    private Box.Filler filler2;
    private Box.Filler filler3;
    private Box.Filler filler4;
    private Box.Filler filler5;
    private Box.Filler filler6;
    private Box.Filler filler7;
    private Box.Filler filler8;
    private JScrollPane itemsPanel;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel lblDescKey;
    private JLabel lblPriceKey;
    private JLabel lblTotalSaleAmmountKey;
    private JLabel lblTotalSaleAmmountValue;
    private JLabel lblTotalSaleValueKey;
    private JLabel lblTotalSaleValueValue;
    private JPanel mainPanel;
    private JPanel northPane;
    private JPanel southPane;
    private JSpinner spPriceValue;
    // End of variables declaration//GEN-END:variables

}
