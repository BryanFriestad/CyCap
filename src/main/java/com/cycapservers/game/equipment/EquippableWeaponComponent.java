//package com.cycapservers.game.equipment;
//
//import com.cycapservers.game.Sound;
//import com.cycapservers.game.components.drawing.DrawingComponent;
//
//public abstract class EquippableWeaponComponent extends EquippableComponent{
//	
//	//parameters
//	protected long fire_time;
//	protected long reload_time;
//	protected int max_reloads;
//	protected int reloads_remaining;
//	
//	protected Sound fire_sound;
//	protected Sound cannot_fire_sound;
//	protected Sound reload_sound;
//	protected Sound cannot_reload_sound;
//	
//	//internal
//	protected long last_fire_time;
//	protected boolean is_reloading;
//	protected long last_reload_start_time;
//	
//	public EquippableWeaponComponent(String name, long switchCooldown, DrawingComponent icon, 
//			long fire_rate, long reload_time, int max_reloads, int reloads_remaining, 
//			Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound, Sound cannot_reload_sound) 
//	{
//		super(name, switchCooldown, icon);
//		this.fire_time = fire_rate;
//		this.reload_time = reload_time;
//		this.max_reloads = max_reloads;
//		this.reloads_remaining = reloads_remaining;
//		this.fire_sound = fire_sound;
//		this.cannot_fire_sound = cannot_fire_sound;
//		this.reload_sound = reload_sound;
//		this.cannot_reload_sound = cannot_reload_sound;
//		this.last_fire_time = System.currentTimeMillis();
//		this.is_reloading = false;
//		this.last_reload_start_time = System.currentTimeMillis();
//	}
//	
//	public abstract boolean fire();
//	public abstract boolean reload();
//	/**
//	 * Called when the weapon is to have its ammo refilled
//	 */
//	public void refill()
//	{
//		reloads_remaining = max_reloads;
//	}
//	
//}