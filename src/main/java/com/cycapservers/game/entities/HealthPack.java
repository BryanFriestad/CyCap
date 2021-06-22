package com.cycapservers.game.entities;

import com.cycapservers.game.components.collision.Collider;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.matchmaking.Game;

public class HealthPack extends PowerUp 
{
	
	/**
	 * The amount to heal the grabber upon use. If this value is -1, then it will heal the user to max health
	 */
	private int heal_amount;
	
	//private long heal_time_total;
	//private double heals_per_ms;
	//private long heal_time_elapsed;
	
	public HealthPack(PositionComponent p, DrawingComponent model, CollisionComponent c, Game g, String name, int max_uses, long duration, int heal_amount) 
	{
		super(p, model, c, g, name, max_uses, duration);
		this.heal_amount = heal_amount;
	}

	@Override
	public boolean update() 
	{
		return uses_remaining >= 1;
	}

	@Override
	public boolean use() 
	{
		if (grabber == null) 
		{
			throw new IllegalStateException("Error, this powerup has no grabber and cannot be used");
		}
		
		if (uses_remaining < 1) 
		{
			return true;
		}
		else 
		{
			if (heal_amount == -1) 
			{
				grabber.takeHeals(grabber.getMax_health());
			}
			else 
			{
				grabber.takeHeals(heal_amount);
			}
			uses_remaining--;
			last_activate_time = System.currentTimeMillis();
			if (uses_remaining == 0) return true;
		}
		
		return false;
	}

	@Override
	public HealthPack clone() 
	{
		return new HealthPack(position.clone(), model.clone(), collision_component.clone(), null, name, getMax_uses(), duration, heal_amount);
	}

}
