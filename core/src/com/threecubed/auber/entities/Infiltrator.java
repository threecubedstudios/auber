package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import java.util.List;


public abstract class Infiltrator extends Npc {
  private static Texture texture = new Texture("player.png");  

  public Infiltrator(float x, float y) {
    super(x, y, texture);
  }

  @Override
  public void update(TiledMap map, Camera camera, List<GameEntity> entities) {}
}

