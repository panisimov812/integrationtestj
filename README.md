## UI + API Automation Framework (Java 17, Gradle, Selenide, TestNG, JUnit 5, Rest Assured, Allure)

This project is a UI and API automation framework:

- **UI:** DemoQA **Text Box**, **Yandex** search (Selenide, TestNG, POM)
- **API:** Petstore User API (Rest Assured, JUnit 5, Page Object / Object Repository)

Stack: **Java 17**, **Gradle**, **Selenide**, **TestNG**, **JUnit 5**, **Rest Assured**, **Allure**.

### Project Structure

- `src/main/java/config` – configuration (`ConfigReader`)
- `src/main/java/core` – driver and Selenide (`DriverManager`)
- `src/main/java/utils` – helpers, Allure utilities (`AllureUtils`)
- `src/main/java/pages` – UI page objects
- **`src/main/java/api/clients`** – API clients (e.g. `UserApiClient`)
- **`src/main/java/api/models`** – request/response models (e.g. `User` with builder)
- **`src/main/java/api/utils`** – Rest Assured config, Allure API utils
- `src/test/java/base` – base test class and TestNG listeners
- `src/test/java/tests` – TestNG UI tests
- **`src/test/java/api`** – JUnit 5 API tests (`UserApiTest`)
- **`src/main/java/db/`** – Database layer (без Spring): `config/`, `model/`, `repository/`, тесты используют `db/container/` в test
- **`src/test/java/db/`** – JUnit 5 DB integration tests (`UserRepositoryDbTest`) и контейнер Testcontainers
- **`src/test/resources/db/`** – `schema.sql` для инициализации таблицы `users` в тестах
- `src/main/resources` – `config.properties` (incl. `api.base.url`)
- `src/test/resources` – TestNG suite, Allure (`testng.xml`, `allure.properties`)

### Prerequisites

- Java 17 installed and available on `PATH`
- Gradle 7+ (or use the wrapper: `./gradlew`)
- Chrome browser installed (default configuration uses Chrome via Selenide)
- **Для тестов с БД:** перед запуском `dbTest` или `allTests` должен быть запущен **Docker** (например, [Docker Desktop](https://www.docker.com/products/docker-desktop/) — проверено на версии 4.38.0). Если Docker не запущен, DB-тесты будут пропущены (SKIPPED) с сообщением в отчёте Allure.

### Команды (Commands)

Из корня проекта (Windows: `gradlew.bat` вместо `./gradlew`). В Cursor/VS Code можно запустить задачу по ссылке **▶ Run** (откроется терминал и выполнится команда):

| Команда | Описание | ▶ Run |
|--------|----------|--------|
| `./gradlew clean` затем `rm -rf build .gradle` | Полная очистка артефактов сборки и локального кэша Gradle. Удаляет `build/` (скомпилированные классы, отчёты) и `.gradle/` (кэш задач, конфигураций). Помогает при сбоях компиляции, «залипшем» кэше, после переключения веток. | — |
| `./gradlew clean test` | Очистка, запуск тестов. Результаты Allure — в `build/allure-results/` | [▶ Run](command:workbench.action.tasks.runTask?%5B%22clean%20test%22%5D) |
| `./gradlew allureReport` | Запуск тестов и генерация HTML-отчёта в `build/reports/allure-report/` | [▶ Run](command:workbench.action.tasks.runTask?%5B%22allureReport%22%5D) |
| `./gradlew allureServe` | Тесты + отчёт + открытие отчёта в браузере | [▶ Run](command:workbench.action.tasks.runTask?%5B%22allureServe%22%5D) |
| `./gradlew openAllureReport` | Сгенерировать отчёт и открыть **`index.html`** вручную (если allureServe не открыл) | [▶ Run](command:workbench.action.tasks.runTask?%5B%22openAllureReport%22%5D) |
| **Все тесты одной командой** | | |
| `./gradlew clean allTests` | Очистка и запуск **всех** тестов: UI (TestNG) + API (JUnit) + DB (JUnit + Testcontainers). Эквивалент «mvn clean test» для полного прогона. | — |
| **API-тесты** | | |
| `./gradlew apiTest` | Только API-тесты (JUnit 5 + Rest Assured): Petstore User — createUser, getUserByName | — |
| `./gradlew apiTest allureReport allureServe` | API-тесты + генерация Allure-отчёта + открытие в браузере | — |
| **DB-тесты** | | |
| `./gradlew dbTest` | Только DB-тесты (JUnit 5 + PostgreSQL Testcontainers + JDBC): сохранение/чтение пользователей | — |
| `./gradlew dbTest allureReport allureServe` | DB-тесты + генерация Allure-отчёта + открытие в браузере | — |

**Если отчёт не открывается в браузере:** выполните **`./gradlew openAllureReport`** или откройте вручную файл **`build/reports/allure-report/index.html`**.

При запуске **`allureReport`** или **`allureServe`** отчёт генерируется и открывается **в любом случае** — даже если тесты упали (сборка не останавливается из-за падения тестов).

### How to Run Tests

1. Запустите тесты одной из команд выше. TestNG выполняет сьют из `src/test/resources/testng.xml` (в т.ч. `TextBoxTest` на `https://demoqa.com/text-box`).

2. Allure-результаты сохраняются в `build/allure-results/`. Для просмотра отчёта используйте `allureReport` или `allureServe` — отдельная установка Allure CLI не нужна.

### Configuration

Main runtime configuration is in `src/main/resources/config.properties`:

- `base.url` – base URL for DemoQA (default: `https://demoqa.com`)
- `yandex.base.url` – URL for Yandex tests (default: `https://ya.ru`)
- `browser` – browser to use for Selenide (default: `chrome`)
- `timeout.ms` – default timeout in milliseconds for Selenide (default: `6000`)
- `api.base.url` – base URL for Petstore API (default: `https://petstore.swagger.io/v2`)

### Несколько сервисов (архитектура)

Тесты для разных сайтов (DemoQA и Яндекс) организованы одинаково:

- **Один BaseTest** — общая настройка Selenide/Allure и tearDown.
- **Свой URL** — для второго сервиса в конфиге задаётся `yandex.base.url`; страница открывается полным URL в `YandexSearchPage.openPage()`, т.к. `Configuration.baseUrl` занят под DemoQA.
- **Свой Page / Steps / Test** на сервис: `YandexSearchPage`, `YandexSearchSteps`, `YandexSearchTest`.
- **Общие** `Locators`, `DriverManager`, `ConfigReader` — без дублирования.

При добавлении третьего сервиса: добавить свойство в `config.properties`, создать Page (с `open(ConfigReader.get("..."))`), Steps и Test, и зарегистрировать класс в `testng.xml`.

You can adjust these values to fit your environment.

### API Tests (Rest Assured + JUnit 5)

API-тесты используют подход **Page Object / Object Repository**: клиенты в `api/clients`, модели в `api/models`, конфигурация в `api/utils`. Эндпоинты: [createUser](https://petstore.swagger.io/#/user/createUser), [getUserByName](https://petstore.swagger.io/#/user/getUserByName).

**Пример запуска API-тестов с генерацией Allure-отчёта:**

```bash
# Только запуск API-тестов
./gradlew apiTest

# Запуск API-тестов + генерация отчёта + открытие в браузере
./gradlew apiTest allureReport allureServe
```

При запуске **`allureReport`** (или **`allureServe`**) выполняются UI (TestNG), API (JUnit 5) и DB (JUnit 5) тесты; отчёт содержит все наборы. Чтобы сгенерировать отчёт только по уже запущенным тестам, выполните сначала **`apiTest`** и/или **`dbTest`**, затем **`allureReport`** и **`allureServe`** (результаты из `build/allure-results/` будут использованы).

---

### Database Testing Example

Интеграционные тесты БД проверяют, что данные, созданные в приложении (или имитированные вызовом репозитория), корректно сохраняются и читаются из PostgreSQL. Используются **Testcontainers** и **чистый JDBC** (без Spring).

**Требование: запустите Docker перед тестами с БД**

Перед запуском `dbTest` или `allTests` обязательно **запустите Docker** (например, Docker Desktop). Если Docker не запущен или ещё не готов (только что открыли Docker Desktop), тесты с БД будут пропущены или упадут. Рекомендуется подождать 15–30 секунд после старта Docker Desktop, затем выполнить в терминале `docker ps` — если команда выполняется без ошибок, можно запускать тесты. Проверено на **Docker Desktop 4.38.0** (и совместимых версиях).

**Как запускается**

- Все тесты (в т.ч. DB): **`./gradlew clean allTests`**
- Только DB-тесты: **`./gradlew dbTest`**

При первом запуске Testcontainers скачивает образ PostgreSQL (если ещё нет), поднимает контейнер, выполняет `src/test/resources/db/schema.sql` и запускает тесты. Контейнер переиспользуется между тестами (Singleton).

**Что демонстрирует**

- **BaseDbTest** — базовый класс: поднимает PostgreSQL-контейнер, создаёт `DataSource`, инициализирует схему из `schema.sql`, предоставляет `UserRepository`.
- **UserRepositoryDbTest** — пример сценария: «создание пользователя» (через репозиторий, как после ответа API) → проверка в БД через `UserRepository` (findById, findByEmail). В отчёте Allure видны шаги с аннотациями `@Step`.
- Структура пакета **db**: `container/` (Singleton контейнера), `config/` (DatabaseConfig, DataSource), `model/` (User), `repository/` (UserRepository на JDBC).

**Зачем используется Testcontainers**

- Реальная СУБД PostgreSQL вместо H2/мока — те же типы (UUID, TIMESTAMP), ограничения и индексы.
- Изолированная среда на каждый прогон: не нужен установленный PostgreSQL на машине, нет влияния на общие dev/CI-базы.
- Один контейнер на JVM (Singleton) ускоряет повторные запуски тестов.
- Подходит как шаблон для automation framework: добавьте свои таблицы в `schema.sql` и репозитории в `db/repository/`.
