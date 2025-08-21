package serviceTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pahanaedu.dao.CustomerDao;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.CustomerService;

public class CustomerServiceTest {

	@Mock
	private CustomerDao mockCustomerDao;

	private CustomerService customerService;

	@Before
	public void setUp() throws Exception {
	    MockitoAnnotations.openMocks(this);
	    customerService = CustomerService.getInstance();

	    Field daoField = CustomerService.class.getDeclaredField("customerDao");
	    daoField.setAccessible(true);
	    daoField.set(customerService, mockCustomerDao);
	}

	@After
	public void tearDown() throws Exception {
		// Reset the singleton instance for clean tests
		Field instanceField = CustomerService.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);
	}

	@Test
	public void testGetInstance_ReturnsSameInstance() {
		// Given
		CustomerService instance1 = CustomerService.getInstance();
		CustomerService instance2 = CustomerService.getInstance();

		// Then
		assertSame("getInstance should return the same instance", instance1, instance2);
		assertNotNull("Instance should not be null", instance1);
	}

	@Test
	public void testGetInstance_ThreadSafety() throws InterruptedException {
		// Reset singleton for this test
		Field instanceField;
		try {
			instanceField = CustomerService.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, null);
		} catch (Exception e) {
			fail("Failed to reset singleton instance");
		}

		final CustomerService[] instances = new CustomerService[10];
		Thread[] threads = new Thread[10];

		// Create multiple threads that call getInstance simultaneously
		for (int i = 0; i < 10; i++) {
			final int index = i;
			threads[i] = new Thread(() -> {
				instances[index] = CustomerService.getInstance();
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
		CustomerService firstInstance = instances[0];
		for (int i = 1; i < instances.length; i++) {
			assertSame("All instances should be the same", firstInstance, instances[i]);
		}
	}
	
	@Test
    public void testPersist_Success() {
        when(mockCustomerDao.persist("John", "Doe", "0771234567", "john@example.com", "Colombo"))
                .thenReturn(PersistResult.SUCCESS);

        PersistResult result = customerService.persist("John", "Doe", "0771234567", "john@example.com", "Colombo");

        assertEquals(PersistResult.SUCCESS, result);
    }

    @Test
    public void testGetByMobile() throws SQLException {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setPhone("0771234567");
        mockCustomer.setIsActive(true);

        when(mockCustomerDao.getByMobile("0771234567")).thenReturn(mockCustomer);

        Customer result = customerService.getByMobile("0771234567");

        assertNotNull(result);
        assertEquals("0771234567", result.getPhone());
    }

    @Test
    public void testByIdOrPhone() throws SQLException {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);

        when(mockCustomerDao.byIdOrPhone("1")).thenReturn(mockCustomer);

        Customer result = customerService.byIdOrPhone("1");

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeactivate() {
        when(mockCustomerDao.deactivate("C003")).thenReturn(true);

        boolean result = customerService.deactivate("C003");

        assertTrue(result);
    }

    @Test
    public void testActivate() {
        when(mockCustomerDao.activate("C004")).thenReturn(true);

        boolean result = customerService.activate("C004");

        assertTrue(result);
    }

    @Test
    public void testUpdate_Success() {
        when(mockCustomerDao.update("C005", "Jane", "Smith", "0712345678", "jane@example.com", "Kandy"))
                .thenReturn(PersistResult.SUCCESS);

        PersistResult result = customerService.update("C005", "Jane", "Smith", "0712345678", "jane@example.com", "Kandy");

        assertEquals(PersistResult.SUCCESS, result);
    }

}
