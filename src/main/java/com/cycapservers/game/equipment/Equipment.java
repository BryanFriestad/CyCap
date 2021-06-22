package com.cycapservers.game.equipment;

import org.json.JSONObject;

import com.cycapservers.JSON_returnable;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.input.ClientInputHandler;
import com.cycapservers.game.entities.Character;

public abstract class Equipment implements JSON_returnable {
	
	//params
	private String name;
	private long switchCooldown;
	private DrawingComponent icon;
	
	//internal
	private Character owner;
	private boolean equipped;
	private long last_equip_time;
	private long last_update_time;
	protected long delta_update_time;
	
	public Equipment(String name, long switchCooldown, DrawingComponent icon) {
		super();
		this.owner = null;
		this.name = name;
		this.equipped = false;
		this.switchCooldown = switchCooldown;
		this.icon = icon;
		this.last_equip_time = System.currentTimeMillis(); 
	}

	/**
	 * Equips this item
	 * @return True if the equipping was a success
	 */
	public boolean equip(){
		if(owner == null) throw new IllegalStateException("This equipment must first have an owner before equipping");
		
		equipped = true;
		last_equip_time = System.currentTimeMillis();
		return equipped;
	}
	
	/**
	 * Attempts to unequip an equipment from its owner
	 * @return Returns True if the unequipping was successful
	 */
	public boolean unequip(){
		if(owner == null) throw new IllegalStateException("This equipment must first have an owner before unequipping");
		if(!equipped) throw new IllegalStateException("Cannot unequip, this equipment is not equipped");
		
		if(System.currentTimeMillis() - last_equip_time >= switchCooldown){
			equipped = false;
		}
		
		return !equipped;
	}
	
	public void update(ClientInputHandler input_handler){
		delta_update_time = System.currentTimeMillis() - last_update_time;
		last_update_time = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return a double between 0.0 and 1.0 (inclusive). This represents a percentage of the UI bar to fill.
	 */
	public abstract double getEquipmentBar();
	
	public abstract boolean addToInventory(Character target);
	
	public abstract boolean removeFromInventory();

	@Override
	public JSONObject toJSONObject()
	{
		JSONObject obj = new JSONObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("equipped", this.equipped);
		obj.put("icon", this.icon.toJSONObject());
		return obj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEquipped() {
		return equipped;
	}

	public void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}

	public DrawingComponent getIcon() {
		return icon;
	}

	public void setIcon(DrawingComponent icon) {
		this.icon = icon;
	}

	public Character getOwner() {
		return owner;
	}

	public long getSwitchCooldown() {
		return switchCooldown;
	}
	
	@Override
	public abstract Equipment clone();

}
