package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.collision.Collider;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.drawing.FadingMaskComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.equipment.ProjectileWeapon;

/**
 * A special class of FallingBullets which create an explosion upon impact. 
 * This explosion damages players and walls within a certain range.
 * This bullet will also create a "cracked earth" ground mask upon impact.
 * @author Bryan Friestad
 *
 */
public class ArtilleryShell extends FallingProjectile
{
	
	private Explosion explosion_template;
	private FadingMaskComponent cracked_ground_template;
	
	private PositionComponent dest;
	
	public ArtilleryShell(DrawingComponent model, Collider c, int collision_priority, PositionComponent source, PositionComponent destination,
			int damage, double wall_damage_mult, String ownerId, Team team, ProjectileWeapon firingWeapon, long lifeSpan,
			double max_height_scale, Explosion explosion, FadingMaskComponent cracked_ground) 
	{
		super(model, c, collision_priority, source, destination, damage, wall_damage_mult, ownerId, team, firingWeapon,
				lifeSpan, max_height_scale);
		this.explosion_template = explosion;
		this.cracked_ground_template = cracked_ground;
		// note: intentionally do not register position component;
		this.dest = destination;
		//life_span = 3sec
		//damage_range = 1.5 grid spaces
	}
	
	@Override
	public boolean update() 
	{
		if (!super.update()) 
		{
			shotFrom.getOwner().getGame().addEntity(explosion_template.clone()); //create explosion
			shotFrom.getOwner().getGame().addEntity(new DrawableEntity(dest, cracked_ground_template.clone())); //create ground_mask
			return false;
		}
		return true;
	}

}