package services;

import DAO.UserDAO;
import java.sql.SQLException;
import model.User;

public class UserService {

    //User DAO Object
    private final UserDAO userDAO = new UserDAO();
    private final ValidationService validationService = new ValidationService();
    
    private void validateUsername(String username) {
        if (validationService.isEmpty(username)) {
         throw new IllegalArgumentException("Username is required.");
        }
        
        if (!validationService.hasMinimumLength(username, 3)) {
            throw new IllegalArgumentException("Username must be at least 3 characters.");
        }
    }
    
    private void validatePassword(String password) {
        if (validationService.isEmpty(password)) {
            throw new IllegalArgumentException("Password is required.");
        }

        if (!validationService.hasMinimumLength(password, 6)) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
    }
    
    // Validate all registration information
    private void validateUser(User user) {

        // Validate username
        validateUsername(user.getUsername());

        // Validate password
        validatePassword(user.getPassword());

        validateEmail(user.getEmail());

        // Check if username already exists
        if (usernameExists(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Check if email already exists
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }   
    }

    // Register New User
    public User register(User user) {
        try {
            // Validate all registration fields
            validateUser(user);

            // Register the user
            User registeredUser = userDAO.register(user);

            // If successful, create default categories
            if (registeredUser != null) {

                CategoryService categoryService = new CategoryService();

                categoryService.insertDefaultCategories(registeredUser.getId());
            }
            return registeredUser;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to register user.");
        }
    }   
    
    // Validate email
    private void validateEmail(String email) {
        if (validationService.isEmpty(email)) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (!validationService.isValidEmail(email)) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

    }

    // Login User
    public User login(String loginInput, String password) {

        // Validate username/email
        if (validationService.isEmpty(loginInput)) {
            throw new IllegalArgumentException("Username or email is required.");
        }

        // Validate password
        if (validationService.isEmpty(password)) {
            throw new IllegalArgumentException("Password is required.");
        }

        try {
            User user = userDAO.login(loginInput, password);

            if (user == null) {
                throw new IllegalArgumentException("Invalid username/email or password.");
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to login.");
        }
    }

    // Update Profile Picture
    public boolean updateProfilePicture(int userId, String imagePath) {

        try {

            return userDAO.updateProfilePicture(userId, imagePath);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to update profile picture.");
        }
    }

    // Check if Username Already Exists
    public boolean usernameExists(String username) {

        try {
            return userDAO.usernameExists(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Unable to verify username.");
    }

    // Check if Email Already Exists
    public boolean emailExists(String email) {
        try {
            return userDAO.emailExists(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Unable to verify email.");
    }

    // Retrieve User by ID
    public User getUserById(int id) {
        try {

            return userDAO.getUserById(id);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to retrieve user.");
        }
    }

    // Update User Information
    public boolean updateUser(User user) {

        // Validate username
        validateUsername(user.getUsername());

       validateEmail(user.getEmail());

        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to update user.");
        }
    }

    // Delete User
    public boolean deleteUser(int id) {

        try {
            return userDAO.deleteUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Unable to delete user.");
    }

    /// Change User Password
    public boolean changePassword(int userId, String hashedPassword) {

        // Validate user ID
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user.");
        }

        // Validate password
        validatePassword(hashedPassword);

        try {
            return userDAO.changePassword(userId, hashedPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to change password.");
        }
    }// Change User Password end
}
