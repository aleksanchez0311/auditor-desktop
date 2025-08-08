package cu.lacumbre.auditor.view.reports;

import cu.lacumbre.auditor.EntitySelector;
import static cu.lacumbre.auditor.Setup.ID_START_FOR_MATERIAS_PRIMAS_LISTAS;
import static cu.lacumbre.auditor.Setup.ID_START_FOR_PRODUCTOS_COCINA;
import static cu.lacumbre.auditor.Setup.ID_START_FOR_RECETAS;
import cu.lacumbre.auditor.view.reports.custom.CustomJasperViewer;
import java.util.HashMap;

import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.utils.InputStreamToTempFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class R_Handler {

    public static final String PERIOD_MOVEMENTS = "period_movements.jasper";
    public static final String PERIOD_INCOMES = "period_incomes.jasper";
    public static final String PERIOD_OUTCOMES = "period_outcomes.jasper";
    public static final String PERIOD_SALE = "period_sale.jasper";
    public static final String RAW_MATERIALS = "raw_materials.jasper";
    public static final String PRODUCTS = "products.jasper";

    private final OperationsCRUD operationsCRUD;
    private final ItemsCRUD itemsCRUD;
    private final Connection connection;

    public R_Handler(Connection connection, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) {
        this.connection = connection;
        this.operationsCRUD = operationsCRUD;
        this.itemsCRUD = itemsCRUD;
    }

    public JasperViewer generateReport(String reportName, HashMap<String, Object> parameters) throws JRException, SQLException {
        JasperReport report = (JasperReport) JRLoader.loadObject(InputStreamToTempFile.resolveFile("reports/" + reportName));
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
        CustomJasperViewer viewer = new CustomJasperViewer(jasperPrint, false);
        return viewer;
    }

    public TreeMap<RawMaterial, TreeMap<String, Double>> getPeriodRawMaterialsExistences(Instant periodStart, Instant periodEnd) throws SQLException {
        return operationsCRUD.getPeriodRawMaterialsExistences(periodStart, periodEnd);
    }

    public ArrayList<Operation> getPeriodIncomes(Instant periodStart, Instant periodEnd, boolean isForWRM) throws SQLException {
        ArrayList<Operation> incomes = new ArrayList<>();
        String queryForWRawMaterials = "SELECT o.id, p.id, p.description, o.date, o.income, o.ammount, o.value, o.billed"
                + " FROM items p"
                + " JOIN operations o ON p.id = o.item"
                + " WHERE O.INCOME = true"
                + " AND O.OPENING = false"
                + " AND P.ID < " + ID_START_FOR_MATERIAS_PRIMAS_LISTAS
                + " AND O.DATE >= to_timestamp(" + periodStart.getEpochSecond() + ")"
                + " AND O.DATE < to_timestamp(" + periodEnd.getEpochSecond() + ")"
                + " AND O.ENTITY = " + EntitySelector.currentEntity.getId()
                + " ORDER BY O.DATE ASC";
        String queryForRFSRawMaterials = "SELECT o.id"
                + " FROM items p"
                + " JOIN operations o ON p.id = o.item"
                + " WHERE O.INCOME = true"
                + " AND O.OPENING = false"
                + " AND P.ID >= " + ID_START_FOR_MATERIAS_PRIMAS_LISTAS + " AND P.ID < " + ID_START_FOR_RECETAS
                + " AND O.DATE >= to_timestamp(" + periodStart.getEpochSecond() + ")"
                + " AND O.DATE < to_timestamp(" + periodEnd.getEpochSecond() + ")"
                + " AND O.ENTITY = " + EntitySelector.currentEntity.getId()
                + " ORDER BY O.DATE ASC";
        String sql = isForWRM ? queryForWRawMaterials : queryForRFSRawMaterials;
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt(1);
            incomes.add(operationsCRUD.get(id));
        }
        if (!incomes.isEmpty()) {
//            Logger.getInstance().updateInfoLog("List of " + Operation.class.getSimpleName() + " that represent " + (isForRaws ? " raw materials" : " ready for sale") + " RawMaterial incomes in period " + Timing.getMonth(periodStart).name() + " of " + Timing.getYear(periodStart) + " was successfully got!");
        }
        incomes.sort(Comparator.comparing(Operation::getInstant));
        return incomes;
    }

    public TreeMap<RawMaterial, Double[]> getPeriodRawMaterialsOutcomes(Instant periodStart, Instant periodEnd) throws SQLException {
        TreeMap<RawMaterial, Double[]> outcomes = new TreeMap<>(Comparator.comparingInt(Item::getCode));
        String sql = "SELECT P.ID,"
                + " SUM(O.AMMOUNT) as ammount,"
                + " SUM(O.VALUE) as value"
                + " FROM items p"
                + " JOIN operations o ON p.id = o.item"
                + " WHERE O.INCOME = false"
                + " AND O.OPENING = false"
                + " AND P.ID < " + ID_START_FOR_RECETAS
                + " AND O.DATE >= to_timestamp(" + periodStart.getEpochSecond() + ")"
                + " AND O.DATE < to_timestamp(" + periodEnd.getEpochSecond() + ")"
                + " AND O.ENTITY = " + EntitySelector.currentEntity.getId()
                + " GROUP BY p.id, p.description";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            RawMaterial RawMaterial = (RawMaterial) itemsCRUD.get(result.getInt(1));
            double ammount = result.getDouble(2);
            double value = result.getDouble(3);
            outcomes.put(RawMaterial, new Double[]{ammount, value});
        }
        if (!outcomes.isEmpty()) {
//            Logger.getInstance().updateInfoLog("Map of " + Item.class.getSimpleName() + " rawMaterials outcomes in the period " + Timing.getMonth(periodStart).name() + " of " + Timing.getYear(periodStart) + " was successfully got!");
        }
        return outcomes;
    }

    public TreeMap<Product, Double> getPeriodSale(Instant periodStart, Instant periodEnd) throws SQLException {
        TreeMap<Product, Double> outcomes = new TreeMap<>(Comparator.comparingInt(Item::getCode).thenComparing(Item::getId));
        String sql = "SELECT P.ID,"
                + " SUM(O.AMMOUNT) AS AMMOUNT"
                + " FROM ITEMS P"
                + " JOIN OPERATIONS O ON P.ID = O.ITEM"
                + " WHERE O.INCOME = false"
                + " AND O.OPENING = false"
                + " AND P.ID >= " + ID_START_FOR_PRODUCTOS_COCINA
                + " AND O.DATE >= to_timestamp(" + periodStart.getEpochSecond() + ")"
                + " AND O.DATE < to_timestamp(" + periodEnd.getEpochSecond() + ")"
                + " AND O.ENTITY = " + EntitySelector.currentEntity.getId()
                + " GROUP BY P.ID, P.DESCRIPTION";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            Product product = (Product) itemsCRUD.get(result.getInt(1));
            double ammount = result.getDouble(2);
            outcomes.put(product, ammount);
        }
        if (!outcomes.isEmpty()) {
//            Logger.getInstance().updateInfoLog("Map of " + Item.class.getSimpleName() + " products outcomes in the period " + Timing.getMonth(periodStart).name() + " of " + Timing.getYear(periodStart) + " was successfully got!");
        }
        return outcomes;
    }
}
