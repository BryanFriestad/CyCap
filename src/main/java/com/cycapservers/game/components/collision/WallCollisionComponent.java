package com.cycapservers.game.components.collision;

import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.entities.Wall;

public class WallCollisionComponent extends CollisionComponent 
{
	private int damage_to_take;

	public WallCollisionComponent(Collider c, int p, PositionComponent start_pos) 
	{
		super(c, p, start_pos);
		damage_to_take = 0;
	}

	@Override
	public void onCollision(CollisionComponent other) 
	{
		return;
	}

	@Override
	public CollisionComponent clone()
	{
		return new WallCollisionComponent(collider.clone(), collision_priority, new PositionComponent());
	}

	@Override
	public boolean update(Entity e) 
	{
		SetPreviousAndCurrentPosition(e);
		Wall w = (Wall) e;
		w.takeDamage(damage_to_take);
		damage_to_take = 0;
		return true;
	}

	public void AddDamageToTake(int damage) 
	{
		this.damage_to_take += damage;
	}

	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub
	}
}
