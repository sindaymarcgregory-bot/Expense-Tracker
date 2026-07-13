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

public class TransactionDAO {

    public boolean addTransaction(Transaction transaction) throws SQLException {

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

    }

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
    }

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
    }

    public boolean deleteTransaction(int id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

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
    }

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
    }

    public List<Transaction> searchTransactionHistory(int userId, String filter, String keyword) throws SQLException {
        List<Transaction> history = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT t.*
            FROM transactions t
            JOIN categories c
            ON t.category_id = c.id
            WHERE t.user_id = ?
            """);

        if (!filter.equalsIgnoreCase("All")) {
            sql.append(" AND t.type = ? ");
        }

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

            if (!filter.equalsIgnoreCase("All")) {
                stmt.setString(index++, filter.toLowerCase());
            }
            String search = "%" + keyword.toLowerCase() + "%";

            stmt.setString(index++, search);
            stmt.setString(index++, search);
            stmt.setString(index++, search);

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
    }

    public BigDecimal getTotalByType(int userId, String type) throws SQLException {

        String sql = """
        SELECT COALESCE(SUM(amount),0) AS total
        FROM transactions
        WHERE user_id = ?
        AND type = ?
        """;

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
    }

    public int getTransactionCount(int userId) throws SQLException {

        String sql = """
        SELECT COUNT(*) AS total
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
    }

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
    }

    /**
     * Retrieves the five most recent transactions of the current user. This
     * method is used by the Dashboard to display the latest transactions in the
     * Recent Transactions table.
     */
    public List<Transaction> getRecentTransactions(int userId) throws SQLException {

        // Create an empty list that will store the transactions.
        List<Transaction> transactions = new ArrayList<>();

        // SQL query:
        // Select all transaction records belonging to the current user.
        // Sort them from newest to oldest.
        // Only retrieve the latest 5 records.
        String sql = """
        SELECT *
        FROM transactions
        WHERE user_id = ?
        ORDER BY transaction_date DESC, id DESC
        LIMIT 5
        """;

        // Open a database connection.
        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            // Replace the ? in the SQL statement with the logged-in user's ID.
            stmt.setInt(1, userId);

            // Execute the query.
            ResultSet rs = stmt.executeQuery();

            // Read every row returned by MySQL.
            while (rs.next()) {

                // Create a Transaction object.
                Transaction transaction = new Transaction();

                // Copy the database values into the Transaction object.
                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setCategoryId(rs.getInt("category_id"));
                transaction.setType(rs.getString("type"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setDescription(rs.getString("description"));

                // Add the transaction into our list.
                transactions.add(transaction);
            }
        }

        // Return all retrieved transactions.
        return transactions;
    }
}
