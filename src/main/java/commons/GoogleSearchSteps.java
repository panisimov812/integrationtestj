package commons;

import io.qameta.allure.Step;
import pages.GoogleSearchPage;

public class GoogleSearchSteps {

    private final GoogleSearchPage googleSearchPage = new GoogleSearchPage();

    @Step("Открываем главную страницу Google (google.com)")
    public GoogleSearchSteps openGoogleHome() {
        googleSearchPage.openPage();
        return this;
    }

    @Step("Проверяем, что поисковая строка отображается и доступна")
    public boolean isSearchBarDisplayedAndEnabled() {
        return googleSearchPage.isSearchInputDisplayed() && googleSearchPage.isSearchInputEnabled();
    }

    @Step("Вводим поисковый запрос с клавиатуры и нажимаем Enter: {query}")
    public GoogleSearchSteps searchWithKeyboard(String query) {
        googleSearchPage.typeSearchAndPressEnter(query);
        return this;
    }

    @Step("Проверяем, что открыта страница результатов поиска Google")
    public boolean isOnSearchResultsPage() {
        return googleSearchPage.isOnSearchResultsPage();
    }
}
