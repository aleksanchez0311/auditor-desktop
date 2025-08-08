package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.model.CustomDocument;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.PaymentRecord;
import cu.lacumbre.auditor.model.Superclass;
import cu.lacumbre.auditor.model.Worker;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import org.postgresql.util.PGobject;

public class PaymentRecordsCRUD implements ModelCRUDBatch {

    private final TreeMap<Integer, PaymentRecord> map;
    private final WorkersCRUD workersCRUD;
    private final CustomDocument document;
    private final Connection connection;

    public PaymentRecordsCRUD(Connection connection, WorkersCRUD workersCRUD, CustomDocument document) throws SQLException {
        this.connection = connection;
        this.workersCRUD = workersCRUD;
        this.document = document;
        this.map = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        loadDB();
        if (map != null) {
            if (!map.isEmpty()) {
                Logger.getInstance().updateInfoLog("Map of Payment Record was successfully got!");
            }
        }
    }

    public void reloadDB() throws SQLException {
        this.workersCRUD.reloadDB();
        loadDB();
        if (map != null) {
            if (!map.isEmpty()) {
                Logger.getInstance().updateInfoLog("Map of PaymentRecords was successfully refreshed!");
            }
        }
    }

    private void loadDB() throws SQLException {
        map.clear();
        String sql = "SELECT * FROM payment_records WHERE document=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, document.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Worker worker = workersCRUD.get(result.getInt(1));
            double workedDays = result.getDouble(2);
            int id = result.getInt(6);
            PGobject pGobject = (PGobject) result.getObject(7);
            double workedDaysAdjusted = result.getDouble(8);
            PaymentRecord paymentRecord = new PaymentRecord(id, worker, workedDays, workedDaysAdjusted, pGobject, document);
            map.put(worker.getId(), paymentRecord);
        }
    }

    public ArrayList<PaymentRecord> getList() {
        return new ArrayList<>(map.values());
    }

    public TreeMap<Integer, PaymentRecord> getMap() {
        return map;
    }

    @Override
    public PaymentRecord get(int id) {
        return map.get(id);
    }

    @Override
    public void save(ArrayList<? extends Superclass> superclases) throws SQLException {
        ArrayList<PaymentRecord> paymentRecords = (ArrayList<PaymentRecord>) superclases;
        String sql = "INSERT INTO payment_records (worker, worked_days, worked_hours, payed, document, map) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (PaymentRecord paymentRecord : paymentRecords) {
            statement.setInt(1, paymentRecord.getWorker().getId());
            statement.setDouble(2, paymentRecord.getWorkedDays());
            statement.setDouble(3, paymentRecord.getWorkedHours());
            statement.setDouble(4, paymentRecord.getToPay());
            statement.setInt(5, paymentRecord.getDocument().getId());
            statement.setObject(6, paymentRecord.getJSONB());
            statement.addBatch();
        }
        int[] rowsInserted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsInserted.length > 0) {
            Logger.getInstance().updateInfoLog("Some Payment Records ware successfully saved!");
        }
        reloadDB();
    }

    @Override
    public void update(ArrayList<? extends Superclass> superclases) throws SQLException {
        ArrayList<PaymentRecord> paymentRecords = (ArrayList<PaymentRecord>) superclases;
        String sql = "UPDATE payment_records SET worked_days=?, worked_hours=?, payed=?, worked_days_adjusted=?, map = ? WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (PaymentRecord paymentRecord : paymentRecords) {
            statement.setDouble(1, paymentRecord.getWorkedDays());
            statement.setDouble(2, paymentRecord.getWorkedHours());
            statement.setDouble(3, paymentRecord.getToPay());
            statement.setDouble(4, paymentRecord.getWorkedDaysAdjusted());
            statement.setObject(5, paymentRecord.getJSONB());
            statement.setInt(6, paymentRecord.getId());
            statement.addBatch();
        }
        int[] rowsUpdated = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsUpdated.length > 0) {
            Logger.getInstance().updateInfoLog("Some PaymentRecods ware successfully updated!");
        }
        reloadDB();
    }

    @Override
    public void delete(ArrayList<? extends Superclass> superclases) throws SQLException {
        ArrayList<PaymentRecord> paymentRecords = (ArrayList<PaymentRecord>) superclases;
        String sql = "DELETE FROM payment_records WHERE worker=? and document=?;";
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(sql);
        for (PaymentRecord paymentRecord : paymentRecords) {
            statement.setInt(1, paymentRecord.getWorker().getId());
            statement.setInt(2, paymentRecord.getDocument().getId());
            statement.addBatch();
        }
        int[] rowsDeleted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsDeleted.length > 0) {
            Logger.getInstance().updateInfoLog("Som PaymentRecords ware successfully deleted!");
        }
        reloadDB();
    }

}
