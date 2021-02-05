package com.threecubed.auber.entities.playerpowerups;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class StunShotPowerUp extends PlayerPowerUp {

	public StunShotPowerUp(Sprite sprite, Vector2 position, int cooldownMs, int keyCode) {
		super("Stun Shot", sprite, position, cooldownMs, -1, Keys.B);
	}

	@Override
	protected void doAction() {
		player.isStunShot = true;
	}

	@Override
	protected void reverseAction() {
	}

}
