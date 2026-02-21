package base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Анализатор повторов для TestNG: при неуспехе UI-теста перезапускает его 1 раз (всего 2 запуска).
 * <p>
 * <b>Когда тест дожимается:</b> TestNG вызывает retry() при любом неуспехе — и при {@link ITestResult#FAILURE}
 * (падение/исключение), и при {@link ITestResult#SKIPPED} (пропуск). Мы не различаем статус: в обоих случаях
 * даётся один повтор. Снижает ложные падения из-за сети/таймингов.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    /** Максимальное количество повторов после первого падения (1 повтор = всего 2 запуска). */
    private static final int MAX_RETRY_COUNT = 1;

    private static final Map<String, AtomicInteger> retryCountByTest = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        String key = getKey(result);
        int current = retryCountByTest.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet();
        boolean doRetry = current <= MAX_RETRY_COUNT;
        if (doRetry) {
            result.setAttribute("retryAttempt", current);
        } else {
            clearKey(result);
        }
        return doRetry;
    }

    static String getKey(ITestResult result) {
        String className = result.getTestClass() != null ? result.getTestClass().getName() : "unknown";
        String methodName = result.getMethod() != null ? result.getMethod().getMethodName() : "unknown";
        return className + "#" + methodName;
    }

    /**
     * Сбрасывает счётчик повторов для теста (вызывается из RetryListener при завершении теста).
     */
    static void clearKey(ITestResult result) {
        retryCountByTest.remove(getKey(result));
    }
}
