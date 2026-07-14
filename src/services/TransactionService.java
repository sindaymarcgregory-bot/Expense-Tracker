package services;

import DAO.TransactionDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import model.Transaction;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();//declaring the object DAO
    private final ValidationService validationService = new ValidationService();

    //start of the addTransaction or Create
    public boolean addTransaction(Transaction transaction) throws SQLException {

        // Validate amount
        if (!validationService.isPositive(transaction.getAmount())) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Validate category
        if (transaction.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Please select a category.");
        }

         // Validate description
        if (validationService.isEmpty(transaction.getDescription())) {
            throw new IllegalArgumentException("Please enter a description.");
        }

        // If this is an expense, check available balance
        if (transaction.getType().equalsIgnoreCase("expense")) {

            BigDecimal balance = transactionDAO.getCurrentBalance(transaction.getUserId());

            if (transaction.getAmount().compareTo(balance) > 0) {
                throw new IllegalArgumentException("Insufficient balance.\n\nAvailable Balance: ₱" + balance);
            }
        }
        return transactionDAO.addTransaction(transaction);
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

    }
    
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

    }

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

    //start of updateTransaction
    public boolean updateTransaction(Transaction transaction) {

        // Validate category.
        if (transaction.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Please select a category.");
        }

        // Validate amount.
        if (!validationService.isPositive(transaction.getAmount())) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Validate description.
        if (validationService.isEmpty(transaction.getDescription())) {
            throw new IllegalArgumentException("Please enter a description.");
        }

        try {

            // Retrieve the original transaction.
            Transaction oldTransaction = transactionDAO.getTransactionById(transaction.getId());

            if (oldTransaction == null) {
                throw new IllegalArgumentException("Transaction not found.");
            }

            // Check balance only for expense transactions.
            if (oldTransaction.getType().equalsIgnoreCase("expense")) {

                BigDecimal balance = transactionDAO.getCurrentBalance(oldTransaction.getUserId());

                // Restore the previous expense before comparing.
                balance = balance.add(oldTransaction.getAmount());

                if (transaction.getAmount().compareTo(balance) > 0) {
                    throw new IllegalArgumentException(
                            "Insufficient balance.\n\nAvailable Balance: ₱"
                            + balance);
                }
            }
            return transactionDAO.updateTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to update transaction.");
        }
    }   

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

