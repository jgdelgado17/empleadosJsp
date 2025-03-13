package dao;

import config.database.MySqlConnectionPool;
import model.Employed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmployedDaoTest {

    private EmployedDao employedDao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet resultSet;
    private MockedStatic<MySqlConnectionPool> mockedStatic;

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
        mockedStatic = Mockito.mockStatic(MySqlConnectionPool.class);
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
        // Close the mocked static
        mockedStatic.close();
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
        mockedStatic.when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);

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
     * Tests that the employed is not saved in the database when the database
     * throws an SQLException.
     * <p>
     * Verifies that the {@link EmployedDao#saveEmployed(Employed)} method returns 0
     * when the employed is not saved due to an SQLException.
     * <p>
     * Verifies that the resources are not closed.
     *
     * @throws SQLException if an error occurs during the test.
     */
    @Test
    void saveEmployedFailed() throws SQLException {
        // Arrange
        Employed employed = new Employed(1, "John", "Doe", Date.valueOf("2000-01-01"), 1000.0);

        // Mock the PreparedStatement to throw an SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Mock the MySqlConnectionPool to return the mockConnection
        mockedStatic.when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);

        // Act
        int result = employedDao.saveEmployed(employed);

        // Assert
        assertEquals(0, result); // The result should be 0 because the save failed

        // Verify that the resources were not closed
        verify(mockStatement, never()).executeUpdate();  // No rows were affected
        verify(mockStatement, never()).close();  // The statement was not closed
        verify(mockConnection).close();  // The connection was closed even though no rows were affected
    }

    /**
     * Tests that the {@link EmployedDao#findAllEmployees(int, int, String, String)} method
     * returns the correct list of employees when the query is successful.
     * <p>
     * Verifies that the correct SQL query is executed with the correct parameters.
     * <p>
     * Verifies that the resources are closed.
     * <p>
     * Verifies that the correct number of rows were returned.
     * <p>
     * Verifies that the correct rows were returned.
     *
     * @throws SQLException if an error occurs during the test.
     */
    @Test
    void findAllEmployeesSuccess() throws SQLException {
        //Arrange
        ArrayList<Employed> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new Employed(1, "John", "Doe", Date.valueOf("2000-01-01"), 1000.0));
        expectedEmployees.add(new Employed(2, "Jane", "Doe", Date.valueOf("2001-01-01"), 2000.0));

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(resultSet);
        //Mock the MySqlConnectionPool to return the mockConnection
        mockedStatic.when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("first_name")).thenReturn("John", "Jane");
        when(resultSet.getString("last_name")).thenReturn("Doe", "Doe");
        when(resultSet.getDate("entry_date")).thenReturn(Date.valueOf("2000-01-01"), Date.valueOf("2001-01-01"));
        when(resultSet.getDouble("salary")).thenReturn(1000.0, 2000.0);

        int page = 1;
        int rows = 2;
        String orderBy = "id";
        String direction = "ASC";

        //Act
        ArrayList<Employed> actualEmployees = employedDao.findAllEmployees(page, rows, orderBy, direction);

        //Assert
        assertEquals(expectedEmployees, actualEmployees);

        //Verify that the correct SQL query was executed
        verify(mockConnection).prepareStatement("SELECT * FROM empleado ORDER BY id ASC LIMIT ?, ?");
        verify(mockStatement).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getInt("id");
        verify(resultSet, times(2)).getString("first_name");
        verify(resultSet, times(2)).getString("last_name");
        verify(resultSet, times(2)).getDate("entry_date");
        verify(resultSet, times(2)).getDouble("salary");

        //Verify that the resources were closed
        verify(mockStatement).close();
        verify(mockConnection).close();

        //Verify that the correct number of rows were returned
        assertEquals(2, actualEmployees.size());

        //Verify that the correct rows were returned
        assertEquals(expectedEmployees.get(0), actualEmployees.get(0));
        assertEquals(expectedEmployees.get(1), actualEmployees.get(1));
    }

    /**
     * Tests that the {@link EmployedDao#findAllEmployees(int, int, String, String)} method returns an empty list
     * when the database throws an SQLException.
     * <p>
     * Verifies that the method returns an empty list when the query fails.
     * <p>
     * Verifies that the resources are not closed.
     *
     * @throws SQLException if an error occurs during the test.
     */
    @Test
    void findAllEmployeesFailed() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));
        // Mock the MySqlConnectionPool to return the mockConnection
        mockedStatic.when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);

        // Act
        int page = 1;
        int rows = 2;
        String orderBy = "id";
        String direction = "ASC";
        ArrayList<Employed> actualEmployees = employedDao.findAllEmployees(page, rows, orderBy, direction);

        // Assert
        assertEquals(0, actualEmployees.size()); // The result should be an empty list because the query failed
    }

    /**
     * Tests that the {@link EmployedDao#findEmployedById(int)} method returns the correct employee
     * when the query is successful.
     * <p>
     * Verifies that the method returns an employee with the correct fields when the query is successful.
     * <p>
     * Verifies that the correct SQL query was executed with the correct parameters.
     * <p>
     * Verifies that the resources were closed.
     *
     * @throws SQLException if an error occurs during the test.
     */
    @Test
    void findEmployedByIdSuccess() throws SQLException {
        // Arrange
        int id = 1;
        String expectedFirstName = "John";
        String expectedLastName = "Doe";
        Date expectedEntryDate = Date.valueOf("2000-01-01");
        double expectedSalary = 1000.0;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(resultSet);
        //Mock the MySqlConnectionPool to return the mockConnection
        mockedStatic.when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("first_name")).thenReturn(expectedFirstName);
        when(resultSet.getString("last_name")).thenReturn(expectedLastName);
        when(resultSet.getDate("entry_date")).thenReturn(expectedEntryDate);
        when(resultSet.getDouble("salary")).thenReturn(expectedSalary);

        // Act
        Employed actualEmployed = employedDao.findEmployedById(id);

        // Assert
        assertEquals(expectedFirstName, actualEmployed.getFirstName());
        assertEquals(expectedLastName, actualEmployed.getLastName());
        assertEquals(expectedEntryDate, actualEmployed.getEntryDate());
        assertEquals(expectedSalary, actualEmployed.getSalary());

        // Verify that the correct SQL query was executed
        verify(mockConnection).prepareStatement("SELECT * FROM empleado WHERE id = ?");
        verify(mockStatement).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet).getString("first_name");
        verify(resultSet).getString("last_name");
        verify(resultSet).getDate("entry_date");
        verify(resultSet).getDouble("salary");

        // Verify that the resources were closed
        verify(mockStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void findEmployedByIdFailed() throws SQLException {
        // Arrange
        int id = 1;
        // Mock the PreparedStatement to throw an SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));
        // Mock the MySqlConnectionPool to return the mockConnection
        mockedStatic.when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);

        // Act
        Employed actualEmployed = employedDao.findEmployedById(id);

        // Assert
        assertNull(actualEmployed);

        // Verify that the resources were not closed
        verify(mockStatement, never()).executeQuery();

        // Verify that the resources were closed
        verify(mockConnection).close();
    }

    @Test
    void searchEmployedByFirstNameOrLastNameSuccess() throws SQLException {
        // Arrange
        String name = "John";
        String expectedFirstName = "John";
        String expectedLastName = "Doe";
        Date expectedEntryDate = Date.valueOf("2000-01-01");
        double expectedSalary = 1000.0;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(resultSet);
        //Mock the MySqlConnectionPool to return the mockConnection
        mockedStatic.when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("first_name")).thenReturn(expectedFirstName);
        when(resultSet.getString("last_name")).thenReturn(expectedLastName);
        when(resultSet.getDate("entry_date")).thenReturn(expectedEntryDate);
        when(resultSet.getDouble("salary")).thenReturn(expectedSalary);

        // Act
        ArrayList<Employed> actualEmployees = employedDao.searchEmployedByFirstNameOrLastName(name);

        // Assert
        assertEquals(1, actualEmployees.size());
        assertEquals(expectedFirstName, actualEmployees.get(0).getFirstName());
        assertEquals(expectedLastName, actualEmployees.get(0).getLastName());
        assertEquals(expectedEntryDate, actualEmployees.get(0).getEntryDate());
        assertEquals(expectedSalary, actualEmployees.get(0).getSalary());

        // Verify that the correct SQL query was executed
        verify(mockConnection).prepareStatement("SELECT * FROM empleado WHERE first_name LIKE ? OR last_name LIKE ?");
        verify(mockStatement).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet).getString("first_name");
        verify(resultSet).getString("last_name");
        verify(resultSet).getDate("entry_date");
        verify(resultSet).getDouble("salary");

        // Verify that the resources were closed
        verify(mockStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void searchEmployedByFirstNameOrLastNameFailed() throws SQLException {
        // Arrange
        String name = "John";
        // Mock the PreparedStatement to throw an SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));
        // Mock the MySqlConnectionPool to return the mockConnection
        mockedStatic.when(MySqlConnectionPool::getConnection).thenReturn(mockConnection);

        // Act
        ArrayList<Employed> actualEmployees = employedDao.searchEmployedByFirstNameOrLastName(name);

        // Assert
        assertEquals(0, actualEmployees.size());

        // Verify that the resources were not closed
        verify(mockStatement, never()).executeQuery();

        // Verify that the resources were closed
        verify(mockConnection).close();
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
}