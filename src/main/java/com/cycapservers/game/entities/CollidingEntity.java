package com.cycapservers.game.entities;

import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

public class CollidingEntity extends Entity 
{
	private CollisionComponent collision_component;

	public CollidingEntity(PositionComponent p, CollisionComponent c) 
	{
		super(p);
		collision_component = c;
		RegisterComponent(collision_component);
	}
	
	@Override
	public boolean update()
	{
		if (!super.update()) return false;
		if (!collision_component.update(this)) return false;
		return true;
	}
	
	@Override
	public CollidingEntity clone() 
	{
		return new CollidingEntity(position.clone(), collision_component.clone());
	}
	
	public CollisionComponent getCollider()
	{
		return collision_component;
	}
}
