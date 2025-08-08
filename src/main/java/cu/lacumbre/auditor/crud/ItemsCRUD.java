package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.model.CostSheet;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Expense;
import cu.lacumbre.auditor.model.MeasureUnit;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.IDSuperClass;
import cu.lacumbre.auditor.model.Ingredient;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.ProductCocina;
import cu.lacumbre.auditor.model.ProductListo;
import cu.lacumbre.auditor.model.RawMaterialCocina;
import cu.lacumbre.auditor.model.RawMaterialLista;
import cu.lacumbre.auditor.model.Recipe;
import cu.lacumbre.auditor.model.TPVCategory;
import cu.lacumbre.auditor.model.Workable;
import cu.lacumbre.auditor.view.utils.CustomComparator;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ItemsCRUD<MAPSORTER> {

//    public static final String GET_INGREDIENTS = "GET_INGREDIENTS";
//    public static final String GET_INGREDIENTS_RAWS = "GET_INGREDIENTS_RAWS";
    public static final Object editableRawExpenseFields = new Object[]{"description", "measure_unit", "code", "basic_cost"};
    public static final String[] editableRawMaterialFields = new String[]{"description", "measure_unit", "code", "basic_cost", "weighted_cost", "last_cost", "highest_cost"};
    public static final String[] editableRecipeFields = new String[]{"description", "measure_unit", "code"};
    public static final Object[] editableProductFields = new Object[]{"description", "measure_unit", "code", "price", "cost_sheet", "tpv_category"};
    private final Entity entity;
    private final CostSheetsCRUD costSheetsCRUD;
    private final MeasureUnitsCRUD measureUnitsCRUD;
    private final TPVCategoriesCRUD tpvCategoriesCRUD;
    private final ItemExpensesCRUD<MAPSORTER> itemExpensesCRUD;
    private final ItemRawMaterialsCRUD<MAPSORTER> itemRawMaterialsCRUD;
    private final ItemWorkablesCRUD<MAPSORTER> itemWorkablesCRUD;
    private final ItemProductsCRUD<MAPSORTER> itemProductsCRUD;
    private final Connection connection;
    private final TreeMap<MAPSORTER, Item> items;
    private final Comparator comparator;
    private final String comparatorColumn;
    private final Boolean showArchivated = Boolean.valueOf(((String) Setup.options.get("showArchivated")));

    public ItemsCRUD(Connection connection, Entity entity, CustomComparator customComparator) throws SQLException {
        this.connection = connection;
        this.entity = entity;
        this.measureUnitsCRUD = new MeasureUnitsCRUD(connection);
        this.tpvCategoriesCRUD = new TPVCategoriesCRUD(connection);
        this.costSheetsCRUD = new CostSheetsCRUD(connection);
        this.comparator = customComparator.getComparator();
        this.comparatorColumn = (String) customComparator.getSelectedColumn().getLeftMember();
        this.items = new TreeMap<>(comparator);
        this.itemExpensesCRUD = new ItemExpensesCRUD<>(this);
        this.itemRawMaterialsCRUD = new ItemRawMaterialsCRUD<>(this);
        this.itemWorkablesCRUD = new ItemWorkablesCRUD<>(this);
        this.itemProductsCRUD = new ItemProductsCRUD<>(this);
        this.loadDB();
        if (!items.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of items was successfully got!");
        }
    }

    public void reloadDB() throws SQLException {
        loadDB();
        if (!items.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Items was successfully refreshed!");
        }
    }

    private void loadDB() throws SQLException {
        items.clear();
        Item defaultItem = Item.generate();
        items.put((MAPSORTER) defaultItem.get(comparatorColumn), defaultItem);
        String sql = "SELECT * FROM items where entity = " + entity.getId() + " order by id";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt(1);
            String descripcion = result.getString(2);
            MeasureUnit measureUnit = measureUnitsCRUD.get(result.getInt(3));
            int code = result.getInt(11);
            boolean archivated = result.getBoolean(12);
            int switchable = Integer.parseInt(String.valueOf(id).replaceFirst(String.valueOf(entity.getId()), "").substring(0, 1));
            switch (switchable) {
                case 0 -> {
                    double cost = result.getDouble(5);
                    Expense expense = new Expense(id, descripcion, measureUnit, cost, code, archivated);
                    items.put((MAPSORTER) expense.get(comparatorColumn), expense);
                    break;
                }
                case 1 -> {
                    double basicCost = result.getDouble(5);
                    double pounderedCost = result.getDouble(6);
                    double lastCost = result.getDouble(7);
                    double highestCost = result.getDouble(8);
                    RawMaterialCocina rawMaterialCocina = new RawMaterialCocina(id, descripcion, measureUnit, basicCost, lastCost, pounderedCost, highestCost, code, archivated);
                    items.put((MAPSORTER) rawMaterialCocina.get(comparatorColumn), rawMaterialCocina);
                    break;
                }
                case 2 -> {
                    double basicCost = result.getDouble(5);
                    double pounderedCost = result.getDouble(6);
                    double lastCost = result.getDouble(7);
                    double highestCost = result.getDouble(8);
                    RawMaterialLista rawMaterialLista = new RawMaterialLista(id, descripcion, measureUnit, basicCost, lastCost, pounderedCost, highestCost, code, archivated);
                    items.put((MAPSORTER) rawMaterialLista.get(comparatorColumn), rawMaterialLista);
                    break;
                }
                case 3 -> {
                    Workable workable = new Workable(id, descripcion, measureUnit, code, archivated);
                    items.put((MAPSORTER) workable.get(comparatorColumn), workable);
                    break;
                }
                case 4 -> {
                    double price = result.getDouble(4);
                    TPVCategory tpvCategory = tpvCategoriesCRUD.get(result.getInt(13));
                    CostSheet costSheet = costSheetsCRUD.get(result.getInt(9)).copy(descripcion);
                    ProductCocina product = new ProductCocina(id, descripcion, measureUnit, tpvCategory, costSheet, price, code, archivated);
                    items.put((MAPSORTER) product.get(comparatorColumn), product);
                    break;
                }
                case 5 -> {
                    double price = result.getDouble(4);
                    TPVCategory tpvCategory = tpvCategoriesCRUD.get(result.getInt(13));
                    CostSheet costSheet = costSheetsCRUD.get(result.getInt(9)).copy(descripcion);
                    ProductListo product = new ProductListo(id, descripcion, measureUnit, tpvCategory, costSheet, price, code, archivated);
                    items.put((MAPSORTER) product.get(comparatorColumn), product);
                    break;
                }
                default -> {
                    break;
                }
            }
        }
        loadIngredients();
    }

    public Comparator getComparator() {
        return comparator;
    }

    public ItemExpensesCRUD<MAPSORTER> getItemExpensesCRUD() {
        return itemExpensesCRUD;
    }

    public ItemRawMaterialsCRUD<MAPSORTER> getItemRawMaterialsCRUD() {
        return itemRawMaterialsCRUD;
    }

    public ItemWorkablesCRUD<MAPSORTER> getItemWorkablesCRUD() {
        return itemWorkablesCRUD;
    }

    public ItemProductsCRUD<MAPSORTER> getItemProductsCRUD() {
        return itemProductsCRUD;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getComparatorColumn() {
        return comparatorColumn;
    }

    public MeasureUnitsCRUD getMeasureUnitsCRUD() {
        return measureUnitsCRUD;
    }

    public TPVCategoriesCRUD getTPVCategoriesCRUD() {
        return tpvCategoriesCRUD;
    }

    public CostSheetsCRUD getCostSheetsCRUD() {
        return costSheetsCRUD;
    }

    public Connection getConnection() {
        return connection;
    }

    public TreeMap<MAPSORTER, Item> getItems() {
        return items;
    }

    public Item get(int id) {
        Item result = null;
        for (Item item : items.values()) {
            if (item.getId() == id) {
                result = item;
                break;
            }
        }
        return result;
    }

    public ArrayList<? extends Item> getList(String category, boolean ignoreArchivationSetting) {
        return new ArrayList<>(getMap(category, ignoreArchivationSetting).values());
    }

    public TreeMap<MAPSORTER, ? extends Item> getMap(String category, boolean ignoreArchivationSetting) {
        TreeMap<MAPSORTER, ? extends Item> result = new TreeMap<>();
        switch (category) {
            case Setup.BUYABLE ->
                result = itemExpensesCRUD.getMap();
            case Setup.SUBCATEGORIA_GASTO ->
                result = itemExpensesCRUD.getExpensesMap();
            case Setup.CATEGORIA_MATERIA_PRIMA ->
                result = itemRawMaterialsCRUD.getMap();
            case Setup.SUBCATEGORIA_MATERIA_PRIMA_COCINA ->
                result = itemRawMaterialsCRUD.getRawMaterialsCocinaMap();
            case Setup.SUBCATEGORIA_MATERIA_PRIMA_LISTA ->
                result = itemRawMaterialsCRUD.getRawMaterialsListaMap();
            case Setup.ELABORABLE ->
                result = itemWorkablesCRUD.getMap();
            case Setup.SUBCATEGORIA_RECETA ->
                result = itemWorkablesCRUD.getWorkableMap();
            case Setup.CATEGORIA_PRODUCTO ->
                result = itemProductsCRUD.getMap();
            case Setup.SUBCATEGORIA_PRODUCTO_COCINA ->
                result = itemProductsCRUD.getProductsCocinaMap();
            case Setup.SUBCATEGORIA_PRODUCTO_LISTO ->
                result = itemProductsCRUD.getProductsListoMap();
            default ->
                result = getItems();
        }
        if (ignoreArchivationSetting) {
            return result;
        } else {
            return showArchivated ? result : removeArchivatedItem(result);
        }
    }

    private TreeMap<MAPSORTER, ? extends Item> removeArchivatedItem(TreeMap<MAPSORTER, ? extends Item> map) {
        TreeMap<MAPSORTER, Item> result = new TreeMap<>(map.comparator());
        for (Map.Entry<MAPSORTER, ? extends Item> entry : map.entrySet()) {
            MAPSORTER key = entry.getKey();
            Item value = entry.getValue();
            if (!value.isArchivated()) {
                result.put(key, value);
            }
        }
        return result;
    }

    public int getNextId(String which) {
        return switch (which) {
            case Setup.SUBCATEGORIA_GASTO ->
                itemExpensesCRUD.getNextId();
            case Setup.SUBCATEGORIA_MATERIA_PRIMA_COCINA ->
                itemRawMaterialsCRUD.getNextCocinaId();
            case Setup.SUBCATEGORIA_MATERIA_PRIMA_LISTA ->
                itemRawMaterialsCRUD.getNextListaId();
            case Setup.SUBCATEGORIA_RECETA ->
                itemWorkablesCRUD.getNextId();
            case Setup.SUBCATEGORIA_PRODUCTO_COCINA ->
                itemProductsCRUD.getNextCocinaId();
            case Setup.SUBCATEGORIA_PRODUCTO_LISTO ->
                itemProductsCRUD.getNextListoId();
            default ->
                0;
        };
    }

    public void save(Object obj) throws SQLException {
        String response = "";
        if (obj instanceof Item item) {
            switch (item) {
                case Product product -> {
                    itemProductsCRUD.save(product);
                    response = Setup.CATEGORIA_PRODUCTO;
                }
                case Workable workable -> {
                    itemWorkablesCRUD.save(workable);
                    response = Setup.CATEGORIA_RECETA;
                }
                case RawMaterial rawMaterial -> {
                    itemRawMaterialsCRUD.save(rawMaterial);
                    response = Setup.CATEGORIA_MATERIA_PRIMA;
                }
                case Expense expense -> {
                    itemExpensesCRUD.save(expense);
                    response = Setup.CATEGORIA_GASTO;
                }
                default -> {
                }
            }
            if (!response.isEmpty()) {
                reloadDB();
                Logger.getInstance().updateInfoLog("A new " + response + " was saved successfully!");
            }
        }
    }

    public void save(ArrayList<? extends IDSuperClass> superClasses) throws SQLException {
        if (!superClasses.isEmpty()) {
            String response = "";
            int affectedRows = 0;
            if (superClasses.get(0) != null && superClasses.get(0) instanceof Item item) {
                switch (item) {
                    case Product product -> {
                        itemProductsCRUD.save((ArrayList<Product>) superClasses);
                        response = Setup.CATEGORIA_PRODUCTO;
                    }
                    case Workable workable -> {
                        itemWorkablesCRUD.save((ArrayList<Workable>) superClasses);
                        response = Setup.CATEGORIA_RECETA;
                    }
                    case RawMaterial rawMaterial -> {
                        itemRawMaterialsCRUD.save((ArrayList<RawMaterial>) superClasses);
                        response = Setup.CATEGORIA_MATERIA_PRIMA;
                    }
                    case Expense expense -> {
                        itemExpensesCRUD.save((ArrayList<Expense>) superClasses);
                        response = Setup.CATEGORIA_GASTO;
                    }
                    default -> {
                    }
                }
            }
            if (!response.isEmpty()) {
                reloadDB();
                Logger.getInstance().updateInfoLog(affectedRows + " " + response + " ware successfully saved!");
            }
        }
    }

    public void update(Object obj) throws SQLException {
        String response = "";
        if (obj instanceof Item item) {
            switch (item) {
                case Product product -> {
                    itemProductsCRUD.update(product);
                    response = Setup.CATEGORIA_PRODUCTO;
                }
                case Workable workable -> {
                    itemWorkablesCRUD.update(workable);
                    response = Setup.CATEGORIA_RECETA;
                }
                case RawMaterial rawMaterial -> {
                    itemRawMaterialsCRUD.update(rawMaterial);
                    response = Setup.CATEGORIA_MATERIA_PRIMA;
                }
                case Expense expense -> {
                    itemExpensesCRUD.update(expense);
                    response = Setup.CATEGORIA_GASTO;
                }
                default -> {
                }
            }
            if (!response.isEmpty()) {
                reloadDB();
                Logger.getInstance().updateInfoLog("An existent  " + response + " was updated successfully!");
            }
        }
    }

    public void update(ArrayList<? extends IDSuperClass> superClasses) throws SQLException {
        if (!superClasses.isEmpty()) {
            String response = "";
            int affectedRows = 0;
            if (superClasses.get(0) != null && superClasses.get(0) instanceof Item item) {
                switch (item) {
                    case Product product -> {
                        itemProductsCRUD.update((ArrayList<Product>) superClasses);
                        response = Setup.CATEGORIA_PRODUCTO;
                    }
                    case Workable workable -> {
                        itemWorkablesCRUD.update((ArrayList<Workable>) superClasses);
                        response = Setup.CATEGORIA_RECETA;
                    }
                    case RawMaterial rawMaterial -> {
                        itemRawMaterialsCRUD.update((ArrayList<RawMaterial>) superClasses);
                        response = Setup.CATEGORIA_MATERIA_PRIMA;
                    }
                    case Expense expense -> {
                        itemExpensesCRUD.update((ArrayList<Expense>) superClasses);
                        response = Setup.CATEGORIA_GASTO;
                    }
                    default -> {
                    }
                }
            }
            if (!response.isEmpty()) {
                reloadDB();
                Logger.getInstance().updateInfoLog(affectedRows + " " + response + " ware successfully updated!");
            }
        }
    }

    public void delete(ArrayList<? extends IDSuperClass> superClasses) throws SQLException {
        if (!superClasses.isEmpty()) {
            String response = "";
            int affectedRows = 0;
            if (superClasses.get(0) != null && superClasses.get(0) instanceof Item item) {
                switch (item) {
                    case Product product -> {
                        itemProductsCRUD.delete((ArrayList<Product>) superClasses);
                        response = Setup.CATEGORIA_PRODUCTO;
                    }
                    case Workable workable -> {
                        itemWorkablesCRUD.delete((ArrayList<Workable>) superClasses);
                        response = Setup.CATEGORIA_RECETA;
                    }
                    case RawMaterial rawMaterial -> {
                        itemRawMaterialsCRUD.delete((ArrayList<RawMaterial>) superClasses);
                        response = Setup.CATEGORIA_MATERIA_PRIMA;
                    }
                    case Expense expense -> {
                        itemExpensesCRUD.delete((ArrayList<Expense>) superClasses);
                        response = Setup.CATEGORIA_GASTO;
                    }
                    default -> {
                    }
                }
            }
            if (!response.isEmpty()) {
                reloadDB();
                Logger.getInstance().updateInfoLog(affectedRows + " " + response + " ware successfully deleted!");
            }
        }
    }

    public void archivate(ArrayList<? extends IDSuperClass> superClasses) throws SQLException {
        String sql = "UPDATE items SET archivated = ?, description = ? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Item item : (ArrayList<Item>) superClasses) {
            statement.setBoolean(1, true);
            statement.setString(1, item.getDescription() + " - Archivado");
            statement.setInt(1, item.getId());
            statement.addBatch();
        }
        int[] rowsDeleted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsDeleted.length > 0) {
            Logger.getInstance().updateInfoLog("Some Items ware successfully archivated!");
        }
        reloadDB();
    }

    public void dearchivate(ArrayList<? extends IDSuperClass> superClasses) throws SQLException {
        String sql = "UPDATE items SET archivated = ?, description = ? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Item item : (ArrayList<Item>) superClasses) {
            statement.setBoolean(1, false);
            statement.setString(1, item.getDescription().replace(" - Archivado", ""));
            statement.setInt(1, item.getId());
            statement.addBatch();
        }
        int[] rowsDeleted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsDeleted.length > 0) {
            Logger.getInstance().updateInfoLog("Some Items ware successfully dearchivated!");
        }
        reloadDB();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final TreeMap<Workable, Recipe> allRecipes = new TreeMap<>(Comparator.comparingInt(Workable::getId));
    private final TreeMap<Integer, Ingredient> allIngredients = new TreeMap<>(Comparator.comparingInt(Integer::intValue));

    public int getNecesaryID() {
        int highestID = 0;
        highestID = 0;
        for (Ingredient ingredient : allIngredients.values()) {
            if (ingredient.getId() > highestID) {
                highestID = ingredient.getId();
            }
        }
        highestID = highestID == 0 ? highestID : highestID + 1;
        return highestID == 0 ? highestID + 1 : highestID;
    }

    private ResultSet queryDataBase() throws SQLException {
        String sql = "SELECT * FROM ingredients where item in (select id from items where entity = ?) order by item";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, EntitySelector.currentEntity.getId());
        return statement.executeQuery();
    }

    private void loadIngredients() throws SQLException {
        allIngredients.clear();
        ResultSet result = queryDataBase();
        while (result.next()) {
            Workable workable = (Workable) get(result.getInt(1));
            Item item = get(result.getInt(2));
            double ammount = result.getDouble(3);
            Ingredient ingredient = new Ingredient(getNecesaryID(), workable, item, ammount);
            allIngredients.put(ingredient.getId(), ingredient);
        }
        if (!allIngredients.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Ingredient was successfully got!");
            linkWorkableToItsRecipe();
        }
    }

    public ArrayList<Ingredient> loadDeepIngredients(Workable workable) throws SQLException {
        ArrayList<Ingredient> deepIngredients = new ArrayList<>();
        String sql = "SELECT * FROM GET_INGREDIENTS_RAWS(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, workable.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            RawMaterial rawMaterial = (RawMaterial) get(result.getInt(1));
            double ammount = result.getDouble(2);
            deepIngredients.add(new Ingredient(workable, rawMaterial, ammount));
        }
        return deepIngredients;
    }

    private void loadRecipes() throws SQLException {
        allRecipes.clear();
        for (Map.Entry<MAPSORTER, ? extends Item> entry : itemWorkablesCRUD.getMap().entrySet()) {
            Workable workable = (Workable) entry.getValue();
            Recipe newRecipe = new Recipe(workable.getDescription(), loadDeepIngredients(workable));
            allRecipes.put(workable, newRecipe);
        }
        if (!allRecipes.isEmpty()) {
            Logger.getInstance().printLog("Map of Recipes was succesfully got!", false);
        }
    }

    public void linkWorkableToItsRecipe() throws SQLException {
        loadRecipes();
        for (Map.Entry<?, ? extends Item> entry : items.entrySet()) {
            if (entry.getValue() instanceof Workable workable) {
                casoBase(workable);
            }
        }
    }

    private void casoBase(Workable workable) {
        Recipe recipe = allRecipes.get(workable);
        if (recipe != null) {
            ArrayList<Ingredient> workableIngredients = allRecipes.get(workable).getIngredients();
            for (Ingredient ingredient : workableIngredients) {
                if (ingredient.getItem() instanceof Workable rawMaterialWorkable) {
                    Recipe rawMaterialWorkableRecipe = allRecipes.get(rawMaterialWorkable);
                    if (rawMaterialWorkableRecipe != null) {
                        if (!rawMaterialWorkable.hasRecipe()) {
                            rawMaterialWorkable.setRecipe(rawMaterialWorkableRecipe);
                        }
                    }
                }
            }
            workable.setRecipe(recipe);
        } else {
            System.out.println(workable + " : " + workable.getClass());
        }
    }

    public void modifyRecipe(Workable workable, ArrayList<Ingredient> newIngredients) throws SQLException {
        deleteIngredient(workable);
        workable.setRecipe(new Recipe(workable.getDescription(), newIngredients));
        saveIngredient(workable);
    }

    private void saveIngredient(Workable workable) throws SQLException {
        String sql = "INSERT INTO ingredients(item, ingredient, ammount) VALUES(?, ?, ?)";
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(sql);
        for (Ingredient ingredient : workable.getRecipe().getIngredients()) {
            statement.setInt(1, workable.getId());
            statement.setInt(2, ingredient.getItem().getId());
            statement.setDouble(3, ingredient.getAmmount());
            statement.addBatch();
        }
        int[] rowsInserted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsInserted.length > 0) {
            Logger.getInstance().updateInfoLog("Recipe of " + workable.getDescription() + " was successfully modified!");
            Logger.getInstance().updateInfoLog("With this ingredients:");
            Logger.getInstance().updateInfoLog(workable.getRecipe() + "");
        }
        reloadDB();
    }

    public void deleteIngredient(Workable workable) throws SQLException {
        String sql = "DELETE FROM ingredients WHERE item = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, workable.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("Recipe of " + workable.getDescription() + " was successfully deleted!");
        }
        reloadDB();
    }

}
