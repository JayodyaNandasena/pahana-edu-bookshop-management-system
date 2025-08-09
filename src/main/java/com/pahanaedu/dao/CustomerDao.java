package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pahanaedu.model.Bill;
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

	public Customer getByMobile(String mobile) throws SQLException {
		Customer customer = null;

		String sql = "SELECT id, first_name, last_name, email FROM customer WHERE phone = ? AND is_active = 1";

		Connection conn = DbConnectionFactory.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, mobile);

		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			int id = resultSet.getInt("id");
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			customer = new Customer(id, firstName, lastName, email);
		}

		return customer;
	}

	public Customer byIdOrPhone(String searchTerm) throws SQLException {
		Customer customer = null;

		String sql = "SELECT "
				+ "c.id, c.first_name, c.last_name, c.email, c.phone, c.address, c.units_consumed, c.is_active, "
				+ "JSON_ARRAYAGG(JSON_OBJECT('bill_id', b.id, 'total', b.total, 'date', b.bill_date, 'time', b.bill_time)) AS bills "
				+ "FROM customer c " + "LEFT JOIN bill b ON c.id = b.customer_id " + "WHERE c.id = ? OR c.phone = ? "
				+ "GROUP BY c.id";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			try {
				stmt.setInt(1, Integer.parseInt(searchTerm));
			} catch (NumberFormatException e) {
				stmt.setInt(1, -1); // Dummy value
			}

			stmt.setString(2, searchTerm);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					customer = new Customer();
					customer.setId(rs.getInt("id"));
					customer.setFirstName(rs.getString("first_name"));
					customer.setLastName(rs.getString("last_name"));
					customer.setEmail(rs.getString("email"));
					customer.setPhone(rs.getString("phone"));
					customer.setAddress(rs.getString("address"));
					customer.setUnitsConsumed(rs.getInt("units_consumed"));
					customer.setIsActive(rs.getBoolean("is_active"));

					String billsJson = rs.getString("bills");
					List<Bill> bills = new ArrayList<>();

					if (billsJson != null && !billsJson.equals("[null]")) {
						JSONArray billArray = new JSONArray(billsJson);
						for (int i = 0; i < billArray.length(); i++) {
							JSONObject billObj = billArray.getJSONObject(i);
							if (billObj.isNull("bill_id"))
								continue; // Skip null entries from LEFT JOIN
							Bill bill = new Bill();
							bill.setId(billObj.getInt("bill_id"));
							bill.setTotal(billObj.getDouble("total"));
							bill.setDate(LocalDate.parse(billObj.getString("date")));
							bill.setTime(LocalTime.parse(billObj.getString("time")));
							bill.setCustomer(null);
							bills.add(bill);
						}
					}

					customer.setBills(bills);
				}
			}
		}

		return customer;
	}

	public boolean deactivate(String id) {
		String sql = "UPDATE customer SET is_active = false WHERE id = ?";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			int affectedRows = stmt.executeUpdate();

			return affectedRows > 0; // return true if update succeeded
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean activate(String id) {
		String sql = "UPDATE customer SET is_active = true WHERE id = ?";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			int affectedRows = stmt.executeUpdate();

			return affectedRows > 0; // return true if update succeeded
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public PersistResult update(String id, String firstName, String lastName, String phone, String email,
			String address) {
		String sql = "UPDATE customer SET first_name = ?, last_name = ?, phone = ?, email = ?, address = ? WHERE id = ?";

		try (Connection conn = DbConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, phone);
			stmt.setString(4, email);
			stmt.setString(5, address);
			stmt.setString(6, id);

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
			System.err.println("Database error: " + e.getMessage());
			return PersistResult.OTHER_ERROR;
		}
	}

	public void increaseUnitsConsumed(int id, int unitsConsumed, Connection conn) throws SQLException {
		String sql = "UPDATE customer SET units_consumed = units_consumed + ? WHERE id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, unitsConsumed);
	        stmt.setInt(2, id);

	        int rowsAffected = stmt.executeUpdate();

	        if (rowsAffected == 0) {
	            throw new SQLException("Updating units consumed failed: no customer found with id " + id);
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
