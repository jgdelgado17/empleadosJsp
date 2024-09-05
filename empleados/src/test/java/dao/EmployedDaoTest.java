package dao;

import config.database.MySqlConnectionPool;
import model.Employed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmployedDaoTest {

    private EmployedDao employedDao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet resultSet;

    /**
     * Sets up the test by creating a new instance of the EmployedDao and mocking
     * the necessary objects.
     */
    @BeforeEach
    void setUp() {
        employedDao = new EmployedDao();
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    /**
     * Tears down the test by setting the references to the mock objects to null.
     * This is done to ensure that the objects are garbage collected.
     */
    @AfterEach
    void tearDown() {
        // Set the references to the mock objects to null
        employedDao = null;
        mockConnection = null;
        mockStatement = null;
        resultSet = null;
    }

    /**
     * Tests that the employed is saved successfully in the database.
     * <p>
     * Verifies that the {@link EmployedDao#saveEmployed(Employed)} method returns 1
     * when the employed is saved successfully.
     * <p>
     * Verifies that the correct SQL query is executed with the correct parameters.
     * <p>
     * Verifies that the resources are closed.
     *
     * @throws SQLException if an error occurs during the test.
     */
    @Test
    void saveEmployedSuccess() throws SQLException {
        //Arrange
        Employed employed = new Employed(1, "John", "Doe", Date.valueOf("2000-01-01"), 1000.0);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        //Mock the MySqlConnectionPool to return the mockConnection
        Mockito.mockStatic(MySqlConnectionPool.class).when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);

        //Act
        int result = employedDao.saveEmployed(employed);

        //Assert
        assertEquals(1, result);

        //Verify that the correct SQL query was executed with the correct parameters
        verify(mockConnection).prepareStatement("INSERT INTO empleado(first_name, last_name, entry_date, salary) VALUES(?, ?, ?, ?)");
        verify(mockStatement).setString(1, employed.getFirstName());
        verify(mockStatement).setString(2, employed.getLastName());
        verify(mockStatement).setDate(3, employed.getEntryDate());
        verify(mockStatement).setDouble(4, employed.getSalary());
        verify(mockStatement).executeUpdate();

        //Verify that the resources were closed
        verify(mockStatement).close();
        verify(mockConnection).close();
    }

    /**
     * Tests that the {@link EmployedDao#saveEmployed(Employed)} method returns 0
     * when the employed is not saved successfully.
     * <p>
     * Verifies that the correct SQL query is executed with the correct parameters.
     * <p>
     * Verifies that the resources are closed.
     *
     * @throws SQLException if an error occurs during the test.
     */
    @Test
    void saveEmployedFailed() throws SQLException {
        //Arrange
        Employed employed = new Employed(1, "John", "Doe", Date.valueOf("2000-01-01"), 1000.0);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(0);

        //Mock the MySqlConnectionPool to return the mockConnection
        Mockito.mockStatic(MySqlConnectionPool.class).when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);

        //Act
        int result = employedDao.saveEmployed(employed);

        //Assert
        assertEquals(0, result);

        //Verify that the correct SQL query was executed
        verify(mockConnection).prepareStatement("INSERT INTO empleado(first_name, last_name, entry_date, salary) VALUES(?, ?, ?, ?)");

        //Verify that the correct parameters were set in the query
        verify(mockStatement).setString(1, employed.getFirstName());
        verify(mockStatement).setString(2, employed.getLastName());
        verify(mockStatement).setDate(3, employed.getEntryDate());
        verify(mockStatement).setDouble(4, employed.getSalary());

        //Verify that the query was executed
        verify(mockStatement).executeUpdate();

        //Verify that the resources were closed
        verify(mockStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void findAllEmployees() {
    }

    @Test
    void findEmployedById() {
    }

    @Test
    void searchEmployedByFirstNameOrLastName() {
    }

    @Test
    void updateEmployed() {
    }

    @Test
    void deleteEmployedById() {
    }

    @Test
    void countEmployees() {
    }

    @Test
    void getConnection() {
    }

    @Test
    void getStatement() {
    }

    @Test
    void getResultSet() {
    }

    @Test
    void setConnection() {
    }

    @Test
    void setStatement() {
    }

    @Test
    void setResultSet() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}