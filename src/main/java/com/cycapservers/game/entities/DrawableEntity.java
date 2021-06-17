package com.cycapservers.game.entities;

import com.cycapservers.JsonToStringObject;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

public class DrawableEntity extends Entity
{
	private DrawingComponent model;
	
	public DrawableEntity(PositionComponent p, DrawingComponent model) 
	{
		super(p);
		this.model = model;
		RegisterComponent(this.model);
	}
	
	public boolean update()
	{
		if (!super.update()) return false;
		if (!model.update(this)) return false;
		return true;
	}
	
	public DrawingComponent getModel() {
		return model;
	}

	public void setModel(DrawingComponent model) {
		this.model = model;
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
