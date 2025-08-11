package cu.lacumbre.auditor.utils.optimization;

import java.util.List;

public class PaginatedResult<T> {
    private final List<T> items;
    private final int totalItems;
    private final int pageSize;
    private final int currentPage;
    private final int totalPages;
    
    public PaginatedResult(List<T> items, int totalItems, int pageSize, int currentPage) {
        this.items = items;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
    }
    
    public List<T> getItems() {
        return items;
    }
    
    public int getTotalItems() {
        return totalItems;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public boolean hasNextPage() {
        return currentPage < totalPages;
    }
    
    public boolean hasPreviousPage() {
        return currentPage > 1;
    }
}
