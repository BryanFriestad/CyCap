package com.cycapservers.game.entities;

import org.json.JSONObject;

import com.cycapservers.JSON_returnable;
import com.cycapservers.game.components.ComponentContainer;
import com.cycapservers.game.components.positioning.PositionComponent;

/**
 * A game object which has a position in the world and an ID. It can be updated.
 * @author Bryan Friestad
 *
 */
public class Entity extends ComponentContainer implements JSON_returnable
{	
	//internal use
	protected PositionComponent position;
	protected String entity_id;
	private long last_update_time;
	/**
	 * The time in ms since the last update call for this entity
	 */
	private long delta_update_time;
	
	public Entity(PositionComponent p)
	{
		super();
		this.position = p;
		RegisterComponent(position);
		this.last_update_time = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return returns whether or not the entity should persist (true) or be deleted (false).
	 */
	public boolean update(){
		delta_update_time = System.currentTimeMillis() - last_update_time;
		last_update_time = System.currentTimeMillis();
		return true;
	}

	@Override
	public JSONObject toJSONObject() {
		if(entity_id == null) throw new IllegalStateException("Entity ID must be set before calling this function");
		
		JSONObject obj = new JSONObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("entity_id", entity_id);
		obj.put("position", position.toJSONObject());
		return obj;
	}

	public double getX() {
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}
	
	public void setX(double x){
		position.setX(x);
	}
	
	public void setY(double y){
		position.setY(y);
	}
	
	public PositionComponent getPosition(){
		return position;
	}
	
	public void setPosition(PositionComponent p){
		setX(p.getX());
		setY(p.getY());
	}

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	
	@Override
	/**
	 * Creates a clone of this Entity. The internal model is also clone()'d. The entity id is not set.
	 */
	public Entity clone() {
		return new Entity(position);
	}

	public long getDelta_update_time() {
		return delta_update_time;
	}
}
