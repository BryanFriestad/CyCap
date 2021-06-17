package com.cycapservers.game.components.input;

import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.entities.Character;

/**
 * A component which allow an entity to move based on some form of input.
 * @author Bryan Friestad
 *
 */
public abstract class InputComponent implements Component
{
	private Object parent;
	
	public InputComponent()
	{
		parent = null;
	}
	
	public abstract boolean update(Character c);
	
	@Override
	public Object GetParent()
	{
		return parent;
	}
	
	@Override
	public void SetParent(Object p)
	{
		parent = p;
	}
}
