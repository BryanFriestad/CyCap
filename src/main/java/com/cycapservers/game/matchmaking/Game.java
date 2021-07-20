package com.cycapservers.game.matchmaking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.BeanUtil;
import com.cycapservers.game.Spawner;
import com.cycapservers.game.Team;
import com.cycapservers.game.UniqueIdGenerator;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.collision.CollisionEngine;
import com.cycapservers.game.components.input.ClientInputComponent;
import com.cycapservers.game.components.input.InputSnapshot;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.database.GameEventsRepository;
import com.cycapservers.game.database.GamePlayersEntity;
import com.cycapservers.game.database.GameType;
import com.cycapservers.game.database.GamesEntity;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.maps.Map;
import com.cycapservers.game.pathfinding.PathfindingNode;

/**
 * A game is held and managed by a Lobby
 * The game holds a GameState and updates clients of the GameState when necessary
 * The game also receives client inputs and parses them
 * @author Bryan Friestad
 *
 */
public abstract class Game 
{
	private static final int ENTITY_ID_LENGTH = 3;
	protected UniqueIdGenerator id_generator;
	
	//parameters
	private GameType type; //used to store in DB
	
	//Game options
	private int max_characters; //calculated internally
	private HashMap<Team, Integer> max_characters_per_team; //a mapping between the available teams in this game and the max # of players on each
	private boolean friendly_fire;
	private int max_character_lives;
	private long respawn_time; //in ms
	private boolean enable_power_ups;
	private long time_limit; //in ms
	
	//set after init
	private Map map; //the map that will be used to init the GameState for this game
	protected GameState game_state;
	private GamesEntity db_entry;
	private boolean started; //whether or not the game has started
	private boolean game_ended; //whether or not the game has finished
	private long start_time; //the unix time in ms since the start of the game
	private CollisionEngine collision_engine;
	private Spawner power_up_spawner;
	
	private JSONObject initial_game_state;
	
	// DATABASE OBJECTS
	private List<GameEventsEntity> game_events; //A list of events that have happened in the game to save to the database
	private List<GamePlayersEntity> game_players;
	
	private List<String> player_input_codes;
	private HashMap<String, WebSocketSession> websocket_session_map;
	
	public Game(GameType type, boolean friendly_fire, int max_character_lives, long respawn_time, boolean enable_power_ups, long time_limit, HashMap<Team, Integer> max_characters_per_team) 
	{
		this.id_generator = new UniqueIdGenerator(ENTITY_ID_LENGTH);
		this.type = type;
		this.friendly_fire = friendly_fire;
		this.max_character_lives = max_character_lives;
		this.respawn_time = respawn_time;
		this.enable_power_ups = enable_power_ups;
		this.time_limit = time_limit;
		this.max_characters = CalculateMaxCharacters(max_characters_per_team);
		this.max_characters_per_team = max_characters_per_team;
		
		this.game_events = new ArrayList<GameEventsEntity>();
		this.game_players = new ArrayList<GamePlayersEntity>();
		
		player_input_codes = new ArrayList<String>();
		websocket_session_map = new HashMap<String, WebSocketSession>();
		
		this.collision_engine = new CollisionEngine();
		this.game_ended = false;
		this.started = false;
	}
	
	private int CalculateMaxCharacters(HashMap<Team, Integer> max_characters_per_team)
	{
		int sum = 0;
		for (Team t : max_characters_per_team.keySet())
		{
			sum += max_characters_per_team.get(t);
		}
		return sum;
	}
	
	public void startGame(List<IncomingPlayer> incoming_players)
	{
		if(map == null) throw new IllegalStateException("The map has not yet been set for this game");
		
		List<Team> team_list = new ArrayList<Team>();
		for (Team t : this.max_characters_per_team.keySet())
		{
			if (t != Team.None) team_list.add(t);
		}
		
		game_state = new GameState(team_list);
		map.InitializeGameState(type, this, enable_power_ups);
		initial_game_state = game_state.toJSONObject();
		
		for(IncomingPlayer i : incoming_players)
		{
			addCharacter(i.BuildPlayer());
		}
		
		db_entry = new GamesEntity(type);
		initial_game_state.put("game_id", getId());
		start_time = System.currentTimeMillis();
		this.started = true;
	}
	
	public void endGame()
	{
		GameEventsRepository gameEventsRepo = BeanUtil.getBean(GameEventsRepository.class);
		gameEventsRepo.save(game_events);
	}

	public abstract Entity getValidSpawnNode(Team team);
	
	public void sendGameState()
	{
		HashMap<String, String> client_id_to_message_map = this.game_state.PrepareGameStateMessages();
		for (String client_id : client_id_to_message_map.keySet())
		{
			String msg = client_id_to_message_map.get(client_id);
			try 
			{
				if (this.websocket_session_map.containsKey(client_id)) // if this is false it means that a client has not yet connected to the game.
				{
					this.websocket_session_map.get(client_id).sendMessage(new TextMessage(msg));
				}
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void update()
	{
		//update AI 
		game_state.Update();
		collision_engine.update();
		//check if game is completed
	}
	
	/**
	 * Checks if the input snapshot belongs to this game and passes it on to the game state.
	 * After updating the inputs of the appropriate player, the game state
	 * is globally updated
	 * @param s
	 */
	public void HandleInputSnapshot(InputSnapshot s)
	{
		if (s.getGame_id() != getId()) throw new IllegalArgumentException("This input snapshot does not belong to this game(" + getId() + ")");
		game_state.handleSnapshot(s);
		update();
	}
	
	protected void addCharacter(Entity c)
	{
		id_generator.AddUsedId(c.getEntityId());
		CheckAndRegisterCollision(c);
		game_state.addCharacter(c);
	}
	
	protected abstract boolean removeCharacter(Character c);
	
	public void AddUndrawnEntity(Entity e)
	{
		CheckAndRegisterCollision(e);
		game_state.addUndrawnEntity(e);
	}
	
	public void AddIntermittentEntity(Entity e)
	{
		CheckAndRegisterCollision(e);
		game_state.addIntermittentEntity(e);
	}
	
	public void AddPersistentEntity(Entity e)
	{
		CheckAndRegisterCollision(e);
		game_state.addPersistentEntity(e);
	}
	
	private void CheckAndRegisterCollision(Entity e)
	{
		if (e.HasComponentOfType(CollisionComponent.class))
		{
			CollisionComponent cc = (CollisionComponent) e.GetComponentOfType(CollisionComponent.class);
			collision_engine.registerCollidable(cc);
		}
	}
	
	public void addGameEvent(GameEventsEntity event)
	{
		event.setSequence_order(game_events.size());
		game_events.add(event);
	}

	public int getId() 
	{
		if (db_entry == null) throw new IllegalStateException("db_entry must have been init before calling this"); 
		return db_entry.getGame_id();
	}

	public boolean isFriendly_fire() 
	{
		return friendly_fire;
	}

	public PositionComponent getGraveyardPosition() 
	{
		throw new UnsupportedOperationException(); // TODO
	}
	
	public List<PathfindingNode> getPathfindingNodes()
	{
		throw new UnsupportedOperationException();
	}

	public long getRespawn_time() 
	{
		return respawn_time;
	}

	public GameType getType() 
	{
		return type;
	}

	public int getMax_players() 
	{
		return max_characters;
	}

	public boolean isStarted() 
	{
		return started;
	}

	public boolean isGame_ended() 
	{
		return game_ended;
	}

	public void setMap(Map map) 
	{
		this.map = map;
	}

	public HashMap<Team, Integer> getMax_characters_per_team() 
	{
		return max_characters_per_team;
	}

	public JSONObject ConnectToGame(String client_id, WebSocketSession session) 
	{
		if (!this.started) throw new IllegalStateException("game must be started to send initial state message");
		
		this.websocket_session_map.put(client_id, session);
		
		JSONObject o = this.initial_game_state;
		o.remove("passcode"); // make sure the passcode key is empty before adding player specific one.
		Entity e = GetPlayer(client_id);
		ClientInputComponent input = (ClientInputComponent) e.GetComponentOfType(ClientInputComponent.class);
		o.put("passcode", input.GetInputPasscode());
		
		return o;
	}
	
	private Entity GetPlayer(String client_id)
	{
		return game_state.GetPlayer(client_id);
	}
	
	public String GenerateUniqueEntityId()
	{
		return id_generator.GenerateUniqueEntityId();
	}
	
//	public void endGame(int winner) {
//		
//		gameFinished = true;
//		List<String> player_ids = new ArrayList<String>();
//		for(Player p : this.players) {
//			player_ids.add(p.getEntity_id());
//			
//			p.stats.updateScore(winner);
//			ProfileDataUpdate.dbSaveData(p.stats);
//			String message = "endgame:";
//			try {
//				p.session.sendMessage(new TextMessage(message + p.stats.get_endgame_message()));
//			} catch (IOException e) {
//				System.out.println("Error sending endgame message");
//				e.printStackTrace();
//			}
//		}
//		
//		GamePlayersRepository gamePlayersRepo = BeanUtil.getBean(GamePlayersRepository.class);
//		for(GamePlayersEntity e : game_players){
//			if(player_ids.contains(e.getUser_id())){
//				e.setLeft_early(false);
//			}
//			else{
//				e.setLeft_early(true);
//			}
//			
//			gamePlayersRepo.save(e);
//		}
//		
//		GamesRepository gamesRepo = BeanUtil.getBean(GamesRepository.class);
//		GamesEntity this_game = gamesRepo.findOne(this.game_id);
//		
//		this_game.setStart_time(start_time);
//		this_game.setWinning_team(winner);
//		gamesRepo.save(this_game);
//	}
//
//	@Override
//	public void run() {
//		if(started && !gameFinished) {
//			////UPDATE GAME STATE////
//			updateGameState();
//			
//			////GET NEW ITEM LIST////
//			this.current_item_list = getItemList();
//			
//			////INFORM PLAYERS OF GS UPDATE////
//			for(Player p : players) {
//				p.setLastUnsentGameState(this.toDataString(p));
//			}
//			
//			//////CLEAR LISTS/////
//			this.new_sounds.clear();
//			this.unhandledInputs.clear(); //empty the queue of unhandled inputs
//		}
//		else if(readyToStart && !gameFinished){
//			if(Utils.DEBUG) System.out.println("Game: " + this.game_id + " - Type: " + this.getClass() +  " - Players size: " + players.size() + " - Incoming size: " + incomingPlayers.size() + " - ReadyTime: " + this.readyTime);
//			if(players.size() == incomingPlayers.size() || (System.currentTimeMillis() - this.readyTime) >= 15000) {
//				setUpGame();
//			}
//		}
//	}

}
