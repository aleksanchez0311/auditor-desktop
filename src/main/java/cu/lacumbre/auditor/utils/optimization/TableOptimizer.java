package cu.lacumbre.auditor.utils.optimization;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.poi.ss.formula.functions.T;

public class TableOptimizer {
    private static final Map<JTable, TableOptimizationInfo> optimizedTables = new ConcurrentHashMap<>();
    
    public static void optimizeTable(JTable table, int pageSize) {
        TableOptimizationInfo info = new TableOptimizationInfo(pageSize);
        optimizedTables.put(table, info);
        
        // Configurar el renderizado optimizado
        table.setDefaultRenderer(Object.class, new OptimizedTableCellRenderer());
    }
    
    public static <T> void setPagedData(JTable table, List<T> data, Function<T, Object[]> rowMapper) {
        TableOptimizationInfo info = optimizedTables.get(table);
        if (info != null) {
            info.setFullData(data, rowMapper);
            updateTablePage(table, 0);
        }
    }
    
    public static void nextPage(JTable table) {
        TableOptimizationInfo info = optimizedTables.get(table);
        if (info != null && info.hasNextPage()) {
            updateTablePage(table, info.getCurrentPage() + 1);
        }
    }
    
    public static void previousPage(JTable table) {
        TableOptimizationInfo info = optimizedTables.get(table);
        if (info != null && info.getCurrentPage() > 0) {
            updateTablePage(table, info.getCurrentPage() - 1);
        }
    }
    
    private static void updateTablePage(JTable table, int page) {
        TableOptimizationInfo info = optimizedTables.get(table);
        if (info != null) {
            info.setCurrentPage(page);
            TableModel model = table.getModel();
            if (model instanceof OptimizedTableModel) {
                ((OptimizedTableModel) model).updateData(info.getCurrentPageData());
            }
        }
    }
    
    private static class TableOptimizationInfo {
        private final int pageSize;
        private List<?> fullData;
        private int currentPage;
        private Function<?, Object[]> rowMapper;
        
        public TableOptimizationInfo(int pageSize) {
            this.pageSize = pageSize;
            this.currentPage = 0;
        }
        
        public <T> void setFullData(List<T> data, Function<T, Object[]> mapper) {
            this.fullData = data;
            this.rowMapper = (Function<?, Object[]>) mapper;
            this.currentPage = 0;
        }
        
        public Object[][] getCurrentPageData() {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, fullData.size());
            
            Object[][] pageData = new Object[end - start][];
            for (int i = start; i < end; i++) {
                pageData[i - start] = ((Function<Object, Object[]>) rowMapper).apply(fullData.get(i));
            }
            
            return pageData;
        }
        
        public boolean hasNextPage() {
            return (currentPage + 1) * pageSize < fullData.size();
        }
        
        public int getCurrentPage() {
            return currentPage;
        }
        
        public void setCurrentPage(int page) {
            if (page >= 0 && page * pageSize < fullData.size()) {
                this.currentPage = page;
            }
        }
    }
}
