package com.cycapservers.game;

import com.cycapservers.JSONObject;

public class HealthPack extends PowerUp {
	
	/**
	 * The amount to heal the grabber upon use. If this value is -1, then it will heal the user to max health
	 */
	private int heal_amount;
	
	//private long heal_time_total;
	//private double heals_per_ms;
	//private long heal_time_elapsed;
	
	public HealthPack(Drawable model, Collider c, int collision_priority, Game g, String name, int max_uses, long duration, int heal_amount) {
		super(model, c, collision_priority, g, name, max_uses, duration);
		this.heal_amount = heal_amount;
	}

	@Override
	public boolean update() {
		return uses_remaining >= 1;
	}

	@Override
	public boolean use() {
		if(grabber == null) {
			throw new IllegalStateException("Error, this powerup has no grabber and cannot be used");
		}
		
		if(uses_remaining < 1) {
			return true;
		}
		else {
			if(heal_amount == -1) {
				grabber.takeHeals(grabber.getMax_health());
			}
			else {
				grabber.takeHeals(heal_amount);
			}
			uses_remaining--;
			last_activate_time = System.currentTimeMillis();
			if(uses_remaining == 0)
				return true;
		}
		
		return false;
	}
	
	@Override
	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("entity_id", getEntity_id());
		obj.put("model", getModel());
		obj.put("uses_remaining", uses_remaining);
		obj.put("max_uses", getMax_uses());
//		obj.put("heal_time_total", heal_time_total);
//		obj.put("heal_time_elapsed", heal_time_elapsed);
		return obj.toString();
	}

	@Override
	public HealthPack clone() {
		return new HealthPack(getModel().clone(), getCollider().clone(), getCollisionPriority(), null, name, getMax_uses(), duration, heal_amount);
	}

}
