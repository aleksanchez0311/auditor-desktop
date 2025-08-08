package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.model.CostSheet;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.MeasureUnit;
import cu.lacumbre.utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class CostSheetsCRUD implements ModelCRUDSingle {

    private final TreeMap<Integer, CostSheet> map;
    private final Entity entity;
    private final Connection connection;

    public CostSheetsCRUD(Connection connection) throws SQLException {
        this.entity = EntitySelector.currentEntity;
        this.connection = connection;
        this.map = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Cost Sheets was successfully got!");
        }
    }

    public void reloadDB() throws SQLException {
        loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Cost Sheets was successfully refreshed!");
        }
    }

    private void loadDB() throws SQLException {
        map.clear();
        map.put(0, CostSheet.generate());
        String sql = "SELECT * FROM cost_sheets where entity= " + entity.getId();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt(1);
            String description = result.getString(2);
            double inputCosts = result.getDouble(3);
            double financialCosts = result.getDouble(4);
            double energyCosts = result.getDouble(5);
            double rentalCosts = result.getDouble(6);
            double laborCosts = result.getDouble(7);
            double profitMargin = result.getDouble(8);
            CostSheet object = new CostSheet(id, description, inputCosts, financialCosts, energyCosts, rentalCosts, laborCosts, profitMargin);
            map.put(id, object);
        }
    }

    public TreeMap<Integer, CostSheet> getMap() {
        return map;
    }
    

    public ArrayList<CostSheet> getList() {
        return new ArrayList<>(map.values());
    }

    @Override
    public CostSheet get(int id) {
        return map.get(id);
    }

    @Override
    public void save(Object obj) throws SQLException {
        CostSheet object = (CostSheet) obj;
        String sql = "INSERT INTO cost_sheets(description, input_costs, financial_cost, energy_cost, rental_costs, labor_costs, profit_margin, entity) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setDouble(2, object.getInputCosts());
        statement.setDouble(3, object.getFinancialCosts());
        statement.setDouble(4, object.getEnergyCosts());
        statement.setDouble(5, object.getRentalCosts());
        statement.setDouble(6, object.getLaborCosts());
        statement.setDouble(7, object.getProfitMargin());
        statement.setInt(8, entity.getId());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A new " + object.getClass().getSimpleName() + " was successfully saved!");
        }
    }

    @Override
    public void update(Object obj) throws SQLException {
        CostSheet object = (CostSheet) obj;
        String sql = "UPDATE cost_sheets SET description=?, input_costs=?, financial_cost=?, energy_cost=?, rental_costs=?, labor_costs=?, profit_margin=? WHERE id=?;";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setDouble(2, object.getInputCosts());
        statement.setDouble(3, object.getFinancialCosts());
        statement.setDouble(4, object.getEnergyCosts());
        statement.setDouble(5, object.getRentalCosts());
        statement.setDouble(6, object.getLaborCosts());
        statement.setDouble(7, object.getProfitMargin());
        statement.setInt(8, object.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getInstance().updateInfoLog("An existing " + object.getClass().getSimpleName() + " was successfully updated!");
        }
    }

    @Override
    public void delete(Object obj) throws SQLException {
        MeasureUnit object = (MeasureUnit) obj;
        String sql = "DELETE FROM cost_sheets WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, object.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("A " + object.getClass().getSimpleName() + " was successfully deleted!");
        }
    }

}
