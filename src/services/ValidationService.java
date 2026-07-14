package services;

import java.math.BigDecimal;

public class ValidationService {

    public boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public boolean isPositive(BigDecimal amount) {
        return amount != null &&
             amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean passwordLength(String password) {
        return password.length() >= 6;
    }

}