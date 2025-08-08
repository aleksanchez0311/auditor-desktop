package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.RawMaterial;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class CustomTM_RMReport extends AbstractTableModel {

    private final ArrayList<RawMaterial> rawMaterials;

    public CustomTM_RMReport(ArrayList<RawMaterial> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    @Override
    public int getRowCount() {
        return rawMaterials.size();
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
                "Costo Fichado($)";
            case 3 ->
                "Costo Ponderado($)";
            case 4 ->
                "Último Costo($)";
            case 5 ->
                "Mayor Costo($)";
            default ->
                null;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RawMaterial rawMaterial = rawMaterials.get(rowIndex);
        Object value = switch (columnIndex) {
            case 0 ->
                rawMaterial.getId();
            case 1 ->
                rawMaterial.toString() + " [" + rawMaterial.getMeasureUnit().getAbrev() + "]";
            case 2 ->
                rawMaterial.getBasicCost();
            case 3 ->
                rawMaterial.getWeightedCost();
            case 4 ->
                rawMaterial.getLastCost();
            case 5 ->
                rawMaterial.getHighestCost();
            default ->
                null;
        };
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

    public ArrayList<RawMaterial> getRawMaterials() {
        return rawMaterials;
    }

}
