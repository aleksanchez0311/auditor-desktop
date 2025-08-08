package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.auditor.crud.PostgreSQL;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.utils.Pair;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public final class CustomComparator {

    public static final String DOUBLE_JAVA_TYPE = "double";
    public static final String INTEGER_JAVA_TYPE = "integer";
    public static final String STRING_JAVA_TYPE = "string";

    public static final int INTEGER_SQL_TYPE = 4;
    public static final int REAL_SQL_TYPE = 7;
    public static final int VARCHAR_SQL_TYPE = 12;

    private static final Comparator<?> COMPARATOR_STRING = Comparator.comparing(String::toString);
    private static final Comparator<?> COMPARATOR_INTEGER = Comparator.comparingInt(Integer::intValue);
    private static final Comparator<?> COMPARATOR_DOUBLE = Comparator.comparingDouble(Double::doubleValue);
    private static final Comparator<?> COMPARATOR_DOUBLE_TYPE1 = (Double i1, Double i2) -> Double.compare(i1, i2);
    private static final Comparator<?> COMPARATOR_INTEGER_TYPE1 = (Integer i1, Integer i2) -> Integer.compare(i1, i2);

    private Comparator<?> comparator;
    private Pair selectedColumn;
    private final Connection connection;

    public CustomComparator(Connection connection, String parametros) throws SQLException {
        this.connection = connection;
        String[] parametrosArray = parametros.split(":");
        setComparator(new Pair(parametrosArray[0], parametrosArray[1]));
    }

    public CustomComparator(Pair pair) throws SQLException {
        this.connection = new PostgreSQL().getConnection();
        setComparator(pair);
    }

    public Comparator<?> getComparator() {
        return comparator;
    }

    public Pair getSelectedColumn() {
        return selectedColumn;
    }

    public Class getSelectedClass() {
        return selectedColumn.getRightMember().getClass();
    }

    public ArrayList<Pair> getPairs() throws SQLException {
        return new ArrayList<>(readColumnsInfo().values());
    }

    public void setComparator(Pair<String, String> pair) {
        switch (pair.getRightMember()) {
            case STRING_JAVA_TYPE -> {
                this.selectedColumn = pair;
                this.comparator = COMPARATOR_STRING;
            }
            case INTEGER_JAVA_TYPE -> {
                this.comparator = COMPARATOR_INTEGER;
                this.selectedColumn = pair;
            }
            case DOUBLE_JAVA_TYPE -> {
                this.comparator = COMPARATOR_DOUBLE;
                this.selectedColumn = pair;
            }
            default -> {
                this.comparator = null;
                this.selectedColumn = null;
            }
        }
    }

    public void setComparator(int columnIndex) throws SQLException {
        for (Map.Entry<Integer, Pair<String, String>> entry : readColumnsInfo().entrySet()) {
            if (columnIndex == entry.getKey()) {
                setComparator(entry.getValue());
            }
        }
    }

    private TreeMap<Integer, Pair<String, String>> readColumnsInfo() throws SQLException {
        TreeMap<Integer, Pair<String, String>> columns = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
        String sql = "SELECT * FROM items";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        int columnCount = result.getMetaData().getColumnCount();
        for (int i = 1; i < columnCount + 1; i++) {
            String columnType = mapTypeName(result.getMetaData().getColumnType(i));
            if (columnType != null) {
                if ("descriptioncodeid".contains(result.getMetaData().getColumnName(i))) {
                    columns.put(i, new Pair(result.getMetaData().getColumnName(i), columnType));
                }
            }
        }
        return columns;
    }

    private String mapTypeName(int columnType) {
        return switch (columnType) {
            case INTEGER_SQL_TYPE ->
                INTEGER_JAVA_TYPE;
            case REAL_SQL_TYPE ->
                DOUBLE_JAVA_TYPE;
            case VARCHAR_SQL_TYPE ->
                STRING_JAVA_TYPE;
            default ->
                null;
        };
    }

    @Override
    public String toString() {
        return "Columna -> " + selectedColumn;
    }

    public ArrayList<? extends Item> sortByCode(ArrayList<? extends Item> categoryList) {
        categoryList.sort(Comparator.comparingInt(Item::getCode));
        return categoryList;
    }

    public ArrayList<? extends Item> sortByID(ArrayList<? extends Item> categoryList) {
        categoryList.sort(Comparator.comparingInt(Item::getId));
        return categoryList;
    }

    public ArrayList<? extends Item> sortByDescription(ArrayList<? extends Item> categoryList) {
        categoryList.sort(Comparator.comparing(Item::getDescription));
        return categoryList;
    }

//    public static final int BIT = -7;
//    public static final int TINYINT = -6;
//    public static final int SMALLINT = 5;
//    public static final int BIGINT = -5;
//    public static final int FLOAT = 6;
//    public static final int DOUBLE = 8;
//    public static final int NUMERIC = 2;
//    public static final int DECIMAL = 3;
//    public static final int CHAR = 1;
//    public static final int LONGVARCHAR = -1;
//    public static final int DATE = 91;
//    public static final int TIME = 92;
//    public static final int TIMESTAMP = 93;
//    public static final int BINARY = -2;
//    public static final int VARBINARY = -3;
//    public static final int LONGVARBINARY = -4;
//    public static final int NULL = 0;
//    public static final int OTHER = 1111;
//    public static final int JAVA_OBJECT = 2000;
//    public static final int DISTINCT = 2001;
//    public static final int STRUCT = 2002;
//    public static final int ARRAY = 2003;
//    public static final int BLOB = 2004;
//    public static final int CLOB = 2005;
//    public static final int REF = 2006;
//    public static final int DATALINK = 70;
//    public static final int BOOLEAN = 16;
//    public static final int ROWID = -8;
//    public static final int NCHAR = -15;
//    public static final int NVARCHAR = -9;
//    public static final int LONGNVARCHAR = -16;
//    public static final int NCLOB = 2011;
//    public static final int SQLXML = 2009;
//    public static final int REF_CURSOR = 2012;
//    public static final int TIME_WITH_TIMEZONE = 2013;
//    public static final int TIMESTAMP_WITH_TIMEZONE = 2014;
}
