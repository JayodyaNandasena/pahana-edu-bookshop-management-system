package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pahanaedu.model.Customer;
import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.util.DbConnectionFactory;

public class CustomerDao {
	public PersistResult persist(String firstName, String lastName, String phone, String email, String address) {
		String sql = "INSERT INTO customer (first_name, last_name, phone, email, address) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, phone);
			stmt.setString(4, email);
			stmt.setString(5, address);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0 ? PersistResult.SUCCESS : PersistResult.OTHER_ERROR;

		} catch (SQLException e) {
			if (isUniqueConstraintViolation(e)) {
				String message = e.getMessage().toLowerCase();
				if (message.contains("email")) {
					return PersistResult.EMAIL_EXISTS;
				}
				return PersistResult.PHONE_EXISTS;
			}
			// Log the error and return failure
			System.err.println("Database error: " + e.getMessage());
			return PersistResult.OTHER_ERROR;
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

	public Customer getByMobile(String mobile) throws SQLException {
		Customer customer = null;

		String sql = "SELECT id, first_name, last_name FROM customer WHERE phone = ? AND is_active = 1";

		Connection conn = DbConnectionFactory.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, mobile);

		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			int id = resultSet.getInt("id");
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			customer = new Customer(id, firstName, lastName);
		}

		return customer;
	}

}
