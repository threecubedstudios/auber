package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.threecubed.auber.World;


public abstract class Civilian extends Npc {
  private static Texture texture = new Texture("player.png");  

  public Civilian(float x, float y) {
    super(x, y, texture);
  }

  @Override
  public void update(World world) {}
}


