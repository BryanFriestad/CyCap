package com.cycapservers.game;

public class CircleCollider extends Collider {
	
	private double radius;

	public CircleCollider() {
		super(new Position());
		this.radius = Utils.GRID_LENGTH / 2.0;
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
			return false;
		}
		else{
			throw new IllegalArgumentException("Unknown collider type: " + other.getClass());
		}
	}

}
