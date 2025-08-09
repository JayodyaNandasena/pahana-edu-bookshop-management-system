package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import com.pahanaedu.model.BillItem;
import com.pahanaedu.service.exception.UniqueConstraintViolationException;

public class BillItemDao {

	public BillItem byId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void persist(BillItem item, Connection conn) throws SQLException {
		String sql = "INSERT INTO bill_item (bill_id, item_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, item.getBill().getId());
			stmt.setInt(2, item.getItem().getId());
			stmt.setInt(3, item.getQuantity());
			stmt.setDouble(4, item.getUnitPrice());

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Creating bill item failed: no rows affected.");
			}

		} catch (SQLException e) {
			if (isUniqueConstraintViolation(e)) {
				throw new UniqueConstraintViolationException("The item with ID " + item.getItem().getId()
						+ " has already been added to bill ID " + item.getBill().getId());
			} else {
				throw e; // rethrow other SQL errors
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
