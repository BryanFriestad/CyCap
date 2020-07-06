package com.cycapservers.game;

import java.util.ArrayList;
import java.util.List;

public class Explosion extends CollidingEntity implements DamageDealer{
	
	//params
	private String owner_id;
	private int damage_amount;
	private double wall_damage_mult;
	private String kill_type;
	private Team owner_team;
	
	//internal
	/**
	 * A list of entities to keep track of which we have hurt already
	 */
	private List<String> hurt_entities;

	public Explosion(Drawable model, Collider c, int collision_priority, String owner_id, int damage_amount,
			double wall_damage_mult, String kill_type, Team owner_team) {
		super(model, c, collision_priority);
		this.owner_id = owner_id;
		this.damage_amount = damage_amount;
		this.wall_damage_mult = wall_damage_mult;
		this.kill_type = kill_type;
		this.owner_team = owner_team;
		this.hurt_entities = new ArrayList<String>();
	}
	
	@Override
	public void onCollision(Collidable other){
		if(other instanceof Character){
			Character other_character = (Character) other;
			if(hurt_entities.contains(other_character.getEntity_id()))
				return; //we've already dealt damage to this entitiy
			other_character.takeDamage(this);
			hurt_entities.add(other_character.getEntity_id());
		}
		else if(other instanceof Wall){
			Wall w = (Wall) other;
			if(hurt_entities.contains(w.getEntity_id()))
				return; //we've already dealt damage to this entitiy
			w.takeDamage((int) (damage_amount * wall_damage_mult));
			hurt_entities.add(w.getEntity_id());
		}
	}

	@Override
	public String getOwnerEntityId() {
		return owner_id;
	}

	@Override
	public int getDamageAmount() {
		return damage_amount;
	}

	@Override
	public String getDeathType() {
		return kill_type;
	}

	@Override
	public Team getOwnerTeam() {
		return owner_team;
	}
	
}
