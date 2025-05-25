package com.P3.Control;

import com.P3.Model.App;
import com.P3.Model.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.P3.Main;
import com.P3.Model.Bullet;
import com.P3.Model.Weapon;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean reloadAuto = true;
    private boolean ability3Active = false;
    private long ability3StartTime = 0;
    private int pendingProjectiles = 0;
    private long lastProjectileTime = 0;
    private static final long PROJECTILE_INTERVAL = 100;
    private Sound gunshotSound;


    public WeaponController(Weapon weapon) {
        this.weapon = weapon;
        if (App.loggedInUser.getWeapon() == 1)
            gunshotSound = Gdx.audio.newSound(Gdx.files.internal("sfx/gun1.mp3"));
        else if (App.loggedInUser.getWeapon() == 0)
            gunshotSound = Gdx.audio.newSound(Gdx.files.internal("sfx/gun2.mp3"));
        else
            gunshotSound = Gdx.audio.newSound(Gdx.files.internal("sfx/gun3.mp3"));
    }

    public void update() {
        weapon.getSprite().draw(Main.getBatch());
        updateBullets();
        weapon.updateReload();

        if (App.autoReloadEnabled && weapon.getAmmo() == 0) {
            weapon.startReload();

        }
    }


    public void handleWeaponShoot(int x, int y) {
        if (weapon.isReloading()) return;

        float currentTime = TimeUtils.nanoTime() / 1_000_000_000f;
        if (currentTime - weapon.getLastShotTime() < weapon.getType().reloadTime) return;

        if (weapon.getAmmo() <= 0) {
            if (reloadAuto) weapon.startReload();
            return;
        }

        weapon.setLastShotTime(currentTime);
        weapon.setAmmo(weapon.getAmmo() - 1);

        if (App.loggedInUser.getAbility() == 2)
            weapon.getType().projectileCount++;

        App.loggedInUser.setAmmo(weapon.getAmmo());

        for (int i = 0; i < weapon.getType().projectileCount; i++)
            bullets.add(new Bullet(x, y));

        if (App.loggedInUser.isPlaySFX())
            gunshotSound.play(0.5f);

    }


    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSprite();
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
        float angle = (float) Math.atan2(y - centerY, x - centerX);
        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void updateBullets() {
        for (Bullet b : bullets) {
            b.getSprite().draw(Main.getBatch());
            Vector2 direction = new Vector2(
                Gdx.graphics.getWidth() / 2f - b.getX(),
                Gdx.graphics.getHeight() / 2f - b.getY()
            ).nor();
            b.getSprite().setX(b.getSprite().getX() - direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
}

