package db.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Модель пользователя для слоя БД (таблица users).
 * Не путать с api.models.User — это DTO для Petstore API.
 * Immutable-подобная сущность: поля задаются при создании.
 */
public final class User {

    private final UUID id;
    private final String name;
    private final String email;
    private final Instant createdAt;

    public User(UUID id, String name, String email, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.email = Objects.requireNonNull(email, "email");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "', createdAt=" + createdAt + "}";
    }
}
