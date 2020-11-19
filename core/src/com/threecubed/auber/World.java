package com.threecubed.auber;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.pathfinding.NavigationMesh;
import com.threecubed.auber.screens.GameOverScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The world class stores information related to what is happening within the game world.
 * It should only be used within the GameScreen screen.
 *
 * @author Daniel O'Brien
 * @version 1.0
 * @since 1.0
 * */
public class World {
  private AuberGame game;

  private List<GameEntity> entities = new ArrayList<>();

  public OrthographicCamera camera = new OrthographicCamera();

  public TiledMap map = new TmxMapLoader().load("map.tmx");
  public OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);

  public ArrayList<RectangleMapObject> systems = new ArrayList<>();

  public Random randomNumberGenerator = new Random();

  // Navigation mesh for AI to use
  public NavigationMesh navigationMesh = new NavigationMesh(
      (TiledMapTileLayer) map.getLayers().get("background_layer")
      );

  // Maximum size of brig, and number of entities currently in the brig
  public final int brigCapacity = 5;
  private int brigCount = 0;

  // IDs of layers that should be rendered behind entities
  public int[] backgroundLayersIds = {
    map.getLayers().getIndex("background_layer"),
    map.getLayers().getIndex("collision_layer")
    };

  // IDs of layers that should be rendered infront of entities
  public int[] foregroundLayersIds = {
    map.getLayers().getIndex("foreground_layer")
    };

  /**
   * Initialise the game world.
   *
   * @param game The game object.
   * */
  public World(AuberGame game) {
    // Configure the camera
    camera.setToOrtho(false, 480, 270);
    camera.update();

    this.game = game;

    MapObjects objects = map.getLayers().get("object_layer").getObjects();
    for (MapObject object : objects) {
      if (object instanceof RectangleMapObject) {
        RectangleMapObject rectangularObject = (RectangleMapObject) object;
        switch (rectangularObject.getProperties().get("type", String.class)) {
          case "system":
            systems.add(rectangularObject);
            break;
          default:
            break;
        }
      }
    }
  }

  public List<GameEntity> getEntities() {
    return entities;
  }

  public void addEntity(GameEntity entity) {
    entities.add(entity);
  }

  public void removeEntities(List<GameEntity> entities) {
    entities.removeAll(entities);
  }

  /**
   * Increment the number of entities in the brig by 1.
   * */
  public void incrementBrigCount() {
    brigCount += 1;
    if (brigCount >= brigCapacity) {
      game.setScreen(new GameOverScreen(this.game));
    }
  }

  public int getBrigCount() {
    return brigCount;
  }
}
