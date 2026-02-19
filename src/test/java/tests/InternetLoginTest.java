package tests;

import base.BaseTest;
import checks.Checks;
import commons.InternetLoginSteps;
import org.testng.annotations.Test;

/**
 * UI-тесты страницы логина The Internet (the-internet.herokuapp.com/login).
 * Проверки: валидный вход (tomsmith / SuperSecretPassword!) и невалидный пароль.
 */
public class InternetLoginTest extends BaseTest {

    private static final String VALID_USERNAME = "tomsmith";
    private static final String VALID_PASSWORD = "SuperSecretPassword!";
    private static final String INVALID_PASSWORD = "WrongPassword";

    private final InternetLoginSteps loginSteps = new InternetLoginSteps();

    @Test(description = "Успешный вход с валидным логином и паролем — переход в защищённую область")
    public void testValidLogin() {
        loginSteps
                .openLoginPage()
                .login(VALID_USERNAME, VALID_PASSWORD);

        Checks.assertDisplayed(
                loginSteps.isOnSecureArea(),
                "страница защищённой области после валидного входа (tomsmith / SuperSecretPassword!)");
    }

    @Test(description = "Невалидный пароль — отображается сообщение об ошибке")
    public void testInvalidPassword() {
        loginSteps
                .openLoginPage()
                .login(VALID_USERNAME, INVALID_PASSWORD);

        Checks.assertDisplayed(
                loginSteps.hasErrorMessage(),
                "сообщение об ошибке при неверном пароле");

        Checks.assertContains(
                loginSteps.getFlashMessageText(),
                "invalid",
                "текст сообщения об ошибке должен содержать 'invalid'");
    }
}
