package com.cycapservers.game.components.collision;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.positioning.PositionComponent;

public class CharacterCollisionComponent extends CollisionComponent 
{
	
	public CharacterCollisionComponent(Collider c, int p) 
	{
		super(c, p);
	}

	@Override
	public CollisionComponent clone() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean Update(long delta_t) 
	{	
		return true;
	}

	@Override
	public void collideWith(CharacterCollisionComponent other) 
	{
		// intentionally blank
	}

	@Override
	public void collideWith(DamagingCollisionComponent other) 
	{
		// intentionally blank
	}

	@Override
	public void collideWith(StaticCollisionComponent other) 
	{
		PositionComponent min = collider.position.GetPreviousPosition();
		PositionComponent max = collider.position;

		int max_depth = 4;
		for (int i = 0; i < max_depth; i++) //get as close to the wall as we can by successive approximation
		{
			PositionComponent working_pos = Utils.CenterOf(min, max);
			Collider test_collider = collider.CloneWithNewPosition(working_pos);
			if (test_collider.isColliding(other.getCollider()))
			{
				max = working_pos;
			}
			else
			{
				min = working_pos;
			}
		}
		min.SetToNearestPixelPosition();
		
		this.parent.Send(new ComponentMessage(ComponentMessageId.COLLISION_CORRECT_POSITION, min));
	}

	@Override
	public void collideWith(GrabbableCollisionComponent other) 
	{
		// intentionally blank
	}
	
	@Override
	public void beCollidedBy(CollisionComponent other) 
	{
		if (!isColliding(other)) return;
		other.collideWith(this);
	}

	@Override
	public Object GetJSONValue() 
	{
		return null;
	}
}
