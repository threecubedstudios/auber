package com.threecubed.auber.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.AuberGame;
import com.threecubed.auber.screens.GameScreen;

public class Button {
  
  Texture texture;
  Pixmap pixmap;
  Sprite sprite;
  AuberGame game;
  Vector2 position;

  public Button(Vector2 position, float scale, String texture, AuberGame game) {
    this.texture = new Texture(texture);
    this.sprite = new Sprite(this.texture);
    sprite.setScale(scale);
    sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));
    this.position = new Vector2(sprite.getX(), sprite.getY());
    this.game = game;
  }

  public void render(SpriteBatch spriteBatch) {
    sprite.draw(spriteBatch);

    if (sprite.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.input.getY())){
      sprite.setScale(1.05f);
      if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
        game.setScreen(new GameScreen(game));
      }
    }
    else {
        sprite.setScale(1.0f);
    }


    
    
  }
}
