package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.model.Ingredient;
import cu.lacumbre.auditor.model.Workable;
import cu.lacumbre.auditor.view.merchandise.RecipableGestion;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.util.Comparator;
import java.util.TreeMap;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;

public class CheckerRecipes extends CheckerAmmounts {

    private final Workable workable;
    private final TreeMap<Integer, Ingredient> initIngredients;
    private final TreeMap<Integer, Ingredient> ingredients;

    public CheckerRecipes(JDialog invoker, ItemsCRUD itemsCRUD, Workable workable, String category, boolean archivatedItemsChecker) {
        super(invoker, itemsCRUD, category, archivatedItemsChecker);
        this.workable = workable;
        this.initIngredients = workable.hasRecipe() ? workable.getRecipe().getIngredientsMap() : new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        this.ingredients = workable.hasRecipe() ? workable.getRecipe().getIngredientsMap() : new TreeMap<>(Comparator.comparingInt(Integer::intValue));
    }

    public TreeMap<Integer, Ingredient> getIngredients() {
        return ingredients;
    }

    public TreeMap<Integer, Ingredient> getInitIngredients() {
        return initIngredients;
    }

    public Workable getWorkable() {
        return workable;
    }

    @Override
    protected void fill(double totalElements, int neededRows) {
        super.fill(totalElements, neededRows);
        for (Component component : panel.getComponents()) {
            JPanel innerPanel = (JPanel) component;
            Component[] innerPanelComponents = innerPanel.getComponents();
            int id = Integer.parseInt(((JLabel) innerPanelComponents[1]).getText());
            Ingredient ingredient = initIngredients.get(id);
            if (ingredient != null) {
                JCheckBox check = (JCheckBox) innerPanelComponents[0];
                JSpinner spinner = (JSpinner) innerPanelComponents[2];
                check.setSelected(true);
                spinner.setValue(ingredient.getAmmount());
                spinner.setEnabled(true);
                innerPanel.revalidate();
                innerPanel.repaint();
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    @Override
    protected Dimension adjust(double wantedCols, int neededRows) {
        Dimension newDimension = super.adjust(wantedCols, neededRows);
        if (workable instanceof Workable) {
            newDimension.setSize(newDimension.width, newDimension.height * 1.5);
        }
        return newDimension;
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        JCheckBox check = (JCheckBox) evt.getSource();
        JPanel innerPanel = (JPanel) check.getParent();
        Component[] childs = innerPanel.getComponents();
        JSpinner spinnerField = (JSpinner) childs[2];
        double ammount = (double) spinnerField.getValue();
        JLabel idField = (JLabel) childs[1];
        int id = Integer.parseInt(idField.getText());
        switch (evt.getStateChange()) {
            case ItemEvent.SELECTED -> {
                spinnerField.setEnabled(true);
                ingredients.put(id, new Ingredient(workable, getItemsCRUD().get(id), ammount));
                break;
            }
            case ItemEvent.DESELECTED -> {
                spinnerField.setEnabled(false);
                spinnerField.setValue(0.0d);
                ingredients.remove(id);
                break;
            }
            default -> {
                break;
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        RecipableGestion recipableGestion = getInvoker() instanceof RecipableGestion ? (RecipableGestion) invoker : null;
        if (recipableGestion != null) {
            JSpinner spinnerField = (JSpinner) evt.getSource();
            JPanel innerPanel = (JPanel) spinnerField.getParent();
            Component[] childs = innerPanel.getComponents();
            JCheckBox check = (JCheckBox) childs[0];
            JLabel idField = (JLabel) childs[1];
            if (check.isSelected() && spinnerField.isEnabled()) {
                int id = Integer.parseInt(idField.getText());
                double ammount = (double) spinnerField.getValue();
                ingredients.put(id, new Ingredient(workable, getItemsCRUD().get(id), ammount));
            }
        }
    }
}
