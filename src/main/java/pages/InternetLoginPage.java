package pages;

import config.ConfigReader;
import io.qameta.allure.Step;
import locators.Locators;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для страницы логина The Internet (the-internet.herokuapp.com/login).
 * Логин: tomsmith, пароль: SuperSecretPassword!
 */
public class InternetLoginPage {

    private static final String USERNAME_ID = "username";
    private static final String PASSWORD_ID = "password";

    public InternetLoginPage openPage() {
        open(ConfigReader.get("internet.login.url"));
        return this;
    }

    @Step("Вводим логин: {username}")
    public InternetLoginPage enterUsername(String username) {
        Locators.id(USERNAME_ID).setValue(username);
        return this;
    }

    @Step("Вводим пароль")
    public InternetLoginPage enterPassword(String password) {
        Locators.id(PASSWORD_ID).setValue(password);
        return this;
    }

    @Step("Нажимаем кнопку Login")
    public InternetLoginPage clickLogin() {
        Locators.css("button[type='submit']").click();
        return this;
    }

    @Step("Выполняем вход: логин {username}")
    public InternetLoginPage login(String username, String password) {
        enterUsername(username).enterPassword(password).clickLogin();
        return this;
    }

    /** Проверяет, что открыта страница защищённой области (успешный вход). */
    public boolean isOnSecureArea() {
        String url = getWebDriver().getCurrentUrl();
        return url != null && url.contains("/secure");
    }

    /** Текст блока с сообщением об ошибке (flash error). */
    public String getFlashMessageText() {
        return Locators.css("#flash").getText().trim();
    }

    /** Есть ли сообщение об ошибке на странице (flash с классом error). */
    public boolean hasErrorMessage() {
        return Locators.css("#flash").exists()
                && Locators.css("#flash").getAttribute("class") != null
                && Locators.css("#flash").getAttribute("class").contains("error");
    }
}
