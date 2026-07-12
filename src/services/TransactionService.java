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
    
    public List<Transaction> getTransactionHistory(int userId) {
        try {
            return transactionDAO.getTransactionHistory(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<Transaction> getTransactionHistoryByType(int userId, String type) {
        try {
            return transactionDAO.getTransactionHistoryByType(userId, type);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<Transaction> searchTransactionHistory(int userId, String filter, String keyword) {
        try {
            return transactionDAO.searchTransactionHistory(userId, filter, keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}