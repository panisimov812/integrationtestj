package locators;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byXpath;

/**
 * Универсальная фабрика локаторов для Selenide.
 * Единая точка создания элементов по id, xpath, css и другим стратегиям.
 * Класс сделан final с приватным конструктором (только статические методы, экземпляры не создаются).
 * При смене стратегии поиска или добавлении обёрток (логирование, retry) меняется только этот класс.
 */
public final class Locators {

    private Locators() {
    }

    public static SelenideElement id(String id) {
        return $(byId(id));
    }

    public static SelenideElement css(String cssSelector) {
        return $(byCssSelector(cssSelector));
    }

    public static SelenideElement xpath(String xpathExpression) {
        return $(byXpath(xpathExpression));
    }

    /** Точное совпадение текста элемента. */
    public static SelenideElement byText(String text) {
        return $(Selectors.byText(text));
    }

    /** Элемент, содержащий заданный текст (подстрока). */
    public static SelenideElement withText(String text) {
        return $(Selectors.withText(text));
    }

    public static SelenideElement byClassName(String className) {
        return $(Selectors.byClassName(className));
    }

    public static SelenideElement byName(String name) {
        return $(Selectors.byName(name));
    }

    public static SelenideElement byAttribute(String attributeName, String attributeValue) {
        return $(Selectors.byAttribute(attributeName, attributeValue));
    }

    public static SelenideElement byTitle(String title) {
        return $(Selectors.byTitle(title));
    }

    public static SelenideElement byValue(String value) {
        return $(Selectors.byValue(value));
    }
}
