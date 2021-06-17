package com.cycapservers.game.entities;

import com.cycapservers.game.components.collision.RectangleCollider;
import com.cycapservers.game.components.collision.WallCollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

public class Wall extends CollidingDrawableEntity 
{
	
	/**
	 * The amount of damage this entity can take before it is destroyed. -1 means it is invincible
	 */
	private int strength;
	
	//internal
	private boolean destroyed;

	public Wall(PositionComponent p, DrawingComponent model, int strength) 
	{
		super(p, new WallCollisionComponent(new RectangleCollider(model.getDrawWidth(), model.getDrawHeight()), Integer.MAX_VALUE, p), model);
		this.strength = strength;
		this.destroyed = false;
	}
	
	@Override
	public boolean update()
	{
		if (!super.update()) return false;
		return !destroyed;
	}
	
	public void takeDamage(int amount)
	{
		if (this.strength != -1) {
			this.strength -= amount;
			if (strength <= 0)
				destroyed = true;
		}
	}
	
	@Override
	public Wall clone() 
	{
		return new Wall(position, model, strength);
	}
	
}