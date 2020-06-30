package com.cycapservers.game;

/**
 * A special class of FallingBullets which create an explosion upon impact. 
 * This explosion damages players and walls within a certain range.
 * This bullet will also create a "cracked earth" ground mask upon impact.
 * @author Bryan Friestad
 *
 */
public class ArtilleryShell extends FallingBullet{
	
	private Explosion explosion_template;
	private GroundMask cracked_ground_template;
	
	public ArtilleryShell(Drawable model, Collider c, int collision_priority, Position source, Position destination,
			int damage, double wall_damage_mult, String ownerId, int team, BulletWeapon firingWeapon, long lifeSpan,
			double max_height_scale, Explosion explosion, GroundMask cracked_ground) {
		super(model, c, collision_priority, source, destination, damage, wall_damage_mult, ownerId, team, firingWeapon,
				lifeSpan, max_height_scale);
		this.explosion_template = explosion;
		this.cracked_ground_template = cracked_ground;
		//life_span = 3sec
		//damage_range = 1.5 grid spaces
	}
	
	@Override
	public boolean update() {
		if(!super.update()) {
			//create explosion
			//create ground_mask
			return false;
		}
		
		return true;
	}

}