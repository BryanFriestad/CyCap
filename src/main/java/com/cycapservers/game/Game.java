package com.cycapservers.game;

import java.util.ArrayList;
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
	
	private GameState game_state;
	
	private Map map;
	
	//Game options
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
	
	public Game(int id, boolean friendly_fire){
		this.setId(id);
		game_state = new GameState(map);
		this.game_events = new ArrayList<GameEventsEntity>();
		this.setFriendly_fire(friendly_fire);
	}
	
	public void addGameEvent(GameEventsEntity event){
		event.setSequence_order(game_events.size());
		game_events.add(event);
	}
	
	public abstract boolean addCharacter(Character c);
	
	public abstract boolean removeCharacter(Character c);
	
	public abstract boolean startGame();
	
	public abstract void receiveInputSnapshot(InputSnapshot s);
	
	public abstract void update();
	
	public abstract void sendGameState();
	
	public abstract Spawn getValidSpawnNode(int team);
	
	public void endGame(int winner){
		GameEventsRepository gameEventsRepo = BeanUtil.getBean(GameEventsRepository.class);
		gameEventsRepo.save(game_events);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFriendly_fire() {
		return friendly_fire;
	}

	public void setFriendly_fire(boolean friendly_fire) {
		this.friendly_fire = friendly_fire;
	}

	public Position getGraveyardPosition() {
		return this.map.getGraveyardPosition();
	}

	public long getRespawn_time() {
		return respawn_time;
	}

	public void setRespawn_time(long respawn_time) {
		this.respawn_time = respawn_time;
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
