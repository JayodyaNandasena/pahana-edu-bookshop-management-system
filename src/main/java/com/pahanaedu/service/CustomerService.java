package com.pahanaedu.service;

import java.sql.SQLException;

import com.pahanaedu.dao.CustomerDao;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.enums.PersistResult;

public class CustomerService {
	private static CustomerService instance;
	private CustomerDao customerDao;

	private CustomerService() {
		this.customerDao = new CustomerDao();
	}

	public static CustomerService getInstance() {
		if (instance == null) {
			synchronized (CustomerService.class) {
				if (instance == null) {
					instance = new CustomerService();
				}
			}
		}
		return instance;
	}

	public PersistResult persist(String firstName, String lastName, String phone, String email, String address) {
		return customerDao.persist(firstName, lastName, phone, email, address);
	}

	public Customer getByMobile(String mobile) throws SQLException {
		return customerDao.getByMobile(mobile);
	}
}
