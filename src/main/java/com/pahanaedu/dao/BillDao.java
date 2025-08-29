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
import java.util.ArrayList;
import java.util.List;

import com.pahanaedu.model.Bill;
import com.pahanaedu.model.BillItem;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.User;
import com.pahanaedu.util.DbConnectionFactory;

public class BillDao {

	public Bill byId(int id) throws SQLException {
	    Bill bill = null;

	    String sql = """
	        SELECT 
	            b.id AS bill_id, b.bill_date, b.bill_time, b.total, b.code, b.cashier_id, 
	            u.name AS cashier_name,
	            bi.id AS bill_item_id, bi.quantity, bi.unit_price,
	            i.id AS item_id, i.name AS item_name
	        FROM bill b
	        LEFT JOIN bill_item bi ON b.id = bi.bill_id
	        LEFT JOIN item i ON bi.item_id = i.id
	        LEFT JOIN user u ON b.cashier_id = u.id
	        WHERE b.id = ?
	    """;

	    try (Connection conn = DbConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, id);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                if (bill == null) {
	                    // initialize bill
	                    bill = new Bill();
	                    bill.setId(rs.getInt("bill_id"));
	                    bill.setDate(rs.getDate("bill_date").toLocalDate());
	                    bill.setTime(rs.getTime("bill_time").toLocalTime());
	                    bill.setTotal(rs.getDouble("total"));
	                    bill.setCode(rs.getString("code"));
	                    bill.setCashier(new User(rs.getInt("cashier_id"), rs.getString("cashier_name")));

	                    bill.setBillItems(new ArrayList<>());
	                }

	                int billItemId = rs.getInt("bill_item_id");
	                if (billItemId > 0) {
	                    BillItem billItem = new BillItem();
	                    billItem.setId(billItemId);
	                    billItem.setQuantity(rs.getInt("quantity"));
	                    billItem.setUnitPrice(rs.getDouble("unit_price"));

	                    // set item details
	                    Item item = new Item();
	                    item.setId(rs.getInt("item_id"));
	                    item.setName(rs.getString("item_name"));

	                    billItem.setItem(item);
	                    bill.getBillItems().add(billItem);
	                }
	            }
	        }
	    }

	    return bill;
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

	public Double getTotal() throws SQLException {
		String sql = "SELECT get_total_bill() AS total";

		try (Connection conn = DbConnectionFactory.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			if (rs.next()) {
				// Get the value as double
				return rs.getDouble("total");
			} else {
				return 0.0; // no rows
			}
		}
	}

	public Double getAverage() throws SQLException {
		String sql = "SELECT get_average_bill() AS average";

		try (Connection conn = DbConnectionFactory.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			if (rs.next()) {
				// Get the value as double
				return rs.getDouble("average");
			} else {
				return 0.0; // no rows
			}
		}
	}

	public List<Double> getMonthlyRevenues() throws SQLException {
		String sql = "SELECT MONTH(bill_date) AS month, SUM(total) AS revenue " + "FROM bill "
				+ "WHERE YEAR(bill_date) = YEAR(CURDATE()) " + "GROUP BY month " + "ORDER BY month";

		// Initialize list with 12 zeros (Jan=0, Feb=1, ..., Dec=11)
		List<Double> monthlyRevenues = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			monthlyRevenues.add(0.0);
		}

		try (Connection conn = DbConnectionFactory.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int month = rs.getInt("month"); // 1 = Jan, 2 = Feb, ...
				double revenue = rs.getDouble("revenue");
				monthlyRevenues.set(month - 1, revenue); // store at index 0-11
			}
		}

		return monthlyRevenues;
	}
}
