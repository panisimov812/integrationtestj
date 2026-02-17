package base;

import com.codeborne.selenide.logevents.SelenideLogger;
import core.DriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.AllureUtils;

@Listeners({base.TestAllureListener.class})
public abstract class BaseTest implements IHookable {

    @BeforeClass(alwaysRun = true)
    public void globalSetUp() {
        DriverManager.configureSelenide();
        DriverManager.headlessMode(false);
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult result) {
        try {
            //Запуск реального тестового метода.
            callBack.runTestMethod(result);
        } catch (Throwable t) {
            // Скриншот в контексте теста, пока драйвер открыт и Allure знает текущий тест
            WebDriver driver = null;
            try {
                driver = DriverManager.getDriver();
            } catch (IllegalStateException ignored) {}
            byte[] bytes = AllureUtils.takeScreenshotBytes(driver);
            AllureUtils.attachScreenshotToCurrentTest("Screenshot at failure: " + result.getName(), bytes);
            throw t;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebDriver driver = null;
        try {
            driver = DriverManager.getDriver();
        } catch (IllegalStateException ignored) {
        }
        if (driver != null) {
            driver.quit();
        }
    }
}

