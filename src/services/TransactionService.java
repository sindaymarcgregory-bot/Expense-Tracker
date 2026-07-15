package services;

import DAO.TransactionDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import model.Transaction;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();//declaring the object DAO
    private final ValidationService validationService = new ValidationService();

    
    // Validate transaction information
    private void validateTransaction(Transaction transaction) {

        // Validate transaction object
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }

        // Validate user ID
        if (!validationService.isValidId(transaction.getUserId())) {
            throw new IllegalArgumentException("Invalid user.");
        }

        // Validate category
        if (!validationService.isValidId(transaction.getCategoryId())) {
            throw new IllegalArgumentException("Please select a category.");
        }

        // Validate amount
        if (!validationService.isPositive(transaction.getAmount())) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Validate description
        if (validationService.isEmpty(transaction.getDescription())) {
            throw new IllegalArgumentException("Please enter a description.");
        }

        // Validate description length
        if (validationService.exceedsMaximumLength(transaction.getDescription(), 255)) {
            throw new IllegalArgumentException("Description cannot exceed 255 characters.");
        }

        // Validate transaction type
        if (validationService.isEmpty(transaction.getType())) {
            throw new IllegalArgumentException("Transaction type is required.");
        }
    }
        
    // Create a new transaction
    public boolean addTransaction(Transaction transaction) {

        // Validate transaction
        validateTransaction(transaction);

        try {
            // Check available balance for expenses
            if (transaction.getType().equalsIgnoreCase("expense")) {
                 BigDecimal balance = transactionDAO.getCurrentBalance(
                transaction.getUserId());
                if (transaction.getAmount().compareTo(balance) > 0) {
                    throw new IllegalArgumentException(
                            "Insufficient balance.\n\nAvailable Balance: ₱"
                            + balance);
                }
            }
             return transactionDAO.addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add transaction.");
        }
    }   
    
    // Retrieve the complete transaction history of a user
    public List<Transaction> getTransactionHistory(int userId) {

        // Validate user ID
        if (!validationService.isValidId(userId)) {
            throw new IllegalArgumentException("Invalid user.");
        }

        try {
            // Retrieve all transactions from the database
            return transactionDAO.getTransactionHistory(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load transaction history.");
        }
    }
    
    // Retrieve transaction history based on transaction type
    public List<Transaction> getTransactionHistoryByType(int userId, String type) {

        // Validate user ID
        if (!validationService.isValidId(userId)) {
            throw new IllegalArgumentException("Invalid user.");
        }

        // Validate transaction type
        if (validationService.isEmpty(type)) {
            throw new IllegalArgumentException("Transaction type is required.");
        }

        try {
            // Retrieve transactions by type
            return transactionDAO.getTransactionHistoryByType(userId, type);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load transaction history.");
        }
    }
    
    // Update an existing transaction
    public boolean updateTransaction(Transaction transaction) {

        // Validate transaction information
        validateTransaction(transaction);

        try {

            // Retrieve the original transaction
            Transaction oldTransaction = transactionDAO.getTransactionById(transaction.getId());

            // Check if transaction exists
            if (oldTransaction == null) {
                throw new IllegalArgumentException("Transaction not found.");
            }

            // Validate balance only for expense transactions
            if (oldTransaction.getType().equalsIgnoreCase("expense")) {

                BigDecimal balance =
                        transactionDAO.getCurrentBalance(oldTransaction.getUserId());

                // Restore the original expense before comparing
                 balance = balance.add(oldTransaction.getAmount());

                if (transaction.getAmount().compareTo(balance) > 0) {

                    throw new IllegalArgumentException(
                            "Insufficient balance.\n\nAvailable Balance: ₱"
                            + balance
                    );
                }
            }

        // Update transaction
        return transactionDAO.updateTransaction(transaction);
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Unable to update transaction.");
    }
}

    // Delete a transaction
    public boolean deleteTransaction(int id) {

        //if the user did not select any transaction
        // Validate transaction ID
        if (!validationService.isValidId(id)) {
            throw new IllegalArgumentException("Please select a transaction first.");
        }

        try {

            return transactionDAO.deleteTransaction(id);// Delete the transaction from the database
        } catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException("Unable to delete transaction.");//notice the user of error

        }

    }//end of deleteTransaction 
    
    // Search transaction history using a filter and keyword
    public List<Transaction> searchTransactionHistory(int userId, String filter, String keyword) {

        // Validate user ID
        if (!validationService.isValidId(userId)) {
            throw new IllegalArgumentException("Invalid user.");
        }

        //Validate the selected filter
        if (validationService.isEmpty(filter)) {
            throw new IllegalArgumentException("Please select a search filter.");
        }

        // Validate search keyword
        if (validationService.isEmpty(keyword)) {
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

