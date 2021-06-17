package com.cycapservers.game.entities;

import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.input.ComputerInputComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.matchmaking.Game;

public class ComputerCharacter extends Character {

	public ComputerCharacter(CollisionComponent c, DrawingComponent model, Game game, Team team, CharacterClass class_name, int inventory_size, int starting_lives) 
	{
		super(new PositionComponent(), c, model, new ComputerInputComponent(), game, team, class_name, inventory_size, starting_lives);
	}

	@Override
	public void respawn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
