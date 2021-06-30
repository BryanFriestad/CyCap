package com.cycapservers.game.components;

import org.json.JSONObject;

import com.cycapservers.game.DamageDealer;

public class HealthComponent extends Component 
{
	private int health;
	private int max_health;
	private boolean is_wall;
	
	// TODO: may just want to have "is_invincible" be a param
	public HealthComponent(int h, int max_h, boolean wall)
	{
		super("health");
		this.health = h;
		this.max_health = max_h;
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
			health = Math.max(0, health - d.getWallDamageAmount());
			// TODO: inform if health goes below zero.
		}
		else
		{
			health = Math.max(0, health - d.getDamageAmount());
		}
	}
	
	private void UpdateHealth(HealingDealing h)
	{
		if (is_wall)
		{
			health = Math.min(max_health, health + h.getWallHealingAmount());
		}
		else
		{
			health = Math.min(max_health, health + h.getHealingAmount());
		}
	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject obj = new JSONObject();
		obj.put("current", health);
		obj.put("max", max_health);
		return obj;
	}

}
