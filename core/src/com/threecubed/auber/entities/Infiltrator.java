package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.threecubed.auber.World;
import com.threecubed.auber.pathfinding.NavigationMesh;


/**
 * The infiltrator is the enemy of the game, it will navigate from system to system and sabotage
 * them until caught by the {@link Player}.
 *
 * @author Daniel O'Brien
 * @version 1.0
 * @since 1.0
 * */
public class Infiltrator extends Npc {
  private static Texture texture = new Texture("player.png");  

  public Infiltrator(float x, float y, NavigationMesh navigationMesh) {
    super(x, y, texture, navigationMesh);
  }

  @Override
  public void update(World world) {}

  @Override
  public void handleDestinationReached(World world) {}
}
