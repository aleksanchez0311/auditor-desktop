package cu.lacumbre.auditor;

import cu.lacumbre.auditor.model.Expense;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.ProductCocina;
import cu.lacumbre.auditor.model.ProductListo;
import cu.lacumbre.auditor.model.RawMaterial;
import cu.lacumbre.auditor.model.RawMaterialCocina;
import cu.lacumbre.auditor.model.RawMaterialLista;
import cu.lacumbre.auditor.model.Workable;
import cu.lacumbre.utils.Settings;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class Setup {

    public static final Font DEFAULT_FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final LineBorder DEFAULT_BORDER = new LineBorder(Color.black, 1);

    public static final String CUADRES_DIRECTORY = "lastDirectoryOfCuadres";
    public static final String REPORTS_DIRECTORY = "lastDirectoryOfReports";
    public static final String CSVS_DIRECTORY = "lastDirectoryOfCVS";
    public static final String ATTENDANCE_DIRECTORY = "lastDirectoryOfAttendance";

    public static int ID_START_FOR_GASTOS;
    public static int ID_START_FOR_MATERIAS_PRIMAS_COCINA;
    public static int ID_START_FOR_MATERIAS_PRIMAS_LISTAS;
    public static int ID_START_FOR_RECETAS;
    public static int ID_START_FOR_PRODUCTOS_COCINA;
    public static int ID_START_FOR_PRODUCTOS_LISTO;

    public static final String BUYABLE = "Item Simple";
    public static final String ELABORABLE = "Item Compuesto ";

    public static final String CATEGORIA_GASTO = "Gasto";
    public static final String CATEGORIA_MATERIA_PRIMA = "Materia Prima";
    public static final String CATEGORIA_RECETA = "Receta";
    public static final String CATEGORIA_PRODUCTO = "Producto";

    private static final String COCINA = "para la cocina";
    private static final String LISTO = "listo para la venta";

    public static final String SUBCATEGORIA_GASTO = CATEGORIA_GASTO;
    public static final String SUBCATEGORIA_MATERIA_PRIMA_COCINA = CATEGORIA_MATERIA_PRIMA + " " + COCINA;
    public static final String SUBCATEGORIA_MATERIA_PRIMA_LISTA = CATEGORIA_MATERIA_PRIMA + " " + LISTO;
    public static final String SUBCATEGORIA_RECETA = CATEGORIA_RECETA;
    public static final String SUBCATEGORIA_PRODUCTO_COCINA = CATEGORIA_PRODUCTO + " " + COCINA;
    public static final String SUBCATEGORIA_PRODUCTO_LISTO = CATEGORIA_PRODUCTO + " " + LISTO;

    public static final Class _GASTO = Expense.class;
    public static final Class _MATERIA_PRIMA = RawMaterial.class;
    public static final Class _MATERIA_PRIMA_COCINA = RawMaterialCocina.class;
    public static final Class _MATERIA_PRIMA_LISTA = RawMaterialLista.class;
    public static final Class _RECETA = Workable.class;
    public static final Class _PRODUCTO = Product.class;
    public static final Class _PRODUCTO_COCINA = ProductCocina.class;
    public static final Class _PRODUCTO_LISTO = ProductListo.class;

    public static String enterpriseName;
    public static Properties options;

    public static void init(Properties options) {
        String enterpriseNameFromPorperties = options.getProperty("enterpriseName");
        if (enterpriseNameFromPorperties != null) {
            enterpriseName = enterpriseNameFromPorperties;
        } else {
            String enterpriseNameEntered = JOptionPane.showInputDialog(null, "Introduzca el nombre de su empresa", "Establecer parametro", JOptionPane.QUESTION_MESSAGE);
            while (enterpriseNameEntered == null) {
                enterpriseNameEntered = JOptionPane.showInputDialog(null, "Introduzca el nombre de su empresa", "Establecer parametro", JOptionPane.QUESTION_MESSAGE);
            }
            enterpriseName = enterpriseNameEntered;
            options.put("enterpriseName", enterpriseName);
            Settings.updateOptions(options);
        }
        Setup.options = options;
    }

    public static File getLastDirectoryOf(String path) {
        File lastDirectory;
        String lastDirectoryOfCuadresPath = (String) options.get(path);
        if (lastDirectoryOfCuadresPath != null) {
            lastDirectory = new File(lastDirectoryOfCuadresPath);
        } else {
            lastDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents" + System.getProperty("file.separator"));
        }
        return lastDirectory;
    }

    public static void setLastDirectory(String key, File directory) {
        options.setProperty(key, directory.getPath());
        Settings.updateOptions(options);
    }

    public static void updateLimits(int entityId) {
        ID_START_FOR_GASTOS = (int) ((entityId + 0.0d) * 10000);
        ID_START_FOR_MATERIAS_PRIMAS_COCINA = (int) ((entityId + 0.1) * 10000);
        ID_START_FOR_MATERIAS_PRIMAS_LISTAS = (int) ((entityId + 0.2) * 10000);
        ID_START_FOR_RECETAS = (int) ((entityId + 0.3) * 10000);
        ID_START_FOR_PRODUCTOS_COCINA = (int) ((entityId + 0.4) * 10000);
        ID_START_FOR_PRODUCTOS_LISTO = (int) ((entityId + 0.5) * 10000);
    }
}
