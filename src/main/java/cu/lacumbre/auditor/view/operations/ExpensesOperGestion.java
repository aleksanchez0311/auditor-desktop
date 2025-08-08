package cu.lacumbre.auditor.view.operations;

import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.view.custom.CustomListModel;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class ExpensesOperGestion extends OperationsGestion {

    public ExpensesOperGestion(JFrame parent, boolean lockParent, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, boolean isIncomeForm) {
        super(parent, lockParent, operationsCRUD, itemsCRUD, isIncomeForm, operationsCRUD.getExpenseOperationsList(), Setup.CATEGORIA_GASTO);
        super.fillCenterPanel();
    }

    @Override
    protected void refreshFrameView() throws SQLException {
        operationsCRUD.reloadDB();
        operations = operationsCRUD.getExpenseOperationsList();
        super.refreshFrameView();
        super.fillCenterPanel();
    }

    @Override
    protected void setListObjectsModel() {
        listObjects.setModel(new CustomListModel(operations));
        super.setListObjectsModel();
    }

    @Override
    protected void setListObjectsModel(ArrayList<Operation> tempList) {
        listObjects.setModel(new CustomListModel(tempList));
    }

    @Override
    protected void save() {
        try {
            Item selectedItem = checker.getSelectedItem();
            Instant atStartOfDay = new Timing(dcDateValue.getDate()).atStartOfDay();
            double ammount = (double) spAmmountValue.getValue();
            double value = (double) spCostValue.getValue();
            boolean isIncome = true;
            boolean isBilled = cbBilledValue.isSelected();
            boolean isOpening = false;
            Operation newOperation = new Operation(selectedItem, atStartOfDay, ammount, value, isIncome, isBilled, isOpening);
            operationsCRUD.save(newOperation);
            refreshFrameView();
            int option = JOptionPane.showConfirmDialog(this, "Operación registrada correctamente.\n ¿Desea agregar más?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                prepareViewToInsertion();
            }
        } catch (IndexOutOfBoundsException | SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    @Override
    protected void update() {
        try {
            Instant atStartOfDay = new Timing(dcDateValue.getDate()).atStartOfDay();
            Operation operation = (Operation) listObjects.getSelectedValue();
            operation.setAmmount((double) spAmmountValue.getValue());
            operation.setValue((double) spCostValue.getValue());
            operation.setInstant(atStartOfDay);
            operation.setBilled(cbBilledValue.isSelected());
            operationsCRUD.update(operation);
            refreshFrameView();
            JOptionPane.showMessageDialog(this, "Operación modificada correctamente");
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

}
