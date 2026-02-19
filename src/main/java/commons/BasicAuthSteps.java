package commons;

import io.qameta.allure.Step;
import pages.BasicAuthPage;

public class BasicAuthSteps {

    private final BasicAuthPage basicAuthPage = new BasicAuthPage();

    @Step("Открываем Basic Auth с логином {username} и паролем")
    public BasicAuthSteps openBasicAuthWithCredentials(String username, String password) {
        basicAuthPage.openPageWithAuth(username, password);
        return this;
    }

    @Step("Проверяем, что отображается сообщение об успешной авторизации")
    public boolean isSuccessMessageDisplayed() {
        return basicAuthPage.isSuccessMessageDisplayed();
    }
}
