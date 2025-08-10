package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.Mapper;
import cu.lacumbre.auditor.crud.OperationsCRUD;
import cu.lacumbre.auditor.exceptions.PriceChangedException;
import cu.lacumbre.auditor.exceptions.UnmapedProductsException;
import cu.lacumbre.auditor.model.Item;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.view.inventory.MakeSale;
import cu.lacumbre.auditor.view.inventory.MapperGestion;
import cu.lacumbre.excelreaper.NullCellException;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Timing;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.formula.CollaboratingWorkbooksEnvironment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadCuadre {

    public static final int DESCRIPTIONS_COL = 0;
    public static final int PRICES_COL = 1;
    public static final int AMMOUNTS_COL = 2;
    public static final int PROFIT_COL = 6;
    private final MakeSale makeSale;
    private final OperationsCRUD operationsCRUD;
    private final ItemsCRUD itemsCRUD;
    private final Mapper mapper;
    private final Timing timing;
    private final Connection connection;

    public LoadCuadre(Connection connection, MakeSale makeSale, OperationsCRUD operationsCRUD, ItemsCRUD itemsCRUD) throws SQLException {
        this.connection = connection;
        this.makeSale = makeSale;
        this.operationsCRUD = operationsCRUD;
        this.timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
        this.itemsCRUD = itemsCRUD;
        this.mapper = new Mapper(connection, itemsCRUD);
    }

    public static File browseCuadreFile() throws IOException {
        File result = null;
        File lastDirectoryOfCuadres = Setup.getLastDirectoryOf(Setup.CUADRES_DIRECTORY);
        JFileChooser chooser = new JFileChooser(lastDirectoryOfCuadres);
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos XLSX", "xlsx"));
        int dialogOption = chooser.showOpenDialog(null);
        if (dialogOption == JFileChooser.APPROVE_OPTION) {
            File currentFile = chooser.getSelectedFile();
            Setup.setLastDirectory(Setup.CUADRES_DIRECTORY, currentFile.getParentFile());
            if (currentFile.getPath().endsWith(".xlsx")) {
                result = currentFile;
            }
        }
        return result;
    }

    public static File browseCuadresDir() throws IOException {
        File result = null;
        File lastDirectoryOfCuadres = Setup.getLastDirectoryOf(Setup.CUADRES_DIRECTORY);
        JFileChooser chooser = new JFileChooser(lastDirectoryOfCuadres);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int dialogOption = chooser.showOpenDialog(null);
        if (dialogOption == JFileChooser.APPROVE_OPTION) {
            File currentFile = chooser.getSelectedFile();
            Setup.setLastDirectory(Setup.CUADRES_DIRECTORY, currentFile.getParentFile());
            result = currentFile;
        }
        return result;
    }

    private TreeMap<Product, Double[]> getDataFromExcel(List<Row> list) throws UnmapedProductsException, IOException, NullCellException, CollaboratingWorkbooksEnvironment.WorkbookNotFoundException {
        TreeMap<Product, Double[]> data = new TreeMap<>(Comparator.comparingInt(Item::getId));
        CustomReaper customReaper = new CustomReaper(list);
        ArrayList<String> unmappedItemsList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            if (customReaper.valueOf(i, DESCRIPTIONS_COL) instanceof String description) {
                if (description != null && !description.equals("")) {
                    Product product = (Product) mapper.get(description.strip());
                    if (product == null) {
                        unmappedItemsList.add(description.strip());
                    } else if (product.getId() != 0) {//OJITO
                        double ammount = customReaper.valueOf(i, AMMOUNTS_COL) instanceof Double ? (double) customReaper.valueOf(i, AMMOUNTS_COL) : 0.0d;
                        double price = customReaper.valueOf(i, PRICES_COL) instanceof Double ? (double) customReaper.valueOf(i, PRICES_COL) : 0.0d;
                        if (ammount > 0) {
                            data.put(product, new Double[]{ammount, price, 0.0d});
                        }
                    }
                }
            }
        }
        if (!unmappedItemsList.isEmpty()) {
            String message = "Producto(s) nuevo(s):\n";
            for (String item : unmappedItemsList) {
                message += item + "\n";
            }
            message += "Debe mapearlo(s) antes de continuar."; // TODO agregar forma automatica de mapear los nuevos porductos
            throw new UnmapedProductsException(message, unmappedItemsList);
        }
        return data;
    }

    public void loadCuadre() throws NullCellException, CollaboratingWorkbooksEnvironment.WorkbookNotFoundException, SQLException {
        ArrayList<Map.Entry<Product, Double[]>> diferentPricesList = new ArrayList<>();
        try {
            File file;
            if (makeSale.getSelectedFile() != null) {
                file = makeSale.getSelectedFile();
            } else {
                file = browseCuadreFile();
            }
            Path sourcePath = Paths.get(file.getPath());
            Path tempPath = Files.createTempFile("tempFile", ".xlsx");
            Files.copy(sourcePath, tempPath, StandardCopyOption.REPLACE_EXISTING);
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(tempPath.toFile()), true);
            XSSFSheet sheet = wb.getSheetAt(timing.getDay() - 1);
            List<Row> list = new ArrayList<>();
            for (Row row : sheet) {
                list.add((XSSFRow) row);
            }

            TreeMap<Product, Double[]> data = getDataFromExcel(list);
            for (int i = 1; i < list.size(); i++) {
//                Cell cellKiosko = wb.getSheet("3 Kiosko").getRow(i).getCell(2); // C94
                //   Cell cellBar = wb.getSheet("3 Bar").getRow(i).getCell(2); // C94
//                Cell cell = wb.getSheet("3").getRow(i).getCell(2); // C94
                //  System.out.println("Kiosko C" + i + " Type: " + cellKiosko.getCellType());
                //  System.out.println("Kiosko C" + i + " Value: " + cellKiosko.toString()); // Muestra el valor visible
                //  System.out.println("Bar C" + i + " Type: " + cellBar.getCellType());
                //   System.out.println("Bar C" + i + " Value: " + cellBar.toString()); // Muestra el valor visible
                //   System.out.println("C" + i + " Type: " + cell.getCellType());
                //   System.out.println("C" + i + " Value: " + cell); // Muestra el valor visible
            }

            if (data != null) {
                for (Map.Entry<Product, Double[]> entry : data.entrySet()) {
                    Double[] excelValues = entry.getValue();
                    double productPrice = entry.getKey().getPrice();
                    if (entry.getKey().isCompuesto()) {
                        if (productPrice - entry.getKey().getCompuesto().getPrice() != excelValues[1]) {
                            diferentPricesList.add(entry);
                        }
                    } else {
                        if (productPrice != excelValues[1]) {
                            diferentPricesList.add(entry);
                        }
                    }
                }
                makeSale.getChecker().setSelectedItems(data);
                makeSale.getBtnLoadCuadre().setEnabled(false);
                String message = "Listado de operaciones de entrada/salida importado correctamente.";
                if (!diferentPricesList.isEmpty()) {
                    message += " Pero el(los) producto(s)\n";
                    for (Map.Entry<Product, Double[]> record : diferentPricesList) {
                        message += record.getKey().toString() + " (Anterior " + ((Product) record.getKey()).getPrice() + " -> Importado: " + record.getValue()[1] + ")" + "\n";
                    }
                    message += " tiene(n) cambio de precio. Debe ajustarlo(s) antes de continuar."; // TODO agregar forma automatica de actualizar los precios
                    throw new PriceChangedException(message, diferentPricesList);
                }
                makeSale.setSelectedFile(file);
                JOptionPane.showMessageDialog(makeSale, message, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            Logger.getInstance().showError("El archivo elegido no parece un cuadre de Auditor", ex);
            makeSale.setSelectedFile(null);
        } catch (IOException ex) {
            Logger.getInstance().showError("Error abriendo el archivo.", ex);
            makeSale.setSelectedFile(null);
        } catch (UnmapedProductsException ex) {
            Logger.getInstance().updateInfoLog(ex.getMessage());
            JOptionPane.showMessageDialog(makeSale, ex.getMessage() + "\nDebe solucionarlo ahora.", "Información", JOptionPane.INFORMATION_MESSAGE);
            MapperGestion dialogMakeCurrentsDaySaleMap = new MapperGestion((JFrame) makeSale.getParent(), makeSale.isModal(), connection, makeSale, operationsCRUD, itemsCRUD, ex.getUnmappedItemsList());
            dialogMakeCurrentsDaySaleMap.setLocationRelativeTo(null);
            dialogMakeCurrentsDaySaleMap.setVisible(true);
        } catch (PriceChangedException ex) {
            Logger.getInstance().updateInfoLog(ex.getMessage());
            int showConfirmDialog = JOptionPane.showConfirmDialog(makeSale, ex.getMessage() + "\nDebe solucionarlo ahora.", "Información", JOptionPane.INFORMATION_MESSAGE);
            try {
                if (showConfirmDialog == JOptionPane.OK_OPTION) {
                    itemsCRUD.getItemProductsCRUD().updatePrices(ex.getDiferentPricesList());
                }
            } catch (SQLException sqlEx) {
                Logger.getInstance().updateErrorLog(sqlEx);
            }
        }
    }
}
