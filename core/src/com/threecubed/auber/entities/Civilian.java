package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.threecubed.auber.World;
import com.threecubed.auber.pathfinding.NavigationMesh;


/**
 * The Civilian is the passive entity in auber that allows for {@link Infiltrator}s to blend in.
 * It will navigate from system to system performing tasks that won't actually have any impact on
 * the game since its existence only serves to conceal infiltrators
 *
 * @author Daniel O'Brien
 * @version 1.0
 * @since 1.0
 * */
public class Civilian extends Npc {
  private static Texture texture = new Texture("civilian.png");

  public Civilian(float x, float y, NavigationMesh navigationMesh) {
    super(x, y, texture, navigationMesh);
  }

  /**
   * Obtain navigation targets and step in their direction.
   *
   * @param world The game world
   * */
  @Override
  public void update(World world) {
    stepTowardsTarget(world);
  }
}
