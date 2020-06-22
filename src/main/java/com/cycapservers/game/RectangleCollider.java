package com.cycapservers.game;

public class RectangleCollider extends Collider {

	private Position bottomLeft;
	private Position topRight;
	
	public RectangleCollider() {
		super(new Position());
		bottomLeft = new Position(-Utils.GRID_LENGTH / 2.0, -Utils.GRID_LENGTH / 2.0);
		topRight = new Position(Utils.GRID_LENGTH / 2.0, Utils.GRID_LENGTH / 2.0);
	}
	
	public RectangleCollider(Position center, double width, double height){
		super(center);
		bottomLeft = new Position(center.getX() - (width / 2.0), center.getY() - (height / 2.0));
		topRight = new Position(center.getX() + (width / 2.0), center.getY() + (height / 2.0));
	}

	@Override
	public boolean isColliding(Collider other) {
		if(other.getClass().equals(CircleCollider.class)){
			CircleCollider other_circle = (CircleCollider) other;
			
			//find point on rect nearest circle
			double nearestX = Utils.clamp(this.bottomLeft.getX(), other_circle.getPos().getX(), this.topRight.getX());
			double nearestY = Utils.clamp(this.bottomLeft.getY(), other_circle.getPos().getY(), this.topRight.getY());
			
			return Utils.distanceBetween(other_circle.getPos(), new Position(nearestX, nearestY)) < other_circle.getRadius();
		}
		else if(other.getClass().equals(RectangleCollider.class)){
			RectangleCollider other_rect = (RectangleCollider) other;
			if (this.topRight.getY() < other_rect.bottomLeft.getY() || this.bottomLeft.getY() > other_rect.topRight.getY()) {
				return false;
			}
		    if (this.topRight.getX() < other_rect.bottomLeft.getX() || this.bottomLeft.getX() > other_rect.topRight.getX()) {
			    return false;
			}
			return true;
		}
		else{
			throw new IllegalArgumentException("Unknown collider type: " + other.getClass());
		}
	}

	public Position getBottomLeft() {
		return bottomLeft;
	}

	public Position getTopRight() {
		return topRight;
	}

	@Override
	public void setWidth(double w) {
		this.bottomLeft.setX(this.getPos().getX() - w/2.0);
		this.topRight.setX(this.getPos().getX() + w/2.0);
	}

	@Override
	public void setHeight(double h) {
		this.bottomLeft.setY(this.getPos().getY() - h/2.0);
		this.topRight.setY(this.getPos().getY() + h/2.0);
	}

}
