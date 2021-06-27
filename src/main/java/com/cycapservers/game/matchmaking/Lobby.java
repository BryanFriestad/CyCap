package com.cycapservers.game.matchmaking;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.input.InputSnapshot;
import com.cycapservers.game.database.GameType;
import com.cycapservers.game.maps.Map;

public class Lobby {
	
	private static int default_game_start_timer_ms = 30000;
//	private static int default_game_start_timer_ms = 60000;
	
	//params
	private String join_code; //a code needed to join this lobby via invite
	//lobby options
//	private boolean mode_voting_enabled; //whether or not players can vote on game modes in this lobby type at all
//	private boolean map_voting_enabled;
//	private boolean team_selection_enabled;
	private List<GameType> available_game_types;
	private List<Map> available_maps;
	private int max_players;
	private LobbyType type; //used to check if this lobby fits a players new lobby search request
	
	//internal
	private Game current_game; //The game that is currently playing or waiting to start. Should not be null once the game type is selected
	
	private GameType next_game_type;
	private Map next_game_map;
	private long next_game_start;
	
	private List<IncomingPlayer> players;
	
	//various internal voting-enabling members
//	private boolean mode_voting_active; //whether players can currently vote on game mode, or if it is already decided;
//	private GameType mode_opt_1;
//	private GameType mode_opt_2;
//	private HashMap<String, Integer> player_mode_votes;
//	
//	private boolean map_voting_active;
//	private Map map_opt_1;
//	private Map map_opt_2;
//	private HashMap<String, Integer> player_map_votes;
	
//	private boolean team_switching_active;
	
	public Lobby(String join_code, LobbyType type, ArrayList<GameType> available_game_types, int max_players, boolean mode_voting_enabled, boolean map_voting_enabled, boolean team_selection_enabled, ArrayList<Map> available_maps) 
	{
		super();
		if(available_game_types.size() == 0) throw new IllegalArgumentException("available_game_types list must not be empty");
		if(available_maps.size() == 0) throw new IllegalArgumentException("available_maps list must not be empty");
		if(max_players < 1) throw new IllegalArgumentException("max_players (" + max_players + ") must be at least 1");
		
		this.join_code = join_code;
		this.type = type;
		this.available_game_types = available_game_types;
		this.max_players = max_players;
//		this.mode_voting_enabled = mode_voting_enabled;
//		this.map_voting_enabled = map_voting_enabled;
//		this.team_selection_enabled = team_selection_enabled;
		this.available_maps = available_maps;
		
//		mode_voting_active = true;
//		map_voting_active = false;
//		team_switching_active = false; 
		
		this.current_game = null;
		
		//pick mode opt 1
		//pick mode opt 2
		//pick map opt 1
		//pick map opt 2
		
//		this.player_mode_votes = new HashMap<String, Integer>();
//		this.player_map_votes = new HashMap<String, Integer>();
		
		players = new ArrayList<IncomingPlayer>();
		
		next_game_start = System.currentTimeMillis() + default_game_start_timer_ms;
		next_game_type = available_game_types.get(0);
		next_game_map = available_maps.get(0);
	}
	
	public void update()
	{
		//update lobby stuff
		long time_remaining = GetTimeRemaining();

		if (time_remaining <= 0)
		{
			if (current_game == null) throw new IllegalStateException("current_game is null at the end of the lobby timer");
			if (!current_game.isStarted()) current_game.startGame(players);
		}
		else if (time_remaining <= 5000)
		{
//			this.team_switching_active = false;
		}
		else if (time_remaining <= 20000)
		{
			//lock in map
//			this.map_voting_active = false;
			current_game.setMap(next_game_map);
		}
		else if (time_remaining <= 40000)
		{
			//lock in game_mode
//			this.mode_voting_active = false;
			this.current_game = GameFactory.getInstance().getGame(next_game_type);
//			this.map_voting_active = true;
//			this.team_switching_active = true;
			BalanceTeams();
		}
	}

	public void joinLobby(String client_id)
	{
		if (IsPlayerInLobby(client_id)) throw new IllegalArgumentException("The given client_id is already in this lobby.");
		if (players.size() >= max_players) throw new IllegalStateException("The lobby is already full.");
		players.add(new IncomingPlayer(client_id, CharacterClass.Recruit, Team.None));
	}
	
	public boolean IsPlayerInLobby(String client_id)
	{
		for (IncomingPlayer i : players)
		{
			if (i.GetClientId().equals(client_id)) return true;
		}
		return false;
	}
	
	private void BalanceTeams()
	{
		// Places unassigned players onto teams so they are balanced.
		if (current_game == null) throw new IllegalStateException("current game must be not null before calling this function");
		
		List<IncomingPlayer> unassigned_players = new ArrayList<IncomingPlayer>();
		
		// Get the map of players needed per team to reach the max values given by the current game
		HashMap<Team, Integer> characters_needed_per_team = this.current_game.getMax_characters_per_team();
		for (IncomingPlayer i : players)
		{
			Team t = i.GetTeam();
			if (t == Team.None)
			{
				unassigned_players.add(i);
			}
			else
			{
				characters_needed_per_team.put(t, characters_needed_per_team.get(t) - 1); //for each already assigned player, subtract one from the totals needed
			}
		}
		
		// Assign players to teams that need the most.
		for (IncomingPlayer i : unassigned_players)
		{
			Team most_needy_team = GetMostNeedyTeam(characters_needed_per_team);
			if (most_needy_team != Team.None)
			{
				i.SetTeam(most_needy_team);
				characters_needed_per_team.put(most_needy_team, characters_needed_per_team.get(most_needy_team) - 1);
			}
		}
	}
	
	private Team GetMostNeedyTeam(HashMap<Team, Integer> needed_per_team)
	{
		Team most_needy_team = Team.None;
		for (Team t : needed_per_team.keySet())
		{
			int characters_needed = needed_per_team.get(t);
			if (needed_per_team.get(most_needy_team) == null) throw new IllegalStateException("characters needed per team map does not have Team.None set");
			if (characters_needed > needed_per_team.get(most_needy_team))
			{
				most_needy_team = t;
			}
		}
		return most_needy_team;
	}
	
	public void leaveLobby(String client_id)
	{
		
	}
	
	public LobbyType getLobbyType()
	{
		return type;
	}
	
	//start game
	//switch team
	//switch class
	//vote for map
	//get last game stats
	
	public boolean canPlayerJoin(String client_id)
	{
		return !current_game.isStarted() && (getPlayerCount() < current_game.getMax_players());
	}
	
	public int getPlayerCount()
	{
		return players.size();
	}
	
	public long GetTimeRemaining()
	{
		return Math.max(0, this.next_game_start - System.currentTimeMillis());
	}
	
	public String getTimeRemainingString()
	{
		long time_diff = GetTimeRemaining();
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		Date date = new Date(time_diff);
		return sdf.format(date);
	}
	
	public void voteOnGameType(String username, Integer vote){
//		if(!this.mode_voting_active)
//			return;
		throw new UnsupportedOperationException();
	}
	
	public void voteOnMap(String username, Integer vote){
//		if(!this.map_voting_active)
//			return;
		throw new UnsupportedOperationException();
	}
	
	public void switchTeam(String username, Team t){
//		if(!this.team_switching_active)
//			return;
		throw new UnsupportedOperationException();
	}
	
	public String GetJoinCode()
	{
		return join_code;
	}
	
	public List<IncomingPlayer> GetPlayersInLobby()
	{
		return players;
	}
	
	public CharacterClass GetClassOfPlayer(String client_id)
	{
		if (!IsPlayerInLobby(client_id)) throw new IllegalArgumentException("cannot get class of player not in game.");
		return GetPlayerWithName(client_id).GetCharacterClass();
	}
	
	private IncomingPlayer GetPlayerWithName(String client_id)
	{
		for (IncomingPlayer i : players)
		{
			if (i.GetClientId().equals(client_id)) return i;
		}
		return null;
	}
	
	public String ConnectToCurrentGame(String client_id, WebSocketSession session)
	{
		if (current_game == null) throw new IllegalStateException("current_game cannot be null.");
		// TODO: check client_id
		return current_game.ConnectToGame(client_id, session).toString();
	}
	
	public boolean IsGameStarted()
	{
		return (current_game != null) && current_game.isStarted();
	}
	
	public void SendCurrentGameStateMessages()
	{
		if (current_game == null) throw new IllegalStateException("current_game cannot be null.");
//		System.out.println("Current Game is not null: " + current_game.);
		current_game.sendGameState();
	}
	
	/**
	 * Checks that the current game is valid and passes the input snapshot to the game.
	 */
	public void HandleInputSnapshot(InputSnapshot i)
	{
		if (current_game == null) throw new IllegalStateException("current_game cannot be null.");
		current_game.HandleInputSnapshot(i);
	}
}