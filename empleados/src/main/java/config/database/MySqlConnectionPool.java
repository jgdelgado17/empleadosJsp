package config.database;

import java.sql.Connection;

public class MySqlConnectionPool {

    /**
     * Returns a connection to the database using the properties defined in
     * the application.properties file.
     *
     * @return a connection to the database
     */
    public static Connection getConnection() {
        MySqlConnectionProperties connectionProperties = MySqlConnectionProperties.builder()
                .database(DbConfigLoader.getProperty("db.database"))
                .username(DbConfigLoader.getProperty("db.username"))
                .password(DbConfigLoader.getProperty("db.password"))
                .host(DbConfigLoader.getProperty("db.host"))
                .port(DbConfigLoader.getProperty("db.port"))
                .url(DbConfigLoader.getProperty("db.url"))
                .driver(DbConfigLoader.getProperty("db.driver"))
                .build();
        return buildConnectionConfiguration(connectionProperties);
    }

    /**
     * Builds a connection configuration given a set of properties.
     *
     * <p>
     * This method takes a set of properties as an argument and uses them to
     * create a connection to a database. The properties are used to:
     * <ul>
     * <li>load the driver</li>
     * <li>get the connection</li>
     * </ul>
     *
     * @param connectionProperties the set of properties to use to build the
     *                              connection configuration
     *
     * @return a connection to the database
     */
    private static Connection buildConnectionConfiguration(MySqlConnectionProperties connectionProperties) {
        Connection connection = null;
        try {
            Class.forName(connectionProperties.getDriver());
            connection = java.sql.DriverManager.getConnection(
                    connectionProperties.getUrl(),
                    connectionProperties.getUsername(),
                    connectionProperties.getPassword());
            System.out.println("Connection established successfully with database " + connectionProperties.getDatabase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
