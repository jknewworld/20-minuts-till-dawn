package com.P3.View;

import com.P3.Control.MainMenuController;
import com.P3.Main;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TalentView implements Screen {
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextButton backButton;
    private Stage stage;
    public Table table;
    private Label label;
    private MainMenuController controller;

    public TalentView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.backgroundTexture = createBackgroundTexture();
        this.backgroundImage = new Image(backgroundTexture);
        this.backButton = createBackButton();
        this.label = createLabel();
        this.table = new Table();

        controller.setView(this);
    }

    private Texture createBackgroundTexture() {
        int lang = StartView.getLanguge();
        if (lang == 1)
            return new Texture(Gdx.files.internal("talentEN.png"));
        else
            return new Texture(Gdx.files.internal("talentF.png"));
    }

    private TextButton createBackButton() {
        BitmapFont font = generateFont("Font/IMFePIit28P.ttf", 65);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = Color.FOREST;
        style.overFontColor = Color.GREEN;
        style.downFontColor = Color.GREEN;

        String text = StartView.getLanguge() == 1 ? "Back" : "Retour";
        return new TextButton(text, style);
    }

    private Label createLabel() {
        BitmapFont font = generateFont("Font/ChevyRay - Express.ttf", 40);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.PURPLE;
        return new Label("", labelStyle);
    }

    private BitmapFont generateFont(String path, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        setupBackground();
        setupBackButton();
        setupLabel();
    }

    private void setupBackground() {
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
    }

    private void setupBackButton() {
        backButton.setSize(200, 100);
        backButton.setPosition(1500, 200);
        stage.addActor(backButton);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
            }
        });
    }

    private void setupLabel() {
        String controls = App.loggedInUser.isKeyboard() ? "W,D,S,A" : "I,L,K,J";
        label.setText(controls);
        label.setSize(300, 100);
        label.setPosition(300, 380);
        stage.addActor(label);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        if (Main.grayscaleEnabled && Main.grayscaleShader != null && Main.grayscaleShader.isCompiled()) {
            stage.getBatch().setShader(Main.grayscaleShader);
        } else {
            stage.getBatch().setShader(null);
        }

        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
