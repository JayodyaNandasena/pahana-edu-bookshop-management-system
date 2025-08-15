package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pahanaedu.model.Category;
import com.pahanaedu.util.DbConnectionFactory;

public class CategoryDao {

	public List<Category> all() throws SQLException {
		List<Category> categoryList = new ArrayList<Category>();

		String sql = "SELECT id, name FROM category";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				categoryList.add(category);
			}
		}
		return categoryList;
	}

}
