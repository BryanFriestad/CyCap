package com.cycapservers.game.components;

import org.json.JSONObject;

import com.cycapservers.game.DamageDealer;

public class HealthComponent extends Component 
{
	private int health;
	private int max_health;
	/**
	 * The number of lives remaining for this character. Decremented upon death. Once you reach 0, you can no longer respawn. -1 means you have unlimited lives.
	 */
	private int lives_remaining;
	private boolean alive;
	private long last_time_of_death;
	private boolean is_wall;
	
	// TODO: may just want to have "is_invincible" be a param
	public HealthComponent(int max_h, int lives_remaining, boolean wall)
	{
		super("health");
		this.health = max_h;
		this.max_health = max_h;
		this.lives_remaining = lives_remaining;
		this.alive = true;
		this.last_time_of_death = 0;
		this.is_wall = wall;
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}
	
	private void UpdateHealth(DamageDealer d)
	{
		if (is_wall)
		{
			health = Math.max(0, health - d.getDamageAmount()); 
			// TODO: inform if health goes below zero.
		}
		else
		{
			health = Math.max(0, health - d.getDamageAmount());
		}
	}
	
//	private void UpdateHealth(HealingDealer h)
//	{
//		if (is_wall)
//		{
//			health = Math.min(max_health, health + h.getWallHealingAmount());
//		}
//		else
//		{
//			health = Math.min(max_health, health + h.getHealingAmount());
//		}
//	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject obj = new JSONObject();
		obj.put("current", health);
		obj.put("max", max_health);
		obj.put("lives", lives_remaining);
		return obj;
	}

}
