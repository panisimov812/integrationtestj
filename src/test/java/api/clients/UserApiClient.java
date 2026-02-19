package api.clients;

import api.models.User;
import api.utils.RestAssuredConfig;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * API-клиент для эндпоинтов User (Petstore).
 * Page Object / Object Repository подход для API.
 *
 * @see io.restassured.response.Response
 */
public class UserApiClient {

    private static final String USER = "/user";
    private static final String USER_BY_USERNAME = "/user/{username}";

    private final RequestSpecification spec;

    public UserApiClient() {
        this.spec = RestAssuredConfig.defaultSpec();
    }

    public UserApiClient(RequestSpecification spec) {
        this.spec = spec;
    }

    @Step("POST /user — создание пользователя '{user.getUsername()}'")
    public Response createUser(User user) {
        return given()
                .spec(spec)
                .body(user)
                .when()
                .post(USER);
    }

    @Step("GET /user/{username} — получение пользователя по имени")
    public Response getUserByName(String username) {
        return given()
                .spec(spec)
                .pathParam("username", username)
                .when()
                .get(USER_BY_USERNAME);
    }

    @Step("PUT /user — обновление пользователя '{user.getUsername()}'")
    public Response updateUser(User user) {
        return given()
                .spec(spec)
                .body(user)
                .when()
                .put(USER);
    }
}
