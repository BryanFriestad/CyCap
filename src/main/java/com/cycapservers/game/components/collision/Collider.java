package com.cycapservers.game.components.collision;

import com.cycapservers.game.components.positioning.PositionComponent;

public abstract class Collider {
	
	protected PositionComponent curPos;
	
	protected Collider()
	{
		curPos = new PositionComponent();
	}

	/**
	 * Whether or not two colliders are currently colliding
	 * @param other
	 * @return Returns true if this and other are colliding with one another
	 */
	public abstract boolean isColliding(Collider other);
	
	public abstract void setWidth(double w);
	
	public abstract void setHeight(double h);
	
	public abstract Collider clone();
	
	public void updateCurPosition(PositionComponent p)
	{
		curPos = p;
	}

}
