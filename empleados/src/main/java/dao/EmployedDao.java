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

    /**
     * Saves a new employee in the database.
     * @param employed the employee to be saved.
     * @return the number of rows affected.
     */
    public int saveEmployed(Employed employed) {
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
            closeResources();
        }
        return result;
    }

    /**
     * Finds all employees in the database, paginated by page and rows.
     * @param page the page to be retrieved.
     * @param rows the number of rows per page.
     * @return a list of employees.
     */
    public ArrayList<Employed> findAllEmployees(int page, int rows) {
        ArrayList<Employed> employedList = new ArrayList<>();
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("SELECT * FROM empleado LIMIT ?, ?");
            statement.setInt(1, (page - 1) * rows);
            statement.setInt(2, rows);
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
            closeResources();
        }
        return employedList;
    }

    /**
     * Finds an employee in the database by id.
     * @param id the id to be searched.
     * @return the employee with the given id, or null if not found.
     */
    public Employed findEmployedById(int id) {
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
            closeResources();
        }
        return employed;
    }

    /**
     * Finds employees in the database by first name or last name.
     * @param search the string to be searched.
     * @return a list of employees with the given first name or last name.
     */
    public ArrayList<Employed> searchEmployedByFirstNameOrLastName(String search) {
        ArrayList<Employed> employedList = new ArrayList<>();
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("SELECT * FROM empleado WHERE first_name LIKE ? OR last_name LIKE ?");
            statement.setString(1, "%" + search + "%");
            statement.setString(2, "%" + search + "%");
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
            closeResources();
        }
        return employedList;
    }

    /**
     * Updates an employee in the database.
     * @param employed the employee to be updated.
     * @return the number of rows affected.
     */
    public int updateEmployed(Employed employed) {
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
            closeResources();
        }
        return result;
    }

    /**
     * Deletes an employee from the database.
     * @param id the id of the employee to be deleted.
     * @return the number of rows affected.
     */
    public int deleteEmployedById(int id) {
        int result = 0;
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("DELETE FROM empleado WHERE id = ?");
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return result;
    }

    /**
     * Counts the number of employees in the database.
     * @return the number of employees.
     */
    public int countEmployees() {
        int result = 0;
        try {
            connection = MySqlConnectionPool.getConnectionConfig();
            statement = connection.prepareStatement("SELECT COUNT(*) FROM empleado");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return result;
    }

    /**
     * Closes the resources used in the DAO.
     * <p>
     * This method is called after every operation and closes the {@link ResultSet},
     * {@link PreparedStatement} and {@link Connection} if they are not null.
     * The resources are closed in a try-with-resources block to ensure that they are
     * closed even if an exception is thrown.
     * <p>
     * If an exception is thrown when closing the resources, it is printed to the
     * console.
     */
    private void closeResources() {
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
}
