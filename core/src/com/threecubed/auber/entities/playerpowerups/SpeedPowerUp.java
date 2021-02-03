package com.threecubed.auber.entities.playerpowerups;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpeedPowerUp extends PlayerPowerUp {
	/**
	 * How long the speed will last
	 */
	protected static final long durationMs = 5000;

	/**
	 * Constructs the speed power up
	 * 
	 * @param sprite
	 * @param position
	 * @param cooldownMs
	 */
	public SpeedPowerUp(Sprite sprite, Vector2 position, long cooldownMs) {
		super(sprite, position, cooldownMs, Keys.B);
	}

	@Override
	protected void doAction() {
		player.speed = 0.8f;

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				player.speed = 0.4f;
			}
		}, durationMs);
	}

}
