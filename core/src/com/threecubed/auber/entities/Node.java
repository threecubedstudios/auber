package com.threecubed.auber.entities;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Node {

  private Vector2 position;
  private ArrayList<Vector2> path;
  private float pathCost, heuristic;

  public Node(Vector2 position, ArrayList<Vector2> path, float pathCost, float heuristic) {
    
    this.position = new Vector2(position);
    this.path = new ArrayList<>(path);
    this.pathCost = pathCost;
    this.heuristic = heuristic;

    addToPath(this.position);

  }

  public Node(Node otherNode) {

    this.position = new Vector2(otherNode.position);
    this.path = new ArrayList<>(otherNode.path);
    this.pathCost = otherNode.pathCost;
    this.heuristic = otherNode.heuristic;
  }

  public float getHeur() { return heuristic; }

  public float getPathCost() { return pathCost; }

  public ArrayList<Vector2> getPath() { return path; }

  public Vector2 getPos() { return position; }

  public void addToPath(Vector2 newPos) { path.add(newPos); }

  public boolean isEnd(Vector2 endPos) { return position.equals(endPos); }
}
