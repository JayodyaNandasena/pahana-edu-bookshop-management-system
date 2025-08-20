package serviceTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pahanaedu.dao.ItemDao;
import com.pahanaedu.model.Category;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.ItemService;

public class ItemServiceTest {

	@Mock
	private ItemDao mockItemDao;

	private ItemService itemService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		itemService = ItemService.getInstance();

		Field daoField = ItemService.class.getDeclaredField("itemDao");
		daoField.setAccessible(true);
		daoField.set(itemService, mockItemDao);
	}

	@After
	public void tearDown() throws Exception {
		// Reset the singleton instance for clean tests
		Field instanceField = ItemService.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);
	}

	@Test
	public void testGetInstance_ReturnsSameInstance() {
		// Given
		ItemService instance1 = ItemService.getInstance();
		ItemService instance2 = ItemService.getInstance();

		// Then
		assertSame("getInstance should return the same instance", instance1, instance2);
		assertNotNull("Instance should not be null", instance1);
	}

	@Test
	public void testGetInstance_ThreadSafety() throws InterruptedException {
		// Reset singleton for this test
		Field instanceField;
		try {
			instanceField = ItemService.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, null);
		} catch (Exception e) {
			fail("Failed to reset singleton instance");
		}

		final ItemService[] instances = new ItemService[10];
		Thread[] threads = new Thread[10];

		// Create multiple threads that call getInstance simultaneously
		for (int i = 0; i < 10; i++) {
			final int index = i;
			threads[i] = new Thread(() -> {
				instances[index] = ItemService.getInstance();
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
		ItemService firstInstance = instances[0];
		for (int i = 1; i < instances.length; i++) {
			assertSame("All instances should be the same", firstInstance, instances[i]);
		}
	}

	@Test
	public void testPersist_Success() {
		when(mockItemDao.persist("Text Book", 1, 500.00, 1500)).thenReturn(PersistResult.SUCCESS);

		PersistResult result = itemService.persist("Text Book", 1, 500.00, 1500);

		assertEquals(PersistResult.SUCCESS, result);
	}

	@Test
	public void testAllItems() throws SQLException {
		Category category = new Category(1, "stationery");
		Item item1 = new Item(1, "Text Book", 500.00, 1500, category);
		Item item2 = new Item(2, "Pen", 500.00, 1500, category);
		List<Item> mockList = Arrays.asList(item1, item2);

		when(mockItemDao.all()).thenReturn(mockList);

		List<Item> result = itemService.all();

		assertEquals(2, result.size());
		assertEquals("Text Book", result.get(0).getName());
		verify(mockItemDao).all();
	}

	@Test
	public void testItemsByCategory() throws SQLException {
		int categoryId = 1;
		Category category = new Category(1, "stationery");
		Item item = new Item(1, "Pencil", 500.00, 1500, category);

		when(mockItemDao.byCategory(categoryId)).thenReturn(List.of(item));

		List<Item> result = itemService.byCategory(categoryId);

		assertEquals(1, result.size());
		assertEquals("Pencil", result.get(0).getName());
		verify(mockItemDao).byCategory(categoryId);
	}

	@Test
	public void testItemsByIdOrName() throws SQLException {
		String search = "Pen";
		Category category = new Category(1, "stationery");
		Item item = new Item(2, "Pen", 500.00, 1500, category);

		when(mockItemDao.byIdOrName(search)).thenReturn(List.of(item));

		List<Item> result = itemService.byIdOrName(search);

		assertEquals(1, result.size());
		assertEquals("Pen", result.get(0).getName());
		verify(mockItemDao).byIdOrName(search);
	}

	@Test
	public void testDeleteItem() {
		int itemId = 5;

		when(mockItemDao.delete(itemId)).thenReturn(true);

		boolean result = itemService.delete(itemId);

		assertTrue(result);
		verify(mockItemDao).delete(itemId);
	}

	@Test
	public void testUpdate_Success() {
		when(mockItemDao.update(1, "Text Book", 1, 500.00, 1500)).thenReturn(PersistResult.SUCCESS);

		PersistResult result = itemService.update(1, "Text Book", 1, 500.00, 1500);

		assertEquals(PersistResult.SUCCESS, result);
	}

}
