package base;

import db.config.DatabaseConfig;
import db.container.DatabaseContainer;
import db.repository.UserRepository;
import org.junit.jupiter.api.Assumptions;
import org.testcontainers.DockerClientFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Базовый класс для интеграционных тестов БД.
 * Поднимает PostgreSQL в Testcontainers, создаёт DataSource, инициализирует схему из schema.sql,
 * предоставляет UserRepository. Закрытие соединений — при завершении JVM (контейнер останавливается).
 */
public abstract class BaseDbTest {

    private static final String SCHEMA_RESOURCE = "db/schema.sql";

    /** DataSource, созданный из контейнера. Переиспользуется в тестах. */
    protected static DataSource dataSource;
    /** Репозиторий для таблицы users. */
    protected static UserRepository userRepository;

    /**
     * Запуск контейнера, создание DataSource, применение schema.sql.
     * Вызывается один раз перед всеми тестами класса.
     */
    /** Сообщение при пропуске тестов из-за недоступности Docker (отображается в Allure Status details). */
    private static final String DOCKER_UNAVAILABLE_MESSAGE =
            "Docker недоступен. Запустите Docker Desktop (или установите Docker) и повторите запуск. "
                    + "Требуется для интеграционных тестов БД (Testcontainers + PostgreSQL).";

    /** Ожидание готовности Docker после запуска Desktop (секунды между проверками / макс. секунд). */
    private static final int DOCKER_WAIT_INTERVAL_SEC = 2;
    private static final int DOCKER_WAIT_MAX_SEC = 30;

    /**
     * Проверяет доступность Docker с повторными попытками (Docker Desktop поднимается 10–30 сек).
     */
    private static boolean waitForDocker() {
        int elapsed = 0;
        while (elapsed < DOCKER_WAIT_MAX_SEC) {
            if (DockerClientFactory.instance().isDockerAvailable()) {
                return true;
            }
            try {
                Thread.sleep(DOCKER_WAIT_INTERVAL_SEC * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            elapsed += DOCKER_WAIT_INTERVAL_SEC;
        }
        return DockerClientFactory.instance().isDockerAvailable();
    }

    @org.junit.jupiter.api.BeforeAll
    static void initDatabase() throws Exception {
        Assumptions.assumeTrue(waitForDocker(), DOCKER_UNAVAILABLE_MESSAGE);

        DatabaseConfig config = new DatabaseConfig(
                DatabaseContainer.getJdbcUrl(),
                DatabaseContainer.getUsername(),
                DatabaseContainer.getPassword()
        );
        dataSource = config.createDataSource();
        runSchema(dataSource);
        userRepository = new UserRepository(dataSource);
    }

    /**
     * Выполняет SQL-скрипт schema.sql из classpath (db/schema.sql).
     * Разбивает по ";" и выполняет каждое выражение (пустые и только комментарии пропускает).
     */
    private static void runSchema(DataSource ds) throws Exception {
        String sql;
        try (var in = BaseDbTest.class.getClassLoader().getResourceAsStream(SCHEMA_RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("Resource not found: " + SCHEMA_RESOURCE);
            }
            sql = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        }
        String[] statements = sql.split(";");
        try (Connection conn = ds.getConnection();
             Statement st = conn.createStatement()) {
            for (String raw : statements) {
                // Убираем однострочные комментарии и пустые строки, затем проверяем, что осталось непустое выражение
                String s = raw.lines()
                        .filter(line -> !line.trim().isEmpty() && !line.trim().startsWith("--"))
                        .reduce("", (a, b) -> a + " " + b)
                        .trim();
                if (s.isEmpty()) continue;
                st.execute(s);
            }
        }
    }

    protected static DataSource getDataSource() {
        return Objects.requireNonNull(dataSource, "DataSource not initialized. Use @BeforeAll initDatabase().");
    }

    protected static UserRepository getUserRepository() {
        return Objects.requireNonNull(userRepository, "UserRepository not initialized.");
    }
}
