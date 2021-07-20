package com.cycapservers.game.entities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.cycapservers.JSON_returnable;
import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;

/**
 * An object which contains a list of comonents and enables them to communicate with one another.
 * @author Bryan Friestad
 *
 */
public class Entity implements JSON_returnable
{
	private String entity_id;
	private List<Component> components;
	
	public Entity(String entity_id)
	{
		this.entity_id = entity_id;
		components = new ArrayList<Component>();
	}
	
	/**
	 * Adds components to this container. The order in which you add them determines the order they are updated in.
	 * @param c The component you want to add.
	 */
	public void AddComponent(Component c)
	{
		if (HasComponentOfType(c.getClass())) throw new IllegalArgumentException("A component container may have no more than one copy of a component.");
		for (Component existing_component : components)
		{
			if (existing_component.GetName().equals(c.GetName())) throw new IllegalArgumentException("Another component with the same name already exists in this container.");
		}
		components.add(c);
		c.SetParent(this);
	}
	
	public boolean Update(long delta_t)
	{
		for (Component c : components)
		{
			if (c.Update(delta_t) == false) return false;
		}
		return true;
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
			if (c.isInstance(comp)) return true;
		}
		return false;
	}
	
	public Component GetComponentOfType(Class<? extends Component> c)
	{
		for (Component comp : components)
		{
			if (c.isInstance(comp)) return comp;
		}
		throw new IllegalArgumentException("This component container has no such component");
	}

	@Override
	public JSONObject toJSONObject() 
	{
		JSONObject obj = new JSONObject();
		obj.put("entity_id", entity_id);
		for (Component c : components)
		{
			obj.put(c.GetName(), c.GetJSONValue());
		}
		return obj;
	}
	
	public String getEntityId()
	{
		return entity_id;
	}
}
