package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.utils.Timer.Task;
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
    attackNearbySystem(world);
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
        world.updateSystemState(system.getRectangle().getX(), system.getRectangle().getY(),
            World.SystemStates.DESTROYED);
        navigateToRandomSystem(world);
      }
    }, World.SYSTEM_BREAK_TIME);
  }
}
