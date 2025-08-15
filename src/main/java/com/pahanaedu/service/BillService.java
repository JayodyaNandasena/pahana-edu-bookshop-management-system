package com.pahanaedu.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pahanaedu.dao.BillDao;
import com.pahanaedu.dao.BillItemDao;
import com.pahanaedu.dao.CustomerDao;
import com.pahanaedu.dao.ItemDao;
import com.pahanaedu.model.Bill;
import com.pahanaedu.model.BillItem;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.User;
import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.util.DbConnectionFactory;

public class BillService {
	private static BillService instance;
	private BillDao billDao;
	private BillItemDao billItemDao;
	private ItemDao itemDao;
	private CustomerDao customerDao;

	private BillService() {
		this.billDao = new BillDao();
		this.billItemDao = new BillItemDao();
		this.itemDao = new ItemDao();
		this.customerDao = new CustomerDao();
	}

	public static BillService getInstance() {
		if (instance == null) {
			synchronized (BillService.class) {
				if (instance == null) {
					instance = new BillService();
				}
			}
		}
		return instance;
	}

	public Bill byId(int id) throws SQLException {
		return null;
	}

	public Bill persist(int customerId, JSONArray itemsJsonArray, String date, String time, double total,
			int cashierId) throws SQLException {
		int unitsConsumed = 0;
		List<BillItem> billItems = new ArrayList<>();

		for (int i = 0; i < itemsJsonArray.length(); i++) {
	        JSONObject itemJson = itemsJsonArray.getJSONObject(i);
	        
	        int quantity = itemJson.getInt("quantity");

	        BillItem billItem = new BillItem();
	        billItem.setItem(new Item(itemJson.getInt("id")));
	        billItem.setQuantity(quantity);
	        billItem.setUnitPrice(itemJson.getDouble("unitPrice"));

	        billItems.add(billItem);
	        
	        unitsConsumed += quantity;
	    }
		
		Bill bill = new Bill();
		
		bill.setDate(LocalDate.parse(date));
		bill.setTime(LocalTime.parse(time));
		bill.setTotal(total);
		bill.setCustomer(new Customer(customerId));
		bill.setCashier(new User(cashierId));		
		
		Connection conn = DbConnectionFactory.getConnection();
		try {
			conn.setAutoCommit(false);
			
			// 1. Add bill
            Bill savedBill = billDao.persist(bill, conn);

            // 2. Add bill items with billId
            for (BillItem item : billItems) {
                item.setBill(new Bill(savedBill.getId()));
                billItemDao.persist(item, conn);
            }

            // 3. Reduce quantity for each item
            for (BillItem item : billItems) {
                itemDao.reduceQuantity(item.getItem().getId(), item.getQuantity(), conn);
            }

            // 4. Increase units consumed for customer
            customerDao.increaseUnitsConsumed(bill.getCustomer().getId(), unitsConsumed, conn);

            conn.commit();  // Commit transaction
            
            return savedBill;
		} catch (SQLException e) {
			if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;  // rethrow exception after rollback
		}
	}

	public Double getTotalRevenue() throws SQLException {
		// TODO Auto-generated method stub
		return billDao.getTotal();
	}

	public Double getAverageBillValue() throws SQLException {
		return billDao.getAverage();
	}
}
