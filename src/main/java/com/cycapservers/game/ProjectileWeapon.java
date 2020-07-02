package com.cycapservers.game;

/**
 * A projectile weapon is a special type of weapon that spawns projectiles
 * @author Bryan Friestad
 *
 */
public abstract class ProjectileWeapon extends Weapon {
	
	private Projectile template;

	public ProjectileWeapon(String name, long switchCooldown, Drawable icon, long fire_rate, long reload_time,
			int max_reloads, int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
			Sound cannot_reload_sound, Projectile template) {
		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound,
				cannot_fire_sound, reload_sound, cannot_reload_sound);
		this.template = template;
	}

}
