package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.crud.WorkersCRUD;
import cu.lacumbre.auditor.model.Role;
import cu.lacumbre.auditor.model.Worker;
import cu.lacumbre.utils.Logger;
import java.sql.SQLException;

import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class CustomTM_Staff extends AbstractTableModel implements TableModelListener {

    private final ArrayList<Worker> workers;
    private final WorkersCRUD workersCRUD;
    private CustomTableModelListener cellListener;

    public void setCellListener(CustomTableModelListener cellListener) {
        this.cellListener = cellListener;
    }

    public CustomTM_Staff(WorkersCRUD workersCRUD) {
        this.workersCRUD = workersCRUD;
        this.workers = new ArrayList<>(workersCRUD.getMap().values());
    }

    @Override
    public int getRowCount() {
        return workers.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                "CODIGO";
            case 1 ->
                "NOMBRE";
            case 2 ->
                "OCUPACION";
            case 3 ->
                "ACTIVO";
            default ->
                null;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Worker worker = workers.get(rowIndex);
        Object value = switch (columnIndex) {
            case 0 ->
                worker.getCode();
            case 1 ->
                worker.toString();
            case 2 ->
                worker.getRole();
            case 3 ->
                worker.isActivated();
            default ->
                null;
        };
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                try {
                    workers.get(rowIndex).setCode((String) aValue);
                    workersCRUD.update(workers.get(rowIndex));
                    fireTableDataChanged();
                    break;
                } catch (SQLException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }
            case 1 -> {
                try {
                    workers.get(rowIndex).setFullName((String) aValue);
                    workersCRUD.update(workers.get(rowIndex));
                    fireTableDataChanged();
                    break;
                } catch (SQLException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }
            case 2 -> {
                try {
                    Role role = (Role) aValue;
                    workers.get(rowIndex).setRole(role);
                    workersCRUD.update(workers.get(rowIndex));
                    fireTableDataChanged();
                    break;
                } catch (SQLException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }
            case 3 -> {
                try {
                    boolean activated = (boolean) aValue;
                    workers.get(rowIndex).setActivated(activated);
                    workersCRUD.update(workers.get(rowIndex));
                    fireTableDataChanged();
                    break;
                } catch (SQLException ex) {
                    Logger.getInstance().updateErrorLog(ex);
                }
            }

            default -> {
                break;
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            default:
                return true;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                String.class;
            case 1 ->
                String.class;
            case 2 ->
                String.class;
            case 3 ->
                Boolean.class;
            default ->
                null;
        };
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    @Override
    public void tableChanged(TableModelEvent evt) {
        int row = evt.getFirstRow();
        int column = evt.getColumn();
        Object newValue = getValueAt(row, column);
        setValueAt(newValue, row, column);
        try {
            cellListener.onTableCellChange(newValue);
        } catch (Exception ex) {
            Logger.getInstance().updateErrorLog( ex);
        }
    }
}
