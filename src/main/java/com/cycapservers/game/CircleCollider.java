package com.cycapservers.game;

public class CircleCollider extends Collider {
	
	private double radius;

	public CircleCollider() {
		super(new Position());
		this.radius = Utils.GRID_LENGTH / 2.0;
	}

	public CircleCollider(Position p, double radius) {
		super(p);
		this.radius = radius;
	}

	@Override
	public boolean isColliding(Collider other) {
		if(other.getClass().equals(CircleCollider.class)){
			CircleCollider other_circle = (CircleCollider) other;
			double dist = Utils.distanceBetween(this.getPos(), other_circle.getPos());
			return (dist < this.radius + other_circle.radius);
		}
		else if(other.getClass().equals(RectangleCollider.class)){
			RectangleCollider other_rect = (RectangleCollider) other;
			
			//find point on rect nearest circle
			double nearestX = Utils.clamp(other_rect.getBottomLeft().getX(), this.getPos().getX(), other_rect.getTopRight().getX());
			double nearestY = Utils.clamp(other_rect.getBottomLeft().getY(), this.getPos().getY(), other_rect.getTopRight().getY());
			
			return Utils.distanceBetween(this.getPos(), new Position(nearestX, nearestY)) < this.radius;
		}
		else{
			throw new IllegalArgumentException("Unknown collider type: " + other.getClass());
		}
	}

	public double getRadius() {
		return this.radius;
	}

	@Override
	public void setWidth(double w) {
		radius = Math.max(radius, w/2.0);
	}

	@Override
	public void setHeight(double h) {
		radius = Math.max(radius, h/2.0);
	}

}
