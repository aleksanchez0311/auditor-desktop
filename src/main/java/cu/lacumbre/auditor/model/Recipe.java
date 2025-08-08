package cu.lacumbre.auditor.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

public class Recipe extends IDSuperClass {

    private final String name;
    private final ArrayList<Ingredient> ingredients;

    public Recipe(String name, ArrayList<Ingredient> ingredients) {
        super(ingredients.stream().mapToInt((Ingredient ingredient) -> ingredient.getId()).sum());
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public TreeMap<Integer, Ingredient> getIngredientsMap() {
        TreeMap<Integer, Ingredient> result = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        for (Ingredient ingredient : ingredients) {
            result.put(ingredient.getItem().getId(), ingredient);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Recipe recipe) {
            if (recipe.getIngredients() == ingredients) {
                return super.equals(obj);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.ingredients);
        return hash + super.hashCode();
    }

    @Override
    public String toString() {
        return "Receta: " + name + "' => Ingredientes:" + ingredients.toString();
    }

}
