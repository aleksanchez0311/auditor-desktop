package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.model.Attendance;
import cu.lacumbre.auditor.model.CustomDocument;
import cu.lacumbre.utils.MonthlyPeriod;
import cu.lacumbre.auditor.model.Payroll;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class DocumentsCRUD implements ModelCRUDSingle {

    private final TreeMap<Integer, CustomDocument> map;
    private final MonthlyPeriod period;
    private final Connection connection;

    public DocumentsCRUD(Connection connection, MonthlyPeriod period) throws SQLException {
        this.connection = connection;
        this.period = period;
        map = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Documents was successfully got!");
        }
    }

    public void reloadDB() throws SQLException {
        loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Documents was successfully refreshed!");
        }
    }

    private void loadDB() throws SQLException {
        map.clear();
        String sql = "SELECT * FROM documents WHERE entity = ? AND period = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, EntitySelector.currentEntity.getId());
        statement.setString(2, period.getDescription());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt(1);
            String description = result.getString(2);
            boolean completed = result.getBoolean(3);
            switch (description) {
                case CustomDocument.ATTENDANCE_REPORT -> {
                    Attendance attendance = new Attendance(id, description, period.getDescription(), completed);
                    map.put(id, attendance);
                }
                case CustomDocument.PAYROLL -> {
                    Payroll payroll = new Payroll(id, description, period.getDescription(), completed);
                    map.put(id, payroll);
                }
                default -> {
                }
            }
        }
    }

    @Override
    public CustomDocument get(int id) {
        return map.get(id);
    }

    public CustomDocument get(String description) {
        for (CustomDocument customDocument : getList()) {
            if(customDocument.getDescription().equals(description)){
                return customDocument;
            }
        }
        return null;
    }

    public ArrayList<CustomDocument> getList() {
        return new ArrayList<>(map.values());
    }

    public TreeMap<Integer, CustomDocument> getMap() {
        return map;
    }

    @Override
    public void save(Object object) throws SQLException {
        CustomDocument customDocument = (CustomDocument) object;
        String sql = "INSERT INTO documents (description, completed, entity, period) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, customDocument.getDescription());
        statement.setBoolean(2, customDocument.isCompleted());
        statement.setInt(3, EntitySelector.currentEntity.getId());
        statement.setString(4, period.getDescription());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A new " + object.getClass().getSimpleName() + " was successfully saved!");
        }
        reloadDB();
    }

    @Override
    public void update(Object object) throws SQLException {
        CustomDocument customDocument = (CustomDocument) object;
        String sql = "UPDATE documents SET completed = true WHERE id = ?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, customDocument.getId());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A " + object.getClass().getSimpleName() + " was successfully completed!");
        }
        reloadDB();
    }

    @Override
    public void delete(Object object) throws SQLException {
        CustomDocument customDocument = (CustomDocument) object;
        String sql = "DELETE FROM documents WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, customDocument.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("A " + object.getClass().getSimpleName() + " was successfully deleted!");
        }
        reloadDB();
    }

}
