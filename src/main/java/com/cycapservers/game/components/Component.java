package com.cycapservers.game.components;

/**
 * An object which can communicate to other components via an intermediate ComponentContainer. 
 * @author Bryan Friestad
 *
 */
public abstract class Component
{
	protected Entity parent;
	private String name;
	
	public Component(String name)
	{
		parent = null;
		this.name = name;
	}
	
	public void SetParent(Entity c)
	{
		this.parent = c;
	}
	
	public Entity GetParent()
	{
		return parent;
	}
	
	public String GetName()
	{
		return name;
	}
	
	public abstract void Receive(ComponentMessage message);
	
	/**
	 * Updates this component.
	 * @param delta_t The amount of time in ms since the last update call.
	 * @return Returns true if this object should persist, false if it should be deleted.
	 */
	public abstract boolean Update(long delta_t);
	
	public abstract Object GetJSONValue();
}
