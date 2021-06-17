package com.cycapservers.game.entities;

import com.cycapservers.JsonToStringObject;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

public class CollidingDrawableEntity extends Entity {
	
	protected CollisionComponent collision_component;
	protected DrawingComponent model;

	public CollidingDrawableEntity(PositionComponent p, CollisionComponent c, DrawingComponent m) {
		super(p);
		collision_component = c;
		RegisterComponent(collision_component);
		model = m;
		RegisterComponent(m);
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
	public String toJSONString() {
		if(entity_id == null)
			throw new IllegalStateException("Entity ID must be set before calling this function");
		
		JsonToStringObject obj = new JsonToStringObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("entity_id", entity_id);
		obj.put("position", position);
		obj.put("model", model);
		return obj.toString();
	}

}
