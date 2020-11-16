package com.threecubed.auber;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class AuberGame extends Game {
  @Override
  public void create() {
    Gdx.graphics.setWindowedMode(1920, 1080);
    setScreen(new MenuScreen(this));
  }
}

