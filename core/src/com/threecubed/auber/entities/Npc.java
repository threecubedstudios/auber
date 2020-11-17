package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.threecubed.auber.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Arrays;


public abstract class Npc extends GameEntity {
  protected Random rng = new Random();
  protected Vector2 newPos;

  // The range of times (in ms) that the NPC will wait in it's idle phase
  private final int minWaitTime = 5000;
  private final int maxWaitTime = 10000;

  //The time (in ms) that has to have passed in order for the idle phase to be over
  private long waitTimeEnd = 0;

  protected boolean[][] validPositions;
  private ArrayList<Vector2> path;

  public boolean aiEnabled = true;

  TileNodeGraph tileNodeGraph;
  GraphPath<TileNode> tileNodePath;

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

    validPositions = getNavMesh(map);
    newPos = position; //The position the NPC will move towards when in it's movement phase
    path = new ArrayList<>();
    //createTileGraph()
  }

  /**
   * Generate a navigation mesh from a tile layer, assuming that any tile which isn't null is a
   * valid location.
   *
   * @param map The tilemap to use TODO: Replace this with a map layer
   * @return A 2d boolean array of valid tiles
   * */
  
   public void createTileGraph() {
     tileNodeGraph = new TileNodeGraph();
     ArrayList<TileNode> tileNodes = new ArrayList<TileNode>();

     for (int y = 0; y < validPositions.length; y++) {
      for (int x = 0; x < validPositions.length; x++) {
        if (validPositions[y][x]) {
          //tileNodes.add(new TileNode(x, y));
          tileNodeGraph.addTile(new TileNode(x, y));

          
        }
      }
    }
    //tileNodeGraph.addTile(tileNodes);


   }

   public boolean[][] getNavMesh(TiledMap map) {
    TiledMapTileLayer backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background_layer");
    boolean[][] temp = new boolean[backgroundLayer.getWidth()][backgroundLayer.getWidth()];

    for (int y = 0; y < temp.length; y++) {
      for (int x = 0; x < temp.length; x++) {
        temp[y][x] = backgroundLayer.getCell(x, y) != null;
      }
    }

    return temp;
  }

  @Override
  public void update(World world) {

    if (waitTimeEnd < System.currentTimeMillis()) {
      //Idle Phase
      if (position.equals(newPos)) {
        waitTimeEnd = generateWaitTime();
        newPos = generateNewPos();
      } 
      else {
        //Movement Phase
        if (path.isEmpty()) {
          path = generatePath(new Vector2((float)Math.floor(position.x / 16), (float)Math.floor(position.y / 16)), new Vector2((float)Math.floor(newPos.x / 16), (float)Math.floor(newPos.y / 16)));
        } else {
          
          
          Vector2 diff = new Vector2((path.get(0).x * 16) - position.x, (path.get(0).y * 16) - position.y);

          if (diff.x > 0) {
            diff.x = 1;
          }
          else if (diff.x < 0) {
            diff.x = -1;
          }

          if (diff.y > 0) {
            diff.y = 1;
          }
          else if (diff.y < 0) {
            diff.y = -1;
          }

          position = new Vector2(position.x + diff.x, position.y + diff.y);

          if (position.x == (path.get(0).x * 16) && position.y == (path.get(0).y * 16)) {
            path.remove(0);
          }
        }
      }
    }


  }

  private long generateWaitTime() {
    return System.currentTimeMillis() + minWaitTime + rng.nextInt(maxWaitTime - minWaitTime);
  }

  private Vector2 generateNewPos() {
    Vector2 temp = new Vector2(
        (position.x / 16) + rng.nextInt(20) - 10,
        (position.y / 16) + rng.nextInt(20) - 10
        );
    while (!validPositions[(int) temp.y][(int) temp.x] || !inBounds(temp, 0, 0, validPositions.length, validPositions.length)) {
      temp = new Vector2(
        (position.x / 16) + rng.nextInt(20) - 10,
        (position.y / 16) + rng.nextInt(20) - 10
        );
    }
    return new Vector2(temp.x * 16, temp.y * 16);
  }

  private ArrayList<Vector2> generatePath(Vector2 startPosition, Vector2 endPosition) {
    
    Comparator<Node> customComparator = new Comparator<Node>() {
      public int compare(Node n1, Node n2) {
        return Float.compare(n1.getHeur(), n2.getHeur());
      }
    };
    PriorityQueue<Node> fringe = new PriorityQueue<>(customComparator);
    ArrayList<Vector2> visited = new ArrayList<>();
    Node node;
    float pathCost, totalCost;

    fringe.add(new Node(startPosition, new ArrayList<Vector2>(), 0,  generateHeur(startPosition, endPosition)));
    //fringe.peek().addToPath(startPosition);

    while(!fringe.isEmpty())
    {
      node = new Node(fringe.poll());

      if (!visited.contains(node.getPos())) {
        visited.add(node.getPos());

        if (node.isEnd(endPosition)) {
          return node.getPath();
        }
        
        for (Vector2 n : getNeighbours(node.getPos())) {
          if (!visited.contains(n)) {
            fringe.add(new Node(n, new ArrayList<Vector2>(node.getPath()), node.getPathCost() + 1,  generateHeur(n, endPosition) + node.getPathCost() + 1));
            //fringe.peek().addToPath(n);
          }
        }
      }
    }
    return visited;

    /*ArrayList<Vector2> visited = new ArrayList<Vector2>();
    ArrayList<Vector2> neighbours = new ArrayList<Vector2>();
    ArrayList<Vector2> history = new ArrayList<Vector2>();
    Comparator<float[]> customComparator = new Comparator<float[]>() {
      public int compare(float[] f1, float[] f2) {
        return Float.compare(f1[2], f2[2]);
      }
    };
    PriorityQueue<float[]> fringe = new PriorityQueue<float[]>(customComparator);
    fringe.add(new float[] {position.x, position.y, generateHeur(position)});
    float[] node;

    while (!fringe.isEmpty()) {
      node = fringe.poll();
      if (!visited.contains(new Vector2(node[0], node[1]))) {
        visited.add(new Vector2(node[0], node[1]));

        if (node[2] == 0) {
          return visited;
        }

        neighbours = getNeighbours(node);
        for (Vector2 n : neighbours) {
          if (!visited.contains(new Vector2(n.x * 16, n.y * 16)) && validPositions[(int) n.x][(int) n.y]) {
            fringe.add(new float[] {n.x * 16, n.y * 16, generateHeur(n)});
          }
        }
      }
    }
    return visited;*/
  }


  private float generateHeur(Vector2 startPosition, Vector2 endPosition) {
    return Vector2.dst(startPosition.x, startPosition.y, endPosition.x, endPosition.y);
  }

  private ArrayList<Vector2> getNeighbours(Vector2 node) {
    ArrayList<Vector2> neighbours = new ArrayList<Vector2>();
    List<Vector2> adjacencies = Arrays.asList(new Vector2(0, 1), new Vector2(1, 0), new Vector2(0, -1), new Vector2(-1, 0));
    Vector2 temp;
    
    for (Vector2 v : adjacencies) {
      temp = new Vector2((float)Math.floor((node.x) + v.x), (float)Math.floor((node.y) + v.y));
      if (inBounds(temp, 0, 0, validPositions.length, validPositions.length) && validPositions[(int)temp.x][(int)temp.y]) {
        neighbours.add(new Vector2(temp.x, temp.y));
      }
    }

    return neighbours;
  }

  private boolean inBounds(Vector2 pos, int minx, int miny, int maxx, int maxy) {
    
    if (pos.x > minx && pos.x < maxx && pos.y > miny && pos.y < maxy) {
      return true;
    }
    return false;
  }
}
