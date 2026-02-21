package base;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Подставляет RetryAnalyzer для всех UI-тестов TestNG через конфигурацию suite,
 * чтобы не указывать retryAnalyzer в каждой аннотации @Test.
 */
@SuppressWarnings("rawtypes")
public class RetryAnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        if (testMethod == null) {
            return;
        }
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
