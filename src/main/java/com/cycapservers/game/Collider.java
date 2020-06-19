package com.cycapservers.game;

public abstract class Collider {
	
	private Position pos;
	
	protected Collider(Position p){
		this.pos = p;
	}

	/**
	 * Whether or not two colliders are currently colliding
	 * @param other
	 * @return Returns true if this and other are colliding with one another
	 */
	public abstract boolean isColliding(Collider other);

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

}
