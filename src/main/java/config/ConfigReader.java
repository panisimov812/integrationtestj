package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Unable to find " + CONFIG_FILE + " on the classpath");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from " + CONFIG_FILE, e);
        }
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property '" + key + "' is not defined in " + CONFIG_FILE);
        }
        return value;
    }

    public static String getOrDefault(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }
}

