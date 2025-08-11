package cu.lacumbre.auditor.utils.optimization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.ss.formula.functions.T;

public class CacheManager<T> {
    private final Map<String, CacheEntry<T>> cache = new ConcurrentHashMap<>();
    private static final CacheManager<?> INSTANCE = new CacheManager<>();
    
    @SuppressWarnings("unchecked")
    public static <T> CacheManager<T> getInstance() {
        return (CacheManager<T>) INSTANCE;
    }
    
    public T get(String key) {
        CacheEntry<T> entry = cache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        }
        cache.remove(key);
        return null;
    }
    
    public void put(String key, T value, long ttlMillis) {
        cache.put(key, new CacheEntry<>(value, ttlMillis));
    }
    
    public void remove(String key) {
        cache.remove(key);
    }
    
    public void clear() {
        cache.clear();
    }
    
    private static class CacheEntry<T> {
        private final T value;
        private final long expiryTime;
        
        CacheEntry(T value, long ttlMillis) {
            this.value = value;
            this.expiryTime = System.currentTimeMillis() + ttlMillis;
        }
        
        T getValue() {
            return value;
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
}
