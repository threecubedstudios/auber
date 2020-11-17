package com.threecubed.auber.entities;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class TileHeuristic implements Heuristic<TileNode> {
    
  @Override
  public float estimate(TileNode currentTile, TileNode goalTile) {
    return Vector2.dst(currentTile.pos.x, currentTile.pos.y, goalTile.pos.x, goalTile.pos.y);
  }
}
