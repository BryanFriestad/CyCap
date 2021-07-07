package com.cycapservers.game.components.usable;

import com.cycapservers.game.components.SpeedComponent;

/**
 * A power up which increases the user's speed for a duration.
 * @author Bryan Friestad
 *
 */
public class SpeedPotionUsableComponent extends UsableComponent 
{
	/**
	 * a percentage to increase the character's speed upon use
	 */
	private double boost_amount;
	private boolean can_stack;
	private SpeedComponent user;

	public SpeedPotionUsableComponent(int max_uses, long duration, double boost_amount, boolean can_stack) 
	{
		super(max_uses, duration);
		this.boost_amount = boost_amount;
		this.can_stack = can_stack;
		user = null;
	}

	@Override
	public boolean Use() 
	{
//		if(this.grabber == null) {
//			throw new IllegalStateException("Error, this powerup has no grabber and cannot be used");
//		}
//		
//		if(uses_remaining < 1)
//			return true;
//		
//		if(!can_stack && (System.currentTimeMillis() - last_activate_time < duration))
//			return false;
//		
//		this.grabber.applyBuff(new Buff(boost_amount, null, duration));
//		uses_remaining--;
//		last_activate_time = System.currentTimeMillis();
		
		return false;
	}

}