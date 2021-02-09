package com.threecubed.auber.save;
//first is the position on NPC, second is the type of them in the position
//then is position and health of player
//[System.Serializable]

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.threecubed.auber.AuberGame;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Player;
import com.badlogic.gdx.utils.Json;
import com.threecubed.auber.ui.GameUi;

import java.util.*;

import static com.threecubed.auber.entities.Player.*;

public class Save {
    public static Save current;

    public List<Float> entityPositionX = new ArrayList<Float>();
    public List<Float> entityPositionY = new ArrayList<Float>();
    public List<Integer> entityType = new ArrayList<Integer>();



    public Save CreatSave(World world){
        Save save = new Save();
        for (GameEntity entity : world.getEntities()) {
            save.entityPositionX.add(entity.getCenterX());
            save.entityPositionY.add(entity.getCenterY());
            save.entityType.add(entity.entityType);
        }
        return save;
    }

    public void SaveJson(World world){

        Json json = new Json();
        Save save = CreatSave(world);
        String jsonStr = json.toJson(save);
        FileHandle file = Gdx.files.local("save.json");
        //True means append, false means overwrite.
        file.writeString(jsonStr, false);
    }

    public JsonValue LoadJson(){
        FileHandle file = Gdx.files.local("save.json");
        JsonReader json = new JsonReader();
        JsonValue save = json.parse(file);
        return save;
    }

}