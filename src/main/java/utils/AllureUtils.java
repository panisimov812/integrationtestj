package utils;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AllureUtils {

    private static final String SCREENSHOT_TYPE = "image/png";

    /**
     * Прикрепляет скриншот к отчёту Allure (при падении или по запросу).
     * Сначала пробует Selenide.screenshot(), затем драйвер — подходит для любого сценария.
     */
    @Attachment(value = "Screenshot: {0}", type = SCREENSHOT_TYPE)
    public static byte[] attachScreenshot(String name, WebDriver driver) {
        return takeScreenshotBytes(driver);
    }

    /** Скриншот с именем по умолчанию (для обратной совместимости). */
    @Attachment(value = "Screenshot", type = SCREENSHOT_TYPE)
    public static byte[] attachScreenshot(WebDriver driver) {
        return attachScreenshot("Screenshot", driver);
    }

    /**
     * Прикрепляет скриншот через Allure Lifecycle к текущему тесту.
     * Использовать при перехвате падения (IHookable и т.п.), когда контекст теста ещё активен.
     */
    public static void attachScreenshotToCurrentTest(String name, byte[] screenshotBytes) {
        if (screenshotBytes == null || screenshotBytes.length == 0) {
            return;
        }
        try {
            Allure.getLifecycle().addAttachment(name, "image/png", "png", screenshotBytes);
        } catch (Exception ignored) {
            // контекст теста уже закрыт
        }
    }

    /** Получает скриншот в байтах (без прикрепления к Allure). */
    public static byte[] takeScreenshotBytes(WebDriver driver) {
        try {
            return Selenide.screenshot(OutputType.BYTES);
        } catch (Throwable ignored) {
        }
        if (driver != null) {
            try {
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            } catch (Exception ignored) {
            }
        }
        return new byte[0];
    }

    public static void attachText(String name, String message) {
        Allure.addAttachment(name, message);
    }
}

