package com.threecubed.auber.entities.playerpowerups;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.entities.Player;

public abstract class PlayerPowerUp {

	protected Sprite sprite;

	private Vector2 position;

	protected long nextActivatableTimeMs;
	protected long cooldownMs;
	protected int keyCode;

	protected Player player;

	public PlayerPowerUp(Sprite sprite, Vector2 position, long cooldownMs, int keyCode) {
		this.sprite = sprite;
		this.position = position;
		this.cooldownMs = cooldownMs;
		this.keyCode = keyCode;
	}

	public void render(Batch batch, Camera camera) {
		if (player == null) {
			sprite.setPosition(position.x, position.y);
			sprite.draw(batch);
		}
	}

	public void collect(Player player) {
		this.player = player;
	}

	public void activate() {
		doAction();
		nextActivatableTimeMs = System.currentTimeMillis() + cooldownMs;
	}
	
	public boolean isKey(int keyCode) {
		return this.keyCode == keyCode;
	}

	public boolean canActivate() {
		return System.currentTimeMillis() > nextActivatableTimeMs;
	}

	protected abstract void doAction();

}
