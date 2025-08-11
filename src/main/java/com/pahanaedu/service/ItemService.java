package com.pahanaedu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahanaedu.dao.ItemDao;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.exception.InsufficientStockException;
import com.pahanaedu.service.exception.ItemNotFoundException;

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

	public Item byId(int id, int quantity) throws SQLException {
		Item item = itemDao.byId(id);

		if (item == null) {
			throw new ItemNotFoundException();
		}

		if (quantity > item.getQuantityAvailable()) {
			throw new InsufficientStockException();
		}

		return item;
	}

	public boolean delete(int itemId) {
		return itemDao.delete(itemId);
	}
	
	public boolean restore(int itemId) {
		return itemDao.restore(itemId);
	}

	public PersistResult persist(String name, int category, double price, int quantity) {
		return itemDao.persist(name, category, price, quantity);
	}

	public PersistResult update(int id, String name, int category, double price, int quantity) {
		return itemDao.update(id, name, category, price, quantity);
	}

	public List<Item> getDeleted() throws SQLException {
		return itemDao.getDeleted();
	}
}
