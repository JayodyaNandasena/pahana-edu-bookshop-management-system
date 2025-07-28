package com.pahanaedu.service;

import java.sql.SQLException;

import com.pahanaedu.dao.LoginDao;
import com.pahanaedu.model.User;

public class LoginService {
	private static LoginService instance;
	private LoginDao loginDao;

	private LoginService() {
		this.loginDao = new LoginDao();
	}

	public static LoginService getInstance() {
		if (instance == null) {
			synchronized (LoginService.class) {
				if (instance == null) {
					instance = new LoginService();
				}
			}
		}
		return instance;
	}

	public User login(String username, String password) throws SQLException {
		return loginDao.authenticateUser(username, password);
	}

}
