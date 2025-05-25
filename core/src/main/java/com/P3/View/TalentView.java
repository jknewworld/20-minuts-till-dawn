package com.P3.View;

import com.P3.Control.MainMenuController;
import com.P3.Control.StartController;
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
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/ChevyRay - Express.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        BitmapFont bigFont = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bigFont;
        labelStyle.fontColor = Color.PURPLE;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/IMFePIit28P.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 65;
        bigFont = generator.generateFont(parameter);
        generator.dispose();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = bigFont;
        style.fontColor = Color.FOREST;
        style.overFontColor = Color.GREEN;
        style.downFontColor = Color.GREEN;
        if (StartView.getLanguge() == 1) {
            this.backgroundTexture = new Texture(Gdx.files.internal("talentEN.png"));
            this.backButton = new TextButton("Back", style);
        }
        else if (StartView.getLanguge() == 2) {
            this.backgroundTexture = new Texture(Gdx.files.internal("talentF.png"));
            this.backButton = new TextButton("Retour", style);
        }
        this.backgroundImage = new Image(backgroundTexture);
        this.label = new Label("", labelStyle);
        this.table = new Table();

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        backButton.setSize(200, 100);
        backButton.setPosition(1500,200);
        stage.addActor(backButton);

        if(App.loggedInUser.isKeyboard())
            label.setText("W,D,S,A");
        else
            label.setText("I,L,K,J");

        label.setSize(300,100);
        label.setPosition(300,380);
        stage.addActor(label);

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
