package com.threecubed.auber.entities.playerpowerups;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class InvisibilityPowerUp extends PlayerPowerUp {

	/**
	 * Constructs the invisibility power up
	 * 
	 * @param sprite
	 * @param position
	 * @param cooldownMs
	 */
	public InvisibilityPowerUp(Sprite sprite, Vector2 position, int cooldownMs, int durationMs) {
		super(sprite, position, cooldownMs, durationMs, Keys.I);
	}

	@Override
	protected void doAction() {
		player.isVisible = false;
	}

	@Override
	protected void reverseAction() {
		player.isVisible = true;
	}

}
