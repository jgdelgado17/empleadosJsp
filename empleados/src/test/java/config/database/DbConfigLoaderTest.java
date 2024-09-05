package config.database;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

class DbConfigLoaderTest {

    /**
     * Tests that the DbConfigLoader returns the correct values for the given
     * properties. This test is more of a sanity check than anything else.
     */
    @Test
    void getProperty() {
        Assertions.assertEquals("3306", DbConfigLoader.getProperty("db.port"));
    }

    /**
     * Tests that the DbConfigLoader returns null when given an empty key. This
     * test is more of a sanity check than anything else.
     */
    @Test
    void getPropertyWithEmptyKey() {
        Assertions.assertNull(DbConfigLoader.getProperty(""));
    }
}