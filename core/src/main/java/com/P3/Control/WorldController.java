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

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public float getBackgroundX() {
        return backgroundX;
    }

    public void setBackgroundX(float backgroundX) {
        this.backgroundX = backgroundX;
    }

    public float getBackgroundY() {
        return backgroundY;
    }

    public void setBackgroundY(float backgroundY) {
        this.backgroundY = backgroundY;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public void setWorldWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }
}
