package utilTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.pahanaedu.util.DbConnection;

public class DbConnectionTest {

	@Test
    public void testGetInstanceNotNull() {
        // Ensure that the singleton instance is created and not null.
        DbConnection instance = DbConnection.getInstance();
        assertNotNull("The DbConnection instance should not be null", instance);
    }

    @Test
    public void testGetConnectionNotNull() {
        // Check that a valid connection is returned.
        DbConnection instance = DbConnection.getInstance();
        Connection connection = instance.getConnection();
        assertNotNull("The Connection should not be null", connection);
    }

    @Test
    public void testSingletonBehavior() {
        // Ensure that multiple calls to getInstance() return the same instance.
        DbConnection instance1 = DbConnection.getInstance();
        DbConnection instance2 = DbConnection.getInstance();
        assertSame("Both instances should be identical", instance1, instance2);
    }

    @Test
    public void testConnectionValidity() throws SQLException {
        // Use the connection's isValid() method to verify the connection.
        DbConnection instance = DbConnection.getInstance();
        Connection connection = instance.getConnection();
        assertTrue("The connection should be valid", connection.isValid(2));
    }

    @Test
    public void testExecuteSimpleQuery() throws SQLException {
        // Execute a simple query to verify that the connection can interact with the database.
        DbConnection instance = DbConnection.getInstance();
        Connection connection = instance.getConnection();
        try (Statement stmt = connection.createStatement()) {
            // Execute a simple SELECT query.
            boolean result = stmt.execute("SELECT 1");
            // The main purpose is to ensure that no exception is thrown.
            // Depending on the JDBC driver, the returned boolean might vary.
            assertNotNull("The query execution should complete without returning a null result", result);
        }
    }
}
