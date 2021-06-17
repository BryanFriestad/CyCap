package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.positioning.GridLockedPositionComponent;

public class Spawn extends Entity 
{
	private Team team;
	
	public Spawn(short x, short y, Team team) 
	{
		super(new GridLockedPositionComponent(x, y));
		this.team = team;
	}

	public Team getTeam() 
	{
		return team;
	}

	public void setTeam(Team team) 
	{
		this.team = team;
	}

}
