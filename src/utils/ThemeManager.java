package utils;

public class ThemeManager {

    private static boolean darkMode = false;

    // Change theme state
    public static void setDark(boolean value) {
        darkMode = value;
    }

    // Check current theme
    public static boolean isDark() {
        return darkMode;
    }

}
