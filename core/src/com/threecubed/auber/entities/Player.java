package com.threecubed.auber.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends GameEntity {
  public Player(float x, float y) {
    super(x, y);
  }

  public void update(TiledMap map, Camera camera) {
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

    Vector3 mouse_position = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(mouse_position);
    Vector2 mouse_position_2d = new Vector2(mouse_position.x, mouse_position.y);
    rotation = (float) (Math.toDegrees(Math.atan2((mouse_position_2d.y-position.y),(mouse_position_2d.x-position.x))) - 90f);

    move(velocity, map);
  }
}
