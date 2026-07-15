package Utils;

import java.util.prefs.Preferences;

public class ThemeManager {

    // Save the theme settings
    private static final Preferences prefs
            = Preferences.userNodeForPackage(ThemeManager.class);

    private static final String THEME_KEY = "darkMode";

    // Save the current theme
    public static void setDarkMode(boolean enabled) {
        prefs.putBoolean(THEME_KEY, enabled);
    }

    // Check if dark mode is enabled
    public static boolean isDarkMode() {
        return prefs.getBoolean(THEME_KEY, false);
    }
}
