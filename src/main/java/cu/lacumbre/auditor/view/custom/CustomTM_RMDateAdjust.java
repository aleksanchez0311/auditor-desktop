package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.RawMaterial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public final class CustomTM_RMDateAdjust extends CustomAbstractTableModelTotalizable {

    private static final int KEY_COLUMN = 1;
    private static final int VALUE_COLUMN_1 = 3;
    private static final int[] VALUE_COLUMNS = new int[]{VALUE_COLUMN_1};
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList(new String[]{"ID", "Producto U/M", "Cantidad", "Valor de la compra($)", "Último Costo($)", "Costo Estándar($)", "Costo Ponderado($)", "Costo Mayor($)", "Facturado"}));

    public CustomTM_RMDateAdjust(TreeMap<RawMaterial, Double> movements) {
        super(movements.size(), COLUMN_NAMES, KEY_COLUMN, VALUE_COLUMNS);
        for (Map.Entry<RawMaterial, Double> entry : movements.entrySet()) {
            RawMaterial rawMaterial = (RawMaterial) entry.getKey();
            double ammount = entry.getValue();
            double value = rawMaterial.getLastCost() * ammount;
            double lastCost = ammount > 0 ? value / ammount : 0.0d;
            ArrayList<Object> sublist = new ArrayList<>();
            sublist.add(rawMaterial);
            sublist.add(rawMaterial.toString() + " [" + rawMaterial.getMeasureUnit().getAbrev() + "]");
            sublist.add(ammount);
            sublist.add(value);
            sublist.add(lastCost);
            sublist.add(rawMaterial.getBasicCost());
            sublist.add(rawMaterial.getWeightedCost());
            sublist.add(rawMaterial.getHighestCost());
            sublist.add(false);
            this.dataArray.add(sublist);
        }
        setTotals();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < rowCount) {
            return switch (columnIndex) {
                case 0 ->
                    ((Item) dataArray.get(rowIndex).get(columnIndex)).getId();
                case 1, 8 ->
                    dataArray.get(rowIndex).get(columnIndex);
                case 2, 3, 4, 5, 6, 7 ->
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
            case 0 ->
                Integer.class;
            case 8 ->
                Boolean.class;
            default ->
                String.class;
        };
    }

}
