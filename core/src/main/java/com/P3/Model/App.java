package com.P3.Model;

public class App {
    public static User loggedInUser;
    public static boolean autoReloadEnabled = false;
    public static boolean mouseLeft = false;
    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static boolean isAutoReloadEnabled() {
        return autoReloadEnabled;
    }

    public static void setAutoReloadEnabled(boolean autoReloadEnabled) {
        App.autoReloadEnabled = autoReloadEnabled;
    }
}
