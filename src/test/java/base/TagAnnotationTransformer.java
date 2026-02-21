package base;

import annotations.Tag;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Подставляет в TestNG groups значения из аннотации @Tag на методе или классе.
 * Позволяет использовать @Tag("ui") вместо @Test(groups = "ui").
 */
@SuppressWarnings("rawtypes")
public class TagAnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        if (testMethod == null) {
            return;
        }
        String[] tagValues = getTagValues(testMethod, testClass);
        if (tagValues != null && tagValues.length > 0) {
            annotation.setGroups(tagValues);
        }
    }

    private static String[] getTagValues(Method method, Class<?> testClass) {
        Tag onMethod = method.getAnnotation(Tag.class);
        if (onMethod != null && onMethod.value().length > 0) {
            return onMethod.value();
        }
        Tag onClass = testClass.getAnnotation(Tag.class);
        if (onClass != null && onClass.value().length > 0) {
            return onClass.value();
        }
        return null;
    }
}
