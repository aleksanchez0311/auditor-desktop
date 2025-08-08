package cu.lacumbre.auditor.view.reports;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_PReport;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

public class DialogReportsProducts extends JDialog{

    private final ItemsCRUD itemsCRUD;
    private final R_Handler reportsHandler;

    public DialogReportsProducts(JFrame owner, boolean modal, Connection connection, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) {
        super(owner, modal);
        this.itemsCRUD = itemsCRUD;
        reportsHandler = new R_Handler(connection, operationsCRUD, itemsCRUD);
        initComponents();
        updateView();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new JPanel();
        center = new JScrollPane();
        tableProducts = new CustomJTable();
        south = new JPanel();
        btnPrint = new JButton();
        btnExport = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Costo de Materias Primas");
        getContentPane().setLayout(new CardLayout(10, 10));

        main.setLayout(new BorderLayout(6, 6));

        center.setBorder(BorderFactory.createTitledBorder(null, "Productos:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14))); // NOI18N

        tableProducts.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        center.setViewportView(tableProducts);

        main.add(center, BorderLayout.CENTER);

        south.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnPrint.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnPrint.setText("Exportar PDF");
        btnPrint.addActionListener(this::btnPrintActionPerformed);
        south.add(btnPrint);

        btnExport.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnExport.setText("Exportar CSV");
        btnExport.addActionListener(this::btnExportActionPerformed);
        south.add(btnExport);

        main.add(south, BorderLayout.SOUTH);

        getContentPane().add(main, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        try {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("TITLE", "Costo de Productos para la Venta");
            parameters.put("ENTITY", EntitySelector.currentEntity.getId());
            JasperViewer viewer = reportsHandler.generateReport(R_Handler.PRODUCTS, parameters);
            viewer.setVisible(true);
        } catch (SQLException | JRException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnExportActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        try {
            File lastDirectoryOfCSV = Setup.getLastDirectoryOf(Setup.CSVS_DIRECTORY);
            JFileChooser chooser = new JFileChooser(lastDirectoryOfCSV);
            chooser.setSelectedFile(new File("ProductResults.csv"));
            chooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
            int dialogOption = chooser.showSaveDialog(this);
            if (dialogOption == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                Setup.setLastDirectory(Setup.CSVS_DIRECTORY, selectedFile.getParentFile());
                if (selectedFile.getName().split("\\.").length == 0) {
                    selectedFile = new File(selectedFile.getPath().concat(".csv"));
                } else {
                    selectedFile = new File(selectedFile.getPath().split("\\.")[0].concat(".csv"));
                }
                FileWriter fw = new FileWriter(selectedFile);
                fw.append("idProducto").append(';').append("descripcion").append(';').append("u/m").append(';').append("precio").append(';').append("costoReceta").append(';').append("costoProduccion").append(';').append("utilidad").append('\n');
                CustomTM_PReport model = (CustomTM_PReport) tableProducts.getModel();
                for (Product product : model.getProducts()) {
                    String stringID = product.getId() + "";
                    String description = product.getDescription();
                    String um = product.getMeasureUnit().getAbrev();
                    String stringPrice = product.getPrice() + "";
                    String stringRecipeCost = product.getRecipeCost() + "";
                    String stringProductionCost = product.getProductionCost() + "";
                    String stringProfit = product.getProfit() + "";
                    fw.append(stringID).append(';').append(description).append(';').append(um).append(';').append(stringPrice).append(';').append(stringRecipeCost).append(';').append(stringProductionCost).append(';').append(stringProfit).append('\n');
                }
                fw.append("\n;\n");
                fw.close();
                JOptionPane.showMessageDialog(this, "Resultado de Productos exportado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (HeadlessException | IOException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void updateView() {
        ArrayList<Product> products = getList();
        CustomTM_PReport tableModel = new CustomTM_PReport(products);
        tableProducts.setModel(tableModel);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnExport;
    private JButton btnPrint;
    private JScrollPane center;
    private JPanel main;
    private JPanel south;
    private CustomJTable tableProducts;
    // End of variables declaration//GEN-END:variables

    public ArrayList getList() {
        return itemsCRUD.getList(Setup.CATEGORIA_PRODUCTO, true);
    }
    
}
