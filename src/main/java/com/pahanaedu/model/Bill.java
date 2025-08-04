package com.pahanaedu.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Bill {
	private long id;
	private LocalDate date;
	private LocalTime time;
	private double total;
	private Customer customer;
	private User cashier;

	public Bill() {
	}

	public Bill(long id, LocalDate date, LocalTime time, double total, Customer customer, User cashier) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.total = total;
		this.customer = customer;
		this.cashier = cashier;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
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
