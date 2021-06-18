package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.positioning.PositionComponent;

public class Spawn extends Entity 
{
	private Team team;
	
	public Spawn(PositionComponent p, Team team) 
	{
		super(p);
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
