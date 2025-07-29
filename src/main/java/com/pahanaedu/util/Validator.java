package com.pahanaedu.util;

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
}
