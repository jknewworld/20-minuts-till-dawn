package com.P3.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class EnemyBullet {
    private Texture monster = new Texture(GameAssetManager.getGameAssetManager().getEnemyBullet());
    private Sprite playerSprite = new Sprite(monster);
    private float x, y;
    private int type = 1;
    private long spawnTime;
    private boolean isDead = false;
    private float deathTime = 0f;
    private boolean shouldBeRemoved = false;


    private float time;
    private CollisionRect rect;

    public EnemyBullet() {
        this.spawnTime = System.currentTimeMillis();
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(10 , 10);
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight()/2,
            monster.getWidth() * 10, monster.getHeight() * 10);
    }

    public boolean isReadyToCollide(long currentTime) {
        return currentTime - spawnTime > 200; // 200ms تأخیر اولیه
    }


    public Texture getMonster() {
        return monster;
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


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDeathTime(float deathTime) {
        this.deathTime = deathTime;
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(long spawnTime) {
        this.spawnTime = spawnTime;
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

}
