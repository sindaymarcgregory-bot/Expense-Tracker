package services;

import DAO.UserDAO;
import java.sql.SQLException;
import model.User;

public class UserService {

  
    //User DAO Object
   
    private final UserDAO userDAO = new UserDAO();

   
    //Register New User
   
    public User register(User user) {

        try {

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

        }

        return null;
    }

    // Login User
    public User login(String loginInput, String password) {

        try {

            return userDAO.login(loginInput, password);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }

    // Update Profile Picture
    public boolean updateProfilePicture(int userId, String imagePath)  {

        try {

            return userDAO.updateProfilePicture(userId, imagePath);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // Check if Username Already Exists
    public boolean usernameExists(String username) {

        try {

            return userDAO.usernameExists(username);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // Check if Email Already Exists
    public boolean emailExists(String email) {

        try {

            return userDAO.emailExists(email);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // Retrieve User by ID
    public User getUserById(int id) {

        try {

            return userDAO.getUserById(id);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }

    // Update User Information

    public boolean updateUser(User user) {

        try {

            return userDAO.updateUser(user);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }
    
    // Delete User
    public boolean deleteUser(int id) {

        try {

            return userDAO.deleteUser(id);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

}