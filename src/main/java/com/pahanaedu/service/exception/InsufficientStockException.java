package com.pahanaedu.service.exception;

public class InsufficientStockException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InsufficientStockException () {
        super("Insufficient stock for the requested quantity.");
    }
}