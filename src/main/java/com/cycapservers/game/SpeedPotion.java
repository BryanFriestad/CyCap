package com.cycapservers.game;

public class SpeedPotion extends PowerUp {
	
	/**
	 * a percentage to increase the character's speed upon use
	 */
	private double boost_amount;
	private boolean can_stack;

	public SpeedPotion(Drawable model, Collider c, int collision_priority, Game g, String name, int max_uses, long duration, double boost_amount, boolean can_stack) {
		super(model, c, collision_priority, g, name, max_uses, duration);
		this.boost_amount = boost_amount;
		this.can_stack = can_stack;
	}

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
		
		this.grabber.applyBuff(new Buff(boost_amount, null, duration));
		uses_remaining--;
		last_activate_time = System.currentTimeMillis();
		
		return false;
	}

	@Override
	public SpeedPotion clone() {
		return new SpeedPotion(getModel().clone(), getCollider().clone(), this.getCollisionPriority(), null, name, getMax_uses(), duration, boost_amount, can_stack);
	}

}