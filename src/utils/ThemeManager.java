package utils;

public class ThemeManager {

    // Stores whether dark mode is enabled
    private static boolean darkMode = false;

    // Enable or disable dark mode
    public static void setDarkMode(boolean enabled) {
        darkMode = enabled;
    }

    // Returns the current mode
    public static boolean isDarkMode() {
        return darkMode;
    }

}