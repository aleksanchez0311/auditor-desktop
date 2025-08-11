package cu.lacumbre.auditor.utils.optimization;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.poi.ss.formula.functions.T;

@FunctionalInterface
public interface TransactionCallback<T> {
    T execute(Connection connection) throws SQLException;
}
