package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import com.pahanaedu.model.Bill;

public class BillDao {

	public Bill byId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int persist(Bill bill, Connection conn) throws SQLException {
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
	                return generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Creating bill failed, no ID obtained.");
	            }
	        }
	    }
	}

}
