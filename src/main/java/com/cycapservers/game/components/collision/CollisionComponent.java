package com.cycapservers.game.components.collision;

import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;

/**
 * A component which allows the entity to collide with other entities.
 * @author Bryan Friestad
 *
 */
public abstract class CollisionComponent extends Component implements Comparable<CollisionComponent>
{	
	protected Collider collider;
	protected int collision_priority;
	
	public CollisionComponent(Collider c, int p)
	{
		super("collider");
		collider = c;
		collision_priority = p;
	}
	
	public boolean isColliding(CollisionComponent other)
	{
		return (collider.isColliding(other.getCollider())) || (other.getCollider().isColliding(collider));
	}
	
	public int getCollisionPriority()
	{
		return collision_priority;
	}

	public Collider getCollider() 
	{
		return this.collider;
	}
	
	@Override
	public int compareTo(CollisionComponent o) 
	{
		return this.collision_priority - o.getCollisionPriority();
	}
	
	@Override
	public void Receive(ComponentMessage message)
	{
		switch (message.getMessage_id())
		{
			
		default:
			break;
		}
	}

	@Override
	public abstract CollisionComponent clone();
	
	public abstract void beCollidedBy(CollisionComponent other);
	public abstract void collideWith(CharacterCollisionComponent other);
	public abstract void collideWith(DamagingCollisionComponent other);
	public abstract void collideWith(StaticCollisionComponent other);
	public abstract void collideWith(GrabbableCollisionComponent other);
}
