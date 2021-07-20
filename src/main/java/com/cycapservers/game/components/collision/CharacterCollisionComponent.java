package com.cycapservers.game.components.collision;

import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.positioning.PositionComponent;

public class CharacterCollisionComponent extends CollisionComponent 
{
	
	public CharacterCollisionComponent(Collider c, int p, PositionComponent start_pos) 
	{
		super(c, p, start_pos);
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
		PositionComponent curr_pos = collider.curPos;
		double delta_x = curr_pos.getX() - getPreviousPosition().getX();
		double delta_y = curr_pos.getY() - getPreviousPosition().getY();
		
		// TODO: Utils.MiddleOf(pos1, pos2);
		PositionComponent working_pos = new PositionComponent(curr_pos.getX() - (delta_x/2.0), 
														      curr_pos.getY() - (delta_y/2.0));
		
		int max_depth = 4;
		for(int i = 2; i <= max_depth; i++) //get as close to the wall as we can by successive approximation
		{
			collider.SetCurPosition(working_pos);
			if(!isColliding(other))
			{
				working_pos.setX(working_pos.getX() + (delta_x / Math.pow(2, i)));
				working_pos.setY(working_pos.getY() + (delta_y / Math.pow(2, i)));
			}
			else
			{
				working_pos.setX(working_pos.getX() - (delta_x / Math.pow(2, i)));
				working_pos.setY(working_pos.getY() - (delta_y / Math.pow(2, i)));
			}
		}
		
		this.parent.Send(new ComponentMessage(ComponentMessageId.COLLISION_CORRECT_POSITION, working_pos));
	}

	@Override
	public void collideWith(GrabbableCollisionComponent other) 
	{
		// intentionally blank
	}
	
	@Override
	public void beCollidedBy(CollisionComponent other) 
	{
		other.collideWith(this);
	}

	@Override
	public Object GetJSONValue() 
	{
		return null;
	}
}
