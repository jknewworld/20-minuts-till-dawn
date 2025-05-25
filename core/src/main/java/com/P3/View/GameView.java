package com.P3.View;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.P3.Control.PreGameMenuController;
import com.P3.Control.StartController;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.P3.Model.Repo.UserRepo;
import com.P3.Model.Shield;
import com.P3.Model.User;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.P3.Control.GameController;
import com.P3.Main;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleShader.Inputs.screenWidth;

public class GameView implements Screen, InputProcessor {
    // Start Part
    private Stage stage;
    private GameController controller;
    private Stage pauseStage;
    private Stage loseStage;
    private Stage winStage;
    private Stage realPauseMenuStage;
    private OrthographicCamera camera;
    private Viewport viewport;
    public Table table;
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
    private boolean extraLifeUsed = false;
    private ArrayList<String> abilities = new ArrayList<>();
    private float finalElapsedTime = 0;
    private boolean first = true;

    // Time
    private float elapsedTime = 0f;
    private float duration;

    // ProgressBar
    private ProgressBar timeBar;
    private ProgressBar healthBar;
    private boolean isPaused = false;
    private int xp = 0;
    private int maxXp = 0;
    private ProgressBar xpBar;

    // Label
    private Label killLabel;
    private Label level;
    private Label ammoLabel;

    // Button
    private TextButton ability1;
    private TextButton ability2;
    private TextButton ability3;

    // Picture
    private Texture darkOverlayTexture;
    private Texture lightMaskTexture;

    // Chang Stage
    private boolean isLose = false;
    private boolean isWin = false;
    private boolean isRealPause = false;
    private static int isLevelUp = 0;
    private boolean soonBasFight = false;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    // SFX
    private Sound loseSound;
    private Sound winSound;
    private Sound levelupSound;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        // SFX
        this.loseSound = Gdx.audio.newSound(Gdx.files.internal("sfx/lose.wav"));
        this.winSound = Gdx.audio.newSound(Gdx.files.internal("sfx/win.wav"));
        this.levelupSound = Gdx.audio.newSound(Gdx.files.internal("sfx/levelup.wav"));
        this.elapsedTime = 0f;

        this.duration = App.loggedInUser.getTime();

        // TimeBar
        timeBar = new ProgressBar(0f, App.loggedInUser.getMaxTime(), 1f, false,
            skin.get("mana", ProgressBar.ProgressBarStyle.class));
        timeBar.setValue(App.loggedInUser.getTime());
        timeBar.setAnimateDuration(0.1f);
        timeBar.setWidth(400);
        timeBar.setHeight(300);

        // HealthBar
        healthBar = new ProgressBar(0f, App.loggedInUser.getHealth(), 1f, false,
            skin.get("health", ProgressBar.ProgressBarStyle.class));
        healthBar.setValue(App.loggedInUser.getHealth());
        healthBar.setAnimateDuration(0.1f);
        healthBar.setWidth(400);
        healthBar.setHeight(300);

        // XP Bar
        xpBar = new ProgressBar(0f, (App.loggedInUser.getLevel() * 20) + 1 * 20, 1f, false,
            skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class));
        xpBar.setValue(xp);
        xpBar.setAnimateDuration(0.1f);
        xpBar.setColor(Color.PURPLE);
        xpBar.setWidth(400);
        xpBar.setHeight(500);

        // Label
        if (StartView.getLanguge() == 1)
            killLabel = new Label("Killed " + App.loggedInUser.getKill() + " beasts — even Orcs would be jealous!"
                , skin);
        else if (StartView.getLanguge() == 2)
            killLabel = new Label("Monstres vaincus " + App.loggedInUser.getKill() + " — Pas mal pour un hobbit !"
                , skin);

        level = new Label("Level " + App.loggedInUser.getLevel(), skin);
        this.ammoLabel = new Label("Ammo " + App.loggedInUser.getAmmo(), skin);

        // Cammera
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        darkOverlayTexture = new Texture(pixmap);
        pixmap.dispose();

        lightMaskTexture = new Texture(Gdx.files.internal("l.png"));
        // Tabel
        this.table = new Table();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        controller.setView(this);

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        InputMultiplexer multiplexer = new InputMultiplexer();
        shapeRenderer = new ShapeRenderer();

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        killLabel.setFontScale(0.5f);
        killLabel.setColor(Color.GOLD);
        killLabel.setPosition(30, 780);
        stage.addActor(killLabel);

        level.setFontScale(0.7f);
        level.setColor(Color.NAVY);
        level.setPosition(30, 740);
        stage.addActor(level);

        ammoLabel.setFontScale(0.7f);
        ammoLabel.setColor(Color.TEAL);
        ammoLabel.setPosition(30, 700);
        stage.addActor(ammoLabel);


        table.setFillParent(true);
        timeBar.setSize(300, 160);
        timeBar.setPosition(30, 900);
        stage.addActor(timeBar);
        healthBar.setSize(300, 160);
        healthBar.setPosition(30, 800);
        stage.addActor(healthBar);
        xpBar.setSize(200, 500);
        xpBar.setPosition(30, 300);
        xpBar.setColor(Color.GRAY);
        stage.addActor(xpBar);

        stage.addActor(table);

        if (App.loggedInUser.getHealth() == 0) {
            isLose = true;
            createLoserMenu(GameAssetManager.getGameAssetManager().getSkin());
            Gdx.input.setInputProcessor(loseStage);
        } else if (timeBar.getValue() == 0) {
            isWin = true;
            createWinnerMenu(GameAssetManager.getGameAssetManager().getSkin());
            Gdx.input.setInputProcessor(winStage);
        }
    }

    @Override
    public void render(float delta) {
        clearScreen();
        beginDrawing();
        if (shouldRenderNormalGame())
            renderNormalGame(delta);
        updateStatusLabels();
        endDrawing();
        float frameTime = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
        if (shouldRenderStage(stage)) {
            renderStage(stage, frameTime);
            if (controller.isShieldActive()) {
                Shield shield = controller.getShield();

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.RED);

                Rectangle bounds = shield.getBounds();
                shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

                shapeRenderer.end();
            }
            return;
        }
        if (shouldRevivePlayer()) {
            revivePlayer();
            return;
        } else if (isLose) {
            renderStage(loseStage, frameTime);
            return;
        } else if (isWin) {
            renderStage(winStage, frameTime);
            return;
        } else if (isRealPause) {
            renderStage(realPauseMenuStage, frameTime);
            return;
        }
        renderStage(pauseStage, frameTime);
    }

    private void clearScreen() {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    private void beginDrawing() {
        Main.getBatch().begin();
    }

    private void endDrawing() {
        Main.getBatch().end();
    }

    private boolean shouldRenderNormalGame() {
        return !isPaused && !isLose && !isWin && !isRealPause;
    }

    private void renderNormalGame(float delta) {
        normalMode(delta);
    }

    private void updateStatusLabels() {
        int language = StartView.getLanguge();
        int killCount = App.loggedInUser.getKill();

        if (language == 1) {
            killLabel.setText("Killed " + killCount + " beasts — even Orcs would be jealous!");
        } else if (language == 2) {
            killLabel.setText("Monstres vaincus " + killCount + " — Pas mal pour un hobbit !");
        }

        level.setText("Level " + App.loggedInUser.getLevel());
        ammoLabel.setText("Ammo " + App.loggedInUser.getAmmo());
    }

    private boolean shouldRenderStage(Stage stage) {
        return !isPaused && !isLose && !isWin && isLevelUp == 0 && !isRealPause;
    }

    private boolean shouldRevivePlayer() {
        return App.loggedInUser.getHealth() == 0
            && Gdx.input.isKeyPressed(Input.Keys.MINUS)
            && !extraLifeUsed;
    }

    private void renderStage(Stage targetStage, float delta) {
        targetStage.act(delta);

        if (Main.grayscaleEnabled && Main.grayscaleShader != null && Main.grayscaleShader.isCompiled()) {
            targetStage.getBatch().setShader(Main.grayscaleShader);
        } else {
            targetStage.getBatch().setShader(null);
        }

        targetStage.draw();
    }

    private void revivePlayer() {
        App.loggedInUser.setHealth(1);
        extraLifeUsed = true;
        isLose = false;

        if (Main.grayscaleEnabled && Main.grayscaleShader != null && Main.grayscaleShader.isCompiled()) {
            stage.getBatch().setShader(Main.grayscaleShader);
        } else {
            stage.getBatch().setShader(null);
        }

        Gdx.input.setInputProcessor(stage);
    }

    public void normalMode(float delta) {
        controller.updateGame();
        elapsedTime += delta;
        timeBar.setValue(duration - elapsedTime);
        float currentHealth = App.loggedInUser.getHealth();
        healthBar.setValue(currentHealth);
        xpBar.setValue(xp);
        if (App.loggedInUser.getHealth() == 0) {
            finalElapsedTime = elapsedTime;
            isLose = true;
            createLoserMenu(GameAssetManager.getGameAssetManager().getSkin());
            Gdx.input.setInputProcessor(loseStage);
        }

        Main.getBatch().setColor(0, 0, 0, 0.4f);
        Main.getBatch().draw(darkOverlayTexture, 0, 0, screenWidth, screenHeight);

        Main.getBatch().setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);

        float centerX = (float) Gdx.graphics.getWidth() / 2;
        float centerY = (float) Gdx.graphics.getHeight() / 2;
        float radius = 200f;

        Main.getBatch().setColor(1, 1, 1, 1f);
        Main.getBatch().draw(lightMaskTexture, centerX - radius, centerY - radius + 50, radius * 2,
            radius * 2);

        Main.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Main.getBatch().setColor(1, 1, 1, 1f);

        if (isLevelUp == 1) {
            isPaused = true;
            createPauseMenu(GameAssetManager.getGameAssetManager().getSkin());
            Gdx.input.setInputProcessor(pauseStage);
        }

        if (elapsedTime >= duration) {
            finalElapsedTime = elapsedTime;
            isWin = true;
            createWinnerMenu(GameAssetManager.getGameAssetManager().getSkin());
            Gdx.input.setInputProcessor(winStage);
            winStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            winStage.draw();
        }
    }

    private void createPauseMenu(Skin skin) {
        Main.setCustomCursor("m.png");
        pauseStage = new Stage(new ScreenViewport());
        Table pauseTable = new Table();

        if (App.loggedInUser.isPlaySFX())
            levelupSound.play(1.0f);

        addBackground(pauseStage);
        pauseTable.setFillParent(true);

        setupAbilityButtons(skin, pauseTable);
        setupExitButton(skin, pauseTable);

        pauseStage.addActor(pauseTable);
    }

    private void addBackground(Stage stage) {
        Texture backgroundTexture = new Texture("levelup.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
    }

    private void setupAbilityButtons(Skin skin, Table table) {
        Random rand = new Random();

        int num1 = rand.nextInt(5);
        this.ability1 = createAbilityButton(num1, skin);

        int num2 = rand.nextInt(5);
        while (num1 == num2) {
            num2 = rand.nextInt(5);
        }
        this.ability2 = createAbilityButton(num2, skin);

        int num3 = rand.nextInt(5);
        while (num1 == num3 || num2 == num3) {
            num3 = rand.nextInt(5);
        }
        this.ability3 = createAbilityButton(num3, skin);

        addAbilityListener(ability1, num1);
        addAbilityListener(ability2, num2);
        addAbilityListener(ability3, num3);

        table.add(ability1).pad(10);
        table.row();
        table.add(ability2).pad(10);
        table.row();
        table.add(ability3).pad(10);
        table.row();
    }

    private TextButton createAbilityButton(int num, Skin skin) {
        if (num == 0)
            return new TextButton("VITALITY", skin);
        else if (num == 1)
            return new TextButton("DAMAGER", skin);
        else if (num == 2)
            return new TextButton("PROCREASE", skin);
        else if (num == 3)
            return new TextButton("AMOGREASE", skin);
        else // num == 4
            return new TextButton("SPEEDY", skin);
    }

    private void addAbilityListener(TextButton button, int abilityNum) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.loggedInUser.setAbility(abilityNum);
                switch (abilityNum) {
                    case 0:
                        abilities.add("vitality");
                        break;
                    case 1:
                        abilities.add("Damager");
                        break;
                    case 2:
                        abilities.add("Procrease");
                        break;
                    case 3:
                        abilities.add("Aamogrease");
                        break;
                    case 4:
                        abilities.add("Speedy");
                        break;
                }
                setIsLevelUp(0);
                UserRepo.saveUser(App.loggedInUser);
                isPaused = false;
                first = true;
                pauseStage.clear();
                InputMultiplexer multiplexer = new InputMultiplexer();
                multiplexer.addProcessor(stage);
                multiplexer.addProcessor(GameView.this);
                Gdx.input.setInputProcessor(multiplexer);
            }
        });
    }

    private void setupExitButton(Skin skin, Table table) {
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isLevelUp = 0;
                first = true;
                Main.getMain().setScreen(new PreGameView(
                    new PreGameMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
            }
        });
        table.add(exitButton).pad(10);
    }

    private void createRealPauseMenu(Skin skin) {
        Main.setCustomCursor("m2.png");
        realPauseMenuStage = new Stage(new ScreenViewport());
        Table pauseTable = new Table();

        if (App.loggedInUser.isPlaySFX())
            levelupSound.play();

        addPauseBackground();
        pauseTable.setFillParent(true);

        CheckBox classic = createClassicCheckBox(skin);
        TextButton backButton = createBackButton(skin);
        TextButton exitButton = createExitButton(skin);
        TextButton saveButton = createSaveButton(skin);

        addPauseButtonListeners(backButton, exitButton, saveButton);
        addClassicToggleListener(classic);

        stylePauseButtons(backButton, exitButton, saveButton, classic);

        realPauseMenuStage.addActor(backButton);
        realPauseMenuStage.addActor(exitButton);
        realPauseMenuStage.addActor(saveButton);
        realPauseMenuStage.addActor(classic);

        addAbilityLabelsToPauseTable(pauseTable, skin);

        realPauseMenuStage.addActor(pauseTable);
    }

    private void addPauseBackground() {
        Texture backgroundTexture = new Texture("pause.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        realPauseMenuStage.addActor(backgroundImage);
    }

    private CheckBox createClassicCheckBox(Skin skin) {
        if (StartView.getLanguge() == 1)
            return new CheckBox("Classic?", skin);
        else
            return new CheckBox("Classique?", skin);
    }

    private TextButton createExitButton(Skin skin) {
        if (StartView.getLanguge() == 1)
            return new TextButton("Exit", skin);
        else
            return new TextButton("Quitter", skin);
    }

    private TextButton createBackButton(Skin skin) {
        if (StartView.getLanguge() == 1)
            return new TextButton("Back", skin);
        else
            return new TextButton("Retour", skin);
    }

    private TextButton createSaveButton(Skin skin) {
        if (StartView.getLanguge() == 1)
            return new TextButton("Save", skin);
        else
            return new TextButton("Sauvegarder", skin);
    }

    private void addPauseButtonListeners(TextButton back, TextButton exit, TextButton save) {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isRealPause = false;
                realPauseMenuStage.clear();
                InputMultiplexer multiplexer = new InputMultiplexer();
                multiplexer.addProcessor(stage);
                multiplexer.addProcessor(GameView.this);
                Gdx.input.setInputProcessor(multiplexer);
            }
        });

        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isRealPause = false;
                realPauseMenuStage.clear();
                InputMultiplexer multiplexer = new InputMultiplexer();
                multiplexer.addProcessor(stage);
                multiplexer.addProcessor(GameView.this);
                Gdx.input.setInputProcessor(multiplexer);

                App.loggedInUser.setTime((int) timeBar.getValue());
                App.loggedInUser.setScore(App.loggedInUser.getKill() * ((int) finalElapsedTime));
                controller.saveGame();

                Main.getMain().setScreen(new PreGameView(
                    new PreGameMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isLevelUp = 0;
                first = true;
                isRealPause = false;
                App.loggedInUser.setHealth(0);
            }
        });
    }

    private void addClassicToggleListener(CheckBox classic) {
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
    }

    private void stylePauseButtons(TextButton back, TextButton exit, TextButton save, CheckBox classic) {
        back.setSize(300, 100);
        back.setPosition(1300, 160);
        back.setColor(Color.YELLOW);

        exit.setSize(300, 100);
        exit.setPosition(1300, 50);
        exit.setColor(Color.YELLOW);

        save.setSize(300, 100);
        save.setPosition(1300, 270);
        save.setColor(Color.YELLOW);

        classic.setSize(300, 100);
        classic.setPosition(100, 300);
        classic.setColor(Color.PINK);
    }

    private void addAbilityLabelsToPauseTable(Table table, Skin skin) {
        table.setFillParent(true);
        table.top().right().padRight(100).padTop(100);
        for (String ability : abilities) {
            Label label = new Label(ability, skin);
            table.add(label).right();
            label.setColor(Color.NAVY);
            label.setFontScale(1.7f);
            table.row();
        }
    }

    private void createLoserMenu(Skin skin) {
        Main.setCustomCursor("m2.png");
        loseStage = new Stage(new ScreenViewport());
        Table pauseTable = new Table();

        if (App.loggedInUser.isPlaySFX())
            loseSound.play();

        addLoserBackground();
        pauseTable.setFillParent(true);

        TextButton exitButton = createExitButtonLoser(skin);
        addExitListener(exitButton);
        styleExitButton(exitButton);
        loseStage.addActor(exitButton);

        addUserLabels(skin);

        loseStage.addActor(pauseTable);
    }

    private void addLoserBackground() {
        Texture backgroundTexture = new Texture("loser.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        loseStage.addActor(backgroundImage);
    }

    private TextButton createExitButtonLoser(Skin skin) {
        return new TextButton("Exit", skin);
    }

    private void addExitListener(TextButton exitButton) {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(
                    new PreGameView(
                        new PreGameMenuController(),
                        GameAssetManager.getGameAssetManager().getSkin()
                    )
                );
            }
        });
    }

    private void styleExitButton(TextButton exitButton) {
        exitButton.setSize(400, 100);
        exitButton.setPosition(1000, 100);
        exitButton.setColor(Color.FIREBRICK);
    }

    private void addUserLabels(Skin skin) {
        Label usernameLabel = new Label(App.loggedInUser.getUsername(), skin);
        usernameLabel.setSize(300, 100);
        usernameLabel.setFontScale(1.7f);
        usernameLabel.setPosition(100, 400);
        usernameLabel.setColor(Color.NAVY);
        loseStage.addActor(usernameLabel);

        App.loggedInUser.setTime((int) finalElapsedTime);
        UserRepo.saveUser(App.loggedInUser);
        Label timeLabel = new Label("Time: " + String.format("%.1f", finalElapsedTime) + "s", skin);
        timeLabel.setFontScale(1.7f);
        timeLabel.setColor(Color.BLACK);
        timeLabel.setSize(300, 100);
        timeLabel.setPosition(100, 350);
        loseStage.addActor(timeLabel);

        Label killLabel = new Label("Kill: " + App.loggedInUser.getKill(), skin);
        killLabel.setFontScale(1.7f);
        killLabel.setColor(Color.SKY);
        killLabel.setSize(300, 100);
        killLabel.setPosition(100, 300);
        loseStage.addActor(killLabel);

        App.loggedInUser.setScore(App.loggedInUser.getKill() * ((int) (finalElapsedTime)));
        UserRepo.saveUser(App.loggedInUser);
        Label scoreLabel = new Label("Score: " + App.loggedInUser.getScore(), skin);
        scoreLabel.setFontScale(1.7f);
        scoreLabel.setColor(Color.VIOLET);
        scoreLabel.setSize(300, 100);
        scoreLabel.setPosition(100, 250);
        loseStage.addActor(scoreLabel);
    }

    private void createWinnerMenu(Skin skin) {
        Main.setCustomCursor("m2.png");
        winStage = new Stage(new ScreenViewport());
        Table pauseTable = new Table();
        winSound.play();

        addWinnerBackground();
        pauseTable.setFillParent(true);

        TextButton exitButton = createExitButtonWinner(skin);
        addExitListenerWinner(exitButton);
        styleExitButtonWinner(exitButton);
        winStage.addActor(exitButton);

        addWinnerLabels(skin);

        winStage.addActor(pauseTable);
    }

    private void addWinnerBackground() {
        Texture backgroundTexture = new Texture("winner.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        winStage.addActor(backgroundImage);
    }

    private TextButton createExitButtonWinner(Skin skin) {
        return new TextButton("Exit", skin);
    }

    private void addExitListenerWinner(TextButton exitButton) {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(
                    new PreGameView(
                        new PreGameMenuController(),
                        GameAssetManager.getGameAssetManager().getSkin()
                    )
                );
            }
        });
    }

    private void styleExitButtonWinner(TextButton exitButton) {
        exitButton.setSize(400, 100);
        exitButton.setPosition(1000, 100);
        exitButton.setColor(Color.FOREST);
    }

    private void addWinnerLabels(Skin skin) {
        Label usernameLabel = new Label(App.loggedInUser.getUsername(), skin);
        usernameLabel.setSize(300, 100);
        usernameLabel.setFontScale(1.7f);
        usernameLabel.setPosition(100, 400);
        usernameLabel.setColor(Color.WHITE);
        winStage.addActor(usernameLabel);

        App.loggedInUser.setTime((int) finalElapsedTime);
        UserRepo.saveUser(App.loggedInUser);
        Label timeLabel = new Label("Time: " + String.format("%.1f", finalElapsedTime) + "s", skin);
        timeLabel.setFontScale(1.7f);
        timeLabel.setColor(Color.TEAL);
        timeLabel.setSize(300, 100);
        timeLabel.setPosition(100, 350);
        winStage.addActor(timeLabel);

        Label killLabel = new Label("Kill: " + App.loggedInUser.getKill(), skin);
        killLabel.setFontScale(1.7f);
        killLabel.setColor(Color.RED);
        killLabel.setSize(300, 100);
        killLabel.setPosition(100, 300);
        winStage.addActor(killLabel);

        App.loggedInUser.setScore(App.loggedInUser.getKill() * ((int) (finalElapsedTime)));
        UserRepo.saveUser(App.loggedInUser);
        Label scoreLabel = new Label("Score: " + App.loggedInUser.getScore(), skin);
        scoreLabel.setFontScale(1.7f);
        scoreLabel.setColor(Color.GOLD);
        scoreLabel.setSize(300, 100);
        scoreLabel.setPosition(100, 250);
        winStage.addActor(scoreLabel);
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

    @Override
    public boolean keyDown(int keycode) {
        if (handleEscapeKey(keycode)) return true;
        if (handleCommaKey(keycode)) return true;
        if (handleF2Key(keycode)) return true;
        if (handleSpaceKey()) return true;
        if (handleF1Key(keycode)) return true;
        if (handleReloadKeys(keycode)) return true;
        if (handleBKey(keycode)) return true;

        return false;
    }

    private boolean handleEscapeKey(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            if (!isPaused) {
                isPaused = true;
                App.loggedInUser.setLevel(App.loggedInUser.getLevel() + 1);
                createPauseMenu(GameAssetManager.getGameAssetManager().getSkin());
                Gdx.input.setInputProcessor(pauseStage);
            } else {
                isPaused = false;
                Gdx.input.setInputProcessor(stage);
            }
            return true;
        }
        return false;
    }

    private boolean handleCommaKey(int keycode) {
        if (keycode == Input.Keys.COMMA) {
            reduceTime(60);
            return true;
        }
        return false;
    }

    private boolean handleF2Key(int keycode) {
        if (keycode == Input.Keys.F2) {
            App.loggedInUser.setKill(App.loggedInUser.getKill() + 5);
            return true;
        }
        return false;
    }

    private boolean handleSpaceKey() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            controller.setAutoAimEnabled(!controller.isAutoAimEnabled());
            return true;
        }
        return false;
    }

    private boolean handleF1Key(int keycode) {
        if (keycode == Input.Keys.F1) {
            if (!isRealPause) {
                isRealPause = true;
                createRealPauseMenu(GameAssetManager.getGameAssetManager().getSkin());
                Gdx.input.setInputProcessor(realPauseMenuStage);
            } else {
                isRealPause = false;
                Gdx.input.setInputProcessor(stage);
            }
            return true;
        }
        return false;
    }

    private boolean handleReloadKeys(int keycode) {
        if (keycode == Input.Keys.R || keycode == Input.Keys.T) {
            App.loggedInUser.setReloadR(true);
            return true;
        }
        return false;
    }

    private boolean handleBKey(int keycode) {
        if (keycode == Input.Keys.B) {
            setSoonBasFight(true);
            return true;
        }
        return false;
    }

    public void reduceTime(int seconds) {
        this.duration -= seconds;
        if (duration < 0) duration = 0;

        timeBar.setValue(duration - elapsedTime);

    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT && !App.mouseLeft) {
            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
            return true;
        } else if (button == Input.Buttons.LEFT && App.mouseLeft) {
            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public static void setIsLevelUp(int isLevelUp) {
        GameView.isLevelUp = isLevelUp;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Vector2 worldToScreen(Vector2 worldPos) {
        Vector3 projected = new Vector3(worldPos.x, worldPos.y, 0);
        camera.project(projected);
        return new Vector2(projected.x, Gdx.graphics.getHeight() - projected.y);
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getMaxXp() {
        return maxXp;
    }

    public void setMaxXp(int maxXp) {
        this.maxXp = maxXp;
    }

    public boolean isSoonBasFight() {
        return soonBasFight;
    }

    public void setSoonBasFight(boolean soonBasFight) {
        this.soonBasFight = soonBasFight;
    }
}
