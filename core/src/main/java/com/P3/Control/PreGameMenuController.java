package com.P3.Control;

import com.P3.Main;
import com.P3.Model.GameAssetManager;
import com.P3.Model.Pregame;
import com.P3.View.GameView;
import com.P3.View.PreGameMenuView;
import com.P3.View.PreGameView;

public class PreGameMenuController {
    private PreGameMenuView view;
    private PreGameView view2;
    private Pregame pregame;


    public void setView(PreGameMenuView view) {
        this.view = view;
        this.pregame = new Pregame();
    }

    public void setView(PreGameView view2) {
        this.view2 = view2;
    }

    public void handlePreGameMenuButtons() {
        if (view != null) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
    }

}
