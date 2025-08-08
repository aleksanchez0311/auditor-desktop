package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.model.MeasureUnit;
import cu.lacumbre.utils.Logger;
import java.sql.*;
import java.util.*;

public class MeasureUnitsCRUD implements ModelCRUDSingle {

    private final TreeMap<Integer, MeasureUnit> map;
    private final Connection connection;
    private final int entity = EntitySelector.currentEntity.getId();

    public MeasureUnitsCRUD(Connection connection) throws SQLException {
        this.connection = connection;
        this.map = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        String sql = "SELECT * FROM measure_units where entity = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, entity);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt(1);
            String descripcion = result.getString(2);
            String abreviatura = result.getString(3);
            MeasureUnit measureUnit = new MeasureUnit(id, descripcion, abreviatura);
            map.put(id, measureUnit);
        }
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Measure Units was successfully got!");
        }
    }

    public ArrayList<MeasureUnit> getList() {
        return new ArrayList<>(map.values());
    }

    public TreeMap<Integer, MeasureUnit> getMap() {
        return map;
    }

    @Override
    public MeasureUnit get(int id) {
        return map.get(id);
    }
    public MeasureUnit getIfContains(String desc) {
        for (Map.Entry<Integer, MeasureUnit> entry : map.entrySet()) {
            MeasureUnit value = entry.getValue();
            if(value.getDescription().contains(desc)){
                return value;
            }
        }
        return null;
    }

    @Override
    public void save(Object obj) throws SQLException {
        MeasureUnit object = (MeasureUnit) obj;
        String sql = "INSERT INTO measure_units (description, abrev, entity) VALUES (?, ?, ?)";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setString(2, object.getAbrev());
        statement.setInt(3, entity);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A new " + object.getClass().getSimpleName() + " was successfully saved!");
        }
    }

    @Override
    public void update(Object obj) throws SQLException {
        MeasureUnit object = (MeasureUnit) obj;
        String sql = "UPDATE measure_units SET description=?, abrev=?  WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setString(2, object.getAbrev());
        statement.setInt(3, object.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getInstance().updateInfoLog("An existing " + object.getClass().getSimpleName() + " was successfully updated!");
        }
    }

    @Override
    public void delete(Object obj) throws SQLException {
        MeasureUnit object = (MeasureUnit) obj;
        String sql = "DELETE FROM measure_units WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, object.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("A " + object.getClass().getSimpleName() + " was successfully deleted!");
        }
    }

}
