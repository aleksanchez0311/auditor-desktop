package cu.lacumbre.auditor.view.inventory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.DaysController;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Ingredient;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.ProductListo;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_RMDatePreviewedOutcomesSummary;
import cu.lacumbre.component.loader.Processor;
import cu.lacumbre.component.loader.Task;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.awt.Font;

public class RawMaterialsFromDate extends JDialog {

    private final OperationsCRUD operationsCRUD;
    private final ItemsCRUD itemsCRUD;
    private final DaysController daysController;
    private final TreeMap<RawMaterial, Double> previwedOutcomes;
    private final TreeMap<RawMaterial, Double> existences;
    private final TreeMap<RawMaterial, Double> previewedIncomed;
    private final ArrayList<Operation> relatedOperations;
    private final JFrame parent;
    private final Timing timing;

    public RawMaterialsFromDate(JFrame parent, boolean modal, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, DaysController daysController, ArrayList<Operation> operationsList, Timing timing) throws SQLException {
        super(parent, modal);
        this.operationsCRUD = operationsCRUD;
        this.itemsCRUD = itemsCRUD;
        this.daysController = daysController;
        this.timing = timing;
        this.parent = parent;
        this.previwedOutcomes = new TreeMap<>(Comparator.comparingInt(RawMaterial::getCode));
        this.relatedOperations = new ArrayList<>();
        for (Operation operation : operationsList) {
            Product product = (Product) operation.getItem();
            ArrayList<Ingredient> deepIngredients = itemsCRUD.loadDeepIngredients(product);
            for (Ingredient ingredient : deepIngredients) {
                RawMaterial RawMaterial = (RawMaterial) ingredient.getItem();
                double ammount = ingredient.getAmmount() * operation.getAmmount();
                previwedOutcomes.merge(RawMaterial, ammount, (oldAmmount, newAmmount) -> oldAmmount + newAmmount);
            }
            relatedOperations.add(operation);
        }
        this.existences = this.operationsCRUD.getInventory(timing.atEndOfDay());
        this.previewedIncomed = new TreeMap<>(Comparator.comparingInt(RawMaterial::getCode));
        for (Map.Entry<RawMaterial, Double> requestedOutcomes : previwedOutcomes.entrySet()) {
            RawMaterial outcomingRawMaterial = requestedOutcomes.getKey();
            double outcomingAmmount = (double) requestedOutcomes.getValue();
            double existingAmmount = existences.get(outcomingRawMaterial) == null ? 0.0d : (double) existences.get(outcomingRawMaterial);
            double existence = existingAmmount - outcomingAmmount;
            if (existence < 0) {
                previewedIncomed.put(outcomingRawMaterial, existence * -1);
            } else {
                Iterator<Operation> iterator = relatedOperations.iterator();
                while (iterator.hasNext()) {
                    Operation relatedOperation = iterator.next();
                    Product product = (Product) relatedOperation.getItem();
                    if (product instanceof ProductListo productListo) {
                        if (productListo.hasItAsIngredient(outcomingRawMaterial)) {
                            iterator.remove(); // Safe removal using Iterator  
                        }
                    }
                }
            }
        }
        for (Map.Entry<RawMaterial, Double> entry : previwedOutcomes.entrySet()) {
            RawMaterial key = entry.getKey();
            Double value = entry.getValue();
            if (existences.get(key) != null) {
                this.previwedOutcomes.put(key, value);
            }
        }
        initComponents();
        boolean enableBtnOperationsAdjust = !timing.getLocalDate().isBefore(EntitySelector.currentEntity.getCurrentDay());
        btnOperationsAdjust.setEnabled(enableBtnOperationsAdjust);
        String title = "Resumen de salidas del almacén del " + timing.getLocalDate().toString();
        title += enableBtnOperationsAdjust ? "" : " - [JORNADA COMPLETADA]";
        setTitle(title);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTable1 = new CustomJTable();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        btnOperationsAdjust = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(6, 6));

        jPanel1.setPreferredSize(new Dimension(600, 360));
        jPanel1.setLayout(new CardLayout());

        CustomTM_RMDatePreviewedOutcomesSummary model = new CustomTM_RMDatePreviewedOutcomesSummary(previwedOutcomes, existences);
        jTable1.setModel(model);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, "card2");

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        jPanel2.setPreferredSize(new Dimension(600, 40));
        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.LINE_AXIS));

        jPanel3.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnOperationsAdjust.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnOperationsAdjust.setText("Completar Jornada");
        btnOperationsAdjust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOperationsAdjustActionPerformed(evt);
            }
        });
        jPanel3.add(btnOperationsAdjust);

        jPanel2.add(jPanel3);

        getContentPane().add(jPanel2, BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOperationsAdjustActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnOperationsAdjustActionPerformed
        try {
            if (previewedIncomed.isEmpty()) {
                int selection = JOptionPane.showConfirmDialog(this, "Listo, no es necesaria ninguna entrada de mercancía\nSe realizarán las salidas para actualizar la base de datos", "Completar Jornada", JOptionPane.OK_CANCEL_OPTION);
                if (selection == 0) {
                    ArrayList<Operation> newOperations = new ArrayList<>();
                    for (Map.Entry<RawMaterial, Double> entry : previwedOutcomes.entrySet()) {
                        RawMaterial RawMaterial = entry.getKey();
                        Double ammount = entry.getValue();
                        Instant atStartOfDay = timing.getInstant();
                        newOperations.add(new Operation(RawMaterial, atStartOfDay, ammount, ammount * RawMaterial.getLastCost(), false, false, false));
                    }
                    operationsCRUD.save(newOperations);
                    new Processor(new Task(null, "Cerrando Jornada") {
                        @Override
                        public void codeToRun() {
                            try {
                                daysController.closeDay();
                            } catch (SQLException ex) {
                                Logger.getInstance().updateErrorLog(ex);
                            }
                        }
                    }).start();
                    JOptionPane.showMessageDialog(this, "Jornada completada");
                }
            } else {
                int option = JOptionPane.showConfirmDialog(this, "Son necesarias algunas operaciones de entrada. ¿Deseas guardar el csv?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    File lastDirectoryOfCSV = Setup.getLastDirectoryOf(Setup.CSVS_DIRECTORY);
                    JFileChooser chooser = new JFileChooser(lastDirectoryOfCSV);
                    chooser.setSelectedFile(new File(chooser.getCurrentDirectory().getPath() + System.getProperty("file.separator") + timing.getLocalDate().getMonthValue() + "-" + timing.getLocalDate().getDayOfMonth() + ".csv"));
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
                        fw.append("idProducto").append(';').append("descripcion").append(';').append("compraMinima").append(';').append("u/m").append(';').append("costoAprox").append('\n');
                        for (Map.Entry<RawMaterial, Double> entry : previewedIncomed.entrySet()) {
                            RawMaterial RawMaterial = entry.getKey();
                            double ammount = entry.getValue();
                            String stringID = RawMaterial.getId() + "";
                            String description = RawMaterial.getDescription();
                            String stringAmmount = ammount + "";
                            String um = RawMaterial.getMeasureUnit().getAbrev();
                            String stringCost = RawMaterial.getLastCost() > 0 ? (RawMaterial.getLastCost() * ammount) + "" : "Desconocido";
                            fw.append(stringID).append(';').append(description).append(';').append(stringAmmount).append(';').append(um).append(';').append(stringCost).append('\n');
                        }
                        fw.append("\n;\n");
                        for (Map.Entry<RawMaterial, Double> entry : previwedOutcomes.entrySet()) {
                            RawMaterial RawMaterial = entry.getKey();
                            double ammount = entry.getValue();
                            String stringID = RawMaterial.getId() + "";
                            String description = RawMaterial.getDescription();
                            String stringAmmount = ammount + "";
                            String um = RawMaterial.getMeasureUnit().getAbrev();
                            String stringCost = RawMaterial.getLastCost() > 0 ? (RawMaterial.getLastCost() * ammount) + "" : "Desconocido";
                            fw.append(stringID).append(';').append(description).append(';').append(stringAmmount).append(';').append(um).append(';').append(stringCost).append('\n');
                        }
                        fw.append("\n;\n");

                        for (Operation operation : relatedOperations) {
                            String stringID = operation.getId() + "";
                            String um = operation.getItem().getDescription();
                            fw.append(stringID).append(';').append(um).append('\n');
                        }
                        fw.append(timing.getLocalDate().toEpochDay() + "\n");
                        fw.close();
                        JOptionPane.showMessageDialog(this, "Operaciones de entrada/salida por ejecutar exportadas correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                new LoadCSV(parent, false, operationsCRUD, itemsCRUD, daysController, previewedIncomed, previwedOutcomes, relatedOperations).setVisible(true);
            }
            dispose();
        } catch (IOException | SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnOperationsAdjustActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnOperationsAdjust;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private CustomJTable jTable1;
    // End of variables declaration//GEN-END:variables
}
