package services;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    public User register(User user) {
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {

                    user.setId(rs.getInt(1));

                    CategoryService categoryService = new CategoryService();
                    categoryService.insertDefaultCategories(user.getId());

                    return user;
                }

            }
        } catch (SQLException e) {
         e.printStackTrace();
        }
        return null;
    }

    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String storedHash = rs.getString("password");

                if (BCrypt.checkpw(password, storedHash)) {

                    User user = new User();

                    // If you add an id field later:
                    // user.setId(rs.getInt("id"));
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(storedHash);

                    return user;
                }
            }
        } catch (SQLException e) {
             e.printStackTrace();
        }
        return null;
    }
}