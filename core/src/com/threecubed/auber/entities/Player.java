package com.threecubed.auber.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.threecubed.auber.Utils;
import com.threecubed.auber.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The player entity that the user controls. Handles keyboard input, and interaction with other
 * entities and tiles in the game world.
 *
 * @author Daniel O'Brien
 * @version 1.0
 * @since 1.0
 * */
public class Player extends GameEntity {
  private static Texture texture = new Texture("player.png");  

  private boolean renderLaser = false;
  private Timer renderLaserTimer = new Timer();

  private ShapeRenderer laserRenderer = new ShapeRenderer();

  public Player(float x, float y) {
    super(x, y, texture);
  }

  /**
   * Handle player controls such as movement, interaction and firing the teleporing gun.
   *
   * @param world The game world
   * */
  @Override
  public void update(World world) {
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
    
    if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !renderLaser) {
      renderLaser = true;
      renderLaserTimer.scheduleTask(new Task() {
        @Override
        public void run() {
          renderLaser = false;
        }
      }, 0.25f);

      List<GameEntity> shotEntities = new ArrayList<>();
      for (GameEntity entity : world.getEntities()) {
        if (entity != this && entity instanceof Npc) {
          Rectangle entityRectangle = entity.sprite.getBoundingRectangle();
          if (Intersector.intersectSegmentRectangle(getCenter(),
                Utils.getMouseCoordinates(world.camera), entityRectangle)) {
            Npc npc = (Npc) entity;
            npc.aiEnabled = false;
            world.incrementBrigCount();
            npc.position.x = (new Random().nextFloat() * 128) + 368;
            npc.position.y = (new Random().nextFloat() * 48) + 736;
          }
        }
      }
      world.removeEntities(shotEntities);
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
      // Interact with an object
      RectangleMapObject nearbyObject = getNearbyObjects(world.map);

      if (nearbyObject != null) {
        MapProperties properties = nearbyObject.getProperties();
        String type = properties.get("type", String.class);

        switch (type) {
          case "teleporter":
            MapObjects objects = world.map.getLayers().get("object_layer").getObjects();

            String linkedTeleporterId = properties.get("linked_teleporter", String.class); 
            RectangleMapObject linkedTeleporter = (RectangleMapObject) objects.get(
                linkedTeleporterId
                );
            velocity.setZero();
            position.x = linkedTeleporter.getRectangle().getX();
            position.y = linkedTeleporter.getRectangle().getY();
            break;

          default:
            break;
        }
      }
    }

    Vector2 mousePosition = Utils.getMouseCoordinates(world.camera);

    // Set the rotation to the angle theta where theta is the angle between the mouse cursor and
    // player position. Correct the player position to be measured from the centre of the sprite.
    rotation = (float) (Math.toDegrees(Math.atan2(
            (mousePosition.y - getCenterY()),
            (mousePosition.x - getCenterX()))
          ) - 90f);

    move(velocity, world.map);
  }

  /**
   * Overrides the GameEntity render method to render the player's teleporter gun laser, as well
   * as the player itself.
   *
   * @param batch The batch to draw to
   * @param camera The world's camera
   * */
  @Override
  public void render(Batch batch, Camera camera) {
    if (renderLaser) {
      batch.end();

      laserRenderer.setProjectionMatrix(camera.combined);
      laserRenderer.begin(ShapeType.Line);
      Vector2 mouseCoordinates = Utils.getMouseCoordinates(camera);
      laserRenderer.line(getCenterX(), getCenterY(), mouseCoordinates.x, mouseCoordinates.y);
      laserRenderer.end();

      batch.begin();
    }
    super.render(batch, camera);
  }
}
