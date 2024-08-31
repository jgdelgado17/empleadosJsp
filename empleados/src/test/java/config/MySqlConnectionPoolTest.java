package config;

import config.database.MySqlConnectionPool;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;

class MySqlConnectionPoolTest {

    @Test
    void getConnectionConfig() throws SQLException {
        Connection connection = MySqlConnectionPool.getConnectionConfig();
        Assertions.assertNotNull(connection);
        Assertions.assertTrue(connection.isValid(0));
    }
}