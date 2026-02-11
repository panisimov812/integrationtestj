## UI Automation Framework (Java 17, Gradle, Selenide, TestNG, Allure)

This project is a UI automation framework for several services (DemoQA **Text Box**, **Yandex** search at ya.ru) using:

- **Java 17**
- **Gradle**
- **Selenide**
- **TestNG**
- **Allure**
- **Page Object Model (POM)**

### Project Structure

- `src/main/java/config` – configuration utilities (`ConfigReader`)
- `src/main/java/core` – driver and Selenide configuration (`DriverManager`)
- `src/main/java/utils` – helpers, Allure utilities (`AllureUtils`)
- `src/main/java/pages` – page objects (`TextBoxPage`, `YandexSearchPage`)
- `src/main/java/elements` – place for custom reusable UI elements
- `src/main/java/steps` – high-level step classes (`TextBoxSteps`, `YandexSearchSteps`)
- `src/test/java/base` – base test class and TestNG listeners (`BaseTest`, `TestAllureListener`)
- `src/test/java/tests` – TestNG tests (`TextBoxTest`, `YandexSearchTest`)
- `src/main/resources` – main configuration (`config.properties`)
- `src/test/resources` – TestNG suite and Allure configuration (`testng.xml`, `allure.properties`)

### Prerequisites

- Java 17 installed and available on `PATH`
- Gradle 7+ (or use the wrapper: `./gradlew`)
- Chrome browser installed (default configuration uses Chrome via Selenide)

### Команды (Commands)

Из корня проекта (Windows: `gradlew.bat` вместо `./gradlew`). В Cursor/VS Code можно запустить задачу по ссылке **▶ Run** (откроется терминал и выполнится команда):

| Команда | Описание | ▶ Run |
|--------|----------|--------|
| `./gradlew clean` затем `rm -rf build .gradle` | Полная очистка артефактов сборки и локального кэша Gradle. Удаляет `build/` (скомпилированные классы, отчёты) и `.gradle/` (кэш задач, конфигураций). Помогает при сбоях компиляции, «залипшем» кэше, после переключения веток. | — |
| `./gradlew clean test` | Очистка, запуск тестов. Результаты Allure — в `build/allure-results/` | [▶ Run](command:workbench.action.tasks.runTask?%5B%22clean%20test%22%5D) |
| `./gradlew allureReport` | Запуск тестов и генерация HTML-отчёта в `build/reports/allure-report/` | [▶ Run](command:workbench.action.tasks.runTask?%5B%22allureReport%22%5D) |
| `./gradlew allureServe` | Тесты + отчёт + открытие отчёта в браузере | [▶ Run](command:workbench.action.tasks.runTask?%5B%22allureServe%22%5D) |
| `./gradlew openAllureReport` | Сгенерировать отчёт и открыть **`index.html`** в браузере вручную (если allureServe не открыл) | [▶ Run](command:workbench.action.tasks.runTask?%5B%22openAllureReport%22%5D) |

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

### Несколько сервисов (архитектура)

Тесты для разных сайтов (DemoQA и Яндекс) организованы одинаково:

- **Один BaseTest** — общая настройка Selenide/Allure и tearDown.
- **Свой URL** — для второго сервиса в конфиге задаётся `yandex.base.url`; страница открывается полным URL в `YandexSearchPage.openPage()`, т.к. `Configuration.baseUrl` занят под DemoQA.
- **Свой Page / Steps / Test** на сервис: `YandexSearchPage`, `YandexSearchSteps`, `YandexSearchTest`.
- **Общие** `Locators`, `DriverManager`, `ConfigReader` — без дублирования.

При добавлении третьего сервиса: добавить свойство в `config.properties`, создать Page (с `open(ConfigReader.get("..."))`), Steps и Test, и зарегистрировать класс в `testng.xml`.

You can adjust these values to fit your environment.
