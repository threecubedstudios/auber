package com.threecubed.auber;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.threecubed.auber.entities.GameEntity;
import java.util.ArrayList;
import java.util.List;


public class World {
  private List<GameEntity> entities = new ArrayList<>();

  public OrthographicCamera camera = new OrthographicCamera();

  public TiledMap map = new TmxMapLoader().load("map.tmx");
  public OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);

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
   * */
  public World() {
    // Configure the camera
    camera.setToOrtho(false, 480, 270);
    camera.update();
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
}
