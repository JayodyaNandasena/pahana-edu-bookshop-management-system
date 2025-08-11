package com.pahanaedu.service.exception;

public class InactiveCustomerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InactiveCustomerException() {
		super("Customer account is inactive. Please reactivate the customer via the Customers page.");
	}
}