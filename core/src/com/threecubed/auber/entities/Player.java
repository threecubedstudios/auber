package com.threecubed.auber.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends GameEntity {
  Texture texture = new Texture("player.png");  

  public Player(float x, float y) {
    super(x, y);
  }

  /**
   * Handle player controls such as movement, interaction and firing the teleporing gun.
   *
   * @param map The game world's tilemap
   * @param camera The game world's camera
   * */
  public void update(TiledMap map, Camera camera) {
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      velocity.y = Math.min(velocity.y + speed, maxSpeed);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      velocity.x = Math.max(velocity.x - speed, -maxSpeed);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      velocity.y = Math.max(velocity.y - speed, -maxSpeed);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      velocity.x = Math.min(velocity.x + speed, maxSpeed);
    }
    
    if (Gdx.input.isKeyPressed(Input.Keys.E)) {
      // Interact with an object
      TiledMapTileLayer interactionLayer = (TiledMapTileLayer) map.getLayers().get("interaction_layer");
      int[] playerTile = {
        (int) position.x / interactionLayer.getTileWidth(),
        (int) position.y / interactionLayer.getTileHeight()
        };

      int[][] potentialTiles = {
          playerTile,
          {playerTile[0] - 1, playerTile[1]},
          {playerTile[0] + 1, playerTile[1]},
          {playerTile[0], playerTile[1] - 1},
          {playerTile[0], playerTile[1] + 1},
        };

      for (int[] tileCoordinates : potentialTiles) {
        Cell currentTile = interactionLayer.getCell(tileCoordinates[0], tileCoordinates[1]);
        if (currentTile != null) {
          // Tile
        }
      }
    }

    Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(mousePosition);
    Vector2 mousePosition_2d = new Vector2(mousePosition.x, mousePosition.y);
    rotation = (float) (Math.toDegrees(Math.atan2((mousePosition_2d.y-position.y),(mousePosition_2d.x-position.x))) - 90f);

    move(velocity, map);
  }
}
