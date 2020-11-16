package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;


public abstract class Civilian extends Npc {
  private static Texture texture = new Texture("player.png");  
  
  public Civilian(float x, float y, TiledMap map) {
    super(x, y, texture, map);
    
  }

  /*@Override
  public void update(TiledMap map, Camera camera, List<GameEntity> entities, World world) {
    super.update(map, camera, entities);
  }*/
}


