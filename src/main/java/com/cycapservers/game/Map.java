package com.cycapservers.game;

import com.cycapservers.JSON_Stringable;

public class Map implements JSON_Stringable {
	
	private Wall[] walls;
	private Position graveyardPosition;

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

}
