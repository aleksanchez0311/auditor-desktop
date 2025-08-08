package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.Workable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ItemWorkablesCRUD<MAPSORTER> {

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";

    private final ItemsCRUD itemsCRUD;
    private final Connection connection;
    private final Entity entity;

    public ItemWorkablesCRUD(ItemsCRUD itemsCRUD) throws SQLException {
        this.itemsCRUD = itemsCRUD;
        this.connection = itemsCRUD.getConnection();
        this.entity = itemsCRUD.getEntity();
    }

    TreeMap<MAPSORTER, Workable> getMap() {
        TreeMap<MAPSORTER, Workable> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, Item> entry : ((TreeMap<MAPSORTER, Item>) itemsCRUD.getItems()).entrySet()) {
            if (entry.getValue() instanceof Workable workable) {
                map.put((MAPSORTER) workable.get(itemsCRUD.getComparatorColumn()), workable);
            }
        }
        return map;
    }

    TreeMap<MAPSORTER, Workable> getWorkableMap() {
        TreeMap<MAPSORTER, Workable> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, Workable> entry : getMap().entrySet()) {
            if (entry.getValue() instanceof Product) {
            } else {
                map.put((MAPSORTER) entry.getValue().get(itemsCRUD.getComparatorColumn()), entry.getValue());
            }
        }
        return map;
    }

    void save(Workable workable) throws SQLException {
        prepareSimpleStatement(workable, INSERT).executeUpdate();
    }

    void update(Workable workable) throws SQLException {
        prepareSimpleStatement(workable, UPDATE).executeUpdate();
    }

    int save(ArrayList<Workable> workables) throws SQLException {
        connection.setAutoCommit(false);
        int[] insertedRows = prepareBatchStatement(workables, INSERT).executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return insertedRows.length;
    }

    int update(ArrayList<Workable> workables) throws SQLException {
        connection.setAutoCommit(false);
        int[] updatedRows = prepareBatchStatement(workables, UPDATE).executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return updatedRows.length;
    }

    private PreparedStatement prepareSimpleStatement(Workable workable, String action) throws SQLException {
        PreparedStatement statement = null;
        switch (action) {
            case INSERT -> {
                statement = connection.prepareStatement(SQL_INSERT);
                fillStatement(statement, workable, action);
            }
            case UPDATE -> {
                statement = connection.prepareStatement(SQL_UPDATE);
                fillStatement(statement, workable, action);
            }
            default -> {
            }
        }
        return statement;
    }

    private PreparedStatement prepareBatchStatement(ArrayList<Workable> workables, String action) throws SQLException {
        PreparedStatement statement = null;
        switch (action) {
            case INSERT -> {
                statement = connection.prepareStatement(action);
                for (Workable workable : workables) {
                    fillStatement(statement, workable, action);
                    statement.addBatch();
                }
            }
            case UPDATE -> {
                statement = connection.prepareStatement(action);
                for (Workable workable : workables) {
                    fillStatement(statement, workable, action);
                    statement.addBatch();
                }
            }
            default -> {
            }
        }
        return statement;

    }

    private static final String SQL_INSERT = "INSERT INTO items (id, description, measure_unit, code, entity, archivated) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE items SET description=?, measure_unit=?, code=? WHERE id=?";

    private PreparedStatement fillStatement(PreparedStatement statement, Workable workable, String action) throws SQLException {
        switch (action) {
            case INSERT -> {
                statement.setInt(1, workable.getId());
                statement.setString(2, workable.getDescription());
                statement.setInt(3, workable.getMeasureUnit().getId());
                statement.setInt(4, workable.getCode());
                statement.setInt(5, entity.getId());
                statement.setBoolean(6, workable.isArchivated());
            }
            case UPDATE -> {
                statement.setString(1, workable.getDescription());
                statement.setInt(2, workable.getMeasureUnit().getId());
                statement.setInt(3, workable.getCode());
                statement.setInt(4, workable.getId());
            }
            default -> {
            }
        }
        return statement;
    }

    int delete(ArrayList<Workable> workables) throws SQLException {
        String sql = "DELETE FROM items WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Workable workable : workables) {
            statement.setInt(1, workable.getId());
            statement.addBatch();
        }
        int[] rowsDeleted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return rowsDeleted.length;
    }

    int getNextId() {
        int highestID = 0;
        for (Workable workable : getWorkableMap().values()) {
            if (workable.getId() > highestID) {
                highestID = workable.getId();
            }
        }
        return highestID == 0 ? highestID + Setup.ID_START_FOR_RECETAS : highestID + 1;
    }
}
