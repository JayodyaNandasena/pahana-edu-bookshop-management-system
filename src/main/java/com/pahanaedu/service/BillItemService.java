package com.pahanaedu.service;

import java.sql.SQLException;
import com.pahanaedu.dao.BillItemDao;
import com.pahanaedu.model.BillItem;

public class BillItemService {
	private static BillItemService instance;
	private BillItemDao billItemDao;

	private BillItemService() {
		this.billItemDao = new BillItemDao();
	}

	public static BillItemService getInstance() {
		if (instance == null) {
			synchronized (BillItemService.class) {
				if (instance == null) {
					instance = new BillItemService();
				}
			}
		}
		return instance;
	}

	public BillItem byId(int id) throws SQLException {
		return null;
	}
}
