package tests;

import annotations.Tag;
import base.BaseTest;
import checks.Checks;
import commons.GoogleSearchSteps;
import org.testng.annotations.Test;

/**
 * UI-тесты Google Search по паттерну Page Object с использованием
 * методов, имитирующих нажатия клавиш (sendKeys, Enter).
 */
public class GoogleSearchKeyboardTest extends BaseTest {

    private final GoogleSearchSteps googleSteps = new GoogleSearchSteps();

    @Tag("ui")
    @Test(description = "Ввод поискового запроса с клавиатуры и отправка по Enter — проверка перехода на страницу результатов")
    public void testGoogleSearchWithKeyboardKeys() {
        String query = "Selenide";

        googleSteps
                .openGoogleHome()
                .searchWithKeyboard(query);

        Checks.assertDisplayed(
                googleSteps.isOnSearchResultsPage(),
                "страница результатов поиска Google после ввода запроса с клавиатуры и Enter");
    }
}
