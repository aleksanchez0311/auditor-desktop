package cu.lacumbre.auditor.view.merchandise;

import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.model.Ingredient;
import cu.lacumbre.auditor.model.Workable;
import cu.lacumbre.auditor.view.utils.CheckerRecipes;
import cu.lacumbre.utils.Logger;
import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class RecipeViewer extends JDialog {

    private final Workable workable;
    private final JList list;
    private final Boolean showArchivated = Boolean.valueOf(((String) Setup.options.get("showArchivated")));

    public RecipeViewer(JDialog owner, boolean modal, ItemsCRUD itemsCRUD, JList list, Workable workable) {
        super((JFrame) owner.getParent(), modal);
        this.workable = workable;
        this.list = list;
        setTitle("Visor de receta -> (" + workable + " [" + workable.getId() + "]: " + workable.toString() + ")");
        initComponents();
        JScrollPane[] panels = new JScrollPane[3];
        CheckerRecipes archivatedWorkablesChecker = new CheckerRecipes(owner, itemsCRUD, workable, Setup.SUBCATEGORIA_RECETA, true);
        CheckerRecipes noArchivatedWorkablesChecker = new CheckerRecipes(owner, itemsCRUD, workable, Setup.SUBCATEGORIA_RECETA, false);
        CheckerRecipes rawMaterialsChecker = new CheckerRecipes(owner, itemsCRUD, workable, Setup.SUBCATEGORIA_MATERIA_PRIMA_COCINA, false);
        JScrollPane noArchivatedWorkablesPane = noArchivatedWorkablesChecker.getPanel(splitPane);
        if (workable instanceof Workable) {
            JScrollPane rawMaterialsPane = rawMaterialsChecker.getPanel(splitPane);
            panels[0] = rawMaterialsPane;
        }
        panels[1] = noArchivatedWorkablesPane;
        if (showArchivated) {
            JScrollPane archivatedWorkablesPane = archivatedWorkablesChecker.getPanel(splitPane);
            panels[2] = archivatedWorkablesPane;
        }
        setPanes(panels);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    TreeMap<Integer, Ingredient> ingredients = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
                    TreeMap<Integer, Ingredient> rmIngredients = rawMaterialsChecker.getIngredients();
                    TreeMap<Integer, Ingredient> initialRMIngredients = rawMaterialsChecker.getInitIngredients();
                    TreeMap<Integer, Ingredient> naWIngredients = noArchivatedWorkablesChecker.getIngredients();
                    TreeMap<Integer, Ingredient> initialNAWIngredients = noArchivatedWorkablesChecker.getInitIngredients();
                    if (!initialRMIngredients.equals(rmIngredients)) {
                        ingredients.putAll(rmIngredients);
                    }
                    if (!initialNAWIngredients.equals(naWIngredients)) {
                        ingredients.putAll(naWIngredients);
                    }
                    if (showArchivated) {
                        TreeMap<Integer, Ingredient> aWIngredients = archivatedWorkablesChecker.getIngredients();
                        TreeMap<Integer, Ingredient> initialAWIngredients = archivatedWorkablesChecker.getInitIngredients();
                        if (!initialAWIngredients.equals(aWIngredients)) {
                            ingredients.putAll(aWIngredients);
                        }
                    }
                    itemsCRUD.modifyRecipe(workable, new ArrayList<>(ingredients.values()));
                    
                } catch (SQLException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
                super.windowClosing(e);
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new JPanel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyTyped(KeyEvent evt) {
                formKeyTyped(evt);
            }
        });
        getContentPane().setLayout(new CardLayout(3, 3));

        splitPane.setLayout(new BoxLayout(splitPane, BoxLayout.PAGE_AXIS));
        getContentPane().add(splitPane, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE ->
                dispose();
            default -> {
                evt.consume();
            }
        }
    }//GEN-LAST:event_formKeyTyped

    private void formKeyPressed(KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE ->
                dispose();
            default -> {
                evt.consume();
            }
        }
    }//GEN-LAST:event_formKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel splitPane;
    // End of variables declaration//GEN-END:variables

    private void setPanes(JScrollPane[] panels) {
        splitPane.removeAll();
        for (JScrollPane scroll : panels) {
            if (scroll != null) {
                splitPane.add(scroll);
            }
        }
        pack();
    }

    @Override
    public void dispose() {
        list.clearSelection();
        list.setSelectedValue(workable, true);
        super.dispose();
    }

}
