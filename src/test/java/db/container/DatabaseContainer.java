package db.container;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Singleton для PostgreSQL-контейнера Testcontainers.
 * Один контейнер на весь JVM — переиспользуется между тестами (быстрее запуск).
 * Потокобезопасная инициализация через holder.
 * Класс расположен в test, т.к. зависит от testImplementation (Testcontainers).
 */
public final class DatabaseContainer {

    private static final String IMAGE = "postgres:16-alpine";

    @SuppressWarnings("resource")
    private static final PostgreSQLContainer<?> INSTANCE = new PostgreSQLContainer<>(DockerImageName.parse(IMAGE))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private static volatile boolean started = false;

    private DatabaseContainer() {
        // Singleton
    }

    /**
     * Возвращает единственный экземпляр контейнера и запускает его при первом вызове.
     */
    public static PostgreSQLContainer<?> getInstance() {
        if (!started) {
            synchronized (DatabaseContainer.class) {
                if (!started) {
                    INSTANCE.start();
                    started = true;
                }
            }
        }
        return INSTANCE;
    }

    /** JDBC URL для подключения к контейнеру. */
    public static String getJdbcUrl() {
        return getInstance().getJdbcUrl();
    }

    /** Имя пользователя БД. */
    public static String getUsername() {
        return getInstance().getUsername();
    }

    /** Пароль БД. */
    public static String getPassword() {
        return getInstance().getPassword();
    }
}
