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
import com.threecubed.auber.entities.Player;
import com.badlogic.gdx.utils.Json;

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
        String jsonStrX = json.toJson(save.entityPositionX);
        FileHandle fileX = Gdx.files.local("entityPositionX.json");
        String jsonStrY = json.toJson(save.entityPositionY);
        FileHandle fileY = Gdx.files.local("entityPositionX.json");
        String jsonStrT = json.toJson(save.entityPositionX);
        FileHandle fileT = Gdx.files.local("entityType.json");
        //True means append, false means overwrite.
        fileX.writeString(jsonStrX, false);
        fileY.writeString(jsonStrY, false);
        fileT.writeString(jsonStrT, false);
    }

    public void LoadJson(){
        FileHandle fileX = Gdx.files.local("entityPositionX.json");
        FileHandle fileY = Gdx.files.local("entityPositionX.json");
        FileHandle fileT = Gdx.files.local("entityType.json");
//        String save = file.readString();
        JsonReader json = new JsonReader();
        JsonValue positionX = json.parse(fileX);
        JsonValue positionY = json.parse(fileY);
        JsonValue positionT = json.parse(fileT);
        //Save save1 = json.fromJson(String, save);
        //entityPositionX = json.fromJson(float.class, .class);
        //JsonValue entityPositionX = base.get("entityPositionX");
        //JsonValue entityPositionY = base.get("entityPositionY");
        //JsonValue entityType = base.get("entityType");

        //System.out.print(entityPositionX);
        JsonValue eachPositionX = positionX.child().next;
        System.out.print(eachPositionX);


    }

}