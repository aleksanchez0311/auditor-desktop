package cu.lacumbre.auditor.view.custom;


import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import cu.lacumbre.auditor.model.Product;
import java.util.TreeMap;

public class CustomTM_PDateSaleSummary implements TableModel {

    private final EventListenerList listenerList;
    private final TreeMap<Product, Double[]> datas;
    private final double totalValue;

    public CustomTM_PDateSaleSummary(TreeMap<Product, Double[]> datas) {
        if (datas != null) {
            this.datas = datas;
        } else {
            throw new IllegalArgumentException("El parámetro data no puede ser null.");
        }
        totalValue = datas.values().stream().mapToDouble((Double[] vals) -> vals[2]).sum();
        listenerList = new EventListenerList();
    }

    @Override
    public int getRowCount() {
        return datas.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                "Producto";
            case 1 ->
                "Cantidad [Unidad]";
            case 2 ->
                "Precio ($)";
            case 3 ->
                "Total ($)";
            default ->
                null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                String.class;
            case 1 ->
                Double.class;
            case 2 ->
                Double.class;
            case 3 ->
                Double.class;
            default ->
                null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object object = null;
        if (rowIndex < datas.size()) {
            Object[] keysArray = datas.keySet().toArray();
            Object[] valuesArray = datas.values().toArray();
            Product product = (Product) keysArray[rowIndex];
            Double[] values = (Double[]) valuesArray[rowIndex];
            double ammount = values[0];
            double singleValue = values[1];
            double value = values[2];
            object = switch (columnIndex) {
                case 0 ->
                    product.toString();
                case 1 ->
                    ammount;
                case 2 ->
                    singleValue;
                case 3 ->
                    value;
                default ->
                    null;
            };
        } else {
            object = switch (columnIndex) {
                case 0 ->
                    "Total ($):";
                case 1 ->
                    null;
                case 2 ->
                    null;
                case 3 ->
                    totalValue;
                default ->
                    null;
            };
        }
        return object;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listenerList.add(TableModelListener.class, l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);
    }

    public TableModelListener[] getTableModelListeners() {
        return listenerList.getListeners(TableModelListener.class);
    }

    protected void notifyTableChanged(TableModelEvent e) {
        TableModelListener[] listeners = getTableModelListeners();
        for (int i = listeners.length - 1; i >= 0; i--) {
            listeners[i].tableChanged(e);
        }
    }

    /**
     * Notifica que el header de la tabla ha cambiado.
     */
    protected void notifyTableHeaderChanged() {
        TableModelEvent e = new TableModelEvent(this, TableModelEvent.HEADER_ROW);
        notifyTableChanged(e);
    }

    /**
     * Notifica que han sido insertadas nuevas filas.
     *
     * @param firstRow El índice de la primera fila insertada.
     * @param lastRow El índice de la última fila insertada.
     */
    protected void notifyTableRowsInserted(int firstRow, int lastRow) {
        TableModelEvent e = new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
        notifyTableChanged(e);
    }

    /**
     * Notifica que una o más filas en un rango han sido modificadas.
     *
     * @param firstRow El índice de la primera fila en el rango.
     * @param lastRow El índice de la última fila en el rango.
     */
    protected void notifyTableRowsUpdated(int firstRow, int lastRow) {
        TableModelEvent e = new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
        notifyTableChanged(e);
    }

    /**
     * Notifica que una o más filas en un rango han sido borradas.
     *
     * @param firstRow El índice de la primera fila en el rango.
     * @param lastRow El índice de la última fila en el rango.
     */
    protected void notifyTableRowsDeleted(int firstRow, int lastRow) {
        TableModelEvent e = new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        notifyTableChanged(e);
    }

    /**
     * Notifica que el valor de una celda ha cambiado.
     *
     * @param row El índice de la fila a la que pertenece la celda.
     * @param column El índice de la columna a la que pertenece la celda.
     */
    protected void notifyTableCellUpdated(int row, int column) {
        TableModelEvent e = new TableModelEvent(this, row, row, column);
    }

}
