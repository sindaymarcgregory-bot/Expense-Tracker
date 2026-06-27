package model;

public class Account {
     private User user;
    private double balance;
    private double totalIncome;
    private double totalExpense;
    private double savings;

    public Account() {

    }

    public Account(User user) {
        this.user = user;
        this.balance = 0;
        this.totalIncome = 0;
        this.totalExpense = 0;
        this.savings = 0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getSavings() {
        return savings;
    }

    public void addIncome(double amount) {
        totalIncome += amount;
        balance += amount;
        savings = balance;
    }

    public void addExpense(double amount) {
        totalExpense += amount;
        balance -= amount;
        savings = balance;
    }
}
