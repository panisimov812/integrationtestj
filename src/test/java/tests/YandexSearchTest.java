package tests;

import base.BaseTest;
import commons.YandexSearchSteps;
import checks.Checks;
import org.testng.annotations.Test;

public class YandexSearchTest extends BaseTest {

    private final YandexSearchSteps steps = new YandexSearchSteps();

    @Test(description = "Вводим запрос в поисковую строку Яндекса и проверяем переход на страницу результатов")
    public void testYandexSearch() {
        String query = "Selenide";

        steps
                .openYandexHome()
                .search(query);

        Checks.assertDisplayed(
                steps.isSearchResultsDisplayed(),
                "страница результатов поиска Яндекса после ввода запроса");
    }
}
