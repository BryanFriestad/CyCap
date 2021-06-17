package com.cycapservers.game.components.collision;

import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentContainer;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.Entity;

/**
 * A component which allows the entity to collide with other entities.
 * @author Bryan Friestad
 *
 */
public abstract class CollisionComponent implements Comparable<CollisionComponent>, Component
{
	private Object parent;
	
	protected Collider collider;
	protected int collision_priority;
	
	private PositionComponent previous_position; //used in collision handling
	
	public CollisionComponent(Collider c, int p, PositionComponent start_pos)
	{
		parent = null;
		collider = c;
		collision_priority = p;
		previous_position = start_pos;
	}
	
	public abstract boolean update(Entity e);
	
	protected void SetPreviousAndCurrentPosition(Entity e)
	{
		previous_position = collider.curPos;
		collider.curPos = e.getPosition();
	}
	
	public boolean isColliding(CollisionComponent other)
	{
		return (collider.isColliding(other.getCollider())) || (other.getCollider().isColliding(collider));
	}
	
	public int getCollisionPriority()
	{
		return collision_priority;
	}
	
	public abstract void onCollision(CollisionComponent other);

	public Collider getCollider() 
	{
		return this.collider;
	}

	public PositionComponent getPreviousPosition() 
	{
		return previous_position;
	}

	public void setPreviousPosition(PositionComponent previousPosition) 
	{
		this.previous_position = previousPosition;
	}
	
	@Override
	public abstract CollisionComponent clone();

	@Override
	public int compareTo(CollisionComponent o) 
	{
		return this.collision_priority - o.getCollisionPriority();
	}
	
	@Override
	public Object GetParent()
	{
		return parent;
	}
	
	@Override
	public void SetParent(Object p)
	{
		parent = p;
	}
}
