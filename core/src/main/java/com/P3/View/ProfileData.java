package com.P3.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ProfileData {
    private static String profileImagePath;

    public static void setProfileImagePath(String path) {
        profileImagePath = path;
        // ذخیره در فایل برای استفاده بعدی
        Preferences prefs = Gdx.app.getPreferences("profile_prefs");
        prefs.putString("image_path", path);
        prefs.flush();
    }

    public static String getProfileImagePath() {
        if (profileImagePath == null) {
            Preferences prefs = Gdx.app.getPreferences("profile_prefs");
            profileImagePath = prefs.getString("image_path", null);
        }
        return profileImagePath;
    }
}

