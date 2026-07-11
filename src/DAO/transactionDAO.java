/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import database.DBConnection;
import java.util.List;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import model.Expense;
import model.Transaction;

/**
 *
 * @author Arch Salon
 */
public class TransactionDAO {

    //add expense
    public void addExpense(Expense expense) throws SQLException {
        String sql = "INSERT INTO transaction (user_id, type, amount, category_id, description,) VALUES (?, expense, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setBigDecimal(3, expense.getAmount());
            stmt.setInt(4, expense.getCategoryId());
            stmt.setString(5, expense.getDescription());

            stmt.executeUpdate();

        }//body of try

    }
    //end of addExpense Method

    //read all expense
    public List<Transaction> getAllExpense() throws SQLException {
        List<Transaction> expenseLists = new ArrayList();
        String sql = "SELECT * FROM transactions";
        try (
                Connection connection = DBConnection.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {

                expenseLists.add(new Transaction(
                        rs.getString("type"),
                        rs.getBigDecimal("amount"),
                        rs.getInt("categoryId"),
                        rs.getString("description")
                ));

            }
            //end of loop

        }//end of try
        return expenseLists;

    }//end of getAllExpense

    //read one expense
    public Transaction getItemExpense(BigDecimal amount) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE amount = ?";
        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setBigDecimal(2, amount);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Transaction(
                        rs.getString("type"),
                        rs.getBigDecimal("amount"),
                        rs.getInt("categoryId"),
                        rs.getString("description")
                );
            }//end of "if"
            return null;
        }
    }// end of getItemExpense

    //update expense
    public void updateExpense(Transaction updatedExp) throws SQLException {
        String sql = "UPDATE transactions SET type = ?, amount = ?, categoryId = ? WHERE description = ?";

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, updatedExp.getType());
            stmt.setBigDecimal(2, updatedExp.getAmount());
            stmt.setInt(3, updatedExp.getCategoryId());
            stmt.setString(4, updatedExp.getDescription());
            stmt.executeUpdate();
        }//end of try
    }//end of updateExpense

    //delete Expense
    public void deleteExpense(BigDecimal amount) throws SQLException {
        String sql = "DELETE FROM transaction WHERE amount = ?";
        try (
            Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setBigDecimal(2, amount);
            stmt.executeUpdate();
        }
    }
}
