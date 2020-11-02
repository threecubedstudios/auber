package com.threecubed.auber;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Player;

public class Main extends ApplicationAdapter {
  SpriteBatch batch;
  ArrayList<GameEntity> entities;

  @Override
  public void create () {
    batch = new SpriteBatch();
    entities = new ArrayList<>();
    entities.add(new Player(0f, 0f));
  }

  @Override
  public void render () {
    batch.begin();
    for (GameEntity entity : entities) {
      entity.update();
      entity.render(batch);
    }
    batch.end();
  }

  @Override
  public void dispose () {
    batch.dispose();
  }
}
