package com.pahanaedu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahanaedu.dao.CategoryDao;
import com.pahanaedu.model.Category;

public class CategoryService {
	private static CategoryService instance;
	private CategoryDao categoryDao;

	private CategoryService() {
		this.categoryDao = new CategoryDao();
	}

	public static CategoryService getInstance() {
		if (instance == null) {
			synchronized (CategoryService.class) {
				if (instance == null) {
					instance = new CategoryService();
				}
			}
		}
		return instance;
	}

	public List<Category> all() throws SQLException {
		return categoryDao.all();
	}
}
