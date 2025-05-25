package com.P3.View;

import com.P3.Control.MainMenuController;
import com.P3.Control.StartController;
import com.P3.Main;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.P3.Model.Repo.UserRepo;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ProfileView implements Screen {
    private Texture backgroundTexture;
    private Texture avtarPic1;
    private Image avatarImage1;
    private Texture avtarPic2;
    private Image avatarImage2;
    private Image backgroundImage;
    private TextButton changUsername;
    private TextButton changePassword;
    private TextButton changeAvatar;
    private TextButton backButton;
    private TextField newName;
    private TextButton change1;
    private TextField newPassword;
    private TextButton remove;
    private Stage stage;
    private Label message;
    private TextButton chooseFileButton;
    private SelectBox<String> selectBox;
    Texture droppedTexture = null;
    Image droppedImage = null;



    public Table table;

    private MainMenuController controller;

    public ProfileView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("profilemenu.png"));
        this.backgroundImage = new Image(backgroundTexture);
        this.avtarPic1 = new Texture(Gdx.files.internal("Avatars/3.png"));
        this.avatarImage1 = new Image(avtarPic1);
        this.avtarPic2 = new Texture(Gdx.files.internal("Avatars/8.png"));
        this.avatarImage2 = new Image(avtarPic2);
        if(StartView.getLanguge() == 1) {
            this.changUsername = new TextButton("Change Username", skin);
            this.changePassword = new TextButton("Change Password", skin);
            this.remove = new TextButton("Remove UR Account", skin);
            this.newName = new TextField("Take Me UR NEW NAME", skin);
            this.newPassword = new TextField("Take Me UR NEW PASS", skin);
            this.change1 = new TextButton("Change", skin);
            this.message = new Label("", skin);
            message.setColor(Color.GREEN);
            this.changeAvatar = new TextButton("Change Avatar", skin);
            this.selectBox = new SelectBox<>(skin);
            selectBox.setItems("None", "Left", "Right");
            this.backButton = new TextButton("Back", skin);
            this.chooseFileButton = new TextButton("Choose File", skin);
        } else if(StartView.getLanguge() == 2) {
            this.changUsername = new TextButton("Changer le nom d'utilisateur", skin);
            this.changePassword = new TextButton("Changer le mot de passe", skin);
            this.remove = new TextButton("Supprimer ton compte", skin);
            this.newName = new TextField("Donne-moi ton NOUVEAU NOM", skin);
            this.newPassword = new TextField("Donne-moi ton NOUVEAU MOT DE PASSE", skin);
            this.change1 = new TextButton("Changer", skin);
            this.message = new Label("", skin);
            message.setColor(Color.GREEN);
            this.changeAvatar = new TextButton("Changer l’avatar", skin);
            this.selectBox = new SelectBox<>(skin);
            selectBox.setItems("Aucun", "Left", "Right");
            this.backButton = new TextButton("Retour", skin);
            this.chooseFileButton = new TextButton("Choisir un fichier", skin);
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
        Table buttonTable1 = new Table();
        buttonTable1.add(changUsername).width(550).height(110).padRight(5);
        buttonTable1.add(changePassword).width(550).height(110).padLeft(5);
        table.add(buttonTable1).width(500);

        table.row().pad(10, 0, 10, 0);
        Table buttonTable2 = new Table();
        buttonTable2.add(remove).width(550).height(110).padRight(5);
        buttonTable2.add(changeAvatar).width(550).height(110).padLeft(5);
        table.add(buttonTable2).width(500);

        table.row().pad(10, 0, 10, 0);
        table.add(backButton).width(550).height(110);

        stage.addActor(table);

        changUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clearChildren();

                table.row().pad(10, 0, 10, 0);
                Table buttonTable1 = new Table();
                buttonTable1.add(changUsername).width(550).height(110).padRight(5);
                buttonTable1.add(changePassword).width(550).height(110).padLeft(5);
                table.add(buttonTable1).width(500);

                table.row().pad(10, 0, 10, 0);
                Table buttonTable2 = new Table();
                buttonTable2.add(remove).width(550).height(110).padRight(5);
                buttonTable2.add(changeAvatar).width(550).height(110).padLeft(5);
                table.add(buttonTable2).width(500);

                table.row().pad(10, 0, 10, 0);
                table.add(backButton).width(550).height(110);

                table.row().pad(10);
                table.add(newName).width(550).height(80);
                table.row().pad(10);
                table.add(change1).width(550).height(110);

                change1.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        controller.handleChangUserName();
                        message.setPosition(700, 75);
                        stage.addActor(message);
                    }
                });
            }
        });

        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clearChildren();

                table.row().pad(10, 0, 10, 0);
                Table buttonTable1 = new Table();
                buttonTable1.add(changUsername).width(550).height(110).padRight(5);
                buttonTable1.add(changePassword).width(550).height(110).padLeft(5);
                table.add(buttonTable1).width(500);

                table.row().pad(10, 0, 10, 0);
                Table buttonTable2 = new Table();
                buttonTable2.add(remove).width(550).height(110).padRight(5);
                buttonTable2.add(changeAvatar).width(550).height(110).padLeft(5);
                table.add(buttonTable2).width(500);

                table.row().pad(10, 0, 10, 0);
                table.add(backButton).width(550).height(110);

                table.row().pad(10);
                table.add(newPassword).width(550).height(80);
                table.row().pad(10);
                table.add(change1).width(550).height(110);

                change1.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        controller.handleChangPass();
                        message.setPosition(700, 75);
                        stage.addActor(message);
                    }
                });
            }
        });

        changeAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clearChildren();

                table.row().pad(10, 0, 10, 0);
                Table buttonTable1 = new Table();
                buttonTable1.add(changUsername).width(550).height(110).padRight(5);
                buttonTable1.add(changePassword).width(550).height(110).padLeft(5);
                table.add(buttonTable1).width(500);

                table.row().pad(10, 0, 10, 0);
                Table buttonTable2 = new Table();
                buttonTable2.add(remove).width(550).height(110).padRight(5);
                buttonTable2.add(changeAvatar).width(550).height(110).padLeft(5);
                table.add(buttonTable2).width(500);

                table.row().pad(10, 0, 10, 0);
                table.add(backButton).width(550).height(110);

                table.row().pad(10, 0, 10, 0);
                table.add(selectBox).width(500).height(80);

                table.row().pad(10, 0, 10, 0);
                table.add(chooseFileButton).width(500).height(100);

                avatarImage1.setSize(192, 261);
                avatarImage1.setPosition(500, 100);
                stage.addActor(avatarImage1);

                avatarImage2.setSize(192, 261);
                avatarImage2.setPosition(1250, 100);
                stage.addActor(avatarImage2);


                chooseFileButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                            try {
                                Class<?> chooserClass = Class.forName("com.P3.Model.DesktopFileChooser");
                                String path = (String) chooserClass.getMethod("chooseFilePath").invoke(null);

                                if (path != null) {
                                    App.loggedInUser.setAvatar(20);
                                    App.loggedInUser.setAvatarPath(path);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                selectBox.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String selected = selectBox.getSelected();
                        controller.handleAvatar(selected);
                    }
                });
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                Main.getMain().setScreen(new StartView(new StartController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });


        remove.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                UserRepo.removeUser(App.loggedInUser);
                App.loggedInUser = null;
                Main.getMain().setScreen(new StartView(new StartController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

    }

    public void showMessage(String messagee, Color color) {
        message.setText(messagee);
        message.setColor(color);
    }

    public void processDraggedImage(String file) {
        App.loggedInUser.setAvatar(20);
       App.loggedInUser.setAvatarPath(file);

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

    public TextButton getChangUsername() {
        return changUsername;
    }

    public void setChangUsername(TextButton changUsername) {
        this.changUsername = changUsername;
    }

    public TextButton getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(TextButton changePassword) {
        this.changePassword = changePassword;
    }

    public TextButton getRemove() {
        return remove;
    }

    public void setRemove(TextButton remove) {
        this.remove = remove;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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

    public TextField getNewName() {
        return newName;
    }

    public void setNewName(TextField newName) {
        this.newName = newName;
    }

    public TextField getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(TextField newPassword) {
        this.newPassword = newPassword;
    }

    public Label getMessage() {
        return message;
    }

    public void setMessage(Label message) {
        this.message = message;
    }

    public TextButton getChangeAvatar() {
        return changeAvatar;
    }

    public void setChangeAvatar(TextButton changeAvatar) {
        this.changeAvatar = changeAvatar;
    }

    public TextButton getChange1() {
        return change1;
    }

    public void setChange1(TextButton change1) {
        this.change1 = change1;
    }

    public Texture getAvtarPic1() {
        return avtarPic1;
    }

    public void setAvtarPic1(Texture avtarPic1) {
        this.avtarPic1 = avtarPic1;
    }

    public Image getAvatarImage1() {
        return avatarImage1;
    }

    public void setAvatarImage1(Image avatarImage1) {
        this.avatarImage1 = avatarImage1;
    }

    public Texture getAvtarPic2() {
        return avtarPic2;
    }

    public void setAvtarPic2(Texture avtarPic2) {
        this.avtarPic2 = avtarPic2;
    }

    public Image getAvatarImage2() {
        return avatarImage2;
    }

    public void setAvatarImage2(Image avatarImage2) {
        this.avatarImage2 = avatarImage2;
    }

    public SelectBox<String> getSelectBox() {
        return selectBox;
    }

    public void setSelectBox(SelectBox<String> selectBox) {
        this.selectBox = selectBox;
    }
}
