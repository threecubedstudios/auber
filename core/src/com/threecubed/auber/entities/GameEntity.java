package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public abstract class GameEntity {
  public Texture texture;
  public Sprite sprite;

  public float speed = 0.1f;
  public float max_speed = 1f;
  public float friction = 0.5f;

  public Vector2 position;
  public Vector2 velocity;
  public float rotation = 0f;

  public GameEntity(float x, float y) {
    texture = new Texture("llama.png");
    sprite = new Sprite(texture);

    position = new Vector2(x, y);
    velocity = new Vector2(0, 0);
  }

  public void render(Batch batch) {
    sprite.setRotation(rotation);
    sprite.setPosition(position.x, position.y);
    sprite.draw(batch);
  }

  public abstract void update(TiledMap map);

  public void move(Vector2 velocity, TiledMap map) {
    float target_x = position.x + velocity.x;
    float target_y = position.y + velocity.y;

    if (velocity.x > 0) {
      target_x += sprite.getWidth();
    }
    if (velocity.y > 0) {
      target_y += sprite.getHeight();
    }

    TiledMapTileLayer collision_layer = (TiledMapTileLayer) map.getLayers().get("collision_layer");

    TiledMapTileLayer.Cell cell = collision_layer.getCell(
        (int) target_x / collision_layer.getTileWidth(),
        (int) target_y / collision_layer.getTileHeight()
    );

    if (cell == null) {
      position.add(velocity);
    } else {
      velocity.setZero();
    }
  }
}
