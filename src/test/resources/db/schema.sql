-- Схема для интеграционных тестов БД (PostgreSQL).
-- Используется BaseDbTest при старте Testcontainers.

CREATE TABLE IF NOT EXISTS users (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_users_email UNIQUE (email)
);

-- Индекс для поиска по email (часто используется в тестах и приложении)
CREATE INDEX IF NOT EXISTS idx_users_email ON users (email);
