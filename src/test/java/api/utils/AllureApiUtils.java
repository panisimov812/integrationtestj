package api.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

/**
 * Утилиты для интеграции API-тестов с Allure: шаги и вложения.
 */
public final class AllureApiUtils {

    private AllureApiUtils() {
    }

    @Step("Проверка статус-кода ответа: ожидается {expectedStatus}")
    public static void expectStatus(int expectedStatus, int actualStatus) {
        if (actualStatus != expectedStatus) {
            Allure.addAttachment("Ожидаемый статус", String.valueOf(expectedStatus));
            Allure.addAttachment("Фактический статус", String.valueOf(actualStatus));
        }
        assert actualStatus == expectedStatus :
                "Expected status " + expectedStatus + " but was " + actualStatus;
    }

    /**
     * Прикрепляет тело запроса/ответа к отчёту Allure.
     */
    public static void attachBody(String name, String contentType, String body) {
        if (body != null && !body.isEmpty()) {
            Allure.getLifecycle().addAttachment(name, contentType, null, body.getBytes());
        }
    }
}
