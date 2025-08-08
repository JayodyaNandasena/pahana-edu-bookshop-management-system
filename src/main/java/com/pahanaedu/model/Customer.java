package com.pahanaedu.model;

import java.util.List;

public class Customer {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	private int unitsConsumed;
	private boolean isActive;
	private List<Bill> bills;

	public Customer() {
	}

	public Customer(int id, String firstName, String lastName, String email, String phone, String address,
			int unitsConsumed, boolean isActive, List<Bill> bills) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.unitsConsumed = unitsConsumed;
		this.isActive = isActive;
		this.bills = bills;
	}

	public Customer(int id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getUnitsConsumed() {
		return unitsConsumed;
	}

	public void setUnitsConsumed(int unitsConsumed) {
		this.unitsConsumed = unitsConsumed;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", unitsConsumed=" + unitsConsumed + ", isActive="
				+ isActive + ", bills=" + bills + "]";
	}

}
