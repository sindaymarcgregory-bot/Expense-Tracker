package model;

import java.math.BigDecimal;

public class Expense {


    private int categoryId;
    private BigDecimal amount;
    private String description;
    

    public Expense() {

    }

    public Expense( int categoryId, BigDecimal amount, String description) {
        
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
    }

   

   

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategory(String category) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
    public String getDescription(){
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}