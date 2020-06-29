package com.cycapservers.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.BeanUtil;
import com.cycapservers.JSONObject;
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
	private static int id_rand_len = 3;
	
	private GameType type;
	
	private HashMap<Integer, Integer> team_scores;
	
	private List<Character> characters;
	private List<Wall> current_walls;
	private List<Entity> background_tiles;
	private List<Entity> entities;
	
	private List<String> used_entity_id;

	public GameState(GameType type, int team_count) {
		super();
		used_entity_id = new ArrayList<String>();
		for(int i = 0; i < team_count; i++) {
			team_scores.put(i, 0);
		}
	}

	/**
	 * Ensures that any entity managed by this game state has a unique entity id
	 * Then it adds them to the appropriate list
	 * @param e
	 */
	public void addEntity(Entity e) {
		if(e instanceof Wall) {
			setUniqueEntityId(e);
			current_walls.add((Wall) e);
		}
		else if(e instanceof Character) {
			used_entity_id.add(e.getEntity_id());
			characters.add((Character) e);
		}
		else {
			setUniqueEntityId(e);
			entities.add(e);
		}
	}
	
	private void setUniqueEntityId(Entity e) {
		String s = Utils.getGoodRandomString(used_entity_id, id_rand_len) + "(" + used_entity_id.size() + ")";
		used_entity_id.add(s);
		e.setEntity_id(s);
	}

	/**
	 * Returns a valid game state message to send to the given player
	 * The message should not include any privy information that a
	 * client could use to cheat
	 * @param p The player to send the message to
	 * @return String
	 */
	public String prepareGameStateMessage(Player p){
		JSONObject obj = new JSONObject();
		//TODO
		//add game type
		//add game id
		//add game join code
		//add interpolating entities: characters, bullets, powerups, flags, etc.
		//add persistent entities: walls, background tiles,
		//add deleted persistent ents
		//add new sounds
		//add scores
		return null;
	}
	
}