package commons;

import io.qameta.allure.Step;
import pages.YandexSearchPage;

public class YandexSearchSteps {

    private final YandexSearchPage yandexSearchPage = new YandexSearchPage();

    @Step("Открываем главную страницу Яндекса (ya.ru)")
    public YandexSearchSteps openYandexHome() {
        yandexSearchPage.openPage();
        return this;
    }

    @Step("Выполняем поиск по запросу: '{query}'")
    public YandexSearchSteps search(String query) {
        yandexSearchPage.setSearchQuery(query).submitSearch();
        return this;
    }

    @Step("Проверяем, что отображается страница результатов поиска")
    public boolean isSearchResultsDisplayed() {
        return yandexSearchPage.isOnSearchResultsPage();
    }
}
