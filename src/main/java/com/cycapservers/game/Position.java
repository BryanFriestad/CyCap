package com.cycapservers.game;

import com.cycapservers.JSONObject;
import com.cycapservers.JSON_Stringable;

public class Position implements JSON_Stringable{
	
	private double x;
	private double y;

	public Position() {
		x = 0;
		y = 0;
	}

	public Position(double x, double y) {
		super();
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
	public String toJSONString() {
		JSONObject object = new JSONObject();
		object.put("class", this.getClass().getSimpleName());
		object.put("x", this.x);
		object.put("y", this.y);
		return object.toString();
	}
	
	@Override
	public Position clone() {
		return new Position(x, y);
	}
}
