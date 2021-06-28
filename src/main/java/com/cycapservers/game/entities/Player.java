package com.cycapservers.game.entities;

import org.json.JSONObject;
import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.input.ClientInputComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.matchmaking.Game;

public class Player extends Character {
	
	public Player(CollisionComponent c, DrawingComponent model, Game game, Team team, CharacterClass class_name, int inventory_size, int starting_lives, String client_id) 
	{
		super(new PositionComponent(64, 64), c, model, new ClientInputComponent(), game, team, class_name, inventory_size, starting_lives, client_id);
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
	
	public JSONObject GetJsonObjectForMyServerMessage()
	{
		return null; // TODO
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
	
	public String GetInputPasscode()
	{
		return ((ClientInputComponent) input_comp).GetInputPasscode();
	}
}