package core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import config.ConfigReader;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    public static void configureSelenide() {
        Configuration.browser = ConfigReader.getOrDefault("browser", "chrome");
        Configuration.baseUrl = ConfigReader.getOrDefault("base.url", "https://www.google.com");
        Configuration.timeout = Long.parseLong(ConfigReader.getOrDefault("timeout.ms", "6000"));

        Configuration.reportsFolder = "build/reports/selenide";
        Configuration.screenshots = true;
    }

    public static WebDriver getDriver() {
        return WebDriverRunner.getWebDriver();
    }

    /**
     * метод для запуска тестов в ржиме скрытого браузера
     * @param value - true для запуска в режиме скрытого браузера, false для запуска в режиме видимого браузера
     */
    public static void headlessMode(boolean value) {
        Configuration.headless = value;
    }
}

