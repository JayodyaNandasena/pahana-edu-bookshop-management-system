package utilTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.pahanaedu.util.Validator;

public class ValidatorTest {

    // isValidString(String input) default min=1 max=Integer.MAX_VALUE
    @Test
    public void testIsValidString_default() {
        assertTrue(Validator.isValidString("hello"));
        assertFalse(Validator.isValidString(null));
        assertFalse(Validator.isValidString(""));
        assertFalse(Validator.isValidString("   "));
    }

    // isValidString(String input, int min, int max)
    @Test
    public void testIsValidString_withMinMax() {
        assertTrue(Validator.isValidString("abc", 2, 5));  // length 3 in range
        assertFalse(Validator.isValidString("a", 2, 5));   // length 1 < min
        assertFalse(Validator.isValidString("abcdef", 2, 5)); // length 6 >= max (exclusive)
        assertFalse(Validator.isValidString(null, 1, 3));
        assertFalse(Validator.isValidString("   ", 1, 3));
    }

    // isValidEmail(String email)
    @Test
    public void testIsValidEmail() {
        assertTrue(Validator.isValidEmail("test@example.com"));
        assertTrue(Validator.isValidEmail("user.name-123@sub.domain.org"));
        assertFalse(Validator.isValidEmail("no-at-symbol"));
        assertFalse(Validator.isValidEmail("missingdomain@"));
        assertFalse(Validator.isValidEmail("@missinguser.com"));
        assertFalse(Validator.isValidEmail(null));
        assertFalse(Validator.isValidEmail("  "));
        assertFalse(Validator.isValidEmail("test@domain.c")); // domain suffix < 2 chars
    }

    // isValidPhoneNumber(String phoneNumber)
    @Test
    public void testIsValidPhoneNumber() {
        assertTrue(Validator.isValidPhoneNumber("0701234567"));
        assertTrue(Validator.isValidPhoneNumber("0717654321"));
        assertFalse(Validator.isValidPhoneNumber("0601234567")); // invalid prefix
        assertFalse(Validator.isValidPhoneNumber("070123456"));  // only 9 digits total
        assertFalse(Validator.isValidPhoneNumber("07012345678")); // 11 digits
        assertFalse(Validator.isValidPhoneNumber(null));
        assertFalse(Validator.isValidPhoneNumber("  "));
        assertFalse(Validator.isValidPhoneNumber("07a1234567")); // contains letter
        assertFalse(Validator.isValidPhoneNumber("-1")); // contains negative value
    }

    // isValidCustomerIdOrPhone(String input)
    @Test
    public void testIsValidCustomerIdOrPhone() {
        // Valid positive customer IDs
        assertTrue(Validator.isValidCustomerIdOrPhone("1"));
        assertTrue(Validator.isValidCustomerIdOrPhone("123456789"));

        // Invalid customer IDs (negative, zero, non-numeric)
        assertFalse(Validator.isValidCustomerIdOrPhone("0"));
        assertFalse(Validator.isValidCustomerIdOrPhone("-1"));
        assertFalse(Validator.isValidCustomerIdOrPhone("abc123"));

        // Valid phone numbers
        assertTrue(Validator.isValidCustomerIdOrPhone("0701234567"));
        assertTrue(Validator.isValidCustomerIdOrPhone("0789876543"));

        // Invalid phone numbers
        assertFalse(Validator.isValidCustomerIdOrPhone("abc"));
        assertFalse(Validator.isValidCustomerIdOrPhone(""));

        // Null or blank
        assertFalse(Validator.isValidCustomerIdOrPhone(null));
        assertFalse(Validator.isValidCustomerIdOrPhone("   "));
    }
}
