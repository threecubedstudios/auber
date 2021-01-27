package com.threecubed.auber;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.threecubed.auber.entities.Infiltrator;
import com.threecubed.auber.entities.Player;
import com.threecubed.auber.screens.GameScreen;

import java.util.Map;

/**
 *  module used to load saving data and update saving data
 *
 * @author haopeng
 * @version 1.1
 * @since 1.1
 * */
public class DataManager {


    public Preferences preferences;

    public DataManager(String savingFile) {
        this.preferences = Gdx.app.getPreferences(savingFile);
    }

    public Player loadPlayerData(World world) {
        float health = preferences.getFloat("health",1f);
        float playerPositionX = preferences.getFloat("PlayerPositionX",64f);
        float playerPositionY = preferences.getFloat("PlayerPositionY",64f);
        boolean confused = preferences.getBoolean("confused",false);
        boolean blinded = preferences.getBoolean("blinded",false);
        boolean slowed = preferences.getBoolean("slowed",false);

        Player player = new Player(playerPositionX,playerPositionY,world);
        player.slowed = slowed;
        player.blinded = blinded;
        player.confused = confused;
        player.health = health;
        return  player;
    }

    public Infiltrator loadInfiltratorData(World world, Integer infiltratorNo) {
        float infiltratorPositionX = preferences.getFloat("InfiltratorPositionX" + infiltratorNo.toString(),0);
        float infiltratorPositionY = preferences.getFloat("InfiltratorPositionY"+infiltratorNo.toString(),0);
        if (infiltratorPositionX==0f && infiltratorPositionY == 0f) {
            return new Infiltrator(world);
        } else {
            return new Infiltrator(infiltratorPositionX,infiltratorPositionY,world);
        }
    }

    public void savePlayerData(World world) {
        preferences.putFloat("health",world.player.health);
        preferences.putFloat("PlayerPositionX",world.player.position.x);
        preferences.putFloat("PlayerPositionY",world.player.position.y);
        preferences.putBoolean("confused",world.player.confused);
        preferences.putBoolean("blinded",world.player.blinded);
        preferences.putBoolean("slowed",world.player.slowed);
        preferences.flush();
    }

    public void saveInfiltratorData(){
        for (Map.Entry<Infiltrator,Integer> entry: GameScreen.enemyTrack.entrySet()) {
            float infiltratorPositionX = entry.getKey().position.x;
            float infiltratorPositionY = entry.getKey().position.y;
            Integer infiltratorNo = entry.getValue();
            preferences.putFloat("InfiltratorPositionX" + infiltratorNo.toString(),infiltratorPositionX);
            preferences.putFloat("InfiltratorPositionY" + infiltratorNo.toString(),infiltratorPositionY);
            preferences.flush();
        }
    }

}
