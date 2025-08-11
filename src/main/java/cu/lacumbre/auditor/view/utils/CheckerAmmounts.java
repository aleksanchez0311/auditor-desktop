package cu.lacumbre.auditor.view.utils;

import static cu.lacumbre.auditor.Setup.DEFAULT_FONT;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.model.Ingredient;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.Workable;
import cu.lacumbre.auditor.view.inventory.MakeSale;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;

public class CheckerAmmounts extends Checker implements ChangeListener {

    public CheckerAmmounts(JDialog invoker, ItemsCRUD itemsCRUD, String category, boolean archivatedItemsChecker) {
        super(invoker, itemsCRUD, category, archivatedItemsChecker);
    }

    @Override
    protected void fill(double totalElements, int neededRows) {
        super.fill(totalElements, neededRows);
        double maxWidth = 0.0d;
        double maxHeight = 0.0d;
        for (Object object : panel.getComponents()) {
            JPanel innerPanel = (JPanel) object;
            JCheckBox check = (JCheckBox) innerPanel.getComponents()[0];
            check.setEnabled(true);
            buttonGroup.remove(check);
            JSpinner ammount = new JSpinner(new SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
            ammount.setFont(DEFAULT_FONT);
            ammount.setEnabled(false);
            innerPanel.add(ammount);
            innerPanel.revalidate();
            innerPanel.repaint();
            if (innerPanel.getPreferredSize().getWidth() > maxWidth) {
                maxWidth = innerPanel.getPreferredSize().getWidth();
            }
            if (innerPanel.getPreferredSize().getHeight() > maxHeight) {
                maxHeight = innerPanel.getPreferredSize().getHeight();
            }
        }
        innerPanelPreferredSize = new Dimension((int) innerPanelPreferredSize.getWidth(), (int) (maxHeight * 1.4));
        enableChecks();
        panel.revalidate();
        panel.repaint();
    }

    @Override
    protected void fill() {
        super.fill();
    }

    @Override
    protected void enableListeners() {
        super.enableListeners();
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JSpinner value = (JSpinner) innerPanel.getComponent(2);
                value.addChangeListener(this);
            }
        }
    }

    @Override
    protected void disableListeners() {
        super.disableListeners();
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JSpinner value = (JSpinner) innerPanel.getComponent(2);
                value.removeChangeListener(this);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        MakeSale makeSale = getInvoker() instanceof MakeSale ? (MakeSale) invoker : null;
        JCheckBox check = (JCheckBox) evt.getSource();
        JPanel innerPanel = (JPanel) check.getParent();
        JSpinner spinnerField = (JSpinner) innerPanel.getComponents()[2];
        switch (evt.getStateChange()) {
            case ItemEvent.SELECTED -> {
                spinnerField.setEnabled(true);
                break;
            }
            case ItemEvent.DESELECTED -> {
                spinnerField.setEnabled(false);
                spinnerField.setValue(0);
                break;
            }
            default -> {
                break;
            }
        }
        if (makeSale != null) {
            makeSale.getLblTotalSaleAmmount().setText(String.valueOf(calcularTotalSaleAmmount()));
        }
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        MakeSale makeSale = getInvoker() instanceof MakeSale ? (MakeSale) invoker : null;
        if (makeSale != null) {
            JSpinner spinnerField = (JSpinner) evt.getSource();
            JPanel innerPanel = (JPanel) spinnerField.getParent();
            Component[] childs = innerPanel.getComponents();
            JCheckBox check = (JCheckBox) childs[0];
//            JLabel label = (JLabel) childs[1];
            if (check.isSelected() && spinnerField.isEnabled()) {
                makeSale.getLblTotalSaleValue().setText(String.valueOf(calcularTotalSaleValue()));
//                int itemID = Integer.parseInt(label.getText());
//                double value = (double) spinnerField.getValue();
//                Product currentItem = (Product) itemsCRUD.get(itemID);
            }
        }
    }

    @Override
    protected void setSelectedItem(Map.Entry<Product, Double[]> entry) {
        Product product = entry.getKey();
        Double[] values = entry.getValue();
        double ammount = values[0];
        if (ammount > 0) {
            int productID = product.getId();
            check(productID);
            enableSpinner(productID);
            setAmmount(productID, ammount);
        }
    }

    protected void enableSpinner(int productID) {
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                int id = Integer.parseInt(((JLabel) innerPanel.getComponent(1)).getText());
                JSpinner spinner = (JSpinner) innerPanel.getComponent(2);
                if (productID == id) {
                    spinner.setEnabled(true);
                }
            }
        }
        enableListeners();
    }

    public void enableSpinners() {
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JSpinner spinner = (JSpinner) innerPanel.getComponent(2);
                spinner.setEnabled(true);
            }
        }
        enableListeners();
    }

    public void disableSpinners() {
        disableListeners();
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JSpinner spinner = (JSpinner) innerPanel.getComponent(2);
                spinner.setEnabled(false);
            }
        }
    }

    public void setAmmount(int productID, double ammount) {
        disableListeners();
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JLabel id = (JLabel) innerPanel.getComponent(1);
                JSpinner value = (JSpinner) innerPanel.getComponent(2);
                if (Integer.parseInt(id.getText()) == productID) {
                    value.setValue(ammount);
                    break;
                }
            }
        }
        enableListeners();
    }

    private int calcularTotalSaleAmmount() {
        int ammount = 0;
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                if (check.isSelected()) {
                    ammount++;
                }
            }
        }
        return ammount;
    }

    private double calcularTotalSaleValue() {
        double value = 0.0d;
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                Product product = (Product) itemsCRUD.get(Integer.parseInt(((JLabel) innerPanel.getComponent(1)).getText()));
                JSpinner spinner = (JSpinner) innerPanel.getComponent(2);
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                if (check.isSelected()) {
                    value += product.getPrice() * ((double) spinner.getValue());
                }
            }
        }
        return value;
    }

    public ArrayList<Ingredient> getSelectedItems() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            if (innerPanel.getComponents().length > 0) {
                JCheckBox check = (JCheckBox) innerPanel.getComponent(0);
                JLabel id = (JLabel) innerPanel.getComponent(1);
                JSpinner value = (JSpinner) innerPanel.getComponent(2);
                if (check.isSelected()) {
                    ingredients.add(new Ingredient(Workable.generate(), itemsCRUD.get(Integer.parseInt(id.getText())), (double) value.getValue()));
                }
            }
        }
        return ingredients;
    }
}
