package com.P3.Control;

import com.badlogic.gdx.graphics.Texture;
import com.P3.Main;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private float backgroundX = 0;
    private float backgroundY = 0;
    int worldWidth;
    int worldHeight;


    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("background.png");
        this.playerController = playerController;
        this.worldWidth = backgroundTexture.getWidth();
        this.worldHeight = backgroundTexture.getHeight();
    }

    public void update() {
        backgroundX = playerController.getPlayer().getPosX();
        backgroundY = playerController.getPlayer().getPosY();
        Main.getBatch().draw(backgroundTexture, backgroundX, backgroundY);
    }

}
