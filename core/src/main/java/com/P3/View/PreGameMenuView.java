package com.P3.View;

import com.P3.Control.StartController;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.P3.Control.PreGameMenuController;
import com.P3.Main;

public class PreGameMenuView implements Screen {
    private Stage stage;
    private final Label gameTitle;
    private final TextButton playButton;
    private final SelectBox selectHero;
    private float elapsedTime;
    private float duration;
    private ProgressBar timeBar;
    public Table table;
    private PreGameMenuController controller;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.gameTitle = new Label("Pregame Menu", skin);
        this.selectHero = new SelectBox<>(skin);
        this.playButton = new TextButton("Play", skin);
        this.duration = App.loggedInUser.getTime();
        this.elapsedTime = 0f;
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
        progressBarStyle.knobBefore = skin.newDrawable("white", Color.GREEN);
        progressBarStyle.knob = skin.newDrawable("white", Color.GREEN);
        timeBar = new ProgressBar(0f, duration, 1f, false, progressBarStyle);
        timeBar.setValue(duration);
        timeBar.setAnimateDuration(0.1f);
        timeBar.setWidth(500);
        timeBar.setHeight(30);
        this.table = new Table();
        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Array<String> hero = new Array<>();

        hero.add("hero1");
        hero.add("hero2");
        hero.add("hero3");

        selectHero.setItems(hero);

        table.setFillParent(true);
        table.center();
        table.row().pad(10, 0 , 10 , 0);
        table.add(gameTitle);
        table.row().pad(10, 0 , 10 , 0);
        table.add(selectHero);
        table.row().pad(10, 0 , 10 , 0);
        table.add(playButton);



        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
       controller.handlePreGameMenuButtons();
    }

    @Override
    public void resize(int width, int height) {

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
