package config;

import java.sql.Connection;

public class MySqlConnectionPool {
    /**
     * Returns a connection to the database given the current configuration.
     *
     * The current configuration is given as follows:
     * <ul>
     * <li>database: empleados</li>
     * <li>username: usuario</li>
     * <li>password: usuariopassword</li>
     * <li>host: localhost</li>
     * <li>port: 3306</li>
     * <li>url: jdbc:mysql://localhost:3306/empleados</li>
     * <li>driver: com.mysql.cj.jdbc.Driver</li>
     * </ul>
     *
     * @return a connection to the database
     */
    public static Connection getConnectionConfig() {
        MySqlConnectionProperties connectionProperties = MySqlConnectionProperties.builder()
                .database("empleados")
                .username("usuario")
                .password("usuariopassword")
                .host("localhost")
                .port("3306")
                .url("jdbc:mysql://localhost:3306/empleados")
                .driver("com.mysql.cj.jdbc.Driver")
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
