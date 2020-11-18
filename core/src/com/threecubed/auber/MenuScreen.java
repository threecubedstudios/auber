package com.threecubed.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MenuScreen extends ScreenAdapter {
  
  AuberGame game;
  Title title;
  Button playButton;
  OrthogonalTiledMapRenderer renderer;

  public MenuScreen(AuberGame game) {
    this.game = game;
    this.title = new Title(new Vector2(Gdx.graphics.getWidth() / 2, 300 + (Gdx.graphics.getHeight() / 2)), 0.5f, "auberv11.png");
    this.playButton = new Button(new Vector2(Gdx.graphics.getWidth() / 2, (Gdx.graphics.getHeight() / 2)), 1.0f, "playButton.png", game);
  }

  @Override
  public void render(float deltaTime) {

    //if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
    //  game.setScreen(new GameScreen(game));
    //}

    
    // Set the background color
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    SpriteBatch spriteBatch = new SpriteBatch();

    spriteBatch.begin();

    title.render(spriteBatch);
    playButton.render(spriteBatch);

    spriteBatch.end();

  }
  
}
