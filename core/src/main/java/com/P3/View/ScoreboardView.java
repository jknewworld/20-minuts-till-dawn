package com.P3.View;

import com.P3.Control.MainMenuController;
import com.P3.Control.StartController;
import com.P3.Main;
import com.P3.Model.GameAssetManager;
import com.P3.Model.ScoreBoard;
import com.P3.Model.App;
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

public class ScoreboardView implements Screen {
    private final Image backgroundImage;
    private TextButton backButton;
    private Stage stage;
    public Table table;
    private Table userRowsTable = new Table();
    private Label rank;
    private Label username;
    private Label score;
    private Label kill;
    private Label time;
    ScoreBoard scoreboard;


    private MainMenuController controller;


    public ScoreboardView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        Texture backgroundTexture = new Texture(Gdx.files.internal("scoreboard.png"));
        if (StartView.getLanguge() == 1) {
            this.backButton = new TextButton("Back", skin);
        } else if (StartView.getLanguge() == 2) {
            this.backButton = new TextButton("Retour", skin);
        }
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/ChevyRay - Express.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont bigFont = generator.generateFont(parameter);
        bigFont.setColor(Color.FOREST);
        generator.dispose();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bigFont;
        this.backgroundImage = new Image(backgroundTexture);
        this.rank = new Label("Rank", style);
        rank.setColor(Color.FOREST);
        this.username = new Label("Username",style);
        username.setColor(Color.VIOLET);
        this.score = new Label("Score", style);
        score.setColor(Color.TEAL);
        this.kill = new Label("Kill", style);
        kill.setColor(Color.RED);
        this.time = new Label("Survive Time", style);
        time.setColor(Color.SKY);
        this.scoreboard = new ScoreBoard(App.loggedInUser.getUsername());
        // this.table = new Table();
        this.table = new Table();


        controller.setView(this);
    }

    @Override
    public void show() {
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

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        scoreboard.sortUsers("score");

        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table.setFillParent(true);
        table.top().center();

        TextButton sortByScore = new TextButton("Sort by Score", style);
        TextButton sortByUsername = new TextButton("Sort by Username", style);
        TextButton sortByKills = new TextButton("Sort by Kills", style);
        TextButton sortBySurvive = new TextButton("Sort by Survive", style);


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new StartView(new StartController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        sortByScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scoreboard.sortUsers("score");
                updateTable();
            }
        });
        sortByUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scoreboard.sortUsers("username");
                updateTable();
            }
        });
        sortByKills.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scoreboard.sortUsers("kills");
                updateTable();
            }
        });
        sortBySurvive.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scoreboard.sortUsers("survive");
                updateTable();
            }
        });

       sortByScore.setSize(400,100);
       sortByUsername.setSize(400,100);
       sortByKills.setSize(400,100);
       sortBySurvive.setSize(400,100);

       sortByScore.setPosition(100, 700);
       sortByUsername.setPosition(1400, 700);
       sortByKills.setPosition(100, 500);
       sortBySurvive.setPosition(1400, 500);


        updateTable();

        stage.addActor(table);
        stage.addActor(backButton);
        stage.addActor(sortByScore);
        stage.addActor(sortByUsername);
        stage.addActor(sortByKills);
        stage.addActor(sortBySurvive);


    }

    private void updateTable() {
        table.clear();
        table.add(rank).pad(5);
        table.add(username).pad(5);
        table.add(score).pad(5);
        table.add(kill).pad(5);
        table.add(time).pad(5);
        table.row();

        int maxDisplay = 10;
        boolean currentUserInTop = false;
        String currentUsername = App.loggedInUser.getUsername();

        for (int i = 0; i < Math.min(scoreboard.getUsers().size(), maxDisplay); i++) {
            String rank1 = String.valueOf(i + 1);
            String username1 = scoreboard.getUsers().get(i).getUsername();
            String score1 = String.valueOf(scoreboard.getUsers().get(i).getScore());
            String kills1 = String.valueOf(scoreboard.getUsers().get(i).getKill());
            String survive1 = String.valueOf(scoreboard.getUsers().get(i).getTime());

            Label rankLabel = new Label(rank1, GameAssetManager.getGameAssetManager().getSkin());
            Label usernameLabel = new Label(username1, GameAssetManager.getGameAssetManager().getSkin());
            Label scoreLabel = new Label(score1, GameAssetManager.getGameAssetManager().getSkin());
            Label killsLabel = new Label(kills1, GameAssetManager.getGameAssetManager().getSkin());
            Label surviveLabel = new Label(survive1, GameAssetManager.getGameAssetManager().getSkin());

            if (i == 0) {
                rankLabel.setColor(Color.GOLD);
                usernameLabel.setColor(Color.GOLD);
                scoreLabel.setColor(Color.GOLD);
                killsLabel.setColor(Color.GOLD);
                surviveLabel.setColor(Color.GOLD);
            } else if (i == 1) {
                rankLabel.setColor(Color.GRAY);
                usernameLabel.setColor(Color.GRAY);
                scoreLabel.setColor(Color.GRAY);
                killsLabel.setColor(Color.GRAY);
                surviveLabel.setColor(Color.GRAY);
            } else if (i == 2) {
                Color bronze = new Color(0.8f, 0.5f, 0.2f, 1);
                rankLabel.setColor(bronze);
                usernameLabel.setColor(bronze);
                scoreLabel.setColor(bronze);
                killsLabel.setColor(bronze);
                surviveLabel.setColor(bronze);
            }

            if (username1.equals(currentUsername)) {
                currentUserInTop = true;
                // می‌توانی یک جلوه خاص مثل ضخیم کردن فونت یا رنگ متفاوت‌تر بدهی:
                usernameLabel.setColor(Color.CYAN);
            }

            table.add(rankLabel).pad(5);
            table.add(usernameLabel).pad(5);
            table.add(scoreLabel).pad(5);
            table.add(killsLabel).pad(5);
            table.add(surviveLabel).pad(5);
            table.row();
        }

        if (!currentUserInTop) {
            int currentUserRank = -1;
            for (int i = 0; i < scoreboard.getUsers().size(); i++) {
                if (scoreboard.getUsers().get(i).getUsername().equals(currentUsername)) {
                    currentUserRank = i + 1;
                    break;
                }
            }
            if (currentUserRank != -1) {
                Label separator = new Label("...", GameAssetManager.getGameAssetManager().getSkin());
                table.add(separator).colspan(5).center().pad(10);
                table.row();

                int i = currentUserRank - 1;
                Label rankLabel = new Label(String.valueOf(currentUserRank), GameAssetManager.getGameAssetManager().getSkin());
                Label usernameLabel = new Label(currentUsername, GameAssetManager.getGameAssetManager().getSkin());
                Label scoreLabel = new Label(String.valueOf(scoreboard.getUsers().get(i).getScore()), GameAssetManager.getGameAssetManager().getSkin());
                Label killsLabel = new Label(String.valueOf(scoreboard.getUsers().get(i).getKill()), GameAssetManager.getGameAssetManager().getSkin());
                Label surviveLabel = new Label(String.valueOf(scoreboard.getUsers().get(i).getTime()), GameAssetManager.getGameAssetManager().getSkin());

                rankLabel.setColor(Color.CYAN);
                usernameLabel.setColor(Color.CYAN);
                scoreLabel.setColor(Color.CYAN);
                killsLabel.setColor(Color.CYAN);
                surviveLabel.setColor(Color.CYAN);

                table.add(rankLabel).pad(5);
                table.add(usernameLabel).pad(5);
                table.add(scoreLabel).pad(5);
                table.add(killsLabel).pad(5);
                table.add(surviveLabel).pad(5);
                table.row();
            }
        }
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
