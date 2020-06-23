package com.cycapservers.game;

public class PowerUpSpawn extends GridLockedPosition {
	
	private PowerUp slot;
	private long last_spawn_time;

	public PowerUpSpawn(short x, short y) {
		super(x, y);
		slot = null;
		last_spawn_time = System.currentTimeMillis();
	}

	public PowerUp getSlot() {
		return slot;
	}

	public long getLast_spawn_time() {
		return last_spawn_time;
	}

	public void setSlot(PowerUp slot) {
		this.slot = slot;
	}

}
