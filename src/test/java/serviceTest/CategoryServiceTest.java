package serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pahanaedu.dao.CategoryDao;
import com.pahanaedu.model.Category;
import com.pahanaedu.service.CategoryService;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

	@Mock
	private CategoryDao mockCategoryDao;

	private CategoryService categoryService;

	@Before
	public void setUp() throws Exception {
		// Get the singleton instance
		categoryService = CategoryService.getInstance();

		// Use reflection to inject the mock CategoryDao
		Field CategoryDaoField = CategoryService.class.getDeclaredField("categoryDao");
		CategoryDaoField.setAccessible(true);
		CategoryDaoField.set(categoryService, mockCategoryDao);
	}

	@After
	public void tearDown() throws Exception {
		// Reset the singleton instance for clean tests
		Field instanceField = CategoryService.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);
	}

	@Test
	public void testGetInstance_ReturnsSameInstance() {
		// Given
		CategoryService instance1 = CategoryService.getInstance();
		CategoryService instance2 = CategoryService.getInstance();

		// Then
		assertSame("getInstance should return the same instance", instance1, instance2);
		assertNotNull("Instance should not be null", instance1);
	}

	@Test
	public void testGetInstance_ThreadSafety() throws InterruptedException {
		// Reset singleton for this test
		Field instanceField;
		try {
			instanceField = CategoryService.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, null);
		} catch (Exception e) {
			fail("Failed to reset singleton instance");
		}

		final CategoryService[] instances = new CategoryService[10];
		Thread[] threads = new Thread[10];

		// Create multiple threads that call getInstance simultaneously
		for (int i = 0; i < 10; i++) {
			final int index = i;
			threads[i] = new Thread(() -> {
				instances[index] = CategoryService.getInstance();
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
		CategoryService firstInstance = instances[0];
		for (int i = 1; i < instances.length; i++) {
			assertSame("All instances should be the same", firstInstance, instances[i]);
		}
	}

	@Test
	public void testAll_ReturnsCategoryList() throws SQLException {
		// Given
		Category cat1 = new Category(1, "Books");
		Category cat2 = new Category(2, "Miscellaneous");
		List<Category> expected = Arrays.asList(cat1, cat2);

		when(mockCategoryDao.all()).thenReturn(expected);

		// When
		List<Category> result = categoryService.all();

		// Then
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(expected, result);
		verify(mockCategoryDao, times(1)).all();
	}

	@Test
	public void testAll_ReturnsEmptyList() throws SQLException {
		// Given
		when(mockCategoryDao.all()).thenReturn(Collections.emptyList());

		// When
		List<Category> result = categoryService.all();

		// Then
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(mockCategoryDao, times(1)).all();
	}

	@Test(expected = SQLException.class)
	public void testAll_ThrowsSQLException() throws SQLException {
		// Given
		when(mockCategoryDao.all()).thenThrow(new SQLException("Database error"));

		// When
		categoryService.all();

		// Then - exception is expected
	}
}
