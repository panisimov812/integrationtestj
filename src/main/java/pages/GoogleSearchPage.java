package pages;

import config.ConfigReader;
import io.qameta.allure.Step;
import locators.Locators;
import utils.KeyboardActions;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для главной страницы Google и поисковой строки.
 */
public class GoogleSearchPage {

    /** Имя атрибута поисковой строки на google.com */
    public static final String SEARCH_INPUT_NAME = "q";

    public GoogleSearchPage openPage() {
        open(ConfigReader.get("google.base.url"));
        return this;
    }

    @Step("Проверяем, что поисковая строка отображается")
    public boolean isSearchInputDisplayed() {
        return Locators.byName(SEARCH_INPUT_NAME).isDisplayed();
    }

    @Step("Проверяем, что поисковая строка доступна для ввода (enabled)")
    public boolean isSearchInputEnabled() {
        return Locators.byName(SEARCH_INPUT_NAME).isEnabled();
    }

    // --- Методы с имитацией нажатий клавиш ---

    @Step("Вводим поисковый запрос с клавиатуры: {query}")
    public GoogleSearchPage typeSearchQueryWithKeys(String query) {
        KeyboardActions.typeWithKeys(Locators.byName(SEARCH_INPUT_NAME), query);
        return this;
    }

    @Step("Отправляем поиск нажатием Enter")
    public GoogleSearchPage submitSearchWithEnter() {
        KeyboardActions.pressEnter(Locators.byName(SEARCH_INPUT_NAME));
        return this;
    }

    @Step("Вводим запрос с клавиатуры и отправляем Enter: {query}")
    public GoogleSearchPage typeSearchAndPressEnter(String query) {
        KeyboardActions.typeAndPressEnter(Locators.byName(SEARCH_INPUT_NAME), query);
        return this;
    }

    /** Проверяет, что открыта страница результатов поиска Google. */
    public boolean isOnSearchResultsPage() {
        String currentUrl = getWebDriver().getCurrentUrl();
        return currentUrl != null && (currentUrl.contains("/search") || currentUrl.contains("google.com/search"));
    }
}
