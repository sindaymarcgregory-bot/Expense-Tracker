package services;

import java.util.ArrayList;
import model.User;

public class UserService {

    private static final ArrayList<User> users = new ArrayList<>();

    // Default test account
    static {
        User testUser = new User();
        testUser.setUsername("admin");
        testUser.setPassword("1234");

        users.add(testUser);
    }

    public boolean register(User user) {

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                return false;
            }
        }

        users.add(user);
        return true;
    }

    public User login(String username, String password) {

        for (User u : users) {
            if (u.getUsername().equals(username)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }
}