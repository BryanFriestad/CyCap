package com.cycapservers.game.components;

/**
 * A interface for a component which can communicate to other components via an intermediate ComponentContainer. 
 * @author Bryan Friestad
 *
 */
public interface Component 
{
	public abstract void SetParent(Object p);
	public abstract Object GetParent();
	public abstract void Receive(ComponentMessage message);
}
