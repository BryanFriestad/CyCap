package com.cycapservers.game.components;

import com.cycapservers.game.entities.Entity;

public class OwnerComponent extends Component 
{
	private Entity owner;
	
	public OwnerComponent(Entity e) 
	{
		super("owner");
		this.owner = e;
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
	public boolean Update(long delta_t) 
	{
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		return owner.getEntityId();
	}
	
	public Entity GetOwner()
	{
		return owner;
	}

}
