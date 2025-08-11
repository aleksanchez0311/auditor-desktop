package cu.lacumbre.auditor.utils.optimization;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cu.lacumbre.utils.InputStreamToTempFile;
import cu.lacumbre.utils.Logger;

public class ConnectionPool {
    private static ComboPooledDataSource cpds;
    private static Properties properties;
    
    static {
        try {
            initialize();
        } catch (Exception e) {
            Logger.getInstance().updateErrorLog(e);
        }
    }
    
    private static void initialize() throws Exception {
        properties = getProperties();
        cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl((String) properties.get("DB_URL"));
        cpds.setUser((String) properties.get("DB_USERNAME"));
        cpds.setPassword((String) properties.get("DB_PASSWORD"));
        cpds.setMinPoolSize(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxIdleTime(300);
        cpds.setIdleConnectionTestPeriod(60);
        cpds.setTestConnectionOnCheckin(true);
    }
    
    private static Properties getProperties() {
        Properties props = new Properties();
        try {
            File file = InputStreamToTempFile.resolveFile("conf/DBProperties.conf");
            props.load(new FileInputStream(file));
        } catch (Exception ex) {
            Logger.getInstance().updateErrorLog(ex);
            props = null;
        }
        return props;
    }
    
    public static Connection getConnection() throws SQLException {
        if (cpds == null) {
            try {
                initialize();
            } catch (Exception e) {
                throw new SQLException("No se pudo inicializar el pool de conexiones", e);
            }
        }
        return cpds.getConnection();
    }
    
    public static void shutdown() {
        if (cpds != null) {
            cpds.close();
        }
    }
}
