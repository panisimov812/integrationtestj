package api;

import api.clients.UserApiClient;
import api.models.User;
import api.utils.AllureApiUtils;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * API-тесты для Petstore User: createUser и getUserByName.
 */
@Epic("Petstore API")
@Feature("User")
@DisplayName("User API Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserApiTest {

    private UserApiClient userApi;

    @BeforeAll
    void setUp() {
        userApi = new UserApiClient();
    }

    @Test
    @DisplayName("GET /user/{username} — получение пользователя по имени, проверка статуса и тела")
    @Description("Запрос пользователя user1 возвращает 200 и корректные поля в JSON.")
    @Severity(SeverityLevel.CRITICAL)
    void getUserByName_returns200_andValidBody() {
        String username = "user1";

        Response response = userApi.getUserByName(username);

        AllureApiUtils.attachBody("Response body", "application/json", response.getBody().asString());
        assertEquals(200, response.getStatusCode(), "Status code");
        assertEquals(username, response.jsonPath().getString("username"), "username");
        assertNotNull(response.jsonPath().getString("firstName"), "firstName");
        assertNotNull(response.jsonPath().get("id"), "id");
    }

    @Test
    @DisplayName("GET /user/{username} — несуществующий пользователь возвращает 404")
    @Description("Запрос несуществующего username возвращает 404.")
    @Severity(SeverityLevel.NORMAL)
    void getUserByName_notFound_returns404() {
        String username = "nonexistent_user_xyz_123";

        Response response = userApi.getUserByName(username);

        AllureApiUtils.attachBody("Response body", "application/json", response.getBody().asString());
        assertEquals(404, response.getStatusCode(), "Status code");
    }

    @Test
    @DisplayName("POST /user — создание пользователя и проверка ответа")
    @Description("Создание пользователя с JSON-телом возвращает успешный статус; затем GET по username возвращает те же данные.")
    @Severity(SeverityLevel.CRITICAL)
    void createUser_withValidBody_returnsSuccess_thenGetReturnsSameUser() {
        User user = User.builder()
                .username("testuser_" + System.currentTimeMillis())
                .firstName("Test")
                .lastName("User")
                .email("testuser@example.com")
                .password("pass123")
                .phone("+79001234567")
                .userStatus(0)
                .build();

        Response createResponse = userApi.createUser(user);
        AllureApiUtils.attachBody("Create response", "application/json", createResponse.getBody().asString());

        assertTrue(
                createResponse.getStatusCode() == 200 || createResponse.getStatusCode() == 201,
                "Create user should return 200 or 201, got " + createResponse.getStatusCode()
        );

        Response getResponse = userApi.getUserByName(user.getUsername());
        assertEquals(200, getResponse.getStatusCode(), "Get user after create");
        assertEquals(user.getUsername(), getResponse.jsonPath().getString("username"));
        assertEquals(user.getFirstName(), getResponse.jsonPath().getString("firstName"));
        assertEquals(user.getLastName(), getResponse.jsonPath().getString("lastName"));
        assertEquals(user.getEmail(), getResponse.jsonPath().getString("email"));
        assertEquals(user.getPhone(), getResponse.jsonPath().getString("phone"));
    }

    @Test
    @DisplayName("PUT /user — обновление пользователя и проверка через GET")
    @Description("Создаём пользователя, обновляем firstName/lastName/email через PUT, затем GET возвращает обновлённые данные.")
    @Severity(SeverityLevel.CRITICAL)
    void updateUser_thenGetReturnsUpdatedData() {
        String username = "upduser_" + System.currentTimeMillis();
        User created = User.builder()
                .username(username)
                .firstName("Original")
                .lastName("Name")
                .email("original@example.com")
                .password("pass123")
                .phone("+79001111111")
                .userStatus(0)
                .build();

        userApi.createUser(created);
        Response getAfterCreate = userApi.getUserByName(username);
        assertEquals(200, getAfterCreate.getStatusCode(), "User created");

        User updated = User.builder()
                .username(username)
                .firstName("Updated")
                .lastName("Surname")
                .email("updated@example.com")
                .password("pass123")
                .phone("+79002222222")
                .userStatus(1)
                .build();

        Response updateResponse = userApi.updateUser(updated);
        AllureApiUtils.attachBody("Update response", "application/json", updateResponse.getBody().asString());
        assertTrue(
                updateResponse.getStatusCode() == 200 || updateResponse.getStatusCode() == 201,
                "Update user should return 200 or 201"
        );

        Response getAfterUpdate = userApi.getUserByName(username);
        AllureApiUtils.attachBody("GET after update", "application/json", getAfterUpdate.getBody().asString());
        assertEquals(200, getAfterUpdate.getStatusCode());
        assertEquals(updated.getFirstName(), getAfterUpdate.jsonPath().getString("firstName"));
        assertEquals(updated.getLastName(), getAfterUpdate.jsonPath().getString("lastName"));
        assertEquals(updated.getEmail(), getAfterUpdate.jsonPath().getString("email"));
        assertEquals(updated.getPhone(), getAfterUpdate.jsonPath().getString("phone"));
    }
}
