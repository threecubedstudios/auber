package com.threecubed.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.threecubed.auber.entities.Civilian;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Player;


public class GameScreen extends ScreenAdapter {
  World world;
  AuberGame game;

  public GameScreen(AuberGame game) {
    this.game = game;

    world = new World(game);
    world.addEntity(new Player(290f, 290f));
    world.addEntity(new Civilian(288f, 288f, world.navigationMesh));
  }

  @Override
  public void render(float delta) {
    // Set the background color
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    OrthogonalTiledMapRenderer renderer = world.renderer;

    renderer.setView(world.camera);
    renderer.render(world.backgroundLayersIds);

    Batch batch = renderer.getBatch();
    // Iterate over all entities. Perform movement logic and render them.
    // TODO: Make .update() call every 5 frames? better performance
    batch.begin();
    for (GameEntity entity : world.getEntities()) {
      entity.update(world);
      entity.render(batch, world.camera);

      if (entity instanceof Player) {
        world.camera.position.set(entity.position.x, entity.position.y, 0);
        world.camera.update();
      }
    }
    batch.end();
    renderer.render(world.foregroundLayersIds);
  }

  @Override
  public void dispose() {
    world.renderer.dispose();
    world.map.dispose();
  }
}
