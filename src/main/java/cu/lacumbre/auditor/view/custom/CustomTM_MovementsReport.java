package cu.lacumbre.auditor.view.custom;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.RawMaterial;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

public class CustomTM_MovementsReport extends CustomAbstractTableModel {

    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList(new String[]{"No.", "ID", "Producto [U/M]", "Al Inicio", "Entrada", "Salida", "Final"}));

    public CustomTM_MovementsReport(TreeMap<RawMaterial, TreeMap<String, Double>> movements) throws SQLException {
        super(movements.size(), COLUMN_NAMES);
        int no = 1;
        for (Map.Entry<RawMaterial, TreeMap<String, Double>> entry : movements.entrySet()) {
            RawMaterial rawMaterial = entry.getKey();
            TreeMap<String, Double> ammounts = entry.getValue();
            ArrayList<Object> sublist = new ArrayList<>();
            sublist.add(no);
            sublist.add(rawMaterial);
            sublist.add(rawMaterial + " [" + rawMaterial.getMeasureUnit().getAbrev() + "]");
            sublist.add(ammounts.get("begin"));
            sublist.add(ammounts.get("income"));
            sublist.add(ammounts.get("outcome"));
            sublist.add(ammounts.get("end"));
            this.dataArray.add(sublist);
            no++;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0, 2 ->
                dataArray.get(rowIndex).get(columnIndex);
            case 1 ->
                ((RawMaterial) dataArray.get(rowIndex).get(columnIndex)).getId();
            case 3, 4, 5, 6 ->
                decimalFormat.format(dataArray.get(rowIndex).get(columnIndex));
            default ->
                null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1 ->
                Integer.class;
            default ->
                String.class;
        };
    }

}
