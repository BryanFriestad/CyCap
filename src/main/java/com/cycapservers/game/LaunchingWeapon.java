package com.cycapservers.game;

/**
 * Describes a type of weapon that specifically launches a falling projectile
 * @author btrf_
 *
 */
public abstract class LaunchingWeapon extends ProjectileWeapon {
	
	public LaunchingWeapon(String name, long switchCooldown, Drawable icon, long fire_rate, long reload_time,
			int max_reloads, int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
			Sound cannot_reload_sound, FallingProjectile template) {
		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound, cannot_fire_sound,
				reload_sound, cannot_reload_sound, template);
		// TODO Auto-generated constructor stub
	}

	/**
	 * the distance, in pixels, that this weapon can fire a projectile
	 */
	protected int range;

//	public ThrownWeapon(String name, int damage, int bt, int rate, int mag_size, int extra_mags, int reload_time, double max_height, int lifeTime, int range) {
//		super(name, damage, bt, rate, 0, mag_size, extra_mags, reload_time, 0);
//		this.max_height = max_height;
//		this.bullet_life_time = lifeTime;
//		this.range = range;
//	}
//	
//	public ThrownWeapon(ThrownWeapon tw) {
//		super(tw);
//		this.max_height = tw.max_height;
//		this.bullet_life_time = tw.bullet_life_time;
//		this.range = tw.range;
//	}
//
//	@Override
//	public void update(Character p, InputSnapshot s, GameState g) {
//		if(!this.is_reloading){
//			this.checkFire(p, s, g);
//		}
//		else{
//			//TODO: play reloading sound or something
//		}
//	}
//
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
//
//	@Override
//	public abstract void fire(Character p, InputSnapshot s, GameState g);

}
