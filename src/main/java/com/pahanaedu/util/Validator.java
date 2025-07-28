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
}
