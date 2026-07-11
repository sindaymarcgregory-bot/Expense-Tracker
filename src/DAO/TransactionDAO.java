package DAO;

import database.DBConnection;
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
                Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {

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
            Connection connection = DBConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
    ) {
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
                Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
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
            Connection connection = DBConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
        }
    }
}