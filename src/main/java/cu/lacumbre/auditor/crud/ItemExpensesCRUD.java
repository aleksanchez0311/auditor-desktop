package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Expense;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.RawMaterial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ItemExpensesCRUD<MAPSORTER> {

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";
    private static final String UPDATE_COST = "UPDATE";

    private final ItemsCRUD itemsCRUD;
    private final Connection connection;
    private final Entity entity;

    public ItemExpensesCRUD(ItemsCRUD itemsCRUD) throws SQLException {
        this.itemsCRUD = itemsCRUD;
        this.connection = itemsCRUD.getConnection();
        this.entity = itemsCRUD.getEntity();
    }

    TreeMap<MAPSORTER, Expense> getMap() {
        TreeMap<MAPSORTER, Expense> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, Item> entry : ((TreeMap<MAPSORTER, Item>) itemsCRUD.getItems()).entrySet()) {
            if (entry.getValue() instanceof Expense expense) {
                map.put((MAPSORTER) expense.get(itemsCRUD.getComparatorColumn()), expense);
            }
        }
        return map;
    }

    TreeMap<MAPSORTER, Expense> getExpensesMap() {
        TreeMap<MAPSORTER, Expense> map = new TreeMap<>();
        for (Map.Entry<MAPSORTER, Expense> entry : getMap().entrySet()) {
            if (entry.getValue() instanceof RawMaterial) {
            } else {
                map.put((MAPSORTER) entry.getValue().get(itemsCRUD.getComparatorColumn()), entry.getValue());
            }
        }
        return map;
    }

    void save(Expense expense) throws SQLException {
        prepareSimpleStatement(expense, INSERT).executeUpdate();
    }

    void update(Expense expense) throws SQLException {
        prepareSimpleStatement(expense, UPDATE).executeUpdate();
    }
    
    int save(ArrayList<Expense> expenses) throws SQLException {
        connection.setAutoCommit(false);
        int[] rowsInserted = prepareBatchStatement(expenses, INSERT).executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return rowsInserted.length;
    }

    int update(ArrayList<Expense> expenses) throws SQLException {
        connection.setAutoCommit(false);
        int[] updatedRows = prepareBatchStatement(expenses, UPDATE).executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return updatedRows.length;
    }

    private PreparedStatement prepareSimpleStatement(Expense expense, String action) throws SQLException {
        PreparedStatement statement = null;
        switch (action) {
            case INSERT -> {
                statement = connection.prepareStatement(SQL_INSERT);
                fillStatement(statement, expense, action);
            }
            case UPDATE -> {
                statement = connection.prepareStatement(SQL_UPDATE);
                fillStatement(statement, expense, action);
            }
            default -> {
            }
        }
        return statement;
    }

    private PreparedStatement prepareBatchStatement(ArrayList<Expense> expenses, String action) throws SQLException {
        PreparedStatement statement = null;
        switch (action) {
            case INSERT -> {
                statement = connection.prepareStatement(action);
                for (Expense expense : expenses) {
                    fillStatement(statement, expense, action);
                    statement.addBatch();
                }
            }
            case UPDATE -> {
                statement = connection.prepareStatement(action);
                for (Expense expense : expenses) {
                    fillStatement(statement, expense, action);
                    statement.addBatch();
                }
            }
            default -> {
            }
        }
        return statement;

    }

    private static final String SQL_INSERT = "INSERT INTO items (id, description, measure_unit, code, entity, archivated, basic_cost) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE items SET description=?, measure_unit=?, code=?, basic_cost=? WHERE id=?";

    private PreparedStatement fillStatement(PreparedStatement statement, Expense expense, String action) throws SQLException {
        switch (action) {
            case INSERT -> {
                statement.setInt(1, expense.getId());
                statement.setString(2, expense.getDescription());
                statement.setInt(3, expense.getMeasureUnit().getId());
                statement.setInt(4, expense.getCode());
                statement.setInt(5, entity.getId());
                statement.setBoolean(6, expense.isArchivated());
                statement.setDouble(7, expense.getCost());
            }
            case UPDATE -> {
                statement.setString(1, expense.getDescription());
                statement.setInt(2, expense.getMeasureUnit().getId());
                statement.setInt(3, expense.getCode());
                statement.setDouble(4, expense.getCost());
                statement.setInt(5, expense.getId());
            }
            default -> {
            }
        }
        return statement;
    }

    int delete(ArrayList<Expense> expenses) throws SQLException {
        String sql = "DELETE FROM items WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Expense expense : expenses) {
            statement.setInt(1, expense.getId());
            statement.addBatch();
        }
        int[] rowsDeleted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        return rowsDeleted.length;
    }

    int getNextId() {
        int highestID = 0;
        for (Expense expense : getExpensesMap().values()) {
            if (expense.getId() > highestID) {
                highestID = expense.getId();
            }
        }
        return highestID == 0 ? highestID + Setup.ID_START_FOR_GASTOS : highestID + 1;
    }
}
