package cu.lacumbre.auditor.view.custom;


import cu.lacumbre.auditor.crud.ItemsCRUD;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import cu.lacumbre.auditor.model.RawMaterial;
import java.util.TreeMap;
import java.util.function.ToDoubleFunction;

public class CustomTM_PAdjustNoSoldProducts implements TableModel {

    private final EventListenerList listenerList;
    private final TreeMap<RawMaterial, Double> datas;
    private final double totalValue;

    public CustomTM_PAdjustNoSoldProducts(TreeMap<RawMaterial, Double> datas, ItemsCRUD itemsCRUD) {
        if (datas != null) {
            this.datas = datas;
        } else {
            throw new IllegalArgumentException("El parámetro data no puede ser null.");
        }
        totalValue = datas.values().stream().mapToDouble((Double value) -> value).sum();
        listenerList = new EventListenerList();
    }

    @Override
    public int getRowCount() {
        return datas.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                "Producto";
            case 1 ->
                "Cantidad [Unidad]";
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
            RawMaterial rawMaterial = (RawMaterial) datas.keySet().toArray()[rowIndex];
            double ammount = (Double)  datas.values().toArray()[rowIndex];
            object = switch (columnIndex) {
                case 0 ->
                    rawMaterial;
                case 1 ->
                    ammount;
                default ->
                    null;
            };
        } else {
            object = switch (columnIndex) {
                case 0 ->
                    "Total ($):";
                case 1 ->
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

    public TreeMap<RawMaterial, Double> getDatas() {
        return datas;
    }

}
