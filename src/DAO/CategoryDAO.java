package DAO;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import model.Category;

public class CategoryDAO {
    // Add Category
    public boolean addCategory(Category category) throws SQLException {
        String sql = """
                INSERT INTO categories
                (user_id, category_name, category_type)
                VALUES (?, ?, ?)
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, category.getUserId());
            ps.setString(2, category.getName());
            ps.setString(3, category.getType());

            return ps.executeUpdate() > 0;
        }
    }
    //end of add category

    // Get Categories
    public ArrayList<Category> getCategories(int userId, String type) throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = """
                SELECT *
                FROM categories
                WHERE user_id = ?
                AND category_type = ?
                ORDER BY category_name
                """;
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);
            ps.setString(2, type);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category();

                category.setId(rs.getInt("id"));
                category.setUserId(rs.getInt("user_id"));
                category.setName(rs.getString("category_name"));
                category.setType(rs.getString("category_type"));

                categories.add(category);
            }
        }
        return categories;
    }
    //end of getCategory

    // Delete Category
    public boolean deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id=?";
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }//end of deleteCategory

    // Insert Default Categories
    public void insertDefaultCategories(int userId) throws SQLException {
        String[] expenseCategories = {
            "Food & Dining",
            "Transportation",
            "Shopping",
            "Bills & Utilities",
            "Rent",
            "Groceries",
            "Healthcare",
            "Entertainment",
            "Education",
            "Travel",
            "Personal Care",
            "Savings",
            "Other"
        };//default categories

        String[] incomeCategories = {
            "Salary",
            "Allowance",
            "Bonus",
            "Freelance",
            "Business",
            "Investment",
            "Gift",
            "Refund",
            "Other"
        };

        String sql = """
                INSERT INTO categories
                (user_id, category_name, category_type)
                VALUES (?, ?, ?)
                """;//differentiating the type 
        
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            for (String category : expenseCategories) {

                ps.setInt(1, userId);
                ps.setString(2, category);
                ps.setString(3, "expense");//setting the category type

                ps.executeUpdate();
            }
            for (String category : incomeCategories) {

                ps.setInt(1, userId);
                ps.setString(2, category);
                ps.setString(3, "income");//setting the category type

                ps.executeUpdate();
            }
        } 
    }//end of insert default categories

    //Get Category Name
    public String getCategoryNameById(int categoryId) throws SQLException {
        String sql = """
                SELECT category_name
                FROM categories
                WHERE id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("category_name");
            }
        } 
        return "";
    }//end of Get Category Name
    
    //checking if the category exists 
    public boolean categoryExists(int userId, String categoryName, String type) throws SQLException {
        String sql = """
                SELECT COUNT(*)
                FROM categories
                WHERE user_id = ?
                AND LOWER(category_name) = LOWER(?)
                AND category_type = ?
                """;
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);
            ps.setString(2, categoryName.trim());
            ps.setString(3, type);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }//end of check category
    
    //Check if category is used in any transaction
public boolean isCategoryInUse(int categoryId) throws SQLException 
{
    String sql = """
            SELECT COUNT(*)
            FROM transactions
            WHERE category_id = ?
            """;

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ) 
    {

        ps.setInt(1, categoryId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) 
        {
            return rs.getInt(1) > 0;
        }
    }
    return false;
    }//end of isCategoryInUse method


}//end of class