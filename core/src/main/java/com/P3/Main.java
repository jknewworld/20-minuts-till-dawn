package com.P3;

import com.P3.Control.StartController;
import com.P3.Model.App;
import com.P3.View.ProfileView;
import com.P3.View.StartView;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ScreenUtils;
import com.P3.Control.MainMenuController;
import com.P3.Model.GameAssetManager;
import com.P3.View.MainMenuView;

import java.awt.*;
import java.nio.file.Path;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    public static ShaderProgram grayscaleShader;
    public static boolean grayscaleEnabled = false;
    private static String currentCursorPath = "";


    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        ShaderProgram.pedantic = false;
        setCustomCursor("m.png");


        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/grayscale.vertex.glsl"),
            Gdx.files.internal("shaders/grayscale.fragment.glsl")
        );

        if (!grayscaleShader.isCompiled()) {
            System.err.println("Shader compile error: " + grayscaleShader.getLog());
            grayscaleShader = null;
        }
        getMain().setScreen(new StartView(new StartController(), GameAssetManager.getGameAssetManager().getSkin()));

    }




    public static void setCustomCursor(String imagePath) {
        if (imagePath.equals(currentCursorPath)) return;

        Pixmap pixmap = new Pixmap(Gdx.files.internal(imagePath));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();
        currentCursorPath = imagePath;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }
}
