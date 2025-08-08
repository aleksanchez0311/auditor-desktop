package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Expense;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.ProductListo;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.model.RawMaterialCocina;
import cu.lacumbre.auditor.model.RawMaterialLista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import static javax.swing.UIManager.get;

public class ItemRawMaterialsCRUD<MAPSORTER> {

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";

    private final ItemsCRUD itemsCRUD;
    private final Connection connection;
    private final Entity entity;

    public ItemRawMaterialsCRUD(ItemsCRUD itemsCRUD) throws SQLException {
        this.itemsCRUD = itemsCRUD;
        this.connection = itemsCRUD.getConnection();
        this.entity = itemsCRUD.getEntity();
    }

    TreeMap<MAPSORTER, RawMaterial> getMap() {
        TreeMap<MAPSORTER, RawMaterial> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, Item> entry : ((TreeMap<MAPSORTER, Item>) itemsCRUD.getItems()).entrySet()) {
            if (entry.getValue() instanceof RawMaterial rawMaterial) {
                map.put((MAPSORTER) rawMaterial.get(itemsCRUD.getComparatorColumn()), rawMaterial);
            }
        }
        return map;
    }

    TreeMap<MAPSORTER, RawMaterialCocina> getRawMaterialsCocinaMap() {
        TreeMap<MAPSORTER, RawMaterialCocina> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, RawMaterial> entry : getMap().entrySet()) {
            if (entry.getValue() instanceof RawMaterialCocina rawMaterialCocina) {
                map.put((MAPSORTER) rawMaterialCocina.get(itemsCRUD.getComparatorColumn()), rawMaterialCocina);
            }
        }
        return map;
    }

    TreeMap<MAPSORTER, RawMaterialLista> getRawMaterialsListaMap() {
        TreeMap<MAPSORTER, RawMaterialLista> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, RawMaterial> entry : getMap().entrySet()) {
            if (entry.getValue() instanceof RawMaterialLista rawMaterialLista) {
                map.put((MAPSORTER) rawMaterialLista.get(itemsCRUD.getComparatorColumn()), rawMaterialLista);
            }
        }
        return map;
    }
    
    ArrayList<RawMaterialLista> getRawMaterialsListaList() {
        return new ArrayList(getRawMaterialsListaMap().values());
    }

    void save(RawMaterial rawMaterial) throws SQLException {
        prepareSimpleStatement(rawMaterial, INSERT).executeUpdate();
    }

    void update(RawMaterial rawMaterial) throws SQLException {
        prepareSimpleStatement(rawMaterial, UPDATE).executeUpdate();
    }
    
    int save(ArrayList<RawMaterial> rawMaterials) throws SQLException {
        connection.setAutoCommit(false);
        int[] insertedRows = prepareBatchStatement(rawMaterials, INSERT).executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return insertedRows.length;
    }

    int update(ArrayList<RawMaterial> rawMaterials) throws SQLException {
        connection.setAutoCommit(false);
        int[] updatedRows = prepareBatchStatement(rawMaterials, UPDATE).executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return updatedRows.length;
    }

    private PreparedStatement prepareSimpleStatement(RawMaterial rawMaterial, String action) throws SQLException {
        PreparedStatement statement = null;
        switch (action) {
            case INSERT -> {
                statement = connection.prepareStatement(SQL_INSERT);
                fillStatement(statement, rawMaterial, action);
            }
            case UPDATE -> {
                statement = connection.prepareStatement(SQL_UPDATE);
                fillStatement(statement, rawMaterial, action);
            }
            default -> {
            }
        }
        return statement;
    }

    private PreparedStatement prepareBatchStatement(ArrayList<RawMaterial> rawMaterials, String action) throws SQLException {
        PreparedStatement statement = null;
        switch (action) {
            case INSERT -> {
                statement = connection.prepareStatement(action);
                for (RawMaterial rawMaterial : rawMaterials) {
                    fillStatement(statement, rawMaterial, action);
                    statement.addBatch();
                }
            }
            case UPDATE -> {
                statement = connection.prepareStatement(action);
                for (RawMaterial rawMaterial : rawMaterials) {
                    fillStatement(statement, rawMaterial, action);
                    statement.addBatch();
                }
            }
            default -> {
            }
        }
        return statement;

    }

    private static final String SQL_INSERT = "INSERT INTO items (id, description, measure_unit, code, entity, archivated, basic_cost, weighted_cost, last_cost, highest_cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE items SET description=?, measure_unit=?, code=?, basic_cost=?, weighted_cost=?, last_cost=?, highest_cost=? WHERE id=?";

    private PreparedStatement fillStatement(PreparedStatement statement, RawMaterial rawMaterial, String action) throws SQLException {
        switch (action) {
            case INSERT -> {
                statement.setInt(1, rawMaterial.getId());
                statement.setString(2, rawMaterial.getDescription());
                statement.setInt(3, rawMaterial.getMeasureUnit().getId());
                statement.setInt(4, rawMaterial.getCode());
                statement.setInt(5, entity.getId());
                statement.setBoolean(6, rawMaterial.isArchivated());
                statement.setDouble(7, rawMaterial.getBasicCost());
                statement.setDouble(8, rawMaterial.getWeightedCost());
                statement.setDouble(9, rawMaterial.getLastCost());
                statement.setDouble(10, rawMaterial.getHighestCost());
            }
            case UPDATE -> {
                statement.setString(1, rawMaterial.getDescription());
                statement.setInt(2, rawMaterial.getMeasureUnit().getId());
                statement.setInt(3, rawMaterial.getCode());
                statement.setDouble(4, rawMaterial.getBasicCost());
                statement.setDouble(5, rawMaterial.getWeightedCost());
                statement.setDouble(6, rawMaterial.getLastCost());
                statement.setDouble(7, rawMaterial.getHighestCost());
                statement.setInt(8, rawMaterial.getId());
            }
            default -> {
            }
        }
        return statement;
    }

    int delete(ArrayList<RawMaterial> rawMaterials) throws SQLException {
        int deletedRows = 0;
        ArrayList<Product> productsListosToDelete = new ArrayList<>();
        String sql = "DELETE FROM items WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (RawMaterial rawMaterial : rawMaterials) {
            if (rawMaterial instanceof RawMaterialLista rawMaterialLista) {
                productsListosToDelete.add(getProductListoOf(rawMaterialLista));
            }
            statement.setInt(1, rawMaterial.getId());
            statement.addBatch();
        }
        int[] rowsDeleted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        deletedRows = rowsDeleted.length;
        if (!productsListosToDelete.isEmpty()) {
            deletedRows += itemsCRUD.getItemProductsCRUD().delete(productsListosToDelete);
        }
        return deletedRows;
    }

    public ProductListo getProductListoOf(RawMaterial rawMaterial) throws SQLException {
        if (rawMaterial instanceof RawMaterialLista rawMaterialLista) {
            String sql = "SELECT * FROM ingredients WHERE ingredient = ?";
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setInt(1, rawMaterialLista.getId());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return (ProductListo) itemsCRUD.get(result.getInt(1));
            }
        }
        return null;
    }

    int getNextCocinaId() {
        int highestID = 0;
        for (RawMaterialCocina rawMaterialCocina : getRawMaterialsCocinaMap().values()) {
            if (rawMaterialCocina.getId() > highestID) {
                highestID = rawMaterialCocina.getId();
            }
        }
        return highestID == 0 ? highestID + Setup.ID_START_FOR_MATERIAS_PRIMAS_COCINA : highestID + 1;
    }

    int getNextListaId() {
        int highestID = 0;
        for (RawMaterialLista rawMaterialLista : getRawMaterialsListaMap().values()) {
            if (rawMaterialLista.getId() > highestID) {
                highestID = rawMaterialLista.getId();
            }
        }
        return highestID == 0 ? highestID + Setup.ID_START_FOR_MATERIAS_PRIMAS_LISTAS : highestID + 1;
    }
}
