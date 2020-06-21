package com.cycapservers.game;

import com.cycapservers.JSONObject;
import com.cycapservers.JSON_Stringable;

public class Entity implements JSON_Stringable{
	
	private String entity_id;
	private Drawable model;
	
	public Entity(String id, Drawable model){
		entity_id = id;
		this.model = model;
	}
	
	/**
	 * 
	 * @return returns whether or not the entity should be kept(true) or deleted(false)
	 */
	public boolean update(){
		return true;
	}

	@Override
	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("entity_id", entity_id);
		obj.put("model", model);
		return obj.toString();
	}

	public Drawable getModel() {
		return model;
	}

	public void setModel(Drawable model) {
		this.model = model;
	}

	public double getX() {
		return this.getModel().getDrawPosition().getX();
	}

	public double getY() {
		return this.getModel().getDrawPosition().getY();
	}
	
	public void setX(double x){
		this.getModel().getDrawPosition().setX(x);
	}
	
	public void setY(double y){
		this.getModel().getDrawPosition().setY(y);
	}
	
	public void setPosition(Position p){
		this.getModel().getDrawPosition().setX(p.getX());
		this.getModel().getDrawPosition().setY(p.getY());
	}

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	
}
