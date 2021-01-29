package com.threecubed.auber;

import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Infiltrator;
import com.threecubed.auber.entities.Player;


/**
 * power ups which change aspects of the game for a period of time 
 * depending on the type of power up
 * activated when the player steps on a specific tile
 * 
 * @author Matt Rogan, Lewis McKenzie
 * @version 1.1
 * @since 1.1
 * */
public class PowerUp extends GameEntity {
    public static enum Type {
        IMMUNITY,
        INVISIBILITY,
        FIREWALL,
        SPEED,
        DETECT,
    }
    private Type powerType;
    private float duration;
    private float timer;
    private boolean active;
    
    public PowerUp(float x, float y, World world, Type powerType) {
        super(x, y, world.atlas.createSprite("projectile"));
        this.powerType = powerType;
        duration = 10f;
        active = false;
    }

    public void activate(World world) {
        active = true;
        timer = duration;
        switch (powerType) {
            case IMMUNITY:
                world.player.immune = true;
                break; 
            case INVISIBILITY:
                world.player.sprite.setAlpha(0.5f);
                break;
            case FIREWALL:
                Infiltrator.canSabotage = false;
                break;
            case SPEED:
                world.player.speed *= 1.5;
                break;
            case DETECT:
                for (GameEntity e:world.getEntities()) {
                    if (e instanceof Infiltrator) {
                        ((Infiltrator) e).expose(world); 
                    }
                }
                break;
        }
    }

    @Override
    public void update(World world) {
        // Assuming timeStep is 1/60 (FPS)
        if (active) {
            timer -= (1/60);

        } else {

            Vector2 playerPosition = world.player.position; // (x,y) component

            int[] tileMapCoords = world.navigationMesh.getTilemapCoordinates(position.x, position.y);
            int[] playerCoords = world.navigationMesh.getTilemapCoordinates(playerPosition.x, playerPosition.y);

            if (tileMapCoords[0] == playerCoords[0] && tileMapCoords[1] == playerCoords[1]) {
                activate(world);

            }

        }
        if (timer <= 0) {
            deactivate(world);
        }
    }

    public void deactivate(World world) {
        active = false;
        switch (powerType) {
            case IMMUNITY:
                world.player.immune = true;
                break; 
            case INVISIBILITY:
                world.player.sprite.setAlpha(1f);
                break;
            case FIREWALL:
                Infiltrator.canSabotage = true;
                break;
            case SPEED:
                world.player.speed /= 1.5;
                break;
            case DETECT:
            for (GameEntity e:world.getEntities()) {
                if (e instanceof Infiltrator) {
                    ((Infiltrator) e).unexpose(world); 
                }
            }
            break;
        }
    }
}
