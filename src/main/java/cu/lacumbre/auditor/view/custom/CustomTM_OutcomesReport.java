package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.RawMaterial;
import java.util.ArrayList;
import java.util.Map;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.TreeMap;

public class CustomTM_OutcomesReport extends CustomAbstractTableModelTotalizable{

    private static final int KEY_COLUMN = 2;
    private static final int VALUE_COLUMN_1 = 4;
    private static final int[] VALUE_COLUMNS = new int[]{VALUE_COLUMN_1};
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList(new String[]{"No.", "ID", "Producto [U/M]", "Cantidad Retirada", "Costo ($)"}));

    public CustomTM_OutcomesReport(TreeMap<RawMaterial, Double[]> outcomes) throws SQLException {
        super(outcomes.size(), COLUMN_NAMES, KEY_COLUMN, VALUE_COLUMNS);
        int no = 1;
        for (Map.Entry<RawMaterial, Double[]> entry : outcomes.entrySet()) {
            RawMaterial rawMaterial = entry.getKey();
            Double[] values = entry.getValue();
            ArrayList<Object> sublist = new ArrayList<>();
            sublist.add(no);
            sublist.add(rawMaterial);
            sublist.add(rawMaterial + " [" + rawMaterial.getMeasureUnit().getAbrev() + "]");
            sublist.add(values[0]);
            sublist.add(values[1]);
            this.dataArray.add(sublist);
            no++;
        }
        setTotals();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < rowCount) {
            return switch (columnIndex) {
                case 0, 2 ->
                    dataArray.get(rowIndex).get(columnIndex);
                case 1 ->
                    ((RawMaterial) dataArray.get(rowIndex).get(columnIndex)).getId();
                case 3, 4 ->
                    decimalFormat.format(dataArray.get(rowIndex).get(columnIndex));
                default ->
                    null;
            };
        } else {
            return switch (columnIndex) {
                case KEY_COLUMN ->
                    dataArray.get(rowCount).get(totalColumnIndexKey);
                case VALUE_COLUMN_1 ->
                    decimalFormat.format(dataArray.get(rowIndex).get(columnIndex));
                default ->
                    null;
            };
        }
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
