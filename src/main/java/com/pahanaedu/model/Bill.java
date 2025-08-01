package com.pahanaedu.model;

public class Bill {
	private int id;
	private String date;
	private String time;
	private double total;
	private Customer customer;
	private User cashier;

	public Bill() {
	}

	public Bill(int id, String date, String time, double total, Customer customer, User cashier) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.total = total;
		this.customer = customer;
		this.cashier = cashier;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getCashier() {
		return cashier;
	}

	public void setCashier(User cashier) {
		this.cashier = cashier;
	}

}