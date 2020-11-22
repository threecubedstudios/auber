package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Timer.Task;
import com.threecubed.auber.Utils;
import com.threecubed.auber.World;


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

  public Infiltrator(float x, float y, World world) {
    super(x, y, texture, world.navigationMesh);
    navigateToRandomSystem(world);
  }

  @Override
  public void handleDestinationReached(World world) {
    state = States.IDLE;
    if (!playerNearby(world)
        && Utils.randomFloatInRange(world.randomNumberGenerator, 0, 1)
        > World.SYSTEM_SABOTAGE_CHANCE) {
      attackNearbySystem(world);
    } else {
      idleForGivenTime(world, Utils.randomFloatInRange(world.randomNumberGenerator, 5f, 8f));
    }
  }

  /**
   * Attack a system nearby to the infiltrator.
   * */
  private void attackNearbySystem(final World world) {
    state = States.ATTACKING_SYSTEM;

    final RectangleMapObject system = getNearbyObjects(World.map);

    world.updateSystemState(system.getRectangle().getX(), system.getRectangle().getY(),
        World.SystemStates.ATTACKED);

    npcTimer.scheduleTask(new Task() {
      @Override
      public void run() {
        if (aiEnabled) {
          world.updateSystemState(system.getRectangle().getX(), system.getRectangle().getY(),
              World.SystemStates.DESTROYED);
          navigateToRandomSystem(world);
        }
      }
    }, World.SYSTEM_BREAK_TIME);
  }

  private boolean playerNearby(World world) {
    Circle infiltratorSight = new Circle(position, World.INFILTRATOR_SIGHT_RANGE);
    if (infiltratorSight.contains(world.player.position)) {
      return true;
    }
    return false;
  }
}
