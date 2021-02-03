package com.threecubed.auber.entities.playerpowerups;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpeedPowerUp extends PlayerPowerUp {

	/**
	 * Constructs the speed power up
	 * 
	 * @param sprite
	 * @param position
	 * @param cooldownMs
	 * @param durationMs
	 */
	public SpeedPowerUp(Sprite sprite, Vector2 position, int cooldownMs, int durationMs) {
		super(sprite, position, cooldownMs, durationMs, Keys.B);
	}

	@Override
	protected void doAction() {
		player.speed = 0.8f;
	}

	@Override
	protected void reverseAction() {
		player.speed = 0.4f;
	}

}
