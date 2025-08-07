package utilTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.pahanaedu.util.DbConnectionFactory;

public class DbConnectionFactoryTest {

    @Test
    public void testGetConnectionNotNull() {
        // Test to ensure that the connection is not null
        Connection conn = DbConnectionFactory.getConnection();
        assertNotNull("Connection should not be null", conn);
    }

    @Test
    public void testGetConnectionValid() throws SQLException {
        // Test to ensure that the connection is valid
        Connection conn = DbConnectionFactory.getConnection();
        assertTrue("Connection should be valid", conn.isValid(2));
    }

    @Test
    public void testConnectionCanExecuteQuery() throws SQLException {
        // Test to ensure that the connection can execute a simple query
        try (Connection conn = DbConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            assertNotNull("Statement should not be null", stmt);
            boolean result = stmt.execute("SELECT 1");
            assertNotNull(result);
        }
    }

    @Test
    public void testGetConnectionMultipleCalls() throws SQLException {
        // Test to ensure multiple calls to getConnection return valid connections
        Connection conn1 = DbConnectionFactory.getConnection();
        Connection conn2 = DbConnectionFactory.getConnection();
        assertNotNull(conn1);
        assertNotNull(conn2);
        assertFalse("Connection 1 should be open", conn1.isClosed());
        assertFalse("Connection 2 should be open", conn2.isClosed());
    }
}
