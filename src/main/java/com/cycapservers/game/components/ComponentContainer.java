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
		if (HasComponentOfType(c.getClass())) throw new IllegalArgumentException("A component container may have no more than one copy of a component."); 
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
	
	public boolean HasComponentOfType(Class<? extends Component> c)
	{
		for (Component comp : components)
		{
			if (comp.getClass().equals(c)) return true;
		}
		return false;
	}
	
	public Component GetComponentOfType(Class<? extends Component> c)
	{
		for (Component comp : components)
		{
			if (comp.getClass().equals(c)) return comp;
		}
		throw new IllegalArgumentException("This component container has no such component");
	}

}
