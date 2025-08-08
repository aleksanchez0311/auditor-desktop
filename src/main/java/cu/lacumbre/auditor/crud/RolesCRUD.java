package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Role;
import cu.lacumbre.utils.Logger;
import java.sql.*;
import java.util.*;

public class RolesCRUD implements ModelCRUDSingle {

    private final TreeMap<Integer, Role> map;
    private final Entity entity;
    private final Connection connection;

    public RolesCRUD(Connection connection) throws SQLException {
        this.connection = connection;
        this.map = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        entity = EntitySelector.currentEntity;
        String sql = "SELECT * FROM roles WHERE entity = ? ORDER BY id";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, entity.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt(1);
            String descripcion = result.getString(2);
            double payment = result.getDouble(3);
            double daysToWork = result.getDouble(4);
            double hoursToWork = result.getDouble(5);
            Role role = new Role(id, descripcion, payment, daysToWork, hoursToWork);
            map.put(id, role);
        }
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Roles was successfully got!");
        }
    }

    public ArrayList<Role> getList() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Role get(int id) {
        return map.get(id);
    }

    @Override
    public void save(Object obj) throws SQLException {
        Role object = (Role) obj;
        String sql = "INSERT INTO roles (description, payment, days_to_work, hours_to_work, entity) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setDouble(2, object.getPayment());
        statement.setDouble(3, object.getDaysToWork());
        statement.setDouble(4, object.getHoursToWork());
        statement.setInt(5, entity.getId());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A new " + object.getClass().getSimpleName() + " was successfully saved!");
        }
    }

    @Override
    public void update(Object obj) throws SQLException {
        Role object = (Role) obj;
        String sql = "UPDATE roles SET description=?, payment=?, days_to_work=?, hours_to_work=? WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
         statement.setString(1, object.getDescription());
        statement.setDouble(2, object.getPayment());
        statement.setDouble(3, object.getDaysToWork());
        statement.setDouble(4, object.getHoursToWork());
        statement.setInt(5, object.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getInstance().updateInfoLog("An existing " + object.getClass().getSimpleName() + " was successfully updated!");
        }
    }

    @Override
    public void delete(Object obj) throws SQLException {
        Role object = (Role) obj;
        String sql = "DELETE FROM roles WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, object.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("A " + object.getClass().getSimpleName() + " was successfully deleted!");
        }
    }
}
