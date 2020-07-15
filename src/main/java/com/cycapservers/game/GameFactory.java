package com.cycapservers.game;

import com.cycapservers.game.database.GameType;

public class GameFactory{
	
	private static GameFactory instance = new GameFactory();
	
	private GameFactory(){}
	
	public static GameFactory getInstance(){
		return instance;
	}

	public Game getGame(GameType type){
		switch(type){
			case ctf:
				return new CaptureTheFlag();
				
			case ffa:
				throw new UnsupportedOperationException("FFA not yet available");
				
			case tdm:
				return new TeamDeathMatch();
				
			default:
				return null;
				
		}
	}

}
