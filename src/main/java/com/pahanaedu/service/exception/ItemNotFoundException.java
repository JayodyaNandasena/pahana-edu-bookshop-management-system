package com.pahanaedu.service.exception;

public class ItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException() {
        super("Item not found.");
    }
}