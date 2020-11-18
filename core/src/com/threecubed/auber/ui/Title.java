package com.threecubed.auber.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * Render a title in the window.
 *
 * @author Joseph Krystek-Walton
 * @version 1.0
 * @since 1.0
 * */
public class Title {
  private Sprite sprite;
  private Texture texture;

  /**
   * Instantiate the title class at a given position and scale with a given texture.
   *
   * @param position A {@link Vector2} representing the position to render the title at
   * @param scale The scale at which to render the title
   * @param texture The texture to use for the title
   * */
  public Title(Vector2 position, float scale, String texture) {
    this.texture = new Texture(texture);
    this.sprite = new Sprite(this.texture);
    sprite.setScale(scale);
    sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
  }

  /**
   * Render the title to a given {@link SpriteBatch}
   *
   * @param spriteBatch The sprite batch to render to.
   * */
  public void render(SpriteBatch spriteBatch) {
    sprite.draw(spriteBatch);
  }
}
