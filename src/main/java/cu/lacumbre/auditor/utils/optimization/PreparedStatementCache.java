package cu.lacumbre.auditor.utils.optimization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PreparedStatementCache {
    private static final Map<String, Map<Connection, PreparedStatement>> statementCache = new ConcurrentHashMap<>();
    
    public static PreparedStatement getPreparedStatement(Connection conn, String sql) throws SQLException {
        return statementCache
            .computeIfAbsent(sql, k -> new ConcurrentHashMap<>())
            .computeIfAbsent(conn, k -> {
                try {
                    return conn.prepareStatement(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
    }
    
    public static void closeStatements(Connection conn) {
        statementCache.values().forEach(connectionMap -> {
            PreparedStatement stmt = connectionMap.remove(conn);
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // Log error
                }
            }
        });
    }
    
    public static void clearCache() {
        statementCache.values().forEach(connectionMap ->
            connectionMap.values().forEach(stmt -> {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // Log error
                }
            })
        );
        statementCache.clear();
    }
}
