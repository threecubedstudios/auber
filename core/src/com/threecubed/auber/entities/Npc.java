package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.threecubed.auber.Utils;
import com.threecubed.auber.World;
import com.threecubed.auber.pathfinding.NavigationMesh;
import java.util.ArrayList;


/**
 * Npc is the class from which all AI controlled {@link GameEntity}s must extend.
 * It contains the code that allows for those entities to interact with the
 * {@link NavigationMesh} in the pathfinding package, along with handling the
 * state of an entity
 *
 * @author Daniel O'Brien
 * @version 1.0
 * @since 1.0
 * */
public abstract class Npc extends GameEntity {
  private ArrayList<Vector2> currentPath = new ArrayList<>();
  private Vector2 targetDirection = new Vector2();
  private NavigationMesh navigationMesh;

  protected States state = States.IDLE;

  protected enum States {
    IDLE,
    NAVIGATING,
    REACHED_DESTINATION,
    ATTACKING_SYSTEM,
    ATTACKING_PLAYER
  }

  public boolean aiEnabled = true;
  protected Timer npcTimer = new Timer();

  public Npc(float x, float y, Texture texture, NavigationMesh navigationMesh) {
    super(x, y, texture);
    this.navigationMesh = navigationMesh;
  }

  /**
   * Update the NPC by stepping it in the direction of its current target.
   *
   * @param world The game world
   * */
  protected void stepTowardsTarget(World world) {
    if (aiEnabled) {
      Vector2 targetCoordinates = currentPath.get(0);
      Vector2 currentDirection = getCurrentDirection();

      // Rotate the entity to face the direction its heading
      rotation = currentDirection.angleDeg();

      boolean entityMoved = false;
      if (currentDirection.x == targetDirection.x && targetDirection.x != 0) {
        // 0.7 makes the player slightly faster, allowing for them to catch up to infiltrators
        position.x += Math.signum(targetCoordinates.x - position.x) * maxSpeed * 0.7;
        entityMoved = true;
      }

      if (currentDirection.y == targetDirection.y && targetDirection.y != 0) {
        position.y += Math.signum(targetCoordinates.y - position.y) * maxSpeed * 0.7;
        entityMoved = true;
      }

      if (!entityMoved) {
        // If the entity hasn't moved, it must have reached its target node.
        // Remove the node and recalculate the current direction to head in.
        currentPath.remove(0);
        if (!currentPath.isEmpty()) {
          targetDirection = getCurrentDirection();
        } else {
          state = States.REACHED_DESTINATION;
        }
      }
    }
  }

  /**
   * Control the state the NPC is in and fire any necessary events when need be.
   *
   * @param world The game world
   * */
  @Override
  public void update(World world) {
    if (aiEnabled) {
      switch (state) {
        case NAVIGATING:
          stepTowardsTarget(world);
          break;
        case REACHED_DESTINATION:
          handleDestinationReached(world);
          break;
        case IDLE:
          break;
        default:
          break;
      }
    }
  }

  /**
   * Update the {@link Npc#currentPath} to a given set of x and y coordinates.
   *
   * @param x The x coordinate to navigate to
   * @param y The y coordinate to navigate to
   * @param world The game world
   * */
  public void updatePath(float x, float y, World world) {
    if (!currentPath.isEmpty()) {
      currentPath.clear();
    }
    currentPath = navigationMesh.generateWorldPathToPoint(position, new Vector2(x, y));
    targetDirection = getCurrentDirection();
  }

  /** 
   * Pick a random system in the game world and navigate towards it.
   *
   * @param world The game world
   * */
  public void navigateToRandomSystem(World world) {
    state = States.NAVIGATING;
    RectangleMapObject system = world.systems.get(
        Utils.randomIntInRange(world.randomNumberGenerator,
          0, world.systems.size() - 1));

    updatePath(system.getRectangle().getX(), system.getRectangle().getY(), world);
  }

  /**
   * Handle the event of the NPC reaching its current destination. For {@link Infiltrator}s this
   * might be to sabotage a system and for {@link Civilian}s this might be to idle for a bit
   * */
  public abstract void handleDestinationReached(World world);

  /**
   * Return a {@link Vector2} representing the direction the NPC is currently heading in.
   *
   * @return A {@link Vector2} representing the direction the NPC is currently heading in.
   * */
  public Vector2 getCurrentDirection() {
    return new Vector2(
        Math.signum(currentPath.get(0).x - position.x),
        Math.signum(currentPath.get(0).y - position.y)
        );
  }
}
