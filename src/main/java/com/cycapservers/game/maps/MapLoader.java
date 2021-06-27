package com.cycapservers.game.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

public class MapLoader
{
	
	public static Map LoadMap(String filename)
	{
		Map output;
		List<String> file_lines = ReadFile(filename);
		RemoveCommentLines(file_lines);
		RemoveBlankLines(file_lines);
		List<JSONObject> wall_objs = DetermineLinesInSection("walls", file_lines);
		List<JSONObject> dyn_objs = DetermineLinesInSection("dynamic", file_lines);
		
		output = new Map(wall_objs, dyn_objs);
		return output;
	}
	
	private static List<String> ReadFile(String filename)
	{
		List<String> file_lines = new ArrayList<String>();
		
		try 
		{
			ClassPathResource resource = new ClassPathResource("maps/" + filename);
			InputStream i_stream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(i_stream));
			
			String line = reader.readLine();
			while(line != null)
			{
				file_lines.add(line.trim());
				line = reader.readLine();
			}
			reader.close();
			i_stream.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return file_lines;
	}
	
	private static void RemoveCommentLines(List<String> file_lines)
	{
		List<String> to_remove = new ArrayList<String>();
		for (String line : file_lines)
		{
			if (line.startsWith("--")) to_remove.add(line);
		}
		file_lines.removeAll(to_remove);
	}
	
	private static void RemoveBlankLines(List<String> file_lines)
	{
		List<String> to_remove = new ArrayList<String>();
		for (String line : file_lines)
		{
			if (line.isEmpty()) to_remove.add(line);
		}
		file_lines.removeAll(to_remove);
	}
	
	private static List<JSONObject> DetermineLinesInSection(String section_name, List<String> file_lines)
	{
		// TODO: check for no nested sections
		List<JSONObject> output = new ArrayList<JSONObject>();
		boolean in_range = false;
		for (int i = 0; i < file_lines.size(); ++i)
		{
			String line = file_lines.get(i);
			if (line.startsWith("</" + section_name + ">")) break; // break before adding to output so the end line isn't included
			if (in_range) output.add(new JSONObject(line));
			if (line.startsWith("<" + section_name + ">")) in_range = true; // set range true after seeing start line, so it isn't included.
		}
		return output;
	}
}
//package com.cycapservers.game;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public final class MapLoader {
//	
//	private MapLoader() {} //so it can't be constructed
//	
//	public static void loadPredefinedMap(int num, GameState g) {
//		switch(num) {
//			case 0:
//				loadMap0(g);
//				break;
//			case 1:
//				loadMap1(g);
//				break;
//			default:
//				throw new IllegalArgumentException("Illegal map number. No such predefined map");
//		}
//	}
//	
//	//////MAP 0//////
//	private static void loadMap0(GameState g) {
//		Utils.generateWallLine(g, 5, 10, 5, 'x');  //done
//		Utils.generateWallLine(g, 9, 3, 7, 'y');   //done
//		Utils.generateWallLine(g, 12, 3, 15, 'x'); //done
//		Utils.generateWallLine(g, 12, 4, 4, 'y');  //done
//		Utils.generateWallLine(g, 12, 10, 8, 'x'); //done
//		Utils.generateWallLine(g, 20, 4, 8, 'y');  //done
//		Utils.generateWallLine(g, 21, 7, 3, 'x');  //done
//		Utils.generateWallLine(g, 26, 4, 4, 'y');  //done
//		Utils.generateWallLine(g, 29, 1, 2, 'y');  //done
//		Utils.generateWallLine(g, 29, 5, 2, 'y');  //done
//		Utils.generateWallLine(g, 29, 7, 2, 'x');  //done
//		Utils.generateWallLine(g, 31, 7, 19, 'y'); //done
//		Utils.generateWallLine(g, 34, 1, 8, 'y');  //done
//		Utils.generateWallLine(g, 38, 5, 2, 'x');  //done
//		Utils.generateWallLine(g, 32, 9, 6, 'x');  //done
//		Utils.generateWallLine(g, 34, 12, 6, 'x'); //done
//		Utils.generateWallLine(g, 34, 13, 4, 'y'); //done
//		Utils.generateWallLine(g, 34, 19, 10, 'y');//done
//		Utils.generateWallLine(g, 24, 10, 5, 'x'); //done
//		Utils.generateWallLine(g, 23, 10, 16, 'y');//done
//		Utils.generateWallLine(g, 28, 14, 3, 'x'); //done
//		Utils.generateWallLine(g, 26, 17, 3, 'x'); //done
//		Utils.generateWallLine(g, 26, 20, 4, 'y'); //done
//		Utils.generateWallLine(g, 27, 23, 4, 'x'); //done
//		Utils.generateWallLine(g, 23, 26, 9, 'x'); //done
//		Utils.generateWallLine(g, 20, 14, 13, 'y');//done
//		Utils.generateWallLine(g, 1, 15, 19, 'x'); //done
//		Utils.generateWallLine(g, 1, 18, 2, 'x');  //done
//		Utils.generateWallLine(g, 5, 18, 3, 'x');  //done
//		Utils.generateWallLine(g, 8, 18, 11, 'y'); //done
//		Utils.generateWallLine(g, 11, 18, 8, 'y'); //done
//		Utils.generateWallLine(g, 12, 18, 6, 'x'); //done
//		Utils.generateWallLine(g, 3, 24, 2, 'y');  //done
//		Utils.generateWallLine(g, 3, 26, 3, 'x');  //done
//		Utils.generateWallLine(g, 11, 26, 5, 'x'); //done
//		Utils.generateWallLine(g, 18, 26, 2, 'x'); //done
//		Utils.placeBorder(g, 41, 30, 0, 0, true);
//		g.mapGridHeight = 30;
//		g.mapGridWidth = 41;
//		
//		if(g.getClass().equals(CaptureTheFlag.class) || g.getClass().equals(GuestCaptureTheFlag.class)) {
//			loadMap0_CTFElements((CaptureTheFlag) g);
//		}
//		else if(g.getClass().equals(FreeForAll.class)) {
//			loadMap0_FFAElements((FreeForAll) g);
//		}
//		else if(g.getClass().equals(TeamDeathMatch.class)) {
//			loadMap0_TDMElements((TeamDeathMatch) g);
//		}
//	}
//	
//	private static void loadMap0_CTFElements(CaptureTheFlag g) {
//		//make a list of nodes
//		List<PowerUpSpawn> pu_nodes = new ArrayList<PowerUpSpawn>();
//		pu_nodes.add(new PowerUpSpawn((short) 7, (short) 8));
//		pu_nodes.add(new PowerUpSpawn((short) 13, (short) 20));
//		pu_nodes.add(new PowerUpSpawn((short) 27, (short) 25));
//		pu_nodes.add(new PowerUpSpawn((short) 33, (short) 6));
//		pu_nodes.add(new PowerUpSpawn((short) 37, (short) 27));
//		g.pu_handler.setNodeList(pu_nodes);
//		
//		g.team1_base = new GridLockedNode((short) 4, (short) 25);
//		g.team2_base = new GridLockedNode((short) 38, (short) 2);
//		String id = Utils.getGoodRandomString(g.usedEntityIds, g.entity_id_len);
//		g.team1_flag = new Flag(g.team1_base, Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0, 1.0, id, 1);
//		g.usedEntityIds.add(id);
//		id = Utils.getGoodRandomString(g.usedEntityIds, g.entity_id_len);
//		g.team2_flag = new Flag(g.team2_base, Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0, 1.0, id, 2);
//		g.usedEntityIds.add(id);
//		
//		//TODO sometimes, for some reason, spawning in a corner keeps the player from moving, idk
//		g.spawns.add(new Spawn((short) 2, (short) 16, 1));
//		g.spawns.add(new Spawn((short) 6, (short) 20, 1));
//		g.spawns.add(new Spawn((short) 6, (short) 28, 1));
//		g.spawns.add(new Spawn((short) 38, (short) 1, 2));
//		g.spawns.add(new Spawn((short) 35, (short) 4, 2));
//		g.spawns.add(new Spawn((short) 39, (short) 10, 2));
//	}
//	
//	private static void loadMap0_FFAElements(FreeForAll g) {
//		//make a list of nodes
//		List<PowerUpSpawn> pu_nodes = new ArrayList<PowerUpSpawn>();
//		pu_nodes.add(new PowerUpSpawn((short) 7, (short) 8));
//		pu_nodes.add(new PowerUpSpawn((short) 13, (short) 20));
//		pu_nodes.add(new PowerUpSpawn((short) 27, (short) 25));
//		pu_nodes.add(new PowerUpSpawn((short) 33, (short) 6));
//		pu_nodes.add(new PowerUpSpawn((short) 37, (short) 27));
//		g.pu_handler.setNodeList(pu_nodes);
//		
//		//TODO sometimes, for some reason, spawning in a corner keeps the player from moving, idk
//		g.spawns.add(new Spawn((short) 2, (short) 16, 1));
//		g.spawns.add(new Spawn((short) 6, (short) 20, 2));
//		g.spawns.add(new Spawn((short) 6, (short) 28, 3));
//		g.spawns.add(new Spawn((short) 38, (short) 1, 4));
//		g.spawns.add(new Spawn((short) 35, (short) 4, 5));
//		g.spawns.add(new Spawn((short) 39, (short) 10, 6));
//		g.spawns.add(new Spawn((short) 35, (short) 4, 7));
//		g.spawns.add(new Spawn((short) 39, (short) 10, 8));
//	}
//	
//	private static void loadMap0_TDMElements(TeamDeathMatch g) {
//		//TODO: change up the powerup and spawn nodes
//		//make a list of nodes
//		List<PowerUpSpawn> pu_nodes = new ArrayList<PowerUpSpawn>();
//		pu_nodes.add(new PowerUpSpawn((short) 7, (short) 8));
//		pu_nodes.add(new PowerUpSpawn((short) 13, (short) 20));
//		pu_nodes.add(new PowerUpSpawn((short) 27, (short) 25));
//		pu_nodes.add(new PowerUpSpawn((short) 33, (short) 6));
//		pu_nodes.add(new PowerUpSpawn((short) 37, (short) 27));
//		g.pu_handler.setNodeList(pu_nodes);
//		
//		//TODO sometimes, for some reason, spawning in a corner keeps the player from moving, idk
//		g.spawns.add(new Spawn((short) 2, (short) 16, 1));
//		g.spawns.add(new Spawn((short) 6, (short) 20, 1));
//		g.spawns.add(new Spawn((short) 6, (short) 28, 1));
//		
//		g.spawns.add(new Spawn((short) 38, (short) 1, 2));
//		g.spawns.add(new Spawn((short) 35, (short) 4, 2));
//		g.spawns.add(new Spawn((short) 39, (short) 10, 2));
//	}
//	//////END MAP 0//////
//	
//	//////MAP 1//////
//	private static void loadMap1(GameState g) {
//		Utils.generateWallLine(g, 2, 7, 3, 'x');
//		Utils.generateWallLine(g, 2, 17, 3, 'x');
//		Utils.generateWallLine(g, 3, 8, 9, 'y');
//		Utils.generateWallLine(g, 6, 8, 10, 'x');
//		Utils.generateWallLine(g, 6, 9, 10, 'x');
//		Utils.generateWallLine(g, 6, 10, 10, 'x');
//		Utils.generateWallLine(g, 6, 11, 10, 'x');
//		Utils.generateWallLine(g, 6, 12, 10, 'x');
//		Utils.generateWallLine(g, 6, 13, 10, 'x');
//		Utils.generateWallLine(g, 7, 7, 3, 'x');
//		Utils.generateWallLine(g, 12, 7, 3, 'x');
//		Utils.generateWallLine(g, 31, 7, 13, 'x');
//		Utils.generateWallLine(g, 25, 7, 11, 'y');
//		Utils.generateWallLine(g, 7, 14, 8, 'x');
//		Utils.generateWallLine(g, 8, 15, 6, 'x');
//		Utils.generateWallLine(g, 9, 16, 4, 'x');
//		Utils.generateWallLine(g, 10, 17, 2, 'x');
//		Utils.generateWallLine(g, 19, 7, 4, 'x');
//		Utils.generateWallLine(g, 19, 12, 4, 'x');
//		Utils.generateWallLine(g, 19, 17, 4, 'x');
//		Utils.generateWallLine(g, 4, 19, 7, 'x');
//		Utils.generateWallLine(g, 1, 19, 1, 'x');
//		Utils.generateWallLine(g, 13, 19, 5, 'x');
//		Utils.generateWallLine(g, 46, 7, 3, 'x');
//		Utils.generateWallLine(g, 43, 13, 3, 'x');
//		Utils.generateWallLine(g, 43, 17, 3, 'x');
//		Utils.generateWallLine(g, 43, 21, 3, 'x');
//		Utils.generateWallLine(g, 26, 12, 3, 'x');
//		Utils.generateWallLine(g, 32, 13, 2, 'x');
//		Utils.generateWallLine(g, 38, 13, 2, 'x');
//
//		Utils.generateWallLine(g, 31, 1, 4, 'y');
//		Utils.generateWallLine(g, 40, 4, 3, 'y');
//		Utils.generateWallLine(g, 40, 1, 1, 'y');
//		Utils.generateWallLine(g, 46, 14, 7, 'y');
//		Utils.generateWallLine(g, 29, 13, 5, 'y');
//		Utils.generateWallLine(g, 36, 13, 5, 'y');
//		Utils.generateWallLine(g, 34, 14, 4, 'y');
//		Utils.generateWallLine(g, 40, 14, 4, 'y');
//		Utils.generateWallLine(g, 18, 8, 4, 'y');
//		Utils.generateWallLine(g, 23, 13, 4, 'y');
//		Utils.generateWallLine(g, 13, 20, 4, 'y');
//		Utils.generateWallLine(g, 6, 20, 2, 'y');
//		Utils.generateWallLine(g, 18, 19, 2, 'y');
//		Utils.generateWallLine(g, 31, 14, 3, 'y');
//		Utils.generateWallLine(g, 42, 14, 3, 'y');
//		Utils.generateWallLine(g, 18, 23, 1, 'y');
//		Utils.generateWallLine(g, 18, 16, 1, 'y');
//		Utils.generateWallLine(g, 23, 8, 1, 'y');
//		Utils.generateWallLine(g, 32, 17, 1, 'y');
//		Utils.generateWallLine(g, 33, 16, 1, 'y');
//		Utils.generateWallLine(g, 37, 14, 1, 'y');
//		Utils.generateWallLine(g, 42, 20, 1, 'y');
//		Utils.placeBorder(g, 50, 25, 0, 0, true);
//		g.mapGridHeight = 25;
//		g.mapGridWidth = 50;
//		
//		if(g.getClass().equals(CaptureTheFlag.class) || g.getClass().equals(GuestCaptureTheFlag.class)) {
//			loadMap1_CTFElements((CaptureTheFlag) g);
//		}
//		else if(g.getClass().equals(FreeForAll.class)) {
//			loadMap1_FFAElements((FreeForAll) g);
//		}
//		else if(g.getClass().equals(TeamDeathMatch.class)) {
//			loadMap1_TDMElements((TeamDeathMatch) g);
//		}
//	}
//	
//	private static void loadMap1_CTFElements(CaptureTheFlag g) {
//		//make a list of nodes
//		List<PowerUpSpawn> pu_nodes = new ArrayList<PowerUpSpawn>();
//		pu_nodes.add(new PowerUpSpawn((short) 2, (short) 2));
//		pu_nodes.add(new PowerUpSpawn((short) 11, (short) 7));
//		pu_nodes.add(new PowerUpSpawn((short) 15, (short) 21));
//		pu_nodes.add(new PowerUpSpawn((short) 20, (short) 9));
//		pu_nodes.add(new PowerUpSpawn((short) 27, (short) 14));
//		pu_nodes.add(new PowerUpSpawn((short) 34, (short) 13));
//		pu_nodes.add(new PowerUpSpawn((short) 38, (short) 15));
//		pu_nodes.add(new PowerUpSpawn((short) 44, (short) 19));
//		g.pu_handler.setNodeList(pu_nodes);
//		
//		g.team1_base = new GridLockedNode((short) 47, (short) 5);
//		g.team2_base = new GridLockedNode((short) 5, (short) 20);
//		String id = Utils.getGoodRandomString(g.usedEntityIds, g.entity_id_len);
//		g.team1_flag = new Flag(g.team1_base, Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0, 1.0, id, 1);
//		g.usedEntityIds.add(id);
//		id = Utils.getGoodRandomString(g.usedEntityIds, g.entity_id_len);
//		g.team2_flag = new Flag(g.team2_base, Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0, 1.0, id, 2);
//		g.usedEntityIds.add(id);
//		
//		g.spawns.add(new Spawn((short) 33, (short) 2, 1));
//		g.spawns.add(new Spawn((short) 37, (short) 5, 1));
//		g.spawns.add(new Spawn((short) 42, (short) 5, 1));
//		g.spawns.add(new Spawn((short) 47, (short) 2, 1));
//		
//		g.spawns.add(new Spawn((short) 1, (short) 11, 2));
//		g.spawns.add(new Spawn((short) 2, (short) 21, 2));
//		g.spawns.add(new Spawn((short) 10, (short) 22, 2));
//		g.spawns.add(new Spawn((short) 20, (short) 15, 2));
//	}
//	
//	private static void loadMap1_FFAElements(FreeForAll g) {
//		//make a list of nodes
//		List<PowerUpSpawn> pu_nodes = new ArrayList<PowerUpSpawn>();
//		pu_nodes.add(new PowerUpSpawn((short) 2, (short) 2));
//		pu_nodes.add(new PowerUpSpawn((short) 11, (short) 7));
//		pu_nodes.add(new PowerUpSpawn((short) 15, (short) 21));
//		pu_nodes.add(new PowerUpSpawn((short) 20, (short) 9));
//		pu_nodes.add(new PowerUpSpawn((short) 27, (short) 14));
//		pu_nodes.add(new PowerUpSpawn((short) 34, (short) 13));
//		pu_nodes.add(new PowerUpSpawn((short) 38, (short) 15));
//		pu_nodes.add(new PowerUpSpawn((short) 44, (short) 19));
//		g.pu_handler.setNodeList(pu_nodes);
//		
//		g.spawns.add(new Spawn((short) 3, (short) 2, 1));
//		g.spawns.add(new Spawn((short) 8, (short) 21, 2));
//		g.spawns.add(new Spawn((short) 15, (short) 23, 3));
//		g.spawns.add(new Spawn((short) 20, (short) 9, 4));
//		g.spawns.add(new Spawn((short) 27, (short) 15, 5));
//		g.spawns.add(new Spawn((short) 33, (short) 6, 6));
//		g.spawns.add(new Spawn((short) 47, (short) 2, 7));
//		g.spawns.add(new Spawn((short) 4, (short) 19, 8));
//	}
//	
//	private static void loadMap1_TDMElements(TeamDeathMatch g) {
//		//make a list of nodes
//		List<PowerUpSpawn> pu_nodes = new ArrayList<PowerUpSpawn>();
//		pu_nodes.add(new PowerUpSpawn((short) 2, (short) 2));
//		pu_nodes.add(new PowerUpSpawn((short) 11, (short) 7));
//		pu_nodes.add(new PowerUpSpawn((short) 15, (short) 21));
//		pu_nodes.add(new PowerUpSpawn((short) 20, (short) 9));
//		pu_nodes.add(new PowerUpSpawn((short) 27, (short) 14));
//		pu_nodes.add(new PowerUpSpawn((short) 34, (short) 13));
//		pu_nodes.add(new PowerUpSpawn((short) 38, (short) 15));
//		pu_nodes.add(new PowerUpSpawn((short) 44, (short) 19));
//		g.pu_handler.setNodeList(pu_nodes);
//		
//		g.spawns.add(new Spawn((short) 33, (short) 2, 1));
//		g.spawns.add(new Spawn((short) 37, (short) 5, 1));
//		g.spawns.add(new Spawn((short) 42, (short) 5, 1));
//		g.spawns.add(new Spawn((short) 47, (short) 2, 1));
//		
//		g.spawns.add(new Spawn((short) 1, (short) 11, 2));
//		g.spawns.add(new Spawn((short) 2, (short) 21, 2));
//		g.spawns.add(new Spawn((short) 10, (short) 22, 2));
//		g.spawns.add(new Spawn((short) 20, (short) 15, 2));
//	}
//	//////END MAP 1//////
//}