package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.RawMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import java.util.TreeMap;
import javax.swing.table.AbstractTableModel;

public class CustomTM_RMCosts extends AbstractTableModel {

    private final Object[][] dataArray;
    private final TreeMap<RawMaterial, ArrayList<Double>> dataMap;
    private double totalCost;

    public CustomTM_RMCosts(TreeMap<RawMaterial, Double> existences) {
        if (existences != null) {
            this.dataArray = new Object[existences.size()][4];
            this.dataMap = new TreeMap<>(Comparator.comparingInt(RawMaterial::getCode));
            int index = 0;
            for (Map.Entry<RawMaterial, Double> entry : existences.entrySet()) {
                RawMaterial item = (RawMaterial) entry.getKey();
                double existentAmmount = entry.getValue();
                double finalValue = item.getLastCost() * existentAmmount;
                double finalCost = existentAmmount > 0 ? finalValue / existentAmmount : 0.0d;
                this.dataArray[index][0] = item;
                this.dataArray[index][1] = existentAmmount;
                this.dataArray[index][2] = finalValue;
                this.dataArray[index][3] = finalCost;
                index++;
                ArrayList<Double> doubles = new ArrayList<>(Arrays.asList(new Double[]{existentAmmount, finalValue, finalCost}));
                dataMap.put(item, doubles);
            }
            totalCost = setTotalRow();
        } else {
            throw new IllegalArgumentException("El parámetro data no puede ser null.");
        }
    }

    private double setTotalRow() {
        ArrayList<RawMaterial> rawMaterials = new ArrayList<>(dataMap.keySet());
        ArrayList<ArrayList<Double>> ammounts = new ArrayList<>(dataMap.values());
        double total = 0.0d;
        int size = ammounts.size() == rawMaterials.size() ? rawMaterials.size() : 0;
        for (int i = 0; i < size; i++) {
            ArrayList<Double> doubles = ammounts.get(i);
            total += doubles.get(2);
        }
        return total;
    }

    @Override
    public int getRowCount() {
        return dataArray.length + 1;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                "ID";
            case 1 ->
                "Producto U/M";
            case 2 ->
                "Cantidad Existente";
            case 3 ->
                "Valor Actual($)";
            case 4 ->
                "Costo Actual($)";
            case 5 ->
                "Costo ($)";
            default ->
                null;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        if (rowIndex < dataArray.length) {
            RawMaterial item = (RawMaterial) dataArray[rowIndex][0];
            double existentAmmount = (double) dataArray[rowIndex][1];
            double finalValue = (double) dataArray[rowIndex][2];
            double finalCost = (double) dataArray[rowIndex][3];
            value = switch (columnIndex) {
                case 0 ->
                    item.getId();
                case 1 ->
                    item.toString() + " [" + item.getMeasureUnit().getAbrev() + "]";
                case 2 ->
                    existentAmmount;
                case 3 ->
                    finalValue;
                case 4 ->
                    finalCost;
                case 5 ->
                    item.getDefaultCost();
                default ->
                    null;
            };
        } else {
            value = switch (columnIndex) {
                case 1 ->
                    "Total ($):";
                case 3 ->
                    totalCost;
                default ->
                    null;
            };
        }
        return value;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                Integer.class;
            case 1 ->
                String.class;
            case 2 ->
                Double.class;
            case 3 ->
                Double.class;
            case 4 ->
                Double.class;
            case 5 ->
                Double.class;
            default ->
                null;
        };
    }

    public Object[][] getDataArray() {
        return dataArray;
    }

}
