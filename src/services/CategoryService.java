package services;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import model.Category;

public class CategoryService {

    // Add a new category
    public boolean addCategory(Category category) {

        String sql = """
                INSERT INTO categories
                (user_id, category_name, category_type)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, category.getUserId());
            ps.setString(2, category.getName());
            ps.setString(3, category.getType());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Load all categories of one type
    public ArrayList<Category> getCategories(int userId, String type) {

        ArrayList<Category> categories = new ArrayList<>();

        String sql = """
                SELECT *
                FROM categories
                WHERE user_id = ?
                AND category_type = ?
                ORDER BY category_name
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Delete a category
    public boolean deleteCategory(int id) {

        String sql = "DELETE FROM categories WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public void insertDefaultCategories(int userId) {

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
        };

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
            """;

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String category : expenseCategories) {
                ps.setInt(1, userId);
                ps.setString(2, category);
                ps.setString(3, "expense");
                ps.executeUpdate();
            }
            for (String category : incomeCategories) {
                ps.setInt(1, userId);
                ps.setString(2, category);
                ps.setString(3, "income");
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getCategoryNameById(int categoryId) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}