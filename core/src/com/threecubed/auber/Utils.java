package com.threecubed.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Utils {
  /**
   * Get the mouse coordinates within the game world.
   *
   * @param camera The gamera of the game world
   * @return The X and Y position of the mouse in the game world in the form of a Vector2
   * */
  public static Vector2 getMouseCoordinates(Camera camera) {
    Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(mousePosition);
    return new Vector2(mousePosition.x, mousePosition.y);
  }
}
