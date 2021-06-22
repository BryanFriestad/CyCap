package com.cycapservers.game.entities;

import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.input.ClientInputComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.matchmaking.Game;

public class Player extends Character {
	
	public Player(CollisionComponent c, DrawingComponent model, Game game, Team team, CharacterClass class_name, int inventory_size, int starting_lives) {
		super(new PositionComponent(), c, model, new ClientInputComponent(), game, team, class_name, inventory_size, starting_lives);
	}
	
	@Override
	public boolean update() 
	{	
		if (!this.isAlive())
		{
			if ((System.currentTimeMillis() - this.getLast_time_of_death()) > this.getGame().getRespawn_time() && this.getLives_remaining() > 0) 
			{
				this.respawn();
			}
		}
		else 
		{
			if (!input_comp.update(this)) return false;
		}
		
		if (!super.update()) return false;
		
		return true;
	}
	
	@Override
	public String toJSONString(){
//		if(this.get_entity_id().equals(client_id)) {
//			String output = "";
//			output += "000,";
//			output += this.highestHandledSnapshot + ",";
//			output += super.toDataString(client_id) + ",";
//			output += this.getRole() + ",";
//			output += this.getTeam() + ",";
//			output += this.getCurrentEquipment().toString() + ",";
//			if(this.getItem_slot() == null) {
//				output += "empty" + ",";
//			}
//			else {
//				output += this.getItem_slot().imageId + ",";
//			}
//			output += this.health + ",";
//			output += this.is_invincible + ",";
//			output += this.speed_boost + ",";
//			output += this.damage_boost + ",";
//			output += this.visibility;
//			return output;
//		}
//		else {
//			String output = "";
//			output += "020,";
//			output += super.toDataString(client_id);
//			return output;
//		}
		return null; //TODO
	}

	@Override
	/**
	 * respawns the player into a proper respawn node, resets their weapons and health
	 */ 
	public void respawn() {
		Spawn n = this.getGame().getValidSpawnNode(this.getTeam());
		this.setX(n.getX());
		this.setY(n.getY());
		
		this.setAlive(true);
		//reset ammo and health
		this.resetClass();
		return;
	}

	@Override
	public void resetClass() {
		// TODO Auto-generated method stub
		
	}
}