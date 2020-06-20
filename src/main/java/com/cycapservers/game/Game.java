package com.cycapservers.game;

import java.util.ArrayList;
import java.util.List;

import com.cycapservers.BeanUtil;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.database.GameEventsRepository;

public abstract class Game {
	
	private int id;
	
	private GameState game_state;

	private List<GameEventsEntity> game_events;
	
	public Game(int id){
		this.setId(id);
		game_state = new GameState();
		this.game_events = new ArrayList<GameEventsEntity>();
	}
	
	public void addGameEvent(GameEventsEntity event){
		event.setSequence_order(game_events.size());
		game_events.add(event);
	}
	
	public abstract void receiveInputSnapshot(InputSnapshot s);
	
	public abstract void update();
	
	public abstract void sendGameState();
	
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

}
