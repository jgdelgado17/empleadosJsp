package config.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;

class MySqlConnectionPoolTest {

    /**
     * Tests that the connection returned by {@link MySqlConnectionPool#getConnection()} is
     * valid and not null.
     *
     * @throws SQLException if there is an error while creating the connection
     */
    @Test
    void getConnection() throws SQLException {
        Connection connection = MySqlConnectionPool.getConnection();
        Assertions.assertNotNull(connection);
        Assertions.assertTrue(connection.isValid(0));
    }

    /**
     * Tests that the connection returned by {@link MySqlConnectionPool#getConnection()} is
     * invalid after it has been closed.
     *
     * @throws SQLException if there is an error while closing the connection
     */
    @Test
    void getInvalidConnection() throws SQLException {
        Connection connection = MySqlConnectionPool.getConnection();
        connection.close();
        Assertions.assertFalse(connection.isValid(0));
    }
}