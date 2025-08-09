package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.pahanaedu.model.Bill;

public class BillDao {

	public Bill byId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Bill persist(Bill bill, Connection conn) throws SQLException {
		String sql = "INSERT INTO bill (bill_date, bill_time, total, customer_id, cashier_id) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setDate(1, Date.valueOf(bill.getDate()));
			stmt.setTime(2, Time.valueOf(bill.getTime()));
			stmt.setDouble(3, bill.getTotal());
			stmt.setInt(4, bill.getCustomer().getId());
			stmt.setInt(5, bill.getCashier().getId());

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Creating bill failed, no rows affected.");
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					Bill savedBill = new Bill();

					int billId = generatedKeys.getInt(1);

					savedBill.setId(billId);
					savedBill.setCode(updateBillCode(billId, bill.getDate(), bill.getTime(), conn));

					return savedBill;
				} else {
					throw new SQLException("Creating bill failed, no ID obtained.");
				}
			}
		}
	}

	private String updateBillCode(int id, LocalDate date, LocalTime time, Connection conn) throws SQLException {
		String sql = "UPDATE bill SET code = ? WHERE id = ?";

		// Format date and time as strings
		String datePart = date.format(DateTimeFormatter.BASIC_ISO_DATE); // YYYYMMDD
		String timePart = time.format(DateTimeFormatter.ofPattern("HHmmss")); // HHMMSS

		// Generate unique bill code
		String billCode = String.format("BILL-%s-%s-%04d", datePart, timePart, id);

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, billCode);
			stmt.setInt(2, id);

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Updating bill code failed, no rows affected.");
			}

			return billCode;
		}
	}
}
