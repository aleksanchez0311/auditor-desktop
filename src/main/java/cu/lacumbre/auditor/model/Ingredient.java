package cu.lacumbre.auditor.model;

import cu.lacumbre.auditor.exceptions.IngredientWorkableException;
import java.util.Objects;

public final class Ingredient extends IDSuperClass {

    private final Workable workable;
    private final Item ingredient;
    private double ammount;

    public Ingredient(int id, Workable workable, Item item, double ammount) throws IngredientWorkableException{
        super(id);
        this.workable = workable;
        this.ingredient = item;
        this.ammount = ammount;
        checkNulls();
    }

    public Ingredient(Workable workable, Item item, double ammount) throws IngredientWorkableException{
        super();
        this.workable = workable;
        this.ingredient = item;
        this.ammount = ammount;
        checkNulls();
    }

    public Workable getWorkable() {
        return workable;
    }

    public Item getItem() {
        return ingredient;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    @Override
    public Object get(String field) {
        return ingredient.get(field);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ingredient othIngredient) {
            if (othIngredient.getAmmount() == this.ammount && othIngredient.getItem().equals(this.ingredient)) {
                return super.equals(obj);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.ingredient);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.ammount) ^ (Double.doubleToLongBits(this.ammount) >>> 32));
        return hash + super.hashCode();
    }

    @Override
    public String toString() {
        return "[" + ingredient.getClass() + "] " + ingredient + " -> " + ammount;
    }

    private void checkNulls() throws IngredientWorkableException{
        if (workable == null || ingredient == null) {
            if (workable == null) {
                throw new IngredientWorkableException("La receta " + id + " que tiene el ingrediente " + ingredient.getDescription() + " es nula");
            } else {
                throw new IngredientWorkableException("La receta " + workable.getDescription() + " tiene este ingrediente nulo.");
            }
        }
    }

}
