package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pahanaedu.model.Category;
import com.pahanaedu.model.Item;
import com.pahanaedu.util.DbConnectionFactory;

public class ItemDao {

	public List<Item> all() throws SQLException {
		List<Item> itemList = new ArrayList<Item>();

		String sql = "SELECT i.id, i.name, i.unit_price, i.quantity_available, c.name AS category_name "
				+ "FROM item i "
				+ "INNER JOIN category c ON i.category_id=c.id "
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
				item.setCategory(new Category(rs.getString("category_name")));
				itemList.add(item);
			}
		}
		return itemList;
	}

	public List<Item> byCategory(int categoryId) throws SQLException {
		List<Item> itemList = new ArrayList<Item>();

		String sql = "SELECT i.id, i.name, i.unit_price, i.quantity_available, c.name AS category_name "
				+ "FROM item i "
				+ "INNER JOIN category c ON i.category_id=c.id "
				+ "WHERE category_id = ? "
				+ "ORDER BY i.id ASC";

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
					item.setCategory(new Category(rs.getString("category_name")));
					itemList.add(item);
				}
			}
		}
		return itemList;
	}

	public List<Item> byIdOrName(String searchTerm) throws SQLException {
		List<Item> itemList = new ArrayList<Item>();

		String sql = "SELECT i.id, i.name, i.unit_price, i.quantity_available, c.name AS category_name "
				+ "FROM item i "
				+ "INNER JOIN category c ON i.category_id=c.id "
				+ "WHERE i.id = ? OR i.name LIKE ? "
				+ "ORDER BY i.id ASC";

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
					item.setCategory(new Category(rs.getString("category_name")));
					itemList.add(item);
				}
			}
		}
		return itemList;
	}

}
