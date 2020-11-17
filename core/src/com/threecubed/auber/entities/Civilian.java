package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.threecubed.auber.World;


public class Civilian extends Npc {
  private static Texture texture = new Texture("player.png");  
  
  public Civilian(float x, float y, TiledMap map) {
    super(x, y, texture, map);
    
  }

  @Override
  public void update(World world) {
    super.update(world);
    
  }

  @Override
  public void render(Batch batch, Camera camera) {
    super.render(batch, camera);
  }
}


