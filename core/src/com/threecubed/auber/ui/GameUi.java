package com.threecubed.auber.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.World;


public class GameUi {
  private static final int CHARGE_METER_WIDTH = 20;
  private static final int CHARGE_METER_MAX_HEIGHT = 100;
  private static final Vector2 CHARGE_METER_POSITION = new Vector2(50f, 50f);

  private static final Vector2 HEALTH_WARNINGS_POSITION = new Vector2(250f, 50f);

  private static final Vector2 SYSTEM_WARNINGS_POSITION = new Vector2(1750f, 50f);

  private ShapeRenderer shapeRenderer = new ShapeRenderer();
  private BitmapFont uiFont = new BitmapFont();

  /**
   * Render the different elements of the UI to the screen.
   *
   * @param world The game world.
   * */
  public void render(World world, SpriteBatch screenBatch) {
    drawChargeMeter(world, screenBatch);
    drawHealthWarnings(world, screenBatch);
    drawSystemWarnings(world, screenBatch);
  }

  /**
   * Return the height of the charge meter based upon Auber's current teleporter charge.
   *
   * @param teleporterCharge The current charge of the teleporter ray
   * @return A float representing the height of the charge bar UI element
   * */
  private float calculateChargeMeterHeight(float teleporterCharge) {
    return teleporterCharge * CHARGE_METER_MAX_HEIGHT;
  }

  /**
   * Draw the teleporter charge meter.
   *
   * @param world The game world
   * @param screenBatch The batch to draw to
   * */
  private void drawChargeMeter(World world, SpriteBatch screenBatch) {
    screenBatch.begin();
    uiFont.draw(screenBatch, "Teleporter Ray Charge",
        CHARGE_METER_POSITION.x,
        CHARGE_METER_POSITION.y + (CHARGE_METER_MAX_HEIGHT / 2));
    screenBatch.end();

    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.rect(CHARGE_METER_POSITION.x + 160f, CHARGE_METER_POSITION.y, CHARGE_METER_WIDTH,
        calculateChargeMeterHeight(world.auberTeleporterCharge));
    shapeRenderer.end();
  }

  /**
   * Draw any health status warnings for Auber to the screen.
   *
   * @param world The game world
   * @param screenBatch The batch to draw to
   * */
  private void drawHealthWarnings(World world, SpriteBatch screenBatch) {
    screenBatch.begin();
    uiFont.setColor(Color.RED);
    if (world.player.confused) {
      uiFont.draw(screenBatch, "CONFUSED", HEALTH_WARNINGS_POSITION.x, HEALTH_WARNINGS_POSITION.y);
    }
    uiFont.setColor(Color.WHITE);
    screenBatch.end();
  }

  /**
   * Draw the state of each system to the screen.
   *
   * @param world The world object
   * @param screenBatch The batch to draw to
   * */
  private void drawSystemWarnings(World world, SpriteBatch screenBatch) {
    screenBatch.begin();
    int offset = 0;
    for (RectangleMapObject system : world.systems) {
      if (system.getProperties().get("type").equals("system")) {
        String systemName = system.getName();

        // No need for DESTROYED case as when system destroyed, removed from systems list
        switch (world.getSystemState(system)) {
          case WORKING:
            uiFont.setColor(Color.GREEN);
            break;
          case ATTACKED:
            uiFont.setColor(Color.RED);
            break;
          default:
            break;
        }

        uiFont.draw(screenBatch, systemName, SYSTEM_WARNINGS_POSITION.x,
            SYSTEM_WARNINGS_POSITION.y + offset);
        offset += 20f;

      }
    }
    uiFont.setColor(Color.WHITE);
    screenBatch.end();
  }
}
