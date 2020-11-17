package com.threecubed.auber.entities;

import com.badlogic.gdx.math.Vector2;

public class TileNode {
  Vector2 pos;

  /** Index used by the A* algorithm. Keep track of it so we don't have to recalculate it later. */
  int index;

  public TileNode(float x, float y) {
    pos = new Vector2(x, y);
  }

  public void setIndex(int index){
    this.index = index;
  }
}