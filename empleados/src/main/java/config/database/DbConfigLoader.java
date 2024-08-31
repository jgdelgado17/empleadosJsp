package config.database;

import java.io.InputStream;
import java.util.Properties;

public class DbConfigLoader {

    private static Properties properties = new Properties();

    static {
        try (InputStream input = MySqlConnectionPool.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Unable to find database.properties");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file", e);
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
