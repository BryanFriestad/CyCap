package com.cycapservers.game.components;

import com.cycapservers.game.Team;

public class TeamComponent extends Component 
{
	private Team team;
	
	public TeamComponent(Team t)
	{
		super("team");
		this.team = t;
	}

	@Override
	public Object GetJSONValue() 
	{
		return team;
	}

	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}
	
	public Team GetTeam()
	{
		return team;
	}

}
