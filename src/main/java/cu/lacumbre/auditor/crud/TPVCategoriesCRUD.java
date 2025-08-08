package cu.lacumbre.auditor.crud;

import com.lowagie.text.pdf.RGBColor;
import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.model.TPVCategory;
import cu.lacumbre.utils.Logger;
import java.awt.Color;
import java.sql.*;
import java.util.*;

public class TPVCategoriesCRUD implements ModelCRUDSingle {

    private final TreeMap<Integer, TPVCategory> map;
    private final Connection connection;
    private final int entity = EntitySelector.currentEntity.getId();

    public TPVCategoriesCRUD(Connection connection) throws SQLException {
        this.connection = connection;
        this.map = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        String sql = "SELECT * FROM tpv_categories where entity = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, entity);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt(1);
            String descripcion = result.getString(2);
            String stringColor = result.getString(4);
            TPVCategory tpvCategory = new TPVCategory(id, descripcion, parseColor(stringColor));
            map.put(id, tpvCategory);
        }
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of TPV Categories was successfully got!");
        }
    }

    public ArrayList<TPVCategory> getList() {
        return new ArrayList<>(map.values());
    }

    public TreeMap<Integer, TPVCategory> getMap() {
        return map;
    }

    @Override
    public TPVCategory get(int id) {
        return map.get(id);
    }

    @Override
    public void save(Object obj) throws SQLException {
        TPVCategory object = (TPVCategory) obj;
        String sql = "INSERT INTO tpv_categories (description, color, entity) VALUES (?, ?, ?)";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setString(2, parseColor(object.getColor()));
        statement.setInt(3, entity);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A new " + object.getClass().getSimpleName() + " was successfully saved!");
        }
    }

    @Override
    public void update(Object obj) throws SQLException {
        TPVCategory object = (TPVCategory) obj;
        String sql = "UPDATE tpv_categories SET description=? , color=? WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, object.getDescription());
        statement.setString(2, parseColor(object.getColor()));
        statement.setInt(3, object.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getInstance().updateInfoLog("An existing " + object.getClass().getSimpleName() + " was successfully updated!");
        }
    }

    @Override
    public void delete(Object obj) throws SQLException {
        TPVCategory object = (TPVCategory) obj;
        String sql = "DELETE FROM tpv_categories WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setInt(1, object.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            Logger.getInstance().updateInfoLog("A " + object.getClass().getSimpleName() + " was successfully deleted!");
        }
    }

    public final RGBColor parseColor(String stringColor) {
        String[] stringValues = stringColor.split(",");
        int[] integerValues = new int[stringValues.length];
        for (int i = 0; i < stringValues.length; i++) {
            integerValues[i] = Integer.parseInt(stringValues[i]);
        }
        return new RGBColor(integerValues[0], integerValues[1], integerValues[2], integerValues[3]);
    }

    public final String parseColor(RGBColor colorColor) {
        return colorColor.getRed() + "," + colorColor.getGreen() + "," + colorColor.getBlue() + "," + colorColor.getAlpha();
    }

}
