package com.threecubed.auber.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

  }



  @Test
  public void infiltratorSaveTest() {

  }



  @Test
  public void systemSaveTest() {

  }




}
