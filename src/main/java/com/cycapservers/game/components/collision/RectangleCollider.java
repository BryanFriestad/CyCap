package com.cycapservers.game.components.collision;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.positioning.PositionComponent;

public class RectangleCollider extends Collider {

	private PositionComponent bottomLeft;
	private PositionComponent topRight;
	
	public RectangleCollider() 
	{
		super();
		bottomLeft = new PositionComponent(-Utils.GRID_LENGTH / 2.0, -Utils.GRID_LENGTH / 2.0);
		topRight = new PositionComponent(Utils.GRID_LENGTH / 2.0, Utils.GRID_LENGTH / 2.0);
	}
	
	public RectangleCollider(double width, double height)
	{
		super();
		bottomLeft = new PositionComponent(curPos.getX() - (width / 2.0), curPos.getY() - (height / 2.0));
		topRight = new PositionComponent(curPos.getX() + (width / 2.0), curPos.getY() + (height / 2.0));
	}

	@Override
	public boolean isColliding(Collider other) 
	{
		if (other.getClass().equals(CircleCollider.class))
		{
			CircleCollider other_circle = (CircleCollider) other;
			
			//find point on rect nearest circle
			double nearestX = Utils.clamp(this.bottomLeft.getX(), other_circle.curPos.getX(), this.topRight.getX());
			double nearestY = Utils.clamp(this.bottomLeft.getY(), other_circle.curPos.getY(), this.topRight.getY());
			
			return Utils.distanceBetween(other_circle.curPos, new PositionComponent(nearestX, nearestY)) < other_circle.getRadius();
		}
		else if (other.getClass().equals(RectangleCollider.class))
		{
			RectangleCollider other_rect = (RectangleCollider) other;
			if (this.topRight.getY() < other_rect.bottomLeft.getY() || this.bottomLeft.getY() > other_rect.topRight.getY()) 
			{
				return false;
			}
		    if (this.topRight.getX() < other_rect.bottomLeft.getX() || this.bottomLeft.getX() > other_rect.topRight.getX()) 
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
		return bottomLeft;
	}

	public PositionComponent getTopRight() 
	{
		return topRight;
	}

	@Override
	public void setWidth(double w) 
	{
		this.bottomLeft.setX(curPos.getX() - w/2.0);
		this.topRight.setX(curPos.getX() + w/2.0);
	}

	@Override
	public void setHeight(double h) 
	{
		this.bottomLeft.setY(curPos.getY() - h/2.0);
		this.topRight.setY(curPos.getY() + h/2.0);
	}

	@Override
	public Collider clone() 
	{
		return new RectangleCollider(topRight.getX() - bottomLeft.getX(), topRight.getY() - bottomLeft.getY());
	}
	
	@Override
	public void SetCurPosition(PositionComponent p)
	{
		PositionComponent delta = Utils.difference(p, curPos);
		bottomLeft = Utils.add(bottomLeft, delta);
		topRight = Utils.add(topRight, delta);
	}

}
