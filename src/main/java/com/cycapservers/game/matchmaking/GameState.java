package com.cycapservers.game.matchmaking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.cycapservers.JSON_returnable;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.input.InputSnapshot;
import com.cycapservers.game.entities.Entity;

public class GameState implements JSON_returnable
{	
	private HashMap<Team, Integer> team_scores;
	
	private List<Entity> characters;
	/**
	 * Entities which are only sent twice. Once when they are created, and once when they are destroyed. Used for non-moving entities.
	 */
	private List<Entity> persistent_entities;
	/**
	 * Entities which are sent everytime there is a game state message sent. Used for moving entities.
	 */
	private List<Entity> intermittent_entities;
	/**
	 * Entities which do not have drawing components.
	 */
	private List<Entity> undrawn_entities;
	
	private List<String> persistent_entities_deleted;
	private List<Entity> persistent_entities_added;
	
	/**
	 * The time in ms of the last call to update().
	 */
	private long last_update_time;

	public GameState(List<Team> team_list) 
	{
		super();
		team_scores = new HashMap<Team, Integer>();
		for (Team t : team_list)
		{
			team_scores.put(t, 0);
		}
		persistent_entities = new ArrayList<Entity>();
		intermittent_entities = new ArrayList<Entity>();
		undrawn_entities = new ArrayList<Entity>();
		characters = new ArrayList<Entity>();
		
		persistent_entities_deleted = new ArrayList<String>();
		persistent_entities_added = new ArrayList<Entity>();
		
		last_update_time = System.currentTimeMillis();
	}
	
	public void Update()
	{
		long delta_t = System.currentTimeMillis() - last_update_time;
		last_update_time = System.currentTimeMillis();
		
		List<Entity> entities_to_delete = new ArrayList<Entity>();
		
		for (Entity e : undrawn_entities)
		{
			if (!e.Update(delta_t))
			{
				entities_to_delete.add(e);
			}
		}
		
		for (Entity e : persistent_entities)
		{
			if (!e.Update(delta_t))
			{
				persistent_entities_deleted.add(e.getEntityId());
				entities_to_delete.add(e);
			}
		}
		
		for (Entity e : intermittent_entities)
		{
			if (!e.Update(delta_t))
			{
				entities_to_delete.add(e);
			}
		}
		
		for (Entity e : characters)
		{
			if (!e.Update(delta_t))
			{
				entities_to_delete.add(e);
				System.out.println("Deleting " + e.getEntityId());
			}
		}
		
		characters.removeAll(entities_to_delete);
		intermittent_entities.removeAll(entities_to_delete);
		persistent_entities.removeAll(entities_to_delete);
		undrawn_entities.removeAll(entities_to_delete);
	}
	
	/**
	 * Finds the player in this game state which matches the client_id of the input snapshot and sends a message to its input component.
	 */
	public void handleSnapshot(InputSnapshot s)
	{
		for(Entity e : characters)
		{
			if(e.getEntityId().equals(s.getClient_id()))
			{
				e.Send(new ComponentMessage(ComponentMessageId.EXTERNAL_INPUT_SNAPSHOT, s));
				return;
			}
		}
		throw new IllegalArgumentException("No client exists in this game state with the following id(" + s.getClient_id() + ")");
	}
	
	public void addUndrawnEntity(Entity e)
	{
//		setUniqueEntityId(e);
		undrawn_entities.add(e);
	}
	
	public void addCharacter(Entity c)
	{
		characters.add(c);
	}
	
	public void addPersistentEntity(Entity e)
	{
//		setUniqueEntityId(e);
		persistent_entities.add(e);
	}
	
	public void addIntermittentEntity(Entity e)
	{
//		setUniqueEntityId(e);
		intermittent_entities.add(e);
	}
	
	public HashMap<String, String> PrepareGameStateMessages()
	{
		HashMap<String, String> player_messages = new HashMap<String, String>();
		for (Entity p : GetPlayerList())
		{
//			System.out.println("putting message for player " + p.getEntity_id());
			player_messages.put(p.getEntityId(), prepareGameStateMessage(p));
		}
		ClearEntitiesDeletedAndAdded();
		return player_messages;
	}
	
	private List<Entity> GetPlayerList()
	{
		List<Entity> list = new ArrayList<Entity>();
		for (Entity e : characters)
		{
			// TODO: have a way to determine which characters are players
			list.add(e);
		}
		return list;
	}
	
	public Entity GetPlayer(String client_id)
	{
		for (Entity e : characters)
		{
			if (e.getEntityId().equals(client_id))
			{
				return e;
			}
		}
		throw new IllegalArgumentException("player does not exist in game state");
	}
	
	private String prepareGameStateMessage(Entity p)
	{
		JSONObject obj = toJSONObject();
		for (Entity e : characters)
		{
			// TODO: if (c.IsPlayerAllowedToRender(p))
			obj.append("intermittent_entities", e.toJSONObject());
		}
		obj.put("client_player", p.toJSONObject());
		return obj.toString();
	}
	
	private void ClearEntitiesDeletedAndAdded()
	{
		this.persistent_entities_added.clear();
		this.persistent_entities_deleted.clear();
	}

	@Override
	public JSONObject toJSONObject() 
	{
		JSONObject obj = new JSONObject();
		obj.put("scores", this.team_scores);
		for (Entity e : intermittent_entities)
		{
			obj.append("intermittent_entities", e.toJSONObject());
		}
		obj.put("deleted_entities", persistent_entities_deleted);
		for (Entity e : this.persistent_entities_added)
		{
			obj.append("persistent_entities", e.toJSONObject());
		}
		return obj;
	}
}