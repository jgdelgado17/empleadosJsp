package config.application;

import config.database.MySqlConnectionPool;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.Properties;

public class AppConfigLoader {

    private static Properties properties = new Properties();

    static {
        try (InputStream input = MySqlConnectionPool.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Unable to find application.properties");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    /**
     * Loads the properties from the properties file into the ServletContext.
     *
     * @param context the ServletContext to load the properties into
     */
    public static void loadPropertiesToContext(ServletContext context) {
        for (String key : properties.stringPropertyNames()) {
            context.setAttribute(key, properties.getProperty(key));
        }
    }

    /**
     * Returns the value associated with the given key from the properties file.
     * Returns null if the key is not found in the properties file.
     *
     * @param key the key to look up in the properties file
     * @return the associated value, or null if the key is not found
     */
    public static String getProperty(String key) {
        return properties != null ? (String) properties.get(key) : null;
    }
}
