package com.threecubed.auber.pathfinding;

import java.util.Arrays;


public class PathNode {
  public int[] position;
  public float heuristic;
  public PathNode parent;
  public int pathCost = 0;

  public PathNode(int[] position, PathNode parent, int[] destination) {
    this.position = position;
    this.parent = parent;

    if (parent != null) {
      pathCost = parent.pathCost + 1;
    }

    heuristic = (int) (NavigationMesh.getEuclidianDistance(position, destination)) + pathCost;
  }

  public boolean equals(Object other) {
    if (other instanceof PathNode) {
      PathNode otherNode = (PathNode) other;
      return Arrays.equals(position, otherNode.position);
    }
    return false;
  }

  public String toString() {
    return Arrays.toString(position);
  }
}
