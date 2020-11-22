package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.threecubed.auber.Utils;
import com.threecubed.auber.World;


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
  private static String[] textureNames = {"alienA.png", "alienB.png"};

  /**
   * Construct a {@link Civilian} entity at given coordinates, with a randomly chosen texture
   * from the list of textureNames.
   *
   * @param x The x coordinate of the civilian
   * @param y The y coordinate of the civilian
   * @param world The game world
   * */
  public Civilian(float x, float y, World world) {
    super(x, y,
        new Texture(textureNames[Utils.randomIntInRange(world.randomNumberGenerator,
                                                        0, textureNames.length - 1)]),
        world.navigationMesh);
    navigateToRandomSystem(world);
  }

  @Override
  public void handleDestinationReached(final World world) {
    state = States.IDLE;
    idleForGivenTime(world, Utils.randomFloatInRange(world.randomNumberGenerator, 5f, 10f));
  }
}
