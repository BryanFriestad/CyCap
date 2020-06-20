package com.cycapservers.game;

public abstract class Weapon extends Equipment{
	
	//parameters
	private long fire_rate;
	private long reload_time;
	private int max_reloads;
	private int reloads_remaining;
	
	private Sound fire_sound;
	private Sound cannot_fire_sound;
	private Sound reload_sound;
	private Sound cannot_reload_sound;
	
	public Weapon(String name, long switchCooldown, Drawable icon, 
			long fire_rate, long reload_time, int max_reloads, int reloads_remaining, 
			Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound, Sound cannot_reload_sound) {
		super(name, switchCooldown, icon);
		this.fire_rate = fire_rate;
		this.reload_time = reload_time;
		this.max_reloads = max_reloads;
		this.reloads_remaining = reloads_remaining;
		this.fire_sound = fire_sound;
		this.cannot_fire_sound = cannot_fire_sound;
		this.reload_sound = reload_sound;
		this.cannot_reload_sound = cannot_reload_sound;
	}
	
	public abstract boolean fire();
	public abstract boolean reload();
	/**
	 * Called when the weapon is to have its ammo refilled
	 */
	public abstract void refill();
	
}