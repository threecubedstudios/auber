package com.threecubed.auber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.threecubed.auber.AuberGame;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.Civilian;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Infiltrator;
import com.threecubed.auber.entities.Player;
import com.threecubed.auber.ui.GameUi;


/**
 * The main screen of the game, responsible for rendering entities and executing their functions.
 *
 * @author Daniel O'Brien
 * @version 1.0
 * @since 1.0
 * */
public class GameScreen extends ScreenAdapter {
  World world;
  AuberGame game;
  Texture stars;

  SpriteBatch screenBatch = new SpriteBatch();
  GameUi ui = new GameUi();


  /**
   * Initialise the game screen with the {@link AuberGame} object and add a few entities.
   *
   * @param game The game object
   * */
  public GameScreen(AuberGame game) {
    this.game = game;

    world = new World(game);

    Player player = new Player(64f, 64f);
    world.addEntity(player);
    world.player = player;

    world.addEntity(new Civilian(64f, 64f, world));
    world.addEntity(new Infiltrator(64f, 64f, world));
    stars = new Texture("stars.png");
  }

  @Override
  public void render(float delta) {

    
    // Set the background color
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    screenBatch.begin();
    screenBatch.draw(stars, 0, 0);
    screenBatch.end();

    OrthogonalTiledMapRenderer renderer = world.renderer;
    renderer.setView(world.camera);
    renderer.render(world.backgroundLayersIds);


    Batch batch = renderer.getBatch();
    // Iterate over all entities. Perform movement logic and render them.
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

    // Draw the UI
    ui.render(world, screenBatch);
  }

  @Override
  public void dispose() {
    world.renderer.dispose();
  }
}
