package tests;

import annotations.Tag;
import base.BaseTest;
import commons.YandexSearchSteps;
import checks.Checks;
import org.testng.annotations.Test;

public class YandexSearchTest extends BaseTest {

    private final YandexSearchSteps yandexSteps = new YandexSearchSteps();

    @Tag("ui")
    @Test(description = "Вводим запрос в поисковую строку Яндекса и проверяем переход на страницу результатов")
    public void testYandexSearch() {
        String query = "Selenide";

        yandexSteps
                .openYandexHome()
                .search(query);

        Checks.assertDisplayed(
                yandexSteps.isSearchResultsDisplayed(),
                "страница результатов поиска Яндекса после ввода запроса");
    }
}
