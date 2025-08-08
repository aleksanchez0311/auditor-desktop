package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.Setup;
import cu.lacumbre.auditor.crud.ItemsCRUD;
import cu.lacumbre.auditor.crud.Mapper;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.Product;
import static cu.lacumbre.auditor.view.utils.LoadCuadre.browseCuadresDir;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Timing;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateCuadreFile{

    static final int DEFAULT_STYLE = 0;
    static final int MAIN_TABLE_HEADER_STYLE = 1;
    static final int TABLE1_NAMES_STYLE = 2;
    static final int TABLE1_FORMULAS_STYLE = 3;
    static final int TABLE2_NAMES_STYLE = 4;
    static final int TABLE2_FORMULAS_STYLE = 5;
    static final String[] DAYS_COLUMN_NAMES = new String[]{"Producto", "Precio", "Venta", "Magic", "Venta $", "Magic$", "Costo", "Costo $", "Utilidad", "Utilidad $", "Diferencia", "", "Venta Total", "", "", "", "Salario", "", ""};
    static final String[] SUMMARY_COLUMN_NAMES = new String[]{"Producto", "Precio", "Venta", "Magic", "Venta $", "Magic$", "Costo", "Costo $", "Utilidad", "Utilidad $", "Diferencia"};
    static final String[] COLUMN_INDEXES_TRANSLATIONS = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ",
        "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ",
        "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ",
        "DA", "DB", "DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ",
        "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK", "EL", "EM", "EN", "EO", "EP", "EQ", "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ",
        "FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV", "FW", "FX", "FY", "FZ",
        "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN", "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY", "GZ",
        "HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR", "HS", "HT", "HU", "HV", "HW", "HX", "HY", "HZ",
        "IA", "IB", "IC", "ID", "IE", "IF", "IG", "IH", "II", "IJ", "IK", "IL", "IM", "IN", "IO", "IP", "IQ", "IR", "IS", "IT", "IU", "IV", "IW", "IX", "IY", "IZ",
        "JA", "JB", "JC", "JD", "JE", "JF", "JG", "JH", "JI", "JJ", "JK", "JL", "JM", "JN", "JO", "JP", "JQ", "JR", "JS", "JT", "JU", "JV", "JW", "JX", "JY", "JZ",
        "KA", "KB", "KC", "KD", "KE", "KF", "KG", "KH", "KI", "KJ", "KK", "KL", "KM", "KN", "KO", "KP", "KQ", "KR", "KS", "KT", "KU", "KV", "KW", "KX", "KY", "KZ",
        "LA", "LB", "LC", "LD", "LE", "LF", "LG", "LH", "LI", "LJ", "LK", "LL", "LM", "LN", "LO", "LP", "LQ", "LR", "LS", "LT", "LU", "LV", "LW", "LX", "LY", "LZ",
        "MA", "MB", "MC", "MD", "ME", "MF", "MG", "MH", "MI", "MJ", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ",
        "NA", "NB", "NC", "ND", "NE", "NF", "NG", "NH", "NI", "NJ", "NK", "NL", "NM", "NN", "NO", "NP", "NQ", "NR", "NS", "NT", "NU", "NV", "NW", "NX", "NY", "NZ",
        "OA", "OB", "OC", "OD", "OE", "OF", "OG", "OH", "OI", "OJ", "OK", "OL", "OM", "ON", "OO", "OP", "OQ", "OR", "OS", "OT", "OU", "OV", "OW", "OX", "OY", "OZ",
        "PA", "PB", "PC", "PD", "PE", "PF", "PG", "PH", "PI", "PJ", "PK", "PL", "PM", "PN", "PO", "PP", "PQ", "PR", "PS", "PT", "PU", "PV", "PW", "PX", "PY", "PZ",
        "QA", "QB", "QC", "QD", "QE", "QF", "QG", "QH", "QI", "QJ", "QK", "QL", "QM", "QN", "QO", "QP", "QQ", "QR", "QS", "QT", "QU", "QV", "QW", "QX", "QY", "QZ",
        "RA", "RB", "RC", "RD", "RE", "RF", "RG", "RH", "RI", "RJ", "RK", "RL", "RM", "RN", "RO", "RP", "RQ", "RR", "RS", "RT", "RU", "RV", "RW", "RX", "RY", "RZ",
        "SA", "SB", "SC", "SD", "SE", "SF", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SP", "SQ", "SR", "SS", "ST", "SU", "SV", "SW", "SX", "SY", "SZ",
        "TA", "TB", "TC", "TD", "TE", "TF", "TG", "TH", "TI", "TJ", "TK", "TL", "TM", "TN", "TO", "TP", "TQ", "TR", "TS", "TT", "TU", "TV", "TW", "TX", "TY", "TZ",
        "UA", "UB", "UC", "UD", "UE", "UF", "UG", "UH", "UI", "UJ", "UK", "UL", "UM", "UN", "UO", "UP", "UQ", "UR", "US", "UT", "UU", "UV", "UW", "UX", "UY", "UZ",
        "VA", "VB", "VC", "VD", "VE", "VF", "VG", "VH", "VI", "VJ", "VK", "VL", "VM", "VN", "VO", "VP", "VQ", "VR", "VS", "VT", "VU", "VV", "VW", "VX", "VY", "VZ",
        "WA", "WB", "WC", "WD", "WE", "WF", "WG", "WH", "WI", "WJ", "WK", "WL", "WM", "WN", "WO", "WP", "WQ", "WR", "WS", "WT", "WU", "WV", "WW", "WX", "WY", "WZ",
        "XA", "XB", "XC", "XD", "XE", "XF", "XG", "XH", "XI", "XJ", "XK", "XL", "XM", "XN", "XO", "XP", "XQ", "XR", "XS", "XT", "XU", "XV", "XW", "XX", "XY", "XZ",
        "YA", "YB", "YC", "YD", "YE", "YF", "YG", "YH", "YI", "YJ", "YK", "YL", "YM", "YN", "YO", "YP", "YQ", "YR", "YS", "YT", "YU", "YV", "YW", "YX", "YY", "YZ",
        "ZA", "ZB", "ZC", "ZD", "ZE", "ZF", "ZG", "ZH", "ZI", "ZJ", "ZK", "ZL", "ZM", "ZN", "ZO", "ZP", "ZQ", "ZR", "ZS", "ZT", "ZU", "ZV", "ZW", "ZX", "ZY", "ZZ"};

    public static void execute(Connection connection, ItemsCRUD itemsCRUD) throws IOException, SQLException {
        Entity entity = EntitySelector.currentEntity;
        ArrayList<Product> products = new ArrayList<>(itemsCRUD.getList(Setup.CATEGORIA_PRODUCTO, true));
        products.sort(Comparator.comparingInt(Product::getCode));
        Timing timing = new Timing(EntitySelector.currentEntity.getCurrentDay());
        Instant instant = timing.atStartOfMonth();
        LocalDate localDate = new Timing(instant).getLocalDate();
        Month month = localDate.getMonth();
        int monthValue = month.getValue();
        String monthName = translateMonthName(month);
        int year = localDate.getYear();
        String zero = monthValue < 10 ? "0" : "";
        String fileName = entity.getAbrev() + " C" + zero + monthValue + " " + monthName + ".xlsx";
        File parent = browseCuadresDir();
        File file = new File(parent + File.separator + fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(); // Crear un nuevo libro de Excel 
//        int rowCount = products.size();
        int rowCount = 99;
        int pageIndex = 0;
        Mapper mapper = new Mapper(connection, itemsCRUD);
        for (int i = 0; i < localDate.lengthOfMonth(); i++) {
            pageIndex++;
            XSSFSheet sheet = workbook.createSheet(String.valueOf(pageIndex)); // Crear una nueva hoja de dia
            FillDays.execute(mapper, workbook, sheet, rowCount, pageIndex, monthValue, year, monthName);
        }
        pageIndex++;
        XSSFSheet sheet = workbook.createSheet("RESUMEN"); // Crear una nueva hoja RESUMEN
        FillSummary.execute(workbook, sheet, 7, pageIndex, monthValue, year, monthName);
        try {
            //Escribir el libro en un archivo 
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
            JOptionPane.showMessageDialog(null, "Archivo Excel creado exitosamente");
        } catch (IOException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }

    static CellStyle getStyle(XSSFWorkbook workbook) {
        return getStyle(workbook, DEFAULT_STYLE);
    }

    static CellStyle getStyle(XSSFWorkbook workbook, int which) {
        //Preparar estilos
        CellStyle style = workbook.createCellStyle();
        // Crear la fuente
        XSSFFont font = workbook.createFont();
        switch (which) {
            case MAIN_TABLE_HEADER_STYLE -> {
                style.setAlignment(HorizontalAlignment.CENTER);// Aliener al centro
                // Configurar el color de fondo (relleno)  
                style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                // Configurar la fuente
                font.setFontHeightInPoints((short) 14);// Tamaño de fuente
                font.setBold(true);// Negrita
                font.setColor(IndexedColors.BLACK.getIndex()); // Color de fuente
                // Aplicar la fuente al estilo de la celda  
                style.setFont(font);
            }
            case TABLE1_NAMES_STYLE, TABLE2_NAMES_STYLE -> {
                style.setAlignment(HorizontalAlignment.CENTER);// Aliener al centro
                // Configurar el color de fondo (relleno)  
                style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                // Configurar la fuente
                font.setFontHeightInPoints((short) 14);// Tamaño de fuente
                font.setBold(true);// Negrita
                font.setColor(IndexedColors.BLACK.getIndex()); // Color de fuente
                // Aplicar la fuente al estilo de la celda  
                style.setFont(font);
            }
            case TABLE1_FORMULAS_STYLE, TABLE2_FORMULAS_STYLE -> {
                // Configurar el color de fondo (relleno)  
                style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                // Configurar la fuente
                font.setFontHeightInPoints((short) 14);// Tamaño de fuente
                font.setBold(true);// Negrita
                font.setColor(IndexedColors.RED.getIndex()); // Color de fuente
                // Aplicar la fuente al estilo de la celda  
                style.setFont(font);
            }
            default -> {
                style.setVerticalAlignment(VerticalAlignment.CENTER);
            }
        }
        return style;
    }

    private static String translateMonthName(Month month) {
        String monthName = month.getDisplayName(TextStyle.FULL, Locale.getDefault());
        String firstChar = monthName.substring(0, 1).toUpperCase();
        String reminingChars = monthName.substring(1, monthName.length()).toLowerCase();
        return firstChar + reminingChars;
    }

}
