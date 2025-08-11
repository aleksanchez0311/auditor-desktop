package cu.lacumbre.auditor.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.poi.ss.formula.functions.T;

import cu.lacumbre.auditor.utils.optimization.CacheManager;
import cu.lacumbre.auditor.utils.optimization.ConnectionPool;
import cu.lacumbre.auditor.utils.optimization.PerformanceMonitor;
import cu.lacumbre.auditor.utils.optimization.PreparedStatementCache;
import cu.lacumbre.auditor.utils.optimization.TransactionCallback;
import cu.lacumbre.auditor.utils.optimization.TransactionManager;
import cu.lacumbre.utils.Logger;

public abstract class OptimizedCrudBase<T> {
    protected static final long CACHE_TTL = 300000; // 5 minutos
    protected final CacheManager<T> cache;
    protected final String tableName;
    
    protected OptimizedCrudBase(String tableName) {
        this.tableName = tableName;
        this.cache = CacheManager.getInstance();
    }
    
    protected Connection getConnection() throws SQLException {
        return ConnectionPool.getConnection();
    }
    
    protected Optional<T> getFromCache(String key) {
        return Optional.ofNullable(cache.get(key));
    }
    
    protected void putInCache(String key, T value) {
        cache.put(key, value, CACHE_TTL);
    }
    
    protected PreparedStatement getPreparedStatement(Connection conn, String sql) throws SQLException {
        return PreparedStatementCache.getPreparedStatement(conn, sql);
    }
    
    protected <R> R executeInTransaction(TransactionCallback<R> callback) throws SQLException {
        return TransactionManager.executeInTransaction(callback);
    }
    
    protected void executeWithMetrics(String operationName, Runnable operation) {
        PerformanceMonitor.runWithMetrics(operationName, operation);
    }
    
    protected <R> R executeWithMetrics(String operationName, java.util.function.Supplier<R> operation) {
        return PerformanceMonitor.measureOperation(operationName, operation);
    }
    
    protected void clearCache() {
        cache.clear();
    }
    
    protected int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = getPreparedStatement(conn, sql)) {
            
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            return stmt.executeUpdate();
        }
    }
    
    protected ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = getPreparedStatement(conn, sql);
        
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        
        return stmt.executeQuery();
    }
    
    protected void logError(Exception e, String message) {
        Logger.getInstance().updateErrorLog(e);
        throw new RuntimeException(message, e);
    }
}
