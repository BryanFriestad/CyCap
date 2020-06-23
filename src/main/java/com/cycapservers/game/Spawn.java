package com.cycapservers.game;

public class Spawn extends GridLockedPosition {
	
	private int team;
	
	public Spawn(double x, double y, int team) {
		super(x, y);
		this.team = team;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

}
