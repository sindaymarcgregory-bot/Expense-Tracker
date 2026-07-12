package services;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import model.Category;
import DAO.CategoryDAO;

public class CategoryService {
    
    private final CategoryDAO categoryDAO = new CategoryDAO();

    // Add a new category
    public boolean addCategory(Category category) {
        try {
            return categoryDAO.addCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Load all categories of one type
    public ArrayList<Category> getCategories(int userId, String type) {
        try {
            return categoryDAO.getCategories(userId, type);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Delete a category
    public boolean deleteCategory(int id) {
        try {
            return categoryDAO.deleteCategory(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void insertDefaultCategories(int userId) {
        try {
            categoryDAO.insertDefaultCategories(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getCategoryNameById(int categoryId) {
        try {
            return categoryDAO.getCategoryNameById(categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public boolean categoryExists(int userId, String categoryName, String type) {
        try {
            return categoryDAO.categoryExists(userId, categoryName, type);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}