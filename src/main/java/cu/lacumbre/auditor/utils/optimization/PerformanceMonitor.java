package cu.lacumbre.auditor.utils.optimization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.poi.ss.formula.functions.T;

public class PerformanceMonitor {
    private static final Map<String, OperationMetrics> metricsMap = new ConcurrentHashMap<>();
    
    public static void recordOperation(String operationName, long startTime) {
        long duration = System.nanoTime() - startTime;
        OperationMetrics metrics = metricsMap.computeIfAbsent(operationName, k -> new OperationMetrics());
        metrics.recordExecution(duration);
    }
    
    public static <T> T measureOperation(String operationName, Supplier<T> operation) {
        long startTime = System.nanoTime();
        try {
            return operation.get();
        } finally {
            recordOperation(operationName, startTime);
        }
    }
    
    public static void runWithMetrics(String operationName, Runnable operation) {
        long startTime = System.nanoTime();
        try {
            operation.run();
        } finally {
            recordOperation(operationName, startTime);
        }
    }
    
    public static Map<String, OperationMetrics> getMetrics() {
        return metricsMap;
    }
    
    private static class OperationMetrics {
        private long totalTime = 0;
        private long count = 0;
        private long minTime = Long.MAX_VALUE;
        private long maxTime = 0;
        
        synchronized void recordExecution(long duration) {
            totalTime += duration;
            count++;
            minTime = Math.min(minTime, duration);
            maxTime = Math.max(maxTime, duration);
        }
        
        public double getAverageTimeMillis() {
            return count > 0 ? TimeUnit.NANOSECONDS.toMillis(totalTime) / (double) count : 0;
        }
        
        public long getMinTimeMillis() {
            return TimeUnit.NANOSECONDS.toMillis(minTime);
        }
        
        public long getMaxTimeMillis() {
            return TimeUnit.NANOSECONDS.toMillis(maxTime);
        }
        
        public long getCount() {
            return count;
        }
    }
}
