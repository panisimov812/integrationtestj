package db.repository;

import db.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с таблицей users (чистый JDBC, без Spring).
 * Single Responsibility: только CRUD-операции для User.
 * Зависит от DataSource (Dependency Inversion) — удобно подменять в тестах.
 */
public class UserRepository {

    private static final String INSERT = "INSERT INTO users (id, name, email, created_at) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT id, name, email, created_at FROM users WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT id, name, email, created_at FROM users WHERE email = ?";

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Сохраняет пользователя в БД.
     *
     * @param user сущность с заполненными полями (id можно сгенерировать заранее)
     * @return сохранённый user (тот же объект)
     */
    public User save(User user) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setObject(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, Timestamp.from(user.getCreatedAt()));
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user: " + user, e);
        }
    }

    /**
     * Ищет пользователя по id.
     */
    public Optional<User> findById(UUID id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by id: " + id, e);
        }
    }

    /**
     * Ищет пользователя по email.
     */
    public Optional<User> findByEmail(String email) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by email: " + email, e);
        }
    }

    private static User mapRow(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        Instant createdAt = rs.getTimestamp("created_at").toInstant();
        return new User(id, name, email, createdAt);
    }
}
