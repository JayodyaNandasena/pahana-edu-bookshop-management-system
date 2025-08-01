package com.pahanaedu.model;

public class Item {
	private int id;
	private String name;
	private double unitPrice;
	private int quantityAvailable;
	private Category category;

	public Item() {
	}

	public Item(int id, String name, double unitPrice, int quantityAvailable, Category category) {
		this.id = id;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantityAvailable = quantityAvailable;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
