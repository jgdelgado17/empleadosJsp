package dao;

import config.MySqlConnectionPool;
import lombok.Data;
import model.Employed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Data
public class EmployedDao {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public ArrayList<Employed> findAll() {

        ArrayList<Employed> employedList = new ArrayList<>();
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("SELECT * FROM empleado");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String entryDate = resultSet.getString("entry_date");
                double salary = resultSet.getDouble("salary");
                employedList.add(new Employed(id, firstName, lastName, entryDate, salary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return employedList;
    }
}
