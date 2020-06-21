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
	private SpawnNode[] spawnNodes;
	
	//Game options
	private boolean friendly_fire;
	private int max_character_lives;
	private long respawn_time;

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
	
	public abstract SpawnNode getValidSpawnNode(int team);
	
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

}
