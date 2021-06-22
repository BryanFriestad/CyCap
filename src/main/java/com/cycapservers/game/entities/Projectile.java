package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.collision.Collider;
import com.cycapservers.game.components.collision.ProjectileCollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.equipment.ProjectileWeapon;

public class Projectile extends CollidingDrawableEntity
{	
	//params
	private double speed;
	private PositionComponent direction;
	protected ProjectileWeapon shotFrom;
	private long life_span;
	
	//internal
	private long time_of_creation;
	private boolean alive;
	
	public Projectile(PositionComponent p, DrawingComponent model, Collider c, int collision_priority, double speed, 
				      PositionComponent direction, int damage, double wall_damage_mult, String ownerId, Team team, 
				      ProjectileWeapon firingWeapon, long lifeSpan) 
	{
		super(p, new ProjectileCollisionComponent(c, collision_priority, p, ownerId, damage, wall_damage_mult, firingWeapon.getName(), team),
			  model);
		this.speed = speed;
		this.direction = direction;
		this.shotFrom = firingWeapon;
		this.life_span = lifeSpan;
		this.time_of_creation = System.currentTimeMillis();
		alive = true;
	}

	@Override
	/**
	 * Calls Entity.update() and then checks to see if this
	 * bullet is past its life span. If it is alive still,
	 * it will update its position according to its direction
	 * and speed
	 */
	public boolean update() {
		if(!super.update()) return false;
		if(System.currentTimeMillis() - time_of_creation >= life_span){
			alive = false;
			return alive;
		}
		
		PositionComponent delta = new PositionComponent(direction.getX() * speed * getDelta_update_time(), direction.getY() * speed * getDelta_update_time());
		setX(getX() + delta.getX());
		setY(getY() + delta.getY());
		
		return alive;
	}
	
	public long getTime_of_creation() {
		return time_of_creation;
	}

	public long getLife_span() {
		return life_span;
	}

//	@Override
//	public Projectile clone() 
//	{
//		return new Projectile(position.clone(), model.clone(), collision_component.getCollider(), collision_component.getCollisionPriority(), speed, direction, -1, 0.0, "", Team.None, shotFrom, life_span);
//	}

}