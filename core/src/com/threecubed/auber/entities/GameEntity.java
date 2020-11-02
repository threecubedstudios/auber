package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameEntity {
  public Texture texture;
  public Sprite sprite;

  public float speed = 5f;
  public float max_speed = 10f;
  public float friction = 0.9f;

  public Vector2 position;
  public Vector2 velocity;
  public float rotation = 0f;

  public void render(SpriteBatch batch) {
    sprite.setRotation(rotation);
    sprite.setPosition(position.x, position.y);
    sprite.draw(batch);
  }

  public abstract void update();
}
