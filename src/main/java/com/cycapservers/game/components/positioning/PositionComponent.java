package com.cycapservers.game.components.positioning;

import org.json.JSONObject;

import com.cycapservers.JSON_returnable;
import com.cycapservers.game.Utils;
import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;

public class PositionComponent implements JSON_returnable, Component
{
	private Object parent;
	
	private double x;
	private double y;

	public PositionComponent() 
	{
		parent = null;
		x = 0;
		y = 0;
	}

	public PositionComponent(double x, double y) 
	{
		parent = null;
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public short getClosestGridX(){
		return (short) Math.round((this.x - (Utils.GRID_LENGTH / 2.0)) / Utils.GRID_LENGTH);
	}
	
	public short getClosestGridY(){
		return (short) Math.round((this.y - (Utils.GRID_LENGTH / 2.0)) / Utils.GRID_LENGTH);
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("class", this.getClass().getSimpleName());
		object.put("x", this.x);
		object.put("y", this.y);
		return object;
	}
	
	@Override
	public PositionComponent clone() 
	{
		return new PositionComponent(x, y);
	}

	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public Object GetParent()
	{
		return parent;
	}
	
	@Override
	public void SetParent(Object p)
	{
		parent = p;
	}
}
