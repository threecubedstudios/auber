package com.threecubed.auber.entities.playerpowerups;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class InvisibilityPowerUp extends PlayerPowerUp {

	/**
	 * How long the invisibility will last
	 */
	protected static final long durationMs = 5000;
	
	/**
	 * Constructs the invisibility power up
	 * 
	 * @param sprite
	 * @param position
	 * @param cooldownMs
	 */
	public InvisibilityPowerUp(Sprite sprite, Vector2 position, long cooldownMs) {
		super(sprite, position, durationMs + cooldownMs, Keys.I);
	}

	@Override
	protected void doAction() {
		player.isVisible = false;
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				player.isVisible = true;
			}
		}, durationMs);
	}

}
