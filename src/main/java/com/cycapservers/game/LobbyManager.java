package com.cycapservers.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbyManager {
	
	private List<Lobby> lobbies;

	public LobbyManager() {
		lobbies = new ArrayList<Lobby>();
	}
	
	public List<Lobby> getLobbies(){
		return lobbies;
	}
	
	public void addNewLobby(Lobby l){
		lobbies.add(l);
	}
	
	public HashMap<LobbyType, Integer> getAvailableLobbyTypes(){
		HashMap<LobbyType, Integer> map = new HashMap<LobbyType, Integer>();
		for(Lobby l : lobbies){
			Integer current_count = map.putIfAbsent(l.getLobbyType(), l.getPlayerCount());
			if(current_count != null){
				map.put(l.getLobbyType(), current_count + l.getPlayerCount());
			}
		}
		return map;
	}

}
