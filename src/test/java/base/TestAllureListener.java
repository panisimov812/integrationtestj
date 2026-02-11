package base;

import core.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AllureUtils;

public class TestAllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        AllureUtils.attachText("Failed test", result.getName());
        // Скриншот при падении делается в BaseTest.tearDown() до закрытия драйвера
    }

    @Override
    public void onTestStart(ITestResult result) {
        // no-op
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = null;
        try {
            driver = DriverManager.getDriver();
        } catch (IllegalStateException ignored) {
        }
        AllureUtils.attachScreenshot("Screenshot at success: " + result.getName(), driver);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // no-op
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // no-op
    }

    @Override
    public void onStart(ITestContext context) {
        // no-op
    }

    @Override
    public void onFinish(ITestContext context) {
        // no-op
    }
}

