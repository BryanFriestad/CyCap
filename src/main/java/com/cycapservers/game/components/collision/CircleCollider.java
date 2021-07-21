package com.cycapservers.game.components.collision;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.positioning.PositionComponent;

public class CircleCollider extends Collider 
{	
	private double radius;

	public CircleCollider(PositionComponent p) 
	{
		super(p);
		this.radius = Utils.GRID_LENGTH / 2.0;
	}

	public CircleCollider(PositionComponent p, double radius) 
	{
		super(p);
		this.radius = radius;
	}

	@Override
	public boolean isColliding(Collider other) 
	{
		if(other.getClass().equals(CircleCollider.class))
		{
			CircleCollider other_circle = (CircleCollider) other;
			double dist = Utils.distanceBetween(position, other_circle.position);
			return (dist < this.radius + other_circle.radius);
		}
		else if(other.getClass().equals(RectangleCollider.class))
		{
			RectangleCollider other_rect = (RectangleCollider) other;
			
			//find point on rect nearest circle
			double nearestX = Utils.clamp(other_rect.getBottomLeft().getX(), position.getX(), other_rect.getTopRight().getX());
			double nearestY = Utils.clamp(other_rect.getBottomLeft().getY(), position.getY(), other_rect.getTopRight().getY());
			
			return Utils.distanceBetween(position, new PositionComponent(nearestX, nearestY)) < this.radius;
		}
		else
		{
			throw new IllegalArgumentException("Unknown collider type: " + other.getClass());
		}
	}

	public double getRadius() 
	{
		return this.radius;
	}

	@Override
	protected void setWidth(double w) 
	{
		radius = Math.max(radius, w/2.0);
	}

	@Override
	protected void setHeight(double h) 
	{
		radius = Math.max(radius, h/2.0);
	}

	@Override
	public Collider clone() 
	{
		return new CircleCollider(position.clone(), radius);
	}

	@Override
	public Collider CloneWithNewPosition(PositionComponent p) 
	{
		return new CircleCollider(p.clone(), radius);
	}
}
