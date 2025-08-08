package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.RawMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.table.AbstractTableModel;

public class CustomTM_RMTransfer extends AbstractTableModel {

    private final Object[][] dataArray;
    private final TreeMap<RawMaterial, ArrayList<Double>> dataMap;

    public CustomTM_RMTransfer(TreeMap<RawMaterial, Double> existencesMap) {
        if (existencesMap != null) {
            this.dataArray = new Object[existencesMap.size()][3];
            this.dataMap = new TreeMap<>(Comparator.comparingInt(RawMaterial::getCode));
            int index = 0;
            for (Map.Entry<RawMaterial, Double> entry : existencesMap.entrySet()) {
                RawMaterial item = (RawMaterial) entry.getKey();
                double existentAmmount = entry.getValue();
                double transferAmmount = existentAmmount;
                this.dataArray[index][0] = item;
                this.dataArray[index][1] = existentAmmount;
                this.dataArray[index][2] = transferAmmount;
                index++;
                ArrayList<Double> doubles = new ArrayList<>(Arrays.asList(new Double[]{existentAmmount, transferAmmount}));
                dataMap.put(item, doubles);
            }
        } else {
            throw new IllegalArgumentException("El parámetro data no puede ser null.");
        }
    }

    @Override
    public int getRowCount() {
        return dataArray.length;
    }

    @Override
    public int getColumnCount() {
        return 5;
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
                "Costo ($)";
            case 4 ->
                "Cantidad a Transferir";
            default ->
                null;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        RawMaterial item = (RawMaterial) dataArray[rowIndex][0];
        double existentAmmount = (double) dataArray[rowIndex][1];
        double transferAmmount = (double) dataArray[rowIndex][2];
        value = switch (columnIndex) {
            case 0 ->
                item.getId();
            case 1 ->
                item.toString() + " [" + item.getMeasureUnit().getAbrev() + "]";
            case 2 ->
                existentAmmount;
            case 3 ->
                item.getHighestCost();
            case 4 ->
                transferAmmount;
            default ->
                null;
        };
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 4 -> {
                double existentAmmount = (double) dataArray[rowIndex][1];
                double ammountToTransfer = (double) aValue;
                if (ammountToTransfer <= existentAmmount) {
                    dataArray[rowIndex][2] = ammountToTransfer;
                }
                break;
            }
            default -> {
                break;
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 4 ->
                true;
            default ->
                false;
        };
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
            default ->
                null;
        };
    }

    public Object[][] getDataArray() {
        return dataArray;
    }

}
