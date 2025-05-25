package com.P3.Control;

import com.P3.Main;
import com.P3.Model.*;
import com.P3.Model.Enum.WeaponType;
import com.P3.Model.Save.EyeDate;
import com.P3.Model.Save.GameState;
import com.P3.Model.Save.MonsterData;
import com.P3.Model.Save.TreeData;
import com.P3.View.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private static WeaponController weaponController;
    public static Tree[] trees;
    public static int treeCount = 20;
    public static Monster[] monsters;
    public static int monsterCount = 10;
    public static Eye[] eyes;
    public static int eyeCount = 10;
    private float spawnTimer = 0f;
    private float gameTime = 0f;
    private final float SPAWN_INTERVAL = 3f;
    private final float MINIMUM_GAME_TIME = 7f / 30f;
    private float eyeShootTimer = 0f;
    private final float EYE_SHOOT_INTERVAL = 20f;
    private List<EnemyBullet> enemyBullets = new ArrayList<>();
    private boolean ability3Active = false;
    private long ability3StartTime = 0;
    private int eyeSpawnCount = 0;
    private final float TOTAL_GAME_DURATION = App.loggedInUser.getTime();
    private Sound damageSound = Gdx.audio.newSound(Gdx.files.internal("sfx/damage.wav"));


    private Elder elder;

    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(new Player(), GameController.this);
        worldController = new WorldController(playerController);
        Weapon weapon = null;
        int userChoice = App.loggedInUser.getWeapon();
        if (userChoice == 0) weapon = new Weapon(WeaponType.DUAL_SMG);
        else if (userChoice == 1) weapon = new Weapon(WeaponType.SHOTGUN);
        else if (userChoice == 2) weapon = new Weapon(WeaponType.REVOLVER);
        trees = new Tree[treeCount];
        for (int i = 0; i < treeCount; i++) {
            trees[i] = new Tree();

            float x = (float) (Math.random() * worldController.worldWidth);
            float y = (float) (Math.random() * worldController.worldHeight);

            trees[i].setX(x);
            trees[i].setY(y);

            Sprite s = new Sprite(GameAssetManager.getGameAssetManager().getTree_frames().getKeyFrame(0));
            s.setPosition(x, y);
            trees[i].setPlayerSprite(s);
        }

        monsters = new Monster[0];
        monsterCount = 0;

        eyes = new Eye[eyeCount];
        for (int i = 0; i < eyeCount; i++) {
            eyes[i] = new Eye();

            float x = (float) (Math.random() * worldController.worldWidth);
            float y = (float) (Math.random() * worldController.worldHeight);

            eyes[i].setX(x);
            eyes[i].setY(y);

            Sprite s = new Sprite(GameAssetManager.getGameAssetManager().getEye_frames().getKeyFrame(0));
            s.setPosition(x, y);
            eyes[i].setPlayerSprite(s);
        }
//        eyes = new Eye[0];
//        eyeCount = 0;

        this.elder = new Elder();
        float x = (float) (Math.random() * worldController.worldWidth);
        float y = (float) (Math.random() * worldController.worldHeight);
        elder.setX(x);
        elder.setY(y);
        Sprite s = new Sprite(GameAssetManager.getGameAssetManager().getElder_frames().getKeyFrame(0));
        s.setPosition(x, y);
        elder.setPlayerSprite(s);

        weaponController = new WeaponController(weapon);
    }

    public void updateGame() {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();

            float delta = Gdx.graphics.getDeltaTime();
            gameTime += delta;
            spawnTimer += delta;

            if (gameTime >= MINIMUM_GAME_TIME && spawnTimer >= SPAWN_INTERVAL) {
                spawnMonster();
                spawnMonster();
                spawnMonster();
                spawnTimer = 0f;
            }
//            float eyeSpawnInterval = getEyeSpawnInterval(gameTime, eyeSpawnCount);
//
//            if (spawnTimer >= eyeSpawnInterval) {
//                // for(int i = 0; i < (4f * i - t + 30f) / 30f;; i++) {}
//                spawnEyebat();
//                eyeSpawnCount++;
//                spawnTimer = 0f;
//            }

            eyeShootTimer += delta;
            if (eyeShootTimer >= EYE_SHOOT_INTERVAL) {
                shootEyesAtPlayer();
                eyeShootTimer = 0f;
            }

            checkPlayerTreeCollision();//Player
            checkBulletMonsterCollision();// Monster
            treeAnimation();// Tree
            renderTrees();// Tree
            treeMonster();//Monster
            renderMonsters();//Monster
            renderEyes();// Eye
            eyeAnimation();// Eye
            renderBullets();// Bullets
            DeathAnimation();// Bomb
            elderAnimation();// Elder


            updateDeadMonsters(delta);
            updateDeadEyes(delta);
            updateDead(delta);

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();


//            for (Monster enemy : monsters) {
//                if (!enemy.isDead()) {
//                    float ex = enemy.getX();
//                    float ey = enemy.getY();
//                    float ew = enemy.getPlayerSprite().getWidth();  // یا enemy.getSprite().getWidth()
//                    float eh = enemy.getPlayerSprite().getHeight(); // یا enemy.getSprite().getHeight()
//
//                    if (mouseX >= ex && mouseX <= ex + ew &&
//                        mouseY >= ey && mouseY <= ey + eh) {
//                        App.getLoggedInUser().setHeavy(true);
//                        Main.setCustomCursor();
//                        break;
//                    } else {
//                        App.getLoggedInUser().setHeavy(false);
//                        Main.setCustomCursor();
//                    }
//                }
//            }

//            for (Tree enemy : trees) {
//                float ex = enemy.getX();
//                float ey = enemy.getY();
//                float ew = enemy.getPlayerSprite().getWidth();
//                float eh = enemy.getPlayerSprite().getHeight();
//
//                if (mouseX >= ex && mouseX <= ex + ew &&
//                    mouseY >= ey && mouseY <= ey + eh) {
//                    App.getLoggedInUser().setHeavy(true);
//                    Main.setCustomCursor();
//                    break;
//                } else {
//                    App.getLoggedInUser().setHeavy(false);
//                    Main.setCustomCursor();
//                }
//
//            }

//            for (Eye enemy : eyes) {
//                if (!enemy.isDead()) {
//                    float ex = enemy.getX();
//                    float ey = enemy.getY();
//                    float ew = enemy.getPlayerSprite().getWidth();  // یا enemy.getSprite().getWidth()
//                    float eh = enemy.getPlayerSprite().getHeight(); // یا enemy.getSprite().getHeight()
//
//                    if (mouseX >= ex && mouseX <= ex + ew &&
//                        mouseY >= ey && mouseY <= ey + eh) {
//                        App.getLoggedInUser().setHeavy(true);
//                        Main.setCustomCursor2();
//                        break;
//                    } else {
//                        App.getLoggedInUser().setHeavy(false);
//                        Main.setCustomCursor();
//                    }
//                }
//            }

        }
    }

    private float getEyeSpawnInterval(float t, int i) {
        if (t > TOTAL_GAME_DURATION / 4f)
            return 10f;

        return Float.MAX_VALUE;
    }

    private void spawnEyebat() {
        Eye newEye = new Eye();
        float x = MathUtils.random(worldController.worldWidth);
        float y = MathUtils.random(worldController.worldHeight);

        newEye.setX(x);
        newEye.setY(y);

        Sprite s = new Sprite(GameAssetManager.getGameAssetManager().getEye_frames().getKeyFrame(0));
        s.setPosition(x, y);
        newEye.setPlayerSprite(s);

        eyes = Arrays.copyOf(eyes, eyes.length + 1);
        eyes[eyes.length - 1] = newEye;
    }

    private void shootEyesAtPlayer() {
        float pX = playerController.getPlayer().getPosX();
        float pY = playerController.getPlayer().getPosY();
        for (Eye eye : eyes) {
            if (!eye.isDead()) {
                EnemyBullet bullet = new EnemyBullet();

                float eyeX = eye.getDx();
                float eyeY = eye.getDy();

                float playerX = Gdx.graphics.getWidth() / 2f;
                float playerY = Gdx.graphics.getHeight() / 2f;

//                float playerX = playerController.getPlayer().getPosX();
//                float playerY = playerController.getPlayer().getPosY();

                float dx = playerX - eyeX;
                float dy = playerY - eyeY;

                float length = (float) Math.sqrt(dx * dx + dy * dy);
                if (length != 0) {
                    dx /= length;
                    dy /= length;
                }

                bullet.setX(eyeX);
                bullet.setY(eyeY);

                enemyBullets.add(bullet);
            }
        }
    }

    public void renderBullets() {
//        float playerX = playerController.getPlayer().getPosX();
//        float playerY = playerController.getPlayer().getPosY();

        float playerX = playerController.getPlayer().getPlayerSprite().getX();
        float playerY = playerController.getPlayer().getPlayerSprite().getY();
        float pX = (float) Gdx.graphics.getWidth() / 2;
        float pY = (float) Gdx.graphics.getHeight() / 2;

        for (EnemyBullet enemyBullet : enemyBullets) {
            if (enemyBullet != null) {

                float drawX = enemyBullet.getX();
                float drawY = enemyBullet.getY();

                moveEnemyTowardsPlayer(enemyBullet);

                if (enemyBullet.isDead())
                    enemyBullet.getPlayerSprite().setScale(2f);

                enemyBullet.getPlayerSprite().setPosition(drawX, drawY);
                enemyBullet.getPlayerSprite().draw(Main.getBatch());
            }
        }
    }

    public void moveEnemyTowardsPlayer(EnemyBullet bullet) {
//        float playerX = (float) Gdx.graphics.getWidth() / 2;
//        float playerY = (float) Gdx.graphics.getHeight() / 2;

        float playerX = playerController.getPlayer().getPlayerSprite().getX();
        float playerY = playerController.getPlayer().getPlayerSprite().getY();

        float monsterX = bullet.getX();
        float monsterY = bullet.getY();

        float dx = playerX - monsterX;
        float dy = playerY - monsterY;

        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        float speed = 30f;
        float delta = Gdx.graphics.getDeltaTime();
        float pX = playerController.getPlayer().getPosX();
        float pY = playerController.getPlayer().getPosY();

        bullet.setX(monsterX + dx * speed * delta);
        bullet.setY(monsterY + dy * speed * delta);
    }

    private void spawnMonster() {
        Monster monster = new Monster();

        float x = (float) (Math.random() * worldController.worldWidth);
        float y = (float) (Math.random() * worldController.worldHeight);

        monster.setX(x);
        monster.setY(y);
        monster.setHP(25);

        Sprite s = new Sprite(GameAssetManager.getGameAssetManager().getMonster_frames().getKeyFrame(0));
        s.setPosition(x, y);
        monster.setPlayerSprite(s);

        List<Monster> currentMonsters = new ArrayList<>(Arrays.asList(monsters));
        currentMonsters.add(monster);
        monsters = currentMonsters.toArray(new Monster[0]);
        monsterCount = monsters.length;
    }

    public void renderTrees() {
        float playerX = playerController.getPlayer().getPosX();
        float playerY = playerController.getPlayer().getPosY();

        for (Tree tree : trees) {
            float drawX = tree.getX() + playerX;
            float drawY = tree.getY() + playerY;

            tree.getPlayerSprite().setScale(1.5f);
            tree.getPlayerSprite().setPosition(drawX, drawY);
            tree.getPlayerSprite().draw(Main.getBatch());
        }
    }

    public void renderEyes() {
        float playerX = playerController.getPlayer().getPosX();
        float playerY = playerController.getPlayer().getPosY();

        for (Eye eye : eyes) {
            float drawX = eye.getX() + playerX;
            float drawY = eye.getY() + playerY;
            eye.setDx(drawX);
            eye.setDy(drawY);

            eye.getPlayerSprite().setScale(1f);

            if (eye.isSeed()) {
                eye.getPlayerSprite().setScale(0.5f);
            }

            eye.getPlayerSprite().setPosition(drawX, drawY);
            eye.getPlayerSprite().draw(Main.getBatch());
        }
    }

    public void eyeAnimation() {
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getEye_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);
        Animation<Texture> death = GameAssetManager.getGameAssetManager().getDeathEye_frames();
        death.setPlayMode(Animation.PlayMode.LOOP);
        Animation<Texture> seed = GameAssetManager.getGameAssetManager().getEyeSeed_frames();
        death.setPlayMode(Animation.PlayMode.LOOP);

        for (int i = 0; i < eyeCount; i++) {
            Eye eye = eyes[i];
            if (!eye.isDead() && !eye.isSeed()) {
                eye.getPlayerSprite().setRegion(animation.getKeyFrame(eye.getTime()));
                if (!animation.isAnimationFinished(eye.getTime())) {
                    eye.setTime(eye.getTime() + Gdx.graphics.getDeltaTime());
                } else {
                    eye.setTime(0);
                }
            } else if (eye.isSeed()) {
                eye.getPlayerSprite().setRegion(seed.getKeyFrame(eye.getTime()));
                if (!seed.isAnimationFinished(eye.getTime())) {
                    eye.setTime(eye.getTime() + Gdx.graphics.getDeltaTime());
                } else {
                    eye.setTime(0);
                }
            } else {
                eye.getPlayerSprite().setRegion(death.getKeyFrame(eye.getTime()));
                if (!death.isAnimationFinished(eye.getTime())) {
                    eye.setTime(eye.getTime() + Gdx.graphics.getDeltaTime());
                } else {
                    eye.setTime(0);
                }
            }
        }
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public void treeAnimation() {
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getTree_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);

        for (int i = 0; i < treeCount; i++) {
            Tree tree = trees[i];
            tree.getPlayerSprite().setRegion(animation.getKeyFrame(tree.getTime()));
            if (!animation.isAnimationFinished(tree.getTime())) {
                tree.setTime(tree.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                tree.setTime(0);
            }
        }
    }

    public void elderAnimation() {
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getElder_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);

        elder.getPlayerSprite().setRegion(animation.getKeyFrame(elder.getTime()));
        if (!animation.isAnimationFinished(elder.getTime())) {
            elder.setTime(elder.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            elder.setTime(0);
        }

    }

    public void treeMonster() {
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getMonster_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);
        Animation<Texture> death = GameAssetManager.getGameAssetManager().getDeath_frames();
        death.setPlayMode(Animation.PlayMode.LOOP);
        Animation<Texture> seed = GameAssetManager.getGameAssetManager().getMonsterSeed_frames();
        seed.setPlayMode(Animation.PlayMode.LOOP);


        for (int i = 0; i < monsterCount; i++) {
            Monster monster = monsters[i];
            if (!monster.isDead()) {
                monster.getPlayerSprite().setRegion(animation.getKeyFrame(monster.getTime()));
                if (!animation.isAnimationFinished(monster.getTime())) {
                    monster.setTime(monster.getTime() + Gdx.graphics.getDeltaTime());
                } else {
                    monster.setTime(0);
                }
            } else if (monster.isSeed()) {
                monster.getPlayerSprite().setRegion(seed.getKeyFrame(monster.getTime()));
                if (!seed.isAnimationFinished(monster.getTime())) {
                    monster.setTime(monster.getTime() + Gdx.graphics.getDeltaTime());
                } else {
                    monster.setTime(0);
                }
            } else {
                monster.getPlayerSprite().setRegion(death.getKeyFrame(monster.getTime()));
                if (!death.isAnimationFinished(monster.getTime())) {
                    monster.setTime(monster.getTime() + Gdx.graphics.getDeltaTime());
                } else {
                    monster.setTime(0);
                }
            }
        }
    }

    public void DeathAnimation() {
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getDeathBullet_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);
        Animation<Texture> death = GameAssetManager.getGameAssetManager().getDeath_frames();
        death.setPlayMode(Animation.PlayMode.LOOP);


        for (int i = 0; i < enemyBullets.size(); i++) {
            EnemyBullet monster = enemyBullets.get(i);
            if (!monster.isDead()) {

            } else {
                monster.getPlayerSprite().setRegion(death.getKeyFrame(monster.getTime()));
                if (!death.isAnimationFinished(monster.getTime())) {
                    monster.setTime(monster.getTime() + Gdx.graphics.getDeltaTime());
                } else {
                    monster.setTime(0);
                }
            }
        }
    }

    public void renderMonsters() {
        float playerX = playerController.getPlayer().getPosX();
        float playerY = playerController.getPlayer().getPosY();

        for (Monster monster : monsters) {
            if (!monster.isDead()) {
                moveMonstersTowardsPlayer(monster);

                float drawX = monster.getX();
                float drawY = monster.getY();

                monster.getPlayerSprite().setScale(2f);
                monster.getPlayerSprite().setPosition(drawX, drawY);
                monster.getPlayerSprite().draw(Main.getBatch());
            } else if (monster.isSeed()) {
                //  moveMonstersTowardsPlayer(monster);

                float drawX = monster.getX() + playerX;
                float drawY = monster.getY() + playerY;

                monster.getPlayerSprite().setScale(0.75f);
                monster.getPlayerSprite().setPosition(drawX, drawY);
                monster.getPlayerSprite().draw(Main.getBatch());
            } else {
                float drawX = monster.getX();
                float drawY = monster.getY();

                monster.getPlayerSprite().setScale(2f);
                monster.getPlayerSprite().setPosition(drawX, drawY);
                monster.getPlayerSprite().draw(Main.getBatch());
            }
        }
    }

    public void moveMonstersTowardsPlayer(Monster monster) {
        float playerX = (float) Gdx.graphics.getWidth() / 2;
        float playerY = (float) Gdx.graphics.getHeight() / 2;
//        float playerX = playerController.getPlayer().getPosX();
//        float playerY = playerController.getPlayer().getPosY();

        float monsterX = monster.getX();
        float monsterY = monster.getY();

        float dx = playerX - monsterX;
        float dy = playerY - monsterY;

        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx /= length;
            dy /= length;
        } else {
            monster.setDead(true);
            monster.setShouldBeRemoved(true);
            List<Monster> remainingMonsters = new ArrayList<>();
            remainingMonsters.add(monster);
            monsters = remainingMonsters.toArray(new Monster[0]);
            monsterCount = monsters.length;
        }

        float speed = 10f;
        float delta = Gdx.graphics.getDeltaTime();
        float pX = playerController.getPlayer().getPosX();
        float pY = playerController.getPlayer().getPosY();

        monster.setX(monsterX + dx * speed * delta);
        monster.setY(monsterY + dy * speed * delta);
    }

    private long lastCollisionTime = -1;

    private void checkPlayerTreeCollision() {
        long currentTime = System.currentTimeMillis();
        if (lastCollisionTime > 0 && currentTime - lastCollisionTime < 5000) return;


        float playerX = playerController.getPlayer().getPlayerSprite().getX();
        float playerY = playerController.getPlayer().getPlayerSprite().getY();
        float playerWidth = playerController.getPlayer().getPlayerSprite().getWidth();
        float playerHeight = playerController.getPlayer().getPlayerSprite().getHeight();

        Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);

        for (Tree tree : trees) {
            Sprite treeSprite = tree.getPlayerSprite();
            Rectangle treeRect = new Rectangle(treeSprite.getX(), treeSprite.getY(),
                treeSprite.getWidth(), treeSprite.getHeight());

            if (playerRect.overlaps(treeRect)) {
                if (App.loggedInUser.isPlaySFX())
                    damageSound.play();
                playerController.getPlayer().decreaseHealth(1);
                lastCollisionTime = currentTime;
                break;
            }
        }

        for (Monster monster : monsters) {
            Sprite monsterSprite = monster.getPlayerSprite();
            Rectangle monsterRect = new Rectangle(monsterSprite.getX(), monsterSprite.getY(),
                monsterSprite.getWidth(), monsterSprite.getHeight());

            if (playerRect.overlaps(monsterRect)) {
                if (monster.isSeed()) {
                    playerController.getPlayer().setXp(playerController.getPlayer().getXp() + 3);
                    monster.setSeed(false);
                    monster.setShouldBeRemoved(true);
                } else {
                    if (App.loggedInUser.isPlaySFX())
                        damageSound.play();
                    playerController.getPlayer().decreaseHealth(1);
                    lastCollisionTime = currentTime;
                }
                break;
            }
        }

        for (Eye eye : eyes) {
            Sprite monsterSprite = eye.getPlayerSprite();
            Rectangle monsterRect = new Rectangle(monsterSprite.getX(), monsterSprite.getY(),
                monsterSprite.getWidth(), monsterSprite.getHeight());

            if (playerRect.overlaps(monsterRect)) {
                if (eye.isSeed()) {
                    playerController.getPlayer().setXp(playerController.getPlayer().getXp() + 3);
                    eye.setSeed(false);
                    eye.setShouldBeRemoved(true);
                } else {
                    if (App.loggedInUser.isPlaySFX())
                        damageSound.play();
                    playerController.getPlayer().decreaseHealth(1);
                    lastCollisionTime = currentTime;
                }
                break;
            }
        }

        for (EnemyBullet eye : enemyBullets) {
            if (eye != null && eye.isReadyToCollide(currentTime)) {
                Sprite monsterSprite = eye.getPlayerSprite();
                Rectangle monsterRect = new Rectangle(monsterSprite.getX(), monsterSprite.getY(),
                    (monsterSprite.getWidth()), monsterSprite.getHeight());

                if (playerRect.overlaps(monsterRect)) {
                    eye.setDead(true);
                    if (App.loggedInUser.isPlaySFX())
                        damageSound.play();
                    playerController.getPlayer().decreaseHealth(1);
                    lastCollisionTime = currentTime;
                    break;
                }
            }
        }
    }

    public boolean willCollideWithTree(float nextX, float nextY) {
        float playerWidth = playerController.getPlayer().getPlayerSprite().getWidth();
        float playerHeight = playerController.getPlayer().getPlayerSprite().getHeight();

        Rectangle nextPlayerRect = new Rectangle(nextX, nextY, playerWidth, playerHeight);

        for (Tree tree : trees) {
            Sprite treeSprite = tree.getPlayerSprite();
            Rectangle treeRect = new Rectangle(treeSprite.getX(), treeSprite.getY(),
                treeSprite.getWidth(), treeSprite.getHeight());

            if (nextPlayerRect.overlaps(treeRect)) {
                return true;
            }
        }

        return false;
    }

    private void checkBulletMonsterCollision() {
        List<Bullet> bullets = new ArrayList<>(weaponController.getBullets());
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Monster> monstersToRemove = new ArrayList<>();
        List<Eye> eyesToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            Rectangle bulletRect = new Rectangle(
                bullet.getSprite().getX(),
                bullet.getSprite().getY(),
                bullet.getSprite().getWidth(),
                bullet.getSprite().getHeight()
            );

            for (Monster monster : monsters) {
                Sprite monsterSprite = monster.getPlayerSprite();
                Rectangle monsterRect = new Rectangle(
                    monsterSprite.getX(),
                    monsterSprite.getY(),
                    monsterSprite.getWidth(),
                    monsterSprite.getHeight()
                );

                if (bulletRect.overlaps(monsterRect)) {
                    bulletsToRemove.add(bullet);

                    int amount = 0;
                    if (App.loggedInUser.getWeapon() == 0)
                        amount = 8;
                    else if (App.loggedInUser.getWeapon() == 1)
                        amount = 3;
                    else if (App.loggedInUser.getWeapon() == 2)
                        amount = 20;


                    float dx = monster.getX() - bullet.getX();
                    float dy = monster.getY() - bullet.getY();
                    float len = (float) Math.sqrt(dx * dx + dy * dy);
                    if (len != 0) {
                        dx /= len;
                        dy /= len;
                    }
                    float knockback = 10f;
                    monster.setX(monster.getX() + dx * knockback);
                    monster.setY(monster.getY() + dy * knockback);


                    monster.setHP(monster.getHP() - amount);

                    if (monster.getHP() <= 0) {
                        monster.setDead(true);
                        App.loggedInUser.setKill(App.loggedInUser.getKill() + 1);
                    }

                    break;
                }
            }

            for (Eye eye : eyes) {
                Sprite monsterSprite = eye.getPlayerSprite();
                Rectangle monsterRect = new Rectangle(
                    monsterSprite.getX(),
                    monsterSprite.getY(),
                    monsterSprite.getWidth(),
                    monsterSprite.getHeight()
                );
                if (bulletRect.overlaps(monsterRect)) {
                    bulletsToRemove.add(bullet);

                    int amount = 0;
                    if (App.loggedInUser.getWeapon() == 0)
                        amount = 8;
                    else if (App.loggedInUser.getWeapon() == 1)
                        amount = 10;
                    else if (App.loggedInUser.getWeapon() == 2)
                        amount = 20;

                    if (App.loggedInUser.getAbility() == 1 && !ability3Active) {
                        ability3Active = true;
                        amount *= 1.25;
                    }
                    if (ability3Active && TimeUtils.timeSinceMillis(ability3StartTime) > 10_000) {
                        ability3Active = false;
                        amount /= 1.25;
                        App.loggedInUser.setAbility(5);
                    }

                    float dx = eye.getX() - bullet.getX();
                    float dy = eye.getY() - bullet.getY();
                    float len = (float) Math.sqrt(dx * dx + dy * dy);
                    if (len != 0) {
                        dx /= len;
                        dy /= len;
                    }
                    float knockback = 10f;
                    eye.setX(eye.getX() + dx * knockback);
                    eye.setY(eye.getY() + dy * knockback);


                    eye.setHP(eye.getHP() - amount);

                    if (eye.getHP() <= 0) {
                        eye.setDead(true);
                        App.loggedInUser.setKill(App.loggedInUser.getKill() + 1);
                    }

                    break;
                }

            }
            for (EnemyBullet eye : enemyBullets) {
                Sprite monsterSprite = eye.getPlayerSprite();
                Rectangle monsterRect = new Rectangle(
                    monsterSprite.getX(),
                    monsterSprite.getY(),
                    monsterSprite.getWidth(),
                    monsterSprite.getHeight()
                );

                if (bulletRect.overlaps(monsterRect)) {
                    bulletsToRemove.add(bullet);
                    eye.setShouldBeRemoved(true);

                    break;
                }
            }
        }


        weaponController.getBullets().removeAll(bulletsToRemove);


        List<Monster> monsterList = new ArrayList<>(Arrays.asList(monsters));
        monsterList.removeAll(monstersToRemove);
        monsterCount = monsterList.size();
        monsters = monsterList.toArray(new Monster[0]);

        List<Eye> eyeList = new ArrayList<>(Arrays.asList(eyes));
        eyeList.removeAll(eyesToRemove);
        eyeCount = eyeList.size();
        eyes = eyeList.toArray(new Eye[0]);
    }

    private void updateDeadMonsters(float delta) {
        for (Monster monster : monsters) {
            if (monster.isDead() && !monster.isSeed()) {
                monster.setTime(monster.getTime() + delta);

                Animation<Texture> death = GameAssetManager.getGameAssetManager().getDeath_frames();
                monster.getPlayerSprite().setRegion(death.getKeyFrame(monster.getTime()));
                if (!death.isAnimationFinished(monster.getTime())) {
                    monster.setTime(monster.getTime() + Gdx.graphics.getDeltaTime());
                }

                if (death.isAnimationFinished(monster.getTime())) {
                    // monster.setShouldBeRemoved(true);
                    monster.setSeed(true);
                }
            }
        }

        List<Monster> remainingMonsters = new ArrayList<>();
        List<Monster> speedMonster = new ArrayList<>();
        for (Monster monster : monsters) {
            if (!monster.shouldBeRemoved()) {
                remainingMonsters.add(monster);
            } else {
                speedMonster.add(monster);
            }
        }
        monsters = remainingMonsters.toArray(new Monster[0]);
        monsterCount = monsters.length;
    }

    private void updateDead(float delta) {
        for (EnemyBullet monster : enemyBullets) {
            if (monster.isDead()) {
                monster.setTime(monster.getTime() + delta);

                Animation<Texture> death = GameAssetManager.getGameAssetManager().getDeathBullet_frames();
                monster.getPlayerSprite().setRegion(death.getKeyFrame(monster.getTime()));
                monster.getPlayerSprite().setScale(3f);
                if (!death.isAnimationFinished(monster.getTime())) {
                    monster.setTime(monster.getTime() + Gdx.graphics.getDeltaTime());
                }

                if (death.isAnimationFinished(monster.getTime())) {
                    monster.setShouldBeRemoved(true);
                }
            }
        }

        List<EnemyBullet> remainingMonsters = new ArrayList<>();
        // List<Monster> speedMonster = new ArrayList<>();
        for (EnemyBullet monster : enemyBullets) {
            if (!monster.shouldBeRemoved()) {
                remainingMonsters.add(monster);
            } else {
                //speedMonster.add(monster);
            }
        }
        enemyBullets = remainingMonsters;
        // speed = speedMonster.toArray(new Monster[0]);
        // monsterCount = monsters.length;
    }

    private void updateDeadEyes(float delta) {
        for (Eye eye : eyes) {
            if (eye.isDead() && !eye.isSeed()) {
                eye.setTime(eye.getTime() + delta);

                Animation<Texture> death = GameAssetManager.getGameAssetManager().getDeathEye_frames();
                eye.getPlayerSprite().setRegion(death.getKeyFrame(eye.getTime()));

                if (!death.isAnimationFinished(eye.getTime())) {
                    eye.setTime(eye.getTime() + Gdx.graphics.getDeltaTime());
                }

                if (death.isAnimationFinished(eye.getTime())) {
                    //eye.setShouldBeRemoved(true);
                    eye.setSeed(true);
                }
            }
        }

        List<Eye> remainingEyes = new ArrayList<>();
        for (Eye eye : eyes) {
            if (!eye.shouldBeRemoved()) {
                remainingEyes.add(eye);
            }
        }

        eyes = remainingEyes.toArray(new Eye[0]);
        eyeCount = eyes.length;
    }

    public void saveGame() {
        GameState save = new GameState();
        save.ammo = App.loggedInUser.getAmmo();
        save.health = App.loggedInUser.getHealth();
        save.time = App.loggedInUser.getTime();
        save.kill = App.loggedInUser.getKill();
        save.score = App.loggedInUser.getScore();
        save.hero = App.loggedInUser.getHero();
        save.level = App.loggedInUser.getLevel();
        save.weapon = App.loggedInUser.getWeapon();
        save.heavy = App.loggedInUser.isHeavy();

        save.monster = new ArrayList<>();
        for (Monster monster : monsters) {
            MonsterData m = new MonsterData();
            m.HP = monster.getHP();
            m.deathTime = monster.getDeathTime();
            m.isDead = monster.isDead();
            m.isSeed = monster.isSeed();
            m.x = monster.getX();
            m.y = monster.getY();
            m.type = monster.getType();
            m.shouldBeRemoved = monster.shouldBeRemoved();
            save.monster.add(m);
        }

        save.eye = new ArrayList<>();
        for (Eye eye : eyes) {
            EyeDate e = new EyeDate();
            e.HP = eye.getHP();
            e.deathTime = eye.getDeathTime();
            e.isDead = eye.isDead();
            e.isSeed = eye.isSeed();
            e.x = eye.getX();
            e.y = eye.getY();
            e.fireRate = eye.getFireRate();
            e.shouldBeRemoved = eye.shouldBeRemoved();
            e.dx = eye.getDx();
            e.dy = eye.getDy();
            save.eye.add(e);
        }

        save.tree = new ArrayList<>();
        for (Tree tree : trees) {
            TreeData t = new TreeData();
            t.x = tree.getX();
            t.y = tree.getY();
            save.tree.add(t);
        }

        GameSaveManager.saveGame(save);
    }


    public static void loadGame() {
        FileHandle file = Gdx.files.local("savegame.json");
        if (file.exists()) {
            Json json = new Json();
            GameState save = json.fromJson(GameState.class, file.readString());

            for (int i = 0; i < monsters.length; i++) {
                monsters[i] = null;
            }
            monsterCount = 0;

            for (MonsterData ed : save.monster) {
                Monster e = new Monster();
                e.setSeed(ed.isSeed);
                e.setX(ed.x);
                e.setY(ed.y);
                e.setShouldBeRemoved(ed.shouldBeRemoved);
                e.setDeathTime(ed.deathTime);
                e.setType(ed.type);
                e.setHP(ed.HP);
                e.setDead(ed.isDead);

                monsters[monsterCount++] = e;
            }

            for (int i = 0; i < eyes.length; i++) {
                eyes[i] = null;
            }
            eyeCount = 0;

            for (EyeDate ed : save.eye) {
                Eye e = new Eye();
                e.setSeed(ed.isSeed);
                e.setX(ed.x);
                e.setY(ed.y);
                e.setShouldBeRemoved(ed.shouldBeRemoved);
                e.setDeathTime(ed.deathTime);
                e.setHP(ed.HP);
                e.setDead(ed.isDead);

                eyes[eyeCount++] = e;
            }

            for (int i = 0; i < trees.length; i++) {
                trees[i] = null;
            }

            treeCount = 0;

            for (TreeData ed : save.tree) {
                Tree e = new Tree();
                e.setX(ed.x);
                e.setY(ed.y);

                trees[treeCount++] = e;
            }

            App.loggedInUser.setTime(save.time);
            App.loggedInUser.setKill(save.kill);
            App.loggedInUser.setScore(save.score);
            App.loggedInUser.setHero(save.hero);
            App.loggedInUser.setLevel(save.level);
            App.loggedInUser.setWeapon(save.weapon);
            App.loggedInUser.setHeavy(save.heavy);
            App.loggedInUser.setHealth(save.health);
            App.loggedInUser.setAmmo(save.ammo);
        }

    }

}
