package DAO;

import database.DBConnection;
import java.sql.*;
import model.User;
import org.mindrot.jbcrypt.BCrypt;//import needed to hash password

public class UserDAO {

    //Register a New User
    public User register(User user) throws SQLException {

        String sql = """
            INSERT INTO users
            (fullname, username, email, phone, age, password)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            ps.setString(1, user.getFullname());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setInt(5, user.getAge());
            ps.setString(6, user.getPassword());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {

                    user.setId(rs.getInt(1));

                    return user;
                }
            }
        }

        return null;
    }//end of register user

    //Login User
    public User login(String loginInput, String password) throws SQLException {
        String sql = """
            SELECT *
            FROM users
            WHERE username = ?
            OR email = ?
            """;
        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            // Search by username OR email
            ps.setString(1, loginInput);
            ps.setString(2, loginInput);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Get the hashed password from the database
                String storedHash = rs.getString("password");

                // Compare the entered password with the stored hash
                if (BCrypt.checkpw(password, storedHash)) {
                    User user = new User();

                    user.setId(rs.getInt("id"));
                    user.setFullname(rs.getString("fullname"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setAge(rs.getInt("age"));
                    user.setPassword(storedHash);
                    user.setProfilePicture(rs.getString("profile_picture"));

                    return user;
                }
            }
        }
        return null;
    }
    //end of login user

    //Update Profile Picture
    public boolean updateProfilePicture(int userId, String imagePath) throws SQLException {

        String sql = """
            UPDATE users
            SET profile_picture = ?
            WHERE id = ?
            """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, imagePath);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } 
    }
//end of update profile picture

    //Check if Username Already Exists
    public boolean usernameExists(String username) throws SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM users
                WHERE username = ?
                """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }//Check if Username Already Exists end

    //Check if Email Already Exists
    public boolean emailExists(String email) throws SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM users
                WHERE email = ?
                """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    } //Check if Email Already Exists end

    //Retrieve User Using ID
    public User getUserById(int id) throws SQLException {

        String sql = """
                SELECT *
                FROM users
                WHERE id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                return user;
            }
        }

        return null;
    } //Retrieve User Using ID end

    //Update User Information
    public boolean updateUser(User user) throws SQLException {

        String sql = """
            UPDATE users
            SET fullname = ?,
                email = ?,
                phone = ?,
                age = ?
            WHERE id = ?
            """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getAge());
            ps.setInt(5, user.getId());

            return ps.executeUpdate() > 0;
        }
    }

    //Delete User Account
    public boolean deleteUser(int id) throws SQLException {

        String sql = """
                DELETE FROM users
                WHERE id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setInt(1, id);

            // Returns true if one row was deleted
            return ps.executeUpdate() > 0;
        }
    }   //Delete User Account end

    // Change User Password
    public boolean changePassword(int userId, String hashedPassword) throws SQLException {

        String sql = """
            UPDATE users
            SET password = ?
            WHERE id = ?
            """;

        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            // Set the new hashed password
            ps.setString(1, hashedPassword);

            // Set the user ID
            ps.setInt(2, userId);

            // Return true if the password was updated successfully
            return ps.executeUpdate() > 0;
        }
    }
// Change User Password end

}//end of class
