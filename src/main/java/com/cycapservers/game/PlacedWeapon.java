package com.cycapservers.game;

/**
 * A weapon that places an entity that can be triggered at
 * a later point in time. A trip mine or a 
 * @author Bryan Friestad
 *
 */
public abstract class PlacedWeapon extends Weapon {

	private Entity object;

	public PlacedWeapon(String name, long switchCooldown, Drawable icon, long fire_rate, long reload_time,
			int max_reloads, int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
			Sound cannot_reload_sound, Entity object) {
		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound,
				cannot_fire_sound, reload_sound, cannot_reload_sound);
		this.object = object;
	}

}
