package com.cycapservers.game.entities;

import org.json.JSONObject;

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
	public JSONObject toJSONObject() 
	{
		if(entity_id == null) throw new IllegalStateException("Entity ID must be set before calling this function");
		
		JSONObject obj = super.toJSONObject();
		obj.put("model", model.toJSONObject());
		return obj;
	}
}
