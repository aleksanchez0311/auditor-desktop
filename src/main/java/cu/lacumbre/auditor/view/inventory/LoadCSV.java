package cu.lacumbre.auditor.view.inventory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.crud.DaysController;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.ProductListo;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.view.custom.CustomJTable;
import cu.lacumbre.auditor.view.custom.CustomTM_RMDateAdjustEditable;
import cu.lacumbre.auditor.view.custom.CustomTM_RMDateAdjust;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;

public class LoadCSV extends JDialog {

    private final OperationsCRUD operationsCRUD;
    private final ItemsCRUD itemsCRUD;
    private final DaysController daysController;
    private TreeMap<RawMaterial, Double> previewedIncomes;
    private TreeMap<RawMaterial, Double> previewedOutcomes;
    private ArrayList<Operation> ralatedOperations;
    private Timing timing;
    private boolean poundsFlag = false;
    private boolean gramsFlag = false;

    public LoadCSV(JFrame parent, boolean modal, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, DaysController daysController) {
        this(parent, modal, operationsCRUD, itemsCRUD, daysController, new TreeMap<>(Comparator.comparingInt(RawMaterial::getCode)), new TreeMap<>(Comparator.comparingInt(RawMaterial::getCode)), new ArrayList<>());
    }

    public LoadCSV(JFrame parent, boolean modal, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, DaysController daysController, TreeMap<RawMaterial, Double> previewedIncomes, TreeMap<RawMaterial, Double> previewedOutcomed, ArrayList<Operation> relatedOperations) {
        super(parent, modal);
        this.daysController = daysController;
        this.operationsCRUD = operationsCRUD;
        this.itemsCRUD = itemsCRUD;
        this.ralatedOperations = relatedOperations;
        this.previewedOutcomes = previewedOutcomed;
        this.previewedIncomes = previewedIncomes;
        timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
        initComponents();

        if (previewedIncomes.isEmpty() && relatedOperations.isEmpty() && previewedOutcomed.isEmpty()) {
            tfPathValue.setVisible(true);
            btnBrowse.setVisible(true);
            jPanel5.setVisible(true);
        } else {
            CustomTM_RMDateAdjustEditable model = new CustomTM_RMDateAdjustEditable(previewedIncomes);
            tableRawMaterials.setModel(model);
            setLocationRelativeTo(null);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new JPanel();
        jPanel1 = new JPanel();
        jLabel5 = new JLabel();
        jScrollPane1 = new JScrollPane();
        tableRawMaterials = new CustomJTable();
        jLabel6 = new JLabel();
        jPanel2 = new JPanel();
        jPanel6 = new JPanel();
        jLabel1 = new JLabel();
        btnAjustarEntradas = new JButton();
        jLabel7 = new JLabel();
        btnAjustarSalidas = new JButton();
        jLabel4 = new JLabel();
        jPanel5 = new JPanel();
        tfPathValue = new JTextField();
        jLabel2 = new JLabel();
        btnBrowse = new JButton();
        jLabel3 = new JLabel();
        jPanel3 = new JPanel();
        jLabel9 = new JLabel();
        spInputPounds = new JSpinner();
        jLabel8 = new JLabel();
        jLabel12 = new JLabel();
        jLabel10 = new JLabel();
        spInputGrams = new JSpinner();
        jLabel11 = new JLabel();
        jLabel13 = new JLabel();
        jLabel14 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ajustar Entradas/Salidas de Materias Primas de la Jornada");
        getContentPane().setLayout(new BorderLayout(6, 6));

        jPanel4.setLayout(new GridLayout(1, 0, 6, 6));
        getContentPane().add(jPanel4, BorderLayout.NORTH);

        jPanel1.setPreferredSize(new Dimension(600, 360));
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.LINE_AXIS));

        jLabel5.setText("   ");
        jPanel1.add(jLabel5);

        jScrollPane1.setBorder(BorderFactory.createTitledBorder(null, "Materias Primas:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14))); // NOI18N
        jScrollPane1.setViewportView(tableRawMaterials);

        jPanel1.add(jScrollPane1);

        jLabel6.setText("   ");
        jPanel1.add(jLabel6);

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        jPanel2.setMinimumSize(new Dimension(600, 140));
        jPanel2.setPreferredSize(new Dimension(600, 140));
        jPanel2.setLayout(new GridLayout(2, 0));

        jPanel6.setLayout(new BoxLayout(jPanel6, BoxLayout.LINE_AXIS));

        jLabel1.setText("   ");
        jPanel6.add(jLabel1);

        btnAjustarEntradas.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnAjustarEntradas.setText("Ajustar Entradas");
        btnAjustarEntradas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAjustarEntradasActionPerformed(evt);
            }
        });
        jPanel6.add(btnAjustarEntradas);

        jLabel7.setText("   ");
        jPanel6.add(jLabel7);

        btnAjustarSalidas.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnAjustarSalidas.setText("Ajustar Salidas");
        btnAjustarSalidas.setVisible(false);
        btnAjustarSalidas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAjustarSalidasActionPerformed(evt);
            }
        });
        jPanel6.add(btnAjustarSalidas);

        jLabel4.setText("   ");
        jPanel6.add(jLabel4);

        jPanel5.setVisible(false);
        jPanel5.setBorder(BorderFactory.createTitledBorder(""));
        jPanel5.setLayout(new BoxLayout(jPanel5, BoxLayout.LINE_AXIS));

        tfPathValue.setVisible(false);
        tfPathValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        tfPathValue.setHorizontalAlignment(JTextField.TRAILING);
        tfPathValue.setToolTipText("Explore en la pc para cargar su archivo CSV");
        tfPathValue.setMaximumSize(new Dimension(2147483647, 30));
        jPanel5.add(tfPathValue);

        jLabel2.setText(" ");
        jPanel5.add(jLabel2);

        btnBrowse.setVisible(false);
        btnBrowse.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnBrowse.setText("Cargar CSV");
        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });
        jPanel5.add(btnBrowse);

        jPanel6.add(jPanel5);

        jLabel3.setText("   ");
        jPanel6.add(jLabel3);

        jPanel2.add(jPanel6);

        jPanel3.setBorder(BorderFactory.createTitledBorder("Convertidor"));
        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.LINE_AXIS));

        jLabel9.setText("   ");
        jPanel3.add(jLabel9);

        spInputPounds.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spInputPounds.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spInputPounds.setMaximumSize(new Dimension(32767, 30));
        spInputPounds.setMinimumSize(new Dimension(31, 30));
        spInputPounds.setPreferredSize(new Dimension(31, 30));
        spInputPounds.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                spInputPoundsStateChanged(evt);
            }
        });
        jPanel3.add(spInputPounds);

        jLabel8.setText(" ");
        jPanel3.add(jLabel8);

        jLabel12.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Lbr.");
        jPanel3.add(jLabel12);

        jLabel10.setText("   ");
        jPanel3.add(jLabel10);

        spInputGrams.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        spInputGrams.setModel(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        spInputGrams.setMaximumSize(new Dimension(32767, 30));
        spInputGrams.setMinimumSize(new Dimension(31, 30));
        spInputGrams.setPreferredSize(new Dimension(31, 30));
        spInputGrams.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                spInputGramsStateChanged(evt);
            }
        });
        jPanel3.add(spInputGrams);

        jLabel11.setText(" ");
        jPanel3.add(jLabel11);

        jLabel13.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Gr.");
        jPanel3.add(jLabel13);

        jLabel14.setText("   ");
        jPanel3.add(jLabel14);

        jPanel2.add(jPanel3);

        getContentPane().add(jPanel2, BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents" + System.getProperty("file.separator"));
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        int dialogOption = chooser.showOpenDialog(this);
        if (dialogOption == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            tfPathValue.setText(selectedFile.getAbsolutePath());
            ralatedOperations = new ArrayList<>();
            previewedOutcomes = new TreeMap<>(Comparator.comparingInt(RawMaterial::getCode));
            previewedIncomes = new TreeMap<>(Comparator.comparingInt(RawMaterial::getCode));
            try {
                BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                String line = "";
                String separator = ";";
                boolean incomesFlag = true;
                boolean outcomesFlag = false;
                int cont = 0;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(separator);
                    if (datos.length > 0) {
                        if (!datos[0].equals("")) {
                            try {
                                int id = Integer.parseInt(datos[0]);
                                Item item = itemsCRUD.get(id);
                                if (item != null) {
                                    try {
                                        RawMaterial RawMaterial = (RawMaterial) item;
                                        double ammount = Double.parseDouble(datos[2]);
                                        if (incomesFlag) {
                                            previewedIncomes.put(RawMaterial, ammount);
                                        }
                                        if (outcomesFlag) {
                                            previewedOutcomes.put(RawMaterial, ammount);
                                        }
                                    } catch (ClassCastException ex) {
                                        System.err.println(item);
                                    }
                                } else {
                                    Operation operation = operationsCRUD.get(id);
                                    if (operation != null) {
                                        ralatedOperations.add(operation);
                                    } else {
                                        break;
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                timing = new Timing(LocalDate.ofEpochDay(Long.parseLong(datos[0])));
                            } catch (NumberFormatException ex) {
                                Logger.getInstance().updateErrorLog(ex);
                            }

                        } else if (cont == 0) {
                            cont++;
                            incomesFlag = false;
                            outcomesFlag = true;
                        } else if (cont == 1) {
                            cont++;
                            incomesFlag = false;
                            outcomesFlag = false;
                        }
                    }
                }
                br.close();
                JOptionPane.showMessageDialog(this, "Mercancías necesarias importadas correctamente", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
            } catch (HeadlessException | IOException | NumberFormatException ex) {
                Logger.getInstance().updateErrorLog(ex);
            }
            CustomTM_RMDateAdjustEditable model = new CustomTM_RMDateAdjustEditable(previewedIncomes);
            tableRawMaterials.setModel(model);
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnAjustarEntradasActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAjustarEntradasActionPerformed
        try {
            int updateOption = JOptionPane.showConfirmDialog(this, "¿Desea realizar las entradas de mercancía correspondientes?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
            if (updateOption == 0) {
                CustomTM_RMDateAdjustEditable mtmc = (CustomTM_RMDateAdjustEditable) tableRawMaterials.getModel();
                TreeMap<Integer, HashMap<String, Object>> datas = mtmc.getDataMap();
                ArrayList<Operation> incomeOperations = new ArrayList<>();
                for (Map.Entry<Integer, HashMap<String, Object>> entry : datas.entrySet()) {
                    RawMaterial rawMaterial = (RawMaterial) itemsCRUD.get(entry.getKey());
                    HashMap<String, Object> values = entry.getValue();
                    if (values.get("Cantidad") != null) {
                        Instant atStartOfDay = timing.atStartOfDay();
                        double ammount = (double) values.get("Cantidad");
                        double value = (double) values.get("Valor de la compra($)");
                        boolean billed = (boolean) values.get("Facturado");
                        if (ammount > 0) {
                            incomeOperations.add(new Operation(rawMaterial, atStartOfDay, ammount, value, true, billed, false));
                        } else if (ammount == 0) {
                            if (previewedOutcomes.containsKey(rawMaterial)) {
                                previewedOutcomes.merge(rawMaterial, (double) previewedIncomes.get(rawMaterial), (oldVal, newVal) -> oldVal - newVal);
                            } else {
                                previewedOutcomes.put(rawMaterial, 0.0d);
                            }
                        }
                    }
                }
                operationsCRUD.save(incomeOperations);
                JOptionPane.showMessageDialog(this, "Operaciones de entrada completadas");
                CustomTM_RMDateAdjust model = new CustomTM_RMDateAdjust(previewedOutcomes);
                tableRawMaterials.setModel(model);
                btnAjustarEntradas.setVisible(false);
                btnAjustarSalidas.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnAjustarEntradasActionPerformed

    private void btnAjustarSalidasActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAjustarSalidasActionPerformed
        try {
            int updateOption = JOptionPane.showConfirmDialog(this, "¿Desea realizar las salidas de mercancía correspondientes para completar esta jornada?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
            if (updateOption == 0) {
                CustomTM_RMDateAdjust mtmc = (CustomTM_RMDateAdjust) tableRawMaterials.getModel();
                TreeMap<Integer, HashMap<String, Object>> datas = mtmc.getDataMap();
                ArrayList<Operation> newOperations = new ArrayList<>();
                ArrayList<Operation> operationsToDelete = new ArrayList<>();
                for (Map.Entry<Integer, HashMap<String, Object>> entry : datas.entrySet()) {
                    RawMaterial rawMaterial = (RawMaterial) itemsCRUD.get(entry.getKey());
                    HashMap<String, Object> values = entry.getValue();
                    Instant atMorningOfDay = timing.atMorningOfDay();
                    double ammount = (double) values.get("Cantidad");
                    double value = (double) values.get("Valor de la compra($)");
                    if (ammount > 0) {
                        newOperations.add(new Operation(rawMaterial, atMorningOfDay, ammount, value, false, false, false));
                    } else if (ammount == 0) {
                        for (Operation relatedOperation : ralatedOperations) {
                            Product product = (Product) relatedOperation.getItem();
                            if (product.hasItAsIngredient(rawMaterial) && product instanceof ProductListo) {// TODO Debe automatizarse este numero entero
                                operationsToDelete.add(operationsCRUD.get(relatedOperation.getId()));
                                break;
                            }
                        }
                    }
                }
                operationsCRUD.save(newOperations);
                operationsCRUD.delete(operationsToDelete);
                daysController.closeDay();
                JOptionPane.showMessageDialog(this, "Operaciones completadas");
                dispose();
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnAjustarSalidasActionPerformed

    private void spInputPoundsStateChanged(ChangeEvent evt) {//GEN-FIRST:event_spInputPoundsStateChanged
        if (!gramsFlag) {
            poundsFlag = true;
            Object value = spInputPounds.getValue();
            if (value != null) {
                try {
                    double ammountInPounds = (double) value;
                    spInputGrams.setValue(ammountInPounds * 460);
//                    spInputGrams.setValue(ammountInPounds * 453.592);
                } catch (ClassCastException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }
            poundsFlag = false;
        }
    }//GEN-LAST:event_spInputPoundsStateChanged

    private void spInputGramsStateChanged(ChangeEvent evt) {//GEN-FIRST:event_spInputGramsStateChanged
        if (!poundsFlag) {
            gramsFlag = true;
            Object value = spInputGrams.getValue();
            if (value != null) {
                try {
                    double ammountInGrams = (double) value;
//                    double ammountInKGrams = ammountInGrams / 1000;
//                    spInputPounds.setValue(ammountInKGrams * 2.20462);
                    spInputPounds.setValue(ammountInGrams / 460);
                } catch (ClassCastException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }
            gramsFlag = false;
        }
    }//GEN-LAST:event_spInputGramsStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAjustarEntradas;
    private JButton btnAjustarSalidas;
    private JButton btnBrowse;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JSpinner spInputGrams;
    private JSpinner spInputPounds;
    private CustomJTable tableRawMaterials;
    private JTextField tfPathValue;
    // End of variables declaration//GEN-END:variables

}
