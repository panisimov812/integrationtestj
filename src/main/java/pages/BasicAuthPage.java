package pages;

import config.ConfigReader;
import io.qameta.allure.Step;
import locators.Locators;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для Basic Auth на The Internet (user/pass: admin).
 * Учётные данные передаются через URL (https://user:pass@host/path) — без CDP и доп. зависимостей.
 */
public class BasicAuthPage {

    private static final String SUCCESS_TEXT = "Congratulations! You must have the proper credentials.";

    /**
     * Открывает страницу /basic_auth с Basic Auth, передавая учётные данные в URL.
     */
    @Step("Открываем страницу Basic Auth с учётными данными: {username}")
    public BasicAuthPage openPageWithAuth(String username, String password) {
        String baseUrl = ConfigReader.get("internet.base.url");
        String hostPart = baseUrl.replaceFirst("^https?://", "");
        String encodedUser = URLEncoder.encode(username, StandardCharsets.UTF_8);
        String encodedPass = URLEncoder.encode(password, StandardCharsets.UTF_8);
        String authUrl = "https://" + encodedUser + ":" + encodedPass + "@" + hostPart + "/basic_auth";

        open(authUrl);
        return this;
    }

    /** Проверяет, что на странице отображается текст успешной авторизации. */
    @Step("Проверяем сообщение об успешной авторизации")
    public boolean isSuccessMessageDisplayed() {
        return Locators.withText(SUCCESS_TEXT).exists();
    }

    /** Возвращает текст контента страницы (для проверок). */
    public String getPageContentText() {
        return Locators.css("body").getText();
    }
}
