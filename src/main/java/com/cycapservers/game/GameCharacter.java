package com.cycapservers.game;

import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.game.database.GameEventType;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.database.PlayerStats;

public abstract class GameCharacter extends Entity {

	private int team;
	private String role;
	
	protected PlayerStats stats;
	
	protected Weapon weapon1;
	protected Weapon weapon2;
	protected Weapon weapon3;
	protected Weapon weapon4;
	protected Weapon currentWeapon;
	protected Item item_slot;
	
	protected int health;
	protected int max_health;
	protected boolean isDead;
	protected long lastDeathTime;
	
	protected double speed; //TODO determine if this needs to be sent
	protected int visibility;
	
	protected boolean is_invincible;
	protected double speed_boost;
	protected double damage_boost;
	
	public GameCharacter(int id, int sprIdx, double x, double y, double w, double h, double r, double a, String entity_id, int team, String role) {
		super(id, sprIdx, x, y, w, h, r, a, entity_id);
		this.spriteIndex = Utils.getSpriteIndexFromTeam(team);
		this.setTeam(team);
		this.setRole(role);
		
		this.isDead = false;
		this.is_invincible = false;
		this.speed_boost = 1.0;
		this.damage_boost = 1.0;
		
		this.item_slot = null;
		Utils.setRole(this);
		
		this.stats = new PlayerStats(this);
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public void takeDamage(GameState game, int amount, GameCharacter c) {
		if(!this.is_invincible){
			this.health -= amount;
		}
		if(this.health <= 0){
			this.die(); //idk what this is gonna do yet
			if(!c.get_entity_id().equals(this.get_entity_id())) {
				c.stats.addKill(); //adds a kill if you didn't kill yourself lmao
				game.addGameEvent(new GameEventsEntity(game.game_id, GameEventType.kill, c.get_entity_id(), this.get_entity_id(), c.currentWeapon.name));
			}
		}
	}
	
	public void takeHeals(int amount) {
		this.health = Math.min(this.max_health, this.health + amount);
	}
	
	protected abstract void respawn(GameState g);
	
	public abstract void die();
	
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
	
	protected void switchWeapon(int weaponNum) {
		switch(weaponNum){
			case 1:
				if(this.weapon1 != null){
					this.currentWeapon = this.weapon1;
				}
				break;
				
			case 2:
				if(this.weapon2 != null){
					this.currentWeapon = this.weapon2;
				}
				break;
				
			case 3:
				if(this.weapon3 != null){
					this.currentWeapon = this.weapon3;
				}
				break;
				
			case 4:
				if(this.weapon4 != null){
					this.currentWeapon = this.weapon4;
				}
				break;
				
			default:
				throw new IllegalArgumentException("Illegal argument in player.switchWeapon()");
		}
	}
}
