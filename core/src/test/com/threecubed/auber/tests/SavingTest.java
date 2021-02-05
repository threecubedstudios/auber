package com.threecubed.auber.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.threecubed.auber.AuberGame;
import com.threecubed.auber.Difficulty;
import com.threecubed.auber.GdxTestRunner;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.Player;
import com.threecubed.auber.screens.GameScreen;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class SavingTest {




  @Test
  public void playerSaveTest() {

     String testFile = "test";
     Preferences preferences = Gdx.app.getPreferences(testFile);
     AuberGame game = new AuberGame();
     TmxMapLoader mapLoader = new TmxMapLoader();
     TiledMap map = mapLoader.load("map.tmx");
     World world = new World(game);

  }



  @Test
  public void infiltratorSaveTest() {

  }



  @Test
  public void systemSaveTest() {

  }




}