package com.cycapservers.game.equipment;

import com.cycapservers.game.Sound;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.entities.Projectile;

/**
 * A shotgun is a type of semi-automatic gun which
 * fires a select (but non-singular) amount of projectile
 * @author btrf_
 *
 */
public class Shotgun extends SemiAutomaticGun {
	
	private int projectile_count;

	public Shotgun(String name, long switchCooldown, DrawingComponent icon, long fire_rate, long reload_time, int max_reloads,
			int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
			Sound cannot_reload_sound, Projectile template, int mag_size, int projectile_count) {
		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound,
				cannot_fire_sound, reload_sound, cannot_reload_sound, template, mag_size);
		this.projectile_count = projectile_count;
	}
	
	
}