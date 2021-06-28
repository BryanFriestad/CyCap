package com.cycapservers.game.entities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.cycapservers.game.Buff;
import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.DamageDealer;
import com.cycapservers.game.Team;
import com.cycapservers.game.Utils;
import com.cycapservers.game.components.collision.CharacterCollisionComponent;
import com.cycapservers.game.components.collision.CircleCollider;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.input.InputComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.database.GameEventType;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.equipment.Equipment;
import com.cycapservers.game.matchmaking.Game;

public abstract class Character extends CollidingDrawableEntity 
{
	public static final int DEFAULT_INVENTORY_SIZE = 4;
	
	protected InputComponent input_comp;
	
	/**
	 * The game in which this character is playing
	 */
	private Game game;

	private Team team;
	private CharacterClass class_name;
	
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
	
	/**
	 * The speed of the character in grid spaces per second.
	 */
	private double speed;
	private int visibility;
	
	private List<Buff> active_buffs;
	private boolean is_invincible;
	private double speed_boost;
	private double damage_boost;

	public Character(PositionComponent p, CollisionComponent c, DrawingComponent model, InputComponent i, Game game, Team team, CharacterClass class_name, int inventory_size, int starting_lives, String client_id) 
	{
		super(p, c, model); //TODO pick an appropriate priority for characters
		this.entity_id = client_id;
		
		input_comp = i;
		RegisterComponent(input_comp);
		this.setGame(game);
		this.team = team;
		this.class_name = class_name;
		
		inventory = new Equipment[inventory_size];
		
		this.setAlive(true);
		this.lives_remaining = starting_lives;
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
	 * resets equipment, health, visibility, speed to match the class spawn state
	 */
	public void resetClass()
	{
		visibility = 5;
		speed = 6;
		health = max_health = 100;
	}
	
	////IMPLEMENTED METHODS////
	public void takeDamage(DamageDealer d) 
	{
		if (d.getOwnerTeam() != this.team || !this.getGame().isFriendly_fire())
		{
			if (!this.is_invincible)
			{
				this.health -= d.getDamageAmount();
			}
			if (this.health <= 0)
			{
				this.die(); //idk what this is gonna do yet
				getGame().addGameEvent(new GameEventsEntity(getGame().getId(), GameEventType.kill, d.getOwnerEntityId(), this.getEntity_id(), d.getDeathType()));
			}
		}
	}
	
	public void takeHeals(int amount) 
	{
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
	
	public void useItem() {
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
	public boolean switchEquipment(int equipmentIndex) 
	{
		if (equipmentIndex >= inventory.length || equipmentIndex < 0)
		{
			throw new IllegalArgumentException("equipmentIndex(" + equipmentIndex + ") is out of bounds");
		}
		
		if (inventory[equipmentIndex] != null)
		{
			Equipment old = getCurrentEquipment();
			if(getCurrentEquipment().unequip())
			{
				if (inventory[equipmentIndex].equip())
				{
					setCurrentEquipment(inventory[equipmentIndex]);
					return true;
				}
				else
				{ //cannot equip new object for some reason
					if (old.equip())
					{
						return false; //did not equip new object
					}
					else
					{ //cannot re-equip old object
						setCurrentEquipment(null);
						throw new IllegalStateException("equipment switch failed and could not switch back.");
					}
				}
			}
			else
			{
				return false; //cannot unequip current item
			}
		}
		else
		{
			return false; //new slot is empty
		}
	}
	
	////GETTERS AND SETTERS////
	public CharacterClass getClass_name() {
		return class_name;
	}

	public void setClass_name(CharacterClass class_name) {
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

	public Equipment getCurrentEquipment() 
	{
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
	 * Returns the speed of this character (in grids/second), multiplied by any current speed boosts
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
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
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
