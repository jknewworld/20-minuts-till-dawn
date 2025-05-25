package com.P3.View;

import com.P3.Control.MainMenuController;
import com.P3.Control.PreGameMenuController;
import com.P3.Main;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.P3.Model.Repo.UserRepo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreGameView implements Screen {
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Stage stage;
    public Table table;
    private SelectBox<String> heroBox;
    private SelectBox<String> timeBox;
    private SelectBox<String> weaponBox;
    private TextButton startButton;
    private TextButton backButton;
    private PreGameMenuController controller;

    public PreGameView(PreGameMenuController controller, Skin skin) {
        this.backgroundTexture = new Texture("pregame.png");
        this.backgroundImage = new Image(backgroundTexture);
        this.heroBox = new SelectBox<>(skin);
        heroBox.setItems("Shana", "Diamond", "Scarlet", "Lilith", "Dasher");
        if(StartView.getLanguge() == 1) {
            this.startButton = new TextButton("Start Game", skin);
            this.backButton = new TextButton("Back", skin);
        }else if(StartView.getLanguge() == 2) {
            this.startButton = new TextButton("Démarrer le jeu", skin);
            this.backButton = new TextButton("Retour", skin);
        }
        this.timeBox = new SelectBox<>(skin);
        timeBox.setItems("2 Min", "5 Min", "10 Min", "20 Min");
        this.weaponBox = new SelectBox<>(skin);
        weaponBox.setItems("SMGs Dual", "Shotgun", "Revolver");
        this.table = new Table();
        this.controller = controller;

        controller.setView(this);
    }

    @Override
    public void show() {
        Main.setCustomCursor("m.png");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table.setFillParent(true);
        table.center();
        table.row().pad(10, 0, 10, 0);
        table.add(heroBox).width(500).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(timeBox).width(500).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(weaponBox).width(500).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(startButton).width(500).height(100);
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).width(500).height(100);

        stage.addActor(table);

        heroBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = heroBox.getSelected();
                switch (selected) {
                    case "Shana":
                        App.loggedInUser.setHero(0);
                        App.loggedInUser.setHealth(4);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    case "Diamond":
                        App.loggedInUser.setHero(1);
                        App.loggedInUser.setHealth(7);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    case "Scarlet":
                        App.loggedInUser.setHero(2);
                        App.loggedInUser.setHealth(3);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    case "Lilith":
                        App.loggedInUser.setHero(3);
                        App.loggedInUser.setHealth(5);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    case "Dasher":
                        App.loggedInUser.setHero(4);
                        App.loggedInUser.setHealth(2);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                }
            }
        });

        timeBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = timeBox.getSelected();
                switch (selected) {
                    case "2 Min":
                        App.loggedInUser.setTime(60);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    case "5 Min":
                        App.loggedInUser.setTime(300);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    case "10 Min":
                        App.loggedInUser.setTime(600);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    default:
                        App.loggedInUser.setTime(1200);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;

                }
            }
        });

        weaponBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = weaponBox.getSelected();
                switch (selected) {
                    case "SMGs Dual":
                        App.loggedInUser.setWeapon(0);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    case "Shotgun":
                        App.loggedInUser.setWeapon(1);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                    case "Revolver":
                        App.loggedInUser.setWeapon(2);
                        UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                        break;
                }
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                App.loggedInUser.setKill(0);
                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (Main.grayscaleEnabled && Main.grayscaleShader != null && Main.grayscaleShader.isCompiled())
            stage.getBatch().setShader(Main.grayscaleShader);
        else
            stage.getBatch().setShader(null);
        stage.draw();

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
