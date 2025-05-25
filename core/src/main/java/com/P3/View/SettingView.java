package com.P3.View;

import com.P3.Control.MainMenuController;
import com.P3.Control.StartController;
import com.P3.Main;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.P3.Model.Repo.UserRepo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.swing.event.ChangeEvent;

public class SettingView implements Screen {
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Stage stage;
    private Music music;
    private Music music1;
    private Slider volumeSlider;
    private TextButton music1Button;
    private TextButton music2Button;
    private TextButton backButton;
    private CheckBox sfxCheckBox;
    private CheckBox classic;
    private CheckBox mouseLeft;
    private CheckBox autoReloadCheckbox;
    private ShaderProgram grayscaleShader;
    private SelectBox<String> keyboardBox;
    private boolean isGrayscale = false;

    public Table table;

    private MainMenuController controller;

    public SettingView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("settingbackground.png"));
        this.backgroundImage = new Image(backgroundTexture);
        if (StartView.getLanguge() == 1) {
            this.music1Button = new TextButton("Music1", skin);
            this.music2Button = new TextButton("Music2", skin);
            this.backButton = new TextButton("Back", skin);
            this.sfxCheckBox = new CheckBox("SFX?", skin);
            autoReloadCheckbox = new CheckBox("Not Auto Reload", skin);
            autoReloadCheckbox.setChecked(App.autoReloadEnabled);
            this.classic = new CheckBox("Do U Want Classic?", skin);
            classic.setChecked(Main.grayscaleEnabled);
            this.mouseLeft = new CheckBox("Left Mouse?", skin);
            mouseLeft.setChecked(App.mouseLeft);
            this.keyboardBox = new SelectBox<>(skin);
            keyboardBox.setItems("Choose your buttons", "W,D,S,A", "I,L,K,J");
        } else if (StartView.getLanguge() == 2) {
            this.music1Button = new TextButton("Musique1", skin);
            this.music2Button = new TextButton("Musique2", skin);
            this.backButton = new TextButton("Retour", skin);
            this.sfxCheckBox = new CheckBox("Supprimer les effets sonores?", skin);
            sfxCheckBox.setChecked(App.loggedInUser.isPlaySFX());
            this.autoReloadCheckbox = new CheckBox("Rechargement automatique", skin);
            autoReloadCheckbox.setChecked(App.autoReloadEnabled);
            this.classic = new CheckBox("Veux-tu le mode classique?", skin);
            classic.setChecked(Main.grayscaleEnabled);
            this.mouseLeft = new CheckBox("bouton gauche de la souris?", skin);
            mouseLeft.setChecked(App.mouseLeft);
            this.keyboardBox = new SelectBox<>(skin);
            keyboardBox.setItems("Choisis tes boutons", "W,D,S,A", "I,L,K,J");
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
        volumeSlider = new Slider(0f, 2f, 0.5f, false, GameAssetManager.getGameAssetManager().getSkin());
        volumeSlider.setValue(0.5f);
        volumeSlider.addListener(event -> {
            music.setVolume(volumeSlider.getValue());
            return false;
        });

        table.add(volumeSlider).width(700);
        table.row().pad(10, 0, 10, 0);
        table.add(sfxCheckBox).width(500).height(100);
        table.row().pad(10, 0, 10, 0);
        table.add(autoReloadCheckbox).width(500).height(100);
        table.row().pad(10, 0, 10, 0);
        table.add(mouseLeft).width(500).height(100);
        table.row().pad(10, 0, 10, 0);
        table.add(classic).width(500).height(100);
        table.row().pad(10, 0, 10, 0);


        Table buttonTable = new Table();
        buttonTable.add(music1Button).width(250).height(100).padRight(5);
        buttonTable.add(music2Button).width(250).height(100).padLeft(5);
        table.add(buttonTable).width(500);

        table.row().pad(10, 0, 10, 0);
        table.add(keyboardBox).width(500).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).width(500).height(100);


        stage.addActor(table);

        music1Button.addListener(event -> {
            if (music != null) music.stop();
            music = Gdx.audio.newMusic(Gdx.files.internal("music1.mp3"));
            music.setLooping(true);
            music.setVolume(volumeSlider.getValue());
            music.play();
            return false;
        });

        music2Button.addListener(event -> {
            if (music != null) music.stop();
            music = Gdx.audio.newMusic(Gdx.files.internal("music2.mp3"));
            music.setLooping(true);
            music.setVolume(volumeSlider.getValue());
            music.play();
            return false;
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });


        classic.addListener(event -> {
            Main.grayscaleEnabled = classic.isChecked();

            if (Main.grayscaleShader == null) {
                ShaderProgram.pedantic = false;
                Main.grayscaleShader = new ShaderProgram(
                    Gdx.files.internal("shaders/grayscale.vertex.glsl"),
                    Gdx.files.internal("shaders/grayscale.fragment.glsl")
                );
                if (!Main.grayscaleShader.isCompiled()) {
                    System.err.println("Shader compile error: " + Main.grayscaleShader.getLog());
                }
            }
            return false;
        });

        sfxCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.loggedInUser.setPlaySFX(sfxCheckBox.isChecked());
            }
        });
        autoReloadCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.autoReloadEnabled = autoReloadCheckbox.isChecked();
            }
        });

        keyboardBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = keyboardBox.getSelected();
                if (selected.equals("W,D,S,A")) {
                    App.loggedInUser.setKeyboard(true);
                    UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                } else if (selected.equals("I,L,K,J")) {
                    App.loggedInUser.setKeyboard(false);
                    UserRepo.saveUser(UserRepo.findUserByUsername(App.loggedInUser.getUsername()));
                }
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
