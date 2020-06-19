package com.cycapservers.game;

public class RectangleCollider extends Collider {

	private double width;
	private double height;
	
	public RectangleCollider() {
		super(new Position());
	}

	@Override
	public boolean isColliding(Collider other) {
		if(other.getClass().equals(CircleCollider.class)){
			CircleCollider other_circle = (CircleCollider) other;
			return false;
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
