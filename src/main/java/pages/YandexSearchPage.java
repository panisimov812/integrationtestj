package pages;

import config.ConfigReader;
import io.qameta.allure.Step;
import locators.Locators;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.url;

/**
 * Page Object для главной страницы Яндекса (ya.ru) и поиска.
 * Открывает полный URL из конфига (yandex.base.url), т.к. baseUrl Selenide занят под основной UI.
 */
public class YandexSearchPage {

    /** Имя атрибута поисковой строки на ya.ru */
    public static final String SEARCH_INPUT_NAME = "text";

    public YandexSearchPage openPage() {
        open(ConfigReader.get("yandex.base.url"));
        return this;
    }

    @Step("Вводим поисковый запрос: {query}")
    public YandexSearchPage setSearchQuery(String query) {
        Locators.byName(SEARCH_INPUT_NAME).setValue(query);
        return this;
    }

    @Step("Отправляем поиск (Enter)")
    public YandexSearchPage submitSearch() {
        Locators.byName(SEARCH_INPUT_NAME).pressEnter();
        return this;
    }

    /**
     * Проверяет, что мы на странице результатов поиска (ждём перехода по URL до 15 сек).
     */
    public boolean isOnSearchResultsPage() {
        try {
            new WebDriverWait(getWebDriver(), Duration.ofSeconds(15))
                    .until(ExpectedConditions.urlContains("search"));
        } catch (Exception e) {
            return false;
        }
        String currentUrl = url();
        return currentUrl != null && currentUrl.contains("yandex") && currentUrl.contains("search");
    }

    public String getPageTitle() {
        return title();
    }
}
