package com.cycapservers.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class LobbyManager {
	
	private List<Lobby> lobbies;

	public LobbyManager() {
		lobbies = new ArrayList<Lobby>();
	}
	
	public List<Lobby> getLobbies(){
		return lobbies;
	}
	
	/**
	 * Finds a valid lobby for the given player and requested lobby type
	 * @param client_id The username of the requesting player
	 * @param type An enumerated type referring to the type of lobby the player wishes to find
	 * @return Returns a randomly selected lobby from the list of all valid lobbies
	 */
	public Lobby findValidLobby(String client_id, LobbyType type){
		List<Lobby> valid_lobbies = new ArrayList<Lobby>();
		for(Lobby l : lobbies){
			if(l.getLobbyType().equals(type) && l.canPlayerJoin(client_id)){
				valid_lobbies.add(l);
			}
		}
		
		if(valid_lobbies.size() == 0){
			// create new lobby of the requested type
			return null; //TODO
		}
		else{
			return valid_lobbies.get(Utils.RANDOM.nextInt(valid_lobbies.size()));
		}
	}
	
	public void closeConnection(WebSocketSession s){
		throw new UnsupportedOperationException();
	}
	
	public void handleTextMessage(WebSocketSession session, TextMessage textMessage){
		throw new UnsupportedOperationException();
	}
	
	public LobbyType[] getAvailableLobbyTypes(){
		LobbyType[] valid_types = {LobbyType.ctf, LobbyType.tdm};
		return valid_types;
	}
	
	public HashMap<LobbyType, Integer> getPlayerCountsByLobbyType(){
		HashMap<LobbyType, Integer> map = new HashMap<LobbyType, Integer>();
		for(Lobby l : lobbies){
			Integer current_count = map.putIfAbsent(l.getLobbyType(), l.getPlayerCount());
			if(current_count != null){
				map.put(l.getLobbyType(), current_count + l.getPlayerCount());
			}
		}
		
		for(LobbyType t : getAvailableLobbyTypes()){
			if(!map.containsKey(t)){
				map.put(t, 0);
			}
		}
		
		return map;
	}

}
