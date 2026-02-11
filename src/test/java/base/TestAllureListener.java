package base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import core.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AllureUtils;

public class TestAllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;
        try {
            driver = DriverManager.getDriver();
        } catch (IllegalStateException ignored) {
            // WebDriver may not be initialized yet
        }

        AllureUtils.attachText("Failed test", result.getName());
        AllureUtils.attachScreenshot(driver);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getName();
        Object[] params = result.getParameters();

        String startedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        StringBuilder info = new StringBuilder();
        info.append("Started at: ").append(startedAt).append("\n");
        info.append("Test: ").append(testName).append("\n");
        info.append("Class: ").append(className).append("\n");
        if (params != null && params.length > 0) {
            info.append("Parameters: ");
            for (int i = 0; i < params.length; i++) {
                if (i > 0) info.append(", ");
                info.append(params[i] != null ? params[i] : "null");
            }
        }
        AllureUtils.attachText("Test started", info.toString());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // no-op
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

