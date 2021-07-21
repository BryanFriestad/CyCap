package com.cycapservers.game.components.collision;

import com.cycapservers.game.components.positioning.PositionComponent;

public abstract class Collider 
{
	protected PositionComponent position;
	
	protected Collider(PositionComponent p)
	{
		position = p;
	}

	/**
	 * Whether or not two colliders are currently colliding
	 * @param other
	 * @return Returns true if this and other are colliding with one another
	 */
	public abstract boolean isColliding(Collider other);
	
	protected abstract void setWidth(double w);
	
	protected abstract void setHeight(double h);
	
	public abstract Collider clone();
	
	/**
	 * Makes a clone of this collider with the new given position.
	 * @param p
	 * @return
	 */
	public abstract Collider CloneWithNewPosition(PositionComponent p);

	public Object getJSONValue() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
