package com.threecubed.auber.entities;

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

    public PowerUp(float x, float y, World world) {
        super(x, y, world.atlas.createSprite("projectile"));
    }

    @Override
    public void update(World world) {

    }
}
