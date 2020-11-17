package com.threecubed.auber.entities;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public class TileNodeGraph implements IndexedGraph<TileNode>{
    
    TileHeuristic tileHeuristic = new TileHeuristic();
    Array<TileNode> tiles = new Array<>();
    Array<TileConnect> connections = new Array<>();

    ObjectMap<TileNode, Array<Connection<TileNode>>> tileMap = new ObjectMap<>();

    private int lastNodeIndex = 0;

    public void addTile(TileNode tile){
        tile.index = lastNodeIndex;
        lastNodeIndex++;
    
        tiles.add(tile);
      }
    
    public void addTile(ArrayList<TileNode> tiles){
      
        for (TileNode tile : tiles) {
          tile.index = lastNodeIndex;
          lastNodeIndex++;
    
          this.tiles.add(tile);
        }
    }

      public void connectTiles(TileNode fromTile, TileNode toTile){
        TileConnect tileConnect = new TileConnect(fromTile, toTile);
        if(!tileMap.containsKey(fromTile)){
          tileMap.put(fromTile, new Array<Connection<TileNode>>());
        }
        tileMap.get(fromTile).add(tileConnect);
        connections.add(tileConnect);
      }
    
      public GraphPath<TileNode> findPath(TileNode startTile, TileNode goalTile){
        GraphPath<TileNode> tilePath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startTile, goalTile, tileHeuristic, tilePath);
        return tilePath;
      }
    
      @Override
      public int getIndex(TileNode node) {
        return node.index;
      }
    
      @Override
      public int getNodeCount() {
        return lastNodeIndex;
      }
    
      @Override
      public Array<Connection<TileNode>> getConnections(TileNode fromNode) {
        if(tileMap.containsKey(fromNode)){
          return tileMap.get(fromNode);
        }
    
        return new Array<>(0);
      }
}
