package com.threecubed.auber.entities;

import com.threecubed.auber.World;

public class PowerUp extends GameEntity{

    public PowerUp(float x, float y, World world) {
        super(x, y, world.atlas.createSprite("projectile"));
    }

    @Override
    public void update(World world) {

    }
}
