package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
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
    NAVIGATING
  }

  public boolean aiEnabled = true;

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
      if (currentPath.size() == 0) {
        updatePath(835f, 751f, world);
      }

      Vector2 targetCoordinates = currentPath.get(0);
      Vector2 currentDirection = getCurrentDirection();

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
        }
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
    currentPath.clear();
    currentPath = navigationMesh.generateWorldPathToPoint(position, new Vector2(x, y));
    targetDirection = getCurrentDirection();
    System.out.println(currentPath.toString());
  }

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
