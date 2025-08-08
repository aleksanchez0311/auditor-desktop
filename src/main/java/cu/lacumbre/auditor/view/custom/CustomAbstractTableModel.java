package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.IDSuperClass;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.table.AbstractTableModel;

public abstract class CustomAbstractTableModel extends AbstractTableModel {

    protected final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    protected final ArrayList<ArrayList<Object>> dataArray;
    protected final int rowCount;
    protected final int columnCount;
    protected TreeMap<Integer, String> columnNames;

    protected CustomAbstractTableModel(int rowCount) {
        this(rowCount, new ArrayList<>());
    }

    protected CustomAbstractTableModel(int rowCount, ArrayList<String> columnNames) {
        this(rowCount, columnNames.size(), columnNames);
    }

    protected CustomAbstractTableModel(int rowCount, int columnCount, ArrayList<String> columnNames) {
        this.dataArray = new ArrayList<>();
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.columnNames = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        for (int i = 0; i < columnNames.size(); i++) {
            this.columnNames.put(i, columnNames.get(i));
        }
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames.get(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public TreeMap<Integer, HashMap<String, Object>> getDataMap() {
        TreeMap<Integer, HashMap<String, Object>> dataMap = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        for (int i = 0; i < rowCount; i++) {
            ArrayList<Object> arrayItem = dataArray.get(i);
            int id = ((IDSuperClass) arrayItem.get(0)).getId();
            HashMap<String, Object> map = new HashMap<>();
            for (int j = 0; j < columnCount; j++) {
                String columnName = columnNames.get(j);
                Object arraySubItem = arrayItem.get(j);
                map.put(columnName, arraySubItem);
            }
            dataMap.put(id, map);
        }
        return dataMap;
    }

}
