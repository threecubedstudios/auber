package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.threecubed.auber.World;
import com.threecubed.auber.pathfinding.NavigationMesh;


public class Civilian extends Npc {
  private static Texture texture = new Texture("civilian.png");

  public Civilian(float x, float y, NavigationMesh navigationMesh) {
    super(x, y, texture, navigationMesh);
  }

  public void update(World world) {
    stepTowardsTarget(world);
  }
}


