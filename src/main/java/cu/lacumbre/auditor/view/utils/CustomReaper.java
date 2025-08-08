/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.excelreaper.NullCellException;
import cu.lacumbre.excelreaper.Reaper;
import java.util.List;
import org.apache.poi.ss.formula.CollaboratingWorkbooksEnvironment;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.ERROR;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 *
 * @author aleks
 */
public class CustomReaper extends Reaper{
    
    public CustomReaper(List<Row> rows) {
        super(rows);
    }

    @Override
    public Object valueOf(int numRow, int numColumn) throws NullCellException, CollaboratingWorkbooksEnvironment.WorkbookNotFoundException {
            Object result = null;
            Row row = rows.get(numRow);
            if (row != null) {
                XSSFCell cel = (XSSFCell) row.getCell(numColumn);
                if (cel != null) {
                    if (cel.getColumnIndex() == numColumn) {
                        if(numRow == 2&& numColumn == 2){
//                            String excelCell = getExcelCell(numRow, numRow);
//                            String reference = cel.getReference();
//                            FormulaEvaluator evaluator = cel.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
//                            CellType evaluatedType = evaluator.evaluateFormulaCell(cel);
                        }
                        switch (cel.getCellType()) {
                            case FORMULA -> {
                                switch (cel.getCachedFormulaResultType()) {
                                    case NUMERIC ->
                                        result = cel.getNumericCellValue();
                                    case STRING ->
                                        result = cel.getStringCellValue();
                                    case BLANK ->
                                        result = "";
                                    case BOOLEAN ->
                                        result = cel.getBooleanCellValue();
                                    case ERROR ->
                                        result = cel.getErrorCellValue();
                                    default ->
                                        result = null;
                                }
                            }
                            case NUMERIC ->
                                result = cel.getNumericCellValue();
                            case STRING ->
                                result = cel.getStringCellValue();
                            case BLANK ->
                                result = "";
                            case BOOLEAN ->
                                result = cel.getBooleanCellValue();
                            case ERROR ->
                                result = cel.getErrorCellValue();
                            default ->
                                result = null;
                        }
                    }
                }
            }
            return result;
        }
    
}
