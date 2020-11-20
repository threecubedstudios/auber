package com.threecubed.auber;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.pathfinding.NavigationMesh;
import com.threecubed.auber.screens.GameOverScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The world class stores information related to what is happening within the game world.
 * It should only be used within the GameScreen screen.
 *
 * @author Daniel O'Brien
 * @version 1.0
 * @since 1.0
 * */
public class World {
  private AuberGame game;

  private List<GameEntity> entities = new ArrayList<>();

  public OrthographicCamera camera = new OrthographicCamera();

  public static final TiledMap map = new TmxMapLoader().load("map.tmx");
  public static final TiledMapTileSet tileset = map.getTileSets().getTileSet(0);

  public OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);

  public ArrayList<RectangleMapObject> systems = new ArrayList<>();

  public final Random randomNumberGenerator = new Random();

  // Navigation mesh for AI to use
  public final NavigationMesh navigationMesh = new NavigationMesh(
      (TiledMapTileLayer) map.getLayers().get("navigation_layer")
      );

  public float auberTeleporterCharge = 0f;
  public static final float AUBER_CHARGE_RATE = 0.05f;
  public static final float AUBER_RAY_TIME = 0.25f;

  /** Coordinates for the bottom left and top right tiles of the brig. */
  public static final float[][] BRIG_BOUNDS = {{240f, 608f}, {352f, 640f}};

  // IDs of layers that should be rendered behind entities
  public final int[] backgroundLayersIds = {
    map.getLayers().getIndex("background_layer"),
    };

  // IDs of layers that should be rendered infront of entities
  public final int[] foregroundLayersIds = {
    map.getLayers().getIndex("foreground_layer"),
    map.getLayers().getIndex("collision_layer")
    };


  public static enum Tiles {
    WALL_SYSTEM(38),
    STANDALONE_SYSTEM(62),
    STANDALONE_SYSTEM_LIGHT(50),

    WALL_SYSTEM_ATTACKED(40),
    STANDALONE_SYSTEM_ATTACKED(64),
    STANDALONE_SYSTEM_LIGHT_ATTACKED(52),

    WALL_SYSTEM_DESTROYED(42),
    STANDALONE_SYSTEM_DESTROYED(66),
    STANDALONE_SYSTEM_LIGHT_DESTROYED(54);



    public final int tileId;

    Tiles(int tileId) {
      this.tileId = tileId;
    }

    /**
     * Return a cell object for a given tile type via its tileId.
     * */
    public Cell getCell() {
      Cell output = new Cell();
      output.setTile(tileset.getTile(tileId));
      return output;
    }

  }

  public static final float SYSTEM_BREAK_TIME = 5f;

  public static enum SystemStates {
    WORKING,
    ATTACKED,
    DESTROYED
  }

  /**
   * Initialise the game world.
   *
   * @param game The game object.
   * */
  public World(AuberGame game) {
    // Configure the camera
    camera.setToOrtho(false, 480, 270);
    camera.update();

    this.game = game;

    MapObjects objects = map.getLayers().get("object_layer").getObjects();
    for (MapObject object : objects) {
      if (object instanceof RectangleMapObject) {
        RectangleMapObject rectangularObject = (RectangleMapObject) object;
        switch (rectangularObject.getProperties().get("type", String.class)) {
          case "system":
            systems.add(rectangularObject);
            break;
          default:
            break;
        }
      }
    }
  }

  public List<GameEntity> getEntities() {
    return entities;
  }

  public void addEntity(GameEntity entity) {
    entities.add(entity);
  }

  public void removeEntity(GameEntity entity) {
    entities.remove(entity);
  }

  public void removeEntities(List<GameEntity> entities) {
    entities.removeAll(entities);
  }

  /**
   * Update the sprite of a system to match a new state.
   *
   * @param x The x coordinate of the system
   * @param y The y coordinate of the system
   * @param newState The new state of the system
   **/
  public void updateSystemState(float x, float y, SystemStates newState) {
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) World.map.getLayers()
        .get("collision_layer");

    int[] systemPosition = {
      (int) x / collisionLayer.getTileWidth(),
      (int) (y / collisionLayer.getTileHeight()) + 1
      };

    Cell attackedSystemCell = collisionLayer.getCell(systemPosition[0], systemPosition[1]);
    int targetTileId = attackedSystemCell.getTile().getId();

    if (targetTileId == World.Tiles.STANDALONE_SYSTEM.tileId
        || targetTileId == World.Tiles.STANDALONE_SYSTEM_ATTACKED.tileId
        || targetTileId == World.Tiles.STANDALONE_SYSTEM_DESTROYED.tileId) {
      Cell newSystem;
      Cell newSystemLight;

      switch (newState) {
        case WORKING:
          newSystem = World.Tiles.STANDALONE_SYSTEM.getCell();
          newSystemLight = World.Tiles.STANDALONE_SYSTEM_LIGHT.getCell();
          break;
        case ATTACKED:
          newSystem = World.Tiles.STANDALONE_SYSTEM_ATTACKED.getCell();
          newSystemLight = World.Tiles.STANDALONE_SYSTEM_LIGHT_ATTACKED.getCell();
          break;
        case DESTROYED:
          newSystem = World.Tiles.STANDALONE_SYSTEM_DESTROYED.getCell();
          newSystemLight = World.Tiles.STANDALONE_SYSTEM_LIGHT_DESTROYED.getCell();
          break;
        default:
          // This line will never fire, However it needs to be here otherwise Java
          // will think that theres a chance newSystem and newSystemLight may not have been
          // initiated even though every enum state is covered
          newSystem = new Cell();
          newSystemLight = new Cell();
          return;
      }
      collisionLayer.setCell(systemPosition[0], systemPosition[1], newSystem);
      collisionLayer.setCell(systemPosition[0], systemPosition[1] + 1, newSystemLight);
    } else {
      Cell newSystem;
      switch (newState) {
        case WORKING:
          newSystem = World.Tiles.WALL_SYSTEM.getCell();
          break;
        case ATTACKED:
          newSystem = World.Tiles.WALL_SYSTEM_ATTACKED.getCell();
          break;
        case DESTROYED:
          newSystem = World.Tiles.WALL_SYSTEM_DESTROYED.getCell();
          break;
        default:
          // See above
          return;
      }
      collisionLayer.setCell(systemPosition[0], systemPosition[1], newSystem);
    }

    if (newState == SystemStates.DESTROYED) {
      for (RectangleMapObject system : systems) {
        if (system.getRectangle().getX() == x
            && system.getRectangle().getY() == y) {
          systems.remove(system);
          break;
        }
      }
    }
  }
}
