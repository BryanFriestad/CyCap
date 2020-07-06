package com.cycapservers.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cycapservers.BeanUtil;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.database.GameEventsRepository;

/**
 * A game is held and managed by a Lobby
 * The game holds a GameState and updates clients of the GameState when necessary
 * The game also receives client inputs and parses them
 * @author Bryan Friestad
 *
 */
public abstract class Game {
	
	private int id;
	private String join_code;
	
	private boolean started;
	private boolean game_ended;
	
	/**
	 * A mapping of character ids to team numbers of the team they are on
	 */
	private HashMap<String, Integer> character_teams;
	private GameType type;
	protected GameState game_state;
	
	private Map map;
	
	private CollisionEngine collision_engine;
	
	//Game options
	private int max_characters;
	private HashMap<Team, Integer> max_characters_per_team;
	private boolean friendly_fire;
	private int max_character_lives;
	/**
	 * in milliseconds
	 */
	private long respawn_time;
	private boolean enable_power_ups;
	/**
	 * in milliseconds
	 */
	private long time_limit;
	
	private PowerUpSpawner power_up_spawner;

	/**
	 * A list of events that have happened in the game to save to the database
	 */
	private List<GameEventsEntity> game_events;
	private long start_time;
	
	public Game(int id, Map map, GameType type, boolean friendly_fire, int max_character_lives, long respawn_time,
			boolean enable_power_ups, long time_limit, int max_players, int num_teams, HashMap<Team, Integer> max_characters_per_team) {
		super();
		this.id = id;
		this.map = map;
		this.type = type;
		this.friendly_fire = friendly_fire;
		this.max_character_lives = max_character_lives;
		this.respawn_time = respawn_time;
		this.enable_power_ups = enable_power_ups;
		this.time_limit = time_limit;
		this.max_characters = max_players;
		this.max_characters_per_team = max_characters_per_team;
		game_state = new GameState(this.type, this.max_characters_per_team.keySet().size());
		map.initializeGameState(type, game_state);
		this.game_events = new ArrayList<GameEventsEntity>();
		this.collision_engine = new CollisionEngine();
		this.game_ended = false;
		this.started = false;
	}
	
	public abstract boolean addCharacter(Character c);
	
	public abstract boolean removeCharacter(Character c);
	
	public abstract boolean startGame();
	
	public abstract void sendGameState();
	
	public abstract Spawn getValidSpawnNode(Team team);
	
	public void update(){
		//update AI 
		//update game state
		//update collision engine
		//check if game is completed
	}
	
	/**
	 * Checks if this input snapshot is valid for this game.
	 * If so, it passes it on to the game_state to find the appropriate
	 * player to attribute it to.
	 * After updating the inputs of the appropriate player, the game state
	 * is globally updated
	 * @param s
	 */
	public void receiveInputSnapshot(InputSnapshot s){
		if(s.getGame_id() != id)
			throw new IllegalArgumentException("This input snapshot does not belong to this game(" + id + ")");
		game_state.handleSnapshot(s);
		update();
	}
	
	public void endGame(){
		GameEventsRepository gameEventsRepo = BeanUtil.getBean(GameEventsRepository.class);
		gameEventsRepo.save(game_events);
	}
	
	public void addEntity(Entity e, boolean needsCollision){
		if(needsCollision)
			collision_engine.registerCollidable((Collidable) e);
		game_state.addEntity(e);
	}
	
	public void addGameEvent(GameEventsEntity event){
		event.setSequence_order(game_events.size());
		game_events.add(event);
	}

	public int getId() {
		return id;
	}

	public boolean isFriendly_fire() {
		return friendly_fire;
	}

	public Position getGraveyardPosition() {
		return this.map.getGraveyardPosition();
	}

	public long getRespawn_time() {
		return respawn_time;
	}

	public GameType getType() {
		return type;
	}

	public int getMax_players() {
		return max_characters;
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isGame_ended() {
		return game_ended;
	}

	public HashMap<Team, Integer> getMax_characters_per_team() {
		return max_characters_per_team;
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
