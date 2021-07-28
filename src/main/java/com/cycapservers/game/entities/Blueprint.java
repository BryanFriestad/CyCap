package com.cycapservers.game.entities;

import java.util.ArrayList;
import java.util.List;

import com.cycapservers.game.components.Component;

public class Blueprint 
{
	private List<Component> components;
	
	public Blueprint(Component... components)
	{
		this.components = new ArrayList<Component>();
		for (Component c : components)
		{
			this.components.add(c);
		}
	}
	
	public void AddComponent(Component c)
	{
		components.add(c);
	}
	
	public void ManufactureEntity(Entity e)
	{
		for (Component c : components) e.AddComponent(c);
	}
}
