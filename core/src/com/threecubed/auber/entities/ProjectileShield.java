package com.threecubed.auber.entities;

import com.badlogic.gdx.math.Circle;
import com.threecubed.auber.World;

public class ProjectileShield {
	/**
	 * The radius of the shielding area
	 */
	private int range;

	/**
	 * True if the shield is activated, False otherwise
	 */
	private boolean isActive = false;

	/**
	 * Creates a shield with a certain range
	 * 
	 * @param range
	 */
	public ProjectileShield(int range) {
		this.range = range;
	}

	/**
	 * Activates the shield
	 */
	public void activate() {
		isActive = true;
	}

	/**
	 * Deactivates the shield
	 */
	public void deactivate() {
		isActive = false;
	}

	/**
	 * Performs the shield's activities
	 * 
	 * @param player
	 * @param world
	 */
	public void update(Player player, World world) {
		if (isActive) {
			Circle exposeArea = new Circle(player.position, range);

			for (GameEntity entity : world.getEntities()) {
				if (entity instanceof Projectile && exposeArea.contains(entity.position)) {
					world.queueEntityRemove(entity);
				}
			}
		}
	}

}
