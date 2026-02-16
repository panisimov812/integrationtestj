package db.config;

import javax.sql.DataSource;

/**
 * Конфигурация подключения к БД.
 * Инкапсулирует параметры (URL, пользователь, пароль) и создание DataSource.
 * Соответствует принципу Single Responsibility: только настройка доступа к данным.
 */
public final class DatabaseConfig {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public DatabaseConfig(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Создаёт DataSource для PostgreSQL (без пула соединений — чистый JDBC).
     * В тестах с Testcontainers одного соединения достаточно.
     */
    public DataSource createDataSource() {
        org.postgresql.ds.PGSimpleDataSource ds = new org.postgresql.ds.PGSimpleDataSource();
        ds.setURL(jdbcUrl);
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }
}
