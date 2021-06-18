package com.cycapservers.game.maps;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.cycapservers.game.Team;
import com.cycapservers.game.database.GameType;
import com.cycapservers.game.entities.EntityFactory;
import com.cycapservers.game.matchmaking.GameState;

public class Map
{
	private List<GameType> legal_game_types;
	
	private List<JSONObject> walls;
	private List<JSONObject> tdm_dynamics;

	protected Map(List<JSONObject> wall_objs, List<JSONObject> dyn_objs) 
	{
		walls = wall_objs;
		tdm_dynamics = FilterDynamics("tdm", dyn_objs);
		PopulateLegalGameTypes(dyn_objs);
	}
	
	private List<JSONObject> FilterDynamics(String filter, List<JSONObject> dyn_objs)
	{
		List<JSONObject> filtered_results = new ArrayList<JSONObject>();
		for (JSONObject o : dyn_objs)
		{
			if (o.get("game_type").equals(filter)) filtered_results.add(o);
		}
		return filtered_results;
	}
	
	private void PopulateLegalGameTypes(List<JSONObject> dyn_objs)
	{
		legal_game_types = new ArrayList<GameType>();
		int tdm_count = 0;
		
		for (JSONObject o : dyn_objs)
		{
			if (o.get("game_type").equals("tdm")) tdm_count++;
		}
		
		if (tdm_count != 0) legal_game_types.add(GameType.tdm);
	}
	
	/**
	 * Adds all of the walls, background tiles, and other persistent entities to the given game state
	 * @param type
	 * @param state
	 */
	public void InitializeGameState(GameType type, GameState state, boolean use_power_ups) 
	{
		if (!legal_game_types.contains(type)) throw new InvalidParameterException("GameType type parameter(" + type + ") not a legal game type for this map.");
		
		for (JSONObject o : walls)
		{
			short x = (short) o.getInt("x");
			short y = (short) o.getInt("y");
			state.addEntity(EntityFactory.getInstance().GetWall(x, y));
		}
		
		if (type == GameType.tdm)
		{
			InitializeGameTypeSpecificState(state, tdm_dynamics);
		}
	}
	
	private void InitializeGameTypeSpecificState(GameState state, List<JSONObject> dynamics)
	{
		for (JSONObject o : dynamics)
		{
			if (o.get("type").equals("spawn"))
			{
				short x = (short) o.getInt("x");
				short y = (short) o.getInt("y");
				Team t = (Team) o.get("team");
				state.addEntity(EntityFactory.getInstance().GetSpawn(x, y, t));
			}
		}
	}

}
