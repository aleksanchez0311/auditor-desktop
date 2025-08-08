package cu.lacumbre.auditor;

import cu.lacumbre.auditor.crud.EntitiesCRUD;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Timing;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DaysController {

    private final Connection connection;
    private final EntitiesCRUD entitiesCRUD;
    private Entity entity;
    private Timing timing;

    public DaysController(Connection connection, EntitiesCRUD entitiesCRUD) {
        this.connection = connection;
        this.entitiesCRUD = entitiesCRUD;
        this.entity = EntitySelector.currentEntity;
        this.timing = new Timing(entity.getCurrentDay());
    }

    public void rewindDay() throws SQLException {
        if (entity.isFilled()) {
            unfillDay();
        }
        String sql = "UPDATE entities set filled = true, closed = true, current=? where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(timing.minusDays(1)));
        statement.setInt(2, entity.getId());
        statement.executeUpdate();
        Logger.getInstance().updateInfoLog("Day " + entity.getCurrentDay() + " of entitiy " + entity.getDescription() + " was successfully rewind!");
        commitChanges();
    }

    public void unfillDay() throws SQLException {
        String sql = "delete from operations where date >= ? and entity = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setTimestamp(1, Timestamp.from(timing.atMorningOfDay()));
        statement.setInt(2, entity.getId());
        int affectedRows = statement.executeUpdate();
        if (affectedRows > 0) {
            Logger.getInstance().updateInfoLog(affectedRows + " operations of day " + entity.getCurrentDay() + " of entitiy " + entity.getDescription() + " ware successfully deleted!");
        }
        sql = "UPDATE entities SET filled = false, closed = false WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, entity.getId());
        statement.executeUpdate();
        Logger.getInstance().updateInfoLog("Day " + entity.getCurrentDay() + " of entitiy " + entity.getDescription() + " was successfully restarted!");
        commitChanges();
    }

    public void fillDay() throws SQLException {
        String sql = "UPDATE entities SET filled = true WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, entity.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("Day " + entity.getCurrentDay() + " of entitiy " + entity.getDescription() + " was successfully filled!");
        }
        commitChanges();
    }

    private void moveToNext() throws SQLException {
        String sql = "UPDATE entities SET filled = false, closed = false, current = ? WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(timing.plusDays(1)));
        statement.setInt(2, entity.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("Day " + entity.getCurrentDay() + " of entitiy " + entity.getDescription() + " was successfully passed!");
        }
        commitChanges();
    }

    public void closeDay() throws SQLException {
        moveToNext();
        Logger.getInstance().updateInfoLog("Day " + entity.getCurrentDay() + " of entitiy " + entity.getDescription() + " was successfully closed!");
    }

    private void commitChanges() throws SQLException {
        entitiesCRUD.reloadDB();
        EntitySelector.currentEntity = entitiesCRUD.get(entity.getId());
        entity = EntitySelector.currentEntity;
        timing = new Timing(entity.getCurrentDay());
    }
}
