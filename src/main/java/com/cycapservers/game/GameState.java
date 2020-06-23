package com.cycapservers.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.BeanUtil;
import com.cycapservers.JSON_Stringable;
import com.cycapservers.account.ProfileDataUpdate;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.database.GameEventsRepository;
import com.cycapservers.game.database.GamePlayersEntity;
import com.cycapservers.game.database.GamePlayersRepository;
import com.cycapservers.game.database.GamesEntity;
import com.cycapservers.game.database.GamesRepository;

public class GameState
{
	
	private Map map;
	
	private List<Character> players;
	private List<Wall> current_walls;
	private List<Entity> background_tiles;
	private List<Entity> entities;

	public GameState(Map m) {
		super();
		this.map = m;
	}



	/**
	 * Returns a valid game state message to send to the given player
	 * The message should not include any privy information that a
	 * client could use to cheat
	 * @param p The player to send the message to
	 * @return String
	 */
	public String prepareGameStateMessage(Player p){
		//TODO
		return null;
	}
}