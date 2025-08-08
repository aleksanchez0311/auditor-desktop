package cu.lacumbre.auditor.crud;

import cu.lacumbre.utils.InputStreamToTempFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import cu.lacumbre.utils.Logger;
import java.io.File;
import java.sql.SQLException;

public class PostgreSQL {

    private Properties properties = getProperties();
    private final String DB_URL = (String) properties.get("DB_URL");
    private final String DB_USERNAME = (String) properties.get("DB_USERNAME");
    private final String DB_PASSWORD = (String) properties.get("DB_PASSWORD");
    private final Connection connection;

    public PostgreSQL() throws SQLException{
        this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        if (connection != null) {
            StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
            StackTraceElement stackTraceElement = stackTraceElements[0];
            Logger.getInstance().printLog("Connected from " + stackTraceElement.getMethodName() + "() in class " + stackTraceElement.getClassName(), true);
        }
    }


    public void disconnect(StackTraceElement[] stackTraceElements) throws SQLException {
        connection.close();
        StackTraceElement stackTraceElement = stackTraceElements[0];
        Logger.getInstance().printLog("Disconnected from " + stackTraceElement.getMethodName() + "() in class " + stackTraceElement.getClassName(), true);
    }
    
     private Properties getProperties() {
        Properties properties = new Properties();
        try {
            File file = InputStreamToTempFile.resolveFile("conf/DBProperties.conf");
            properties.load(new FileInputStream(file));
        } catch (IOException ex) {
            Logger.getInstance().updateErrorLog( ex);
            properties = null;
        }
        return properties;
    }

    public Connection getConnection() {
        return connection;
    }
}
