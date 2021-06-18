package com.cycapservers.game.matchmaking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.game.Utils;

public class LobbyManager 
{	
	private static final int join_code_length = 8;
	
	private List<Lobby> lobbies;
	private List<String> used_lobby_join_codes;

	public LobbyManager() 
	{
		lobbies = new ArrayList<Lobby>();
		used_lobby_join_codes = new ArrayList<String>();
	}
	
	public List<Lobby> getLobbies()
	{
		return lobbies;
	}
	
	/**
	 * Finds a valid lobby for the given player and requested lobby type
	 * @param client_id The username of the requesting player
	 * @param type An enumerated type referring to the type of lobby the player wishes to find
	 * @return Returns a randomly selected lobby from the list of all valid lobbies
	 */
	public Lobby findValidLobby(String client_id, LobbyType type)
	{
		List<Lobby> valid_lobbies = new ArrayList<Lobby>();
		for (Lobby l : lobbies)
		{
			if (l.getLobbyType().equals(type) && l.canPlayerJoin(client_id))
			{
				valid_lobbies.add(l);
			}
		}
		
		if (valid_lobbies.size() == 0)
		{
			String s = Utils.getGoodRandomString(used_lobby_join_codes, join_code_length);
			used_lobby_join_codes.add(s);
			Lobby new_lobby = LobbyFactory.getInstance().getLobby(type, s);
			System.out.println("lobby factory done");
			lobbies.add(new_lobby);
			return new_lobby;
		}
		else
		{
			return valid_lobbies.get(Utils.RANDOM.nextInt(valid_lobbies.size()));
		}
	}
	
	/**
	 * Retrieves the Lobby that the given user belongs to,
	 * or null if there is no such lobby
	 * @param client_id
	 * @return
	 */
	public Lobby findLobbyOfUser(String client_id)
	{
		for (Lobby l : lobbies)
		{
			if(l.containsUser(client_id)) return l;
		}
		
		return null;
	}
	
	@Scheduled(fixedRate = 500)
	public void updateLobbies()
	{
		for (Lobby l : lobbies)
		{
			//check if game is completed in lobby and if no players
			// are left in the lobby. if so, add to a list for 
			// deletion
			l.update();
		}
	}
	
	//schedule every 100 ms
	public void sendGameStates()
	{
		
	}
	
	public LobbyType[] getAvailableLobbyTypes()
	{
		LobbyType[] valid_types = {LobbyType.tdm};
		return valid_types;
	}
	
	public HashMap<LobbyType, Integer> getPlayerCountsByLobbyType()
	{
		HashMap<LobbyType, Integer> map = new HashMap<LobbyType, Integer>();
		for (Lobby l : lobbies)
		{
			Integer current_count = map.putIfAbsent(l.getLobbyType(), l.getPlayerCount());
			if (current_count != null)
			{
				map.put(l.getLobbyType(), current_count + l.getPlayerCount());
			}
		}
		
		for (LobbyType t : getAvailableLobbyTypes())
		{
			if (!map.containsKey(t))
			{
				map.put(t, 0);
			}
		}
		
		return map;
	}
}
