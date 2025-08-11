package cu.lacumbre.auditor.utils.optimization;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallback<T> {
    T execute(Connection connection) throws SQLException;
}
