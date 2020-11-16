package com.threecubed.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

public class MenuScreen extends ScreenAdapter {
  AuberGame game;

  public MenuScreen(AuberGame game) {
    this.game = game;
  }

  @Override
  public void render(float deltaTime) {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      game.setScreen(new GameScreen(game));
    }
  }
  
}
