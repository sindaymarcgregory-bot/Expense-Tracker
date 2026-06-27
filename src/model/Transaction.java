package model;

public class Transaction {
    private String title;
    private String type;
    private double amount;
    private String category;
    private String date;

    public Transaction() {

    }

    public Transaction(String title, String type, double amount, String category, String date) {
        this.title = title;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
