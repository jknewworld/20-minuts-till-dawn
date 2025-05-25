package com.P3.View;

import com.P3.Control.GameController;
import com.P3.Control.PreGameMenuController;
import com.P3.Control.StartController;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import com.P3.Control.MainMenuController;
import com.P3.Main;

public class MainMenuView implements Screen {
    private Stage stage;
    private Texture backgroundTexture;
    private Texture avatar;
    private Label name;
    private Label score;
    private TextButton settingButton;
    private TextButton profileButton;
    private TextButton pre_gameButton;
    private TextButton scoreboardButton;
    private TextButton talentButton;
    private TextButton saveButton;
    private TextButton backButton;
    private TextButton logooutButton;
    private Image backgroundImage;
    private Image avatarImage;
    private FileHandle handle = Gdx.files.absolute(App.loggedInUser.getAvatarPath());
    public Table table;

    private MainMenuController controller;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("mainmenubackground.png"));
        this.backgroundImage = new Image(backgroundTexture);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/IMFePIit28P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 23;
        BitmapFont bigFont = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bigFont;
        if (StartView.getLanguge() == 1) {

            this.settingButton = new TextButton("Setting", skin);
            this.profileButton = new TextButton("Profile", skin);
            this.pre_gameButton = new TextButton("Pre Game", skin);
            this.scoreboardButton = new TextButton("Scoreboard", skin);
            this.talentButton = new TextButton("Talent", skin);
            this.saveButton = new TextButton("Save Game", skin);
            this.name = new Label("NAME : " + App.loggedInUser.getUsername(), style);
            this.score = new Label("SCORE : " + App.loggedInUser.getScore(), style);
            this.backButton = new TextButton("Back", skin);
            this.logooutButton = new TextButton("Logout", skin);
        } else if (StartView.getLanguge() == 2) {
            this.settingButton = new TextButton("Paramètres", skin);
            this.profileButton = new TextButton("Profil", skin);
            this.pre_gameButton = new TextButton("Pré-Jeu", skin);
            this.scoreboardButton = new TextButton("Tableau des scores", skin);
            this.talentButton = new TextButton("Talent", skin);
            this.saveButton = new TextButton("Sauvegarder la partie", skin);
            style.fontColor = Color.PINK;
            this.name = new Label("Nom : " + App.loggedInUser.getUsername(), style);
            style.fontColor = Color.BLUE;
            this.score = new Label("Score : " + App.loggedInUser.getScore(), style);
            style.fontColor = Color.PINK;
            this.backButton = new TextButton("Retour", skin);
            this.logooutButton = new TextButton("Logout", skin);
        }

        if (App.loggedInUser.getAvatar() != 20) {
            this.avatar = new Texture(Gdx.files.internal("Avatars/" + App.loggedInUser.getAvatar() + ".png"));
            this.avatarImage = new Image(avatar);
        } else {
            this.avatar = new Texture(handle);
            this.avatarImage = new Image(avatar);
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


        avatarImage.setSize(192, 261);
        avatarImage.setPosition(1150, 600);
        stage.addActor(avatarImage);

        name.setFontScale(2.0f);
        name.setColor(Color.PINK);
        name.setPosition(570, 750);
        stage.addActor(name);

        score.setColor(Color.PURPLE);
        score.setFontScale(2.0f);
        score.setColor(0.5f, 1f, 0.5f, 1f);
        score.setPosition(570, 650);
        stage.addActor(score);

        table.setFillParent(true);
        table.center().padTop(250);
        table.row().pad(10, 0, 10, 0);
        Table buttonTable1 = new Table();
        buttonTable1.add(settingButton).width(400).height(100).padRight(5);
        buttonTable1.add(profileButton).width(400).height(100).padLeft(5);
        table.add(buttonTable1).width(500);
        table.setPosition(0, -50);

        table.row().pad(10, 0, 10, 0);
        Table buttonTable2 = new Table();
        buttonTable2.add(pre_gameButton).width(400).height(100).padRight(5);
        buttonTable2.add(scoreboardButton).width(400).height(100).padLeft(5);
        table.add(buttonTable2).width(500);

        table.row().pad(10, 0, 10, 0);
        Table buttonTable3 = new Table();
        buttonTable3.add(talentButton).width(400).height(100).padRight(5);
        buttonTable3.add(saveButton).width(400).height(100).padLeft(5);
        table.add(buttonTable3).width(500);

        table.row().pad(10, 0, 10, 0);
        Table buttonTable4 = new Table();
        buttonTable4.add(backButton).width(400).height(100).padRight(5);
        buttonTable4.add(logooutButton).width(400).height(100).padLeft(5);
        table.add(buttonTable4).width(500);

        stage.addActor(table);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new StartView(new StartController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        logooutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.loggedInUser = null;
                table.clear();
                Main.getMain().setScreen(new StartView(new StartController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new SettingView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        talentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new TalentView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //  if (App.loggedInUser.getPassword().equals("ghost")) {
                table.clear();
                Main.getMain().setScreen(new ProfileView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                //  }
            }
        });

        pre_gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new PreGameView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new ScoreboardView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                GameController.getInstance().loadGame();
                Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public TextButton getSettingButton() {
        return settingButton;
    }

    public void setSettingButton(TextButton settingButton) {
        this.settingButton = settingButton;
    }

    public TextButton getProfileButton() {
        return profileButton;
    }

    public void setProfileButton(TextButton profileButton) {
        this.profileButton = profileButton;
    }

    public TextButton getPre_gameButton() {
        return pre_gameButton;
    }

    public void setPre_gameButton(TextButton pre_gameButton) {
        this.pre_gameButton = pre_gameButton;
    }

    public TextButton getScoreboardButton() {
        return scoreboardButton;
    }

    public void setScoreboardButton(TextButton scoreboardButton) {
        this.scoreboardButton = scoreboardButton;
    }

    public TextButton getTalentButton() {
        return talentButton;
    }

    public void setTalentButton(TextButton talentButton) {
        this.talentButton = talentButton;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public MainMenuController getController() {
        return controller;
    }

    public void setController(MainMenuController controller) {
        this.controller = controller;
    }
}
