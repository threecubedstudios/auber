package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;


public abstract class Npc extends GameEntity {
  protected Random rng = new Random();
  protected Vector2 newPos;

  // The range of times (in ms) that the NPC will wait in it's idle phase
  private final int minWaitTime = 5000;
  private final int maxWaitTime = 10000;

  //The time (in ms) that has to have passed in order for the idle phase to be over
  private int waitTimeEnd = 0;

  private boolean[][] validPositions;
  private ArrayList<Vector2> path;

  public boolean aiEnabled = true;

  /**
   * Initialise an NPC at a set of given coordinates with a given texture.
   *
   * @param x The x coordinate to initiate the NPC at
   * @param y The y coordinate to initiate the NPC at
   * @param texture The texture to use as the NPC sprite
   * @param map The tilemap of the game world
   * */
  public Npc(float x, float y, Texture texture, TiledMap map) {
    super(x, y, texture);

    newPos = new Vector2(x, y); //The position the NPC will move towards when in it's movement phase
    validPositions = getNavMesh(map);
    path = new ArrayList<Vector2>();
  }

  /**
   * Generate a navigation mesh from a tile layer, assuming that any tile which isn't null is a
   * valid location.
   *
   * @param map The tilemap to use TODO: Replace this with a map layer
   * @return A 2d boolean array of valid tiles
   * */
  public boolean[][] getNavMesh(TiledMap map) {
    TiledMapTileLayer backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background_layer");
    boolean[][] temp = new boolean[backgroundLayer.getWidth()][backgroundLayer.getWidth()];

    for (int y = 0; y < temp.length; y++) {
      for (int x = 0; x < temp.length; x++) {
        temp[x][y] = backgroundLayer.getCell(x, y) != null;
      }
    }

    return temp;
  }

  @Override
  public void update(World world) {
    if (waitTimeEnd < System.currentTimeMillis()) {
      //Idle Phase
      if (position == newPos) {
        waitTimeEnd = generateWaitTime();
        newPos = generateNewPos();
      } else {
        //Movement Phase
        if (path.isEmpty()) {
          path = generatePath();
        } else {
          Vector2 diff = new Vector2(path.get(0).x - position.x, position.y - path.get(0).y);

          diff.x = diff.x > 0 ? 1 : 0;
          diff.y = diff.y > 0 ? 1 : 0;

          position = diff;

          if (position == path.get(0)) {
            path.remove(0);
          }
        }
      }
    }


  }

  private int generateWaitTime() {
    return (int) System.currentTimeMillis() + minWaitTime + rng.nextInt(maxWaitTime - minWaitTime);
  }

  private Vector2 generateNewPos() {
    Vector2 temp = new Vector2(
        rng.nextInt(validPositions.length),
        rng.nextInt(validPositions.length)
        );
    while (!validPositions[(int) temp.x][(int) temp.y]) {
      temp = new Vector2(rng.nextInt(validPositions.length), rng.nextInt(validPositions.length));
    }
    return temp;
  }

  private ArrayList<Vector2> generatePath() {
    ArrayList<Vector2> visited = new ArrayList<Vector2>();
    ArrayList<Vector2> neighbours = new ArrayList<Vector2>();
    Comparator<float[]> customComparator = new Comparator<float[]>() {
      public int compare(float[] f1, float[] f2) {
        return Float.compare(f1[2], f2[2]);
      }
    };
    PriorityQueue<float[]> fringe = new PriorityQueue<float[]>(customComparator);
    fringe.add(new float[] {position.x, position.y, generateHeur()});
    float[] node;

    while (!fringe.isEmpty()) {
      node = fringe.poll();
      if (!visited.contains(new Vector2(node[0], node[1]))) {
        visited.add(new Vector2(node[0], node[1]));

        if (node[2] == 0) {
          return visited;
        }

        neighbours = getNeighbours();
        for (Vector2 n : neighbours) {
          if (!visited.contains(n) && validPositions[(int) n.x][(int) n.y]) {
            fringe.add(new float[] {n.x, n.y, generateHeur()});
          }
        }
      }
    }
    return visited;
  }

  private float generateHeur() {
    return Math.abs((position.y - newPos.y) / (position.x - newPos.x));
  }

  private ArrayList<Vector2> getNeighbours() {
    ArrayList<Vector2> neighbours = new ArrayList<Vector2>();

    neighbours.add(new Vector2(position.x + 1, position.y));
    neighbours.add(new Vector2(position.x, position.y + 1));
    neighbours.add(new Vector2(position.x - 1, position.y));
    neighbours.add(new Vector2(position.x, position.y - 1));

    return neighbours;
  }
}
