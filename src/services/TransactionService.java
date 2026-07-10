package services;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Transaction;

public class TransactionService {

    
    //add
    public boolean addTransaction(Transaction transaction) {

        String sql = """
                INSERT INTO transactions
                (user_id, category_id, type,
                 transaction_date, amount, description)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transaction.getUserId());
            ps.setInt(2, transaction.getCategoryId());
            ps.setString(3, transaction.getType());
            ps.setDate(4, transaction.getTransactionDate());
            ps.setBigDecimal(5, transaction.getAmount());
            ps.setString(6, transaction.getDescription());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    //read all
    
    
    
    
    //read one
    
    
    
    /

}