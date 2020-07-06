package com.cycapservers.game;

public class Spawn extends GridLockedPosition {
	
	private Team team;
	
	public Spawn(double x, double y, Team team) {
		super(x, y);
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
