package services;

import java.sql.*;
import java.util.ArrayList;
import model.Category;
import DAO.CategoryDAO;

public class CategoryService {
    
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ValidationService validationService = new ValidationService();
    
    // Validate category information
    private void validateCategory(Category category) {

        // Validate category object
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

        // Validate user ID
        if (!validationService.isValidId(category.getUserId())) {
            throw new IllegalArgumentException("Invalid user.");
        }

        // Validate category name
        if (validationService.isEmpty(category.getName())) {
            throw new IllegalArgumentException("Category name is required.");
        }

        // Validate maximum length
        if (validationService.exceedsMaximumLength(category.getName(), 30)) {
            throw new IllegalArgumentException("Category name cannot exceed 30 characters.");
        }

        // Validate category type
        if (validationService.isEmpty(category.getType())) {
             throw new IllegalArgumentException("Category type is required.");
        }
    }

    // Add a new category
    public boolean addCategory(Category category) {

        // Validate category information
        validateCategory(category);
        try {
            // Check duplicate category
            if (categoryExists(
                    category.getUserId(),
                    category.getName(),
                    category.getType())) {

                throw new IllegalArgumentException("Category already exists.");
            }
            return categoryDAO.addCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add category.");
        }
    }

    // Load all categories of one type
    public ArrayList<Category> getCategories(int userId, String type) {

        // Validate user
        if (!validationService.isValidId(userId)) {
            throw new IllegalArgumentException("Invalid user.");
        }

        // Validate category type
        if (validationService.isEmpty(type)) {
            throw new IllegalArgumentException("Category type is required.");
        }

        try {
            return categoryDAO.getCategories(userId, type);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load categories.");
        }
    }

    // Delete a category
    public boolean deleteCategory(int id) {

        // Validate category ID
        if (!validationService.isValidId(id)) {
            throw new IllegalArgumentException("Please select a category.");
        }

        try {
            // Prevent deleting categories that are still used
            if (isCategoryInUse(id)) {
                throw new IllegalArgumentException(
                        "This category is being used by existing transactions.");
            }
            return categoryDAO.deleteCategory(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to delete category.");
        }
    }
    
    // Insert the default categories for a new user
    public void insertDefaultCategories(int userId) {

        if (!validationService.isValidId(userId)) {
            throw new IllegalArgumentException("Invalid user.");
        }

        try {
            categoryDAO.insertDefaultCategories(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create default categories.");
        }
    }
    
   // Retrieve category name by ID
    public String getCategoryNameById(int categoryId) {

        if (!validationService.isValidId(categoryId)) {
             throw new IllegalArgumentException("Invalid category.");
        }

        try {
            return categoryDAO.getCategoryNameById(categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to retrieve category.");
        }
    }   
    
    // Check whether a category already exists
    public boolean categoryExists(int userId, String categoryName, String type) {

        if (!validationService.isValidId(userId)) {
            throw new IllegalArgumentException("Invalid user.");
        }

        if (validationService.isEmpty(categoryName)) {
            throw new IllegalArgumentException("Category name is required.");
        }

        if (validationService.isEmpty(type)) {
            throw new IllegalArgumentException("Category type is required.");
        }

        try {
            return categoryDAO.categoryExists(userId, categoryName, type);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to verify category.");
        }
    }
    
   // Check if a category is currently used by transactions
    public boolean isCategoryInUse(int categoryId) {

        if (!validationService.isValidId(categoryId)) {
            throw new IllegalArgumentException("Invalid category.");
        }

        try {
            return categoryDAO.isCategoryInUse(categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to verify category usage.");
         }   
    }
}