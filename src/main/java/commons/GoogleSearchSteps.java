package commons;

import io.qameta.allure.Step;
import pages.GoogleSearchPage;

public class GoogleSearchSteps {

    private final GoogleSearchPage googleSearchPage = new GoogleSearchPage();

    @Step("Открываем главную страницу Google (google.com)")
    public void openGoogleHome() {
        googleSearchPage.openPage();
    }

    @Step("Проверяем, что поисковая строка отображается и доступна")
    public boolean isSearchBarDisplayedAndEnabled() {
        return googleSearchPage.isSearchInputDisplayed() && googleSearchPage.isSearchInputEnabled();
    }
}
