package com.threecubed.auber.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.AuberGame;
import com.threecubed.auber.Utils;


/**
 * Render a button and also handle the pressing of said button.
 *
 * @author Joseph Krystek-Walton
 * @version 1.0
 * @since 1.0
 * */
public class Button {
  Pixmap pixmap;
  Sprite sprite;
  AuberGame game;
  Vector2 position;
  Runnable onClick;
  Camera camera;

  /**
   * Instantiate a button at a given position with a given scale and texture.
   * Pass the game object so that the button's action can be configured.
   *
   * @param position The position to render the center of the button at
   * @param scale The scale to render the button with
   * @param sprite The sprite of the button
   * @param game The game object
   * @param onClick The code to execute when the button is pressed wrapped in a {@link Runnable}
   * */
  public Button(Vector2 position, float scale, Sprite sprite, AuberGame game, Runnable onClick, Camera camera) {
    this.sprite = sprite;
    this.onClick = onClick;
    this.camera = camera;

    sprite.setScale(scale);
    sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));

    this.position = new Vector2(sprite.getX(), sprite.getY());
    this.game = game;
  }

  /**
   * Render the button at the provided position and scale to the given {@link SpriteBatch}.
   *
   * @param spriteBatch The sprite batch to render the button to
   * */
  public void render(SpriteBatch spriteBatch) {
    sprite.draw(spriteBatch);
    Vector2 mouseCoords = Utils.getMouseCoordinates(camera);

    if (sprite.getBoundingRectangle().contains(mouseCoords.x, mouseCoords.y)) {
      sprite.setScale(1.05f);
      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        onClick.run();
      }
    } else {
      sprite.setScale(1.0f);
    }
  }
}
