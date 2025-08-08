package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.Product;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class CustomTM_PReport extends AbstractTableModel {

    private final ArrayList<Product> products;

    public CustomTM_PReport(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return products.size();
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
                "Producto [Unidad]";
            case 2 ->
                "Precio de venta ($)";
            case 3 ->
                "Costo de Receta($)";
            case 4 ->
                "Último de Producción ($)";
            case 5 ->
                "Utilidad ($)";
            default ->
                null;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        Object value = switch (columnIndex) {
            case 0 ->
                product.getId();
            case 1 ->
                product.toString();
            case 2 ->
                product.getPrice();
            case 3 ->
                product.getRecipeCost();
            case 4 ->
                product.getProductionCost();
            case 5 ->
                product.getProfit();
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

    public ArrayList<Product> getProducts() {
        return products;
    }

}
