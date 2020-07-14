package com.cycapservers.game;

import com.cycapservers.game.database.GameType;

public class GameFactory{
	
	private static GameFactory instance = new GameFactory();
	
	private GameFactory(){}
	
	public static GameFactory getInstance(){
		return instance;
	}

	public Game getGame(GameType type, String join_code){
		switch(type){
			case ctf:
				return new CaptureTheFlag(join_code);
				
			case ffa:
				throw new UnsupportedOperationException("FFA not yet available");
				
			case tdm:
				return new TeamDeathMatch(join_code);
				
			default:
				return null;
				
		}
	}

}
