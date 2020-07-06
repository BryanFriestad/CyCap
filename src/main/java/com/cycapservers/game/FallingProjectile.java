package com.cycapservers.game;

/**
 * A bullet which is launched into the air and falls to the ground.
 * It scales up its draw height and width as it gains altitude and then 
 * shrinks as it falls
 * @author Bryan Friestad
 *
 */
public class FallingProjectile extends Projectile {
	
	//internal
	private double initial_v;
	private double scale_per_height;
	private double initial_draw_height;
	private double initial_draw_width;
	
	/**
	 * 
	 * @param model
	 * @param c
	 * @param collision_priority
	 * @param source
	 * @param destination
	 * @param damage
	 * @param wall_damage_mult
	 * @param ownerId
	 * @param team
	 * @param firingWeapon
	 * @param lifeSpan
	 * @param max_height_scale The scale of the entity at the maximum point in this bullet's lifespan
	 */
	public FallingProjectile(Drawable model, Collider c, int collision_priority, Position source, Position destination,
			int damage, double wall_damage_mult, String ownerId, Team team, ProjectileWeapon firingWeapon, long lifeSpan,
			double max_height_scale) {
		super(model, c, collision_priority, Utils.distanceBetween(source, destination) / lifeSpan, Utils.getDirection(source, destination), damage, wall_damage_mult, ownerId, team, firingWeapon,
				lifeSpan);
		initial_v = -Utils.GRAVITY / 2.0 * lifeSpan; //initial velocity to get height = 0 at time = lifeSpan
		double max_height = ((-Utils.GRAVITY / 2.0) * (lifeSpan / 2.0) * (lifeSpan / 2.0)) + (initial_v * (lifeSpan / 2.0));
		scale_per_height = (max_height_scale - model.getDrawWidth()) / (max_height);
		initial_draw_height = model.getDrawHeight();
		initial_draw_width = model.getDrawWidth();
	}
	
	/**
	 * Calls Bullet.update() and then performs scaling
	 * calculations based on current "height"
	 */
	@Override
	public boolean update() {
		if(!super.update()) return false;
		
		long elapsed_life_time = System.currentTimeMillis() - getTime_of_creation();
		double current_scale = scale_per_height * (((-Utils.GRAVITY / 2.0) * (elapsed_life_time) * (elapsed_life_time)) + (initial_v * (elapsed_life_time)));
		setHeight(initial_draw_height * current_scale);
		setWidth(initial_draw_width * current_scale);
		
		return true;
	}
	
}
