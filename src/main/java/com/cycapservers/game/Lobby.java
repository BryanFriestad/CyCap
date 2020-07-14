package com.cycapservers.game;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.cycapservers.game.database.GameType;

public class Lobby {
	
	private static int default_game_start_timer_ms = 45000;
	
	//params
	private String join_code;
	private LobbyType type;
	
	//internal
	/**
	 * The game that is currently playing or waiting to start. Can be null if the game has not started
	 */
	private Game current_game;
	
	private GameType next_game_type;
	private Map next_game_map;
	private long next_game_start;
	
	private HashMap<Team, Integer> max_characters_per_team;
	private List<String> player_ids;
	private HashMap<String, Team> player_teams;
	private HashMap<String, CharacterClass> selected_classes;
	private int max_players;
	
	//lobby options
	private boolean mode_voting_enabled; //whether or not players can vote on game modes in this lobby type at all
	private boolean mode_voting_active; //whether players can current vote on game mode, or if it is already decided;
	private boolean map_voting_enabled;
	private boolean map_voting_active;
	private boolean team_selection_enabled;
	private boolean team_switching_active;
	
	public Lobby(String join_code, LobbyType type) {
		super();
		this.join_code = join_code;
		this.type = type;
		
		this.current_game = null;
		
		//set the starting game
		switch(type){
			case ctf:
				next_game_type = GameType.ctf;
				max_characters_per_team = new HashMap<Team, Integer>();
				max_characters_per_team.put(Team.Red, 4);
				max_characters_per_team.put(Team.Blue, 4);
//				current_game = new CaptureTheFlag(0, new Map(), false, -1, 10000, true, 3*60*1000, 8);
				break;
				
			case ffa:
	//			return GameType.ffa;
				throw new UnsupportedOperationException("FFA not available yet");
				
			case mixed:
				throw new UnsupportedOperationException("Mixed Lobby not available yet");
	//			return GameType.values()[RANDOM.nextInt(GameType.values().length)];
				
			case tdm:
				next_game_type = GameType.tdm;
				max_characters_per_team = new HashMap<Team, Integer>();
				max_characters_per_team.put(Team.Red, 4);
				max_characters_per_team.put(Team.Blue, 4);
//				current_game = new TeamDeathMatch(0, new Map(), false, -1, 5000, true, 2*60*1000, 8);
				break;
				
			default:
				throw new IllegalArgumentException("Invalid LobbyType passed as parameter");
		}
		
		this.max_players = Utils.sumIntArray((Integer[]) max_characters_per_team.values().toArray());
		
		player_ids = new ArrayList<String>();
		player_teams = new HashMap<String, Team>();
		selected_classes = new HashMap<String, CharacterClass>();
		next_game_start = System.currentTimeMillis() + default_game_start_timer_ms;
	}
	
	public void update(){
		//update lobby stuff
		long time_remaining = this.next_game_start - System.currentTimeMillis();

		if(time_remaining <= 0){
			switch(this.next_game_type){
				case ctf:
					break;
					
				case ffa:
					break;
					
				case tdm:
					break;
					
				default:
					break;
			}
		}
		//if less than 40 seconds, lock in the game mode and enable team switching
		//if less than 20 seconds, lock in the map and instantiate the game
		//if less than 5 seconds until start time, lock in the map
		//if less than 0 seconds until start time, start game
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
		@SuppressWarnings("unchecked")
		HashMap<Team, Integer> chars_per_team = (HashMap<Team, Integer>) this.max_characters_per_team.clone();
		for(Team t : player_teams.values()){
			chars_per_team.put(t, chars_per_team.get(t) - 1); //for each already assigned player, subtract one from the totals needed
		}
		
		System.out.println(this.max_characters_per_team);
		
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
	
}
