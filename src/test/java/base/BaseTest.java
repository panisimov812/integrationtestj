package base;

import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigReader;
import core.DriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.openqa.selenium.WebDriver;

@Listeners({base.TestAllureListener.class})
public abstract class BaseTest {


    @BeforeClass(alwaysRun = true)
    public void globalSetUp() {
        DriverManager.configureSelenide();
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // Each test can rely on Selenide's lazy driver initialization via first browser interaction
        String baseUrl = ConfigReader.get("base.url");
        // baseUrl is configured in DriverManager via Configuration.baseUrl; nothing to open yet
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = null;
        try {
            driver = DriverManager.getDriver();
        } catch (IllegalStateException ignored) {
            // WebDriver was never started
        }
        if (driver != null) {
            driver.quit();
        }
    }
}

