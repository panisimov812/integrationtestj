## UI + API Automation Framework (Java 17, Gradle, Selenide, TestNG, JUnit 5, Rest Assured, Allure)

This project is a UI and API automation framework:

- **UI:** **Google** и **Yandex** search (Selenide, TestNG, POM)
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

### Теги и запуск по тегам

Тесты помечаются **отдельной аннотацией `@Tag(...)`** над тестом (или классом):

| Тип   | Фреймворк | Аннотация   | Задача      | Запуск по тегу |
|-------|-----------|-------------|-------------|----------------|
| **UI**  | TestNG     | `@Tag("ui")` (своя аннотация `annotations.Tag`) | `test`, `uiTest` | `./gradlew test` или `./gradlew uiTest`; фильтр: `./gradlew test -PtestngGroups=ui` |
| **API** | JUnit 5    | `@Tag("api")` (JUnit) | `apiTest`   | `./gradlew apiTest`; свои теги: `./gradlew apiTest -PincludeTags=api,smoke` |
| **DB**  | JUnit 5    | `@Tag("db")` (JUnit)  | `dbTest`    | `./gradlew dbTest`; свои теги: `./gradlew dbTest -PincludeTags=db` |

- **UI:** над каждым тестом в `tests.*` стоит отдельная аннотация `@Tag("ui")` (класс `annotations.Tag`). Трансформер `TagAnnotationTransformer` подставляет значение в группы TestNG при запуске, в `testng.xml` в `<groups>` указано `<include name="ui"/>`. Запуск только по тегу: `./gradlew test -PtestngGroups=ui` (несколько через запятую).
- **API:** класс `UserApiTest` помечен `@Tag("api")` (JUnit 5). Задача `apiTest` по умолчанию запускает тесты с тегом `api`.
- **DB:** класс `UserRepositoryDbTest` помечен `@Tag("db")` (JUnit 5). Задача `dbTest` по умолчанию запускает тесты с тегом `db`.

Все тесты: `./gradlew allTests` (UI + API + DB).

### Команды запуска (Commands)

Из корня проекта (Windows: `gradlew.bat` вместо `./gradlew`). В VS Code можно запустить задачу по ссылке **▶ Run** (откроется терминал и выполнится команда).

#### 1) Запуск всех UI-тестов + Allure-отчёт

```bash
./gradlew test allureReport allureServe
```

Запускаются все UI-тесты (TestNG, сьют из `testng.xml`), затем генерируется Allure-отчёт и открывается в браузере.  
*Примечание:* задача `allureReport` по умолчанию запускает также API и DB тесты; отчёт будет содержать все типы тестов. Чтобы отчёт строился только по уже выполненным результатам в `build/allure-results/`, сначала выполните только `test`, затем `allureReport allureServe`.

#### 2) Запуск одного UI-теста + Allure-отчёт

```bash
# Пример: только GoogleSearchTest
./gradlew test --tests "tests.GoogleSearchTest" allureReport allureServe

# Другие классы из testng.xml:
# ./gradlew test --tests "tests.YandexSearchTest" allureReport allureServe
# ./gradlew test --tests "tests.GoogleSearchKeyboardTest" allureReport allureServe
```

#### 3) Запуск тестов по БД + Allure-отчёт

Перед запуском должен быть запущен **Docker** (см. раздел Prerequisites).

```bash
./gradlew dbTest allureReport allureServe
```

#### 4) Запуск API-тестов + Allure-отчёт

```bash
./gradlew apiTest allureReport allureServe
```

#### 5) Запуск всех тестов + Allure-отчёт

```bash
./gradlew allTests allureReport allureServe
```

Запускаются UI (TestNG), API (JUnit 5) и DB (JUnit 5 + Testcontainers), затем генерируется Allure-отчёт и открывается в браузере.

---

#### Дополнительные команды

| Команда | Описание | ▶ Run |
|--------|----------|--------|
| `./gradlew clean` затем `rm -rf build .gradle` | Полная очистка артефактов сборки и локального кэша Gradle | — |
| `./gradlew clean test` | Очистка и запуск только UI-тестов. Результаты Allure — в `build/allure-results/` | [▶ Run](command:workbench.action.tasks.runTask?%5B%22clean%20test%22%5D) |
| `./gradlew allureReport` | Запуск всех тестов (UI + API + DB) и генерация HTML-отчёта в `build/reports/allure-report/allureReport/` | [▶ Run](command:workbench.action.tasks.runTask?%5B%22allureReport%22%5D) |
| `./gradlew allureServe` | То же, что `allureReport`, плюс открытие отчёта в браузере | [▶ Run](command:workbench.action.tasks.runTask?%5B%22allureServe%22%5D) |
| `./gradlew openAllureReport` | Сгенерировать отчёт, запустить локальный HTTP-сервер и открыть отчёт в браузере (Overview, Categories загружаются). Задача блокируется до **Ctrl+C** | [▶ Run](command:workbench.action.tasks.runTask?%5B%22openAllureReport%22%5D) |
| `./gradlew clean allTests` | Очистка и запуск **всех** тестов без генерации отчёта | — |

**Если отчёт не открывается в браузере:** выполните **`./gradlew openAllureReport`** — отчёт откроется по **http://localhost:19999**. Не открывайте `index.html` по `file://`: данные (Overview, Categories) не загрузятся из-за ограничений браузера.

При запуске **`allureReport`** или **`allureServe`** отчёт генерируется **даже если тесты упали** (сборка не останавливается из-за падения тестов).

### How to Run Tests

Запустите тесты одной из команд из раздела **Команды запуска** выше. TestNG выполняет сьют из `src/test/resources/testng.xml`. Allure-результаты сохраняются в `build/allure-results/`; для просмотра отчёта используйте `allureReport` или `allureServe` — отдельная установка Allure CLI не нужна.

### Configuration

Main runtime configuration is in `src/main/resources/config.properties`:

- `base.url` – base URL for main UI (default: `https://www.google.com`)
- `yandex.base.url` – URL for Yandex tests (default: `https://ya.ru`)
- `browser` – browser to use for Selenide (default: `chrome`)
- `timeout.ms` – default timeout in milliseconds for Selenide (default: `6000`)
- `api.base.url` – base URL for Petstore API (default: `https://petstore.swagger.io/v2`)

### Несколько сервисов (архитектура)

Тесты для разных сайтов (Google и Яндекс) организованы одинаково:

- **Один BaseTest** — общая настройка Selenide/Allure и tearDown.
- **Свой URL** — для второго сервиса в конфиге задаётся `yandex.base.url`; страница открывается полным URL в `YandexSearchPage.openPage()`, т.к. `Configuration.baseUrl` занят под основной UI.
- **Свой Page / Steps / Test** на сервис: `YandexSearchPage`, `YandexSearchSteps`, `YandexSearchTest`.
- **Общие** `Locators`, `DriverManager`, `ConfigReader` — без дублирования.

При добавлении третьего сервиса: добавить свойство в `config.properties`, создать Page (с `open(ConfigReader.get("..."))`), Steps и Test, и зарегистрировать класс в `testng.xml`.

You can adjust these values to fit your environment.

### API Tests (Rest Assured + JUnit 5)

API-тесты используют подход **Page Object / Object Repository**: клиенты в `api/clients`, модели в `api/models`, конфигурация в `api/utils`. Эндпоинты: [createUser](https://petstore.swagger.io/#/user/createUser), [getUserByName](https://petstore.swagger.io/#/user/getUserByName).

Команды запуска API-тестов с Allure-отчётом — см. раздел **Команды запуска** выше (п. 4).

---

### Database Testing Example

Интеграционные тесты БД проверяют, что данные, созданные в приложении (или имитированные вызовом репозитория), корректно сохраняются и читаются из PostgreSQL. Используются **Testcontainers** и **чистый JDBC** (без Spring).

**Требование: запустите Docker перед тестами с БД**

Перед запуском `dbTest` или `allTests` обязательно **запустите Docker** (например, Docker Desktop). Если Docker не запущен или ещё не готов (только что открыли Docker Desktop), тесты с БД будут пропущены или упадут. Рекомендуется подождать 15–30 секунд после старта Docker Desktop, затем выполнить в терминале `docker ps` — если команда выполняется без ошибок, можно запускать тесты. Проверено на **Docker Desktop 4.38.0** (и совместимых версиях).

**Как запускается**

- Все тесты (в т.ч. DB) + Allure: **`./gradlew allTests allureReport allureServe`** (см. п. 5 в **Команды запуска**)
- Только DB-тесты + Allure: **`./gradlew dbTest allureReport allureServe`** (п. 3)
- Только DB-тесты без отчёта: **`./gradlew dbTest`**

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
