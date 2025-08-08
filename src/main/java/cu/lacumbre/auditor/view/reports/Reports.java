package cu.lacumbre.auditor.view.reports;

import cu.lacumbre.auditor.EntitySelector;
import static cu.lacumbre.auditor.Setup.ID_START_FOR_MATERIAS_PRIMAS_COCINA;
import static cu.lacumbre.auditor.Setup.ID_START_FOR_MATERIAS_PRIMAS_LISTAS;
import static cu.lacumbre.auditor.Setup.ID_START_FOR_RECETAS;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.utils.Timing;
import cu.lacumbre.utils.Logger;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import cu.lacumbre.auditor.view.utils.PeriodsNavigableView;
import java.sql.Connection;

public abstract class Reports extends JDialog implements PeriodsNavigableView {

    protected final DecimalFormat df = new DecimalFormat("#.##");
    protected Timing timing;
    protected final R_Handler reportsHandler;
    protected final OperationsCRUD operationsCRUD;
    protected HashMap<String, Object> parameters = new HashMap<>();
    private final String reportName;

    public Reports(JFrame owner, boolean modal, Connection connection, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD, String reportName) {
        super(owner, modal);
        this.operationsCRUD = operationsCRUD;
        this.reportName = reportName;
        reportsHandler = new R_Handler(connection, operationsCRUD, itemsCRUD);
        timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
    }
    
    @Override
    public void export() {
        addCommonParameters();
        initReport(reportName).setVisible(true);
        parameters = new HashMap<>();
    }

    protected JasperViewer initReport(String reportName) {
        try {
            return reportsHandler.generateReport(reportName, parameters);
        } catch (SQLException | JRException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
        return null;
    }

    protected void addCommonParameters() {
        parameters.put("PERIOD", timing.getDate());
        parameters.put("FDCP", timing.atStartOfMonth().getEpochSecond());
        parameters.put("FDNP", timing.atStartOfNextMonth().getEpochSecond());
        parameters.put("NAME", EntitySelector.currentEntity.getDescription());
        parameters.put("ENTITY", EntitySelector.currentEntity.getId());
        parameters.put("COPY", "");
        parameters.put("SFWRM", ID_START_FOR_MATERIAS_PRIMAS_COCINA);
        parameters.put("SFRFSRM", ID_START_FOR_MATERIAS_PRIMAS_LISTAS);
        parameters.put("SFW", ID_START_FOR_RECETAS);
    }

}
