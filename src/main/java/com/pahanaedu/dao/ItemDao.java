package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pahanaedu.model.Item;
import com.pahanaedu.util.DbConnectionFactory;

public class ItemDao {

	public List<Item> all() throws SQLException {
		List<Item> itemList = new ArrayList<Item>();

		String sql = "SELECT id, name, unit_price, quantity_available FROM item";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setUnitPrice(rs.getDouble("unit_price"));
				item.setQuantityAvailable(rs.getInt("quantity_available"));
				itemList.add(item);
			}
		}
		return itemList;
	}

}
