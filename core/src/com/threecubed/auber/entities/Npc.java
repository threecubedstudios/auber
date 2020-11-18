package com.threecubed.auber.entities;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.World;
import com.threecubed.auber.pathfinding.NavigationMesh;



public class NewNpc extends GameEntity {
  private ArrayList<Vector2> currentPath = new ArrayList<>();
  private Vector2 targetDirection = new Vector2();
  private NavigationMesh navigationMesh;

  public NewNpc(float x, float y, Texture texture, NavigationMesh navigationMesh) {
    super(x, y, texture);
    this.navigationMesh = navigationMesh;
  }

  /**
   * Update the NPC by stepping it in the direction of its current target
   *
   * @param world The game world
   * */
  public void update(World world) {
    if (currentPath.size() == 0) {
      updatePath(835f, 751f, world);
    }

    Vector2 targetCoordinates = currentPath.get(0);
    Vector2 currentDirection = getCurrentDirection();

    boolean entityMoved = false;
    if (currentDirection.x == targetDirection.x && targetDirection.x != 0) {
      // 0.9 makes the player slightly faster, allowing for them to catch up to infiltrators
      position.x += Math.signum(targetCoordinates.x - position.x) * maxSpeed * 0.9;
      entityMoved = true;
    }

    if (currentDirection.y == targetDirection.y && targetDirection.y != 0) {
      position.y += Math.signum(targetCoordinates.y - position.y) * maxSpeed * 0.9;
      entityMoved = true;
    }

    if (!entityMoved) {
      // If the entity hasn't moved, it must have reached its target node.
      // Remove the node and recalculate the current direction to head in.
      currentPath.remove(0);
      targetDirection = getCurrentDirection();
    }
  }

  /**
   * Update the {@link NewNpc#currentPath} to a given set of x and y coordinates
   *
   * @param x The x coordinate to navigate to
   * @param y The y coordinate to navigate to
   * */
  public void updatePath(float x, float y, World world) {
    currentPath.clear();
    currentPath = navigationMesh.generateWorldPathToPoint(position, new Vector2(x, y));
    targetDirection = getCurrentDirection();
    System.out.println(currentPath.toString());
  }

  /**
   * Return a {@link Vector2} representing the direction the NPC is currently heading in.
   * */
  public Vector2 getCurrentDirection() {
    //System.out.println(currentPath.get(0).x);
    return new Vector2(
        Math.signum(currentPath.get(0).x - position.x),
        Math.signum(currentPath.get(0).y - position.y)
        );
  }
}
