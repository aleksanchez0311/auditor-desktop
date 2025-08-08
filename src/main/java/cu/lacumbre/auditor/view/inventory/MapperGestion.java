package cu.lacumbre.auditor.view.inventory;

import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.view.utils.CheckerMap;
import cu.lacumbre.auditor.crud.Mapper;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.view.custom.CustomListModel;
import cu.lacumbre.auditor.view.utils.LoadCuadre;
import cu.lacumbre.excelreaper.NullCellException;
import cu.lacumbre.utils.Logger;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.TreeMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.OverlayLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import org.apache.poi.ss.formula.CollaboratingWorkbooksEnvironment;

public class MapperGestion extends JDialog {

    private final ArrayList<String> unmappedItemsList;
    private final Mapper mapper;
    private String selecteditem;
    private String currentFilter;
    private final CheckerMap checker;

    public MapperGestion(JFrame parent, boolean lockParent, Connection connection, MakeSale makeSale, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, ArrayList<String> unmappedItemsList) throws SQLException {
        super(parent, lockParent);
        this.mapper = new Mapper(connection, itemsCRUD);
        this.checker = new CheckerMap(this, mapper, Setup.CATEGORIA_PRODUCTO);
        if (makeSale != null) {
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        super.windowClosing(e);
                        makeSale.setLoadCuadre(new LoadCuadre(connection, makeSale, operationsCRUD, itemsCRUD));
                        makeSale.setMapper(MapperGestion.this.mapper);
                        makeSale.getLoadCuadre().loadCuadre();
                    } catch (SQLException | NullCellException | CollaboratingWorkbooksEnvironment.WorkbookNotFoundException ex) {
                        Logger.getInstance().updateErrorLog(ex);
                    }
                }
            });
        }
        this.unmappedItemsList = unmappedItemsList;
        initComponents();
        this.selecteditem = null;
        setListObjectsModel();
        fillCenterPanel();
        checker.enableChecks();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new JPopupMenu();
        miMapToNull = new JMenuItem();
        centerPane = new JPanel();
        cuadresPane = new JPanel();
        jPanel2 = new JPanel();
        filter = new JPanel();
        tfFilter = new JTextField();
        jButton1 = new JButton();
        jScrollPane1 = new JScrollPane();
        listObjects = new JList<>();
        filler1 = new Box.Filler(new Dimension(0, 5), new Dimension(0, 5), new Dimension(32767, 5));
        jPanel1 = new JPanel();
        btnRfresh = new JButton();
        filler2 = new Box.Filler(new Dimension(5, 0), new Dimension(5, 0), new Dimension(5, 0));
        jToggleButton1 = new JToggleButton();
        jButton2 = new JButton();
        mainPanel = new JPanel();
        itemsPanel = new JScrollPane();

        miMapToNull.setText("Mapear como No Activo");
        miMapToNull.addActionListener(this::miMapToNullActionPerformed);
        jPopupMenu1.add(miMapToNull);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mapear Productos");
        getContentPane().setLayout(new CardLayout());

        centerPane.setMinimumSize(new Dimension(1200, 700));
        centerPane.setPreferredSize(new Dimension(1200, 700));
        centerPane.setLayout(new BorderLayout());

        cuadresPane.setBorder(BorderFactory.createTitledBorder("Productos en Cuadres:"));
        cuadresPane.setMinimumSize(new Dimension(200, 100));
        cuadresPane.setPreferredSize(new Dimension(200, 100));
        cuadresPane.setLayout(new BoxLayout(cuadresPane, BoxLayout.PAGE_AXIS));

        jPanel2.setLayout(new OverlayLayout(jPanel2));

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

        jPanel2.add(filter);

        jScrollPane1.setMinimumSize(new Dimension(200, 100));
        jScrollPane1.setPreferredSize(new Dimension(200, 100));

        listObjects.setComponentPopupMenu(jPopupMenu1);
        listObjects.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                listObjectsKeyTyped(evt);
            }
        });
        listObjects.addListSelectionListener(this::listObjectsValueChanged);
        jScrollPane1.setViewportView(listObjects);

        jPanel2.add(jScrollPane1);

        cuadresPane.add(jPanel2);
        cuadresPane.add(filler1);

        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.LINE_AXIS));

        btnRfresh.setText("Actualizar");
        btnRfresh.addActionListener(this::btnRfreshActionPerformed);
        jPanel1.add(btnRfresh);
        jPanel1.add(filler2);

        jToggleButton1.setText("Ver no Mapeados");
        jToggleButton1.addItemListener(this::jToggleButton1ItemStateChanged);
        jPanel1.add(jToggleButton1);

        jButton2.setText("Nueva Materia Prima");
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jButton2.setVisible(false);
        jPanel1.add(jButton2);

        cuadresPane.add(jPanel1);

        centerPane.add(cuadresPane, BorderLayout.WEST);

        mainPanel.setBorder(BorderFactory.createTitledBorder("Productos en BD:"));
        mainPanel.setLayout(new CardLayout());
        mainPanel.add(itemsPanel, "card2");

        centerPane.add(mainPanel, BorderLayout.CENTER);

        getContentPane().add(centerPane, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listObjectsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        if (!evt.getValueIsAdjusting()) {
            String name = (String) listObjects.getSelectedValue();
            TreeMap<String, Product> mappedElements = mapper.getMap();
            if (name != null) {
                selecteditem = name;
                if (mappedElements.get(name) != null) {
                    if (!mappedElements.get(name).getDescription().equals("Autogenerated")) {
                        Product product = mappedElements.get(name);
                        checker.check(product.getId());
                    } else {
                        checker.clearSelection();
                    }
                } else {
                    checker.clearSelection();
                }
            }
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void btnRfreshActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnRfreshActionPerformed
        try {
            refreshFrameView();
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_btnRfreshActionPerformed

    private void jButton2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //TODO ACTUALIZAR ESTA VISTA AUTOMATICAMENTE AL AGREGAR UN NUEVa MATERIA PRIMA o PRODUCTO SIN TENER QUE CERRARLA
    }//GEN-LAST:event_jButton2ActionPerformed

    private void miMapToNullActionPerformed(ActionEvent evt) {//GEN-FIRST:event_miMapToNullActionPerformed
        try {
            if (selecteditem != null) {
                mapper.save(selecteditem, Product.generate(), false);
            } else {
                JOptionPane.showMessageDialog(this, "No hay seleccionado ningún objeto de la lista.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }//GEN-LAST:event_miMapToNullActionPerformed

    private void listObjectsKeyTyped(KeyEvent evt) {//GEN-FIRST:event_listObjectsKeyTyped
        currentFilter = tfFilter.getText();
        char charTyped = evt.getKeyChar();
        if (Character.isAlphabetic(charTyped) || Character.isWhitespace(charTyped) || Character.isDigit(charTyped)) {
            currentFilter = currentFilter + charTyped;
            CustomListModel<String> model = (CustomListModel<String>) listObjects.getModel();
            ArrayList<String> tempList = new ArrayList<>();
            ArrayList<String> list = model.getList();
            for (String string : list) {
                if (string.toLowerCase().contains(currentFilter.toLowerCase())) {
                    tempList.add(string);
                }
            }
            listObjects.setModel(new CustomListModel<>(tempList));
            tfFilter.setText(currentFilter);

        } else if (charTyped == '\b') {
            if (!currentFilter.isEmpty()) {
                currentFilter = currentFilter.substring(0, currentFilter.length() - 1);
                ArrayList<String> tempList = new ArrayList<>();
                ArrayList<String> list = unmappedItemsList != null ? unmappedItemsList : new ArrayList<>(mapper.getMap().keySet());
                for (String string : list) {
                    if (string.toLowerCase().contains(currentFilter.toLowerCase())) {
                        tempList.add(string);
                    }
                }
                listObjects.setModel(new CustomListModel<>(tempList));
                tfFilter.setText(currentFilter);
            }
        } else if (charTyped == '\u001b') {
            if (currentFilter.equals("")) {
                dispose();
            } else {
                setListObjectsModel();
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_listObjectsKeyTyped

    private void jToggleButton1ItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jToggleButton1ItemStateChanged
        TreeMap<String, Product> map = null;
        if (jToggleButton1.isSelected()) {
            jToggleButton1.setText("Ver Todos");
            map = mapper.getUnmappedMap();
        } else {
            jToggleButton1.setText("Ver no Mapeados");
            map = mapper.getMap();
        }
        listObjects.setModel(new CustomListModel<>(new ArrayList<>(map.keySet())));
    }//GEN-LAST:event_jToggleButton1ItemStateChanged

    public void refreshFrameView() throws SQLException {
        selecteditem = null;
        setListObjectsModel();
        fillCenterPanel();
        checker.enableChecks();
    }

    private void setListObjectsModel() {
        tfFilter.setText("");
        if (unmappedItemsList != null) {
            listObjects.setModel(new CustomListModel<>(unmappedItemsList));
        } else {
            listObjects.setModel(new CustomListModel<>(new ArrayList<>(mapper.getOthMap())));
        }
    }

    private void fillCenterPanel() {
        mainPanel.removeAll();
        itemsPanel = checker.getPanel(mainPanel);
        mainPanel.add(itemsPanel);
        mainPanel.validate();
        pack();
    }

    public String getSelecteditem() {
        return selecteditem;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnRfresh;
    private JPanel centerPane;
    private JPanel cuadresPane;
    private Box.Filler filler1;
    private Box.Filler filler2;
    private JPanel filter;
    private JScrollPane itemsPanel;
    private JButton jButton1;
    private JButton jButton2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPopupMenu jPopupMenu1;
    private JScrollPane jScrollPane1;
    private JToggleButton jToggleButton1;
    private JList<String> listObjects;
    private JPanel mainPanel;
    private JMenuItem miMapToNull;
    private JTextField tfFilter;
    // End of variables declaration//GEN-END:variables

}
