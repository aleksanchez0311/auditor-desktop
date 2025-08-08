package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.auditor.EntitySelector;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.DAYS_COLUMN_NAMES;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.SUMMARY_COLUMN_NAMES;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.TABLE1_NAMES_STYLE;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.getStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.LocalDate;
import org.apache.poi.ss.util.CellRangeAddress;

public class FillSummary {

    static void execute(XSSFWorkbook workbook, XSSFSheet sheet, int rowCount, int pageIndex, int monthValue, int year, String monthName) {
        //Crear Filas
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
//        for (int rowIndex = 0; rowIndex < products.size(); rowIndex++) {
            LocalDate currentDay = EntitySelector.currentEntity.getCurrentDay();
            int length = currentDay.getMonth().length(currentDay.isLeapYear());
            if (rowIndex == 0) {
                fillHeaderRow(workbook, sheet, rowIndex, length);
            } else {
                fillContentRows(workbook, sheet, rowIndex, length, pageIndex, monthValue, year, monthName);
            }
        }
        //formulaEvaluator.evaluateAll();
    }

    private static void fillHeaderRow(XSSFWorkbook workbook, XSSFSheet sheet, int rowIndex, int length) {
        // Crear fila de encabezados 
        Row row = sheet.createRow(rowIndex);
        // Crear y llenar celdas en la fila de encabezados
        for (int columnIndex = 0; columnIndex < length; columnIndex++) {
            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(columnIndex + 1);
            cell.setCellStyle(getStyle(workbook, TABLE1_NAMES_STYLE));
        }
    }

    private static void fillContentRows(XSSFWorkbook workbook, XSSFSheet sheet, int rowIndex, int length, int pageIndex, int monthValue, int year, String monthName) {
        // Crear resto de filas con datos de productos 
        Row row = sheet.createRow(rowIndex);
        // Crear y llenar celdas en la fila
        for (int columnIndex = 0; columnIndex < length; columnIndex++) {
            Cell cell = row.createCell(columnIndex);
            int formulaIndex = columnIndex + 1;
            if (rowIndex == 1 || rowIndex == 2) {
                cell.setCellFormula("'%d'!O%d".formatted(formulaIndex, rowIndex));
            } else {
                if (columnIndex == 0) {
                    int ro;
                    switch (rowIndex) {
                        case 3 -> {
                            ro = rowIndex - 1;
                            cell.setCellFormula("AVERAGE(A$%d:%s$%d)".formatted(ro, CreateCuadreFile.COLUMN_INDEXES_TRANSLATIONS[length], ro));
                        }
                        case 4 -> {
                            ro = rowIndex - 2;
                            cell.setCellFormula("MAX(A$%d:%s$%d)".formatted(ro, CreateCuadreFile.COLUMN_INDEXES_TRANSLATIONS[length], ro));
                        }
                        case 5, 6 -> {
                            ro = rowIndex - 3;
                            cell.setCellFormula("SUM(A$%d:%s$%d)".formatted(ro, CreateCuadreFile.COLUMN_INDEXES_TRANSLATIONS[length], ro));
                        }
                        default -> {
                        }
                    }
                    cell.setCellStyle(getStyle(workbook, TABLE1_NAMES_STYLE));
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, length - 1)); // Combina las celdas K1 (0,10) a L1 (0,11) 
                }
            }
        }
    }
}
