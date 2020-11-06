package com.threecubed.auber.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Player extends GameEntity {
  public Player(float x, float y) {
    super(x, y);
  }

  public void update(TiledMap map) {

    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      velocity.y = Math.min(velocity.y + speed, max_speed);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      velocity.x = Math.max(velocity.x - speed, -max_speed);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      velocity.y = Math.max(velocity.y - speed, -max_speed);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      velocity.x = Math.min(velocity.x + speed, max_speed);
    }

    move(velocity, map);
  }
}
