package com.cycapservers.game;

import java.util.ArrayList;
import java.util.List;

import com.cycapservers.game.database.GameEventType;
import com.cycapservers.game.database.GameEventsEntity;

public abstract class Character extends CollidingEntity {
	
	/**
	 * The game in which this character is playing
	 */
	private Game game;

	private int team;
	private String class_name;
	
	private Equipment[] inventory;
	private Equipment currentEquipment;
	private Item item_slot;
	
	private int health;
	private int max_health;
	private boolean alive;
	private long last_time_of_death;
	/**
	 * The number of lives remaining for this character. Decremented upon death. Once you reach 0, you can no longer respawn. -1 means you have unlimited lives.
	 */
	private int lives_remaining;
	
	private double speed;
	private int visibility;
	
	private List<Buff> active_buffs;
	private boolean is_invincible;
	private double speed_boost;
	private double damage_boost;

	public Character(String id, Drawable model, Game game, int team, String class_name, int max_health, double speed, int visibility, int inventory_size) {
		super(id, model, new CircleCollider(model.getDrawPosition(), Math.max(model.getDrawWidth(), model.getDrawHeight()/2.0)), 10); //TODO pick an appropriate priority for characters
		this.setGame(game);
		this.team = team;
		this.class_name = class_name;
		this.max_health = max_health;
		this.health = max_health;
		this.speed = speed;
		this.visibility = visibility;
		
		inventory = new Equipment[inventory_size];
		
		this.setAlive(true);
		this.setLast_time_of_death(System.currentTimeMillis());
		
		this.active_buffs = new ArrayList<Buff>();
		this.is_invincible = false;
		this.speed_boost = 1.0;
		this.damage_boost = 1.0;
		
		this.setItem_slot(null);
		this.resetClass();
	}
	
	////ABSTRACT METHODS////
	public abstract void respawn();
	
	/**
	 * resets equipment, health, etc. to match the class spawn state
	 */
	public abstract void resetClass();

	@Override
	public abstract String toJSONString();
	
	////IMPLEMENTED METHODS////
	public void takeDamage(DamageDealer d) {
		if(d.getOwnerTeam() != this.team || !this.getGame().isFriendly_fire()){
			if(!this.is_invincible){
				this.health -= d.getDamageAmount();
			}
			if(this.health <= 0){
				this.die(); //idk what this is gonna do yet
				getGame().addGameEvent(new GameEventsEntity(getGame().getId(), GameEventType.kill, d.getOwnerEntityId(), this.getEntity_id(), d.getDeathType()));
			}
		}
	}
	
	public void takeHeals(int amount) {
		this.health = Math.min(this.max_health, this.health + amount);
	}
	
	public void die(){
		this.setAlive(false);
		if(this.getLives_remaining() != -1)
			this.setLives_remaining(this.getLives_remaining() - 1);
		this.setLast_time_of_death(System.currentTimeMillis());
		if(this.getItem_slot() !=  null) {
			this.getItem_slot().drop();
			this.setItem_slot(null);
		}
		
		this.setPosition(this.getGame().getGraveyardPosition());
	}
	
	protected void useItem() {
		if(this.getItem_slot() == null){
			return;
		}
		else{
			if(this.getItem_slot().use()) {
				this.setItem_slot(null);
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
			Equipment old = getCurrentEquipment();
			if(getCurrentEquipment().unequip()){
				if(inventory[equipmentIndex].equip()){
					setCurrentEquipment(inventory[equipmentIndex]);
					return true;
				}
				else{ //cannot equip new object for some reason
					if(old.equip()){
						return false; //did not equip new object
					}
					else{ //cannot re-equip old object
						setCurrentEquipment(null);
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
	public void onCollision(Collidable other) {
		if(other instanceof Item){
			if(this.getItem_slot() == null){
				Item i = (Item) other;
				i.pickUp(this);
			}
		}
		else if(other instanceof Wall){
			double delta_x = getX() - getPreviousPosition().getX();
			double delta_y = getY() - getPreviousPosition().getY();
			
			setPosition(getPreviousPosition());
			int max_depth = 4;
			for(int i = 1; i <= max_depth; i++){ //get as close to the wall as we can by successive approximation
				if(!isColliding(other)){
					setX(getX() + (delta_x / Math.pow(2, i)));
					setY(getY() + (delta_y / Math.pow(2, i)));
				}
				else{
					setX(getX() - (delta_x / Math.pow(2, i)));
					setY(getY() - (delta_y / Math.pow(2, i)));
				}
			}
			
		}
		else if(other instanceof Character){
			return;
		}
		
	}
	
	////GETTERS AND SETTERS////
	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public long getLast_time_of_death() {
		return last_time_of_death;
	}

	public void setLast_time_of_death(long last_time_of_death) {
		this.last_time_of_death = last_time_of_death;
	}

	public Equipment getCurrentEquipment() {
		return currentEquipment;
	}

	private void setCurrentEquipment(Equipment currentEquipment) {
		this.currentEquipment = currentEquipment;
	}

	public Item getItem_slot() {
		return item_slot;
	}

	public void setItem_slot(Item item_slot) {
		this.item_slot = item_slot;
	}
	
	/**
	 * Returns the speed of this character, multiplied by any current speed boosts
	 * @return
	 */
	public double getSpeed(){
		return this.speed * this.speed_boost;
	}
	
	public void setSpeed(double newSpeed){
		this.speed = newSpeed;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public int getLives_remaining() {
		return lives_remaining;
	}

	public void setLives_remaining(int lives_remaining) {
		this.lives_remaining = lives_remaining;
	}
	
	public Equipment[] getInventory() {
		return inventory;
	}

	public int getMax_health() {
		return max_health;
	}

	public void applyBuff(Buff buff) {
		switch(buff.getType()) {
			case Speed:
				speed_boost *= buff.getMult_factor();
				active_buffs.add(buff);
				break;
				
			case Damage:
				damage_boost *= buff.getMult_factor();
				active_buffs.add(buff);
				break;
				
			case Invincibility:
				is_invincible = true;
				active_buffs.add(buff);
				break;
				
			default:
				throw new UnsupportedOperationException("The buff type you have passed is not supported(" + buff.getType() + ")");
				
		}
	}
	
}
