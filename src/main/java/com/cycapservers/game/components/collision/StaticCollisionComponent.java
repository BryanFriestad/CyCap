package com.cycapservers.game.components.collision;

import org.json.JSONObject;

import com.cycapservers.game.components.positioning.PositionComponent;

public class StaticCollisionComponent extends CollisionComponent 
{

	public StaticCollisionComponent(Collider c, int p, PositionComponent start_pos) 
	{
		super(c, p, start_pos);
	}

	@Override
	public CollisionComponent clone() 
	{
		return new StaticCollisionComponent(collider, collision_priority, new PositionComponent());
	}

	@Override
	public void beCollidedBy(CollisionComponent other) 
	{
		other.collideWith(this);
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
		// intentionally blank
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject obj = new JSONObject();
		obj.put("collider", this.collider.getJSONValue());
		return obj;
	}

}
