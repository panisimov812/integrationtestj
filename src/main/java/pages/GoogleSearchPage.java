package pages;

import config.ConfigReader;
import io.qameta.allure.Step;
import locators.Locators;

import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для главной страницы Google и поисковой строки.
 */
public class GoogleSearchPage {

    /** Имя атрибута поисковой строки на google.com */
    public static final String SEARCH_INPUT_NAME = "q";

    public void openPage() {
        open(ConfigReader.get("google.base.url"));
    }

    @Step("Проверяем, что поисковая строка отображается")
    public boolean isSearchInputDisplayed() {
        return Locators.byName(SEARCH_INPUT_NAME).isDisplayed();
    }

    @Step("Проверяем, что поисковая строка доступна для ввода (enabled)")
    public boolean isSearchInputEnabled() {
        return Locators.byName(SEARCH_INPUT_NAME).isEnabled();
    }
}
