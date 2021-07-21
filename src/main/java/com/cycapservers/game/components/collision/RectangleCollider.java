package com.cycapservers.game.components.collision;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.positioning.PositionComponent;

public class RectangleCollider extends Collider 
{
	private double height;
	private double width;
	
	public RectangleCollider(PositionComponent p) 
	{
		super(p);
		height = Utils.GRID_LENGTH;
		width = Utils.GRID_LENGTH;
	}
	
	public RectangleCollider(PositionComponent p, double w, double h)
	{
		super(p);
		height = h;
		width = w;
	}

	@Override
	public boolean isColliding(Collider other) 
	{
		PositionComponent topRight = getTopRight();
		PositionComponent bottomLeft = getBottomLeft();
		
		if (other.getClass().equals(CircleCollider.class))
		{
			CircleCollider other_circle = (CircleCollider) other;
			
			//find point on rect nearest circle
			double nearestX = Utils.clamp(bottomLeft.getX(), other_circle.position.getX(), topRight.getX());
			double nearestY = Utils.clamp(bottomLeft.getY(), other_circle.position.getY(), topRight.getY());
			
			return Utils.distanceBetween(other_circle.position, new PositionComponent(nearestX, nearestY)) < other_circle.getRadius();
		}
		else if (other.getClass().equals(RectangleCollider.class))
		{
			RectangleCollider other_rect = (RectangleCollider) other;
			if (topRight.getY() < other_rect.getBottomLeft().getY() || bottomLeft.getY() > other_rect.getTopRight().getY()) 
			{
				return false;
			}
		    if (topRight.getX() < other_rect.getBottomLeft().getX() || bottomLeft.getX() > other_rect.getTopRight().getX()) 
		    {
			    return false;
			}
			return true;
		}
		else
		{
			throw new IllegalArgumentException("Unknown collider type: " + other.getClass());
		}
	}

	public PositionComponent getBottomLeft() 
	{
		return Utils.difference(position, new PositionComponent(width / 2.0, height / 2.0));
	}

	public PositionComponent getTopRight() 
	{
		return Utils.add(position, new PositionComponent(width / 2.0, height / 2.0));
	}

	@Override
	protected void setWidth(double w) 
	{
		this.width = w;
	}

	@Override
	protected void setHeight(double h) 
	{
		this.height = h;
	}

	@Override
	public Collider clone() 
	{
		return new RectangleCollider(position.clone(), width, height);
	}

	@Override
	public Collider CloneWithNewPosition(PositionComponent p) 
	{
		return new RectangleCollider(p.clone(), width, height);
	}

}
