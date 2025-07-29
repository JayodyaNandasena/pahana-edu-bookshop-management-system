package com.pahanaedu.service;

import com.pahanaedu.dao.CustomersDao;
import com.pahanaedu.model.enums.PersistResult;

public class CustomersService {
	private static CustomersService instance;
	private CustomersDao customersDao;

	private CustomersService() {
		this.customersDao = new CustomersDao();
	}

	public static CustomersService getInstance() {
		if (instance == null) {
			synchronized (CustomersService.class) {
				if (instance == null) {
					instance = new CustomersService();
				}
			}
		}
		return instance;
	}

	public PersistResult persist(String firstName, String lastName, String phone, String email, String address) {
		return customersDao.persist(firstName, lastName, phone, email, address);
	}
}
