package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public abstract class GameEntity {
  public Texture texture;
  public Sprite sprite;

  public float speed = 0.4f;
  public float max_speed = 2f;
  public float friction = 0.9f;

  public Vector2 position;
  public Vector2 velocity;
  public float rotation = 0f;

  private float[][] collision_offsets;

  public GameEntity(float x, float y) {
    texture = new Texture("llama.png");
    sprite = new Sprite(texture);

    position = new Vector2(x, y);
    velocity = new Vector2(0, 0);

    collision_offsets = new float[][] {
      {0f, 0f},
      {sprite.getWidth(), 0f},
      {0f, sprite.getHeight()},
      {sprite.getWidth(), sprite.getHeight()}
    };
  }

  public void render(Batch batch) {
    sprite.setRotation(rotation);
    sprite.setPosition(position.x, position.y);
    sprite.draw(batch);
  }

  public abstract void update(TiledMap map, Camera camera);

  public void move(Vector2 velocity, TiledMap map) {
    TiledMapTileLayer collision_layer = (TiledMapTileLayer) map.getLayers().get("collision_layer");

    for (float[] offset : collision_offsets) {
      Cell cell = collision_layer.getCell(
            (int) ((position.x + velocity.x + offset[0]) / collision_layer.getTileWidth()),
            (int) ((position.y + offset[1]) / collision_layer.getTileHeight())
      );

      while (cell != null && velocity.x != 0) {
        velocity.x -= Math.signum(velocity.x) * 0.1f;
        if (velocity.x < 0.01f) {
          velocity.x = 0f;
        }

        cell = collision_layer.getCell(
            (int) ((position.x + velocity.x + offset[0]) / collision_layer.getTileWidth()),
            (int) ((position.y + offset[1]) / collision_layer.getTileHeight())
        );
      }
      cell = collision_layer.getCell(
            (int) ((position.x + offset[0]) / collision_layer.getTileWidth()),
            (int) ((position.y + velocity.y + offset[1]) / collision_layer.getTileHeight())
      );
      while (cell != null && velocity.y != 0) {
        velocity.y -= Math.signum(velocity.y) * 0.1f;
        if (velocity.y < 0.01f) {
          velocity.y = 0f;
        }

        cell = collision_layer.getCell(
            (int) ((position.x + offset[0]) / collision_layer.getTileWidth()),
            (int) ((position.y + velocity.y + offset[1]) / collision_layer.getTileHeight())
        );
      }
    }

    position.add(velocity);
    velocity.scl(friction);
  }
}
