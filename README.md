## UI Automation Framework (Java 17, Gradle, Selenide, TestNG, Allure)

This project is a sample UI automation framework for the DemoQA **Text Box** page using:

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
- `src/main/java/pages` – page objects (`TextBoxPage`)
- `src/main/java/elements` – place for custom reusable UI elements
- `src/main/java/steps` – high-level step classes (`TextBoxSteps`)
- `src/test/java/base` – base test class and TestNG listeners (`BaseTest`, `TestAllureListener`)
- `src/test/java/tests` – TestNG tests (`TextBoxTest`)
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
| `./gradlew clean test` | Очистка, запуск тестов. Результаты Allure — в `build/allure-results/` | [▶ Run](command:workbench.action.tasks.runTask?%5B%22clean%20test%22%5D) |
| `./gradlew allureReport` | Запуск тестов и генерация HTML-отчёта в `build/reports/allure-report/` | [▶ Run](command:workbench.action.tasks.runTask?%5B%22allureReport%22%5D) |
| `./gradlew allureServe` | Тесты + отчёт + открытие отчёта в браузере | [▶ Run](command:workbench.action.tasks.runTask?%5B%22allureServe%22%5D) |

Отчёт после `allureReport` можно открыть вручную: **`build/reports/allure-report/index.html`**.

### How to Run Tests

1. Запустите тесты одной из команд выше. TestNG выполняет сьют из `src/test/resources/testng.xml` (в т.ч. `TextBoxTest` на `https://demoqa.com/text-box`).

2. Allure-результаты сохраняются в `build/allure-results/`. Для просмотра отчёта используйте `allureReport` или `allureServe` — отдельная установка Allure CLI не нужна.

### Configuration

Main runtime configuration is in `src/main/resources/config.properties`:

- `base.url` – base URL of the application (default: `https://demoqa.com`)
- `browser` – browser to use for Selenide (default: `chrome`)
- `timeout.ms` – default timeout in milliseconds for Selenide (default: `6000`)

You can adjust these values to fit your environment.
