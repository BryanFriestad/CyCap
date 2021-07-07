package com.cycapservers.game.components.usable;

import com.cycapservers.game.components.HealthComponent;

public class HealthPackUsableComponent extends UsableComponent 
{
	
	/**
	 * The amount to heal the grabber upon use. If this value is -1, then it will heal the user to max health
	 */
	private int heal_amount;
	private HealthComponent user;
	
	//private long heal_time_total;
	//private double heals_per_ms;
	//private long heal_time_elapsed;
	
	public HealthPackUsableComponent(int max_uses, int heal_amount) 
	{
		super(max_uses, 0);
		this.heal_amount = heal_amount;
		user = null;
	}


	@Override
	public boolean Use() 
	{
//		if (grabber == null) 
//		{
//			throw new IllegalStateException("Error, this powerup has no grabber and cannot be used");
//		}
//		
//		if (uses_remaining < 1) 
//		{
//			return true;
//		}
//		else 
//		{
//			if (heal_amount == -1) 
//			{
//				grabber.takeHeals(grabber.getMax_health());
//			}
//			else 
//			{
//				grabber.takeHeals(heal_amount);
//			}
//			uses_remaining--;
//			last_activate_time = System.currentTimeMillis();
//			if (uses_remaining == 0) return true;
//		}
		
		return false;
	}
}
