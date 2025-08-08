package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.ProductCocina;
import cu.lacumbre.auditor.model.ProductListo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ItemProductsCRUD<MAPSORTER> {

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";

    private final ItemsCRUD itemsCRUD;
    private final Connection connection;
    private final Entity entity;

    public ItemProductsCRUD(ItemsCRUD itemsCRUD) throws SQLException {
        this.itemsCRUD = itemsCRUD;
        this.connection = itemsCRUD.getConnection();
        this.entity = itemsCRUD.getEntity();
    }

    public ArrayList<Product> getList() {
        return new ArrayList(getMap().values());
    }

    public TreeMap<MAPSORTER, Product> getMap() {
        TreeMap<MAPSORTER, Product> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, Item> entry : ((TreeMap<MAPSORTER, Item>) itemsCRUD.getItems()).entrySet()) {
            if (entry.getValue() instanceof Product product) {
                map.put((MAPSORTER) product.get(itemsCRUD.getComparatorColumn()), product);
            }
        }
        return map;
    }

    public ArrayList<Product> getProductsCocinaList() {
        return new ArrayList(getProductsCocinaMap().values());
    }

    public TreeMap<MAPSORTER, ProductCocina> getProductsCocinaMap() {
        TreeMap<MAPSORTER, ProductCocina> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, Product> entry : getMap().entrySet()) {
            if (entry.getValue() instanceof ProductCocina productCocina) {
                map.put((MAPSORTER) productCocina.get(itemsCRUD.getComparatorColumn()), productCocina);
            }
        }
        return map;
    }

    public ArrayList<Product> getProductsListoList() {
        return new ArrayList(getProductsListoMap().values());
    }

    TreeMap<MAPSORTER, ProductListo> getProductsListoMap() {
        TreeMap<MAPSORTER, ProductListo> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, Product> entry : getMap().entrySet()) {
            if (entry.getValue() instanceof ProductListo productListo) {
                map.put((MAPSORTER) productListo.get(itemsCRUD.getComparatorColumn()), productListo);
            }
        }
        return map;
    }

    void save(Product product) throws SQLException {
        prepareSimpleStatement(product, INSERT).executeUpdate();
    }

    void update(Product product) throws SQLException {
        prepareSimpleStatement(product, UPDATE).executeUpdate();
    }

    int save(ArrayList<Product> products) throws SQLException {
        connection.setAutoCommit(false);
        int[] insertedRows = prepareBatchStatement(products, INSERT).executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return insertedRows.length;
    }

    int update(ArrayList<Product> products) throws SQLException {
        connection.setAutoCommit(false);
        int[] updatedRows = prepareBatchStatement(products, UPDATE).executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return updatedRows.length;
    }

    private PreparedStatement prepareSimpleStatement(Product product, String action) throws SQLException {
        PreparedStatement statement = null;
        switch (action) {
            case INSERT -> {
                statement = connection.prepareStatement(SQL_INSERT);
                fillStatement(statement, product, action);
            }
            case UPDATE -> {
                statement = connection.prepareStatement(SQL_UPDATE);
                fillStatement(statement, product, action);
            }
            default -> {
            }
        }
        return statement;
    }

    private PreparedStatement prepareBatchStatement(ArrayList<Product> products, String action) throws SQLException {
        PreparedStatement statement = null;
        switch (action) {
            case INSERT -> {
                statement = connection.prepareStatement(action);
                for (Product product : products) {
                    fillStatement(statement, product, action);
                    statement.addBatch();
                }
            }
            case UPDATE -> {
                statement = connection.prepareStatement(action);
                for (Product product : products) {
                    fillStatement(statement, product, action);
                    statement.addBatch();
                }
            }
            default -> {
            }
        }
        return statement;

    }

    private static final String SQL_INSERT = "INSERT INTO items (id, description, measure_unit, code, entity, archivated, price, cost_sheet, tpv_category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE items SET description=?, measure_unit=?, code=?, price=?, cost_sheet=?, tpv_category=? WHERE id=?";

    private PreparedStatement fillStatement(PreparedStatement statement, Product product, String action) throws SQLException {
        switch (action) {
            case INSERT -> {
                statement.setInt(1, product.getId());
                statement.setString(2, product.getDescription());
                statement.setInt(3, product.getMeasureUnit().getId());
                statement.setInt(4, product.getCode());
                statement.setInt(5, entity.getId());
                statement.setBoolean(6, product.isArchivated());
                statement.setDouble(7, product.getPrice());
                statement.setInt(8, product.getCostSheet().getId());
                statement.setInt(9, product.getTpvCategory().getId());
            }
            case UPDATE -> {
                statement.setString(1, product.getDescription());
                statement.setInt(2, product.getMeasureUnit().getId());
                statement.setInt(3, product.getCode());
                statement.setDouble(4, product.getPrice());
                statement.setInt(5, product.getCostSheet().getId());
                statement.setInt(6, product.getTpvCategory().getId());
                statement.setInt(7, product.getId());
            }
            default -> {
            }
        }
        return statement;
    }

    int delete(ArrayList<Product> products) throws SQLException {
        String sql = "DELETE FROM items WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Product product : products) {
            statement.setInt(1, product.getId());
            statement.addBatch();
        }
        int[] rowsDeleted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return rowsDeleted.length;
    }

    public int getNextCocinaId() {
        int highestID = 0;
        for (Product product : getProductsCocinaList()) {
            if (product.getId() > highestID) {
                highestID = product.getId();
            }
        }
        return highestID == 0 ? highestID + Setup.ID_START_FOR_PRODUCTOS_COCINA : highestID + 1;
    }

    public int getNextListoId() {
        int highestID = 0;
        for (Product product : getProductsListoList()) {
            if (product.getId() > highestID) {
                highestID = product.getId();
            }
        }
        return highestID == 0 ? highestID + Setup.ID_START_FOR_PRODUCTOS_LISTO : highestID + 1;
    }

    public void updatePrices(ArrayList<Map.Entry<Product, Double[]>> objects) throws SQLException {
        for (Map.Entry<Product, Double[]> entry : objects) {
            Product key = (Product) entry.getKey();
            Double[] values = entry.getValue();
            if (key.isCompuesto()) {
                key.setPrice(values[1] + key.getCompuesto().getPrice());
            } else {
                key.setPrice(values[1]);
            }
            update(key);
        }
    }
}
