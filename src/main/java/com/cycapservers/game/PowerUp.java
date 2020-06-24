package com.cycapservers.game;

import com.cycapservers.JSONObject;

public abstract class PowerUp extends Item{
	
	private int max_uses;
	protected int uses_remaining;
	protected long duration;
	
	//internal
	protected long last_activate_time;
	
	public PowerUp(String id, Drawable model, Collider c, int collision_priority, Game g, String name, int max_uses, long duration) {
		super(id, model, c, collision_priority, g, name);
		this.max_uses = max_uses;
		this.uses_remaining = max_uses;
		this.duration = duration;
		this.last_activate_time = System.currentTimeMillis();
	}

	@Override
	public abstract boolean update();

	@Override
	public abstract boolean use();

	public int getMax_uses() {
		return max_uses;
	}

	public long getDuration() {
		return duration;
	}
	
	@Override
	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("entity_id", getEntity_id());
		obj.put("model", getModel());
		obj.put("uses_remaining", uses_remaining);
		obj.put("max_uses", max_uses);
		return obj.toString();
	}
	
}
