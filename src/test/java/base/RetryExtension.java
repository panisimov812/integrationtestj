package base;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;

/**
 * JUnit 5 Extension: при падении теста перезапускает его 1 раз
 * (всего до 2 запусков). Для нестабильных API/DB-тестов из-за сети/таймингов.
 * Подключение: {@code @ExtendWith(RetryExtension.class)} на классе или глобально через
 * {@code junit-platform.properties} (junit.jupiter.extensions.autodetection.enabled=true и
 * META-INF/services/...).
 */
public class RetryExtension implements InvocationInterceptor {

    private static final int MAX_RETRY_COUNT = 1;

    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        int attempts = 0;
        Throwable lastThrowable = null;

        while (true) {
            try {
                if (attempts == 0) {
                    invocation.proceed();
                } else {
                    invokeViaReflection(invocationContext);
                }
                return;
            } catch (Throwable t) {
                lastThrowable = t;
                attempts++;
                if (attempts > MAX_RETRY_COUNT) {
                    throw lastThrowable;
                }
                String name = extensionContext.getDisplayName();
                System.err.printf("[RetryExtension] %s failed (attempt %d/%d), retrying...%n",
                        name != null && !name.isEmpty() ? name : extensionContext.getUniqueId(), attempts, MAX_RETRY_COUNT + 1);
            }
        }
    }

    private static void invokeViaReflection(ReflectiveInvocationContext<Method> invocationContext) throws Throwable {
        Method method = invocationContext.getExecutable();
        Object target = invocationContext.getTarget().orElseThrow();
        Object[] args = invocationContext.getArguments().toArray();
        try {
            method.setAccessible(true);
            method.invoke(target, args);
        } catch (java.lang.reflect.InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}
