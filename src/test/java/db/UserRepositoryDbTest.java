package db;

import base.BaseDbTest;
import db.model.User;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тест: сохранение пользователя в БД (PostgreSQL в Testcontainers)
 * и проверка через UserRepository, что запись появилась в БД.
 * Демонстрирует сценарий «API создал пользователя → проверяем в БД» (создание имитируется репозиторием).
 * Если Docker недоступен, тесты пропускаются (SKIPPED) с сообщением в отчёте Allure.
 */
 
@DisplayName("Database: UserRepository integration tests")
@Tag("db")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryDbTest extends BaseDbTest {

    @Test
    @DisplayName("Сохранить пользователя и найти по id — запись есть в БД")
    @Description("Создание пользователя через репозиторий (имитация ответа API) и проверка чтения из БД по id.")
    @Severity(SeverityLevel.CRITICAL)
    void saveUser_thenFindById_recordExistsInDb() {
        UUID id = UUID.randomUUID();
        String name = "Integration Test User";
        String email = "integration-" + System.currentTimeMillis() + "@example.com";
        Instant createdAt = Instant.now();

        User toSave = new User(id, name, email, createdAt);

        // Шаг 1: «создание пользователя» (в реальном сценарии это сделал бы API)
        User saved = saveUserStep(toSave);

        // Шаг 2: проверка в БД через репозиторий
        User found = findUserByIdAndAssertExistsStep(id, saved);

        // Шаг 3: проверка совпадения данных
        assertUserDataMatchesStep(saved, found);
    }

    @Step("Создать пользователя в БД (имитация ответа API)")
    private User saveUserStep(User user) {
        User saved = getUserRepository().save(user);
        assertNotNull(saved, "Saved user must not be null");
        return saved;
    }

    @Step("Найти пользователя в БД по id и убедиться, что запись существует")
    private User findUserByIdAndAssertExistsStep(UUID id, User saved) {
        Optional<User> foundOpt = getUserRepository().findById(id);
        assertTrue(foundOpt.isPresent(), "User must exist in DB after save");
        return foundOpt.get();
    }

    @Step("Проверить, что данные в БД совпадают с сохранёнными")
    private void assertUserDataMatchesStep(User saved, User found) {
        assertEquals(saved.getId(), found.getId(), "id");
        assertEquals(saved.getName(), found.getName(), "name");
        assertEquals(saved.getEmail(), found.getEmail(), "email");
        assertNotNull(found.getCreatedAt(), "created_at must be set");
    }

    @Test
    @DisplayName("Сохранить пользователя и найти по email — запись есть в БД")
    @Description("Создание пользователя и поиск по email через UserRepository.")
    @Severity(SeverityLevel.NORMAL)
    void saveUser_thenFindByEmail_recordExistsInDb() {
        UUID id = UUID.randomUUID();
        String email = "by-email-" + System.currentTimeMillis() + "@example.com";
        User toSave = new User(id, "Email Test", email, Instant.now());

        getUserRepository().save(toSave);

        Optional<User> found = getUserRepository().findByEmail(email);
        assertTrue(found.isPresent(), "User must be findable by email");
        assertEquals(email, found.get().getEmail());
        assertEquals(id, found.get().getId());
    }
}
