package com.cycapservers.game;

import com.cycapservers.JSONObject;
import com.cycapservers.JSON_Stringable;

public abstract class Equipment implements JSON_Stringable {
	
	private String name;
	
	private Character owner;
	private boolean equipped;
	private long switchCooldown;
	
	private Drawable icon;
	
	public Equipment(String name, long switchCooldown, Drawable icon) {
		super();
		this.owner = null;
		this.name = name;
		this.equipped = false;
		this.switchCooldown = switchCooldown;
		this.icon = icon;
	}

	/**
	 * Equips this item
	 * @return True if the equipping was a success
	 */
	public abstract boolean equip();
	
	public abstract boolean unequip();
	
	public abstract void update(Object inputs); //TODO choose a different object type to pass in inputs
	
	/**
	 * 
	 * @return a double between 0.0 and 1.0 (inclusive). This represents a percentage of the UI bar to fill.
	 */
	public abstract double getEquipmentBar();
	
	public abstract boolean addToInventory(Character target);
	
	public abstract boolean removeFromInventory();

	@Override
	public String toJSONString(){
		JSONObject obj = new JSONObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("equipped", this.equipped);
		obj.put("icon", this.icon);
		return obj.toString();
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

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public Character getOwner() {
		return owner;
	}

	public long getSwitchCooldown() {
		return switchCooldown;
	}

}
