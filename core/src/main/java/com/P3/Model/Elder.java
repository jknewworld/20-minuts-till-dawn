package com.P3.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Elder {
    private Texture monster = new Texture(GameAssetManager.getGameAssetManager().getEye0());
    private Sprite playerSprite = new Sprite(monster);
    private float x, y;
    private float dx, dy;
    private int HP;
    private int type = 1;
    private boolean isDead = false;
    private float deathTime = 0f;
    private boolean shouldBeRemoved = false;
    private float fireRate = 5f;
    private boolean isSeed = false;

    private float time;
    private CollisionRect rect;

    public Elder() {
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(monster.getWidth() * 10, monster.getHeight() * 10);
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), monster.getWidth() * 10, monster.getHeight() * 10);
        this.HP = 400;
    }


    public void setMonster(Texture monster) {
        this.monster = monster;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
        deathTime = 0f;
    }

    public float getDeathTime() {
        return deathTime;
    }

    public void updateDeathTime(float delta) {
        deathTime += delta;
    }

    public boolean shouldBeRemoved() {
        return shouldBeRemoved;
    }

    public void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
    }

    public Texture getMonster() {
        return monster;
    }

    public void setDeathTime(float deathTime) {
        this.deathTime = deathTime;
    }

    public boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    public float getFireRate() {
        return fireRate;
    }

    public void setFireRate(float fireRate) {
        this.fireRate = fireRate;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public boolean isSeed() {
        return isSeed;
    }

    public void setSeed(boolean seed) {
        isSeed = seed;
    }
}
