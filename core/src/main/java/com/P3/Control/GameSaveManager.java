package com.P3.Control;

import com.P3.Model.App;
import com.P3.Model.Save.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

public class GameSaveManager {
    private static final String SAVE_FILE = "savegame.json";

    public static void saveGame(GameState state) {
        Json json = new Json();
        String saveData = json.prettyPrint(state); // می‌تونی از json.toJson(save) هم استفاده کنی
        FileHandle file = Gdx.files.local("savegame.json");
        file.writeString(saveData, false); // false = overwrite
    }


}
