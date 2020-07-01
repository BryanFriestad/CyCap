package com.cycapservers.game;

import java.util.ArrayList;
import java.util.List;

public class InputSnapshot {
	
	private Position mouse_position;
	private List<Integer> keys_down;
	private int snapshotNum;
	private int game_id;
	private String client_id;
	private String password;
	
	//unused
	private double frameTime;
	private long timeStamp;
	private double canvasX;
	private double canvasY;
	private boolean mouse_clicked;
	private boolean lmb_down;
	private List<Integer> keys_pnr;
	protected double mapX;
	protected double mapY;
	
	/**
	 * 
	 * @param data Takes in a string representing the input sent from the client
	 */
	public InputSnapshot(String data) {
		this.timeStamp = System.currentTimeMillis();
		String[] arr = data.split(":");
		this.game_id = Integer.parseInt(arr[1]);
		this.password = arr[2];
		
		this.mapX = Double.parseDouble(arr[3]);
		this.mapY = Double.parseDouble(arr[4]);
		this.mouse_position = new Position(mapX, mapY);
		this.canvasX = Double.parseDouble(arr[5]);
		this.canvasY = Double.parseDouble(arr[6]);
		this.mouse_clicked = Boolean.parseBoolean(arr[7]);
		this.lmb_down = Boolean.parseBoolean(arr[8]);
		
		this.keys_down = new ArrayList<Integer>();
		String[] temp = arr[9].split(",");
		if(!temp[0].equals("")) {
			for(int i = 0; i < temp.length; i++) {
				this.keys_down.add(Integer.parseInt(temp[i]));
			}
		}
		if(lmb_down) {
			this.keys_down.add(1); //JS keycode 1 is seemingly unused
		}
		
		this.keys_pnr = new ArrayList<Integer>();
		temp = arr[10].split(",");
		if(!temp[0].equals("")) {
			for(int i = 0; i < temp.length; i++) {
				this.keys_pnr.add(Integer.parseInt(temp[i]));
			}
		}
		
		this.snapshotNum = Integer.parseInt(arr[11]);
		this.frameTime = Double.parseDouble(arr[12]);
	}
	
	public List<Integer> getDown(){
		return this.keys_down;
	}

	public Position getMouse_position() {
		return mouse_position;
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