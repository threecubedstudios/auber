package com.threecubed.auber.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameEntity {
  public Player(float x, float y) {
    texture = new Texture("llama.png");
    sprite = new Sprite(texture);

    position = new Vector2(x, y);
    velocity = new Vector2(0, 0);
  }

  public void update() {
    velocity.scl(friction);

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

    position.add(velocity);
  }
}
