package com.cycapservers.game;

/**
 * Describes a type of weapon that specifically launches a falling projectile
 * @author Bryan Friestad
 *
 */
public class LaunchingWeapon extends ProjectileWeapon {
	
	/**
	 * the distance, in pixels, that this weapon can fire a projectile
	 */
	protected int range;

	public LaunchingWeapon(String name, long switchCooldown, Drawable icon, long fire_rate, long reload_time,
			int max_reloads, int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
			Sound cannot_reload_sound, Projectile template, int mag_size, int range) {
		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound,
				cannot_fire_sound, reload_sound, cannot_reload_sound, template, mag_size);
		this.range = range;
	}

	@Override
	public Equipment clone() {
		throw new UnsupportedOperationException();
	}

//	@Override
//	public void checkFire(Character p, InputSnapshot s, GameState g) {
//		if(s.mouse_clicked && ((System.currentTimeMillis() - this.last_shot) >= this.fire_rate)){
//			if(this.ammo_in_clip - 1 != -1){
//				Point point = Utils.mapCoordinatesToGridCoordinates(s.mapX, s.mapY);
//				int x = (int) (point.x * Utils.GRID_LENGTH) + Utils.GRID_LENGTH/2;
//				int y = (int) (point.y * Utils.GRID_LENGTH) + Utils.GRID_LENGTH/2;
//				double fire_dist = Utils.distanceBetween(p.x, p.y, x, y);
//				if(fire_dist < this.range) {
//					this.fire(p, s, g);
//					this.last_shot = System.currentTimeMillis();
//				}
//			}
//			else{
//				//TODO: play click sound
//			}
//		}
//	}

}
