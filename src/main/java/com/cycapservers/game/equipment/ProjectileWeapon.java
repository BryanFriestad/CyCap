package com.cycapservers.game.equipment;

import com.cycapservers.game.Sound;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.entities.Character;
import com.cycapservers.game.entities.Projectile;

/**
 * A projectile weapon is a special type of weapon that spawns projectiles
 * @author Bryan Friestad
 *
 */
public abstract class ProjectileWeapon extends Weapon {
	
	//params
	private Projectile template;
	private int magazine_size;
	
	//internal
	private int in_magazine;

	public ProjectileWeapon(String name, long switchCooldown, DrawingComponent icon, long fire_rate, long reload_time,
			int max_reloads, int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
			Sound cannot_reload_sound, Projectile template, int mag_size) {
		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound,
				cannot_fire_sound, reload_sound, cannot_reload_sound);
		this.template = template;
		this.magazine_size = mag_size;
	}

	@Override
	public boolean fire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getEquipmentBar() {
		return (double) in_magazine / (double) magazine_size;
	}

	@Override
	public boolean addToInventory(Character target) {
		return false; //TODO
	}

	@Override
	public boolean removeFromInventory() {
		// TODO Auto-generated method stub
		return false;
	}

}
