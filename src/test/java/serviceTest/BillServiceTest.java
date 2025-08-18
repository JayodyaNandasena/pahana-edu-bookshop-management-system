package serviceTest;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pahanaedu.dao.BillDao;
import com.pahanaedu.dao.BillItemDao;
import com.pahanaedu.dao.CustomerDao;
import com.pahanaedu.dao.ItemDao;
import com.pahanaedu.model.Bill;
import com.pahanaedu.model.BillItem;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.User;
import com.pahanaedu.service.BillService;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceTest {

	@Mock
	private BillDao mockBillDao;
	@Mock
	private BillItemDao mockBillItemDao;
	@Mock
	private ItemDao mockItemDao;
	@Mock
	private CustomerDao mockCustomerDao;
	@Mock
	private Connection mockConnection;

	private BillService billService;

	Bill mockBill = new Bill(1, "BILL-001", LocalDate.now(), LocalTime.now(), 1251.00, new Customer(1), new User(10),
			Arrays.asList(new BillItem(1, 1, 1200.00, null, new Item(100)),
					new BillItem(2, 2, 25.50, null, new Item(101))));

	@Before
	public void setUp() throws Exception {
		// Get the singleton instance
		billService = BillService.getInstance();

		// Use reflection to inject the mock daos
		Field BillDaoField = BillService.class.getDeclaredField("billDao");
		BillDaoField.setAccessible(true);
		BillDaoField.set(billService, mockBillDao);

		Field BillItemDaoField = BillService.class.getDeclaredField("billItemDao");
		BillItemDaoField.setAccessible(true);
		BillItemDaoField.set(billService, mockBillItemDao);

		Field ItemDaoField = BillService.class.getDeclaredField("itemDao");
		ItemDaoField.setAccessible(true);
		ItemDaoField.set(billService, mockItemDao);

		Field CustomerDaoField = BillService.class.getDeclaredField("customerDao");
		CustomerDaoField.setAccessible(true);
		CustomerDaoField.set(billService, mockCustomerDao);
	}

	@After
	public void tearDown() throws Exception {
		// Reset singleton
		Field instanceField = BillService.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);
	}

	@Test
	public void testGetInstance_ReturnsSameInstance() {
		// Given
		BillService instance1 = BillService.getInstance();
		BillService instance2 = BillService.getInstance();

		// Then
		assertSame("getInstance should return the same instance", instance1, instance2);
		assertNotNull("Instance should not be null", instance1);
	}

	@Test
	public void testGetInstance_ThreadSafety() throws InterruptedException {
		// Reset singleton for this test
		Field instanceField;
		try {
			instanceField = BillService.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, null);
		} catch (Exception e) {
			fail("Failed to reset singleton instance");
		}

		final BillService[] instances = new BillService[10];
		Thread[] threads = new Thread[10];

		// Create multiple threads that call getInstance simultaneously
		for (int i = 0; i < 10; i++) {
			final int index = i;
			threads[i] = new Thread(() -> {
				instances[index] = BillService.getInstance();
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
		BillService firstInstance = instances[0];
		for (int i = 1; i < instances.length; i++) {
			assertSame("All instances should be the same", firstInstance, instances[i]);
		}
	}

	@Test
	public void testPersist_Success() throws Exception {
		JSONArray items = new JSONArray(
				Arrays.asList(new JSONObject().put("id", 100).put("quantity", 2).put("unitPrice", 50.0),
						new JSONObject().put("id", 101).put("quantity", 1).put("unitPrice", 30.0)));

		Bill savedBill = new Bill();
		savedBill.setId(1);

		when(mockBillDao.persist(any(Bill.class), any(Connection.class))).thenReturn(savedBill);

		Bill result = billService.persist(1, items, "2025-08-18", "10:00", 130.0, 10);

		assertEquals(1L, result.getId());

		verify(mockBillDao).persist(any(Bill.class), any(Connection.class));
		verify(mockBillItemDao, times(2)).persist(any(BillItem.class), any(Connection.class));
		verify(mockItemDao).reduceQuantity(eq(100), eq(2), any(Connection.class));
		verify(mockItemDao).reduceQuantity(eq(101), eq(1), any(Connection.class));
		verify(mockCustomerDao).increaseUnitsConsumed(eq(1), eq(3), any(Connection.class));
	}

	@Test(expected = SQLException.class)
	public void testPersist_RollbackOnError() throws Exception {
		JSONArray items = new JSONArray(
				Arrays.asList(new JSONObject().put("id", 100).put("quantity", 2).put("unitPrice", 50.0)));

		when(mockBillDao.persist(any(Bill.class), any(Connection.class))).thenThrow(new SQLException("DB error"));

		billService.persist(1, items, "2025-08-18", "10:00", 100.0, 10);
	}

	@Test
	public void testGetTotalRevenue_Success() throws SQLException {
		// Given
		Double expected = 1234.00;
		when(mockBillDao.getTotal()).thenReturn(expected);

		// When
		Double result = billService.getTotalRevenue();

		// Then
		assertNotNull(result);
		assertEquals(expected, result);
		verify(mockBillDao, times(1)).getTotal();
	}

	@Test
	public void testGetTotalRevenue_ThrowsSQLException() throws SQLException {
		when(mockBillDao.getTotal()).thenThrow(new SQLException("DB error"));

		SQLException exception = assertThrows(SQLException.class, () -> {
			billService.getTotalRevenue();
		});

		assertEquals("DB error", exception.getMessage());
		verify(mockBillDao, times(1)).getTotal();
	}

	@Test
	public void testGetAverageBillValue_Success() throws SQLException {
		// Given
		Double expected = 256.75;
		when(mockBillDao.getAverage()).thenReturn(expected);

		// When
		Double result = billService.getAverageBillValue();

		// Then
		assertNotNull(result);
		assertEquals(expected, result);
		verify(mockBillDao, times(1)).getAverage();
	}

	@Test
	public void testGetAverageBillValue_ThrowsSQLException() throws SQLException {
		when(mockBillDao.getAverage()).thenThrow(new SQLException("DB error"));

		SQLException exception = assertThrows(SQLException.class, () -> {
			billService.getAverageBillValue();
		});

		assertEquals("DB error", exception.getMessage());
		verify(mockBillDao, times(1)).getAverage();
	}

	@Test
	public void testGetMonthlyRevenues_Success() throws SQLException {
		// Given
		List<Double> expected = Arrays.asList(100.00, 200.00, 300.00);
		when(mockBillDao.getMonthlyRevenues()).thenReturn(expected);

		// When
		List<Double> result = billService.getMonthlyRevenues();

		// Then
		assertNotNull(result);
		assertEquals(expected.size(), result.size());
		assertEquals(expected, result);
		verify(mockBillDao, times(1)).getMonthlyRevenues();
	}

	@Test
	public void testGetMonthlyRevenues_ThrowsSQLException() throws SQLException {
		when(mockBillDao.getMonthlyRevenues()).thenThrow(new SQLException("DB error"));

		SQLException exception = assertThrows(SQLException.class, () -> {
			billService.getMonthlyRevenues();
		});

		assertEquals("DB error", exception.getMessage());
		verify(mockBillDao, times(1)).getMonthlyRevenues();
	}
}
