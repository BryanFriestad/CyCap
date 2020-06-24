package com.cycapservers.game;

public class SpeedPotion extends PowerUp {
	
	private double boost_amount;
	private boolean can_stack;

	@Override
	public boolean update() {
		return (uses_remaining > 1 || (System.currentTimeMillis() - last_activate_time < duration));
	}

	@Override
	public boolean use() {
		if(this.grabber == null) {
			throw new IllegalStateException("Error, this powerup has no grabber and cannot be used");
		}
		
		if(uses_remaining < 1)
			return true;
		
		if(!can_stack && (System.currentTimeMillis() - last_activate_time < duration))
			return false;
		
		this.grabber.applyBuff(new SpeedBuff(boost_amount, duration));
		uses_remaining--;
		last_activate_time = System.currentTimeMillis();
		
		return false;
	}
}