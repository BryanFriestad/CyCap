package com.cycapservers.game.database;

import java.util.ArrayList;

public enum GameType {
	ctf, tdm, ffa;
	
	public static ArrayList<String> gameTypesAsStrings(){
		ArrayList<String> result = new ArrayList<String>();
		for(GameType t : values()){
			result.add(t.toString());
		}
		return result;
	}
}
