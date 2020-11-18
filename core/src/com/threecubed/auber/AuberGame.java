package com.threecubed.auber;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.threecubed.auber.screens.MenuScreen;

public class AuberGame extends Game {
  @Override
  public void create() {
    Gdx.graphics.setWindowedMode(1920, 1080);
    setScreen(new MenuScreen(this));
  }
}
