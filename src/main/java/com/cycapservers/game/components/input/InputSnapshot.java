package com.cycapservers.game.components.input;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cycapservers.game.components.positioning.PositionComponent;

public class InputSnapshot {
	
	private PositionComponent mouse_position_canvas;
	private PositionComponent mouse_position_world;
	private List<Integer> keys_down;
	
	private int snapshotNum;
	
	private int game_id;
	private String client_id;
	private String password;
	
	public InputSnapshot(JSONObject message)
	{
		JSONObject snapshot_obj = message.getJSONObject("snapshot");
		this.game_id = snapshot_obj.getInt("game_id");
		this.client_id = message.getString("client_id");
		this.password = snapshot_obj.getString("input_code");
		
		double canvasX = snapshot_obj.getDouble("canvasX");
		double canvasY = snapshot_obj.getDouble("canvasY");
		this.mouse_position_canvas = new PositionComponent(canvasX, canvasY);
		
		double mapX = snapshot_obj.getDouble("mapX");
		double mapY = snapshot_obj.getDouble("mapY");
		this.mouse_position_world = new PositionComponent(mapX, mapY);
		
		keys_down = new ArrayList<Integer>();
		JSONArray arr = snapshot_obj.getJSONArray("keys_down");
		for (int i = 0; i < arr.length(); i++)
		{
			keys_down.add(arr.getInt(i));
		}
		
		if (snapshot_obj.getBoolean("lmb_down"))
		{
			keys_down.add(1); // The key number 1 is unused by JS I guess.
		}
		
		this.snapshotNum = snapshot_obj.getInt("snapshotNum");
	}
	
	public List<Integer> getDown(){
		return this.keys_down;
	}

	public PositionComponent GetMouseCanvasPosition() 
	{
		return mouse_position_canvas;
	}
	
	public PositionComponent GetMouseWorldPosition()
	{
		return mouse_position_world;
	}

	public int getSnapshotNum() {
		return snapshotNum;
	}

	public String getPassword() {
		return password;
	}

	public int getGame_id() {
		return game_id;
	}

	public String getClient_id() {
		return client_id;
	}
}