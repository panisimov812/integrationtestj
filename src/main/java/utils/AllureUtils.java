package utils;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AllureUtils {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshot(WebDriver driver) {
        if (driver == null) {
            try {
                return Selenide.screenshot(OutputType.BYTES);
            } catch (Throwable ignored) {
                return new byte[0];
            }
        }

        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public static void attachText(String name, String message) {
        Allure.addAttachment(name, message);
    }
}

