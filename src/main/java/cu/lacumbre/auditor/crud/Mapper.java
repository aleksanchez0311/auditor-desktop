package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Mapper {

    private final TreeMap<String, Product> map;
    private final TreeMap<String, Integer> idsMap;
    private final ItemsCRUD itemsCRUD;
    private final Connection connection;
    private final Entity entity;

    public Mapper(Connection connection, ItemsCRUD itemsCRUD) throws SQLException {
        this.itemsCRUD = itemsCRUD;
        this.connection = connection;
        this.entity = EntitySelector.currentEntity;
        this.map = new TreeMap<>(Comparator.comparing(String::toString));
        this.idsMap = new TreeMap<>(Comparator.comparing(String::toString));
        loadBD();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of " + TreeMap.class.getSimpleName() + " was successfully got!");
        }
    }

    private void reloadBD() throws SQLException {
        loadBD();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of " + TreeMap.class.getSimpleName() + " was successfully refreshed!");
        }
    }

    private void loadBD() throws SQLException {
        map.clear();
        idsMap.clear();
        String sql = "SELECT id, item, name, active FROM map where entity = ? order by item";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, entity.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt(1);
            Item thing = itemsCRUD.get(result.getInt(2));
            Product product = thing != null && thing instanceof Product ? (Product) thing : Product.generate();
            String name = result.getString(3);
            boolean active = result.getBoolean(4);
            if (product != null) {
                    map.put(name, product);
                    idsMap.put(name, id);
            }
        }
    }

    public TreeMap<String, Product> getMap() {
        return map;
    }

    public TreeMap<String, Product> getUnmappedMap() {
        TreeMap<String, Product> unmappedMap = new TreeMap<>(Comparator.comparing(String::toString));
        for (Map.Entry<String, Product> entry : map.entrySet()) {
            String key = entry.getKey();
            Product value = entry.getValue();
            if (value.equals(Product.generate())) {
                unmappedMap.put(key, value);
            }
        }
        return unmappedMap;
    }

    public ArrayList<String> getOthMap() {
        TreeMap<Product, List<String>> othMap = new TreeMap<>(Comparator.comparingInt(Product::getCode));
        for (Map.Entry<String, Product> entry : map.entrySet()) {
            ArrayList<String> productDescriptions = new ArrayList<>();
            Product currentProduct = entry.getValue();
            for (java.util.Map.Entry<String, Product> entry1 : map.entrySet()) {
                String description = entry1.getKey();
                Product product = entry1.getValue();
                if (currentProduct.equals(product)) {
                    productDescriptions.add(description);
                }
            }
            othMap.put(currentProduct, productDescriptions);
        }
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<Product, List<String>> entry : othMap.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    public ItemsCRUD getItemsCRUD() {
        return itemsCRUD;
    }

    public Product get(String name) {
        Product result = null;
        for (Map.Entry<String, Product> entry : map.entrySet()) {
            String key = entry.getKey();
            Product product = entry.getValue();
            if (key.equals(name)) {
                result = product;
                break;
            }
        }
        return result;
    }

    public void save(String name, Product product, boolean active) throws SQLException {
        boolean containsKey = map.containsKey(name);
        if (containsKey) {
            update(name, product, idsMap.get(name));
        } else {
            String sql = "INSERT INTO map (name, item, entity, active) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, product != null ? product.getId() : 0);
            statement.setInt(3, entity.getId());
            statement.setBoolean(4, active);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Logger.getInstance().updateInfoLog("Product mapped as inactive!");
            }
        }
        reloadBD();
    }

    private void update(String name, Product product, int id) throws SQLException {
        String sql = "UPDATE map set name = ?, item = ? where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setInt(2, product != null ? product.getId() : 0);
        statement.setInt(3, id);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("Product map modified!");
        }
    }

    public void remove(String name, Product product) throws SQLException {
        String sql = "DELETE FROM map WHERE name = ? AND item = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setInt(2, product != null ? product.getId() : 0);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("Product unmapped!");
        }
        reloadBD();
    }
}
