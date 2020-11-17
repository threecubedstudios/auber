package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class Civilian extends Npc {
  private static Texture texture = new Texture("civilian.png");

  public Civilian(float x, float y, TiledMap map) {
    super(x, y, texture, map);
  }
}


