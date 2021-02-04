package com.threecubed.auber.entities.playerpowerups;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ShieldPowerUp extends PlayerPowerUp {

	/**
	 * Creates a shield power up
	 * 
	 * @param sprite
	 * @param position
	 * @param cooldownMs
	 * @param durationMs
	 */
	public ShieldPowerUp(Sprite sprite, Vector2 position, int cooldownMs, int durationMs) {
		super(sprite, position, cooldownMs, durationMs, Keys.Z);
	}

	@Override
	protected void doAction() {
		player.shield.activate();
	}

	@Override
	protected void reverseAction() {
		player.shield.deactivate();
	}

}
