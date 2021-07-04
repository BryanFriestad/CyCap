package com.cycapservers.game.components.collision;

import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.positioning.PositionComponent;

/**
 * A component which allows the entity to collide with other entities.
 * @author Bryan Friestad
 *
 */
public abstract class CollisionComponent extends Component implements Comparable<CollisionComponent>
{	
	protected Collider collider;
	protected int collision_priority;
	private PositionComponent previous_position; //used in collision handling
	
	public CollisionComponent(Collider c, int p, PositionComponent start_pos)
	{
		super("collider");
		collider = c;
		collision_priority = p;
		previous_position = start_pos;
	}
	
	private void SetPreviousAndCurrentPosition(PositionComponent p)
	{
		previous_position = collider.curPos;
		collider.curPos = p;
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

	public PositionComponent getPreviousPosition() 
	{
		return previous_position;
	}

	protected void setPreviousPosition(PositionComponent previousPosition) 
	{
		this.previous_position = previousPosition;
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
		case POSITIONING_UPDATE:
			SetPreviousAndCurrentPosition((PositionComponent) message.getData());
			break;
			
		default:
			break;
		}
	}

	@Override
	public abstract CollisionComponent clone();
	
	public abstract void beCollidedBy(CollisionComponent other);
	public abstract void collideWith(CharacterCollisionComponent other);
	public abstract void collideWith(DamagingCollisionComponent other);
	public abstract void collideWith(WeakDamagingCollisionComponent other);
	public abstract void collideWith(StaticCollisionComponent other);
}
