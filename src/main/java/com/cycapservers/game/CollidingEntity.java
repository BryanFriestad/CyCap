package com.cycapservers.game;

public class CollidingEntity extends Entity implements Collidable {
	
	private Collider collider;
	private int collisionPriority;
	
	private Position previousPosition; //used in collision handling

	public CollidingEntity(String id, Drawable model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean update(){
		previousPosition = this.getModel().getDrawPosition();
		
		return true;
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
		return this.collisionPriority;
	}

	@Override
	public Collider getCollider() {
		return this.collider;
	}

}
