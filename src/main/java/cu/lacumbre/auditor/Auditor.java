package cu.lacumbre.auditor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import cu.lacumbre.auditor.crud.EntitiesCRUD;
import cu.lacumbre.auditor.crud.PostgreSQL;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.utils.optimization.ConnectionPool;
import cu.lacumbre.auditor.utils.optimization.PreparedStatementCache;
import cu.lacumbre.auditor.utils.optimization.SqlOptimizer;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Settings;
import java.awt.EventQueue;

public class Auditor {

    public static String defaultModeString = "F";
    private static boolean defaultModeBoolean = false;

    public static void main(String args[]) {
        try {
            Logger.getInstance().updateInfoLog("INFO LOG STARTED");
            Logger.getInstance().updateErrorLog(null);
            
            // Inicializar optimizaciones
            initializeOptimizations();
            
            Setup.init(Settings.getOptions());
            setLookAndFeel();
            if (args != null) {
                if (args.length > 0) {
                    if (args[0] != null) {
                        defaultModeString = args[0];
                        defaultModeBoolean = true;
                    }
                }
            }
            PostgreSQL postgreSQL = new PostgreSQL();
            EntitiesCRUD entitiesCRUD = new EntitiesCRUD(postgreSQL.getConnection());
            if (defaultModeBoolean) {
                EntitySelector.currentEntity = Entity.generate();
                Setup.updateLimits(EntitySelector.currentEntity.getId() - 1);
                runFrame(new DashBoard(postgreSQL, entitiesCRUD));
            } else {
                runFrame(new EntitySelector(postgreSQL, entitiesCRUD));
            }
        } catch (SQLException | IOException ex) {
            Logger.getInstance().updateErrorLog( ex);
        }
    }

    private static void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo aspect : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(aspect.getName())) {
                    UIManager.setLookAndFeel(aspect.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getInstance().updateErrorLog( ex);
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void runFrame(final JFrame frame) {
        EventQueue.invokeLater(() -> {
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    
    private static void initializeOptimizations() {
        try {
            // Inicializar pool de conexiones
            ConnectionPool.initialize();
            
            // Optimizar índices de la base de datos
            try (Connection conn = ConnectionPool.getConnection()) {
                SqlOptimizer.addIndexes(conn);
                SqlOptimizer.analyzeDatabase(conn);
            }
            
            // Registrar shutdown hook para limpieza
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    PreparedStatementCache.clearCache();
                    ConnectionPool.shutdown();
                } catch (Exception e) {
                    Logger.getInstance().updateErrorLog(e);
                }
            }));
            
            Logger.getInstance().updateInfoLog("Sistema optimizado inicializado correctamente");
        } catch (Exception e) {
            Logger.getInstance().updateErrorLog(e);
            throw new RuntimeException("Error al inicializar las optimizaciones", e);
        }
    }
}
