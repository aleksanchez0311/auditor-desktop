package cu.lacumbre.auditor.utils.optimization;

import java.sql.Connection;
import java.sql.SQLException;

import cu.lacumbre.utils.Logger;

public class SqlOptimizer {
    
    public static void addIndexes(Connection conn) throws SQLException {
        try {
            // Índices para la tabla operations
            createIndexIfNotExists(conn, "idx_operations_date_entity", "operations", "date, entity");
            createIndexIfNotExists(conn, "idx_operations_item", "operations", "item");
            createIndexIfNotExists(conn, "idx_operations_income", "operations", "income");
            
            // Índices para la tabla items
            createIndexIfNotExists(conn, "idx_items_entity", "items", "entity");
            createIndexIfNotExists(conn, "idx_items_type", "items", "type");
            
            // Índices para otras tablas frecuentemente consultadas
            createIndexIfNotExists(conn, "idx_workers_entity", "workers", "entity");
            createIndexIfNotExists(conn, "idx_roles_entity", "roles", "entity");
            createIndexIfNotExists(conn, "idx_measure_units_entity", "measure_units", "entity");
            
            Logger.getInstance().updateInfoLog("Índices de base de datos optimizados correctamente");
        } catch (SQLException e) {
            Logger.getInstance().updateErrorLog(e);
            throw e;
        }
    }
    
    private static void createIndexIfNotExists(Connection conn, String indexName, String tableName, String columns) 
            throws SQLException {
        try {
            String sql = String.format("CREATE INDEX IF NOT EXISTS %s ON %s(%s)", 
                                     indexName, tableName, columns);
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            Logger.getInstance().updateErrorLog(e);
            throw e;
        }
    }
    
    public static void analyzeDatabase(Connection conn) throws SQLException {
        try {
            conn.createStatement().execute("ANALYZE VERBOSE");
            Logger.getInstance().updateInfoLog("Análisis de la base de datos completado");
        } catch (SQLException e) {
            Logger.getInstance().updateErrorLog(e);
            throw e;
        }
    }
    
    public static void vacuumDatabase(Connection conn) throws SQLException {
        try {
            conn.createStatement().execute("VACUUM ANALYZE");
            Logger.getInstance().updateInfoLog("Vacuum de la base de datos completado");
        } catch (SQLException e) {
            Logger.getInstance().updateErrorLog(e);
            throw e;
        }
    }
}
