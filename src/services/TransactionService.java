package services;

import DAO.TransactionDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();//declaring the object DAO

    //start of the addTransaction
    public boolean addTransaction(Transaction transaction) {

        // Category validation
        if (transaction.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Please select a category.");
        }

        // Amount validation
        if (transaction.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required.");
        }

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        try {

            //Check balance only for expense transactions
            if (transaction.getType().equalsIgnoreCase("expense")) {

                BigDecimal currentBalance
                        = transactionDAO.getCurrentBalance(transaction.getUserId());

                if (transaction.getAmount().compareTo(currentBalance) > 0) {
                    throw new IllegalArgumentException("Insufficient balance.");
                }
            }
            //Save the transaction
            return transactionDAO.addTransaction(transaction);

        } catch (SQLException e) {

            e.printStackTrace();
            throw new RuntimeException("Unable to save transaction.");

        }

    }

    //read all
    public List<Transaction> getAllTransactions(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user.");
        }

        try {

            return transactionDAO.getAllTransactions(userId);

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException("Unable to load transactions.");

        }

    }//end of readAll

    //start of updateTransaction
    public boolean updateTransaction(Transaction transaction) {

        // if the user did not select any category
        if (transaction.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Please select a category.");
        }
        //if there are no value in amount field
        if (transaction.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required.");
        }
        //if the amount is less than or equal to zero
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        try {

            return transactionDAO.updateTransaction(transaction);//calling the update method for updating

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException("Unable to update transaction.");//notice the user of error

        }

    }//end of updateTransaction

    //start of deleteTransaction
    public boolean deleteTransaction(int id) {

        //if the user did not select any transaction
        if (id <= 0) {
            throw new IllegalArgumentException("Please select a transaction first.");
        }

        try {

            return transactionDAO.deleteTransaction(id);//calling the method that deletes the data

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException("Unable to delete transaction.");//notice the user of error

        }

    }//end of deleteTransaction

    //start of getTransactionHistory / readAll
    public List<Transaction> getTransactionHistory(int userId) {

        //if the user is not selecting any transaction item
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user.");
        }

        try {
            return transactionDAO.getTransactionHistory(userId);//getting all the transaction item
        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException("Unable to load transaction history.");

        }

    }//end of getTransactionHistory / readAll

    //getting the transaction history item for the history panel
    public List<Transaction> getTransactionHistoryByType(int userId, String type) {

        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user.");//i
        }

        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction type is required.");
        }

        try {

            return transactionDAO.getTransactionHistoryByType(userId, type);

        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException("Unable to load transaction history.");

        }

    }

   
    
// Search transaction history using a filter and keyword
public List<Transaction> searchTransactionHistory(int userId, String filter, String keyword) {

        //Validate the user ID
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user.");
        }

        //Validate the selected filter
        if (filter == null || filter.trim().isEmpty()) {
            throw new IllegalArgumentException("Please select a search filter.");
        }

        //Validate the search keyword
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a keyword.");
        }

        try {

            //Call the DAO to search the transactions in the database
            return transactionDAO.searchTransactionHistory(userId, filter, keyword);

        } catch (SQLException e) {

            //Print the database error for debugging
            e.printStackTrace();

            //Notify the GUI that the search operation failed
            throw new RuntimeException("Unable to search transactions.");

        }

    }
    

    
   }//end of class

