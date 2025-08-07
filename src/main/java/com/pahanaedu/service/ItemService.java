package com.pahanaedu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahanaedu.dao.ItemDao;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.enums.PersistResult;

public class ItemService {
	private static ItemService instance;
	private ItemDao itemDao;

	private ItemService() {
		this.itemDao = new ItemDao();
	}

	public static ItemService getInstance() {
		if (instance == null) {
			synchronized (ItemService.class) {
				if (instance == null) {
					instance = new ItemService();
				}
			}
		}
		return instance;
	}

	public List<Item> all() throws SQLException {
		return itemDao.all();
	}

	public List<Item> byCategory(int categoryId) throws SQLException {
		return itemDao.byCategory(categoryId);
	}

	public List<Item> byIdOrName(String searchTerm) throws SQLException {
		return itemDao.byIdOrName(searchTerm);
	}

	public boolean delete(int itemId) {
		return itemDao.delete(itemId);
	}

	public PersistResult persist(String name, int category, double price, int quantity) {
		return itemDao.persist(name, category, price, quantity);
	}
}
