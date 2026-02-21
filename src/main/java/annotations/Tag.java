package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Отдельная аннотация для разметки тестов по типу (ui, api, db).
 * Используется над классом или методом теста.
 * В TestNG значение попадает в groups через TagAnnotationTransformer.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Tag {
    String[] value();
}
