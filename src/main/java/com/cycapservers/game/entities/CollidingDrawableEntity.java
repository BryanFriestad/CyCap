package com.cycapservers.game.entities;

import org.json.JSONObject;

import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

public class CollidingDrawableEntity extends Entity {
	
	protected CollisionComponent collision_component;
	protected DrawingComponent model;

	public CollidingDrawableEntity(PositionComponent p, CollisionComponent c, DrawingComponent m) {
		super(p);
		collision_component = c;
		AddComponent(collision_component);
		model = m;
		AddComponent(m);
	}
	
	@Override
	public boolean update()
	{
		if (!super.update()) return false;
		if (!model.update(this)) return false;
		if (!collision_component.update(this)) return false;
		return true;
	}
	
	@Override
	public JSONObject toJSONObject() 
	{
		if(entity_id == null) throw new IllegalStateException("Entity ID must be set before calling this function");
		
		JSONObject obj = super.toJSONObject();
		obj.put("model", model.toJSONObject());
		return obj;
	}

}
