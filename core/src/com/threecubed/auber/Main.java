package com.threecubed.auber;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Player;
import java.util.ArrayList;


public class Main extends ApplicationAdapter {
  ArrayList<GameEntity> entities;

  TiledMap map;
  OrthogonalTiledMapRenderer renderer;

  OrthographicCamera camera;

  int[] backgroundLayersIds;
  int[] foregroundLayersIds;

  @Override
  public void create() {
    Gdx.graphics.setWindowedMode(1920, 1080);

    entities = new ArrayList<>();
    entities.add(new Player(290f, 290f));

    // Load the tilemap
    map = new TmxMapLoader().load("map.tmx");
    renderer = new OrthogonalTiledMapRenderer(map);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 480, 270);
    camera.update();

    // IDs of layers that should be rendered behind entities
    backgroundLayersIds = new int[] {
      map.getLayers().getIndex("background_layer"),
      map.getLayers().getIndex("collision_layer")
      };

    // IDs of layers that should be rendered infront of entities
    foregroundLayersIds = new int[] {
      map.getLayers().getIndex("foreground_layer")
      };
  }

  @Override
  public void render() {
    // Set the background color
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    renderer.setView(camera);
    renderer.render(backgroundLayersIds);

    Batch batch = renderer.getBatch();
    // Iterate over all entities. Perform movement logic and render them.
    // TODO: Make .update() call every 5 frames? better performance
    batch.begin();
    for (GameEntity entity : entities) {
      entity.update(map, camera, entities);
      entity.render(batch, camera);

      camera.position.set(entity.position.x, entity.position.y, 0);
      camera.update();
    }
    batch.end();
    renderer.render(foregroundLayersIds);
  }

  @Override
  public void dispose() {
  }
}
