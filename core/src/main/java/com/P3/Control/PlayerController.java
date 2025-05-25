package com.P3.Control;

import com.P3.Model.App;
import com.P3.View.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.P3.Main;
import com.P3.Model.GameAssetManager;
import com.P3.Model.Player;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerController {
    private Player player;
    private GameController gameController;
    private boolean ability3Active = false;
    private long ability3StartTime = 0;
    private boolean first = false;
    private boolean first2 = false;
    private boolean first3 = false;
    private boolean first4 = false;

    public PlayerController(Player player, GameController gameController) {
        this.player = player;
        this.gameController = gameController;
    }

    public void update() {
        player.getPlayerSprite().draw(Main.getBatch());
        int xp = player.getXp();
        if (xp == 21) {
            App.loggedInUser.setLevel(2);
            if (!first) {
                first = true;
                GameView.setIsLevelUp(1);
            }
        } else if(xp==42){
            App.loggedInUser.setLevel(3);
            if (!first2) {
                first2 = true;
                GameView.setIsLevelUp(1);
            }
        } else if(xp==60){
            App.loggedInUser.setLevel(4);
            if (!first3) {
                first3 = true;
                GameView.setIsLevelUp(1);
            }
        } else if(xp==81){
            App.loggedInUser.setLevel(4);
            if (!first4) {
                first4 = true;
                GameView.setIsLevelUp(1);
            }
        }

        if (isMoving()) {
            walkAnimation();
        } else {
            idleAnimation();
        }


        handlePlayerInput();
    }

    private boolean isMoving() {
        if (!App.loggedInUser.isKeyboard()) {
            return Gdx.input.isKeyPressed(Input.Keys.I) ||
                Gdx.input.isKeyPressed(Input.Keys.J) ||
                Gdx.input.isKeyPressed(Input.Keys.K) ||
                Gdx.input.isKeyPressed(Input.Keys.L);
        } else {
            return Gdx.input.isKeyPressed(Input.Keys.W) ||
                Gdx.input.isKeyPressed(Input.Keys.A) ||
                Gdx.input.isKeyPressed(Input.Keys.S) ||
                Gdx.input.isKeyPressed(Input.Keys.D);
        }
    }


    public void handlePlayerInput() {
        if (App.loggedInUser.getAbility() == 4 && !ability3Active) {
            ability3Active = true;
            ability3StartTime = TimeUtils.millis();
            player.setSpeed(player.getSpeed() * 2);
        }
        if (ability3Active && TimeUtils.timeSinceMillis(ability3StartTime) > 10_000) {
            ability3Active = false;
            player.setSpeed(player.getSpeed() / 2);
            App.loggedInUser.setAbility(5);
        }

        if (App.loggedInUser.isKeyboard()) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) && !gameController.willCollideWithTree(player.getPosX(), player.getPosY() - player.getSpeed())) {
                player.setPosY(player.getPosY() - player.getSpeed());
                setPlayer(player);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && !gameController.willCollideWithTree(player.getPosX() - player.getSpeed(), player.getPosY())) {
                player.setPosX(player.getPosX() - player.getSpeed());
                setPlayer(player);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) && !gameController.willCollideWithTree(player.getPosX(), player.getPosY() + player.getSpeed())) {
                player.setPosY(player.getPosY() + player.getSpeed());
                setPlayer(player);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.setPosX(player.getPosX() + player.getSpeed());
                setPlayer(player);
                player.getPlayerSprite().flip(true, false);
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                player.setPosY(player.getPosY() - player.getSpeed());
                setPlayer(player);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                player.setPosX(player.getPosX() - player.getSpeed());
                setPlayer(player);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                player.setPosY(player.getPosY() + player.getSpeed());
                setPlayer(player);

            }
            if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                player.setPosX(player.getPosX() + player.getSpeed());
                setPlayer(player);
                player.getPlayerSprite().flip(true, false);
            }
        }
    }

    public void walkAnimation() {
        Animation<Texture> animation = null;

        switch (App.loggedInUser.getHero()) {
            case 0:
                break;
            case 1:
                animation = GameAssetManager.getGameAssetManager().getWalk1_frames();
                break;
            case 2:
                animation = GameAssetManager.getGameAssetManager().getWalk2_frames();
                break;
            case 3:
                break;
            case 4:
                break;
        }

        if (animation != null) {
            player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));
            if (!animation.isAnimationFinished(player.getTime())) {
                player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                player.setTime(0);
            }
            animation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }


    public void idleAnimation() {
        if (App.loggedInUser.getHero() == 1) {
            Animation<Texture> animation = GameAssetManager.getGameAssetManager().getCharacter1_idle_animation();

            player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

            if (!animation.isAnimationFinished(player.getTime())) {
                player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                player.setTime(0);
            }


            animation.setPlayMode(Animation.PlayMode.LOOP);
        } else if (App.loggedInUser.getHero() == 2) {
            Animation<Texture> animation = GameAssetManager.getGameAssetManager().getCharacter2_idle_frames();

            player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

            if (!animation.isAnimationFinished(player.getTime())) {
                player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                player.setTime(0);
            }

            animation.setPlayMode(Animation.PlayMode.LOOP);
        } else if (App.loggedInUser.getHero() == 3) {
            Animation<Texture> animation = GameAssetManager.getGameAssetManager().getCharacter3_idle_frames();

            player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

            if (!animation.isAnimationFinished(player.getTime())) {
                player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                player.setTime(0);
            }

            animation.setPlayMode(Animation.PlayMode.LOOP);
        } else if (App.loggedInUser.getHero() == 4) {
            Animation<Texture> animation = GameAssetManager.getGameAssetManager().getCharacter4_idle_frames();

            player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

            if (!animation.isAnimationFinished(player.getTime())) {
                player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                player.setTime(0);
            }

            animation.setPlayMode(Animation.PlayMode.LOOP);
        } else if (App.loggedInUser.getHero() == 0) {
            Animation<Texture> animation = GameAssetManager.getGameAssetManager().getCharacter0_idle_frames();

            player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

            if (!animation.isAnimationFinished(player.getTime())) {
                player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                player.setTime(0);
            }

            animation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
