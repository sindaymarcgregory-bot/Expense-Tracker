package services;

import DAO.TransactionDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;

public class TransactionService {
    private final TransactionDAO transactionDAO = new TransactionDAO();
    
    public boolean addTransaction(Transaction transaction) {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        try {
            return transactionDAO.addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Transaction> getTransactionsByType(int userId, String type) {
        try {
            return transactionDAO.getTransactionsByType(userId, type);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public boolean updateTransaction(Transaction transaction) {
    try {
        return transactionDAO.updateTransaction(transaction);
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }
    }
    
    public boolean deleteTransaction(int id) {
        try {
            return transactionDAO.deleteTransaction(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}