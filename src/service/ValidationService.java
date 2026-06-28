package service;

public class ValidationService {

    public boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public boolean isPositive(double amount) {
        return amount > 0;
    }

    public boolean passwordLength(String password) {
        return password.length() >= 6;
    }

}