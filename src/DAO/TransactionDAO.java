package DAO;

import database.DBConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import model.Transaction;
import java.util.LinkedHashMap;//import needed for chart
import java.util.Map;

public class TransactionDAO {

    //adding the transaction
    public boolean addTransaction(Transaction transaction) throws SQLException {//create method

        String sql = """
                INSERT INTO transactions
                (user_id, category_id, type, transaction_date, amount, description)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, transaction.getUserId());
            stmt.setInt(2, transaction.getCategoryId());
            stmt.setString(3, transaction.getType());
            stmt.setDate(4, transaction.getTransactionDate());
            stmt.setBigDecimal(5, transaction.getAmount());
            stmt.setString(6, transaction.getDescription());

            return stmt.executeUpdate() > 0;

        }

    }//end of addTransaction

    
    //read all transactions of a user
    public List<Transaction> getAllTransactions(int userId) throws SQLException {

        List<Transaction> transactions = new ArrayList<>();

        String sql = """
        SELECT *
        FROM transactions
        WHERE user_id = ?
        ORDER BY transaction_date DESC, id DESC
        """;//sql command

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Transaction transaction = new Transaction();

                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setCategoryId(rs.getInt("category_id"));
                transaction.setType(rs.getString("type"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setDescription(rs.getString("description"));
                transaction.setCreatedAt(rs.getTimestamp("created_at"));
                transaction.setUpdatedAt(rs.getTimestamp("updated_at"));

                transactions.add(transaction);
            }
        }

        return transactions;
    }//end of getAllTransactions

    //read one / getTransactions
    public Transaction getTransactionById(int transactionId) throws SQLException {

        String sql = """
        SELECT *
        FROM transactions
        WHERE id = ?
        """;

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, transactionId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Transaction transaction = new Transaction();

                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setCategoryId(rs.getInt("category_id"));
                transaction.setType(rs.getString("type"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setDescription(rs.getString("description"));
                transaction.setCreatedAt(rs.getTimestamp("created_at"));
                transaction.setUpdatedAt(rs.getTimestamp("updated_at"));

                return transaction;
            }
        }

        return null;
    }//end of getTransactionById

    //retrieves all transactions of a specific type (Income or Expense)
    public List<Transaction> getTransactionsByType(int userId, String type) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        String sql = """
            SELECT *
            FROM transactions
            WHERE user_id = ?
            AND type = ?
            ORDER BY transaction_date DESC, id DESC
            """;

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();

                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setCategoryId(rs.getInt("category_id"));
                transaction.setType(rs.getString("type"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setDescription(rs.getString("description"));

                transactions.add(transaction);
            }
        }
        return transactions;
    }//end of getTransactionsByType

    //update transaction
    public boolean updateTransaction(Transaction transaction) throws SQLException {
        String sql = """
            UPDATE transactions
            SET category_id = ?,
                amount = ?,
                description = ?
            WHERE id = ?
            """;
        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, transaction.getCategoryId());
            stmt.setBigDecimal(2, transaction.getAmount());
            stmt.setString(3, transaction.getDescription());
            stmt.setInt(4, transaction.getId());

            return stmt.executeUpdate() > 0;
        }
    }//end of updateTransaction

    //delete transaction
    public boolean deleteTransaction(int id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }  // end of deleteTransaction

    
    //for the history panel method
    public List<Transaction> getTransactionHistory(int userId) throws SQLException {

        List<Transaction> history = new ArrayList<>();
        String sql = """
            SELECT *
            FROM transactions
            WHERE user_id = ?
            ORDER BY transaction_date DESC, id DESC
            """;
        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();

                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setCategoryId(rs.getInt("category_id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setDescription(rs.getString("description"));

                history.add(transaction);
            }
        }
        return history;
    }//end of getTransactionHistory

    //for filtering in the transaction panel
    public List<Transaction> getTransactionHistoryByType(int userId, String type) throws SQLException {
        List<Transaction> history = new ArrayList<>();
        String sql = """
            SELECT *
            FROM transactions
            WHERE user_id = ?
            AND type = ?
            ORDER BY transaction_date DESC, id DESC
            """;
        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();

                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setCategoryId(rs.getInt("category_id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setDescription(rs.getString("description"));

                history.add(transaction);
            }
        }
        return history;
    }//end of getTransactionHistoryByType method

    
    //for searchbar in the history panel
    public List<Transaction> searchTransactionHistory(int userId, String filter, String keyword) throws SQLException {
        List<Transaction> history = new ArrayList<>();

          //Base SQL query to retrieve transactions together with their category
        StringBuilder sql = new StringBuilder("""
            SELECT t.*
            FROM transactions t
            JOIN categories c
            ON t.category_id = c.id
            WHERE t.user_id = ?
            """);

         //if the user selected a specific filter (Income or Expense),
         //add a condition to only retrieve that transaction type.
        if (!filter.equalsIgnoreCase("All")) {
            sql.append(" AND t.type = ? ");
        }

         //Search the keyword in category name, description, or transaction type.
         //LOWER() makes the search case-insensitive.
         //LIKE allows partial matches using %keyword%.
        sql.append("""
            AND (
                LOWER(c.category_name) LIKE ?
                OR LOWER(t.description) LIKE ?
                OR LOWER(t.type) LIKE ?
            )
            ORDER BY t.transaction_date DESC, t.id DESC
            """);
        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql.toString());) {
            int index = 1;

            stmt.setInt(index++, userId);
             //If a specific filter was selected, set its value
            if (!filter.equalsIgnoreCase("All")) {
                stmt.setString(index++, filter.toLowerCase());
            }
             //Convert the keyword to lowercase and add wildcards
             //for partial matching in the LIKE clause.
            String search = "%" + keyword.toLowerCase() + "%";

            //set the search keyword for category name
            stmt.setString(index++, search);
            
            //set the search keyword for description
            stmt.setString(index++, search);
            
            
            //set the search keyword for transaction type
            stmt.setString(index++, search);
            
           
            ResultSet rs = stmt.executeQuery();
            
            //looping through the transactions
            while (rs.next()) {
                Transaction transaction = new Transaction();
                
                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setCategoryId(rs.getInt("category_id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setDescription(rs.getString("description"));

                history.add(transaction);
            }
        }
        return history;
    }// end of searchTransactionHistory

    
    //getting the total amount by each transaction type
    public BigDecimal getTotalByType(int userId, String type) throws SQLException {

        //SQL query that adds up all transaction amounts.
        String sql = """
        SELECT COALESCE(SUM(amount),0) AS total
        FROM transactions
        WHERE user_id = ?
        AND type = ?
        """; //COALESCE() returns 0 instead of NULL when no matching transactions exist.

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, userId);
            stmt.setString(2, type);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        }

        return BigDecimal.ZERO;
    }//end of getTotalByType

    /**
     * Calculates the current balance of the logged-in user. Balance = Total
     * Income - Total Expense
     *
     * @param userId ID of the logged-in user
     * @return Current balance
     */
    
    //getting the current balance
    public BigDecimal getCurrentBalance(int userId) throws SQLException {

        // Get total income.
        BigDecimal income = getTotalByType(userId, "income");

        // Get total expense.
        BigDecimal expense = getTotalByType(userId, "expense");

        // Return Income - Expense.
        return income.subtract(expense);

    }//end of getCurrentBalance

    
    //for the transaction count in the table of dashboard
    public int getTransactionCount(int userId) throws SQLException {

        String sql = """
        SELECT COUNT(*) AS total
        FROM transactions
        WHERE user_id = ?
        """;//COUNT(*) used for getting the total count of how many times each category is used in transaction

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
        }

        return 0;
    }//end of getTransactionCount

    ////counts how many unique categories the user has used in their transactions
    public int getCategoryCount(int userId) throws SQLException {

        String sql = """
        SELECT COUNT(DISTINCT category_id) AS total
        FROM transactions
        WHERE user_id = ?
        """;

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
        }

        return 0;
    }//end of getCategoryCount

   
   
    //for the dashboard panel chart
    public LinkedHashMap<String, Double> getExpenseCategoryPercentages(int userId)
            throws SQLException {

         //LinkedHashMap preserves the order in which data is inserted.
        LinkedHashMap<String, Double> data = new LinkedHashMap<>();

        String sql = """
        SELECT c.category_name,
               SUM(t.amount) AS total
        FROM transactions t
        JOIN categories c
             ON t.category_id = c.id
        WHERE t.user_id = ?
          AND t.type = 'expense'
        GROUP BY c.category_name
        ORDER BY total DESC
    """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            double grandTotal = 0;

            ArrayList<String> names = new ArrayList<>();
            ArrayList<Double> totals = new ArrayList<>();

            while (rs.next()) {

                String category = rs.getString("category_name");
                double total = rs.getDouble("total");

                names.add(category);
                totals.add(total);
                
                grandTotal += total;
            }
             //calculate the percentage contribution of each category
            for (int i = 0; i < names.size(); i++) {

                double percent = (totals.get(i) / grandTotal) * 100;

                data.put(names.get(i), percent);
            }
        }

        return data;
    }//end of getExpenseCategoryPercentages

    //for expenseOverviewTable
    public List<Object[]> getExpenseCategorySummary(int userId) throws SQLException {
        List<Object[]> list = new ArrayList<>();

        String sql
                = "SELECT c.category_name, "
                + "SUM(t.amount) total_amount, "
                + "COUNT(*) transaction_count "
                + "FROM transactions t "
                + "JOIN categories c ON t.category_id = c.id "
                + "WHERE t.user_id=? AND t.type='expense' "
                + "GROUP BY c.category_name "
                + "ORDER BY total_amount DESC";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        double grandTotal = 0;

        //temporary list to hold category data before calculating percentages
        ArrayList<Object[]> temp = new ArrayList<>();

         //read each category's summary
        while (rs.next()) {
            String category = rs.getString("category_name");
            double amount = rs.getDouble("total_amount");
            int count = rs.getInt("transaction_count");

            grandTotal += amount;

            temp.add(new Object[]{
                category,
                amount,
                count
            });
        }
        
          //calculate the percentage for each category
        for (Object[] row : temp) {
            double amount = (double) row[1];
            
              //compute the percentage of the total expenses
            double percent = amount / grandTotal * 100;

            list.add(new Object[]{
                row[0],
                amount,
                percent,
                row[2]
            });
        }

        return list;
    }//end of getExpenseCategorySummary
}
