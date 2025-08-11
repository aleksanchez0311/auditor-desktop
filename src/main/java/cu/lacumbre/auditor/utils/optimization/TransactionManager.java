package cu.lacumbre.auditor.utils.optimization;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.poi.ss.formula.functions.T;

import cu.lacumbre.utils.Logger;

public class TransactionManager {
    
    public static <T> T executeInTransaction(TransactionCallback<T> callback) throws SQLException {
        Connection conn = null;
        boolean originalAutoCommit = true;
        
        try {
            conn = ConnectionPool.getConnection();
            originalAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            
            T result = callback.execute(conn);
            
            conn.commit();
            return result;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    Logger.getInstance().updateErrorLog(rollbackEx);
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(originalAutoCommit);
                    conn.close();
                } catch (SQLException e) {
                    Logger.getInstance().updateErrorLog(e);
                }
            }
        }
    }
    
    public static void executeInTransaction(Connection conn, Runnable operation) throws SQLException {
        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            operation.run();
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            if (e instanceof SQLException) {
                throw (SQLException) e;
            }
            throw new SQLException("Error en la transacción", e);
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }
}
