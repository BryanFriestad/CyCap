package com.cycapservers.game;

public class Wall extends CollidingGridLockedEntity {
	
	/**
	 * The amount of damage this entity can take before it is destroyed. -1 means it is invincible
	 */
	private int strength;
	
	//internal
	private boolean destroyed;

	public Wall(String id, GridLockedDrawable model, int strength) {
		super(id, model, new RectangleCollider(model.getDrawPosition(), model.getDrawWidth(), model.getDrawHeight()), Integer.MAX_VALUE);
		this.strength = strength;
		this.destroyed = false;
	}
	
	@Override
	public boolean update(){
		return !destroyed;
	}
	
	public void takeDamage(int amount){
		if(this.strength != -1)
			this.strength -= amount;
	}
	
	@Override
	public void onCollision(Collidable other){
		return; //wall should not move
	}
	
}