package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class EntitiesCRUD implements ModelCRUDSingle {

    private final TreeMap<Integer, Entity> map;
    private TreeMap<Integer, Entity> actives;
    private TreeMap<Integer, Entity> inactives;
    private final Entity defaultEntity;
    private final Connection connection;
    private final Comparator comparator = Comparator.comparingInt(Integer::intValue);

    public EntitiesCRUD(Connection connection) throws SQLException {
        this.connection = connection;
        this.map = new TreeMap<>(comparator);
        defaultEntity = loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Entities was successfully got!");
        }
    }

    public void reloadDB() throws SQLException {
        loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Entities was successfully refreshed!");
        }
    }

    private Entity loadDB() throws SQLException {
        map.clear();
        Entity tempEntity = null;
        String sql = "SELECT * FROM entities order by id";
        Statement statement;
        statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt(1);
            String descripcion = result.getString(2);
            boolean isWorkable = result.getBoolean(3);
            LocalDate first = result.getDate(4).toLocalDate();
            LocalDate current = result.getDate(5).toLocalDate();
            boolean isFilled = result.getBoolean(6);
            boolean isClosed = result.getBoolean(7);
            boolean isMagic = result.getBoolean(8);
            double mammount = result.getDouble(9);
            boolean isActive = result.getBoolean(10);
            Entity entity = new Entity(id, descripcion, isWorkable, first, current, isFilled, isClosed, isMagic, mammount, isActive);
            map.put(id, entity);
        }
        tempEntity = map.get(0);
        inactives = new TreeMap<>(comparator);
        actives = new TreeMap<>(comparator);
        map.forEach((key, value) -> {
            if (key > 0) {
                inactives.put(key, value);
            }
        });
        inactives.forEach((key, value) -> {
            if (value.isActive()) {
                actives.put(key, value);
            }
        });
        return tempEntity;
    }

    public Entity getDefaultEntity() {
        return defaultEntity;
    }

    public ArrayList<Entity> getList() {
        return new ArrayList<>(map.values());
    }

    public ArrayList<Entity> getActiveList(boolean enterpriseLevelEnabled) {
        ArrayList<Entity> list = new ArrayList<>(actives.values());
        if (!list.contains(defaultEntity) && enterpriseLevelEnabled) {
            list.add(defaultEntity);
        } else if (list.contains(defaultEntity) && !enterpriseLevelEnabled) {
            list.remove(defaultEntity);
        }
        return list;
    }

    public ArrayList<Entity> getInactiveList(boolean enterpriseLevelEnabled) {
        ArrayList<Entity> list = new ArrayList<>(inactives.values());
        if (!list.contains(defaultEntity) && enterpriseLevelEnabled) {
            list.add(defaultEntity);
        } else if (list.contains(defaultEntity) && !enterpriseLevelEnabled) {
            list.remove(defaultEntity);
        }
        return list;
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Entity get(int id) {
        return map.get(id);
    }

    @Override
    public void save(Object obj) throws SQLException {
        Entity object = (Entity) obj;
        String sql = "INSERT INTO entities (description, workable, start, current, filled, closed, magic, mammount, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setBoolean(2, object.isWorkable());
        statement.setDate(3, Date.valueOf(object.getFirstDay()));
        statement.setDate(4, Date.valueOf(object.getFirstDay()));
        statement.setBoolean(5, object.isFilled());
        statement.setBoolean(6, object.isClosed());
        statement.setBoolean(7, object.isMagic());
        statement.setDouble(8, object.getMAmmount());
        statement.setBoolean(9, object.isActive());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A new " + object.getClass().getSimpleName() + " was successfully saved!");
        }
        reloadDB();
    }

    @Override
    public void update(Object obj) throws SQLException {
        Entity object = (Entity) obj;
        String sql = "UPDATE entities SET description=?, workable=?, start=?, current=?, filled=?, closed=?, mammount=?, active=? WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setBoolean(2, object.isWorkable());
        statement.setDate(3, Date.valueOf(object.getFirstDay()));
        statement.setDate(4, Date.valueOf(object.getCurrentDay()));
        statement.setBoolean(5, object.isFilled());
        statement.setBoolean(6, object.isClosed());
        statement.setDouble(7, object.getMAmmount());
        statement.setBoolean(8, object.isActive());
        statement.setInt(9, object.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getInstance().updateInfoLog("An existing " + object.getClass().getSimpleName() + " was successfully updated!");
        }
        reloadDB();
    }

    @Override
    public void delete(Object obj) throws SQLException {
        Entity object = (Entity) obj;
        String sql = "DELETE FROM entities WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, object.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("A " + object.getClass().getSimpleName() + " was successfully deleted!");
        }
        reloadDB();
    }

    public class DayController {

        public void rewindDay(Entity object) throws SQLException {
            if (object.isFilled()) {
                unfillDay(object);
            }
            String sql = "UPDATE entities set filled = true, closed = true, current=? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(object.getCurrentDay().minusDays(1)));
            statement.setInt(2, object.getId());
            statement.executeUpdate();
            Logger.getInstance().updateInfoLog("Day " + object.getCurrentDay() + " of entitiy " + object.getDescription() + " was successfully rewind!");
            reloadDB();
        }

        public void unfillDay(Entity object) throws SQLException {
            String sql = "delete from operations where date >= ? and entity = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(object.getCurrentDay()));
            statement.setInt(2, object.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                Logger.getInstance().updateInfoLog(affectedRows + " operations of day " + object.getCurrentDay() + " of entitiy " + object.getDescription() + " ware successfully deleted!");
            }
            sql = "UPDATE entities SET filled = false, closed = false WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            statement.executeUpdate();
            Logger.getInstance().updateInfoLog("Day " + object.getCurrentDay() + " of entitiy " + object.getDescription() + " was successfully restarted!");
            reloadDB();
        }

        public void fillDay(Entity object) throws SQLException {
            String sql = "UPDATE entities SET filled = true WHERE id=?";
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                Logger.getInstance().updateInfoLog("Day " + object.getCurrentDay() + " of entitiy " + object.getDescription() + " was successfully filled!");
            }
            reloadDB();
        }

        private void moveToNext(Entity object) throws SQLException {
            String sql = "UPDATE entities SET filled = false, closed = false, current = ? WHERE id=?";
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(object.getCurrentDay().plusDays(1)));
            statement.setInt(2, object.getId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                Logger.getInstance().updateInfoLog("Day " + object.getCurrentDay() + " of entitiy " + object.getDescription() + " was successfully closed!");
            }
            reloadDB();
        }

        public void closeDay(Entity object) throws SQLException {
            moveToNext(object);
        }
    }
}
