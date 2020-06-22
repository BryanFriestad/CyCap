package com.cycapservers.game;

public abstract class PowerUp extends Item {
	
	private int max_uses;
	private int uses_remaining;
	private long duration;
	
	//internal
	protected long last_activate_time;
	
	public PowerUp(String id, Drawable model, Collider c, int collision_priority, Game g, String name, int max_uses, long duration) {
		super(id, model, c, collision_priority, g, name);
		this.max_uses = max_uses;
		this.uses_remaining = max_uses;
		this.duration = duration;
		this.last_activate_time = System.currentTimeMillis();
	}

	/**
	 * Updates important information about the power up.
	 * @return boolean: whether or not the powerup is finished
	 */
	public abstract boolean update();

	@Override
	public abstract boolean use();
	
	@Override
	public abstract String toJSONString();

	public int getMax_uses() {
		return max_uses;
	}

	public int getUses_remaining() {
		return uses_remaining;
	}

	public long getDuration() {
		return duration;
	}
	
}
