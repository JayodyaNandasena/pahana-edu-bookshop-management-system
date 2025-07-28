package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pahanaedu.model.User;
import com.pahanaedu.model.enums.UserType;
import com.pahanaedu.util.DbConnectionFactory;

public class LoginDao {
	public User findUserByUsernameAndPassword(String username, String password) throws SQLException {
		String sql = "SELECT id, username, name, user_type FROM user WHERE username = ? AND password_hash = ?";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, username);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setName(rs.getString("name"));
					user.setUserType(UserType.valueOf(rs.getString("user_type")));
					return user;
				}
			}
		}
		return null;
	}
}
