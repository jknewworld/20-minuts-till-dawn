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
            skin.get("default-vertical", ProgressBar.ProgressBarStyle.class));
        xpBar.setValue(xp);
        xpBar.setAnimateDuration(0.1f);
        xpBar.setWidth(400);
        xpBar.setHeight(150);

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
        xpBar.setSize(200, 300);
        xpBar.setPosition(30, 500);
        xpBar.setColor(Color.SLATE);
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

        if (shouldRenderNormalGame()) {
            renderNormalGame(delta);
        }

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
        }

        if (isLose) {
            renderStage(loseStage, frameTime);
            return;
        }

        if (isWin) {
            renderStage(winStage, frameTime);
            return;
        }

        if (isRealPause) {
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
        Main.getBatch().draw(lightMaskTexture, centerX - radius, centerY - radius + 50, radius * 2, radius * 2);

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

        Texture backgroundTexture = new Texture("levelup.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pauseStage.addActor(backgroundImage);
        pauseTable.setFillParent(true);

        Random rand = new Random();
        int num1 = rand.nextInt(5);
        if (num1 == 0) {
            this.ability1 = new TextButton("VITALITY", skin);
        } else if (num1 == 1) {
            this.ability1 = new TextButton("DAMAGER", skin);
        } else if (num1 == 2) {
            this.ability1 = new TextButton("PROCREASE", skin);
        } else if (num1 == 3) {
            this.ability1 = new TextButton("AMOGREASE", skin);
        } else if (num1 == 4) {
            this.ability1 = new TextButton("SPEEDY", skin);
        }

        int num2 = rand.nextInt(5);
        while (num1 == num2) {
            num2 = rand.nextInt(5);
        }
        if (num2 == 0) {
            this.ability2 = new TextButton("VITALITY", skin);
        } else if (num2 == 1) {
            this.ability2 = new TextButton("DAMAGER", skin);
        } else if (num2 == 2) {
            this.ability2 = new TextButton("PROCREASE", skin);
        } else if (num2 == 3) {
            this.ability2 = new TextButton("AMOGREASE", skin);
        } else if (num2 == 4) {
            this.ability2 = new TextButton("SPEEDY", skin);
        }

        int num3 = rand.nextInt(5);
        while (num1 == num3 || num2 == num3) {
            num3 = rand.nextInt(5);
        }
        if (num3 == 0) {
            this.ability3 = new TextButton("VITALITY", skin);
        } else if (num3 == 1) {
            this.ability3 = new TextButton("DAMAGER", skin);
        } else if (num3 == 2) {
            this.ability3 = new TextButton("PROCREASE", skin);
        } else if (num3 == 3) {
            this.ability3 = new TextButton("AMOGREASE", skin);
        } else if (num3 == 4) {
            this.ability3 = new TextButton("SPEEDY", skin);
        }


        TextButton exitButton = new TextButton("Exit", skin);

        ability1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.loggedInUser.setAbility(num1);
                switch (num1) {
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
                pauseStage.clear();
                InputMultiplexer multiplexer = new InputMultiplexer();
                multiplexer.addProcessor(stage);
                multiplexer.addProcessor(GameView.this);
                Gdx.input.setInputProcessor(multiplexer);

            }
        });

        int finalNum = num2;
        ability2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.loggedInUser.setAbility(finalNum);
                switch (finalNum) {
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
                isLevelUp = 0;
                first = true;
                UserRepo.saveUser(App.loggedInUser);
                isPaused = false;
                pauseStage.clear();
                InputMultiplexer multiplexer = new InputMultiplexer();
                multiplexer.addProcessor(stage);
                multiplexer.addProcessor(GameView.this);
                Gdx.input.setInputProcessor(multiplexer);

            }
        });

        int finalNum1 = num3;
        ability3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.loggedInUser.setAbility(finalNum1);
                switch (finalNum1) {
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
                isLevelUp = 0;
                UserRepo.saveUser(App.loggedInUser);
                first = true;
                isPaused = false;
                pauseStage.clear();
                InputMultiplexer multiplexer = new InputMultiplexer();
                multiplexer.addProcessor(stage);
                multiplexer.addProcessor(GameView.this);
                Gdx.input.setInputProcessor(multiplexer);

            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isLevelUp = 0;
                first = true;
                Main.getMain().setScreen(new PreGameView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        pauseTable.add(ability1).pad(10);
        pauseTable.row();
        pauseTable.add(ability2).pad(10);
        pauseTable.row();
        pauseTable.add(ability3).pad(10);
        pauseTable.row();
        pauseTable.add(exitButton).pad(10);

        pauseStage.addActor(pauseTable);
    }

    private void createRealPauseMenu(Skin skin) {
        Main.setCustomCursor("m2.png");
        realPauseMenuStage = new Stage(new ScreenViewport());
        Table pauseTable = new Table();
        if (App.loggedInUser.isPlaySFX())
            levelupSound.play();

        CheckBox classic;
        if (StartView.getLanguge() == 1)
            classic = new CheckBox("Classic?", skin);
        else
            classic = new CheckBox("Classique?", skin);

        Texture backgroundTexture = new Texture("pause.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        realPauseMenuStage.addActor(backgroundImage);
        pauseTable.setFillParent(true);

        TextButton exitButton;
        if (StartView.getLanguge() == 1)
            exitButton = new TextButton("Exit", skin);
        else
            exitButton = new TextButton("Quitter", skin);

        TextButton backButton;
        if (StartView.getLanguge() == 1)
            backButton = new TextButton("Back", skin);
        else
            backButton = new TextButton("Retour", skin);

        TextButton saveButton;
        if (StartView.getLanguge() == 1)
            saveButton = new TextButton("Save", skin);
        else
            saveButton = new TextButton("Sauvegarder", skin);


        backButton.addListener(new ClickListener() {
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

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isRealPause = false;
                realPauseMenuStage.clear();
                InputMultiplexer multiplexer = new InputMultiplexer();
                multiplexer.addProcessor(stage);
                multiplexer.addProcessor(GameView.this);
                Gdx.input.setInputProcessor(multiplexer);

                App.loggedInUser.setTime((int) timeBar.getValue());
                App.loggedInUser.setScore(App.loggedInUser.getKill() * ((int) (finalElapsedTime)));

                controller.saveGame();

                Main.getMain().setScreen(new PreGameView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });


        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isLevelUp = 0;
                first = true;
                isRealPause = false;
                App.loggedInUser.setHealth(0);
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


        backButton.setSize(300, 100);
        backButton.setPosition(1300, 160);
        backButton.setColor(Color.YELLOW);

        exitButton.setSize(300, 100);
        exitButton.setPosition(1300, 50);
        exitButton.setColor(Color.YELLOW);

        saveButton.setSize(300, 100);
        saveButton.setPosition(1300, 270);
        saveButton.setColor(Color.YELLOW);

        classic.setSize(300, 100);
        classic.setPosition(100, 300);
        classic.setColor(Color.PINK);

        realPauseMenuStage.addActor(backButton);
        realPauseMenuStage.addActor(exitButton);
        realPauseMenuStage.addActor(classic);
        realPauseMenuStage.addActor(saveButton);

        pauseTable.setFillParent(true);
        pauseTable.top().right().padRight(100).padTop(100);

        for (String ability : abilities) {
            Label label = new Label(ability, skin);
            pauseTable.add(label).right();
            label.setColor(Color.NAVY);
            label.setFontScale(1.7f);
            pauseTable.row();
        }

        realPauseMenuStage.addActor(pauseTable);
    }

    private void createLoserMenu(Skin skin) {
        Main.setCustomCursor("m2.png");
        loseStage = new Stage(new ScreenViewport());
        Table pauseTable = new Table();
        if (App.loggedInUser.isPlaySFX())
            loseSound.play();

        Texture backgroundTexture = new Texture("loser.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        loseStage.addActor(backgroundImage);
        pauseTable.setFillParent(true);


        TextButton exitButton = new TextButton("Exit", skin);


        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new PreGameView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });


        //pauseTable.add(exitButton).pad(10);
        exitButton.setSize(400, 100);
        exitButton.setPosition(1000, 100);
        exitButton.setColor(Color.FIREBRICK);

        Label usernameLabel = new Label(App.loggedInUser.getUsername(), skin);
        usernameLabel.setSize(300, 100);
        usernameLabel.setFontScale(1.7f);
        usernameLabel.setPosition(100, 400);
        usernameLabel.setColor(Color.NAVY);

        App.loggedInUser.setTime((int) finalElapsedTime);
        UserRepo.saveUser(App.loggedInUser);
        Label timeLabel = new Label("Time: " + String.format("%.1f", finalElapsedTime) + "s", skin);
        timeLabel.setFontScale(1.7f);
        timeLabel.setColor(Color.BLACK);
        timeLabel.setSize(300, 100);
        timeLabel.setPosition(100, 350);

        UserRepo.saveUser(App.loggedInUser);
        Label killLabel = new Label("Kill: " + App.loggedInUser.getKill(), skin);
        killLabel.setFontScale(1.7f);
        killLabel.setColor(Color.SKY);
        killLabel.setSize(300, 100);
        killLabel.setPosition(100, 300);

        App.loggedInUser.setScore(App.loggedInUser.getKill() * ((int) (finalElapsedTime)));
        UserRepo.saveUser(App.loggedInUser);
        Label scoreLabel = new Label("Score: " + App.loggedInUser.getScore(), skin);
        scoreLabel.setFontScale(1.7f);
        scoreLabel.setColor(Color.VIOLET);
        scoreLabel.setSize(300, 100);
        scoreLabel.setPosition(100, 250);


        loseStage.addActor(usernameLabel);
        loseStage.addActor(exitButton);
        loseStage.addActor(timeLabel);
        loseStage.addActor(killLabel);
        loseStage.addActor(scoreLabel);


    }

    private void createWinnerMenu(Skin skin) {
        Main.setCustomCursor("m2.png");
        winStage = new Stage(new ScreenViewport());
        Table pauseTable = new Table();
        winSound.play();
        Texture backgroundTexture = new Texture("winner.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        winStage.addActor(backgroundImage);
        pauseTable.setFillParent(true);


        TextButton exitButton = new TextButton("Exit", skin);


        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new PreGameView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });


        //pauseTable.add(exitButton).pad(10);
        exitButton.setSize(400, 100);
        exitButton.setPosition(1000, 100);
        exitButton.setColor(Color.FOREST);
        winStage.addActor(exitButton);

        Label usernameLabel = new Label(App.loggedInUser.getUsername(), skin);
        usernameLabel.setSize(300, 100);
        usernameLabel.setFontScale(1.7f);
        usernameLabel.setPosition(100, 400);
        usernameLabel.setColor(Color.WHITE);

        App.loggedInUser.setTime((int) finalElapsedTime);
        UserRepo.saveUser(App.loggedInUser);
        Label timeLabel = new Label("Time: " + String.format("%.1f", finalElapsedTime) + "s", skin);
        timeLabel.setFontScale(1.7f);
        timeLabel.setColor(Color.TEAL);
        timeLabel.setSize(300, 100);
        timeLabel.setPosition(100, 350);

        Label killLabel = new Label("Kill: " + App.loggedInUser.getKill(), skin);
        killLabel.setFontScale(1.7f);
        killLabel.setColor(Color.RED);
        killLabel.setSize(300, 100);
        killLabel.setPosition(100, 300);

        App.loggedInUser.setScore(App.loggedInUser.getKill() * ((int) (finalElapsedTime)));
        UserRepo.saveUser(App.loggedInUser);
        Label scoreLabel = new Label("Score: " + App.loggedInUser.getScore(), skin);
        scoreLabel.setFontScale(1.7f);
        scoreLabel.setColor(Color.GOLD);
        scoreLabel.setSize(300, 100);
        scoreLabel.setPosition(100, 250);

        winStage.addActor(usernameLabel);
        winStage.addActor(exitButton);
        winStage.addActor(timeLabel);
        winStage.addActor(killLabel);
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
        if (keycode == Input.Keys.COMMA) {
            reduceTime(60);
            return true;
        }
        if (keycode == Input.Keys.F2) {
            App.loggedInUser.setKill(App.loggedInUser.getKill() + 5);
            return true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            controller.setAutoAimEnabled(!controller.isAutoAimEnabled());
        }
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

        if (keycode == Input.Keys.R || keycode == Input.Keys.T) {
            App.loggedInUser.setReloadR(true);
        }
        if(keycode == Input.Keys.B) {
            setSoonBasFight(true);
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
