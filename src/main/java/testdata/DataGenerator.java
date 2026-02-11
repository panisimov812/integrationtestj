package testdata;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Генератор тестовых данных.
 * Использует только JDK, без внешних зависимостей.
 */
public class DataGenerator {

    private static final String[] NAMES = {"Иван Иванов", "Мария Петрова", "Алексей Сидоров", "Елена Козлова"};
    private static final String[] CITIES = {"Москва", "Санкт-Петербург", "Казань", "Новосибирск"};
    private static final String[] STREETS = {"ул. Ленина", "пр. Мира", "ул. Пушкина", "ул. Гагарина"};
    private static final String[] DOMAINS = {"example.com", "test.org", "mail.ru", "yandex.ru"};
    private static final String[] QUERIES = {"Selenide", "Selenium", "TestNG", "Allure", "Java"};

    private DataGenerator() {
    }

    /**
     * Уникальный email (с timestamp для избежания коллизий)
     */
    public static String generateEmail() {
        return "test_" + System.currentTimeMillis() + "@" + randomOf(DOMAINS);
    }

    /**
     * Полное имя
     */
    public static String generateFullName() {
        return randomOf(NAMES);
    }

    /**
     * Текущий адрес
     */
    public static String generateCurrentAddress() {
        return randomOf(STREETS) + ", д. " + (1 + ThreadLocalRandom.current().nextInt(100));
    }

    /**
     * Постоянный адрес
     */
    public static String generatePermanentAddress() {
        return randomOf(STREETS) + ", " + randomOf(CITIES) + ", Россия";
    }

    /**
     * Поисковый запрос из набора популярных вариантов
     */
    public static String generateSearchQuery() {
        return randomOf(QUERIES);
    }

    /**
     * Случайная строка заданной длины
     */
    public static String generateString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        var random = ThreadLocalRandom.current();
        var sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private static String randomOf(String[] arr) {
        return arr[ThreadLocalRandom.current().nextInt(arr.length)];
    }
}
