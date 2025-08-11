package cu.lacumbre.auditor.view.custom;

import java.util.ArrayList;
import java.util.Map;

import cu.lacumbre.auditor.model.Product;
import java.util.Arrays;
import java.util.TreeMap;

public class CustomTM_SalesReport extends CustomAbstractTableModelTotalizable {

    private static final int KEY_COLUMN = 2;
    private static final int VALUE_COLUMN_1 = 4;
    private static final int VALUE_COLUMN_2 = 5;
    private static final int VALUE_COLUMN_3 = 6;
    private static final int[] VALUE_COLUMNS = new int[]{VALUE_COLUMN_1, VALUE_COLUMN_2, VALUE_COLUMN_3};
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList(new String[]{"No.", "ID", "Producto [U/M]", "Cantidad Vendida", "Valor de Venta ($)", "Costo de Venta($)", "Utilidad de Venta($)"}));
    private final double periodExpenses;

    public CustomTM_SalesReport(TreeMap<Product, Double> sale, double periodExpenses) {
        super(sale.size(), COLUMN_NAMES, KEY_COLUMN, VALUE_COLUMNS);
        this.periodExpenses = periodExpenses;
        int no = 1;
        for (Map.Entry<Product, Double> entry : sale.entrySet()) {
            Product product = entry.getKey();
            Double ammount = entry.getValue();
            ArrayList<Object> sublist = new ArrayList<>();
            sublist.add(no);
            sublist.add(product);
            sublist.add(product + " [" + product.getMeasureUnit().getAbrev() + "]");
            sublist.add(ammount);
            sublist.add(product.getPrice() * ammount);
            sublist.add(product.getRecipeCost() * ammount);
            sublist.add((product.getProfit() < product.getLowerProfit() ? product.getProfit() : product.getLowerProfit()) * ammount);
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
                    ((Product) dataArray.get(rowIndex).get(columnIndex)).getId();
                case 3, 4, 5, 6 ->
                    decimalFormat.format(dataArray.get(rowIndex).get(columnIndex));
                default ->
                    null;
            };
        } else {
            return switch (columnIndex) {
                case KEY_COLUMN ->
                    dataArray.get(rowCount).get(totalColumnIndexKey);
                case VALUE_COLUMN_1, VALUE_COLUMN_2, VALUE_COLUMN_3 ->
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

    @Override
    public void setTotals() {
        int length = columnNames.size() == columnCount ? columnCount : columnNames.size();
        totals = new double[length];
        for (int i = 0; i < totalColumnIndexValues.length; i++) {
            int totalColumnIndexValue = totalColumnIndexValues[i];
            if (totalColumnIndexValue == VALUE_COLUMN_3) {
                totals[VALUE_COLUMN_3] = totals[VALUE_COLUMN_1] - totals[VALUE_COLUMN_2] - periodExpenses;
            } else {
                double total = 0.0d;
                for (int row = 0; row < rowCount; row++) {
                    ArrayList<Object> values = dataArray.get(row);
                    total += (double) values.get(totalColumnIndexValue);
                }
                totals[totalColumnIndexValue] = total;
            }
        }
        ArrayList<Object> subList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            subList.add(null);
        }
        subList.set(totalColumnIndexKey, "Total ($) => ");
        for (int i = 0; i < totalColumnIndexValues.length; i++) {
            int totalColumnIndexValue = totalColumnIndexValues[i];
            subList.set(totalColumnIndexValue, totals[totalColumnIndexValue]);
        }
        this.dataArray.add(subList);
    }

}
