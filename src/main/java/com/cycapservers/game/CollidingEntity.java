package com.cycapservers.game;

public class CollidingEntity extends Entity implements Collidable {
	
	private Collider collider;
	private int collision_priority;
	
	private Position previousPosition; //used in collision handling

	public CollidingEntity(Drawable model, Collider c, int collision_priority) {
		super(model);
		this.collider = c;
		this.collision_priority = collision_priority;
	}
	
	@Override
	public boolean update(){
		if(!super.update()) return false;
		setPreviousPosition(this.getModel().getDrawPosition());
		return true;
	}
	
	@Override
	public void setX(double x){
		super.setX(x);
		this.collider.setX(x);
	}
	
	@Override
	public void setY(double y){
		super.setY(y);
		this.collider.setY(y);
	}
	
	@Override
	public void setPosition(Position p){
		super.setPosition(p);
		this.collider.setPos(p);
	}
	
	@Override
	public void setWidth(double w){
		super.setWidth(w);
		this.collider.setWidth(w);
	}
	
	@Override
	public void setHeight(double h){
		super.setHeight(h);
		this.collider.setHeight(h);
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
		this.getModel().setDrawPosition(this.getPreviousPosition());
	}

	@Override
	public int getCollisionPriority() {
		return this.collision_priority;
	}

	@Override
	public Collider getCollider() {
		return this.collider;
	}

	public Position getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(Position previousPosition) {
		this.previousPosition = previousPosition;
	}
	
	@Override
	public CollidingEntity clone() {
		return new CollidingEntity(getModel().clone(), getCollider().clone(), collision_priority);
	}

}
