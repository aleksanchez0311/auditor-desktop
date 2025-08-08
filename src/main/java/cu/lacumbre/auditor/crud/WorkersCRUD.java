package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.utils.MonthlyPeriod;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Role;
import cu.lacumbre.auditor.model.Worker;
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

public class WorkersCRUD implements ModelCRUDSingle {

    private final RolesCRUD rolesCRUD;
    private final TreeMap<Integer, Worker> map;
    private final Entity entity;
    private final Connection connection;
    private final Comparator codeComparator = new createCodeComparator();

    public WorkersCRUD(Connection connection) throws SQLException {
        rolesCRUD = new RolesCRUD(connection);
        this.connection = connection;
        this.entity = EntitySelector.currentEntity;
        this.map = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Workers was successfully got!");
        }
    }

    public void reloadDB() throws SQLException {
        loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Workers was successfully refreshed!");
        }
    }

    private void loadDB() throws SQLException {
        map.clear();
        String sql = "SELECT * FROM workers WHERE entity = " + entity.getId() + " order by code";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt(1);
            String lastName = result.getString(2);
            String name = result.getString(3);
            Role role = rolesCRUD.get(result.getInt(4));
            String dni = result.getString(5);
            String address = result.getString(6);
            String phonePrefix = result.getString(7);
            String phone = result.getString(8);
            String email = result.getString(9);
            LocalDate date = result.getDate(11).toLocalDate();
            boolean activated = result.getBoolean(12);
            String code = result.getString(13);
            Worker worker = new Worker(id, name, lastName, role, dni, address, phonePrefix, phone, email, date, activated, code);
            map.put(id, worker);
        }
    }

    public RolesCRUD getRolesCRUD() {
        return rolesCRUD;
    }

    public ArrayList<Worker> getList() {
        return new ArrayList<>(map.values());
    }

    public TreeMap<Integer, Worker> getMap() {
        TreeMap<Integer, Worker> result = new TreeMap<>(codeComparator);
        result.putAll(map);
        return result;
    }

    @Override
    public Worker get(int id) {
        return map.get(id);
    }

    public ArrayList<Worker> getActiveList(MonthlyPeriod period) {
        ArrayList<Worker> result = new ArrayList<>();
        map.forEach((Integer t, Worker worker) -> {
//                if (!worker.getEnrollDate().isAfter(EntitySelector.currentEntity.getCurrentDay())){
            if (isEnrollmentInPeriod(worker, period)) {
                if (worker.isActivated()) {
                    result.add(worker);
                }
            }
        });
        return result;
    }

    @Override
    public void save(Object obj) throws SQLException {
        Worker object = (Worker) obj;
        String sql = "INSERT INTO workers (last_name, name, role, dni, address, phone_prefix, phone, email, entity, date, activated, code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getLastName());
        statement.setString(2, object.getName());
        statement.setInt(3, object.getRole().getId());
        statement.setString(4, object.getDni());
        statement.setString(5, object.getAddress());
        statement.setString(6, object.getPhonePrefix());
        statement.setString(7, object.getPhone());
        statement.setString(8, object.getEmail());
        statement.setInt(9, EntitySelector.currentEntity.getId());
        statement.setDate(10, Date.valueOf(object.getEnrollDate()));
        statement.setBoolean(11, object.isActivated());
        statement.setString(12, object.getCode());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A new " + object.getClass().getSimpleName() + " was successfully saved!");
        }
        reloadDB();
    }

    @Override
    public void update(Object obj) throws SQLException {
        Worker object = (Worker) obj;
        String sql = "UPDATE workers SET name=?, last_name=?, dni=?, phone=?, address=?, email=?, phone_prefix=?, role=?, activated=?, code=? WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getName());
        statement.setString(2, object.getLastName());
        statement.setString(3, object.getDni());
        statement.setString(4, object.getPhone());
        statement.setString(5, object.getAddress());
        statement.setString(6, object.getEmail());
        statement.setString(7, object.getPhonePrefix());
        statement.setInt(8, object.getRole().getId());
        statement.setBoolean(9, object.isActivated());
        statement.setString(10, object.getCode());
        statement.setInt(11, object.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getInstance().updateInfoLog("An existing " + object.getClass().getSimpleName() + " was successfully updated!");
        }
        reloadDB();
    }

    public void update(Object obj, int previousID) throws SQLException {
        Worker object = (Worker) obj;
        String sql = "UPDATE workers SET name=?, last_name=?, dni=?, phone=?, address=?, email=?, phone_prefix=?, ocupation=?, username=?, password=?, id=?, activated=?, code=? WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getName());
        statement.setString(2, object.getLastName());
        statement.setString(3, object.getDni());
        statement.setString(4, object.getPhone());
        statement.setString(5, object.getAddress());
        statement.setString(6, object.getEmail());
        statement.setString(7, object.getPhonePrefix());
        statement.setInt(8, object.getRole().getId());
        statement.setInt(9, object.getId());
        statement.setBoolean(10, object.isActivated());
        statement.setString(11, object.getCode());
        statement.setInt(12, previousID);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getInstance().updateInfoLog("An existing " + object.getClass().getSimpleName() + " was successfully updated!");
        }
        reloadDB();
    }

    @Override
    public void delete(Object obj) throws SQLException {
        Worker object = (Worker) obj;
        String sql = "DELETE FROM workers WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, object.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("A " + object.getClass().getSimpleName() + " was successfully deleted!");
        }
        reloadDB();
    }

    private boolean isEnrollmentInPeriod(Worker worker, MonthlyPeriod period) {
        return period.contains(worker.getEnrollDate());
    }

    private class createCodeComparator implements Comparator<Integer> {

        public createCodeComparator() {
        }

        @Override
        public int compare(Integer k1, Integer k2) {
//            int first = Integer.parseInt(map.get(k1).getCode());
//            int second = Integer.parseInt(map.get(k2).getCode());
//            int comp = 0;
//            if(first == second){
//                return k1.compareTo(k2);
//            }else if(first< second){
//                comp = -1;
//            }else{
//                comp = 1;
//            }
//            return comp;
            int comp = map.get(k1).getCode().compareTo(map.get(k2).getCode());
            if (comp == 0) {
                return k1.compareTo(k2); // Mantener orden de claves si los valores son iguales
            }
            return comp;
        }
    }
}
