package cu.lacumbre.auditor.view.utils;

import static cu.lacumbre.auditor.Setup.DEFAULT_BORDER;
import static cu.lacumbre.auditor.Setup.DEFAULT_FONT_BOLD;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Checker extends JDialog implements ItemListener {

    protected static final String INTEGER = "INTEGER";
    protected static final String DOUBLE = "DOUBLE";
    protected final ArrayList<? extends Item> list;
    protected JPanel panel;
    protected int totalSubPanels;

    protected final ItemsCRUD itemsCRUD;
    protected ButtonGroup buttonGroup;
    protected Dimension innerPanelPreferredSize = new Dimension(0, 0);
    protected final JDialog invoker;
    private final int wantedCols = 10;
    private final TitledBorder border;

    public Checker(JDialog invoker, ItemsCRUD itemsCRUD, String category, boolean archivatedItemsChecker) {
        String borderTitle = archivatedItemsChecker ? category + " (Archivado/as)" : category;
        this.border = BorderFactory.createTitledBorder(DEFAULT_BORDER, borderTitle, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, DEFAULT_FONT_BOLD);
        this.invoker = invoker;
        this.itemsCRUD = itemsCRUD;
        this.list = itemsCRUD.getList(category, false);
        if (listIsListOfProducts(list)) {
            this.list.sort(Comparator.comparingInt((Item p) -> ((Product) p).getTpvCategory().getId()));
        }
        this.panel = new JPanel();
        this.buttonGroup = new ButtonGroup();
    }

    protected ItemsCRUD getItemsCRUD() {
        return itemsCRUD;
    }

    protected JDialog getInvoker() {
        return invoker;
    }

    public JScrollPane getPanel(JPanel parent) {
        double totalItems = list.size();
        int neededRows = getNeededRows(totalItems, wantedCols);
        
        if (!list.isEmpty()) {
            create(wantedCols, neededRows);
            fill(totalItems, neededRows);
            totalSubPanels = refill(totalItems, neededRows);
        } else {
            create();
            fill();
            totalSubPanels = 1;
        }
        
        // Configuramos el scrollPane para ser responsivo
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Establecemos un tamaño mínimo para el parent
        Dimension minSize = new Dimension(300, 200);
        parent.setMinimumSize(minSize);
        
        // Ajustamos el tamaño preferido basado en el contenido
        Dimension preferredSize = adjust(wantedCols, neededRows);
        parent.setPreferredSize(preferredSize);
        
        // Permitimos que el parent crezca
        parent.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        return scroll;
    }

    private int getNeededRows(double totalItems, double wantedCols) {
        double fraction = totalItems / wantedCols;
        int integerPart = (int) fraction;
        double decimals = fraction - integerPart;
        return decimals > 0 ? integerPart + 1 : integerPart;
    }

    private JPanel create(double wantedCols, int neededRows) {
        panel = new JPanel();
        buttonGroup = new ButtonGroup();
        panel.setBorder(border);
        GridLayout layout = new GridLayout(0, (int) wantedCols, 5, 5);
        panel.setLayout(layout);
        return panel;
    }

    private JPanel create() {
        panel = new JPanel();
        panel.setBorder(border);
        panel.setLayout(new BorderLayout());
        return panel;
    }

    protected void fill(double totalItems, int neededRows) {
        double maxWidth = 0.0d;
        double maxHeight = 0.0d;
        for (Item item : list) {
            JPanel itemPanel = new JPanel();
            if (item instanceof Product product) {
                itemPanel.setBackground(product.getTpvCategory().getColor());
            }
            itemPanel.setBorder(DEFAULT_BORDER);
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            JCheckBox check = new JCheckBox();
            check.setText(item.getDescription() + " (" + item.getMeasureUnit().getAbrev() + ")");
            check.setToolTipText(check.getText());
            check.setEnabled(false);
            check.setFont(DEFAULT_FONT_BOLD);
            if (check.getPreferredSize().getWidth() > maxWidth) {
                maxWidth = check.getPreferredSize().getWidth();
            }
            if (check.getPreferredSize().getHeight() > maxHeight) {
                maxHeight = check.getPreferredSize().getHeight();
            }
            buttonGroup.add(check);
            itemPanel.add(check);
            JLabel id = new JLabel();
            id.setText(item.getId() + "");
            id.setEnabled(false);
            id.setVisible(false);
            itemPanel.add(id);
            panel.add(itemPanel);
        }
        innerPanelPreferredSize = new Dimension((int) (maxWidth * 0.65), (int) maxHeight);
    }

    protected void fill() {
        double maxWidth = 0.0d;
        double maxHeight = 0.0d;
        JPanel itemPanel = new JPanel();
        itemPanel.setBorder(DEFAULT_BORDER);
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        JLabel text1 = new JLabel();
        text1.setFont(DEFAULT_FONT_BOLD);
        text1.setHorizontalAlignment(JLabel.CENTER);
        text1.setText("No existen elementos de la categoría seleccionada.");
        JLabel text2 = new JLabel();
        text2.setFont(DEFAULT_FONT_BOLD);
        text2.setHorizontalAlignment(JLabel.CENTER);
        text2.setText("Presione Alt+ M o Alt + R o Alt + P para agregar alguno.");
        if (text1.getPreferredSize().getWidth() > maxWidth) {
            maxWidth = text1.getPreferredSize().getWidth();
        }
        if (text1.getPreferredSize().getHeight() > maxHeight) {
            maxHeight = text1.getPreferredSize().getHeight();
        }
        itemPanel.add(text1);
        itemPanel.add(text2);
        panel.add(itemPanel);
        innerPanelPreferredSize = new Dimension((int) (maxWidth * 0.65), (int) maxHeight);
    }

    private int refill(double totalItems, int neededRows) {
        int neededRefills = neededRows * 5 - (int) totalItems;
        for (int i = 0; i < neededRefills; i++) {
            JPanel refill = new JPanel();
            panel.add(refill);
        }
        return panel.getComponents().length;
    }

    protected Dimension adjust(double wantedCols, int neededRows) {
        // Calculamos tamaños base
        int baseWidth = (int) (wantedCols * innerPanelPreferredSize.width);
        int baseHeight = (int) (neededRows * innerPanelPreferredSize.height);
        
        // Añadimos espacio para márgenes y gaps
        int width = baseWidth + (int)(wantedCols * 5);
        int height = baseHeight + (int)(neededRows * 5);
        
        // Establecemos tamaños mínimos razonables
        int minWidth = Math.max(300, width / 3);
        int minHeight = Math.max(200, height / 3);
        
        panel.setMinimumSize(new Dimension(minWidth, minHeight));
        
        // El tamaño preferido es más flexible
        panel.setPreferredSize(new Dimension(width, height));
        
        // Permitimos que crezca sin límite
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        return new Dimension(width + 20, height + 20);
    }

    protected void setSelectedItem(Map.Entry<Product, Double[]> entry) {
        Product product = entry.getKey();
        Double[] values = entry.getValue();
        if (values[0] > 0) {
            check(product.getId());
        }
    }

    public void enableChecks() {
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                check.setEnabled(true);
            }
        }
        enableListeners();
    }

    protected void disableChecks() {
        disableListeners();
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                check.setEnabled(false);
            }
        }
    }

    public void clearSelection() {
        disableListeners();
        buttonGroup.clearSelection();
        enableListeners();
    }

    public void check(int itemId) {
        disableListeners();
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                JLabel id = (JLabel) innerPanel.getComponent(1);
                if (Integer.parseInt(id.getText()) == itemId) {
                    check.setSelected(true);
                    break;
                }
            }
        }
        enableListeners();
    }

    public Item getSelectedItem() {
        Item item = null;
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                JLabel id = (JLabel) innerPanel.getComponent(1);
                if (check.isSelected()) {
                    item = itemsCRUD.get(Integer.parseInt(id.getText()));
                    break;
                }
            }
        }
        return item;
    }

    public void setSelectedItems(TreeMap<Product, Double[]> items) {
        for (Map.Entry<Product, Double[]> entry : items.entrySet()) {
            setSelectedItem(entry);
        }
    }

    protected void enableListeners() {
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                check.addItemListener(this);
            }
        }
    }

    protected void disableListeners() {
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                check.removeItemListener(this);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    private boolean listIsListOfProducts(ArrayList<? extends Item> list) {
        for (Item item : list) {
            if (item instanceof Product) {
            } else {
                return false;
            }
        }
        return true;
    }

}
