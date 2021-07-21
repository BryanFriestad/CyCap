package com.cycapservers.game.components.collision;

import org.json.JSONObject;

public class StaticCollisionComponent extends CollisionComponent 
{

	public StaticCollisionComponent(Collider c, int p) 
	{
		super(c, p);
	}

	@Override
	public CollisionComponent clone() 
	{
		return new StaticCollisionComponent(collider, collision_priority);
	}

	@Override
	public void beCollidedBy(CollisionComponent other) 
	{
		if (!isColliding(other)) return;
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
	public void collideWith(GrabbableCollisionComponent other) 
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
