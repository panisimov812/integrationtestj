package tests;

import base.BaseTest;
import checks.Checks;
import commons.BasicAuthSteps;
import org.testng.annotations.Test;

/**
 * UI-тесты Basic Auth на The Internet (the-internet.herokuapp.com/basic_auth).
 * Учётные данные: user и pass — admin.
 */
public class BasicAuthTest extends BaseTest {

    private static final String VALID_USER = "admin";
    private static final String VALID_PASS = "admin";
    private static final String INVALID_USER = "wrong";
    private static final String INVALID_PASS = "wrong";

    private final BasicAuthSteps basicAuthSteps = new BasicAuthSteps();

    @Test(description = "Basic Auth с валидными учётными данными (admin/admin) — отображается сообщение об успехе")
    public void testBasicAuthValidCredentials() {
        basicAuthSteps.openBasicAuthWithCredentials(VALID_USER, VALID_PASS);

        Checks.assertDisplayed(
                basicAuthSteps.isSuccessMessageDisplayed(),
                "сообщение 'Congratulations! You must have the proper credentials.' после входа с admin/admin");
    }

    @Test(description = "Basic Auth с невалидными учётными данными — сообщение об успехе не отображается")
    public void testBasicAuthInvalidCredentials() {
        basicAuthSteps.openBasicAuthWithCredentials(INVALID_USER, INVALID_PASS);

        Checks.checkThat(
                !basicAuthSteps.isSuccessMessageDisplayed(),
                "при неверных учётных данных сообщение об успешной авторизации не должно отображаться");
    }
}
