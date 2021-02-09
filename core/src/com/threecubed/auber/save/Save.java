package com.threecubed.auber.save;
//first is the position on NPC, second is the type of them in the position
//then is position and health of player
//[System.Serializable]

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.GameEntity;
import com.badlogic.gdx.utils.Json;

import java.util.*;

public class Save {

    public List<Float> entityPositionX = new ArrayList<Float>();
    public List<Float> entityPositionY = new ArrayList<Float>();
    public List<Integer> entityType = new ArrayList<Integer>();



    public static Save createSave(World world){
        Save save = new Save();
        for (GameEntity entity : world.getEntities()) {
            save.entityPositionX.add(entity.getCenterX());
            save.entityPositionY.add(entity.getCenterY());
            save.entityType.add(entity.entityType);
        }
        return save;
    }

    public static void saveJson(World world){

        Json json = new Json();
        Save save = createSave(world);
        String jsonStr = json.toJson(save);
        FileHandle file = Gdx.files.local("save.json");
        //True means append, false means overwrite.
        file.writeString(jsonStr, false);
    }

    public static JsonValue loadJson(){
        FileHandle file = Gdx.files.local("save.json");
        JsonReader json = new JsonReader();
        JsonValue save = json.parse(file);
        return save;
    }

}