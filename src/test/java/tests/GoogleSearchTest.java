package tests;

import base.BaseTest;
import checks.Checks;
import commons.GoogleSearchSteps;
import org.testng.annotations.Test;

public class GoogleSearchTest extends BaseTest {

    private final GoogleSearchSteps steps = new GoogleSearchSteps();

    @Test(description = "Открываем google.com и проверяем, что поисковая строка отображается и доступна для ввода")
    public void testGoogleSearchBarDisplayed() {
        steps.openGoogleHome();

        Checks.assertDisplayed(
                steps.isSearchBarDisplayedAndEnabled(),
                "поисковая строка на главной странице Google");
    }
}
