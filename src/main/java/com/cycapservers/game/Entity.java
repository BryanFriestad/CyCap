package com.cycapservers.game;

import com.cycapservers.JSONObject;
import com.cycapservers.JSON_Stringable;

public class Entity implements JSON_Stringable{
	
	private String entity_id;
	private Drawable model;
	
	//internal use
	private long last_update_time;
	/**
	 * The time in ms since the last update call for this entity
	 */
	protected long delta_update_time;
	
	//TODO: remove id as a parameter to the entity, it should only ever be set by the game state after the fact
	public Entity(String id, Drawable model){
		entity_id = id;
		this.model = model;
		this.last_update_time = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return returns whether or not the entity should be kept(true) or deleted(false)
	 */
	public boolean update(){
		delta_update_time = System.currentTimeMillis() - last_update_time;
		last_update_time = System.currentTimeMillis();
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
	
	public Position getPosition(){
		return this.getModel().getDrawPosition();
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

	public void setWidth(double w) {
		this.model.setDrawWidth(w);
	}
	
	public void setHeight(double h) {
		this.model.setDrawHeight(h);
	}
	
}
