package dao;

import config.MySqlConnectionPool;
import lombok.Data;
import model.Employed;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Data
public class EmployedDao {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public int save(Employed employed) {
        int result = 0;
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("INSERT INTO empleado(first_name, last_name, entry_date, salary) VALUES(?, ?, ?, ?)");
            statement.setString(1, employed.getFirstName());
            statement.setString(2, employed.getLastName());
            statement.setDate(3, employed.getEntryDate());
            statement.setDouble(4, employed.getSalary());
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
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
        return result;
    }

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
                Date entryDate = resultSet.getDate("entry_date");
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

    public Employed findById(int id) {
        Employed employed = null;
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("SELECT * FROM empleado WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date entryDate = resultSet.getDate("entry_date");
                double salary = resultSet.getDouble("salary");
                employed = new Employed(id, firstName, lastName, entryDate, salary);
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
        return employed;
    }

    public int update(Employed employed) {
        int result = 0;
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("UPDATE empleado SET first_name = ?, last_name = ?, entry_date = ?, salary = ? WHERE id = ?");
            statement.setString(1, employed.getFirstName());
            statement.setString(2, employed.getLastName());
            statement.setDate(3, employed.getEntryDate());
            statement.setDouble(4, employed.getSalary());
            statement.setInt(5, employed.getId());
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
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
        return result;
    }

    public void deleteById(int id) {
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("DELETE FROM empleado WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
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
    }
}
