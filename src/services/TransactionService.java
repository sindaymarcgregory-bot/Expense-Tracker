package services;

import DAO.TransactionDAO;
import database.DBConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Transaction;

public class TransactionService {

    private static final TransactionDAO transactionsDAO = new TransactionDAO();

    public boolean registerExpense(Transaction transaction) {
        try {
            if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
            transactionsDAO.addExpense(transaction);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

}
