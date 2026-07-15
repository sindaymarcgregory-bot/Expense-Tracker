package services;

import java.math.BigDecimal;

public class ValidationService {

    // Checks if a text field is empty.
    public boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
    
    // Checks if a number is greater than zero.
    public boolean isPositive(BigDecimal amount) {
        return amount != null
                && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    // Checks whether the entered value is numeric.
    public boolean isNumeric(String value) {

        if (isEmpty(value)) {
            return false;
        }

        try {
            new BigDecimal(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    //Checks if the text meets the minimum length.
    public boolean hasMinimumLength(String text, int minimumLength) {
        return text != null
                && text.trim().length() >= minimumLength;
    }
    
    // Checks if the text exceeds the maximum length.
    public boolean exceedsMaximumLength(String text, int maximumLength) {
        if (text == null) {
            return false;
        }
        return text.trim().length() > maximumLength;
    }
    
    // Checks if an email address has a valid format.
    public boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    
    // Checks if an ID is valid.
    public boolean isValidId(int id) {
        return id > 0;
    }
}