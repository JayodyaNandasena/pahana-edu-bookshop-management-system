package serviceTest;

import static org.junit.Assert.*;
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

import com.pahanaedu.dao.BillItemDao;
import com.pahanaedu.model.Bill;
import com.pahanaedu.model.BillItem;
import com.pahanaedu.model.Item;
import com.pahanaedu.service.BillItemService;

@RunWith(MockitoJUnitRunner.class)
public class BillItemServiceTest {
	
	@Mock
	private BillItemDao mockBillItemDao;

	private BillItemService billItemService;

	@Before
	public void setUp() throws Exception {
		// Get the singleton instance
		billItemService = BillItemService.getInstance();

		// Use reflection to inject the mock BillItemDao
		Field BillItemDaoField = BillItemService.class.getDeclaredField("billItemDao");
		BillItemDaoField.setAccessible(true);
		BillItemDaoField.set(billItemService, mockBillItemDao);
	}

	@After
	public void tearDown() throws Exception {
		// Reset the singleton instance for clean tests
		Field instanceField = BillItemService.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);
	}

	@Test
	public void testGetInstance_ReturnsSameInstance() {
		// Given
		BillItemService instance1 = BillItemService.getInstance();
		BillItemService instance2 = BillItemService.getInstance();

		// Then
		assertSame("getInstance should return the same instance", instance1, instance2);
		assertNotNull("Instance should not be null", instance1);
	}

	@Test
	public void testGetInstance_ThreadSafety() throws InterruptedException {
		// Reset singleton for this test
		Field instanceField;
		try {
			instanceField = BillItemService.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, null);
		} catch (Exception e) {
			fail("Failed to reset singleton instance");
		}

		final BillItemService[] instances = new BillItemService[10];
		Thread[] threads = new Thread[10];

		// Create multiple threads that call getInstance simultaneously
		for (int i = 0; i < 10; i++) {
			final int index = i;
			threads[i] = new Thread(() -> {
				instances[index] = BillItemService.getInstance();
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
		BillItemService firstInstance = instances[0];
		for (int i = 1; i < instances.length; i++) {
			assertSame("All instances should be the same", firstInstance, instances[i]);
		}
	}

	// --------- SUCCESS TEST ---------
    @Test
    public void testById_Success() throws SQLException {
        Bill bill = new Bill(); // create mock Bill
        Item item = new Item(); // create mock Item
        BillItem expected = new BillItem(1, 5, 10.0, bill, item);

        when(mockBillItemDao.byId(1)).thenReturn(expected);

        BillItem result = billItemService.byId(1);

        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getQuantity(), result.getQuantity());
        assertEquals(expected.getUnitPrice(), result.getUnitPrice());
        assertEquals(expected.getBill(), result.getBill());
        assertEquals(expected.getItem(), result.getItem());

        verify(mockBillItemDao, times(1)).byId(1);
    }

    @Test
    public void testById_ThrowsSQLException() throws SQLException {
        when(mockBillItemDao.byId(1)).thenThrow(new SQLException("DB error"));

        SQLException exception = assertThrows(SQLException.class, () -> {
            billItemService.byId(1);
        });

        assertEquals("DB error", exception.getMessage());
        verify(mockBillItemDao, times(1)).byId(1);
    }

    @Test
    public void testById_NotFound() throws SQLException {
        when(mockBillItemDao.byId(1)).thenReturn(null);

        BillItem result = billItemService.byId(1);

        assertNull(result);
        verify(mockBillItemDao, times(1)).byId(1);
    }

}
