package config;

import java.sql.Connection;

public class MySqlConnectionPool {

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
