package com.threecubed.auber.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.threecubed.auber.Utils;

public abstract class Npc extends GameEntity {
    protected Random rng = new Random();
    protected Vector2 newPos;
    private int minWaitTime, maxWaitTime, waitTimeEnd;
    private boolean[][] validPositions;
    private ArrayList<Vector2> path;
    

    public Npc(float x, float y, Texture texture, TiledMap map) {
        super(x, y, texture);
        
        newPos = new Vector2(x, y); //The position the NPC will move towards when in it's movement phase
        minWaitTime = 5000; //The shortest time (in ms) that the NPC will wait in it's idle phase
        maxWaitTime = 10000; //The longest time (in ms) that the NPC will wait in it's idle phase
        waitTimeEnd = 0; //The time (in ms) that has to have passed in order for the idle phase to be over
        validPositions = getBackground(map);
        path = new ArrayList<Vector2>();
    }

    public boolean[][] getBackground(TiledMap map)
    {
        TiledMapTileLayer backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background_layer");
        boolean[][] temp = new boolean[backgroundLayer.getWidth()][backgroundLayer.getWidth()];
        
        for (int Y = 0; Y < temp.length; Y++) {
            for (int X = 0; X < temp.length; X++) {
                temp[X][Y] = backgroundLayer.getCell(X, Y) != null; 
            }
        }

        return temp;
    }
    
    public void update(TiledMap map, Camera camera, List<GameEntity> entities) {

        if (waitTimeEnd < System.currentTimeMillis()) {
            //Idle Phase
            if (position == newPos) {
                waitTimeEnd = generateWaitTime();
                newPos = generateNewPos();
            }

            //Movement Phase
            else
            {
                if (path.isEmpty()) {
                    path = generatePath();
                }
                else {
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
        return (int)System.currentTimeMillis() + minWaitTime + rng.nextInt(maxWaitTime - minWaitTime);
    }

    private Vector2 generateNewPos() {
        Vector2 temp = new Vector2(rng.nextInt(validPositions.length), rng.nextInt(validPositions.length));
        while (!validPositions[(int)temp.x][(int)temp.y]) {
            temp = new Vector2(rng.nextInt(validPositions.length), rng.nextInt(validPositions.length));
        }
        return temp;
    }

    private ArrayList<Vector2> generatePath() {
        ArrayList<Vector2> visited = new ArrayList<Vector2>();
        ArrayList<Vector2> neighbours = new ArrayList<Vector2>();
        Comparator<float[]> customComparator = new Comparator<float[]>() {
            public int compare(float[] f1, float[] f2)
            {
                return Float.compare(f1[2], f2[2]);
            }
        };
        PriorityQueue<float[]> fringe = new PriorityQueue<float[]>(customComparator);
        fringe.add(new float[] {position.x, position.y, generateHeur()});
        float[] node;
        


        while (!fringe.isEmpty()) {

            node = fringe.poll();
            if (!visited.contains(new Vector2(node[0], node[1]))){
                visited.add(new Vector2(node[0], node[1]));

                if (node[2] == 0) {
                    return visited;
                }

                neighbours = getNeighbours();
                for (Vector2 n : neighbours) {
                    if (!visited.contains(n) && validPositions[(int)n.x][(int)n.y]) {
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
        ArrayList<Vector2> temp = new ArrayList<Vector2>();

        temp.add(new Vector2(position.x + 1, position.y));
        temp.add(new Vector2(position.x, position.y + 1));
        temp.add(new Vector2(position.x - 1, position.y));
        temp.add(new Vector2(position.x, position.y - 1));

        return temp;
    }
}
