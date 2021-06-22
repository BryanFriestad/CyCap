package com.cycapservers.game.entities;

import org.json.JSONObject;

import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.matchmaking.Game;

public abstract class PowerUp extends Item
{
	
	private int max_uses;
	protected int uses_remaining;
	protected long duration;
	
	//internal
	protected long last_activate_time;
	
	public PowerUp(PositionComponent p, DrawingComponent model, CollisionComponent c, Game g, String name, int max_uses, long duration) 
	{
		super(p, model, c, g, name);
		this.max_uses = max_uses;
		this.uses_remaining = max_uses;
		this.duration = duration;
		this.last_activate_time = System.currentTimeMillis();
	}

	@Override
	public abstract boolean update();

	@Override
	public abstract boolean use();

	public int getMax_uses() 
	{
		return max_uses;
	}

	public long getDuration() 
	{
		return duration;
	}
	
	@Override
	public JSONObject toJSONObject() 
	{
		JSONObject obj = super.toJSONObject();
		obj.put("uses_remaining", uses_remaining);
		obj.put("max_uses", max_uses);
		return obj;
	}
	
	@Override
	public abstract PowerUp clone();
	
}
