package com.threecubed.auber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.JsonValue;
import com.threecubed.auber.AuberGame;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.*;
import com.threecubed.auber.save.Save;
import com.threecubed.auber.ui.GameUi;


/**
 * The main screen of the game, responsible for rendering entities and executing their functions.
 *
 * @author Daniel O'Brien
 * @version 1.0
 * @since 1.0
 * */
public class GameScreen extends ScreenAdapter {
  public World world;
  public AuberGame game;
  Sprite stars;

  SpriteBatch screenBatch = new SpriteBatch();
  GameUi ui;

  int workingSystems = 0;

  /**
   * Initialise the game screen with the {@link AuberGame} object and add a few entities.
   *
   * @param game The game object
   * @param demoMode Whether the game should run in demo mode
   * */
  public GameScreen(AuberGame game, boolean demoMode, boolean load) {
    this.game = game;
    ui = new GameUi(game);
    ui.queueMessage(MenuScreen.difficulty.toString());
    world = new World(game, demoMode, load);
    world.ui = ui;

    if(!load){
      for (int i = 0; i < World.MAX_INFILTRATORS_IN_GAME; i++) {
        world.queueEntityAdd(new Infiltrator(world));
        world.infiltratorsAddedCount++;
      }
      for (int i = 0; i < World.NPC_COUNT; i++) {
        world.queueEntityAdd(new Civilian(world));
      }

      for (int i = 0; i < World.POWER_UP_COUNT; i++) {
        world.queueEntityAdd(new PowerUp(world));
      }
    }else{
      Save save = new Save();
      JsonValue savedValues = save.loadJson();
      MenuScreen.setDifficulty(savedValues.get("difficulty").toString());
      for (int i = 0; i < savedValues.get("entityPositionX").size; i++) {
        //1=Civilian, 2=Infiltrator, 3=Player, 4=Projectile, 5=PowerUp
        //Will print the values saved
//        System.out.print("X of entity number" + i + "is " + savedValues.get("entityPositionX").asFloatArray()[i]
//                + ", its Y is" + savedValues.get("entityPositionY").asFloatArray()[i] + "and its type is "
//                + savedValues.get("entityType").asFloatArray()[i] + "\n");
        if(savedValues.get("entityType").asFloatArray()[i] == 2){
          world.queueEntityAdd(new Infiltrator(savedValues.get("entityPositionX").asFloatArray()[i], savedValues.get("entityPositionY").asFloatArray()[i], world));
          world.infiltratorsAddedCount++;
        }else if (savedValues.get("entityType").asFloatArray()[i] == 1){
          world.queueEntityAdd(new Civilian(savedValues.get("entityPositionX").asFloatArray()[i], savedValues.get("entityPositionY").asFloatArray()[i], world));
        }else if (savedValues.get("entityType").asFloatArray()[i] == 4){
          //world.queueEntityAdd(new Projectile());
          //three more states to save
        }else if (savedValues.get("entityType").asFloatArray()[i] == 3){
          //place the Player
          //world.queueEntityAdd(new Player(savedValues.get("entityPositionX").asFloatArray()[i], savedValues.get("entityPositionY").asFloatArray()[i], world));
        }else{
          world.queueEntityAdd(new PowerUp(savedValues.get("entityPositionX").asFloatArray()[i], savedValues.get("entityPositionY").asFloatArray()[i], world));
        }
      }
    }



    stars = game.atlas.createSprite("stars");
  }

  @Override
  public void render(float delta) {
    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
      game.setScreen(new MenuScreen(game));
    }
    // Add any queued entities
    world.updateEntities();

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
    world.infiltratorCount = 0;
    for (GameEntity entity : world.getEntities()) {
      entity.update(world);
      entity.render(batch, world.camera);

      if (entity instanceof Player) {
        world.camera.position.set(entity.position.x, entity.position.y, 0);
        world.camera.update();
      } else if (entity instanceof Infiltrator) {
        Infiltrator infiltrator = (Infiltrator) entity;
        if (infiltrator.aiEnabled) {
          world.infiltratorCount += 1;
        }
      }
    }
    batch.end();
    renderer.render(world.foregroundLayersIds);

    if (world.infiltratorCount < World.MAX_INFILTRATORS_IN_GAME
        && world.infiltratorsAddedCount < World.MAX_INFILTRATORS) {
      Infiltrator newInfiltrator = new Infiltrator(world);
      while (newInfiltrator.entityOnScreen(world)) {
        newInfiltrator.moveToRandomLocation(world);
      }
      world.queueEntityAdd(newInfiltrator);
      world.infiltratorsAddedCount++;
    }

    // Draw the UI
    ui.render(world, screenBatch);
    world.checkForEndState();
  }

  @Override
  public void dispose() {
    world.renderer.dispose();
  }
}
