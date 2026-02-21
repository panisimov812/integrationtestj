package base;

import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Слушатель TestNG: сбрасывает счётчик повторов RetryAnalyzer только при успешном прохождении теста.
 * При FAILURE/SKIPPED не очищаем ключ — иначе счётчик обнулится до вызова retry() и повторы не ограничатся (бесконечный цикл).
 * При окончательном отказе от повтора ключ очищается в RetryAnalyzer.retry().
 */
public class RetryListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        RetryAnalyzer.clearKey(result);
    }
}
