package com.P3.Control;

import com.P3.Model.App;
import com.P3.Model.Repo.UserRepo;
import com.P3.Model.User;
import com.P3.View.*;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public class MainMenuController {
    private MainMenuView view;
    private SettingView view2;
    private ProfileView view3;
    private TalentView view4;
    private ScoreboardView view5;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void setView(SettingView view) {
        this.view2 = view;
    }

    public void setView(ProfileView view) {
        this.view3 = view;
    }

    public void setView(TalentView view) {
        this.view4 = view;
    }

    public void setView(ScoreboardView view) {
        this.view5 = view;
    }

    public void handleMainMenuButtons() {
//        if (view != null) {
//            if (view.getPlayButton().isChecked() && view.getField().getText().equals("kiarash")) {
//                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
//            }
//        }
    }

    public void handleChangUserName() {
        String newName = view3.getNewName().getText();
        if (newName.equals(App.getLoggedInUser().getUsername())) {
            view3.showMessage("^_^ Same old thing", Color.FIREBRICK);
        } else {
            view3.showMessage("^_^ Wait, the wording is different now!", Color.GREEN);
            App.loggedInUser.setUsername(newName);
            UserRepo.saveUser(App.loggedInUser);
        }
    }


    public void handleChangPass() {
        String passwoord = view3.getNewPassword().getText();
        String allowedChars = "^(?=.*[@%$#&*()_])(?=.*[A-Z])(?=.*\\d).+$";
        if (passwoord.equals(App.getLoggedInUser().getPassword())) {
            view3.showMessage("^_^ Same old thing", Color.FIREBRICK);
        } else if (passwoord.length() < 8 || !passwoord.matches(allowedChars)) {
            view3.showMessage("^_^ Invalid", Color.FIREBRICK);
        } else {
            view3.showMessage("^_^ Wait, the wording is different now!", Color.GREEN);
            App.loggedInUser.setPassword(passwoord);
            UserRepo.saveUser(App.loggedInUser);
        }
    }

    public void handleAvatar(String newAvatar) {
        if (newAvatar.equals("Left")) {
            App.loggedInUser.setAvatar(3);
            UserRepo.saveUser(App.loggedInUser);
        } else if (newAvatar.equals("Right")) {
            App.loggedInUser.setAvatar(8);
            UserRepo.saveUser(App.loggedInUser);
        }
    }
}
