package com.threecubed.auber.entities;

import com.badlogic.gdx.ai.pfa.Connection;

public class TileConnect implements Connection<TileNode> {
  TileNode fromTile, toTile;
  float cost;

  public TileConnect(TileNode fromTile, TileNode toTile) {
    this.fromTile = fromTile;
    this.toTile = toTile;
    cost = 1;
  }

  @Override
  public float getCost() {
    return cost;
  }

  @Override
  public TileNode getFromNode() {
    return fromTile;
  }

  @Override
  public TileNode getToNode() {
    return toTile;
  }
}