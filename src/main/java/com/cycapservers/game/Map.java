package com.cycapservers.game;

import java.util.HashMap;
import java.util.List;

import com.cycapservers.JSON_Stringable;

public class Map implements JSON_Stringable {
	
	private Wall[] walls;
	private Position graveyardPosition;
	private Spawn[] spawns;
	
	//CTF specific components
	private HashMap<Integer, GridLockedPosition> team_flag_locations;

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

	public Wall[] getWalls() {
		return walls;
	}

	public Position getGraveyardPosition() {
		return graveyardPosition;
	}
	
	/**
	 * Adds all of the walls, background tiles, and other persistent entities to the given game state
	 * @param type
	 * @param state
	 */
	public void initializeGameState(GameType type, GameState state) {
		
	}
	
	public List<PathfindingNode> getPathfindingNodes(){
		throw new UnsupportedOperationException();
	}

}
