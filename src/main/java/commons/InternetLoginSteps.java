package commons;

import io.qameta.allure.Step;
import pages.InternetLoginPage;

public class InternetLoginSteps {

    private final InternetLoginPage loginPage = new InternetLoginPage();

    @Step("Открываем страницу логина The Internet")
    public InternetLoginSteps openLoginPage() {
        loginPage.openPage();
        return this;
    }

    @Step("Выполняем вход с логином {username} и указанным паролем")
    public InternetLoginSteps login(String username, String password) {
        loginPage.login(username, password);
        return this;
    }

    @Step("Проверяем, что открыта защищённая область (успешный вход)")
    public boolean isOnSecureArea() {
        return loginPage.isOnSecureArea();
    }

    @Step("Проверяем, что отображается сообщение об ошибке")
    public boolean hasErrorMessage() {
        return loginPage.hasErrorMessage();
    }

    @Step("Получаем текст сообщения (ошибка или успех)")
    public String getFlashMessageText() {
        return loginPage.getFlashMessageText();
    }
}
