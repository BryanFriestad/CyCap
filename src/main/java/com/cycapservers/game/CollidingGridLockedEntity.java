package com.cycapservers.game;

public class CollidingGridLockedEntity extends GridLockedEntity implements Collidable {
	
	private Collider collider;
	private int collision_priority;
	
	private Position previousPosition; //used in collision handling

	public CollidingGridLockedEntity(String id, GridLockedDrawable model, Collider collider, int collision_priority) {
		super(id, model);
		this.collider = collider;
		this.collision_priority = collision_priority;
	}

	@Override
	public int compareTo(Collidable o) {
		return this.getCollisionPriority() - o.getCollisionPriority();
	}

	@Override
	public boolean isColliding(Collidable other) {
		return this.collider.isColliding(other.getCollider());
	}

	@Override
	public void onCollision(Collidable other) {
		this.getModel().setDrawPosition(this.previousPosition);
	}

	@Override
	public int getCollisionPriority() {
		return this.collision_priority;
	}

	@Override
	public Collider getCollider() {
		return this.collider;
	}

}
