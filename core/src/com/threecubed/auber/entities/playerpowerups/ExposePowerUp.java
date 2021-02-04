package com.threecubed.auber.entities.playerpowerups;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Infiltrator;

public class ExposePowerUp extends PlayerPowerUp {

	/**
	 * The world of the game
	 */
	private World world;
	
	/**
	 * The radius of the exposing area
	 */
	private int range;

	/**
	 * The constructor of the expose power up
	 * 
	 * @param sprite
	 * @param position
	 * @param cooldownMs
	 * @param world
	 * @param range
	 */
	public ExposePowerUp(Sprite sprite, Vector2 position, int cooldownMs, World world, int range) {
		super(sprite, position, cooldownMs, -1, Keys.Z);
		this.world = world;
		this.range = range;
	}

	@Override
	protected void doAction() {
		Circle exposeArea = new Circle(player.position, range);

		for (GameEntity entity : world.getEntities()) {
			if (entity instanceof Infiltrator && exposeArea.contains(entity.position)) {
				Infiltrator infiltrator = (Infiltrator) entity;

				if (!infiltrator.exposed) {
					infiltrator.handleTeleporterShot(world);
				}
			}
		}
	}

	@Override
	protected void reverseAction() {
	}

}
