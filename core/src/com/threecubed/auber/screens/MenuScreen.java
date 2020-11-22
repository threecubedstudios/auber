package com.threecubed.auber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.AuberGame;
import com.threecubed.auber.World;
import com.threecubed.auber.ui.Button;
import com.threecubed.auber.ui.Title;


/**
 * The menu screen is the first screen that shows in the game and is responsible for controlling
 * when the game begins.
 *
 * @author Joseph Krystek-Walton
 * @version 1.0
 * @since 1.0
 * */
public class MenuScreen extends ScreenAdapter {
  //World world;
  AuberGame game;
  Title title;
  Button playButton;
  OrthogonalTiledMapRenderer renderer;
  TextureRegion backgroundTexture = new TextureRegion(new Texture("stars.png"), 0, 0,
      1920, 1080);
  Texture instructions = new Texture("instructions.png");

  /**
   * Instantiate the screen with the {@link AuberGame} object. Set the title and button up to be
   * rendered.
   *
   * @param game The game object
   * */
  public MenuScreen(AuberGame game) {
    this.game = game;
    //this.world = new World(game);
    this.title = new Title(new Vector2(Gdx.graphics.getWidth() / 4,
          300 + (Gdx.graphics.getHeight() / 3)), 0.5f, "auberv11.png");
    this.playButton = new Button(new Vector2(Gdx.graphics.getWidth() / 4,
          (Gdx.graphics.getHeight() / 3)), 1.0f, "playButton.png", game);
  }

  @Override
  public void render(float deltaTime) {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      game.setScreen(new GameScreen(game));
    }
    
    // Set the background color
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    SpriteBatch spriteBatch = new SpriteBatch();
    spriteBatch.begin();

    spriteBatch.draw(backgroundTexture, 0, 0);
    spriteBatch.draw(instructions, 900f, 125f);
    title.render(spriteBatch);
    playButton.render(spriteBatch);
    

    spriteBatch.end();
  }
}
