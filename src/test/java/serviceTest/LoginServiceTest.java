package serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pahanaedu.dao.LoginDao;
import com.pahanaedu.model.User;
import com.pahanaedu.service.LoginService;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

	@Mock
	private LoginDao mockLoginDao;

	private LoginService loginService;

	@Before
	public void setUp() throws Exception {
		// Get the singleton instance
		loginService = LoginService.getInstance();

		// Use reflection to inject the mock LoginDao
		Field loginDaoField = LoginService.class.getDeclaredField("loginDao");
		loginDaoField.setAccessible(true);
		loginDaoField.set(loginService, mockLoginDao);
	}

	@After
	public void tearDown() throws Exception {
		// Reset the singleton instance for clean tests
		Field instanceField = LoginService.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);
	}

	@Test
	public void testGetInstance_ReturnsSameInstance() {
		// Given
		LoginService instance1 = LoginService.getInstance();
		LoginService instance2 = LoginService.getInstance();

		// Then
		assertSame("getInstance should return the same instance", instance1, instance2);
		assertNotNull("Instance should not be null", instance1);
	}

	@Test
	public void testGetInstance_ThreadSafety() throws InterruptedException {
		// Reset singleton for this test
		Field instanceField;
		try {
			instanceField = LoginService.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, null);
		} catch (Exception e) {
			fail("Failed to reset singleton instance");
		}

		final LoginService[] instances = new LoginService[10];
		Thread[] threads = new Thread[10];

		// Create multiple threads that call getInstance simultaneously
		for (int i = 0; i < 10; i++) {
			final int index = i;
			threads[i] = new Thread(() -> {
				instances[index] = LoginService.getInstance();
			});
		}

		// Start all threads
		for (Thread thread : threads) {
			thread.start();
		}

		// Wait for all threads to complete
		for (Thread thread : threads) {
			thread.join();
		}

		// Verify all instances are the same
		LoginService firstInstance = instances[0];
		for (int i = 1; i < instances.length; i++) {
			assertSame("All instances should be the same", firstInstance, instances[i]);
		}
	}

	@Test
	public void testLogin_ValidCredentials_ReturnsUser() throws SQLException {
		// Given
		String username = "testuser";
		String password = "testpass";
		User expectedUser = new User(); // Assuming User has a default constructor
		when(mockLoginDao.authenticateUser(username, password)).thenReturn(expectedUser);

		// When
		User result = loginService.login(username, password);

		// Then
		assertNotNull("User should not be null", result);
		assertEquals("Should return the expected user", expectedUser, result);
		verify(mockLoginDao, times(1)).authenticateUser(username, password);
	}

	@Test
	public void testLogin_InvalidCredentials_ReturnsNull() throws SQLException {
		// Given
		String username = "invaliduser";
		String password = "invalidpass";
		when(mockLoginDao.authenticateUser(username, password)).thenReturn(null);

		// When
		User result = loginService.login(username, password);

		// Then
		assertNull("User should be null for invalid credentials", result);
		verify(mockLoginDao, times(1)).authenticateUser(username, password);
	}

	@Test
	public void testLogin_EmptyUsername() throws SQLException {
		// Given
		String username = "";
		String password = "testpass";
		when(mockLoginDao.authenticateUser(username, password)).thenReturn(null);

		// When
		User result = loginService.login(username, password);

		// Then
		assertNull("User should be null for empty username", result);
		verify(mockLoginDao, times(1)).authenticateUser(username, password);
	}

	@Test
	public void testLogin_EmptyPassword() throws SQLException {
		// Given
		String username = "testuser";
		String password = "";
		when(mockLoginDao.authenticateUser(username, password)).thenReturn(null);

		// When
		User result = loginService.login(username, password);

		// Then
		assertNull("User should be null for empty password", result);
		verify(mockLoginDao, times(1)).authenticateUser(username, password);
	}

	@Test
	public void testLogin_NullUsername() throws SQLException {
		// Given
		String username = null;
		String password = "testpass";
		when(mockLoginDao.authenticateUser(username, password)).thenReturn(null);

		// When
		User result = loginService.login(username, password);

		// Then
		assertNull("User should be null for null username", result);
		verify(mockLoginDao, times(1)).authenticateUser(username, password);
	}

	@Test
	public void testLogin_NullPassword() throws SQLException {
		// Given
		String username = "testuser";
		String password = null;
		when(mockLoginDao.authenticateUser(username, password)).thenReturn(null);

		// When
		User result = loginService.login(username, password);

		// Then
		assertNull("User should be null for null password", result);
		verify(mockLoginDao, times(1)).authenticateUser(username, password);
	}

	@Test(expected = SQLException.class)
	public void testLogin_DatabaseException() throws SQLException {
		// Given
		String username = "testuser";
		String password = "testpass";
		when(mockLoginDao.authenticateUser(username, password))
				.thenThrow(new SQLException("Database connection failed"));

		// When
		loginService.login(username, password);

		// Then - SQLException should be thrown
	}

	@Test
	public void testLogin_MultipleSuccessfulCalls() throws SQLException {
		// Given
		String username1 = "user1";
		String password1 = "pass1";
		String username2 = "user2";
		String password2 = "pass2";

		User user1 = new User();
		User user2 = new User();

		when(mockLoginDao.authenticateUser(username1, password1)).thenReturn(user1);
		when(mockLoginDao.authenticateUser(username2, password2)).thenReturn(user2);

		// When
		User result1 = loginService.login(username1, password1);
		User result2 = loginService.login(username2, password2);

		// Then
		assertNotNull("First user should not be null", result1);
		assertNotNull("Second user should not be null", result2);
		assertEquals("Should return first expected user", user1, result1);
		assertEquals("Should return second expected user", user2, result2);

		verify(mockLoginDao, times(1)).authenticateUser(username1, password1);
		verify(mockLoginDao, times(1)).authenticateUser(username2, password2);
	}

	@Test
	public void testLogin_VerifyParametersPassed() throws SQLException {
		// Given
		String username = "specificUser";
		String password = "specificPass";
		User expectedUser = new User();
		when(mockLoginDao.authenticateUser(username, password)).thenReturn(expectedUser);

		// When
		loginService.login(username, password);

		// Then
		verify(mockLoginDao).authenticateUser(eq(username), eq(password));
	}
}
