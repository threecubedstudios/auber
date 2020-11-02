package com.threecubed.auber;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
    // Set the background color
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Iterate over all entities. Perform movement logic and render them.
    // TODO: Make .update() call every 5 frames? better performance
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
