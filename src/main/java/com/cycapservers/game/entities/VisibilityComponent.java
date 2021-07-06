package com.cycapservers.game.entities;

import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;

public class VisibilityComponent extends Component 
{
	/**
	 * The number of grid spaces out the attached entity can see.
	 */
	private int visibility;
	
	public VisibilityComponent()
	{
		super("visibility");
		visibility = 0;
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		return visibility;
	}

}
