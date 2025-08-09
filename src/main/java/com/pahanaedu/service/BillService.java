package com.pahanaedu.service;

import java.sql.SQLException;

import com.pahanaedu.dao.BillDao;
import com.pahanaedu.model.Bill;
import com.pahanaedu.model.enums.PersistResult;

public class BillService {
	private static BillService instance;
	private BillDao billDao;

	private BillService() {
		this.billDao = new BillDao();
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
	
	public PersistResult persist(Bill bill) {
		return null;		
	}

	public Bill byId(int id) throws SQLException {
		return null;
	}
}
