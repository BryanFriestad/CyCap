package com.cycapservers.game.components;

import java.util.ArrayList;
import java.util.List;

/**
 * An object which contains a list of comonents and enables them to communicate with one another.
 * @author Bryan Friestad
 *
 */
public abstract class ComponentContainer {
	
	private List<Component> components;
	
	public ComponentContainer()
	{
		components = new ArrayList<Component>();
	}
	
	protected void RegisterComponent(Component c)
	{
		components.add(c);
		c.SetParent(this);
	}
	
	public void Send(ComponentMessage msg)
	{
		for (Component c : components)
		{
			c.Receive(msg);
		}
	}

}
