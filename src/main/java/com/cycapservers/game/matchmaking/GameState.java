package com.cycapservers.game.matchmaking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.cycapservers.JSON_returnable;
import com.cycapservers.game.Team;
import com.cycapservers.game.Utils;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.input.InputSnapshot;
import com.cycapservers.game.entities.Character;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.entities.Player;
import com.cycapservers.game.entities.Wall;

public class GameState implements JSON_returnable
{	
	private static int id_rand_len = 3;
	
	private HashMap<Team, Integer> team_scores;
	
	private List<Character> characters;
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
	
	private List<String> used_entity_id;
	
	private List<String> persistent_entities_deleted;
	private List<Entity> persistent_entities_added;

	public GameState(List<Team> team_list) 
	{
		super();
		used_entity_id = new ArrayList<String>();
		team_scores = new HashMap<Team, Integer>();
		for (Team t : team_list)
		{
			team_scores.put(t, 0);
		}
		persistent_entities = new ArrayList<Entity>();
		intermittent_entities = new ArrayList<Entity>();
		undrawn_entities = new ArrayList<Entity>();
		characters = new ArrayList<Character>();
		
		persistent_entities_deleted = new ArrayList<String>();
		persistent_entities_added = new ArrayList<Entity>();
	}
	
	public void update()
	{
		List<Entity> entities_to_delete = new ArrayList<Entity>();
		
		for (Entity e : undrawn_entities)
		{
			if (!e.update())
			{
				entities_to_delete.add(e);
			}
		}
		
		for (Entity e : persistent_entities)
		{
			if (!e.update())
			{
				persistent_entities_deleted.add(e.getEntity_id());
				entities_to_delete.add(e);
			}
		}
		
		for (Entity e : intermittent_entities)
		{
			if (!e.update())
			{
				entities_to_delete.add(e);
			}
		}
		
		for (Character c : characters)
		{
			if (!c.update())
			{
				entities_to_delete.add(c);
				System.out.println("Deleting " + c.getEntity_id());
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
		for(Character c : characters)
		{
			if(c instanceof Player && c.getEntity_id().equals(s.getClient_id()))
			{
				Player p = (Player) c;
				p.Send(new ComponentMessage(ComponentMessageId.EXTERNAL_INPUT_SNAPSHOT, s));
				return;
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
		if (isUndrawnEntity(e))
		{
			addUndrawnEntity(e);
		}
		else if (isCharacter(e))
		{
			addCharacter((Character) e);
		}
		else if (isPersistentEntity(e))
		{
			addPersistentEntity(e);
			persistent_entities_added.add(e);
		}
		else if (IsIntermittentEntity(e))
		{
			addIntermittentEntity(e);
		}
		else
		{
			throw new IllegalStateException("This should not be possible.");
		}
	}
	
	private boolean isUndrawnEntity(Entity e)
	{
		return !e.HasComponentOfType(DrawingComponent.class);
	}
	
	private boolean isCharacter(Entity e)
	{
		return (e instanceof Character);
	}
	
	private boolean isPersistentEntity(Entity e)
	{
		return (e instanceof Wall);
	}
	
	private boolean IsIntermittentEntity(Entity e)
	{
		return !(isCharacter(e) || isPersistentEntity(e));
	}
	
	private void addUndrawnEntity(Entity e)
	{
		setUniqueEntityId(e);
		undrawn_entities.add(e);
	}
	
	private void addCharacter(Character c)
	{
		used_entity_id.add(c.getEntity_id());
		characters.add(c);
	}
	
	private void addPersistentEntity(Entity e)
	{
		setUniqueEntityId(e);
		persistent_entities.add(e);
	}
	
	private void addIntermittentEntity(Entity e)
	{
		setUniqueEntityId(e);
		intermittent_entities.add(e);
	}
	
	public String GetUniqueEntityId()
	{
		return Utils.getGoodRandomString(used_entity_id, id_rand_len) + "(" + used_entity_id.size() + ")";
	}
	
	private void setUniqueEntityId(Entity e) 
	{
		String s = GetUniqueEntityId();
		used_entity_id.add(s);
		e.setEntity_id(s);
	}
	
	public HashMap<String, String> PrepareGameStateMessages()
	{
		HashMap<String, String> player_messages = new HashMap<String, String>();
		for (Player p : GetPlayerList())
		{
//			System.out.println("putting message for player " + p.getEntity_id());
			player_messages.put(p.getEntity_id(), prepareGameStateMessage(p));
		}
		ClearEntitiesDeletedAndAdded();
		return player_messages;
	}
	
	private List<Player> GetPlayerList()
	{
		List<Player> list = new ArrayList<Player>();
		for (Character c : characters)
		{
			if (c instanceof Player)
			{
				list.add((Player) c);
			}
		}
		return list;
	}
	
	public Player GetPlayer(String client_id)
	{
		for (Character c : characters)
		{
			if (c instanceof Player && c.getEntity_id().equals(client_id))
			{
				return (Player) c;
			}
		}
		throw new IllegalArgumentException("player does not exist in game state");
	}
	
	private String prepareGameStateMessage(Player p)
	{
		JSONObject obj = toJSONObject();
		for (Character c : characters)
		{
			// TODO: if (c.IsPlayerAllowedToRender(p))
			obj.append("intermittent_entities", c.toJSONObject());
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