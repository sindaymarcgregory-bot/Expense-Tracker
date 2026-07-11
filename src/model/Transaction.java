package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Transaction {

    
    
    private String type;
    private BigDecimal amount;
    private int categoryId;
    private String description;


    public Transaction(String type, BigDecimal amount, int categoryId,  String description) {
     
        this.type = type;
        this.amount = amount;
        this.categoryId = categoryId;
        this.description = description;
        
        
    }




    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}