package com.threecubed.auber.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.Utils;
import com.threecubed.auber.World;

/**
 * The power up entity that the player may pick up. Handles searching for player and calling methods for relevant
 * power up.
 *
 * @author Harry Kelly
 * @version 1.0
 * @since 1.0
 * */
public class PowerUp extends GameEntity{

    private boolean used;
    private float playerDetectRadius = 10f;
    public PowerUpType type;

    public enum PowerUpType{
//        SPEED_BOOST,
        REDUCE_CHARGE_TIME,
//        STRONGER_RAY,
//        REDUCE_DAMAGE,
        ESCAPE_CONFUSION,
    }

    /**
     * Initialise the power up item, which will pick a random type.
     *
     *    @param x The x position of the power up
     *    @param y The y position of the power up
     *    @param world The game world
     */
    public PowerUp(float x, float y, World world) {
        super(x, y, world.atlas.createSprite("projectile"));
        this.used = false;
        this.type = PowerUpType.values()[world.randomNumberGenerator.nextInt(PowerUpType.values().length)];
    }

    public PowerUp(World world){
        this(0, 0, world);
        moveToRandomLocation(world);
    }

    /**
     * Get absolute distance between this power up and player
     * @param world The game world
     * @return The absolute distance between this power up and the player.
     */
    private float getPlayerDistance(World world){
        Vector2 playerPos = world.player.position;
        Vector2 thisPos = this.position;
        float deltaX = playerPos.x - thisPos.x;
        float deltaY = playerPos.y - thisPos.y;
        return (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    @Override
    public void update(World world) {
        if (!used && getPlayerDistance(world) < playerDetectRadius){
            used = true;
            world.player.receivePowerUp(type);
        }
    }

    @Override
    public void render(Batch batch, Camera camera) {
        if(!used){
            super.render(batch, camera);
        }
    }

    /**
     * Move the entity to a random location within the world.
     *
     * @param world The game world
     **/
    public void moveToRandomLocation(World world) {
        float[] location = world.spawnLocations.get(Utils.randomIntInRange(
                world.randomNumberGenerator, 0,
                world.spawnLocations.size() - 1));
        position.x = location[0];
        position.y = location[1];
    }
}
