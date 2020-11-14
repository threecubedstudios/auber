package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import java.util.List;


public abstract class Npc extends GameEntity {
  public Npc(float x, float y, Texture texture) {
    super(x, y, texture);
  }

  @Override
  public void update(TiledMap map, Camera camera, List<GameEntity> entities) {}
}
