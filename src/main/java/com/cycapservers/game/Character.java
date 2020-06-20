package com.cycapservers.game;

import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.game.database.GameEventType;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.database.PlayerStats;

public abstract class Character extends Entity {
	
	/**
	 * The game in which this character is playing
	 */
	private Game game;

	private int team;
	private String class_name;
	
	protected Equipment[] inventory;
	protected Equipment currentEquipment;
	protected Item item_slot;
	
	protected int health;
	protected int max_health;
	protected boolean alive;
	protected long last_time_of_death;
	
	protected double speed;
	protected int visibility;
	
	protected boolean is_invincible;
	protected double speed_boost;
	protected double damage_boost;
	
	

	public Character(String id, Drawable model, Game game, int team, String class_name, int max_health, double speed, int visibility, int inventory_size) {
		super(id, model);
		this.game = game;
		this.team = team;
		this.class_name = class_name;
		this.max_health = max_health;
		this.health = max_health;
		this.speed = speed;
		this.visibility = visibility;
		
		inventory = new Equipment[inventory_size];
		
		this.alive = true;
		this.last_time_of_death = System.currentTimeMillis();
		
		this.is_invincible = false;
		this.speed_boost = 1.0;
		this.damage_boost = 1.0;
		
		this.item_slot = null;
		Utils.setRole(this);
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public void takeDamage(DamageDealer d) {
		if(!this.is_invincible){
			this.health -= d.getDamageAmount();
		}
		if(this.health <= 0){
			this.die(); //idk what this is gonna do yet
			game.addGameEvent(new GameEventsEntity(game.getId(), GameEventType.kill, d.getOwnerEntityId(), this.getEntity_id(), d.getDeathType()));
		}
	}
	
	public void takeHeals(int amount) {
		this.health = Math.min(this.max_health, this.health + amount);
	}
	
	protected abstract void respawn(GameState g);
	
	public void die(){
		this.alive = false;
		this.last_time_of_death = System.currentTimeMillis();
	}
	
	public abstract void update(GameState game, InputSnapshot s);
	
	protected void useItem() {
		if(this.item_slot == null){
			return;
		}
		else{
			if(this.item_slot.use()) {
				this.item_slot = null;
			}
		}
	}
	
	/**
	 * @param equipmentIndex which inventory slot to equip
	 * @return true = success / false = failure
	 */
	protected boolean switchEquipment(int equipmentIndex) {
		if(equipmentIndex >= inventory.length || equipmentIndex < 0){
			throw new IllegalArgumentException("equipmentIndex(" + equipmentIndex + ") is out of bounds");
		}
		
		if(inventory[equipmentIndex] != null){
			Equipment old = currentEquipment;
			if(currentEquipment.unequip()){
				if(inventory[equipmentIndex].equip()){
					currentEquipment = inventory[equipmentIndex];
					return true;
				}
				else{ //cannot equip new object for some reason
					if(old.equip()){
						return false; //did not equip new object
					}
					else{ //cannot re-equip old object
						currentEquipment = null;
						throw new IllegalStateException("equipment switch failed and could not switch back.");
					}
				}
			}
			else{
				return false; //cannot unequip current item
			}
		}
		else{
			return false; //new slot is empty
		}
	}
	
	@Override
	public String toJSONString(){
		//TODO
		return null;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	
}
