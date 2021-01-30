package com.threecubed.auber.entities;

import com.badlogic.gdx.math.Vector2;
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

    private float playerDetectRadius = 10f;
    public PowerUp(float x, float y, World world) {
        super(x, y, world.atlas.createSprite("projectile"));
    }

    private float getPlayerDistance(World world){
        Vector2 playerPos = world.player.position;
        Vector2 thisPos = this.position;
        float deltaX = playerPos.x - thisPos.x;
        float deltaY = playerPos.y - thisPos.y;
        return (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    @Override
    public void update(World world) {

        if (getPlayerDistance(world) < playerDetectRadius){
        }
    }
}
