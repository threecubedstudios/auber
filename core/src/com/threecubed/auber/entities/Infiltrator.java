package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.threecubed.auber.World;
import com.threecubed.auber.pathfinding.NavigationMesh;


public class Infiltrator extends Npc {
  private static Texture texture = new Texture("player.png");  

  public Infiltrator(float x, float y, NavigationMesh navigationMesh) {
    super(x, y, texture, navigationMesh);
  }

  @Override
  public void update(World world) {}
}
