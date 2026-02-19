package utils;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Утилита для имитации нажатий клавиш в UI-тестах.
 * Использует Selenium Actions и Keys для отправки клавиш в элементы.
 */
public final class KeyboardActions {

    private KeyboardActions() {
    }

    /**
     * Вводит текст в элемент посимвольно через sendKeys (имитация нажатий клавиш).
     */
    @Step("Вводим текст с клавиатуры: '{text}'")
    public static void typeWithKeys(SelenideElement element, String text) {
        element.click();
        element.sendKeys(text);
    }

    /**
     * Нажимает Enter в указанном элементе.
     */
    @Step("Нажимаем клавишу Enter")
    public static void pressEnter(SelenideElement element) {
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Нажимает Tab (переход к следующему элементу).
     */
    @Step("Нажимаем клавишу Tab")
    public static void pressTab(SelenideElement element) {
        element.sendKeys(Keys.TAB);
    }

    /**
     * Нажимает Escape.
     */
    @Step("Нажимаем клавишу Escape")
    public static void pressEscape(SelenideElement element) {
        element.sendKeys(Keys.ESCAPE);
    }

    /** Модификатор для «Выделить всё»: Command на Mac, Control на Windows/Linux. */
    private static Keys modifierSelectAll() {
        String os = System.getProperty("os.name", "").toLowerCase();
        return os.contains("mac") ? Keys.COMMAND : Keys.CONTROL;
    }

    /**
     * Выделяет всё содержимое в элементе (Ctrl+A / Cmd+A).
     */
    @Step("Выделяем всё (Ctrl+A / Cmd+A)")
    public static void selectAll(SelenideElement element) {
        element.click();
        Keys mod = modifierSelectAll();
        new Actions(getWebDriver())
                .keyDown(mod)
                .sendKeys("a")
                .keyUp(mod)
                .perform();
    }

    /**
     * Очищает поле с помощью Ctrl+A и Delete (Cmd+A на Mac).
     */
    @Step("Очищаем поле (Ctrl+A, Delete)")
    public static void clearWithSelectAllAndDelete(SelenideElement element) {
        element.click();
        Keys mod = modifierSelectAll();
        new Actions(getWebDriver())
                .keyDown(mod)
                .sendKeys("a")
                .keyUp(mod)
                .sendKeys(Keys.DELETE)
                .perform();
    }

    /**
     * Вводит текст и нажимает Enter (удобно для поисковых строк).
     */
    @Step("Вводим с клавиатуры '{text}' и нажимаем Enter")
    public static void typeAndPressEnter(SelenideElement element, String text) {
        element.click();
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Переводит фокус на элемент и нажимает Enter (для кнопок/сабмита).
     */
    @Step("Фокус на элемент и нажатие Enter")
    public static void focusAndPressEnter(SelenideElement element) {
        element.scrollIntoView(true);
        element.sendKeys(Keys.ENTER);
    }
}
