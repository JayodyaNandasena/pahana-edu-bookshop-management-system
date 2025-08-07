package com.pahanaedu.util;

import java.math.BigDecimal;

public class Validator {
	public static boolean isValidString(String input) {
		return isValidString(input, 1, Integer.MAX_VALUE); // Default min = 1, max = unlimited
	}

	public static boolean isValidString(String input, int min, int max) {
		if (input != null && !input.trim().isEmpty()) {
			int length = input.length();
			return length >= min && length < max;
		}
		return false;
	}

	public static boolean isValidEmail(String email) {
		if (email != null && !email.trim().isEmpty()) {
			String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
			return email.matches(emailRegex);
		}
		return false;
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
			String mobileRegex = "^(070|071|072|075|076|077|078)\\d{7}$";
			return phoneNumber.matches(mobileRegex);
		}
		return false;
	}
	
	public static boolean isValidCustomerIdOrPhone(String input) {
		if (input == null || input.trim().isEmpty()) {
			return false;
		}
		input = input.trim();

		try {
			long customerId = Long.parseLong(input);
			if (customerId > 0) {
				return true;
			}
		} catch (NumberFormatException e) {
			// Not a valid long number, try phone number
		}

		// Check if it's a valid phone number
		return isValidPhoneNumber(input);
	}
	
	public static boolean isValidDigit(int input, int min, int max) {
		return input>=min && input<=max;
	}
	
	public static boolean isValidPrice(double price, double min, double max) {
	    if (price <= min || price > max) {
	        return false;
	    }

	    // Check for at most two decimal places
	    BigDecimal bd = BigDecimal.valueOf(price);
	    return bd.scale() <= 2;
	}

}
