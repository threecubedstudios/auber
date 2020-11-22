package com.threecubed.auber;

import com.badlogic.gdx.graphics.Color;
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
import com.threecubed.auber.entities.Player;
import com.threecubed.auber.pathfinding.NavigationMesh;
import com.threecubed.auber.screens.GameOverScreen;
import java.util.ArrayList;
import java.util.Arrays;
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
  public Player player;

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
  public static final Color rayColorA = new Color(0.106f, 0.71f, 0.714f, 1f);
  public static final Color rayColorB = new Color(0.212f, 1f, 1f, 0.7f);

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

    /**
     * Return a tile from this enum by a given tile ID.
     *
     * @param id The ID of the {@link Tiles} object to return
     * @return A {@link Tiles} object of the given ID
     * @throws IllegalArgumentException Thrown if no tile could be found with the given ID
     * */
    public static Tiles getTileById(int id) {
      for (Tiles tile : Tiles.values()) {
        if (tile.tileId == id) {
          return tile;
        }
      }
      throw new IllegalArgumentException("Tile of given ID not found.");
    }
  }

  public static final float SYSTEM_BREAK_TIME = 5f;
  public static final float SYSTEM_SABOTAGE_CHANCE = 0.5f;

  /** The distance the infiltrator can see. Default: 5 tiles */
  public static final float INFILTRATOR_SIGHT_RANGE = 80f;

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
   * Increment the number of entities in the brig by 1.
   * */
  public void incrementBrigCount() {
    brigCount += 1;
    if (brigCount >= brigCapacity) {
      game.setScreen(new GameOverScreen(this.game, "YOU WIN"));
    }
  }

  public void updateWorkingSystems(int update) {
    this.workingSystems += update;
    if (this.workingSystems == 0) {
      game.setScreen(new GameOverScreen(this.game, "YOU LOSE"));
    }
  }

  /**
   * Update the sprite of a system to match a new state.
   *
   * @param x The x coordinate of the system object (not the tile)
   * @param y The y coordinate of the system object (not the tile)
   * @param newState The new state of the system
   * */
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

      TiledMapTileLayer foregroundLayer = (TiledMapTileLayer) map.getLayers()
          .get("foreground_layer");
      foregroundLayer.setCell(systemPosition[0], systemPosition[1] + 1, newSystemLight);
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

  /**
   * Return the state of a system, given the coordinates of the system object (not the tile).
   *
   * @param x The x coordinate to check
   * @param y The y coordinate to check
   * @return A {@link SystemStates} representing the state of the system
   * @throws IllegalArgumentException if a standalone system light is at the coordinates provided
   * */
  public SystemStates getSystemState(float x, float y) {
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) World.map.getLayers()
        .get("collision_layer");

    int[] systemPosition = {
      (int) x / collisionLayer.getTileWidth(),
      (int) (y / collisionLayer.getTileHeight()) + 1
      };

    Cell attackedSystemCell = collisionLayer.getCell(systemPosition[0], systemPosition[1]);
    if (attackedSystemCell == null) {
      throw new IllegalArgumentException("No tile at given coordinates");
    }

    Tiles targetTile = Tiles.getTileById(attackedSystemCell.getTile().getId());

    switch (targetTile) {
      case WALL_SYSTEM:
      case STANDALONE_SYSTEM:
        return SystemStates.WORKING;

      case WALL_SYSTEM_ATTACKED:
      case STANDALONE_SYSTEM_ATTACKED:
        return SystemStates.ATTACKED;

      case WALL_SYSTEM_DESTROYED:
      case STANDALONE_SYSTEM_DESTROYED:
        return SystemStates.DESTROYED;
      
      default:
        throw new IllegalArgumentException("Use the coordinates of the System object on the"
                                           .concat("tilemap - not the system tile."));
    }
  }
}
