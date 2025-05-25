package com.P3.View;

import com.P3.Control.StartController;
import com.P3.Main;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.P3.Model.Repo.UserRepo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartView implements Screen {
    private static int languge = 1;
    private Texture backgroundTexture;
    private Texture backgroundTexture2;
    private Image backgroundImage;
    private float elapsedTime = 0;
    private boolean showingFirst = true;
    private Stage stage;
    private TextButton signupButton;
    private TextButton mainMenuButton;
    private TextButton exitButton;
    private SelectBox<String> languageBox;
    public Table table;


    private StartController controller;

    public StartView(StartController controller, Skin skin) {
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("start1.png"));
        this.backgroundTexture2 = new Texture(Gdx.files.internal("start2.png"));
        this.backgroundImage = new Image(backgroundTexture);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/IMFePIit28P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 65;
        BitmapFont bigFont = generator.generateFont(parameter);
        generator.dispose();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = bigFont;
        style.fontColor = Color.FOREST;
        style.overFontColor = Color.GREEN;
        style.downFontColor = Color.GREEN;
        if (languge == 1) {
            this.signupButton = new TextButton("SignUp", style);
            this.mainMenuButton = new TextButton("MainMenu", style);
            this.exitButton = new TextButton("Exit", style);
            this.languageBox = new SelectBox<>(skin);
            languageBox.setItems("English", "Franch");
        } else if (languge == 2) {
            this.signupButton = new TextButton("Inscription", style);
            this.mainMenuButton = new TextButton("MenuPrincipal", style);
            this.exitButton = new TextButton("Quitter", style);
            this.languageBox = new SelectBox<>(skin);
            languageBox.setItems("Franch", "English");
        }

        this.table = new Table();

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table.setFillParent(true);
        table.center().padTop(100);
        table.row().pad(10, 0, 20, 0);
        table.setPosition(0, -100);
        table.add(signupButton).width(500).height(60);

        table.row().pad(10, 0, 20, 0);
        table.add(mainMenuButton).width(500).height(60);

        table.row().pad(10, 0, 20, 0);
        table.add(languageBox).width(500).height(85);

        table.row().pad(10, 0, 20, 0);
        table.add(exitButton).width(500).height(60);

        stage.addActor(table);

        languageBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = languageBox.getSelected();
                switch (selected) {
                    case "English":
                        StartView.setLanguge(1);
                        break;
                    case "Franch":
                        StartView.setLanguge(2);
                        break;

                }
                Gdx.app.postRunnable(() -> {
                    Main.getMain().setScreen(new StartView(new StartController(), GameAssetManager.getGameAssetManager().getSkin()));
                });
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });



    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        elapsedTime += v;
        if (elapsedTime >= 1f) {
            elapsedTime = 0;
            showingFirst = !showingFirst;
            Texture newTex = showingFirst ? backgroundTexture : backgroundTexture2;
            backgroundImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newTex)));
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (Main.grayscaleEnabled && Main.grayscaleShader != null && Main.grayscaleShader.isCompiled())
            stage.getBatch().setShader(Main.grayscaleShader);
        else
            stage.getBatch().setShader(null);
        stage.draw();

        controller.handleButtons();
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

    public TextButton getSignupButton() {
        return signupButton;
    }

    public TextButton getMainMenuButton() {
        return mainMenuButton;
    }

    public static int getLanguge() {
        return languge;
    }

    public static void setLanguge(int languge) {
        StartView.languge = languge;
    }
}
