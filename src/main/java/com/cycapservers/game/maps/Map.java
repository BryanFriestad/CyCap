package com.cycapservers.game.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cycapservers.JSON_Stringable;
import com.cycapservers.game.BackgroundTile;
import com.cycapservers.game.Collider;
import com.cycapservers.game.Drawable;
import com.cycapservers.game.Flag;
import com.cycapservers.game.GameState;
import com.cycapservers.game.GridLockedDrawable;
import com.cycapservers.game.GridLockedPosition;
import com.cycapservers.game.Image;
import com.cycapservers.game.PathfindingNode;
import com.cycapservers.game.Position;
import com.cycapservers.game.Spawn;
import com.cycapservers.game.Team;
import com.cycapservers.game.Wall;
import com.cycapservers.game.database.GameType;

public class Map{
	
	private ArrayList<GameType> legal_game_types;
	
	private ArrayList<HashMap<String, String>>  walls;
	private ArrayList<HashMap<String, String>>  bg_tiles;
	private ArrayList<HashMap<String, String>>  ents;

	protected Map(ArrayList<GameType> legal_game_types, ArrayList<HashMap<String, String>> walls, ArrayList<HashMap<String, String>> bg_tiles, ArrayList<HashMap<String, String>> ents) {
		super();
		this.legal_game_types = legal_game_types;
		this.walls = walls;
		this.bg_tiles = bg_tiles;
		this.ents = ents;
	}
	
	/**
	 * Adds all of the walls, background tiles, and other persistent entities to the given game state
	 * @param type
	 * @param state
	 */
	public void initializeGameState(GameType type, GameState state, boolean use_power_ups) {
		//add all walls to game state
		for(HashMap<String, String> wall_param_map : walls){
			if(wall_param_map.get("class").equals(Wall.class.getName())){
				state.addEntity(new Wall(new GridLockedDrawable(new Image(wall_param_map.get("source"), -1), Integer.parseInt(wall_param_map.get("spriteIndex")), new GridLockedPosition(Short.parseShort(wall_param_map.get("x")), Short.parseShort(wall_param_map.get("y")))), Integer.parseInt(wall_param_map.get("strength"))));
			}
		}
		
		//add background tiles to game state
		if(!bg_tiles.isEmpty()){
			for(HashMap<String, String> bg_param_map : bg_tiles){
				if(bg_param_map.get("class").equals(BackgroundTile.class.getName())){
					state.addEntity(new BackgroundTile(new GridLockedDrawable(new Image(bg_param_map.get("source"), -1), Integer.parseInt(bg_param_map.get("spriteIndex")), new GridLockedPosition(Short.parseShort(bg_param_map.get("x")), Short.parseShort(bg_param_map.get("y"))))));
				}
			}
		}
		else{
			//randomly generate background tiles
		}
		
		//loop through entities map list
		for(HashMap<String, String> ent_param_map : ents){
			String tags_concat = ent_param_map.get("tags").substring(1); //remove the first # symbol
			String[] tags = tags_concat.split("#");
			
			boolean entity_valid = true;
			for(String tag : tags){ //check tags for validity of this game type and settings
				if(GameType.gameTypesAsStrings().contains(tag)){ //check to see if the tag is a game type
					if(!type.toString().equals(tag)){ //if it is, check it is not the given game type
						entity_valid = false;
						break; //if it is not, skip this entity
					}
				}
				
				if(tag.equals("power_up")){
					if(!use_power_ups)
						entity_valid = false;
						break;
				}
			}
			
			if(entity_valid){
				if(ent_param_map.get("class").equals(Flag.class.getName())){
					//TODO: should add flag bases instead of the actual flag
				}
			}
		}

		
//		graveyardPosition, spawns, team_flag_locations;
	}

}