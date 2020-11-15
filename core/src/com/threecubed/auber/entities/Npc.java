package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.threecubed.auber.World;


public class Npc extends GameEntity {
  public boolean aiEnabled = true;

  public Npc(float x, float y, Texture texture) {
    super(x, y, texture);
  }

  @Override
  public void update(World world) {}
}
