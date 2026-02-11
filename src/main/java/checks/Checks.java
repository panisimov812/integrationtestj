package checks;

import io.qameta.allure.Step;

/**
 * Централизованный класс типовых проверок.
 * Все проверки попадают в отчёт Allure как отдельные шаги с понятными сообщениями.
 * Использует {@link AssertionError} — любой тестовый фреймворк (TestNG, JUnit) корректно обработает падение.
 */
public final class Checks {

    private Checks() {
    }

    /**
     * Проверяет, что условие истинно.
     *
     * @param condition результат проверки (например, из steps.verify...)
     * @param message   сообщение при падении (отображается в Allure и в отчёте)
     */
    @Step("Проверка: {message}")
    public static void checkThat(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    /**
     * Проверяет, что строка {@code fullText} содержит подстроку {@code expected}.
     *
     * @param fullText          полный текст (например, из страницы)
     * @param expectedSubstring ожидаемая подстрока
     * @param message           описание проверки при падении
     */
    @Step("Проверка: текст содержит ожидаемое значение. {message}")
    public static void assertContains(String fullText, String expectedSubstring, String message) {
        if (fullText == null) {
            throw new AssertionError("Текст для проверки не должен быть null. " + message);
        }
        if (!fullText.contains(expectedSubstring)) {
            throw new AssertionError(
                    String.format("%s Ожидалось наличие: '%s', получено: '%s'", message, expectedSubstring, fullText)
            );
        }
    }

    /**
     * Проверяет, что элемент/состояние отображается (видимо).
     *
     * @param isDisplayed        true, если элемент отображается
     * @param elementDescription описание элемента для сообщения об ошибке
     */
    @Step("Проверка: отображается — {elementDescription}")
    public static void assertDisplayed(boolean isDisplayed, String elementDescription) {
        if (!isDisplayed) {
            throw new AssertionError("Ожидалось, что отображается: " + elementDescription);
        }
    }

    /**
     * Проверка равенства двух строк (с понятным сообщением в отчёте).
     */
    @Step("Проверка: фактическое значение совпадает с ожидаемым. {message}")
    public static void assertEquals(String actual, String expected, String message) {
        if (actual == null && expected == null) {
            return;
        }
        if (actual == null || !actual.equals(expected)) {
            throw new AssertionError(String.format("%s Ожидалось: '%s', получено: '%s'", message, expected, actual));
        }
    }

    /**
     * Проверка, что строка не пустая.
     */
    @Step("Проверка: значение не пустое. {message}")
    public static void assertNotEmpty(String value, String message) {
        if (value == null) {
            throw new AssertionError(message);
        }
        if (value.isBlank()) {
            throw new AssertionError(message + " Значение не должно быть пустым.");
        }
    }
}
