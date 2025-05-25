package com.P3.Control;

import com.P3.Main;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.P3.View.*;

public class StartController {
    private StartView view;

    public void setView(StartView view) {
        this.view = view;
    }

    public void handleButtons() {
        if (view != null) {
            if (view.getSignupButton().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SignupView(new SignupController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if(view.getMainMenuButton().isChecked() && App.getLoggedInUser()!=null) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }

}
