package com.cycapservers.game.equipment;

import com.cycapservers.game.components.Component;

public abstract class ShootableComponent extends Component 
{

	public ShootableComponent() 
	{
		super("shooter");
	}

	protected abstract void OnShootDown();
	protected abstract void OnShootUp();

}
