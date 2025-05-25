package com.P3.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Player {
    private Texture playerTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1_idle0());
    private Sprite playerSprite = new Sprite(playerTexture);
    private float posX = 0;
    private float posY = 0;
    private float playerHealth;
    private CollisionRect rect;
    private float time;
    private float speed;
    private int level;
    private int xp;
    private int ability;
    private boolean levelUpNotified = true;


    public float getSpeed() {
        return speed;
    }

    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    public Player() {
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.time = App.loggedInUser.getTime();
        this.level = 0;
        this.xp = 0;
        if (App.loggedInUser.getAvatar() == 0) {
            this.playerHealth = 4;
            this.speed = 4;
        } else if (App.loggedInUser.getAvatar() == 1) {
            this.playerHealth = 7;
            this.speed = 1;
        } else if (App.loggedInUser.getAvatar() == 2) {
            this.playerHealth = 3;
            this.speed = 5;
        } else if (App.loggedInUser.getAvatar() == 3) {
            this.playerHealth = 5;
            this.speed = 3;
        } else if (App.loggedInUser.getAvatar() == 4) {
            this.playerHealth = 2;
            this.speed = 10;
        }

        this.ability = App.loggedInUser.getAbility();
    }

    public void decreaseHealth(int amount) {
        this.playerHealth -= amount;
        if (playerHealth < 0) {
            playerHealth = 0;
            this.playerHealth = 0;
        }
        if (App.loggedInUser.getHealth() - amount < 0) {
            App.loggedInUser.setHealth(0);
        }
        App.loggedInUser.setHealth(App.loggedInUser.getHealth() - amount);

    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }


    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (xp == (20 * level)) {
            level++;
        }
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public boolean isLevelUpNotified() {
        return levelUpNotified;
    }

    public void setLevelUpNotified(boolean levelUpNotified) {
        this.levelUpNotified = levelUpNotified;
    }
}
