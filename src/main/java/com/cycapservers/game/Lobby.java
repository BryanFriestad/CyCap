package com.cycapservers.game;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.cycapservers.game.database.GameType;

public class Lobby {
	
	private static int default_game_start_timer_ms = 60000;
	
	//params
	private String join_code; //a code needed to join this lobby via invite
	//lobby options
	private boolean mode_voting_enabled; //whether or not players can vote on game modes in this lobby type at all
	private boolean map_voting_enabled;
	private boolean team_selection_enabled;
	private List<GameType> available_game_types;
	private List<Map> available_maps;
	private int max_players;
	private LobbyType type; //used to check if this lobby fits a players new lobby search request
	
	//internal
	private Game current_game; //The game that is currently playing or waiting to start. Should not be null once the game type is selected
	
	private GameType next_game_type;
	private Map next_game_map;
	private long next_game_start;
	
	private List<String> player_ids;
	private HashMap<String, CharacterClass> selected_classes;
	
	//various internal voting-enabling members
	private boolean mode_voting_active; //whether players can currently vote on game mode, or if it is already decided;
	private GameType mode_opt_1;
	private GameType mode_opt_2;
	private HashMap<String, Integer> player_mode_votes;
	
	private boolean map_voting_active;
	private Map map_opt_1;
	private Map map_opt_2;
	private HashMap<String, Integer> player_map_votes;
	
	private boolean team_switching_active; 
	private HashMap<String, Team> player_teams;
	
	public Lobby(String join_code, LobbyType type, ArrayList<GameType> available_game_types, int max_players, boolean mode_voting_enabled, boolean map_voting_enabled, boolean team_selection_enabled, ArrayList<Map> available_maps) {
		super();
		this.join_code = join_code;
		this.type = type;
		this.available_game_types = available_game_types;
		this.max_players = max_players;
		this.mode_voting_enabled = mode_voting_enabled;
		this.map_voting_enabled = map_voting_enabled;
		this.team_selection_enabled = team_selection_enabled;
		this.available_maps = available_maps;
		
		mode_voting_active = true;
		map_voting_active = false;
		team_switching_active = false; 
		
		this.current_game = null;
		
		//pick mode opt 1
		//pick mode opt 2
		//pick map opt 1
		//pick map opt 2
		
		this.player_mode_votes = new HashMap<String, Integer>();
		this.player_map_votes = new HashMap<String, Integer>();
		
		player_ids = new ArrayList<String>();
		player_teams = new HashMap<String, Team>();
		selected_classes = new HashMap<String, CharacterClass>();
		next_game_start = System.currentTimeMillis() + default_game_start_timer_ms;
	}
	
	public void update(){
		//update lobby stuff
		long time_remaining = this.next_game_start - System.currentTimeMillis();

		if(time_remaining <= 0){
			if(current_game == null)
				throw new IllegalStateException("current_game is null at the end of the lobby timer");
			current_game.startGame(player_ids, player_teams, selected_classes);
		}
		else if(time_remaining <= 5000){
			this.team_switching_active = false;
		}
		else if(time_remaining <= 20000){
			//lock in map
			this.map_voting_active = false;
			current_game.setMap(next_game_map);
		}
		else if(time_remaining <= 40000){
			//lock in game_mode
			this.mode_voting_active = false;
			this.current_game = GameFactory.getInstance().getGame(next_game_type);
			this.map_voting_active = true;
			this.team_switching_active = true;
			for(String username : player_ids){
				player_teams.put(username, nextBalancedTeam());
			}
		}
	}

	public void joinLobby(String client_id){
		if(player_ids.contains(client_id)) throw new IllegalArgumentException("The given client_id is already in this lobby.");
		
		player_ids.add(client_id);
		selected_classes.put(client_id, CharacterClass.Recruit);
		if(this.team_switching_active)
			player_teams.put(client_id, nextBalancedTeam());
	}
	
	/**
	 * Gets the next team such that the number of players per team is as balanced as can be.
	 * Before calling this function, the player's client id should be added to the list
	 * of client ids.
	 * @return Enum - the team 
	 */
	private Team nextBalancedTeam(){
		if(current_game == null)
			throw new IllegalStateException("current game must be not null before calling this function");
		
		//Get the map of players needed per team to reach the max values given by the current game
		HashMap<Team, Integer> chars_per_team = this.current_game.getMax_characters_per_team();
		for(Team t : player_teams.values()){
			chars_per_team.put(t, chars_per_team.get(t) - 1); //for each already assigned player, subtract one from the totals needed
		}
		
		System.out.println(this.current_game.getMax_characters_per_team());
		
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
		long time_diff = this.next_game_start - System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		Date date = new Date(time_diff);
		return sdf.format(date);
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
	
	public void voteOnGameType(String username, Integer vote){
		if(!this.mode_voting_active)
			return;
		throw new UnsupportedOperationException();
	}
	
	public void voteOnMap(String username, Integer vote){
		if(!this.map_voting_active)
			return;
		throw new UnsupportedOperationException();
	}
	
	public void switchTeam(String username, Team t){
		if(!this.team_switching_active)
			return;
		throw new UnsupportedOperationException();
	}
	
}
