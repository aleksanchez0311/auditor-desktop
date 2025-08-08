package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.crud.Mapper;
import cu.lacumbre.auditor.model.Product;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.MAIN_TABLE_HEADER_STYLE;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.TABLE1_FORMULAS_STYLE;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.TABLE1_NAMES_STYLE;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.TABLE2_NAMES_STYLE;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.getStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.DAYS_COLUMN_NAMES;
import static cu.lacumbre.auditor.view.utils.CreateCuadreFile.COLUMN_INDEXES_TRANSLATIONS;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.TreeMap;

public class FillDays {

    static void execute(Mapper mapper, XSSFWorkbook workbook, XSSFSheet sheet, int rowCount, int pageIndex, int monthValue, int year, String monthName) {
        //Crear Filas
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            if (rowIndex == 0) {
                fillHeaderRow(mapper, workbook, sheet, rowIndex, rowCount);
            } else {
                fillContentRows(mapper, workbook, sheet, rowIndex, rowCount, pageIndex, monthValue, year, monthName);
            }
        }
        //formulaEvaluator.evaluateAll();
    }

    private static void fillHeaderRow(Mapper mapper, XSSFWorkbook workbook, XSSFSheet sheet, int rowIndex, int rowCount) {
        // Crear fila de encabezados 
        Row row = sheet.createRow(rowIndex);
        // Crear y llenar celdas en la fila de encabezados
        for (int columnIndex = 0; columnIndex < DAYS_COLUMN_NAMES.length; columnIndex++) {
            Cell cell = row.createCell(columnIndex);
            switch (columnIndex) {
                case 11, 15 -> {
                    cell.setCellStyle(getStyle(workbook));
                }
                case 12 -> {
                    cell.setCellValue(DAYS_COLUMN_NAMES[columnIndex]);
                    cell.setCellStyle(getStyle(workbook, TABLE1_NAMES_STYLE));
                }
                case 14 -> {
                    String formula = createFormulaSumIfSeveral(mapper, "E", rowCount, "A", rowCount, "<>");
                    cell.setCellFormula(formula);
                    cell.setCellStyle(getStyle(workbook, TABLE1_FORMULAS_STYLE));
                }
                case 16 -> {
                    cell.setCellValue(DAYS_COLUMN_NAMES[columnIndex]);
                    cell.setCellStyle(getStyle(workbook, TABLE2_NAMES_STYLE));
                }
                case 18 -> {
                }
                default -> {
                    cell.setCellValue(DAYS_COLUMN_NAMES[columnIndex]);
                    cell.setCellStyle(getStyle(workbook, MAIN_TABLE_HEADER_STYLE));
                }

            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 12, 13)); // Combina las celdas K1 (0,10) a L1 (0,11) 
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 16, 17)); // Combina las celdas K1 (0,10) a L1 (0,11) 
    }

    private static void fillContentRows(Mapper mapper, XSSFWorkbook workbook, XSSFSheet sheet, int rowIndex, int rowCount, int pageIndex, int monthValue, int year, String monthName) {
        String monthZero = monthValue < 10 ? "0" : "";
        String formatedMonthValue = monthZero + monthValue;
        String dayZero = pageIndex < 10 ? "0" : "";
        String formatedPageIndex = dayZero + pageIndex;
        // Crear resto de filas con datos de productos 
        Row row = sheet.createRow(rowIndex);
        // Crear y llenar celdas en la fila
        for (int columnIndex = 0; columnIndex < DAYS_COLUMN_NAMES.length; columnIndex++) {
            Cell cell = row.createCell(columnIndex);
            int formulaIndex = rowIndex + 1;
            switch (columnIndex) {
                case 0 -> {
                    cell.setCellFormula("""
                                        IFERROR('https://d.docs.live.net/341ab10a171537ad/La Cumbre SURL/2 HLC/Cuadre Diario/%d/%d %s/[Cuadre %s-%s.xlsx]Cuadre caja'!$%s$7, 0)""".formatted(year, monthValue, monthName, formatedMonthValue, formatedPageIndex, COLUMN_INDEXES_TRANSLATIONS[rowIndex]));
                }
                case 1 -> {
                    cell.setCellFormula("""
                                        IFERROR('https://d.docs.live.net/341ab10a171537ad/La Cumbre SURL/2 HLC/Cuadre Diario/%d/%d %s/[Cuadre %s-%s.xlsx]Cuadre caja'!$%s$6, 0)""".formatted(year, monthValue, monthName, formatedMonthValue, formatedPageIndex, COLUMN_INDEXES_TRANSLATIONS[rowIndex]));
                }
                case 2 -> {
                LocalDate dateOfChange = LocalDate.of(2025, Month.FEBRUARY, 15);
                    if (LocalDate.of(year, monthValue, pageIndex).isBefore(dateOfChange)) {
                        cell.setCellFormula("""
                                        IFERROR('https://d.docs.live.net/341ab10a171537ad/La Cumbre SURL/2 HLC/Cuadre Diario/%d/%d %s/[Cuadre %s-%s.xlsx]Cuadre caja'!$%s$75, 0)""".formatted(year, monthValue, monthName, formatedMonthValue, formatedPageIndex, COLUMN_INDEXES_TRANSLATIONS[rowIndex]));
                    } else {
                        cell.setCellFormula("""
                                        IFERROR('https://d.docs.live.net/341ab10a171537ad/La Cumbre SURL/2 HLC/Cuadre Diario/%d/%d %s/[Cuadre %s-%s.xlsx]Cuadre caja'!$%s$86, 0)""".formatted(year, monthValue, monthName, formatedMonthValue, formatedPageIndex, COLUMN_INDEXES_TRANSLATIONS[rowIndex]));
                    }
                }
                case 3 -> {
                    double magic = (100 - EntitySelector.currentEntity.getMAmmount()) / 100;
                    cell.setCellFormula("""
                                        ROUND(C%d-(C%d * %.2f),0)""".formatted(formulaIndex, formulaIndex, magic));
                }
                case 4 ->
                    cell.setCellFormula("B" + formulaIndex + " * C" + formulaIndex);
                case 5 -> {
                    cell.setCellFormula("B" + formulaIndex + " * D" + formulaIndex);
                }
                case 6 -> {
                    cell.setCellFormula("""
                                        IFERROR('https://d.docs.live.net/341ab10a171537ad/La Cumbre SURL/2 HLC/Cuadre Diario/%d/%d %s/[Cuadre %s-%s.xlsx]Cuadre caja'!$%s$5, 0)""".formatted(year, monthValue, monthName, formatedMonthValue, formatedPageIndex, COLUMN_INDEXES_TRANSLATIONS[rowIndex]));
                }
                case 7 ->
                    cell.setCellFormula("C" + formulaIndex + " * G" + formulaIndex);
                case 8 ->
                    cell.setCellFormula("(B" + formulaIndex + " - G" + formulaIndex + ") * 80%");
                case 9 ->
                    cell.setCellFormula("C" + formulaIndex + " * I" + formulaIndex);
                case 10 ->
                    cell.setCellFormula("(B" + formulaIndex + " - G" + formulaIndex + ") * C" + formulaIndex);
                case 12 -> {
                    switch (formulaIndex) {
                        case 2 -> {
                            cell.setCellValue("Magic");
                            cell.setCellStyle(getStyle(workbook, TABLE1_NAMES_STYLE));
                        }
                        case 3 -> {
                            cell.setCellValue("Cantidad");
                            cell.setCellStyle(getStyle(workbook, TABLE1_NAMES_STYLE));
                        }
                        default -> {
                        }
                    }
                    sheet.addMergedRegion(new CellRangeAddress(formulaIndex - 1, formulaIndex - 1, 12, 13)); // Combina las celdas K1 (0,10) a L1 (0,11) 
                }
                case 14 -> {
                    switch (formulaIndex) {
                        case 2 -> {
                            String formula = createFormulaSumIfSeveral(mapper, "F", rowCount, "A", rowCount, "<>");
                            cell.setCellFormula(formula);
                            cell.setCellStyle(getStyle(workbook, TABLE1_FORMULAS_STYLE));
                        }
                        case 3 -> {
                            String formula = createFormulaCountIfSeveral(mapper, "C", rowCount, ">0", "A", rowCount, "<>");
                            cell.setCellFormula(formula);
                            cell.setCellStyle(getStyle(workbook, TABLE1_FORMULAS_STYLE));
                        }
                        default -> {
                        }
                    }
                }
                default -> {
                }
            }

        }
    }

    private static String createFormulaCountIfSeveral(Mapper mapper, String rowRangeCriteriaEnd, int columnRangeCriteriaEnd, String criteria, String rowRangeBulkEnd, int columnRangeBulkEnd, String bulk) {
        String result = """
                        COUNTIFS(%s2:%s%d, "%s" """.formatted(rowRangeCriteriaEnd, rowRangeCriteriaEnd, columnRangeCriteriaEnd, criteria);
        TreeMap<String, Product> unmappedMap = mapper.getUnmappedMap();
        for (Map.Entry<String, Product> entry : unmappedMap.entrySet()) {
            String append = """
                            , %s2:%s%d, "%s""".formatted(rowRangeBulkEnd, rowRangeBulkEnd, columnRangeBulkEnd, bulk);
            result += append;
            String key = entry.getKey();
            append = """
                     %s" """.formatted(key);
            result += append;
        }

        return result + ")";
    }

    private static String createFormulaSumIfSeveral(Mapper mapper, String rowRangeCriteriaEnd, int columnRangeCriteriaEnd, String rowRangeBulkEnd, int columnRangeBulkEnd, String bulk) {
        String result = """
                        SUMIFS(%s2:%s%d""".formatted(rowRangeCriteriaEnd, rowRangeCriteriaEnd, columnRangeCriteriaEnd);
        TreeMap<String, Product> unmappedMap = mapper.getUnmappedMap();
        for (Map.Entry<String, Product> entry : unmappedMap.entrySet()) {
            String append = """
                            , %s2:%s%d, "%s""".formatted(rowRangeBulkEnd, rowRangeBulkEnd, columnRangeBulkEnd, bulk);
            result += append;
            String key = entry.getKey();
            append = """
                     %s" """.formatted(key);
            result += append;
        }

        return result + ")";
    }
}
