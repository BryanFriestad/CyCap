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
	private boolean destroy_when_out_of_lives;
	
	// TODO: may just want to have "is_invincible" be a param
	/**
	 * 
	 * @param max_h Maximum health value.
	 * @param lives_remaining The number of lives to start with.
	 * @param wall If this should take wall damage or not.
	 * @param destroy_when_out_of_lives If this component should kill the entity when it is out of lives.
	 */
	public HealthComponent(int max_h, int lives_remaining, boolean wall, boolean destroy_when_out_of_lives)
	{
		super("health");
		this.health = max_h;
		this.max_health = max_h;
		this.lives_remaining = lives_remaining;
		this.alive = true;
		this.last_time_of_death = 0;
		this.is_wall = wall;
		this.destroy_when_out_of_lives = destroy_when_out_of_lives;
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		switch (message.getMessage_id())
		{
		case COLLISION_TAKE_DAMAGE:
			UpdateHealth((DamageDealer) message.getData());
			break;
			
		default:
			break;
		
		}
	}

	@Override
	public boolean Update(long delta_t) 
	{
		if (destroy_when_out_of_lives && lives_remaining == 0) return false;
		return true;
	}
	
	private void UpdateHealth(DamageDealer d)
	{
//		System.out.println("Entity " + parent.getEntityId() + " is taking " + d.getDamageAmount() + " points of damage");
		if (is_wall)
		{
			int health_to_be = health - d.getDamageAmount();
			if (health_to_be <= 0)
			{
				alive = false;
				lives_remaining--;
			}
			health = Math.max(0, health_to_be);
			// TODO: inform if health goes below zero.
		}
		else
		{
			int health_to_be = health - d.getDamageAmount();
			if (health_to_be <= 0)
			{
				alive = false;
				lives_remaining--;
			}
			health = Math.max(0, health_to_be);
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
	
	private void Respawn()
	{
		alive = true;
	}

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
