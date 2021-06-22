package com.cycapservers.game.matchmaking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cycapservers.JSON_Stringable;
import com.cycapservers.JsonToStringObject;
import com.cycapservers.game.Team;
import com.cycapservers.game.Utils;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.input.InputSnapshot;
import com.cycapservers.game.database.GameType;
import com.cycapservers.game.entities.Character;
import com.cycapservers.game.entities.DrawableEntity;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.entities.Player;
import com.cycapservers.game.entities.Wall;

public class GameState implements JSON_Stringable
{	
	private static int id_rand_len = 3;
	
	private GameType type;
	
	private HashMap<Team, Integer> team_scores;
	
	private List<Character> characters;
	private List<Wall> current_walls;
	private List<DrawableEntity> background_tiles;
	private List<Entity> entities;
	
	private List<String> used_entity_id;

	public GameState(List<Team> team_list) 
	{
		super();
		used_entity_id = new ArrayList<String>();
		team_scores = new HashMap<Team, Integer>();
		for (Team t : team_list)
		{
			team_scores.put(t, 0);
		}
		entities = new ArrayList<Entity>();
		background_tiles = new ArrayList<DrawableEntity>();
		current_walls = new ArrayList<Wall>();
		characters = new ArrayList<Character>();
	}
	
	public void update()
	{
		
	}
	
	public void handleSnapshot(InputSnapshot s)
	{
		for(Character c : characters)
		{
			if(c instanceof Player && c.getEntity_id().equals(s.getClient_id()))
			{
				Player p = (Player) c;
				p.Send(new ComponentMessage(ComponentMessageId.EXTERNAL_INPUT_SNAPSHOT, s.GetRawData()));
			}
		}
		throw new IllegalArgumentException("No client exists in this game state with the following id(" + s.getClient_id() + ")");
	}

	/**
	 * Ensures that any entity managed by this game state has a unique entity id
	 * Then it adds them to the appropriate list
	 * @param e
	 */
	public void addEntity(Entity e) 
	{
		if (e instanceof Wall) 
		{
			setUniqueEntityId(e);
			current_walls.add((Wall) e);
		}
		else if (e instanceof Character) 
		{
			used_entity_id.add(e.getEntity_id());
			characters.add((Character) e);
		}
		else 
		{
			setUniqueEntityId(e);
			entities.add(e);
		}
	}
	
	public void addBackgroundtile(DrawableEntity e)
	{
		setUniqueEntityId(e);
		background_tiles.add(e);
	}
	
	private void setUniqueEntityId(Entity e) 
	{
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
	public String prepareGameStateMessage(Player p)
	{
		JsonToStringObject obj = new JsonToStringObject();
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

	@Override
	public String toJSONString() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}