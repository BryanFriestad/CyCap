package com.cycapservers.game.matchmaking;

import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.Team;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.entities.EntityFactory;

public class IncomingPlayer 
{	
	private String client_id;
	private CharacterClass role;
//	private WebSocketSession session;
	private Team team;
	
	public IncomingPlayer(String c, CharacterClass r, Team t) 
	{
		this.client_id = c;
		this.role = r;
//		this.session = session;
		this.team = t;
	}
	
	public Entity BuildPlayer()
	{
		return EntityFactory.getInstance().ManufacturePlayerCharacter(client_id, team, role);
	}
	
	public String GetClientId()
	{
		return client_id;
	}

	public Team GetTeam() 
	{
		return team;
	}
	
	public void SetTeam(Team t)
	{
		team = t;
	}
	
	public CharacterClass GetCharacterClass()
	{
		return role;
	}
}