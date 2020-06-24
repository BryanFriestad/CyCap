package com.cycapservers.game;

public class HealthPack extends PowerUp {
	
	/**
	 * The amount to heal the grabber upon use. If this value is -1, then it will heal the user to max health
	 */
	private int heal_amount;
	
	public HealthPack(String id, Drawable model, Collider c, int collision_priority, Game g, String name, int max_uses, long duration) {
		super(id, model, c, collision_priority, g, name, max_uses, duration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update() {
		if(this.started && ((System.currentTimeMillis() - this.startTime) > this.duration)){
			return true;
		}
		return false;
	}

	@Override
	public boolean use() {
		if(this.grabber == null) {
			throw new IllegalStateException("Error, this powerup has no grabber and cannot be used");
		}
		if(!this.started){
			this.grabber.health = this.grabber.max_health;
			this.startTime = System.currentTimeMillis();
			this.started = true;
			return true;
		}
		return false;
	}

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

}
