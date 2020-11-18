package com.threecubed.auber;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class Title {



  private Sprite sprite;
  private Texture texture;



  public Title(Vector2 position, float scale, String texture) {
    
    this.texture = new Texture(texture);
    this.sprite = new Sprite(this.texture);
    sprite.setScale(scale);
    sprite.setPosition(position.x - (sprite.getWidth() / 2), position.y - (sprite.getHeight() / 2));

  }



  public void render(SpriteBatch spriteBatch) {

    sprite.draw(spriteBatch);
    
  }

}
