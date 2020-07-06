package com.cycapservers.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lobby {
	
	//params
	private String join_code;
	private LobbyType type;
	
	//internal
	/**
	 * The last game that was played. It is already completed. 
	 * Used to show after-game reports. Can be null if this is a new lobby.
	 */
	private Game previous_game;
	/**
	 * The game that is currently playing or waiting to start. Should never be null.
	 */
	private Game current_game;
	private List<String> player_ids;
	private HashMap<String, Team> player_teams;
	private HashMap<String, CharacterClass> selected_classes;
	
	public Lobby(String join_code, LobbyType type) {
		super();
		this.join_code = join_code;
		this.type = type;
		previous_game = null;
		
		//set the starting game
		switch(type){
			case ctf:
				current_game = new CaptureTheFlag(0, new Map(), false, 0, 0, false, 0, 0);
				break;
				
			case ffa:
	//			return GameType.ffa;
				throw new UnsupportedOperationException("FFA not available yet");
				
			case mixed:
				throw new UnsupportedOperationException("Mixed Lobby not available yet");
	//			return GameType.values()[RANDOM.nextInt(GameType.values().length)];
				
			case tdm:
				current_game = new TeamDeathMatch(0, new Map(), null, false, 0, 0, false, 0, 0);
				break;
				
			default:
				throw new IllegalArgumentException("Invalid LobbyType passed as parameter");
		}
		
		player_ids = new ArrayList<String>();
		player_teams = new HashMap<String, Team>();
		selected_classes = new HashMap<String, CharacterClass>();
	}

	public void joinLobby(String client_id){
		if(player_ids.contains(client_id)) throw new IllegalArgumentException("The given client_id is already in this lobby.");
		
		player_ids.add(client_id);
		selected_classes.put(client_id, CharacterClass.Recruit);
		player_teams.put(client_id, nextBalancedTeam());
	}
	
	/**
	 * Gets the next team such that the number of players per team is as balanced as can be.
	 * Before calling this function, the player's client id should be added to the list
	 * of client ids.
	 * @return Enum - the team 
	 */
	private Team nextBalancedTeam(){
		//Get the map of players needed per team to reach the max values given by the current game
		HashMap<Team, Integer> chars_per_team = current_game.getMax_characters_per_team();
		for(Team t : player_teams.values()){
			chars_per_team.put(t, chars_per_team.get(t) - 1); //for each already assigned player, subtract one from the totals needed
		}
		
		//find the team which has the most players left until full
		Team most_players_needed = null;
		int most_players = 0;
		for(Team t : chars_per_team.keySet()){
			int temp = chars_per_team.get(t); 
			if(temp > most_players){
				most_players_needed = t;
				most_players = temp;
			}
		}
		
		return most_players_needed;
	}
	
	public boolean containsUser(String client_id){
		return player_ids.contains(client_id);
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
	
	public boolean canPlayerJoin(String client_id){
		return !current_game.isStarted() && player_ids.size() < current_game.getMax_players();
	}
	
	public int getPlayerCount(){
		return player_ids.size();
	}
	
	public String getTimeRemaining(){
		return "0:00"; //TODO
	}

	public List<String> getPlayer_ids() {
		return player_ids;
	}

	public HashMap<String, Team> getPlayer_teams() {
		return player_teams;
	}

	public HashMap<String, CharacterClass> getSelected_classes() {
		return selected_classes;
	}
	
}
