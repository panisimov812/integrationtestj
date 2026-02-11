package api.utils;

import config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Конфигурация Rest Assured: base URL, логирование, Allure-вложения.
 */
public final class RestAssuredConfig {

    private static final String BASE_URI = ConfigReader.getOrDefault("api.base.url", "https://petstore.swagger.io/v2");

    private RestAssuredConfig() {
    }

    /**
     * Спецификация по умолчанию для API-запросов: base URI, JSON, Allure-фильтр.
     */
    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    /**
     * Инициализация глобальных настроек Rest Assured (опционально).
     */
    public static void init() {
        RestAssured.requestSpecification = defaultSpec();
    }
}
