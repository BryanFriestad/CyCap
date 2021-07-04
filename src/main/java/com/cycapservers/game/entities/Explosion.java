package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.collision.Collider;
import com.cycapservers.game.components.collision.DamagingCollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

public class Explosion extends CollidingDrawableEntity
{

	public Explosion(PositionComponent p, DrawingComponent model, Collider c, int collision_priority, String owner_id, int damage_amount, double wall_damage_mult, String kill_type, Team owner_team) {
		super(p, new DamagingCollisionComponent(c, collision_priority, p, owner_id, damage_amount, wall_damage_mult, kill_type, owner_team), model);
	}
	
}
