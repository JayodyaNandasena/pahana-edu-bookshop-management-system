package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pahanaedu.model.Category;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.exception.ItemNotFoundException;
import com.pahanaedu.util.DbConnectionFactory;

public class ItemDao {

	public List<Item> all() throws SQLException {
		List<Item> itemList = new ArrayList<Item>();

		String sql = "SELECT i.id, i.name, i.unit_price, i.quantity_available, c.id AS category_id, c.name AS category_name "
				+ "FROM item i " + "INNER JOIN category c ON i.category_id=c.id " + "WHERE i.is_deleted = false "
				+ "ORDER BY i.id ASC";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setUnitPrice(rs.getDouble("unit_price"));
				item.setQuantityAvailable(rs.getInt("quantity_available"));
				item.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
				itemList.add(item);
			}
		}
		return itemList;
	}

	public List<Item> byCategory(int categoryId) throws SQLException {
		List<Item> itemList = new ArrayList<Item>();

		String sql = "SELECT i.id, i.name, i.unit_price, i.quantity_available,c.id AS category_id, c.name AS category_name "
				+ "FROM item i " + "INNER JOIN category c ON i.category_id=c.id "
				+ "WHERE category_id = ? AND i.is_deleted = 0 " + "ORDER BY i.id ASC";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, categoryId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id"));
					item.setName(rs.getString("name"));
					item.setUnitPrice(rs.getDouble("unit_price"));
					item.setQuantityAvailable(rs.getInt("quantity_available"));
					item.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
					itemList.add(item);
				}
			}
		}
		return itemList;
	}

	public List<Item> byIdOrName(String searchTerm) throws SQLException {
		List<Item> itemList = new ArrayList<Item>();

		String sql = "SELECT i.id, i.name, i.unit_price, i.quantity_available, c.id AS category_id, c.name AS category_name "
				+ "FROM item i " + "INNER JOIN category c ON i.category_id=c.id "
				+ "WHERE i.id = ? OR i.name LIKE ? AND is_deleted = 0 " + "ORDER BY i.id ASC";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			// Try to parse the searchTerm as ID (number), else set a dummy ID
			try {
				stmt.setInt(1, Integer.parseInt(searchTerm));
			} catch (NumberFormatException e) {
				stmt.setInt(1, -1);
			}

			stmt.setString(2, "%" + searchTerm + "%");

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id"));
					item.setName(rs.getString("name"));
					item.setUnitPrice(rs.getDouble("unit_price"));
					item.setQuantityAvailable(rs.getInt("quantity_available"));
					item.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
					itemList.add(item);
				}
			}
		}
		return itemList;
	}

	public boolean delete(int itemId) {
		String sql = "UPDATE item SET is_deleted = true WHERE id = ?";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, itemId);
			int rowsAffected = stmt.executeUpdate();

			return rowsAffected > 0;

		} catch (SQLException e) {
			return false;
		}
	}

	public boolean restore(int itemId) {
		String sql = "UPDATE item SET is_deleted = false WHERE id = ?";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, itemId);
			int rowsAffected = stmt.executeUpdate();

			return rowsAffected > 0;

		} catch (SQLException e) {
			return false;
		}
	}

	public PersistResult persist(String name, int category, double price, int quantity) {
		String sql = "INSERT INTO item (name, unit_price, quantity_available, category_id) VALUES (?, ?, ?, ?)";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, name);
			stmt.setDouble(2, price);
			stmt.setInt(3, quantity);
			stmt.setInt(4, category);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0 ? PersistResult.SUCCESS : PersistResult.OTHER_ERROR;

		} catch (SQLException e) {
			if (isUniqueConstraintViolation(e)) {
				return PersistResult.ITEM_NAME_CATEGORY_EXISTS;
			}
			// Log the error and return failure
			System.err.println("Database error: " + e.getMessage());
			return PersistResult.OTHER_ERROR;
		}
	}

	public PersistResult update(int id, String name, int category, double price, int quantity) {
		String sql = "UPDATE item SET name = ?, unit_price = ?, quantity_available = ?, category_id = ? WHERE id = ?";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, name);
			stmt.setDouble(2, price);
			stmt.setInt(3, quantity);
			stmt.setInt(4, category);
			stmt.setInt(5, id);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0 ? PersistResult.SUCCESS : PersistResult.OTHER_ERROR;

		} catch (SQLException e) {
			if (isUniqueConstraintViolation(e)) {
				return PersistResult.ITEM_NAME_CATEGORY_EXISTS;
			}
			System.err.println("Database error: " + e.getMessage());
			return PersistResult.OTHER_ERROR;
		}
	}

	public Item byId(int id) throws SQLException {
		String sql = "SELECT id, name, unit_price, quantity_available " + "FROM item "
				+ "WHERE id = ? AND is_deleted = 0";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id"));
					item.setName(rs.getString("name"));
					item.setUnitPrice(rs.getDouble("unit_price"));
					item.setQuantityAvailable(rs.getInt("quantity_available"));
					return item;
				}
				return null;
			}
		}
	}

	public List<Item> getDeleted() throws SQLException {
		List<Item> itemList = new ArrayList<Item>();

		String sql = "SELECT i.id, i.name, i.unit_price, i.quantity_available, c.id AS category_id, c.name AS category_name "
				+ "FROM item i " + "INNER JOIN category c ON i.category_id=c.id " + "WHERE i.is_deleted = 1 "
				+ "ORDER BY i.id ASC";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id"));
					item.setName(rs.getString("name"));
					item.setUnitPrice(rs.getDouble("unit_price"));
					item.setQuantityAvailable(rs.getInt("quantity_available"));
					item.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
					itemList.add(item);
				}
			}
		}
		return itemList;
	}

	public void reduceQuantity(int id, int quantity, Connection conn) throws SQLException {
		String sql = "UPDATE item SET quantity_available = quantity_available - ? WHERE id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, quantity);
			stmt.setInt(2, id);

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new ItemNotFoundException("Reducing quantity failed: no item found with id " + id);
			}
		}
	}

	// Helper method to detect unique constraint violations
	private boolean isUniqueConstraintViolation(SQLException e) {
		String sqlState = e.getSQLState();
		int errorCode = e.getErrorCode();

		// MySQL specific codes for unique constraint violations:
		// - Error code 1062: Duplicate entry for key
		// - SQL state 23000: Integrity constraint violation
		return errorCode == 1062 || "23000".equals(sqlState);
	}
}
