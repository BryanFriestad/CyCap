package com.cycapservers.game;

import java.util.HashMap;
import java.util.List;

public class Lobby {
	
	//params
	private String join_code;
	private LobbyType type;
	
	//internal
	private Game previousGame;
	private Game currentGame;
	private List<String> player_ids;
	private HashMap<String, Integer> player_teams;
	private HashMap<String, CharacterClass> selected_classes;
	
	public void joinLobby(String client_id){
		
	}
	
	public void leaveLobby(String client_id){
		
	}
	
	public LobbyType getLobbyType(){
		return type;
	}
	
	//start game
	//switch team
	//switch class
	//vote for map
	//get last game stats
	
	public int getPlayerCount(){
		return player_ids.size();
	}
	
}
