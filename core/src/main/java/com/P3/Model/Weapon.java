package com.P3.Model;

import com.P3.Model.Enum.WeaponType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;

public class Weapon {
    private WeaponType type;
    private Texture texture;
    private Sprite sprite;
    private int ammo;
    private float lastShotTime = 0;
    private boolean isReloading = false;
    private float reloadStartTime = 0;

    public Weapon(WeaponType type) {
        this.type = type;
        this.ammo = type.maxAmmo;

        // texture selection
        if (type == WeaponType.DUAL_SMG) {
            texture = new Texture(GameAssetManager.getGameAssetManager().getSmg());
        } else if (type == WeaponType.SHOTGUN) {
            texture = new Texture(GameAssetManager.getGameAssetManager().getGun());
        } else {
            texture = new Texture(GameAssetManager.getGameAssetManager().getSword());
        }

        sprite = new Sprite(texture);
        sprite.setSize(50, 50);
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public WeaponType getType() {
        return type;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public void startReload() {
        isReloading = true;
        reloadStartTime = TimeUtils.nanoTime() / 1_000_000_000f;
    }

    public void updateReload() {
        if (isReloading && TimeUtils.nanoTime() / 1_000_000_000f - reloadStartTime >= type.reloadTime) {
            if(App.loggedInUser.getAbility()==3){
                type.maxAmmo += 5;
            }
            ammo = type.maxAmmo;
            isReloading = false;
        }
    }

    public float getLastShotTime() {
        return lastShotTime;
    }

    public void setLastShotTime(float time) {
        lastShotTime = time;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setReloading(boolean reloading) {
        isReloading = reloading;
    }

    public float getReloadStartTime() {
        return reloadStartTime;
    }

    public void setReloadStartTime(float reloadStartTime) {
        this.reloadStartTime = reloadStartTime;
    }



}
