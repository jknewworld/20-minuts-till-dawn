package com.P3.View;

import com.P3.Control.SignupController;
import com.P3.Control.StartController;
import com.P3.Main;
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

public class SignupView implements Screen {
    // Register
    private Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextField name;
    private TextField password;
    private Label sQustion;
    private BitmapFont customFont;
    private TextField answer;
    // Button
    private TextButton registerButton;
    private TextButton loginButton;
    private TextButton ghostButtom;
    private TextButton backButton;
    // Message
    private Label messageLabel;
    // Login
    private TextField loginUsername;
    private TextField loginPassword;
    private TextButton confirmLogin;
    private TextButton backToSignup;
    // Forget Pass
    private TextButton forgetPasswordButton;
    private TextField forgetPasswordField;
    private TextButton answerButton;
    private TextField newPass;

    public Table table;

    private SignupController controller;

    public SignupView(SignupController controller, Skin skin) {
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("signupbackground.png"));
        this.backgroundImage = new Image(backgroundTexture);
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/ChevyRay - Express.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 32;
//        BitmapFont bigFont = generator.generateFont(parameter);
//        generator.dispose();
//        Label.LabelStyle style = new Label.LabelStyle();
//        style.font = bigFont;
//        style.fontColor = Color.LIME;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/IMFePIit28P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        BitmapFont bigFont = generator.generateFont(parameter);
        generator.dispose();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = bigFont;
        style.fontColor = Color.FOREST;
        style.overFontColor = Color.GREEN;
        style.downFontColor = Color.GREEN;
        Label.LabelStyle styleLabel = new Label.LabelStyle();
        styleLabel.font = bigFont;
        styleLabel.fontColor = Color.LIME;
        if(StartView.getLanguge() == 1) {
            this.name = new TextField("What's your name?", skin);
            this.password = new TextField("Create your password!", skin);
            this.sQustion = new Label("How old are you?", skin);
            sQustion.setColor(Color.LIME);
            this.answer = new TextField("Answer?", skin);
            this.registerButton = new TextButton("Register", style);
            this.loginButton = new TextButton("Login", style);
            this.ghostButtom = new TextButton("Guest", style);
            this.messageLabel = new Label("", skin);
            messageLabel.setColor(Color.GREEN);
            this.backButton = new TextButton("Back", style);
        } else if (StartView.getLanguge() == 2) {
            this.name = new TextField("Comment tu t'appelles ?", skin);
            this.password = new TextField("Crée ton mot de passe!", skin);
            this.sQustion = new Label("Quel âge as-tu?", styleLabel);
            sQustion.setColor(Color.LIME);
            this.answer = new TextField("Réponse?", skin);
            this.registerButton = new TextButton("S'inscrire", skin);
            this.loginButton = new TextButton("Connexion", skin);
            this.ghostButtom = new TextButton("Invité", skin);
            this.messageLabel = new Label("", skin);
            messageLabel.setColor(Color.GREEN);
            this.backButton = new TextButton("Retour", skin);
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
        table.center();
        table.row().pad(10, 0, 10, 0);
        table.setPosition(0, -50);
        table.add(name).width(500).height(80);

        table.row().pad(10, 0, 10, 0);
        table.add(password).width(500).height(80);

        table.row().pad(0, 0, 0, 0);
        table.add(sQustion).width(500).height(50);

        table.row().pad(10, 0, 10, 0);
        table.add(answer).width(500).height(80);

        table.row().pad(10, 0, 0, 0);
        table.add(messageLabel).width(500).height(50);

        table.row().pad(0, 0, 0, 0);
        table.add(registerButton).width(500).height(70);

        table.row().pad(10, 0, 10, 0);
        Table buttonTable = new Table();
        buttonTable.add(loginButton).width(250).height(70).padRight(5);
        buttonTable.add(ghostButtom).width(250).height(70).padLeft(5);
        table.add(buttonTable).width(500);

        table.row().pad(10, 0, 10, 0);
        table.add(backButton).width(500).height(100);

        stage.addActor(table);

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadLoginForm();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new StartView(new StartController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                  controller.handelRegister();
            }
        });

        ghostButtom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGhost();
            }
        });

    }

    public void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setColor(color);
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

    private void loadLoginForm() {
        table.clear();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/IMFePIit28P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        BitmapFont bigFont = generator.generateFont(parameter);
        generator.dispose();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = bigFont;
        style.fontColor = Color.FOREST;
        style.overFontColor = Color.GREEN;
        style.downFontColor = Color.GREEN;

        loginUsername = new TextField("Username", GameAssetManager.getGameAssetManager().getSkin());
        loginPassword = new TextField("Password", GameAssetManager.getGameAssetManager().getSkin());
        forgetPasswordField = new TextField("How old are you?", GameAssetManager.getGameAssetManager().getSkin());
        newPass = new TextField("New PASS", GameAssetManager.getGameAssetManager().getSkin());

        confirmLogin = new TextButton("Confirm Login", style);
        backToSignup = new TextButton("Back", style);
        forgetPasswordButton = new TextButton("Forget", style);
        answerButton = new TextButton("My Password", style);


        loginUsername.setColor(Color.WHITE);
        loginPassword.setColor(Color.WHITE);

        table.row().pad(10);
        table.add(loginUsername).width(500).height(80);

        table.row().pad(10);
        table.add(loginPassword).width(500).height(80);

        table.row().pad(10, 0, 10, 0);
        table.add(messageLabel).width(500).height(10);

        table.row().pad(20);
        table.add(confirmLogin).width(500).height(100);

        table.row().pad(10);
        Table buttonTable = new Table();
        buttonTable.add(backToSignup).width(250).height(100).padRight(5);
        buttonTable.add(forgetPasswordButton).width(250).height(100).padLeft(5);
        table.add(buttonTable).width(500);


        backToSignup.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                showMessage("", Color.GREEN);
                show();
            }
        });

        confirmLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLogin();
            }
        });

        forgetPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.row().pad(10);
                table.add(forgetPasswordField).width(500).height(80);
                table.row().pad(10);
                table.add(newPass).width(500).height(80);
                table.row().pad(10);
                table.add(answerButton).width(500).height(100);
            }
        });

        answerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleForgetPassword();
            }
        });
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

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getPassword() {
        return password;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }

    public Label getsQustion() {
        return sQustion;
    }

    public void setsQustion(Label sQustion) {
        this.sQustion = sQustion;
    }

    public BitmapFont getCustomFont() {
        return customFont;
    }

    public void setCustomFont(BitmapFont customFont) {
        this.customFont = customFont;
    }

    public TextField getAnswer() {
        return answer;
    }

    public void setAnswer(TextField answer) {
        this.answer = answer;
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(TextButton registerButton) {
        this.registerButton = registerButton;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(TextButton loginButton) {
        this.loginButton = loginButton;
    }

    public TextButton getGhostButtom() {
        return ghostButtom;
    }

    public void setGhostButtom(TextButton ghostButtom) {
        this.ghostButtom = ghostButtom;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public SignupController getController() {
        return controller;
    }

    public void setController(SignupController controller) {
        this.controller = controller;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setBackButton(TextButton backButton) {
        this.backButton = backButton;
    }

    public TextField getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(TextField loginUsername) {
        this.loginUsername = loginUsername;
    }

    public TextField getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(TextField loginPassword) {
        this.loginPassword = loginPassword;
    }

    public TextButton getConfirmLogin() {
        return confirmLogin;
    }

    public void setConfirmLogin(TextButton confirmLogin) {
        this.confirmLogin = confirmLogin;
    }

    public TextButton getBackToSignup() {
        return backToSignup;
    }

    public void setBackToSignup(TextButton backToSignup) {
        this.backToSignup = backToSignup;
    }

    public TextButton getForgetPasswordButton() {
        return forgetPasswordButton;
    }

    public void setForgetPasswordButton(TextButton forgetPasswordButton) {
        this.forgetPasswordButton = forgetPasswordButton;
    }

    public TextField getForgetPasswordField() {
        return forgetPasswordField;
    }

    public void setForgetPasswordField(TextField forgetPasswordField) {
        this.forgetPasswordField = forgetPasswordField;
    }

    public TextButton getAnswerButton() {
        return answerButton;
    }

    public void setAnswerButton(TextButton answerButton) {
        this.answerButton = answerButton;
    }

    public TextField getNewPass() {
        return newPass;
    }

    public void setNewPass(TextField newPass) {
        this.newPass = newPass;
    }
}
