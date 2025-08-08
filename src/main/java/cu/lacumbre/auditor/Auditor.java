package cu.lacumbre.auditor;

import static cu.lacumbre.auditor.EntitySelector.currentEntity;
import cu.lacumbre.auditor.crud.EntitiesCRUD;
import cu.lacumbre.auditor.crud.PostgreSQL;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Settings;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Auditor {

    public static String defaultModeString = "F";
    private static boolean defaultModeBoolean = false;

//TODO Implementar la creacion de la base de datos en el primer arranque
//TODO Implementar usuarios y turnos
//TODO cruds a tablemodels para mejorar rendimiento
//TODO  Verificar que el precio de venta en el reporte de ventas del periodo sea acorde a las variaciones de precio
//    public static void main(String args[]) throws IOException {
//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//            EventQueue.invokeLater(() -> {
//                try {
//                    EntitySelector entitySelector = new EntitySelector();
//                    entitySelector.setLocationRelativeTo(null);
//                    entitySelector.setVisible(true);
//                } catch (SQLException ex) {
//                    Logger.getInstance().updateErrorLog( ex);
//                }
//            });
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            Logger.getInstance().updateErrorLog( ex);
//        }
//        
//
//    }
    public static void main(String args[]) {
        try {
            Logger.getInstance().updateInfoLog("INFO LOG STARTED");
            Logger.getInstance().updateErrorLog( null);
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
                currentEntity = Entity.generate();
                Setup.updateLimits(currentEntity.getId() - 1);
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
}
