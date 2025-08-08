package cu.lacumbre.auditor.crud;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import static cu.lacumbre.auditor.Setup.ID_START_FOR_MATERIAS_PRIMAS_COCINA;
import static cu.lacumbre.auditor.Setup.ID_START_FOR_RECETAS;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Expense;
import cu.lacumbre.auditor.model.RawMaterial;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.RawMaterialLista;
import cu.lacumbre.auditor.model.Superclass;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Timing;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

public class OperationsCRUD implements ModelCRUDBatchExtended {

    private final TreeMap<Integer, Operation> map;
    private final ItemsCRUD itemsCrud;
    private final Entity entity;
    private final Connection connection;
    private final Comparator rawMaterialIdComparator = Comparator.comparingInt(RawMaterial::getId);
    private final Comparator operationIdComparator = Comparator.comparingInt(Integer::intValue);
    private final Comparator listComparator = Comparator.comparing(Operation::getInstant);
    private final Comparator singleCostComparator = Comparator.comparing(Operation::getSigleCost);

    public OperationsCRUD(Connection connection, Entity entity, ItemsCRUD itemsCRUD) throws SQLException {
        this(connection, entity, itemsCRUD, null);
    }

    public OperationsCRUD(Connection connection, Entity entity, ItemsCRUD itemsCRUD, RawMaterial rawMaterial) throws SQLException {
        this.connection = connection;
        this.entity = entity;
        this.itemsCrud = itemsCRUD;
        this.map = new TreeMap<>(operationIdComparator);
        if (rawMaterial == null) {
            loadDB();
            if (!map.isEmpty()) {
                Logger.getInstance().updateInfoLog("Map of Operations was successfully got!");
            }
        } else {
            loadSmallDB(rawMaterial);
            if (!map.isEmpty()) {
                Logger.getInstance().updateInfoLog("Map of Operations of " + rawMaterial.getDescription() + " was successfully got!");
            }
        }
    }

    public void reloadDB() throws SQLException {
        loadDB();
        if (!map.isEmpty()) {
            Logger.getInstance().updateInfoLog("Map of Operations was successfully refreshed!");
        }
    }

    private void loadDB() throws SQLException {
        map.clear();
        String sql = "SELECT * FROM operations where entity = " + entity.getId() + " order by date desc";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt(1);
            boolean income = result.getBoolean(2);
            double value = result.getDouble(3);
            double ammount = result.getDouble(4);
            Item item = itemsCrud.get(result.getInt(5));
            boolean billed = result.getBoolean(6);
            boolean opening = result.getBoolean(7);
            Instant date = result.getTimestamp(8, Calendar.getInstance()).toInstant();
            Operation operation = new Operation(id, item, date, ammount, value, income, billed, opening);
            map.put(id, operation);
        }
    }

    private void loadSmallDB(RawMaterial rawMaterial) throws SQLException {
        String sql = "SELECT * FROM operations where entity = " + entity.getId() + " order by date desc";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, rawMaterial.getId());
        statement.setInt(2, entity.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int id = result.getInt(1);
            boolean income = result.getBoolean(2);
            double value = result.getDouble(3);
            double ammount = result.getDouble(4);
            Item item = itemsCrud.get(result.getInt(5));
            boolean billed = result.getBoolean(6);
            boolean opening = result.getBoolean(7);
            Instant date = result.getTimestamp(8, Calendar.getInstance()).toInstant();
            Operation operation = new Operation(id, item, date, ammount, value, income, billed, opening);
            map.put(id, operation);
        }
    }

    public ItemsCRUD getItemsCrud() {
        return itemsCrud;
    }

    public ArrayList<Operation> getList() {
        return new ArrayList<>(map.values());
    }

    public Timing getFirstOperationDate() {
        ArrayList<Operation> operations = getList();
        if (!operations.isEmpty()) {
            operations.sort(listComparator);
            return new Timing(operations.get(0).getInstant());
        }
        return new Timing(entity.getFirstDay());
    }

    public Timing getCurrentOperationDate() {
        return new Timing(entity.getCurrentDay());
    }

    public Timing getLastOperationDate() {
        ArrayList<Operation> operations = getList();
        if (!operations.isEmpty()) {
            operations.sort(listComparator.reversed());
            return new Timing(operations.get(0).getInstant());
        }
        return new Timing(entity.getCurrentDay());
    }

    public ArrayList<Operation> getExpenseOperationsList() {
        ArrayList<Operation> result = new ArrayList<>();
        for (Operation operation : getList()) {
            if (operation.isIncome() && operation.getItem() instanceof Expense && !(operation.getItem() instanceof RawMaterial)) {
                result.add(operation);
            }
        }
        result.sort(listComparator.reversed());
        return result;
    }

    public ArrayList<Operation> getIncomeOperationsList() {
        ArrayList<Operation> result = new ArrayList<>();
        for (Operation operation : getList()) {
            if (operation.isIncome() && operation.getItem() instanceof RawMaterial) {
                result.add(operation);
            }
        }
        result.sort(listComparator.reversed());
        return result;
    }

    public ArrayList<Operation> getOutcomeOperationsList() {
        ArrayList<Operation> result = new ArrayList<>();
        for (Operation operation : getList()) {
            if (!operation.isIncome() && operation.getItem() instanceof RawMaterial) {
                result.add(operation);
            }
        }
        result.sort(listComparator.reversed());
        return result;
    }

    public ArrayList<Operation> getSaleOperationsList() {
        ArrayList<Operation> result = new ArrayList<>();
        for (Operation operation : getList()) {
            if (!operation.isIncome() && operation.getItem() instanceof Product) {
                result.add(operation);
            }
        }
        result.sort(listComparator.reversed());
        return result;
    }

    public ArrayList<Operation> getDateSaleOperationsList(LocalDate filterDate) {
        ArrayList<Operation> result = getSaleOperationsList();
        result.removeIf((Operation operation) -> !new Timing(operation.getInstant()).isTheSameDay(filterDate));
        return result;
    }

    public TreeMap<RawMaterial, Double> getPeriodUnsoldSaleableRawMaterials(Timing filterTiming) throws SQLException {
        TreeMap<RawMaterial, Double> result = new TreeMap<>(Comparator.comparingInt(RawMaterial::getId));
        TreeMap<RawMaterial, TreeMap<String, Double>> periodRawMaterialsExistences = getPeriodRawMaterialsExistences(filterTiming.atStartOfMonth(), filterTiming.atStartOfNextMonth());
        for (Map.Entry<RawMaterial, TreeMap<String, Double>> entry : periodRawMaterialsExistences.entrySet()) {
            RawMaterial key = entry.getKey();
            TreeMap<String, Double> values = entry.getValue();
            if (key instanceof RawMaterialLista) {
                if (values.get("income") == 0 && values.get("outcome") == 0) {
                    result.put(key, values.get("begin"));
                }
            }
        }
        return result;
    }

    public TreeMap<RawMaterial, TreeMap<String, Double>> getPeriodRawMaterialsExistences(Instant periodStart, Instant periodEnd) throws SQLException {
        TreeMap<RawMaterial, TreeMap<String, Double>> moves = new TreeMap<>(Comparator.comparingInt(Item::getCode));
        String sql = "SELECT * FROM"
                + " (SELECT p.id, p.description,"
                + " (SELECT SUM(CASE WHEN o.income THEN o.ammount ELSE -o.ammount END) FROM operations o WHERE o.item = p.id" + " AND O.ENTITY = " + EntitySelector.currentEntity.getId() + " AND O.DATE < to_timestamp(" + periodStart.getEpochSecond() + ")) AS at_begin,"
                + " (SELECT SUM(o.ammount) FROM operations o WHERE o.item = p.id" + " AND O.ENTITY = " + EntitySelector.currentEntity.getId() + " AND O.DATE >= to_timestamp(" + periodStart.getEpochSecond() + ") AND O.DATE < to_timestamp(" + periodEnd.getEpochSecond() + ") AND o.income = true) AS incomes,"
                + " (SELECT SUM(o.ammount) FROM operations o WHERE o.item = p.id" + " AND O.ENTITY = " + EntitySelector.currentEntity.getId() + " AND O.DATE >= to_timestamp(" + periodStart.getEpochSecond() + ") AND O.DATE < to_timestamp(" + periodEnd.getEpochSecond() + ") AND o.income = false) AS outcomes,"
                + " (SELECT SUM(CASE WHEN o.income THEN o.ammount ELSE -o.ammount END) FROM operations o WHERE o.item = p.id" + " AND O.ENTITY = " + EntitySelector.currentEntity.getId() + " AND O.DATE < to_timestamp(" + periodEnd.getEpochSecond() + ")) AS at_end"
                + " FROM items p"
                + " WHERE p.id >= " + ID_START_FOR_MATERIAS_PRIMAS_COCINA
                + " AND p.id < " + ID_START_FOR_RECETAS
                + " ORDER BY p.id) AS existences"
                + " WHERE at_begin > 0"
                + " OR incomes > 0"
                + " OR outcomes > 0"
                + " OR at_end > 0;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            RawMaterial rawMaterial = (RawMaterial) itemsCrud.get(result.getInt(1));
            double atBegin = result.getDouble(3);
            double income = result.getDouble(4);
            double outcome = result.getDouble(5);
            double atEnd = result.getDouble(6);
            TreeMap<String, Double> values = new TreeMap<>(Comparator.comparing(String::toString));
            values.put("begin", atBegin);
            values.put("income", income);
            values.put("outcome", outcome);
            values.put("end", atEnd);
            moves.put(rawMaterial, values);
        }
        if (!moves.isEmpty()) {
//            Logger.getInstance().updateInfoLog("Map of " + Item.class.getSimpleName() + " existences in period " + localDa.getMonth(periodStart).name() + " of " + Timing.getYear(periodStart) + " was successfully got!");
        }
        return moves;
    }

    @Override
    public Operation get(int id) {
        return map.get(id);
    }

    @Override
    public void save(ArrayList<? extends Superclass> superclases) throws SQLException {
        ArrayList<Operation> operations = (ArrayList<Operation>) superclases;
        String sql = "INSERT INTO operations(income, value, ammount, date, item, billed, entity) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Operation operation : operations) {
            statement.setBoolean(1, operation.isIncome());
            statement.setDouble(2, operation.getValue());
            statement.setDouble(3, operation.getAmmount());
            statement.setTimestamp(4, Timestamp.from(operation.getInstant()));
            statement.setInt(5, operation.getItem().getId());
            statement.setBoolean(6, operation.isBilled());
            statement.setInt(7, entity.getId());
            statement.addBatch();
        }
        int[] rowsInserted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsInserted.length > 0) {
            Logger.getInstance().updateInfoLog("Some Operations ware successfully saved!");
        } else {
            Logger.getInstance().updateInfoLog("No Operations was saved!");
        }
        updateRelatedCosts(operations);
        reloadDB();
    }

    @Override
    public void save(Object obj) throws SQLException {
        Operation object = (Operation) obj;
        String sql = "INSERT INTO operations(income, value, ammount, date, item, billed, entity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setBoolean(1, object.isIncome());
        statement.setDouble(2, object.getValue());
        statement.setDouble(3, object.getAmmount());
        statement.setTimestamp(4, Timestamp.from(object.getInstant()));
        statement.setInt(5, object.getItem().getId());
        statement.setBoolean(6, object.isBilled());
        statement.setInt(7, entity.getId());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("A new " + object.getClass().getSimpleName() + " was saved successfully!");
        }
        updateRelatedCosts(new ArrayList<>(Arrays.asList(new Operation[]{object})));
        reloadDB();
    }

    public void save(Object obj, int entityID) throws SQLException {
        Operation object = (Operation) obj;
        String sql = "INSERT INTO operations(income, value, ammount, date, item, billed, entity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setBoolean(1, object.isIncome());
        statement.setDouble(2, object.getValue());
        statement.setDouble(3, object.getAmmount());
        statement.setTimestamp(4, Timestamp.from(object.getInstant()));
        statement.setInt(5, object.getItem().getId());
        statement.setBoolean(6, object.isBilled());
        statement.setInt(7, entityID);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("Some operations ware successfully saved!");
        }
        updateRelatedCosts(new ArrayList<>(Arrays.asList(new Operation[]{object})));
        reloadDB();
    }

    @Override
    public void update(Object obj) throws SQLException {
        Operation object = (Operation) obj;
        String sql = "UPDATE operations SET income=?, value=?, ammount=?, date=?, item=? , billed=?  WHERE id=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setBoolean(1, object.isIncome());
        statement.setDouble(2, object.getValue());
        statement.setDouble(3, object.getAmmount());
        statement.setTimestamp(4, Timestamp.from(object.getInstant()));
        statement.setInt(5, object.getItem().getId());
        statement.setBoolean(6, object.isBilled());
        statement.setInt(7, object.getId());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Logger.getInstance().updateInfoLog("An existing " + object.getClass().getSimpleName() + " was successfully updated!");
        }
        updateRelatedCosts(new ArrayList<>(Arrays.asList(new Operation[]{object})));
        reloadDB();
    }

    @Override
    public void update(ArrayList<? extends Superclass> superclases) throws SQLException {
        ArrayList<Operation> operations = (ArrayList<Operation>) superclases;
        String sql = "UPDATE operations SET income=?, value=?, ammount=?, date=?, item=? , billed=?  WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Operation operation : operations) {
            statement.setBoolean(1, operation.isIncome());
            statement.setDouble(2, operation.getValue());
            statement.setDouble(3, operation.getAmmount());
            statement.setTimestamp(4, Timestamp.from(operation.getInstant()));
            statement.setInt(5, operation.getItem().getId());
            statement.setBoolean(6, operation.isBilled());
            statement.setInt(7, operation.getId());
            statement.addBatch();
        }
        int[] rowsInserted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsInserted.length > 0) {
            Logger.getInstance().updateInfoLog("Some Operattions ware successfully updated!");
        }
        updateRelatedCosts(operations);
        reloadDB();
    }

    public void updateAll() throws SQLException {
        String sql = "UPDATE operations SET income=?, value=?, ammount=?, date=?, item=? , billed=?  WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Operation operation : getList()) {
            statement.setBoolean(1, operation.isIncome());
            statement.setDouble(2, operation.getValue());
            statement.setDouble(3, operation.getAmmount());
            statement.setTimestamp(4, Timestamp.from(operation.getInstant()));
            statement.setInt(5, operation.getItem().getId());
            statement.setBoolean(6, operation.isBilled());
            statement.setInt(7, operation.getId());
            statement.addBatch();
        }
        int[] rowsInserted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsInserted.length > 0) {
            Logger.getInstance().updateInfoLog("Some Operattions ware successfully updated!");
        }
        updateRelatedCosts(getList());
        reloadDB();
    }

    @Override
    public void delete(ArrayList<? extends Superclass> superclases) throws SQLException {
        ArrayList<Operation> operations = (ArrayList<Operation>) superclases;
        String sql = "DELETE FROM operations WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (Operation operation : operations) {
            statement.setInt(1, operation.getId());
            statement.addBatch();
        }
        int[] rowsDeleted = statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        if (rowsDeleted.length > 0) {
            Logger.getInstance().updateInfoLog("Some Operations ware successfully deleted!");
        }
        updateRelatedCosts(operations);
        reloadDB();
    }

    public TreeMap<RawMaterial, Double> getInventory(Instant atEndOfDay) throws SQLException {
        TreeMap<RawMaterial, Double> treeMap = new TreeMap<>(rawMaterialIdComparator);
        if (atEndOfDay != null) {
            String sql = "SELECT P.ID, SUM(CASE WHEN O.INCOME THEN O.AMMOUNT ELSE - O.AMMOUNT END) AS EXISTENCE "
                    + "FROM operations O "
                    + "JOIN items P ON P.ID = O.ITEM "
                    + "WHERE p.id>=? and p.id<? AND O.ENTITY = ? AND O.DATE <= ? "
                    + "GROUP BY P.ID "
                    + "ORDER BY P.ID;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Setup.ID_START_FOR_MATERIAS_PRIMAS_COCINA);
            statement.setInt(2, Setup.ID_START_FOR_RECETAS);
            statement.setInt(3, entity.getId());
            statement.setTimestamp(4, Timestamp.from(atEndOfDay));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                double ammount = result.getDouble(2);
                if (ammount > 0) {
                    treeMap.merge(((RawMaterial) itemsCrud.get(id)), ammount, (Double oldVal, Double newVal) -> oldVal - newVal);
                }
            }
            if (!treeMap.isEmpty()) {
                Logger.getInstance().updateInfoLog("Map of " + Item.class.getSimpleName() + " existences was successfully got!");
            }
        }
        return treeMap;

    }

    public void updateRelatedExpenseCosts(Expense expense) throws SQLException {
        ArrayList<Operation> relatedOperations = getIncomeOperationsListOf(expense);
        if (!relatedOperations.isEmpty()) {
            relatedOperations.sort(listComparator);
            Operation lastOperation = relatedOperations.get(relatedOperations.size() - 1);
            double lastCost = lastOperation.getValue() / lastOperation.getAmmount();
            expense.setCost(lastCost);
        }
        itemsCrud.update(expense);
    }

    public void updateRelatedRawMaterialCosts(RawMaterial rawMaterial) throws SQLException {
        ArrayList<Operation> buyOperationsList = getIncomeOperationsListOf(rawMaterial);
        if (buyOperationsList.isEmpty()) {
            rawMaterial.setLastCost(rawMaterial.getBasicCost());
            rawMaterial.setHighestCost(rawMaterial.getBasicCost());
            rawMaterial.setWeightedCost(rawMaterial.getBasicCost());
        } else {
            rawMaterial.setWeightedCost(rawMaterial.promediarCosto(buyOperationsList));
            buyOperationsList.sort(listComparator);
            Operation lastOperation = buyOperationsList.get(buyOperationsList.size() - 1);
            rawMaterial.setLastCost(lastOperation.getSigleCost());
            buyOperationsList.sort(singleCostComparator);
            double highestCost = buyOperationsList.get(buyOperationsList.size() - 1).getSigleCost();
            rawMaterial.setHighestCost(highestCost > rawMaterial.getBasicCost() ? highestCost : rawMaterial.getBasicCost());
        }
        itemsCrud.update(rawMaterial);
    }

    private ArrayList<Operation> getIncomeOperationsListOf(Item rawMaterial) {
        ArrayList<Operation> result = new ArrayList<>();
        for (Operation operation : getIncomeOperationsList()) {
            if (operation.getItem().getId() == rawMaterial.getId()) {
                result.add(operation);
            }
        }
        result.sort(listComparator);
        return result;
    }

    private ArrayList<Operation> getOutcomeOperationsListOf(RawMaterial rawMaterial) {
        ArrayList<Operation> result = new ArrayList<>();
        for (Operation operation : getOutcomeOperationsList()) {
            if (operation.getItem().getId() == rawMaterial.getId()) {
                result.add(operation);
            }
        }
        result.sort(listComparator);
        return result;
    }

    private ArrayList<Operation> getSaleOperationsListOf(Product product) {
        ArrayList<Operation> result = new ArrayList<>();
        for (Operation operation : getSaleOperationsList()) {
            if (operation.getItem().getId() == product.getId()) {
                result.add(operation);
            }
        }
        result.sort(listComparator);
        return result;
    }

    private void updateRelatedCosts(ArrayList<Operation> operations) throws SQLException {
        for (Operation operation : operations) {
            if (operation.isIncome()) {
                if (operation.getItem() instanceof RawMaterial rawMaterial) {
                    updateRelatedRawMaterialCosts(rawMaterial);
                } else if (operation.getItem() instanceof Expense expense) {
                    updateRelatedExpenseCosts(expense);
                }
            }
        }
    }

    public ArrayList<Operation> getRawMaterialOutcomeOperationsFromDateInstant(Instant instant) {
        ArrayList<Operation> rawMaterialOutcomeOperationsFromDateInstant = new ArrayList<>();
        for (Operation operation : getOutcomeOperationsList()) {
            if (operation.getItem() instanceof RawMaterial) {
                if (operation.getInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(instant.minusSeconds(57599).atZone(ZoneId.systemDefault()).toLocalDate())) {
                    rawMaterialOutcomeOperationsFromDateInstant.add(operation);
                }
            }
        }
        return rawMaterialOutcomeOperationsFromDateInstant;
    }

    public int getWeightedSaleOf(Product productListo) {
        return productListo.promediarVenta(getSaleOperationsListOf(productListo));
    }

    public int[] adjustUnsoldProducts(int weightedSale, int ammount, LocalDate fecha) {
        return SimuladorVentas.simularVentas(weightedSale, ammount, fecha);

    }

    public double getPeriodExpenses(Instant atStartOfMonth, Instant atStartOfNextMonth) {
        double periodExpenses = 0.0d;
        for (Operation operation : getExpenseOperationsList()) {
            if ((operation.getInstant().isAfter(atStartOfMonth) || operation.getInstant().equals(atStartOfMonth)) && operation.getInstant().isBefore(atStartOfNextMonth)) {
                periodExpenses += operation.getValue();
            }
        }
        return periodExpenses;
    }

    public class SimuladorVentas {
        public static int[] simularVentas(int ventaPromedio, int inventario, LocalDate fecha) {
            YearMonth ym = YearMonth.from(fecha);
            int dias = ym.lengthOfMonth();

            int[] ventas = new int[dias];
            Random rand = new Random();

            int suma = 0;

            for (int i = 0; i < dias; i++) {
                // Genera una venta aleatoria entera entre 0 y ventaPromedio inclusive.
                int ventaDia = rand.nextInt(Double.valueOf((ventaPromedio + 1) * 1.3).intValue());

                // Ajusta si se pasa del inventario
                if (suma + ventaDia > inventario) {
                    ventaDia = inventario - suma;
                    if (ventaDia < 0) {
                        ventaDia = 0;
                    }
                }

                ventas[i] = ventaDia;
                suma += ventaDia;

                // Si agotamos el inventario, ventas restantes son 0
                if (suma >= inventario) {
                    for (int j = i + 1; j < dias; j++) {
                        ventas[j] = 0;
                    }
                    break;
                }
            }

            return ventas;
        }
    }
}
