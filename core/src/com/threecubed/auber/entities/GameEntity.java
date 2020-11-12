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
  public Texture texture = new Texture("default.png");
  public Sprite sprite;

  public float speed = 0.4f;
  public float maxSpeed = 2f;
  public float friction = 0.9f;

  public Vector2 position;
  public Vector2 velocity;
  public float rotation = 0f;

  private float[][] collisionOffsets;

  /**
   * Initialise a game entity at a given x and y coordinates
   *
   * @param x The x coordinate of the entity
   * @param y The y coordinate of the entity
   * */
  public GameEntity(float x, float y) {
    sprite = new Sprite(texture);

    position = new Vector2(x, y);
    velocity = new Vector2(0, 0);

    collisionOffsets = new float[][] {
        {0f, 0f},
        {sprite.getWidth(), 0f},
        {0f, sprite.getHeight()},
        {sprite.getWidth(), sprite.getHeight()}
      };
  }

  /**
   * Render the entity at its current coordinates with its current rotation.
   *
   * @param batch The batch to draw the sprite to
   * */
  public void render(Batch batch) {
    sprite.setRotation(rotation);
    sprite.setPosition(position.x, position.y);
    sprite.draw(batch);
  }

  /**
   * The "brain" of the entity, run any code that should be run at each render cycle that isn't
   * related to rendering the entity.
   * @param map The tilemap of the world
   * @param camera The world camera
   * */
  public abstract void update(TiledMap map, Camera camera);

  /**
   * Update the entity's {@link Position}, taking into account any obstacles and their current
   * velocity.
   *
   * @param velocity The entity's current velocity
   * @param map The tilemap to test for collisions on
   * */
  public void move(Vector2 velocity, TiledMap map) {
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("collision_layer");

    for (float[] offset : collisionOffsets) {
      Cell cell = collisionLayer.getCell(
            (int) ((position.x + velocity.x + offset[0]) / collisionLayer.getTileWidth()),
            (int) ((position.y + offset[1]) / collisionLayer.getTileHeight())
      );

      while (cell != null && velocity.x != 0) {
        velocity.x -= Math.signum(velocity.x) * 0.1f;
        if (velocity.x < 0.01f) {
          velocity.x = 0f;
        }

        cell = collisionLayer.getCell(
            (int) ((position.x + velocity.x + offset[0]) / collisionLayer.getTileWidth()),
            (int) ((position.y + offset[1]) / collisionLayer.getTileHeight())
        );
      }
      cell = collisionLayer.getCell(
            (int) ((position.x + offset[0]) / collisionLayer.getTileWidth()),
            (int) ((position.y + velocity.y + offset[1]) / collisionLayer.getTileHeight())
      );
      while (cell != null && velocity.y != 0) {
        velocity.y -= Math.signum(velocity.y) * 0.1f;
        if (velocity.y < 0.01f) {
          velocity.y = 0f;
        }

        cell = collisionLayer.getCell(
            (int) ((position.x + offset[0]) / collisionLayer.getTileWidth()),
            (int) ((position.y + velocity.y + offset[1]) / collisionLayer.getTileHeight())
        );
      }
    }

    position.add(velocity);
    velocity.scl(friction);
  }
}
