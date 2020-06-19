package com.cycapservers.game;

public interface Collidable extends Comparable<Collidable>{
	
	public boolean isColliding(Collidable other);
	public void onCollision(Collidable other);
	public int getCollisionPriority();
	public Collider getCollider();
	//public CollisionRule[] getRules(); //this may be needed, not sure 

}
