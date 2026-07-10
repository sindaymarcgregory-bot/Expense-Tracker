/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import database.DBConnection;
import java.awt.List;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import model.Expense;

/**
 *
 * @author Arch Salon
 */
public class transactionDAO {

    //add
    public void addExpense(Expense expense) throws SQLException {
        String sql = "INSERT INTO transaction (user_id, type, amount, category_id, description, transaction_date) VALUES (?, expense, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setString(1, expense.getCategory());
            stmt.setBigDecimal(2, expense.getAmount());
            stmt.setString(3, expense.getDescription());
            
            stmt.executeUpdate();

        }//body of try

    }
    //end of addExpense Method

    //read
    

}
