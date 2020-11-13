package com.threecubed.auber.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends GameEntity {
  public static Texture texture = new Texture("player.png");  

  public Player(float x, float y) {
    super(x, y, texture);
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
    
    // TODO: Abstract into different method
    if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
      // Interact with an object
      MapLayer interactionLayer = map.getLayers().get("object_layer");
      MapObjects objects = interactionLayer.getObjects();

      for (MapObject object : objects) {
        if (object instanceof RectangleMapObject) {
          RectangleMapObject rectangularObject = (RectangleMapObject) object;
          if (Intersector.overlaps(sprite.getBoundingRectangle(), rectangularObject.getRectangle())) {
            MapProperties properties = rectangularObject.getProperties();
            String type = properties.get("type", String.class);

            switch (type) {
              case "teleporter":
                String linkedTeleporterId = properties.get("linked_teleporter", String.class); 
                RectangleMapObject linkedTeleporter = (RectangleMapObject) objects.get(linkedTeleporterId);
                velocity.setZero();
                position.x = linkedTeleporter.getRectangle().getX();
                position.y = linkedTeleporter.getRectangle().getY();
                break;

              default:
                break;
            }
          }
        }
      }
    }

    Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(mousePosition);
    Vector2 mousePosition2d = new Vector2(mousePosition.x, mousePosition.y);

    // Set the rotation to the angle theta where theta is the angle between the mouse cursor and
    // player position. Correct the player position to be measured from the centre of the sprite.
    rotation = (float) (Math.toDegrees(Math.atan2(
            (mousePosition2d.y - position.y - (sprite.getWidth() / 2)),
            (mousePosition2d.x - position.x - (sprite.getHeight() / 2)))
          ) - 90f);

    move(velocity, map);
  }
}
