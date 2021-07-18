package com.cycapservers.game.equipment;

import com.cycapservers.game.components.Component;

public abstract class ReloadComponent extends Component 
{
	
	public ReloadComponent()
	{
		super("reload");
	}
	
	protected abstract void OnReloadDown();
	protected abstract void OnReloadUp();
}
